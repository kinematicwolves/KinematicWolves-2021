/*----------------------------------------------------------------------------*/
/* 1/22/2020 v1                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.analog.adis16448.frc.ADIS16448_IMU;
import com.analog.adis16448.frc.ADIS16448_IMU.IMUAxis;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.SlewRateLimiter;

public class DriveTrainSubsystem extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   */
  
  // Motor definition
  private final WPI_TalonFX rightMaster = new WPI_TalonFX(Constants.RIGHT_MOTOR_1); // This is the CAN ID for the device
  private final WPI_TalonFX rightSlave = new WPI_TalonFX(Constants.RIGHT_MOTOR_2); // This is the CAN ID for the device
  private final WPI_TalonFX leftMaster = new WPI_TalonFX(Constants.LEFT_MOTOR_1); // This is the CAN ID for the device
  private final WPI_TalonFX leftSlave = new WPI_TalonFX(Constants.LEFT_MOTOR_2); // This is the CAN ID for the device

  // IMU definition
  private final ADIS16448_IMU imu = new ADIS16448_IMU(IMUAxis.kX, SPI.Port.kMXP, 4); // This is the gyroscope definition

  // Hydraulics definition
  private final DoubleSolenoid DriveTrainSwitch = new DoubleSolenoid(Constants.PNEUMATIC_CONTROL_MODULE, Constants.DRVTRN_SOL_FWD_CHN, Constants.DRVTRN_SOL_RVS_CHN); // This is the definition of the solenoid for switching gears in the drivetrain 

  // Trajectory following objects
  private final DifferentialDrive drive = new DifferentialDrive(leftMaster, rightMaster);
  private final DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(Units.inchesToMeters(Constants.TrackWidth));
  private final DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(GetAngle());
  private Pose2d pose = new Pose2d();
  private final SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(Constants.RobotCharacterization_kS, Constants.RobotCharacterization_kV, Constants.RobotCharacterization_kA);
  private final PIDController leftPIDController = new PIDController(Constants.Traj_Following_Feedback_P_Gain, 0, 0);
  private final PIDController rightPIDController = new PIDController(Constants.Traj_Following_Feedback_P_Gain, 0, 0);
   
  // Slew rate limiter objects to use for driveability
  public SlewRateLimiter rotationFilter = new SlewRateLimiter(Constants.SLEW_RATE_LIMIT_ROTATE);
  public SlewRateLimiter accelerationFilter = new SlewRateLimiter(Constants.SLEW_RATE_LIMIT_ACCEL);

  // Gear for trajectory following
  public double gear_for_traj_following = Constants.GR1; // Assume we want to do trajectory following in second gear

  // Robot wheel radius
  public double robot_wheel_radius = Units.inchesToMeters(Constants.WheelRadius); // Convert from inches to meters

  // Drivetrain variables
  public boolean isHighGear = false; // Initialize to low gear
  public double leftDistance; // Distance traveled by robot (left)
  public double rightDistance; // Distance traveled by robot (right)
  public double left_wheel_speed; // Wheel speed of robot in m/s (left)
  public double right_wheel_speed; // Wheel speed of robot in m/s (right)
  public double yaw_angle; // Yaw angle of the robot in degrees

  public DriveTrainSubsystem() {      

    // Set motor inversion state
    leftSlave.setInverted(true);
    leftMaster.setInverted(true);
    rightSlave.setInverted(true);
    rightMaster.setInverted(true);
    rightMaster.configOpenloopRamp(Constants.DRIVE_CURRENT_RAMP_TIME);
    leftMaster.configOpenloopRamp(Constants.DRIVE_CURRENT_RAMP_TIME);

    leftMaster.setNeutralMode(NeutralMode.Coast);
    leftSlave.setNeutralMode(NeutralMode.Coast);
    rightMaster.setNeutralMode(NeutralMode.Coast);
    rightSlave.setNeutralMode(NeutralMode.Coast);

    // Set master slave relation
    rightSlave.follow(rightMaster);
    leftSlave.follow(leftMaster);

    // Set yaw axis
    

    // Reset gyroscope
    imu.reset();

  }

  public Rotation2d GetAngle() {

    // Get angle measurement
    return Rotation2d.fromDegrees(-imu.getAngle());

  }

  public DifferentialDriveWheelSpeeds GetSpeeds() {

    // Return DifferentialDriveWheelSpeeds object
    return new DifferentialDriveWheelSpeeds(left_wheel_speed, right_wheel_speed);

  }

  public DifferentialDriveKinematics getKinematics() {

    // Return kinematic object
    return kinematics;
    
  }

  public Pose2d getPose() {

    // Return pose object
    return pose;

  }

  public SimpleMotorFeedforward getFeedforward() {

    // Return feedforward object
    return feedforward;

  }

  public PIDController getLeftPIDController() {

    // Return PID object
    return leftPIDController;

  }

  public PIDController getRightPIDController() {

    // Return PID object
    return rightPIDController;

  }

  public void setOutputVolts(double leftVolts, double rightVolts) {

    // Set motor voltage
    leftMaster.set(ControlMode.PercentOutput, leftVolts / 12);
    rightMaster.set(ControlMode.PercentOutput, rightVolts / 12);

  }

  public void reset() {

    // Reset robot position
    odometry.resetPosition(new Pose2d(), GetAngle());

  }

  @Override
  public void periodic() {

    // In periodic we want to update sensor measurements

    // Update distance traveled
    leftDistance = ( (leftMaster.getSelectedSensorPosition() / Constants.EncoderResolution) / gear_for_traj_following) * robot_wheel_radius; // m
    rightDistance = ( (rightMaster.getSelectedSensorPosition() / Constants.EncoderResolution) / gear_for_traj_following) * robot_wheel_radius; // m

    // Update wheel speeds
    // Note: getSelectedSensorVelocity returns cts per 100ms, need to divide by 100 to get cts per ms, multiply by 1000 to get cts per seconds
    left_wheel_speed = 10 * ( (leftMaster.getSelectedSensorVelocity() / Constants.EncoderResolution) / gear_for_traj_following) * robot_wheel_radius;
    right_wheel_speed = 10 * ( (rightMaster.getSelectedSensorVelocity() / Constants.EncoderResolution) / gear_for_traj_following) * robot_wheel_radius;

    // Update yaw angle
    yaw_angle = -imu.getAngle();

    // Update pose
    pose = odometry.update(GetAngle(), leftDistance, rightDistance);

    // Smart Dashboard for debugging
    SmartDashboard.putNumber("Left Distance", leftDistance);
    SmartDashboard.putNumber("Left Wheel Speeds", left_wheel_speed);
    SmartDashboard.putNumber("Yaw Angle", yaw_angle);    

  }

  public void move_with_joysticks(Joystick driver_controller) {

    // Get axis values for speed and rotational speed
    double xSpeed = driver_controller.getRawAxis(Constants.left_y_axis);
    double zRotation_rate = -1*driver_controller.getRawAxis(Constants.left_x_axis);

    // Apply rate limiter filters
    accelerationFilter.calculate(xSpeed);
    rotationFilter.calculate(zRotation_rate);

    // Drive Robot with commanded linear velocity and yaw rate commands
    drive.arcadeDrive(xSpeed, zRotation_rate, true);

    SmartDashboard.putNumber("X speed commanded by driver", driver_controller.getRawAxis(Constants.left_x_axis));
    SmartDashboard.putNumber("zRotation Rate Commanded by driver", driver_controller.getRawAxis(Constants.left_y_axis));

  }

  public void move_forward(double speed){

    // Simple call to arcade drive to move along a straight line at a constant speed
    drive.arcadeDrive(speed, 0);

  }

  public void rotateDrivetrain(double zRotation_rate){
    // Simple call to arcade drive to rotate along yaw axis at a constant rate
    drive.arcadeDrive(0, zRotation_rate);

  }

	public void shiftGear() {
    
    // Shift gears logic (if we are high gear, downshift, otherwise upshift)
		if (isHighGear) {
			shiftToLowGear();
		} else {
			shiftToHighGear();
    }
    
	}

  private void shiftToHighGear() {

    // Set solenoid switch to forward
		DriveTrainSwitch.set(Value.kForward);
    isHighGear = true;
    
	}

	private void shiftToLowGear() {

    // Set solenoid switch to reverse
		DriveTrainSwitch.set(Value.kReverse);
    isHighGear = false;
    
	}

  public void align_with_target(double zRotation_rate){

    // Method to align robot with target to be called by PID

    // Clip speed PID can command
    double clip_pid_speed = Constants.visionPID_Clip;

    // Output value for debugging
    SmartDashboard.putNumber("zRotation commanded by controller", zRotation_rate);


    // Implement simple clip logic
    if (zRotation_rate > clip_pid_speed){
      zRotation_rate = clip_pid_speed;
    }

    if (zRotation_rate < -clip_pid_speed){
      zRotation_rate = -clip_pid_speed;
    }

    // Call arcade drive
    drive.arcadeDrive(0, zRotation_rate);

  }
  
}
