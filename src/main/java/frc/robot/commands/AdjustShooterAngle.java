/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.Constants;
import frc.robot.Utilities;
import frc.robot.subsystems.VisionSubsystem;

public class AdjustShooterAngle extends CommandBase {
  /**
   * Creates a new AdjustShooterAngle.
   */

  // Move this to constants so the same array is passed to both commands
  
  VisionSubsystem m_visionSubsystem;
  ShooterSubsystem m_shooterSubsystem;
  private double targetDistance;
  private double actuatorPosition;

  public AdjustShooterAngle(ShooterSubsystem shooterSubsystem, VisionSubsystem visionSubsytem){
    m_shooterSubsystem = shooterSubsystem;
    m_visionSubsystem = visionSubsytem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooterSubsystem);
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // m_shooterSubsystem.setLinearActuatorPosition(position);
    targetDistance = m_visionSubsystem.getFilteredDistance();
    actuatorPosition = Utilities.linearInterpolation(Constants.distances, Constants.position, targetDistance);
    m_shooterSubsystem.setLinearActuatorPosition(actuatorPosition);
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
    return m_shooterSubsystem.servo_at_position(actuatorPosition);
  }
}
