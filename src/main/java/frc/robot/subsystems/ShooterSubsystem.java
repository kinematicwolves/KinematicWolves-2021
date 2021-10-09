/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import java.lang.Math;

public class ShooterSubsystem extends SubsystemBase {
  /**
   * Creates a new ShooterSubsystem.
   */

  public static WPI_TalonSRX spinShooterTalon_1 = new WPI_TalonSRX(Constants.SPIN_SHOOTER_MOTOR_1); // This is the CAN ID for the device 
  public static WPI_TalonSRX spinShooterTalon_2 = new WPI_TalonSRX(Constants.SPIN_SHOOTER_MOTOR_2); // This is the CAN ID for the device 
  public static Servo angleActuator_1 = new Servo(Constants.LINEAR_ACTUATOR_1); // PWM controlled
  public static Servo angleActuator_2 = new Servo(Constants.LINEAR_ACTUATOR_2); // PWM controlled

  public ShooterSubsystem(){
    spinShooterTalon_1.setInverted(true);
    spinShooterTalon_2.setInverted(true);

  }

  public void shootBall(double speed){
    spinShooterTalon_1.set(speed);
    spinShooterTalon_2.set(speed);
  }

  public void setLinearActuatorPosition(double position){
    // Check to make sure position is not out of bounds
    position = clipLinearActuatorPositionCommand(position);
    angleActuator_1.set(position);
    angleActuator_2.set(position);

  }

  private double clipLinearActuatorPositionCommand(double position){
    // Make sure command does not exceed the hardware limit
    if (position > Constants.UPPER_SERVO_POS_LIMIT){
      position = Constants.UPPER_SERVO_POS_LIMIT;
    }

    if (position < Constants.LOWER_SERVO_POS_LIMIT){
      position = Constants.LOWER_SERVO_POS_LIMIT;
    }
    return position;
  }

  public boolean servo_at_position(double endPosition){
    double actuator_1_position = angleActuator_1.getPosition();
    double actuator_2_position = angleActuator_2.getPosition();

    double actuator_1_position_delta = Math.abs(actuator_1_position - endPosition);
    double actuator_2_position_delta = Math.abs(actuator_2_position - endPosition);

    return ((actuator_1_position_delta + actuator_2_position_delta) < 0.04);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}