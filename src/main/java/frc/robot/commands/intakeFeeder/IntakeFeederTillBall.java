package frc.robot.commands.intakeFeeder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;

/**
 * Command to run the feeder and intake in
 */
public class IntakeFeederTillBall extends CommandBase{
    private IntakeFeeder m_if;
    
    public IntakeFeederTillBall(IntakeFeeder m_if){
        this.m_if = m_if;
        
        addRequirements(m_if);
    }

    @Override
    public void initialize(){
        m_if.directionIn();
        m_if.startIntake();
        m_if.reverseFeeder();
    }

    @Override
    public void execute(){
    }

    @Override
    public boolean isFinished(){
        return m_if.oneBallPresent();
    }

    @Override
    public void end(boolean interrupted){
        m_if.stopIntake();
        m_if.stopFeeder();
    }
    
}
