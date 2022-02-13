package frc.robot.commands.intakeFeeder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;
import frc.robot.subsystems.shooter.Shooter;

/**
 * Command to feed ball into shooter once it's up to speed
 */
public class Shoot extends CommandBase {

    private IntakeFeeder m_intakeFeeder;
    private Shooter m_shooter;

    private boolean hasShot;

    public Shoot (IntakeFeeder m_intakeFeeder, Shooter m_shooter) {
        this.m_intakeFeeder = m_intakeFeeder;
        this.m_shooter = m_shooter;

        hasShot = false;

    }

    // Called just before this Command runs the first time
    @Override
    public void initialize(){
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute(){
        // if (m_shooter.isShooterAtSpeed()) {
        //     m_intakeFeeder.startFeeder();
        //     hasShot = true;
        // }
        
        if (m_intakeFeeder.getNumberOfBalls() == 2 && m_shooter.isShooterAtSpeed()) {
            m_intakeFeeder.startFeeder();
            m_intakeFeeder.startIntake();
            hasShot = true;
        } else if (m_intakeFeeder.getNumberOfBalls() == 1 && m_shooter.isShooterAtSpeed()) {
            m_intakeFeeder.startFeeder();
            hasShot = true;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        if (hasShot) {
            return true;
        } else {
            return false;
        }
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted){
    }
    
}
