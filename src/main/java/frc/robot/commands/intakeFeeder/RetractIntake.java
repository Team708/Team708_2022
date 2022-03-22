package frc.robot.commands.intakeFeeder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;

/**
 * Command To Retrack the Intake
 */
public class RetractIntake extends CommandBase{
    private IntakeFeeder m_if;
    
    public RetractIntake(IntakeFeeder m_if) {
        this.m_if = m_if;
    }
    // Called just before this Command runs the first time
    @Override
    public void initialize(){
        m_if.intakeUp();  
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute(){
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished(){
        return true;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted){
    }
}
