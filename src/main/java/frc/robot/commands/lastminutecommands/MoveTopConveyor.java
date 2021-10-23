package frc.robot.commands.lastminutecommands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorSubsystem;

public class MoveTopConveyor extends CommandBase {
  /**
   * Creates a new MoveTopConveyor.
   */

    // The subsytem command runs on the following:
    private final ConveyorSubsystem m_conveyorSubsystem;
    double m_speed;

  public MoveTopConveyor(ConveyorSubsystem subsystem,double speed) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_conveyorSubsystem = subsystem;
    m_speed= speed;
    addRequirements(m_conveyorSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_conveyorSubsystem.move_top_conveyor(m_speed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_conveyorSubsystem.move_top_conveyor(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}