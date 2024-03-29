// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.drive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;
import frc.robot.OI;
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

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CompressorConfigType;
import com.revrobotics.REVLibError;

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
  private final SparkMaxPIDController m_leftPidController = m_leftPrimary.getPIDController();
  private final RelativeEncoder       m_leftEncoder       = m_leftPrimary.getEncoder();
  

  // The right-side drive encoder
  private final RelativeEncoder       m_rightEncoder       = m_rightPrimary.getEncoder();
  private final SparkMaxPIDController m_rightPidController = m_rightPrimary.getPIDController();

  // private final DoubleSolenoid shiftSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH,
  //     DriveConstants.kShiftHSolenoidPort, DriveConstants.kShiftLSolenoidPort);
  private final DoubleSolenoid shiftSolenoid;
  // private final Solenoid dropSolenoid = new Solenoid(PneumaticsModuleType.REVPH, DriveConstants.kDriveSolenoidPort);
  Solenoid dropSolenoid;

  // The gyro sensor
  // private final Pigeon m_gyro = Pigeon.getInstance();
  private final PigeonTwo m_gyro = PigeonTwo.getInstance();

  // Odometry class for tracking robot pose
  private final DifferentialDriveOdometry m_odometry;

  private boolean gearHigh       = false;
  private boolean brake          = true;
  private boolean climberEngaged = false;

  // double kP = .00005; //0.00005
  // double kI = 0.000002; //0.000001
  // double kD = 0.00002; //0.0
  // double kIz = 0; 
  // double kFF = 0.04; //2
  // int kMaxOutput = 1; 
  // int kMinOutput = -1;
  // double maxRPM = 5700;
  // double maxVel = 5700; // rpm
  // double maxAcc = 2500;
  // double allowedErr = 0.028;

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem(PneumaticHub hub2) {

    shiftSolenoid = new DoubleSolenoid(hub2.getModuleNumber(), PneumaticsModuleType.REVPH,
        DriveConstants.kShiftHSolenoidPort, DriveConstants.kShiftLSolenoidPort);

    dropSolenoid = new Solenoid(hub2.getModuleNumber(), 
                                    PneumaticsModuleType.REVPH, 
                                        DriveConstants.kDriveSolenoidPort);

    liftWheels();
    shiftGearLow();

    m_leftPrimary.setSmartCurrentLimit(40); //they were 40
    m_leftSecondary.setSmartCurrentLimit(40);
    m_rightPrimary.setSmartCurrentLimit(40);
    m_rightSecondary.setSmartCurrentLimit(40);

    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_rightMotors.setInverted(true); // false - true
    m_leftMotors.setInverted(false); // true

    m_leftPidController.setP(Constants.DriveConstants.kP); 
    m_rightPidController.setP(Constants.DriveConstants.kP); 
    
    m_leftPidController.setI(Constants.DriveConstants.kI);
    m_rightPidController.setI(Constants.DriveConstants.kI);
   
    m_leftPidController.setD(Constants.DriveConstants.kD);
    m_rightPidController.setD(Constants.DriveConstants.kD);
    
    m_leftPidController.setIZone(Constants.DriveConstants.kIZone); 
    m_rightPidController.setIZone(Constants.DriveConstants.kIZone); 
    
    m_leftPidController.setFF(Constants.DriveConstants.kFF); 
    m_rightPidController.setFF(Constants.DriveConstants.kFF); 
    
    m_leftPidController.setOutputRange(-1, 1);  
    m_rightPidController.setOutputRange(-1, 1);  

    m_leftPidController.setSmartMotionMaxVelocity(6000, 0);
    m_rightPidController.setSmartMotionMaxVelocity(6000, 0);

    m_leftPidController.setSmartMotionMinOutputVelocity(0, 0);
    m_rightPidController.setSmartMotionMinOutputVelocity(0, 0);

    m_leftPidController.setSmartMotionMaxAccel(4500,0);
    m_rightPidController.setSmartMotionMaxAccel(4500,0);

    m_leftPidController.setSmartMotionAllowedClosedLoopError(0.0, 0);
    m_rightPidController.setSmartMotionAllowedClosedLoopError(0.0, 0);

    // Sets the distance per pulse for the encoders
    m_leftEncoder.setPositionConversionFactor(DriveConstants.kDriveEncoderDistancePerPulse);
    // m_leftEncoder.setInverted(DriveConstants.kLeftEncoderInverted);

    m_rightEncoder.setPositionConversionFactor(DriveConstants.kDriveEncoderDistancePerPulse);
    // m_rightEncoder.setInverted(DriveConstants.kRightEncoderInverted);

    resetEncoders();
    m_odometry = new DifferentialDriveOdometry(m_gyro.getAngle());
  }

  

  @Override
  public void periodic() {
    // Update the odometry in the periodic block
    m_odometry.update(
        Rotation2d.fromDegrees(getHeading()), m_leftEncoder.getPosition(), m_rightEncoder.getPosition());

    arcadeDrive(OI.getDriverLeftY(), OI.getDriverRightX());
  }

  public void rotateWithEncoders(double counts){
    m_leftPidController.setReference(counts, ControlType.kSmartMotion);
    m_rightPidController.setReference(counts, ControlType.kSmartMotion);
  }

  public void goToPosition(double counts){
    m_leftPidController.setReference(counts, ControlType.kPosition);
    m_rightPidController.setReference(-counts, ControlType.kPosition);
  }

  public void turnToPosition(double counts){
    m_leftPidController.setReference(counts, ControlType.kPosition);
    m_rightPidController.setReference(counts, ControlType.kPosition);
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


  public void setMotorAmps() {
    // m_leftPrimary.setSmartCurrentLimit(80);
    // m_leftSecondary.setSmartCurrentLimit(80);
    // m_rightPrimary.setSmartCurrentLimit(80);
    // m_rightSecondary.setSmartCurrentLimit(80);
    m_leftPrimary.setSmartCurrentLimit(40);
    m_leftSecondary.setSmartCurrentLimit(40);
    m_rightPrimary.setSmartCurrentLimit(40);
    m_rightSecondary.setSmartCurrentLimit(40);
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
    shiftSolenoid.set(Value.kReverse);
  }

  /** Shifts the robot into low gear. */
  public void shiftGearLow() {
    gearHigh = false;
    shiftSolenoid.set(Value.kForward);
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

  public double getRoll() {
    return m_gyro.getRoll().getDegrees();
  }

  public double getAcc() {
    return m_gyro.getRateX();
  }

  
  public void sendToDashboard() {
    // m_gyro.outputToSmartDashboard();
    // SmartDashboard.putBoolean("Gear High",    gearHigh);			    //Drivetrain Gear mode
    
    // SmartDashboard.putNumber("Left Encoder",  m_leftEncoder.getPosition());
    // SmartDashboard.putNumber("Right Encoder", m_rightEncoder.getPosition());

    // SmartDashboard.putNumber("Roll  ",        m_gyro.getRoll().getDegrees());
    // SmartDashboard.putNumber("Rate X",        m_gyro.getRateX());

    // SmartDashboard.putNumber("Driver Left Y", OI.getDriverLeftY());    
    // SmartDashboard.putNumber("Driver Right X", OI.getDriverRightX());    

    // SmartDashboard.putNumber("Pitch",         m_gyro.getPitch().getDegrees());
    // SmartDashboard.putNumber("Rate Y",        m_gyro.getRateY());
    // SmartDashboard.putNumber("Rate Z",        m_gyro.getRateZ());    

    // SmartDashboard.putBoolean("Climb enaged", climberEngaged);		//Drivetrain Climb Engaged
    // SmartDashboard.putBoolean("Brake",        brake);			        // Brake or Coast

    // SmartDashboard.putNumber("DT Motor 11 voltage", m_leftPrimary.getBusVoltage());
    // SmartDashboard.putNumber("DT Motor 12 voltage", m_leftSecondary.getBusVoltage());
    // SmartDashboard.putNumber("DT Motor 14 voltage", m_rightPrimary.getBusVoltage());
    // SmartDashboard.putNumber("DT Motor 15 voltage", m_rightSecondary.getBusVoltage());
    
    // SmartDashboard.putNumber("DT Motor 11 Current", m_leftPrimary.getOutputCurrent());
    // SmartDashboard.putNumber("DT Motor 12 Current", m_leftSecondary.getOutputCurrent());
    // SmartDashboard.putNumber("DT Motor 14 Current", m_rightPrimary.getOutputCurrent());
    // SmartDashboard.putNumber("DT Motor 15 Current", m_rightSecondary.getOutputCurrent());
  }

  
}
