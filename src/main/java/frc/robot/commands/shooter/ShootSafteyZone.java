package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.shooter.Shooter;


public class ShootSafteyZone extends CommandBase{

    Shooter m_shooter;

    public ShootSafteyZone(Shooter m_shooter){
        this.m_shooter = m_shooter;
        addRequirements(m_shooter);
    }

    @Override
    public void initialize(){
        m_shooter.shooterHoodUp();
        m_shooter.shootAtVelocity(Constants.ShooterConstants.kShooterStafetyZone);
    }

    @Override
    public void execute(){
    }

    @Override
    public boolean isFinished(){
        return true; // m_shooter.isShooterAtSpeed();
    }

    @Override
    public void end(boolean interrupted){
        // m_shooter.stopShooter();
    }

}
