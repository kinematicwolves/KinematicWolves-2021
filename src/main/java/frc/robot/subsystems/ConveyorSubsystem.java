/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

public class ConveyorSubsystem extends SubsystemBase {
  /**
   * Creates a new ConveyorSubsystem.
   */
  public boolean ballDetectedSensor1 = false;
  public boolean ballDetectedSensor2 = false;
  public boolean ballDetectedSensor3 = false;
  public boolean ballDetectedSensor4 = false;
 
  public boolean intakeExtended = false;
  //                                       [Index 0]                  [Index 1]              [Index 2]               [Index 3]
  // Add a boolean array containing each of the ballDetectedSensor readings
  // public boolean[] ballDetectedArray = 

  DigitalInput ballIndexSensor1 = new DigitalInput(Constants.BALL_INDEX_SENSOR_1_DI_NUM);
  DigitalInput ballIndexSensor2 = new DigitalInput(Constants.BALL_INDEX_SENSOR_2_DI_NUM);
  DigitalInput ballIndexSensor3 = new DigitalInput(Constants.BALL_INDEX_SENSOR_3_DI_NUM);
  DigitalInput ballIndexSensor4 = new DigitalInput(Constants.BALL_INDEX_SENSOR_4_DI_NUM);

  public int nextBallPosition = 2;
  
  public static WPI_TalonSRX lowerConveyoorTalon = new WPI_TalonSRX(Constants.LOWER_CONVEYOR_MOTOR);
  public static WPI_TalonSRX intakeTalon = new WPI_TalonSRX(Constants.INTAKE_MOTOR);
  public static WPI_TalonSRX topConveyorTalon = new WPI_TalonSRX(Constants.TOP_CONVEYOR_MOTOR);

  public static DoubleSolenoid intakeSwitch = new DoubleSolenoid(Constants.PNEUMATIC_CONTROL_MODULE, Constants.INTAKE_SOL_FWD_CHN, Constants.INTAKE_SOL_RVS_CHN);
  
  public ConveyorSubsystem() {
    lowerConveyoorTalon.setNeutralMode(NeutralMode.Coast);
    intakeTalon.setInverted(true);
  }
  
  public void actuate_intake() {
    if (intakeExtended) {
      intakeSwitch.set(Value.kForward);
      intakeExtended = false;
    }
    else {
      intakeSwitch.set(Value.kReverse);
      intakeExtended = true;
    }

  }

  public void move_top_conveyor(double speed) {
    topConveyorTalon.set(speed);
  }
  
  // Update this method to only move the lower conveyor (no checks in place)
  public void move_lower_conveyor(double speed){
    
  }

  /*
  Create a method to adjust the ball position. This will happen after a ball has been
  taken in by the intake (and found by sensor 1). This method will have two different
  scenarios it must handle. 
  1. When it only needs to move the lower conveyor (hint, first ball it takes in)
  2. When it needs to move both conveyors at once (hint, second or third ball that is taken in)
  */
  public void adjust_ball_positioning(double speed){

  }

  public void move_intake_motor(double speed){
    intakeTalon.set(speed);
  }

  public void override_Lower_conveyor(double speed){
    lowerConveyoorTalon.set(speed);
  }

  public int get_next_ball_position() {
    return nextBallPosition;
  }

  /*
  We need a method to increment the ball position after one is taken in.
  This will be called after a ball is moved to its desired position.
  */
  public void increment_ball_position(){

  }

  /*
  We need a method to tell us if the ball is at the desired position. Hint,
  this is why we created the boolean array earlier!

  - This method gets called when the intake is running
  - Tells the command when to stop
  */
  //public boolean ball_at_desired_position(){
    
  //}

