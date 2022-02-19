package frc.robot.commands.intakeFeeder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;

/**
 * Command To Lower the Intake
 */
public class DeployIntake extends CommandBase{
    private IntakeFeeder m_if;
    
    public DeployIntake(IntakeFeeder m_if) {
        this.m_if = m_if;
    }
    // Called just before this Command runs the first time
    @Override
    public void initialize(){
        
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute(){
      m_if.intakeDown();  
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
