package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.shooter.Shooter;

public class ToggleHood extends CommandBase{
    
    Shooter m_shooter;

    public ToggleHood(Shooter m_shooter){
        this.m_shooter = m_shooter;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        m_shooter.toggleShooterHood();
    }

    @Override
    public boolean isFinished(){
        return true;
    }

    @Override
    public void end(boolean interrupted){

    }

}
