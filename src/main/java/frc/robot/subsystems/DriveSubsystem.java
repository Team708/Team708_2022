// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;
import frc.robot.Constants.DriveConstants;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxRelativeEncoder.Type;

public class DriveSubsystem extends SubsystemBase {
  // The motors on the left side of the drive.

  private final CANSparkMax m_leftPrimary = new CANSparkMax(DriveConstants.kLeftMotor1Port, MotorType.kBrushless);
  // private final CANSparkMax m_leftSecondary = new CANSparkMax(DriveConstants.kLeftMotor2Port, MotorType.kBrushless);

  private final MotorControllerGroup m_leftMotors = new MotorControllerGroup(
      m_leftPrimary);
      // m_leftSecondary);

  // The motors on the right side of the drive.

  private final CANSparkMax m_rightPrimary = new CANSparkMax(DriveConstants.kRightMotor1Port, MotorType.kBrushless);
  // private final CANSparkMax m_rightSecondary = new CANSparkMax(DriveConstants.kRightMotor2Port, MotorType.kBrushless);

  private final MotorControllerGroup m_rightMotors = new MotorControllerGroup(
      m_rightPrimary);
      // m_rightSecondary);

  // The robot's drive
  private final DifferentialDrive m_drive = new DifferentialDrive(m_leftMotors, m_rightMotors);

  // The left-side drive encoder
  // private final RelativeEncoder m_leftEncoder = m_leftPrimary.getEncoder(Type.kHallSensor, Constants.DriveConstants.kEncoderCPR);
  private final RelativeEncoder m_leftEncoder = m_leftPrimary.getEncoder();

  // The right-side drive encoder
  // private final RelativeEncoder m_rightEncoder = m_rightPrimary.getEncoder(Type.kHallSensor, Constants.DriveConstants.kEncoderCPR);
  private final RelativeEncoder m_rightEncoder = m_rightPrimary.getEncoder();

  private final Solenoid shiftHSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, DriveConstants.kShiftHSolenoidPort);
  private final Solenoid shiftLSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, DriveConstants.kShiftLSolenoidPort);
  private final Solenoid dropSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, DriveConstants.kDriveSolenoidPort);

  // The gyro sensor
  private final Pigeon m_gyro = Pigeon.getInstance();

  // Odometry class for tracking robot pose
  private final DifferentialDriveOdometry m_odometry;

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    // m_leftEncoder.setInverted(true);
    // m_rightEncoder.setInverted(false);

    m_rightMotors.setInverted(false); //false - true
    m_leftMotors.setInverted(true); //true

    // Sets the distance per pulse for the encoders
    m_leftEncoder.setPositionConversionFactor(DriveConstants.kEncoderDistancePerPulse);
    m_rightEncoder.setPositionConversionFactor(DriveConstants.kEncoderDistancePerPulse);

    resetEncoders();
    m_odometry = new DifferentialDriveOdometry(m_gyro.getAngle());
  }

  public double getRawLeftEncoderCount(){
    return -m_leftEncoder.getPosition() * m_leftEncoder.getCountsPerRevolution();
  }

  public double getRawRightEncoderCount(){
    return m_rightEncoder.getPosition() * m_rightEncoder.getCountsPerRevolution();
  }

  @Override
  public void periodic() {
    // Update the odometry in the periodic block
    m_odometry.update(
        m_gyro.getAngle(), m_leftEncoder.getPosition(), m_rightEncoder.getPosition());
  }

  public Pigeon getGyro(){
    return m_gyro;
  }

  /**
   * Returns the currently-estimated pose of the robot.
   *
   * @return The pose.
   */
  public Pose2d getPose() {
    return m_odometry.getPoseMeters();
  }

  public MotorControllerGroup getLeft(){
    return m_leftMotors;
  }

  public MotorControllerGroup getRight(){
    return m_rightMotors;
  }

  /**
    * Returns the current wheel speeds of the robot. (MODIFIED)
    *
    * @return The current wheel speeds.
    */
  public DifferentialDriveWheelSpeeds getWheelSpeedsAuto() {
    // System.out.println("LEFT: " + m_leftEncoder.getVelocity() + "     RIGHT: " + m_rightEncoder.getVelocity());
    return new DifferentialDriveWheelSpeeds(m_leftEncoder.getVelocity(), -m_rightEncoder.getVelocity());
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
   * Sets the Left Encoder to a certain inversion state
   * 
   * @param inverted Boolean value for inverted or not
   */
  public void setLeftEncoderInverted(boolean inverted){
    m_leftEncoder.setInverted(inverted);
  }

  /**
   * Sets the right encoder to a certain inversion state
   * 
   * @param inverted Boolean value for inverted or not
   */
  public void setRightEncoderInverted(boolean inverted){
    m_rightEncoder.setInverted(inverted);
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
   * @return the robot's heading in degrees, from -180 to 180
   */
  public double getHeading() {
    return m_gyro.getAngle().getDegrees();
  }

  /**
   * Returns the turn rate of the robot.
   *
   * @return The turn rate of the robot, in degrees per second
   */
  public double getTurnRate() {
    return -m_gyro.getRate();
  }

  /** Shifts the robot into high gear. */
  public void shiftGearHigh() {
    shiftHSolenoid.set(true);
    shiftLSolenoid.set(false);
  }

  /** Shifts the robot into low gear. */
  public void shiftGearLow() {
    shiftHSolenoid.set(false);
    shiftLSolenoid.set(true);
  }

  /** Drops the omni wheels. */
  public void dropWheels() {
    dropSolenoid.set(true);
  }

  /** Lifts the omni wheels. */
  public void liftWheels() {
    dropSolenoid.set(false);
  }

  public void sendToDashboard(){
    SmartDashboard.putNumber("Wheel Rotation Left", -m_leftEncoder.getPosition());
    SmartDashboard.putNumber("Wheel Rotation Right", m_rightEncoder.getPosition());
    SmartDashboard.putNumber("Left Encoder Raw", getRawLeftEncoderCount());
    SmartDashboard.putNumber("Right Encoder Raw", getRawRightEncoderCount());
    SmartDashboard.putNumber("Raw Angle", m_gyro.getRawAngle());
  }

}
