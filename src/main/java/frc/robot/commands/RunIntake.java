/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ConveyorSubsystem;


public class RunIntake extends CommandBase {
  /**
   * Creates a new RunIntake.
   */


  // The subsystem the command runs on
  private final ConveyorSubsystem m_conveyorSubsystem;

  public RunIntake(ConveyorSubsystem conveyorSubsystem ) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_conveyorSubsystem = conveyorSubsystem;
    addRequirements(m_conveyorSubsystem);
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_conveyorSubsystem.move_conveyors(Constants.INTAKE_SPEED);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_conveyorSubsystem.move_conveyors(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
