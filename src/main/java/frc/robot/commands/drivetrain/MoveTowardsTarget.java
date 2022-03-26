package frc.robot.commands.drivetrain;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.vision.Limelight;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveTowardsTarget extends CommandBase{
    
    Limelight m_limeLight;
    DriveSubsystem m_driveSubsystem;
    PIDController m_controller;

    double targetPosition;
    boolean done;
    int turn_dir;
    double targetAngle;

    double location = 9.0;

    public MoveTowardsTarget(Limelight m_limeLight, DriveSubsystem m_driveSubsystem){
        this.m_limeLight = m_limeLight;
        this.m_driveSubsystem = m_driveSubsystem;
        addRequirements(m_driveSubsystem);
        addRequirements(m_limeLight);
    }

    @Override
    public void initialize(){
        m_driveSubsystem.resetEncoders();
        done = false;
        targetAngle = m_limeLight.getY();
    }

    @Override
    public void execute(){
        
        if (Math.abs(targetAngle- location) <= 1.0) { 
           done = true;
        }
        else {
            if (targetAngle < location)
                turn_dir = -1;
            else 
                turn_dir = 1;
            
            m_driveSubsystem.arcadeDrive(turn_dir * .45, 0);
            done = false;
        }    
        targetAngle = m_limeLight.getY();
    }

    @Override
    public boolean isFinished(){
        return done;
    }

    @Override
    public void end(boolean interrupted){
    }

}
