/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AutonLineUpShootBall;
import frc.robot.commands.DriveRobotWithJoysticks;
import frc.robot.commands.ReverseConveyors;
import frc.robot.commands.SequentialIntakeBall;
import frc.robot.commands.ShiftGear;
import frc.robot.commands.ShootBall;
import frc.robot.commands.ShootBallSequence;
import frc.robot.commands.TurnLeftLineUp;
import frc.robot.commands.TurnLimelightOff;
import frc.robot.commands.TurnLimelightOn;
import frc.robot.commands.TurnRightLineUp;
import frc.robot.commands.AutonLineUpShootBall;
import frc.robot.commands.RunIntake;
import frc.robot.commands.ActuateIntake;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.commands.lastminutecommands.*;
import frc.robot.commands.MoveLowerConveyor;
import frc.robot.commands.IntakeBall;



/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ConveyorSubsystem m_conveyorSubsystem = new ConveyorSubsystem();
  private final DriveTrainSubsystem m_driveTrain = new DriveTrainSubsystem();
  private final VisionSubsystem m_visionSubsystem = new VisionSubsystem();
  private final ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem();
  //private final ElevatorSubsystem m_elevatorSubsystem = new ElevatorSubsystem();
  
  private final UsbCamera camera0 = CameraServer.getInstance().startAutomaticCapture(0);
  
  // Controllers
  private final Joystick driverController = new Joystick(Constants.DRIVER_CONTROLLER);
  private final Joystick manipulatorController = new Joystick(Constants.MANIPULATOR_CONTROLLER);  

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    setDefaultCommands();
    camera0.setResolution(Constants.IMG_WIDTH, Constants.IMG_HEIGHT);
  }

  public Joystick getJoystick() {
    return driverController;
  }

  public void setDefaultCommands(){
    m_driveTrain.setDefaultCommand(new DriveRobotWithJoysticks(m_driveTrain, driverController));
    m_visionSubsystem.turnLimelightOff();
  }

  public void setDisabledState(){
    if (m_driveTrain.isHighGear){
      new ShiftGear(m_driveTrain);
    }
    m_visionSubsystem.turnLimelightOff();
  }
  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    /**
     * Declare buttons/controller inputs
     * 
     * naming convention: d_* is driver controller, m_* is manipulator controller
     */

    //final JoystickButton m_aButton = new JoystickButton(manipulatorController, Constants.A_BUTTON);
    final JoystickButton m_bButton = new JoystickButton(manipulatorController, Constants.B_BUTTON);
    final JoystickButton m_xButton = new JoystickButton(manipulatorController, Constants.X_BUTTON);
    final JoystickButton m_yButton = new JoystickButton(manipulatorController, Constants.Y_BUTTON);
    // final JoystickButton m_dPadUp = new JoystickButton(manipulatorController, Constants.D_PAD_UP);
    // final JoystickButton m_dPadDown = new JoystickButton(manipulatorController, Constants.D_PAD_DOWN);
    final JoystickButton m_aButton = new JoystickButton(manipulatorController, Constants.A_BUTTON);
    final JoystickButton m_r1Button = new JoystickButton(manipulatorController, Constants.R2);

    //m_dPadUp.whileHeld(new MoveElevator(m_elevatorSubsystem, Constants.ELEVATOR_SPEED);
    //m_dPadDown.whileHeld(new MoveElevator(m_elevatorSubsystem, -1 * Constants.ELEVATOR_SPEED));
    m_aButton.whileHeld(new ShootBallSequence(m_shooterSubsystem, m_visionSubsystem, m_conveyorSubsystem)); // Works
    m_xButton.whileHeld(new MoveLowerConveyor(m_conveyorSubsystem, 0.6));
    m_bButton.whileHeld(new MoveLowerConveyor(m_conveyorSubsystem, -0.6)); //need to add negative values
    m_r1Button.whileHeld(new ShootBallSequence(m_shooterSubsystem, m_visionSubsystem, m_conveyorSubsystem));
    /*
    Troubleshooting the MoveLowerConveyor

    - We think the commands being sent to the controller are correct
    - Next question: is the controller recieving them?
      - Look at controller light, is it responding when you try to move the lower conveyor?
        - If it isn't, then might be the wrong CAN ID in constants.java. Verify CAN id using pheonix tuner.
        - It might not be connected to the can network.
        - In pheonix tuner, check the firmware versions of talons, make sure they are up to date.
      If it is recieving the commands, we maybe need to look at code a little harder.
    */

    //m_yButton.whileHeld(new ShootBall(m_shooterSubsystem, m_visionSubsystem, m_conveyorSubsystem)); // works 
    //m_r2Button.whileHeld(new ShootBall(m_shooterSubsystem, m_visionSubsystem, m_conveyorSubsystem)); // Does not work (10/9)
    m_yButton.whileHeld(new MoveTopConveyor(m_conveyorSubsystem, 0.6));
    
    // Driver Controller
 
    final JoystickButton d_xButton = new JoystickButton(driverController, Constants.X_BUTTON);
    final JoystickButton d_aButton = new JoystickButton(driverController, Constants.A_BUTTON);
    final JoystickButton d_bButton = new JoystickButton(driverController, Constants.B_BUTTON);
    // final JoystickButton d_yButton = new JoystickButton(driverController, Constants.Y_BUTTON);
    // final JoystickButton d_aButton = new JoystickButton(driverController, Constants.A_BUTTON); 
    final JoystickButton d_r1Button = new JoystickButton(driverController, Constants.R1);
    final JoystickButton d_r2Button = new JoystickButton(driverController, Constants.R2);
    final JoystickButton d_l2Button = new JoystickButton(driverController, Constants.L2);

    // d_yButton.whenPressed(new AutonLineUpShootBall(m_driveTrain,  m_visionSubsystem,
    // m_shooterSubsystem, m_conveyorSubsystem));
    d_aButton.whileHeld(new RunIntake(m_conveyorSubsystem));// not sure
    d_r1Button.whenPressed(new ShiftGear(m_driveTrain));  // works     
    //d_xButton.whileHeld(new IntakeBall(m_driveTrain, m_visionSubsystem, m_shooterSubsystem));
    d_xButton.whileHeld(new IntakeBall(m_conveyorSubsystem, 0.8, 0.6)); // not sure
    
    d_bButton.whileHeld(new TurnRightLineUp(m_driveTrain, m_visionSubsystem, m_shooterSubsystem)); // works
    //d_r1Button.whenPressed(new TurnLimelightOn(m_visionSubsystem));
    d_r2Button.whenPressed(new TurnLimelightOff(m_visionSubsystem)); 

  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    Command autonShootBall = new AutonLineUpShootBall(m_driveTrain, m_visionSubsystem, m_shooterSubsystem, m_conveyorSubsystem);
    return autonShootBall;
  }
}