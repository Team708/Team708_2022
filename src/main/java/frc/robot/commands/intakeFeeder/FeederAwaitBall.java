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

    @Override
    public void initialize(){
        
    }

    @Override
    public void execute(){
        m_intakeFeeder.startFeeder();
    }

    @Override
    public boolean isFinished() {
        return m_intakeFeeder.feederContactingBall();
    }

    @Override
    public void end(boolean interrupted){
        m_intakeFeeder.stopFeeder();
    }

}
