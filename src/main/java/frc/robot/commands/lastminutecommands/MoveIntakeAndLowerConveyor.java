package frc.robot.commands.lastminutecommands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConveyorSubsystem;

public class MoveIntakeAndLowerConveyor extends CommandBase {
  /**
   * Creates a new MoveIntakeAndLowerConveyor.
   */

    // The subsytem command runs on the following:
    private final ConveyorSubsystem m_conveyorSubsystem;

  public MoveIntakeAndLowerConveyor(ConveyorSubsystem subsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_conveyorSubsystem = subsystem;
    addRequirements(m_conveyorSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_conveyorSubsystem.move_intake_motor(0.6);
    m_conveyorSubsystem.override_Lower_conveyor(0.6);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_conveyorSubsystem.move_intake_motor(0);
    m_conveyorSubsystem.override_Lower_conveyor(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}