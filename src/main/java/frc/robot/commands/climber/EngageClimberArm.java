package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.climber.Climber;
import frc.robot.subsystems.drive.DriveSubsystem;

import frc.robot.Constants;
import frc.robot.OI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class EngageClimberArm extends CommandBase{
    
    DriveSubsystem m_driveSubsystem;
    Climber m_climber;
    double distance;

    public EngageClimberArm(DriveSubsystem m_driveSubsystem, Climber m_climber, double distance){
        this.m_driveSubsystem = m_driveSubsystem;
        this.m_climber      = m_climber;
        this.distance       = distance;

        addRequirements(m_driveSubsystem);
        addRequirements(m_climber);
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute(){
            m_driveSubsystem.arcadeDrive(1.0, 0.0);
    }

    @Override
    public boolean isFinished(){
        return (!m_climber.hangSwitch2_engaged() && !m_climber.hangSwitch3_engaged()) || (m_climber.getQuadrature() >= distance);
    }

    @Override
    public void end(boolean interrupted){

    }

}
