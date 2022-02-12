package frc.robot.commands.intakeFeeder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;

public class StartFeeder extends CommandBase {
    private IntakeFeeder m_intakeFeeder;
    
    public StartFeeder (IntakeFeeder m_intakeFeeder) {
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
        if(m_intakeFeeder.getFeederSpeed() != 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted){
    }
}
