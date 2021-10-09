package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.VisionSubsystem;


public class TurnRightLineUp extends SequentialCommandGroup {

    public TurnRightLineUp(DriveTrainSubsystem m_drivetrain, VisionSubsystem m_visionSubsystem,
            ShooterSubsystem m_shooter) {
        addCommands(
            new TurnLimelightOn(m_visionSubsystem),
            new TurnUntilTargetFound(m_drivetrain, m_visionSubsystem, true, Constants.RotationRate),
            new AimRobotAtTarget(0, m_visionSubsystem, m_drivetrain),
            new TurnLimelightOff(m_visionSubsystem)
        );
    }
}