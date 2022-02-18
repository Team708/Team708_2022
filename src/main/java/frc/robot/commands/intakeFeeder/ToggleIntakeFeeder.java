package frc.robot.commands.intakeFeeder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;

/**
 * Command to toggle the direction of the intake and feeder motors
 */
public class ToggleIntakeFeeder extends CommandBase{
    
    private IntakeFeeder m_if;
    private int previousDirection;

    public ToggleIntakeFeeder(IntakeFeeder m_if){
        this.m_if = m_if;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize(){
        previousDirection = m_if.getDirection();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute(){
        m_if.toggleIntakeFeeder();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished(){
        if (m_if.getDirection() != previousDirection) {
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
