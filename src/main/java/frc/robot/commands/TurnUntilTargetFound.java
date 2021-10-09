/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class TurnUntilTargetFound extends CommandBase {
    /**
     * Creates a new TurnUntilTargetFound.
     */
    private final DriveTrainSubsystem m_drivetrain;
    private final VisionSubsystem m_visionSubsystem;
    private boolean direction;
    private double rotatationRate;

    public TurnUntilTargetFound(DriveTrainSubsystem drivetrain, VisionSubsystem visionSubsystem, boolean direction,
            double rotatationRate) {

        m_drivetrain = drivetrain;
        m_visionSubsystem = visionSubsystem;
        this.direction = direction;
        this.rotatationRate = rotatationRate;

        // Use addRequirements() here to declare subsystem dependencies.
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        if (direction) {
            // Rotate in the positive direction, clockwise
            m_drivetrain.rotateDrivetrain(-1 * rotatationRate);
        } else {
            // Rotate in the negative direction, counter-clockwise
            m_drivetrain.rotateDrivetrain(rotatationRate);
        }

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        
        if (m_visionSubsystem.getCaptureStatus() == 1) {
            return true;
        } else {
            return false;
        }

    }
}
