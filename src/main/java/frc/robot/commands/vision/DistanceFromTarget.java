package frc.robot.commands.vision;

import frc.robot.subsystems.vision.Limelight;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.commands.drivetrain.TurnTowardsTarget;
import frc.robot.subsystems.drive.DriveSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DistanceFromTarget extends CommandBase{
    
    Limelight m_limeLight;
    DriveSubsystem m_driveSubsystem;
    
    double targetMoveDistance;

    public DistanceFromTarget(Limelight m_limeLight, DriveSubsystem m_driveSubsystem) {
        this.m_limeLight = m_limeLight;

        addRequirements(m_limeLight, m_driveSubsystem);
    }

    @Override
    public void initialize(){
        double targetDistance = m_limeLight.getY();
        targetMoveDistance = (targetDistance * .000); //???
    }

    @Override
    public void execute(){
        m_driveSubsystem.goToPosition(targetMoveDistance);
    }

    public boolean isFinished(){
        return true;
    }

    @Override
    public void end(boolean interrupted){
    }
}
