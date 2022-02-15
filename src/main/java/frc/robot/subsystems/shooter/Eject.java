package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Might have to just make this an executable, adding a timeout is not possible I think
 * except with commandgroups.
 */
public class Eject extends CommandBase{
    
    private Shooter m_shooter;

    public Eject(Shooter m_shooter){
        this.m_shooter = m_shooter;
    }

    @Override
    public void initialize(){
        this.withTimeout(2); //CHECK IF THIS WORKS
    }

    @Override
    public void execute(){
        m_shooter.eject();
    }

    @Override
    public boolean isFinished(){
        return false;
    }

    @Override
    public void end(boolean interrupted){
        m_shooter.stopShooter();
    }

}
