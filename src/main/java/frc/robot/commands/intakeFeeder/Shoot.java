package frc.robot.commands.intakeFeeder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;
import frc.robot.subsystems.shooter.Shooter;

public class Shoot extends CommandBase {

    private IntakeFeeder m_intakeFeeder;
    private Shooter m_shooter;

    private boolean hasShot;

    public Shoot (IntakeFeeder m_intakeFeeder, Shooter m_shooter) {
        this.m_intakeFeeder = m_intakeFeeder;
        this.m_shooter = m_shooter;

        hasShot = false;

    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute(){
        if (m_shooter.isShooterAtSpeed()) {
            m_intakeFeeder.startFeeder();
            hasShot = true;
        }
    }

    @Override
    public boolean isFinished() {
        if (hasShot) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted){
    }
    
}
