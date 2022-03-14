package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.subsystems.climber.Climber;
import frc.robot.subsystems.drive.DriveSubsystem;

public class ClimberArmDown extends CommandBase{
    
    DriveSubsystem m_driveSubsystem;
    Climber m_climber;
    double dist_off_bar;
    double distance;

    public ClimberArmDown(DriveSubsystem m_driveSubsystem, Climber m_climber, double distance){
        this.m_driveSubsystem = m_driveSubsystem;
        this.m_climber = m_climber;
        this.distance = distance;

        addRequirements(m_driveSubsystem);
        addRequirements(m_climber);
    }

    @Override
    public void initialize(){
        dist_off_bar = m_climber.getQuadrature() + distance;
    }

    @Override
    public void execute(){
         m_driveSubsystem.arcadeDrive(.6, 0);
    }

    @Override
    public boolean isFinished(){
        return (m_climber.getQuadrature() >= dist_off_bar);
    }

    @Override
    public void end(boolean interrupted){
    }

}
