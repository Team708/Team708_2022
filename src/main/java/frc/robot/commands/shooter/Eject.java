package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.shooter.Shooter;

/**
 * When using the command linked with a controller button, you have to do
 * new Eject().withTimeout(seconds);
 * 
 * Run with a .andThen(new StopShooterCommand())
 */
public class Eject extends CommandBase{
    
    private Shooter m_shooter;

    public Eject(Shooter m_shooter){
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
        return false;
    }

    @Override
    public void end(boolean interrupted){
    }

}
