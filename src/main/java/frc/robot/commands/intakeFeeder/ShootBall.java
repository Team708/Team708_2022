package frc.robot.commands.intakeFeeder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;
import frc.robot.subsystems.shooter.Shooter;

/**
 * Command to start the feeder
 */
public class ShootBall extends CommandBase {
    private IntakeFeeder m_intakeFeeder;
    private Shooter      m_shooter;

    // private boolean shooterAtSpeed = false;
    
    public ShootBall(IntakeFeeder m_intakeFeeder, Shooter m_shooter) {
        this.m_intakeFeeder = m_intakeFeeder;
        this.m_shooter      = m_shooter;

        addRequirements(m_intakeFeeder);
        addRequirements(m_shooter);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize(){
        // m_intakeFeeder.feederForward();
        // m_intakeFeeder.intakeForward();
    }
    
    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute(){
        if (m_shooter.isShooterAtSpeed()) {
            m_intakeFeeder.feederShoot();
            m_intakeFeeder.intakeShoot();
            // shooterAtSpeed = true;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted){
        m_intakeFeeder.stopFeeder();
        m_intakeFeeder.stopIntake();
    }
}
