package frc.robot.commands.drivetrain;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive.DriveSubsystem;
import frc.robot.subsystems.Vision.Limelight;

/**
 *
 */
public class TurnToTargetEncoder extends CommandBase {
	
	private double rotationSpeed;
	private double goalDegrees;

    DriveSubsystem m_DriveSubsystem;
    Limelight m_Limelight;

    public TurnToTargetEncoder(double rotationSpeed, DriveSubsystem m_DriveSubsystem, Limelight m_Limelight) {
        addRequirements(m_DriveSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_DriveSubsystem.resetOdometry(new Pose2d());
        this.goalDegrees = m_Limelight.turnToTarget();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
    	if (goalDegrees >= 0) {
    		m_DriveSubsystem.arcadeDrive(0.0, rotationSpeed); //switched from -
    	} else {
    		m_DriveSubsystem.arcadeDrive(0.0, -rotationSpeed);  //switched from +
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
    	return (Math.abs(goalDegrees) * 8 <= Math.max(Math.abs(m_DriveSubsystem.getRightEncoder().getPosition()),
                                                   Math.abs(m_DriveSubsystem.getLeftEncoder().getPosition())));
                                                }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    	m_DriveSubsystem.arcadeDrive(0.0, 0.0);
    }
}