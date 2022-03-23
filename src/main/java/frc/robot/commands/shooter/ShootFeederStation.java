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
public class ShootFeederStation extends CommandBase{

    Shooter m_shooter;

    public ShootFeederStation(Shooter m_shooter){
        this.m_shooter = m_shooter;
        addRequirements(m_shooter);
    }

    @Override
    public void initialize(){
        m_shooter.shooterHoodUp();
        m_shooter.shootAtVelocity(Constants.ShooterConstants.kShooterFeederStation);
    }

    @Override
    public void execute(){
    }

    @Override
    public boolean isFinished(){
        return m_shooter.isShooterAtSpeed();  //true;
    }

    @Override
    public void end(boolean interrupted){
        // m_shooter.stopShooter();
    }

}
