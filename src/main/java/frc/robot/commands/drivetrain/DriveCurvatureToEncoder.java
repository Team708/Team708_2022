package frc.robot.commands.drivetrain;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive.DriveSubsystem;

/**
 *
 */
public class DriveCurvatureToEncoder extends CommandBase {

	private double xSpeed;
    private double zRotation;
    private boolean isQuickTurn;
	private double targetDistance;

    private final DriveSubsystem m_drive;

    public DriveCurvatureToEncoder(double xSpeed, double zRotation, boolean isQuickTurn, double distance, DriveSubsystem m_drive) {
        

        this. m_drive = m_drive;
        this.xSpeed = xSpeed;
        this.zRotation = zRotation;
        this.isQuickTurn = isQuickTurn;
        this.targetDistance = distance;

        addRequirements(m_drive);
    }

    // Called just before this Command runs the first time
    public void initialize() {
    	m_drive.resetOdometry(new Pose2d());

    }

    // Called repeatedly when this Command is scheduled to run
    public void execute() {
    	m_drive.curvatureDrive(xSpeed, zRotation, isQuickTurn);
    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
    	if (targetDistance >= 0) {
    		return (m_drive.getAverageEncoderDistance() >= targetDistance);
    	} else {
    		return (m_drive.getAverageEncoderDistance() <= targetDistance);
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
        m_drive.stop();
        m_drive.resetOdometry(new Pose2d());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
