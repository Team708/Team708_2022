package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.subsystems.climber.Climber;
import frc.robot.subsystems.drive.DriveSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ReleasePTO extends CommandBase{
    
    Climber m_climber;
    // double distance;

    public ReleasePTO(Climber m_climber){
        this.m_climber = m_climber;

        addRequirements(m_climber);
    }

    @Override
    public void initialize(){
        // distance = m_climber.getClimberExtended() + 7500; // 49000-41500
        m_climber.releasePTO();
    }

    @Override
    public void execute(){
    }

    @Override
    public boolean isFinished(){
        // return (m_climber.getQuadrature() <= 41500);   //distance);
        return true;
    }

    @Override
    public void end(boolean interrupted){

    }

}