  public void move_conveyors(double motorOutput){ 
    if (!ballDetectedSensor1 && !ballDetectedSensor2 && !ballDetectedSensor3 && !ballDetectedSensor4){
      lowerConveyoorTalon.set(motorOutput); //state 1
      intakeTalon.set(motorOutput);
      topConveyorTalon.set(motorOutput);
    } 
    else if (ballDetectedSensor1 && !ballDetectedSensor2 && !ballDetectedSensor3 && !ballDetectedSensor4){
      lowerConveyoorTalon.set(motorOutput); //state 2
      intakeTalon.set(0);
      topConveyorTalon.set(0);
    }
    else if (!ballDetectedSensor1 && ballDetectedSensor2 && !ballDetectedSensor3 && !ballDetectedSensor4){
      lowerConveyoorTalon.set(0); //state 3
      intakeTalon.set(motorOutput);
      topConveyorTalon.set(0);
      }
    else if (ballDetectedSensor1 && ballDetectedSensor2 && !ballDetectedSensor3 && !ballDetectedSensor4){
      lowerConveyoorTalon.set(motorOutput); //state 4
      intakeTalon.set(0);
      topConveyorTalon.set(motorOutput);
    }
    else if (!ballDetectedSensor1 && !ballDetectedSensor2 && ballDetectedSensor3 && !ballDetectedSensor4){
      lowerConveyoorTalon.set(motorOutput); //done 1
      intakeTalon.set(motorOutput);
      topConveyorTalon.set(motorOutput);
    }
    else if (ballDetectedSensor1 && !ballDetectedSensor2 && ballDetectedSensor3 && !ballDetectedSensor4){
      lowerConveyoorTalon.set(motorOutput); //done 2
      intakeTalon.set(0);
      topConveyorTalon.set(motorOutput);
    }
    else if (!ballDetectedSensor1 && ballDetectedSensor2 && ballDetectedSensor3 && !ballDetectedSensor4){
      lowerConveyoorTalon.set(0); // state 5
      intakeTalon.set(motorOutput);
      topConveyorTalon.set(0);
    }
    else if (ballDetectedSensor1 && ballDetectedSensor2 && ballDetectedSensor3 && !ballDetectedSensor4){
      lowerConveyoorTalon.set(motorOutput); //state 6
      intakeTalon.set(0);
      topConveyorTalon.set(motorOutput);
    }
    else if (!ballDetectedSensor1 && !ballDetectedSensor2 && !ballDetectedSensor3 && ballDetectedSensor4){
      lowerConveyoorTalon.set(0); //done 3
      intakeTalon.set(motorOutput);
      topConveyorTalon.set(0);
    }
    else if (ballDetectedSensor1 && !ballDetectedSensor2 && !ballDetectedSensor3 && ballDetectedSensor4){
      lowerConveyoorTalon.set(0); //done 4
      intakeTalon.set(0);
      topConveyorTalon.set(0);
    }
    else if (!ballDetectedSensor1 && ballDetectedSensor2 && !ballDetectedSensor3 && ballDetectedSensor4){
      lowerConveyoorTalon.set(0); //done 5
      intakeTalon.set(motorOutput);
      topConveyorTalon.set(0);
    }
    else if (ballDetectedSensor1 && !ballDetectedSensor2 && ballDetectedSensor3 && ballDetectedSensor4){
      lowerConveyoorTalon.set(0); //done 6
      intakeTalon.set(0);
      topConveyorTalon.set(0);
    }
    else if (!ballDetectedSensor1 && !ballDetectedSensor2 && ballDetectedSensor3 && ballDetectedSensor4){
      lowerConveyoorTalon.set(motorOutput); //done 7
      intakeTalon.set(motorOutput);
      topConveyorTalon.set(0);
    }
    else if (ballDetectedSensor1 && !ballDetectedSensor2 && ballDetectedSensor3 && ballDetectedSensor4){
      lowerConveyoorTalon.set(motorOutput); // done 8
      intakeTalon.set(0);
      topConveyorTalon.set(0);
    }
    else if (!ballDetectedSensor1 && ballDetectedSensor2 && ballDetectedSensor3 && ballDetectedSensor4){
      lowerConveyoorTalon.set(0); //state 7
      intakeTalon.set(0);
      topConveyorTalon.set(0);
    }
    else if (ballDetectedSensor1 && ballDetectedSensor2 && ballDetectedSensor3 && ballDetectedSensor4){
      lowerConveyoorTalon.set(0); //done 9
      intakeTalon.set(0);
      topConveyorTalon.set(0);
    }

  }

