package frc.robot.commands.drivetrain;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.drive.*;
import frc.robot.subsystems.vision.*;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
/**
 *
 */
public class TurnToTargetSetPoint extends CommandBase {
	
	private double rotationSpeed;
	private double goalDegrees;
    private double moveToSetPoint;

    DriveSubsystem m_DriveSubsystem;
    Limelight m_Limelight;

    private CANSparkMax           m_motor;
    private SparkMaxPIDController m_pidController;
    private RelativeEncoder       m_encoder;

    public TurnToTargetSetPoint(double rotationSpeed, DriveSubsystem m_DriveSubsystem, Limelight m_Limelight) {
        addRequirements(m_DriveSubsystem);
        this.m_DriveSubsystem = m_DriveSubsystem;
        this.m_Limelight = m_Limelight;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        this.goalDegrees = m_Limelight.turnToTarget();
        
        m_DriveSubsystem.resetOdometry(new Pose2d());
        m_DriveSubsystem.resetEncoders();

        // m_motor         = m_DriveSubsystem.;  
        // m_pidController = m_DriveSubsystem.getRightEncoder();

        m_encoder = m_motor.getEncoder();

        // set PID coefficients
        m_pidController.setP(5e-5);
        m_pidController.setI(1e-6);
        m_pidController.setD(0);
        m_pidController.setIZone(0);
        m_pidController.setFF(0.000156);
        m_pidController.setOutputRange(-1, 1);  

        m_pidController.setSmartMotionMaxVelocity(5700, 0);
        m_pidController.setSmartMotionMinOutputVelocity(2000, 0);
        m_pidController.setSmartMotionMaxAccel(1500,0);
        m_pidController.setSmartMotionAllowedClosedLoopError(0, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {

        moveToSetPoint = (goalDegrees * (Constants.DriveConstants.kCountsPerDegree));
        m_pidController.setReference(moveToSetPoint, CANSparkMax.ControlType.kSmartMotion);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        SmartDashboard.putNumber("SetPoint", moveToSetPoint);
    	return (true);
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    	m_DriveSubsystem.arcadeDrive(0.0, 0.0);
    }
}