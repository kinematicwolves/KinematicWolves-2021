/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorSubsystem;

public class ActuateIntake extends CommandBase {
  /**
   * Creates a new ActuateIntake.
   */

    // private double time = 0.5;

    // The subsytem command runs on the following:
    private final ConveyorSubsystem m_conveyorSubsystem;

  public ActuateIntake(ConveyorSubsystem subsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_conveyorSubsystem = subsystem;
    addRequirements(m_conveyorSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_conveyorSubsystem.actuate_intake();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}