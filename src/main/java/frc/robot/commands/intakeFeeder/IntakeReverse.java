package frc.robot.commands.intakeFeeder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;

/**
 * Command to stop the feeder
 */
public class IntakeReverse extends CommandBase {
    private IntakeFeeder m_intakeFeeder;

    public IntakeReverse (IntakeFeeder m_intakeFeeder) {
        this.m_intakeFeeder = m_intakeFeeder;

        addRequirements(m_intakeFeeder);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize(){
        m_intakeFeeder.reverseIntake();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute(){
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted){
        m_intakeFeeder.stopIntake();

    }
}
