package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.climber.Climber;
import frc.robot.subsystems.drive.DriveSubsystem;

import frc.robot.Constants;
import frc.robot.OI;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class EngageClimberArm extends CommandBase{
    
    DriveSubsystem m_driveSubsystem;
    Climber m_climber;
    double distance;
    double speed;
    boolean go;

    public EngageClimberArm(DriveSubsystem m_driveSubsystem, Climber m_climber, double distance, double speed){
        this.m_driveSubsystem = m_driveSubsystem;
        this.m_climber      = m_climber;
        this.distance       = distance;
        this.speed          = speed;

        addRequirements(m_driveSubsystem);
        addRequirements(m_climber);
    }

    @Override
    public void initialize(){
        go = false;
    }

    @Override
    public void execute(){
        if (!go)
            if (Math.abs(m_driveSubsystem.getAcc()) >= 55 ) 
                go=false;
            else if ((m_driveSubsystem.getRoll() <= -35) || (m_driveSubsystem.getRoll() >= 18))
                go=false;
            else
                go=true;
        
        SmartDashboard.putBoolean("Climb", go);

        if (go)
            m_driveSubsystem.arcadeDrive(speed, 0.0);

    }

    @Override
    public boolean isFinished(){
        return (!m_climber.hangSwitch2_engaged() && !m_climber.hangSwitch3_engaged()) || (m_climber.getQuadrature() >= distance);
    }

    @Override
    public void end(boolean interrupted){
    }

}
