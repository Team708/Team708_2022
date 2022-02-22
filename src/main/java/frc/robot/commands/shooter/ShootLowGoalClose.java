package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.shooter.Shooter;

/**
 * When using the command linked with a controller button, you have to do
 * new Eject().withTimeout(seconds);
 * 
 * Run with a .andThen(new StopShooterCommand())
 */
public class ShootLowGoalClose extends CommandBase{

    Shooter m_shooter;

    public ShootLowGoalClose(Shooter m_shooter){
        this.m_shooter = m_shooter;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        if (m_shooter.settargetSpeed == 0) m_shooter.settargetSpeed = Constants.ShooterConstants.kShooterLowClose ;
        m_shooter.shootAtVelocity(m_shooter.settargetSpeed);
    }

    @Override
    public boolean isFinished(){
        return false;
    }

    @Override
    public void end(boolean interrupted){
    }

}
