package frc.robot.commands.drivetrain;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Drive.DriveSubsystem;
import frc.robot.subsystems.Vision.Limelight;

/**
 *
 */
public class TurnToTargetDegrees extends PIDCommand {

    DriveSubsystem m_DriveSubsystem;
    Limelight m_Limelight;

    public TurnToTargetDegrees(DriveSubsystem m_DriveSubsystem, Limelight m_Limelight) {
        super(
        new PIDController(0.2,0,0),
        m_DriveSubsystem::getHeading,
        m_DriveSubsystem.getHeading() + m_Limelight.turnToTarget(),
        output -> m_DriveSubsystem.arcadeDrive(0, 2 * output),
        m_DriveSubsystem);

        getController().enableContinuousInput(-180, 180);
        getController().setTolerance(1.5, 10);
        
        
    }


    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return getController().atSetpoint();
    }
}