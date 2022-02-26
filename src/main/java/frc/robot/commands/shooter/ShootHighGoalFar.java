package frc.robot.commands.shooter;

import java.lang.module.ModuleDescriptor.Requires;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.shooter.Shooter;

/**
 * When using the command linked with a controller button, you have to do
 * new Eject().withTimeout(seconds);
 * 
 * Run with a .andThen(new StopShooterCommand())
 */
public class ShootHighGoalFar extends CommandBase{

    Shooter m_shooter;

    public ShootHighGoalFar(Shooter m_shooter){
        this.m_shooter = m_shooter;

        addRequirements(m_shooter);
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute(){
        m_shooter.shooterHoodUp();
        if (m_shooter.settargetSpeed == 0) m_shooter.settargetSpeed = Constants.ShooterConstants.kShooterHighFar ;
        m_shooter.shootAtVelocity(m_shooter.settargetSpeed);
        // m_shooter.shootAtVelocity(Constants.ShooterConstants.kShooterHighFar);
    }

    @Override
    public boolean isFinished(){
        return true;
    }

    @Override
    public void end(boolean interrupted){
    }

}
