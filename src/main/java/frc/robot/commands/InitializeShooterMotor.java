/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

public class InitializeShooterMotor extends CommandBase {
  /**
   * Creates a new InitializeShooterMotor.
   */


  // The subsystem the command runs on
  private final ShooterSubsystem m_shooterSubsystem;
  double speed;
  double timer;
  

  public InitializeShooterMotor(ShooterSubsystem shooterSubsystem, double speed ) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_shooterSubsystem = shooterSubsystem;
    addRequirements(m_shooterSubsystem);
    this.speed = speed;
    this.timer = 0;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooterSubsystem.shootBall(speed);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      timer += 20;
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // End when timer is more than 500 ms
    return (timer > 2500);
  }
}
