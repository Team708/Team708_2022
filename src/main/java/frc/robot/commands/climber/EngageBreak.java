package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.subsystems.climber.Climber;
import frc.robot.subsystems.drive.DriveSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class EngageBreak extends CommandBase{
    
    Climber m_climber;

    public EngageBreak(Climber m_climber){
        this.m_climber = m_climber;

        addRequirements(m_climber);
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute(){
        m_climber.engageBrake();
    }

    @Override
    public boolean isFinished(){
        return true;
    }

    @Override
    public void end(boolean interrupted){

    }

}
