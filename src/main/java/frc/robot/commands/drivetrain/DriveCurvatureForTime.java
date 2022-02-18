package frc.robot.commands.drivetrain;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drive.DriveSubsystem;


public class DriveCurvatureForTime extends CommandBase {

	private double runTime;
	private double xSpeed;
    private double zRotation;
    private boolean isQuickTurn;
	
    private final DriveSubsystem m_drive;

    public DriveCurvatureForTime(double xSpeed, double zRotation, boolean isQuickTurn, double runTime, DriveSubsystem m_drive) {
        // Use requires() here to declare subsystem dependencies
        
        this.xSpeed    = xSpeed;
        this.zRotation = zRotation;
        this.isQuickTurn = isQuickTurn;
        this.runTime     = runTime;
        this.m_drive   = m_drive;

        addRequirements(m_drive);
    }

    // Called just before this Command runs the first time
    public void initialize() {
    	m_drive.resetOdometry(new Pose2d());
    }

    // Called repeatedly when this Command is scheduled to run
    public void execute() {
    	m_drive.curvatureDrive(xSpeed, zRotation, isQuickTurn);
//    	Curvature drive method for differential drive platform.
//    	The rotation argument controls the curvature of the robot's path rather than its rate of heading 
//    	change. This makes the robot more controllable at high speeds. 
//    	Also handles the robot's quick turn functionality - "quick turn" overrides 
//    	constant-curvature turning for turn-in-place maneuvers.
    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        return (timeSinceInitialized() > this.runTime);
        
    }
private double timeSinceInitialized() {
        return 0;
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
