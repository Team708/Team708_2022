package frc.robot.commands.vision;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Limelight;

public class TurnTowardsTarget extends CommandBase{
    
    Limelight m_limeLight;
    DriveSubsystem m_driveSubsystem;
    PIDController m_controller;

    double targetPosition;

    public TurnTowardsTarget(Limelight m_limeLight, DriveSubsystem m_driveSubsystem){
        this.m_limeLight = m_limeLight;
        this.m_driveSubsystem = m_driveSubsystem;
        
        addRequirements(m_limeLight, m_driveSubsystem);
    }

    @Override
    public void initialize(){
        m_driveSubsystem.resetOdometry(new Pose2d());
        m_driveSubsystem.resetEncoders();
        double targetAngle = m_limeLight.turnToTarget();
        targetPosition = (targetAngle * Constants.DriveConstants.kWheelRadiusFromCenter)
                          / (Math.PI * Constants.DriveConstants.kWheelDiameterMeters);
    }

    @Override
    public void execute(){
        m_driveSubsystem.rotateWithEncoders(targetPosition);
    }

    @Override
    public boolean isFinished(){
        return true;
    }

    @Override
    public void end(boolean interrupted){

    }

}
