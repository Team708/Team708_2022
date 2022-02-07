package frc.robot.commands.Drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Limelight;

/**
 *
 */
public class TurnToTargetEncoder extends CommandBase {
	
	private double rotationSpeed;
	private double goalDegrees;

    DriveSubsystem m_DriveSubsystem;
    Limelight m_Limelight;

    public TurnToTargetEncoder(double rotationSpeed, DriveSubsystem m_DriveSubsystem, Limelight m_Limelight) {
        this.m_DriveSubsystem = m_DriveSubsystem;
        this.rotationSpeed = rotationSpeed;
        this.m_Limelight = m_Limelight;
        // System.out.println(goalDegrees);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        //TODO SEE IF REALISTIC
    	// m_DriveSubsystem.getGyro().reset();
        // m_DriveSubsystem.resetEncoders();
        this.goalDegrees = m_Limelight.turnToTarget();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        // System.out.println(goalDegrees);
    	if (goalDegrees >= 0) {
    		m_DriveSubsystem.arcadeDrive(0.0, rotationSpeed); //switched from -
    	} else {
    		m_DriveSubsystem.arcadeDrive(0.0, -rotationSpeed);  //switched from +
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        System.out.println("Goal degrees: " + goalDegrees * 12);
        System.out.println("Compared: " + Math.max(Math.abs(m_DriveSubsystem.getRawLeftEncoderCount()),
        Math.abs(m_DriveSubsystem.getRawRightEncoderCount())));
    	if (Math.abs(goalDegrees) * 8 <= Math.max(Math.abs(m_DriveSubsystem.getRawLeftEncoderCount()),
                                                   Math.abs(m_DriveSubsystem.getRawRightEncoderCount()))) { //changed > to <
    		// return (m_DriveSubsystem.getGyro().getRawAngle() >= goalDegrees);
            return true;
    	}
        //  else {
    	// 	return (m_DriveSubsystem.getGyro().getRawAngle() <= goalDegrees);
    	// }
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        System.out.println("END: " + m_DriveSubsystem.getGyro().getRawAngle());
        System.out.println("L: " + m_DriveSubsystem.getRawLeftEncoderCount() + "   R: " + m_DriveSubsystem.getRawRightEncoderCount());
    	m_DriveSubsystem.arcadeDrive(0.0, 0.0);
        // m_DriveSubsystem.resetEncoders(); //TODO SEE IF REALISTIC
        // m_DriveSubsystem.getGyro().reset();
    }
}