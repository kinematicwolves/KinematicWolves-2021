package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ElevatorSubsystem;

public class MoveElevator extends CommandBase {
    /**
     * Creates a new MoveElevator.
     */
  
      // The subsytem command runs on the following:
      private final ElevatorSubsystem m_elevatorSubsystem;
      double m_Speed;
  
    public MoveElevator(ElevatorSubsystem subsystem,double speed) {
      // Use addRequirements() here to declare subsystem dependencies.
      m_elevatorSubsystem = subsystem;
      m_Speed = speed;
      addRequirements(m_elevatorSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    m_elevatorSubsystem.moveElevatorOpenLoop(m_Speed);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
     public void execute() {
    }

    // Called once the command ends or is interrupted.
     @Override
    public void end(boolean interrupted) {
    m_elevatorSubsystem.moveElevatorOpenLoop(0);
     }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return false;
    }
  }