  public String find_truth_table_state(){
    if (!ballDetectedSensor1 && !ballDetectedSensor2 && !ballDetectedSensor3 && !ballDetectedSensor4){
      return "state 1";
    } 
    else if (ballDetectedSensor1 && !ballDetectedSensor2 && !ballDetectedSensor3 && !ballDetectedSensor4){
      return "state 2";
    }
    else if (!ballDetectedSensor1 && ballDetectedSensor2 && !ballDetectedSensor3 && !ballDetectedSensor4){
      return "state 3";
      }
    else if (ballDetectedSensor1 && ballDetectedSensor2 && !ballDetectedSensor3 && !ballDetectedSensor4){
      return "state 4";
    }
    else if (!ballDetectedSensor1 && !ballDetectedSensor2 && ballDetectedSensor3 && !ballDetectedSensor4){
      return "done 1";
    }
    else if (ballDetectedSensor1 && !ballDetectedSensor2 && ballDetectedSensor3 && !ballDetectedSensor4){
      return "done 2";
    }
    else if (!ballDetectedSensor1 && ballDetectedSensor2 && ballDetectedSensor3 && !ballDetectedSensor4){
      return " state 5";
    }
    else if (ballDetectedSensor1 && ballDetectedSensor2 && ballDetectedSensor3 && !ballDetectedSensor4){
      return "state 6";
    }
    else if (!ballDetectedSensor1 && !ballDetectedSensor2 && !ballDetectedSensor3 && ballDetectedSensor4){
      return "done 3";
    }
    else if (ballDetectedSensor1 && !ballDetectedSensor2 && !ballDetectedSensor3 && ballDetectedSensor4){
      return "done 4";
    }
    else if (!ballDetectedSensor1 && ballDetectedSensor2 && !ballDetectedSensor3 && ballDetectedSensor4){
      return "done 5";
    }
    else if (ballDetectedSensor1 && !ballDetectedSensor2 && ballDetectedSensor3 && ballDetectedSensor4){
      return "done 6";
    }
    else if (!ballDetectedSensor1 && !ballDetectedSensor2 && ballDetectedSensor3 && ballDetectedSensor4){
      return "done 7";
    }
    else if (ballDetectedSensor1 && !ballDetectedSensor2 && ballDetectedSensor3 && ballDetectedSensor4){
      return " done 8";
    }
    else if (!ballDetectedSensor1 && ballDetectedSensor2 && ballDetectedSensor3 && ballDetectedSensor4){
      return "state 7";
    }
    else if (ballDetectedSensor1 && ballDetectedSensor2 && ballDetectedSensor3 && ballDetectedSensor4){
      return "done 9";
    }
    else {
      return "nothing";
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    ballDetectedSensor1 = !ballIndexSensor1.get();
    ballDetectedSensor2 = !ballIndexSensor2.get();
    ballDetectedSensor3 = !ballIndexSensor3.get();
    ballDetectedSensor4 = !ballIndexSensor4.get();
    SmartDashboard.putBoolean("Digital Sensor 1 Value:", ballDetectedSensor1);
    SmartDashboard.putBoolean("Digital Sensor 2 Value:", ballDetectedSensor2);
    SmartDashboard.putBoolean("Digital Sensor 3 Value:", ballDetectedSensor3);
    SmartDashboard.putBoolean("Digital Sensor 4 Value:", ballDetectedSensor4);
    SmartDashboard.putString("Truth table line: ", find_truth_table_state());
  }
}