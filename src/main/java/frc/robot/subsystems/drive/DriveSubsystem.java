// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.drive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants.DriveConstants;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.SparkMaxPIDController;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class DriveSubsystem extends SubsystemBase {
  // The motors on the left side of the drive.

  private final CANSparkMax m_leftPrimary = new CANSparkMax(DriveConstants.kLeftMotor1Port, MotorType.kBrushless);
  private final CANSparkMax m_leftSecondary = new CANSparkMax(DriveConstants.kLeftMotor2Port, MotorType.kBrushless);

  private final MotorControllerGroup m_leftMotors = new MotorControllerGroup(m_leftPrimary, m_leftSecondary);

  // The motors on the right side of the drive.

  private final CANSparkMax m_rightPrimary = new CANSparkMax(DriveConstants.kRightMotor1Port, MotorType.kBrushless);
  private final CANSparkMax m_rightSecondary = new CANSparkMax(DriveConstants.kRightMotor2Port, MotorType.kBrushless);

  private final MotorControllerGroup m_rightMotors = new MotorControllerGroup(m_rightPrimary, m_rightSecondary);

  // The robot's drive
  private final DifferentialDrive m_drive = new DifferentialDrive(m_leftMotors, m_rightMotors);

  // The left-side drive encoder
  private final SparkMaxPIDController m_leftPidController = m_leftPrimary.getPIDController();;
  private final RelativeEncoder       m_leftEncoder       = m_leftPrimary.getEncoder();
  

  // The right-side drive encoder
  private final RelativeEncoder       m_rightEncoder       = m_rightPrimary.getEncoder();
  private final SparkMaxPIDController m_rightPidController = m_rightPrimary.getPIDController();;

  private final DoubleSolenoid shiftSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,
      DriveConstants.kShiftHSolenoidPort, DriveConstants.kShiftLSolenoidPort);
  private final Solenoid dropSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, DriveConstants.kDriveSolenoidPort);

  // The gyro sensor
  private final Pigeon m_gyro = Pigeon.getInstance();

  // Odometry class for tracking robot pose
  private final DifferentialDriveOdometry m_odometry;

  private boolean gearHigh       = false;
  private boolean brake          = true;
  private boolean climberEngaged = false;

  private SparkMaxPIDController leftController, rightController;

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_rightMotors.setInverted(false); // false - true
    m_leftMotors.setInverted(true); // true

    leftController = m_leftPrimary.getPIDController();
    rightController = m_rightPrimary.getPIDController();

    // Sets the distance per pulse for the encoders
    m_leftEncoder.setPositionConversionFactor(DriveConstants.kEncoderDistancePerPulse);
    // m_leftEncoder.setInverted(DriveConstants.kLeftEncoderInverted);

    m_rightEncoder.setPositionConversionFactor(DriveConstants.kEncoderDistancePerPulse);
    // m_rightEncoder.setInverted(DriveConstants.kRightEncoderInverted);

    resetEncoders();
    m_odometry = new DifferentialDriveOdometry(m_gyro.getAngle());


  }

  @Override
  public void periodic() {
    // Update the odometry in the periodic block
    m_odometry.update(
        Rotation2d.fromDegrees(getHeading()), m_leftEncoder.getPosition(), m_rightEncoder.getPosition());
  }

  public void rotateWithEncoders(double counts){
    leftController.setReference(counts, ControlType.kPosition);
    rightController.setReference(-counts, ControlType.kPosition);
  }

  /**
   * Returns the currently-estimated pose of the robot.
   *
   * @return The pose.
   */
  public Pose2d getPose() {
    return m_odometry.getPoseMeters();
  }

  /**
   * Returns the current wheel speeds of the robot.
   *
   * @return The current wheel speeds.
   */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(m_leftEncoder.getVelocity(), m_rightEncoder.getVelocity());
  }

  /**
   * Resets the odometry to the specified pose.
   *
   * @param pose The pose to which to set the odometry.
   */
  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    m_gyro.setAngle(0.0);
    m_odometry.resetPosition(pose, m_gyro.getAngle());
  }

  /**
   * Drives the robot using arcade controls.
   *
   * @param fwd the commanded forward movement
   * @param rot the commanded rotation
   */
  public void arcadeDrive(double fwd, double rot) {
    m_drive.arcadeDrive(fwd, rot);
  }

  public void curvatureDrive(double xSpeed, double zRotation, boolean isQuickTurn) {
		m_drive.curvatureDrive(xSpeed, zRotation, isQuickTurn);
	}

  public void stop() {
    m_drive.arcadeDrive(0,0);
  }

  /**
   * Controls the left and right sides of the drive directly with voltages.
   *
   * @param leftVolts  the commanded left output
   * @param rightVolts the commanded right output
   */
  public void tankDriveVolts(double leftVolts, double rightVolts) {
    m_leftMotors.setVoltage(leftVolts);
    m_rightMotors.setVoltage(rightVolts);
    m_drive.feed();
  }

  /** Resets the drive encoders to currently read a position of 0. */
  public void resetEncoders() {
    m_leftEncoder.setPosition(0);
    m_rightEncoder.setPosition(0);
  }

  /**
   * Gets the average distance of the two encoders.
   *
   * @return the average of the two encoder readings
   */
  public double getAverageEncoderDistance() {
    return (m_leftEncoder.getPosition() + m_rightEncoder.getPosition()) / 2.0;
  }

  /**
   * Gets the left drive encoder.
   *
   * @return the left drive encoder
   */
  public RelativeEncoder getLeftEncoder() {
    return m_leftEncoder;
  }

  /**
   * Gets the right drive encoder.
   *
   * @return the right drive encoder
   */
  public RelativeEncoder getRightEncoder() {
    return m_rightEncoder;
  }

  /**
   * Sets the max output of the drive. Useful for scaling the drive to drive more
   * slowly.
   *
   * @param maxOutput the maximum output to which the drive will be constrained
   */
  public void setMaxOutput(double maxOutput) {
    m_drive.setMaxOutput(maxOutput);
  }

  /** Zeroes the heading of the robot. */
  public void zeroHeading() {
    m_gyro.reset();
  }

  /**
   * Returns the heading of the robot.
   *
   * @return the robot's heading in degrees, from -180 to 180 (0 - 360)
   */
  public double getHeading() {
    return (Math.floorMod((long) m_gyro.getAngle().getDegrees(), (long) 360));
  }

  public double getAngle() {
    return m_gyro.getAngle().getDegrees();
  }

  /**
   * Returns the turn rate of the robot.
   *
   * @return The turn rate of the robot, in degrees per second
   */
  public double getTurnRateX() {
    return -m_gyro.getRateX();
  }


  /** Shifts the robot into high gear. */
  public void shiftGearHigh() {
    gearHigh = true;
    shiftSolenoid.set(Value.kForward);
  }

  /** Shifts the robot into low gear. */
  public void shiftGearLow() {
    gearHigh = false;
    shiftSolenoid.set(Value.kReverse);
  }

  public void setBrakeMode(boolean setBrake) {
		brake = setBrake;
		if (brake) {
			m_leftPrimary.setIdleMode(IdleMode.kBrake);
			m_leftSecondary.setIdleMode(IdleMode.kBrake);
			m_rightPrimary.setIdleMode(IdleMode.kBrake);
			m_rightSecondary.setIdleMode(IdleMode.kBrake);
		} 
		else {
			m_leftPrimary.setIdleMode(IdleMode.kCoast);
			m_leftSecondary.setIdleMode(IdleMode.kCoast);
			m_rightPrimary.setIdleMode(IdleMode.kCoast);
			m_rightSecondary.setIdleMode(IdleMode.kCoast);
		}
	}


  /** Drops the omni wheels. */
  public void dropWheels() {
    dropSolenoid.set(true);
  }

  /** Lifts the omni wheels. */
  public void liftWheels() {
    dropSolenoid.set(false);
  }

  public void sendToDashboard() {
    m_gyro.outputToSmartDashboard();
    SmartDashboard.putNumber("Left Encoder", m_leftEncoder.getPosition());
    SmartDashboard.putNumber("Right Encoder", m_rightEncoder.getPosition());

    SmartDashboard.putNumber("Wheel Rotation Left", -m_leftEncoder.getPosition());
    SmartDashboard.putNumber("Wheel Rotation Right", m_rightEncoder.getPosition());
    SmartDashboard.putNumber("Raw Angle",            m_gyro.getRawAngle());
  
    SmartDashboard.putNumber("Roll",          m_gyro.getRoll().getDegrees());
    SmartDashboard.putNumber("Pitch",         m_gyro.getPitch().getDegrees());

    SmartDashboard.putBoolean("Gear High",    gearHigh);			//Drivetrain Gear mode
    SmartDashboard.putBoolean("Climb enaged", climberEngaged);		//Drivetrain Climb Engaged
    SmartDashboard.putBoolean("Brake",        brake);			// Brake or Coast
  }


}
