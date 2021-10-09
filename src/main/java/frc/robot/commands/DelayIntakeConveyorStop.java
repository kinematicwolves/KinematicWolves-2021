/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorSubsystem;

public class DelayIntakeConveyorStop extends CommandBase {
  /**
   * Creates a new DelayIntakeConveyorStop.
   */


  // The subsystem the command runs on
  private final ConveyorSubsystem m_conveyorSubsystem;
  double conveyor_speed;
  double timer = 0;

  public DelayIntakeConveyorStop(ConveyorSubsystem conveyorSubsystem, double conveyor_speed ) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_conveyorSubsystem = conveyorSubsystem;
    addRequirements(m_conveyorSubsystem);
    this.conveyor_speed = conveyor_speed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_conveyorSubsystem.override_Lower_conveyor(conveyor_speed);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      timer += 20;
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_conveyorSubsystem.override_Lower_conveyor(0);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // End when timer is more than 500 ms
    return (timer > 1000);
  }
}
