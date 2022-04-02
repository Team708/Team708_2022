package frc.robot.commands.drivetrain;

import javax.lang.model.util.ElementScanner6;

import com.ctre.phoenix.led.ColorFlowAnimation.Direction;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.vision.Limelight;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnTowardsTarget extends CommandBase{
    
    Limelight m_limeLight;
    DriveSubsystem m_driveSubsystem;
    PIDController m_controller;

    double targetPosition;
    boolean done;
    int turn_dir;

    public TurnTowardsTarget(Limelight m_limeLight, DriveSubsystem m_driveSubsystem){
        this.m_limeLight = m_limeLight;
        this.m_driveSubsystem = m_driveSubsystem;
        addRequirements(m_driveSubsystem);
        addRequirements(m_limeLight);
    }

    @Override
    public void initialize(){
        m_driveSubsystem.resetEncoders();

        double targetAngle = m_limeLight.getX();

        done = false;
        if (targetAngle<0)
          turn_dir = -1;
        else 
          turn_dir = 1;
    }

    @Override
    public void execute(){
        m_driveSubsystem.arcadeDrive(OI.getClimberLeftY(), turn_dir * .40);

        if (Math.abs(m_limeLight.getX()) <= 1.0) 
            done = true;
        else {
            double targetAngle = m_limeLight.getX();
            if (targetAngle<0)
                turn_dir = -1;
            else 
                turn_dir = 1;

            done = false;
        } 
    }

    @Override
    public boolean isFinished(){
        return done;
    }

    @Override
    public void end(boolean interrupted){
    }

}
