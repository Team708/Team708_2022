package frc.robot.commands.vision;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.vision.Limelight;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnTowardsTarget extends CommandBase{
    
    Limelight m_limeLight;
    DriveSubsystem m_driveSubsystem;
    PIDController m_controller;

    double targetPosition;

    public TurnTowardsTarget(Limelight m_limeLight, DriveSubsystem m_driveSubsystem){
        this.m_limeLight = m_limeLight;
        this.m_driveSubsystem = m_driveSubsystem;
        addRequirements(/*m_limeLight, */m_driveSubsystem);
    }

    @Override
    public void initialize(){
        m_driveSubsystem.resetOdometry(new Pose2d());
        m_driveSubsystem.resetEncoders();
        double targetAngle = m_limeLight.getX(); //Doesn't get a value, even when there is a value. . .
        targetPosition = (targetAngle * .015 * 10);  //What's .015 ??  and what's 10 ???
        System.out.println("Target Angle: " + targetAngle);
        System.out.println("Target Position: " + targetPosition);

    }

    @Override
    public void execute(){
        m_driveSubsystem.rotateWithEncoders(-targetPosition);
    }

    @Override
    public boolean isFinished(){
        return false;
    }

    @Override
    public void end(boolean interrupted){
    	m_driveSubsystem.arcadeDrive(0.0, 0.0);
    }

}
