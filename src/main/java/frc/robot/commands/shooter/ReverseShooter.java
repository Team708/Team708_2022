package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.shooter.Shooter;

public class ReverseShooter extends CommandBase{

    private Shooter m_shooter;

    public ReverseShooter(Shooter m_shooter){
        this.m_shooter = m_shooter;
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute(){
        m_shooter.reverseShooter();
    }

    @Override
    public boolean isFinished(){
        return true; //Test a few things
    }

    @Override
    public void end(boolean interrupted){
        
    }
}
