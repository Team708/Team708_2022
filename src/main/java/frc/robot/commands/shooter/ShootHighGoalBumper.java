package frc.robot.commands.shooter;

import java.lang.module.ModuleDescriptor.Requires;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.shooter.Shooter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * When using the command linked with a controller button, you have to do
 * new Eject().withTimeout(seconds);
 * 
 * Run with a .andThen(new StopShooterCommand())
 */
public class ShootHighGoalBumper extends CommandBase{

    Shooter m_shooter;

    public ShootHighGoalBumper(Shooter m_shooter){
        this.m_shooter = m_shooter;
        addRequirements(m_shooter);
    }

    @Override
    public void initialize(){
        m_shooter.shooterHoodDown();
        m_shooter.shootAtVelocity(Constants.ShooterConstants.kShooterHighBumper);
    }

    @Override
    public void execute(){
    }

    @Override
    public boolean isFinished(){
        return true;
        // return m_shooter.isShooterAtSpeed();
    }

    @Override
    public void end(boolean interrupted){
        // m_shooter.stopShooter();
    }

}
