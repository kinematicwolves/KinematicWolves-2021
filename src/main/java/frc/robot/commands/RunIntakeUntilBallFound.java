/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorSubsystem;

public class RunIntakeUntilBallFound extends CommandBase {
  /**
   * Creates a new RunIntakeUntilBallFound.
   */
  ConveyorSubsystem m_conveyorSubsystem;
  double conveyor_speed;

  public RunIntakeUntilBallFound(ConveyorSubsystem conveyorSubsystem, double speed) {
    this.m_conveyorSubsystem = conveyorSubsystem;
    this.conveyor_speed = speed;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_conveyorSubsystem.override_Lower_conveyor(conveyor_speed);
    m_conveyorSubsystem.move_intake_motor(conveyor_speed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_conveyorSubsystem.override_Lower_conveyor(0);
    m_conveyorSubsystem.move_intake_motor(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_conveyorSubsystem.ballDetectedSensor1;
  }
}
