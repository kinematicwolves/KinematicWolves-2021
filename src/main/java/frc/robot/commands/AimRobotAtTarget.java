/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.VisionSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AimRobotAtTarget extends PIDCommand {
  /**
   * Creates a new AimRobotAtTarget.
   */

  public AimRobotAtTarget(double targetAngle, VisionSubsystem m_visionSubsystem, DriveTrainSubsystem m_drivetrain) {
    super(
        // The controller that the command will use
        new PIDController(Constants.visionKp, Constants.visionKi, Constants.visionKd),
        // This should return the measurement
        m_visionSubsystem :: getFilteredHorizontalAngle,
        // This should return the setpoint (can also be a constant)
        targetAngle,
        // This uses the output
        output -> {
          m_drivetrain.align_with_target(output);
          // Use the output here
        });
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_drivetrain);
    addRequirements(m_visionSubsystem);

    // Configure additional PID options by calling `getController` here.
    getController().setTolerance(Constants.alignment_x_tolerance);
    System.out.println("Command recognized");
  }

  // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return getController().atSetpoint();

    }
}
