package frc.robot.commands.drivetrain;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.Drive.*;
import frc.robot.subsystems.Vision.*;

/**
 *
 */
public class TurnToTargetDegrees extends PIDCommand {
	
	private double rotationSpeed;
	private double goalDegrees;

    DriveSubsystem m_DriveSubsystem;
    Limelight m_Limelight;

    public TurnToTargetDegrees(double rotationSpeed, DriveSubsystem m_DriveSubsystem, Limelight m_Limelight) {
        super(
        new PIDController(DriveConstants.kDriveP, DriveConstants.kDriveI, DriveConstants.kDriveD),
        // Close loop on heading
        m_DriveSubsystem::getHeading,
        // Set reference to target
        () -> m_Limelight.turnToTarget(),
        // Pipe output to turn robot
        output -> m_DriveSubsystem.arcadeDrive(0, output),
        // Require the drive
        m_DriveSubsystem);
        System.out.println("DEGREES: " + goalDegrees);
        this.m_DriveSubsystem = m_DriveSubsystem;
        this.rotationSpeed = rotationSpeed;
        this.m_Limelight = m_Limelight;
        getController()
        .setTolerance(DriveConstants.kTurnToleranceDeg, DriveConstants.kTurnRateToleranceDegPerS);
    }

    // Called just before this Command runs the first time
    public void initialize() {
        //TODO SEE IF REALISTIC
        System.out.println("INITIALIZED");
    	m_DriveSubsystem.getGyro().reset();
        getController().setSetpoint(m_Limelight.turnToTarget());
        // this.goalDegrees = m_Limelight.turnToTarget();
    }

    // Called repeatedly when this Command is scheduled to run
    public void execute() {
        System.out.println("Execute");
    	if (goalDegrees >= 0) {
    		m_DriveSubsystem.arcadeDrive(0.0, rotationSpeed); //switched from -
    	} else {
    		m_DriveSubsystem.arcadeDrive(0.0, -rotationSpeed);  //switched from +
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return this.getController().atSetpoint();
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        System.out.println("ENDED");
    	m_DriveSubsystem.arcadeDrive(0.0, 0.0);
        m_DriveSubsystem.resetEncoders(); //TODO SEE IF REALISTIC
        m_DriveSubsystem.getGyro().reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end(true);
    }
}