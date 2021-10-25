package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
//import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
//import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
//import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ElevatorSubsystem extends SubsystemBase{
    
    private final WPI_TalonFX m_elevatorMotor = new WPI_TalonFX(Constants.ELEVATOR_TALON_FX);

  double init_setpoint;
  double setpoint = 0;


//make the command 
public void elevatorSubsystem() {
    // configureFeedback();
    // this.init_setpoint = m_elevatorMotor.getSelectedSensorPosition(Constants.ELEVATOR_PID_LOOP);
    // this.setpoint += this.init_setpoint;
    m_elevatorMotor.setNeutralMode(NeutralMode.Coast);
    m_elevatorMotor.setInverted(true);
    
  }

  
  public void moveElevatorOpenLoop(double speed){
    SmartDashboard.putNumber("Elevator speed", speed);
    m_elevatorMotor.set(speed);
  }

  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // SmartDashboard.putNumber("Elevator Position Counts", getPosition_counts());
    // SmartDashboard.putNumber("Elevator Error", getError());
    // SmartDashboard.putNumber("Elevator setpoint (counts)", this.setpoint);
  }
}