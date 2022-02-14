package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.shooter.Shooter;

public class Shoot extends CommandBase{
    
    private Shooter m_shooter;

    private boolean shotFired = false;

    private boolean hoodUp;

    public Shoot(Shooter m_shooter, boolean hoodUp){
        this.m_shooter = m_shooter;
        this.hoodUp = hoodUp;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        //Turn to target with shooter
        if(m_shooter.getHoodUp() == hoodUp){
            m_shooter.shoot();
            if(m_shooter.isShooterAtSpeed()){
                //Set feeder motor on
                shotFired = true;
            }
        }else{
            m_shooter.toggleShooterHood();
        }
    }

    @Override
    public boolean isFinished(){
        return shotFired;
    }

    @Override
    public void end(boolean interrupted){
        //stop feeder motor
        m_shooter.stopShooter();
    }

}
