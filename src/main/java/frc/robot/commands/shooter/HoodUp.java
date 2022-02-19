package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.shooter.Shooter;

/**
 * Command to bring shooter hood up
 */
public class HoodUp extends CommandBase {
    
    private Shooter m_shooter;

    public HoodUp(Shooter m_shooter){
        this.m_shooter = m_shooter;
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute(){
        m_shooter.shooterHoodUp();
    }

    @Override
    public boolean isFinished(){
        return true;
    }

    @Override
    public void end(boolean interrupted){
    }
}
