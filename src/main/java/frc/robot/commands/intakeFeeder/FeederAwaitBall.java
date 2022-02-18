package frc.robot.commands.intakeFeeder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;

/**
 * Command to assume that the feeder is constantly running unless it is contacting a ball
 */
public class FeederAwaitBall extends CommandBase{
    
    private IntakeFeeder m_intakeFeeder;

    public FeederAwaitBall(IntakeFeeder m_intakeFeeder){
        this.m_intakeFeeder = m_intakeFeeder;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize(){
        
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute(){
        m_intakeFeeder.startFeeder();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return m_intakeFeeder.feederContactingBall();
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted){
        m_intakeFeeder.stopFeeder();
    }

}
