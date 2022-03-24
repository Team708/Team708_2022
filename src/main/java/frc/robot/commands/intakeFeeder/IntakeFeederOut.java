package frc.robot.commands.intakeFeeder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;

/**
 * Command to run the intake and feeder out
 */
public class IntakeFeederOut extends CommandBase {

    private IntakeFeeder m_if;

    public IntakeFeederOut(IntakeFeeder m_if){
        this.m_if = m_if;
    }

    @Override
    public void initialize(){
        m_if.reverseIntake();
        m_if.reverseFeeder();
    }

    @Override
    public void execute(){
    }

    @Override
    public boolean isFinished(){
        return true;
    }

    @Override
    public void end(boolean interrupted){
    }
    
}
