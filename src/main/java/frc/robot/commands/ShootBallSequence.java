/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class ShootBallSequence extends SequentialCommandGroup{
   /* Add your docs here.
   */

  public ShootBallSequence(ShooterSubsystem m_shooterSubsystem, VisionSubsystem m_visionSubsystem,
      ConveyorSubsystem m_conveyorSubsystem) {
    addCommands(new InitializeShooterMotor(m_shooterSubsystem, 0.75),
        new ShootBall(m_shooterSubsystem, m_visionSubsystem, m_conveyorSubsystem)
        );
  }

}
