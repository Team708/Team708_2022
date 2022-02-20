package frc.robot.commands.intakeFeeder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;

public class ToggleIntakeSolenoid extends CommandBase {
    private IntakeFeeder m_if;
    private boolean previousIntakeState;

    /**
     * Command to toggle the state of the intake solenoid - brings intake up and down
     */
    public ToggleIntakeSolenoid(IntakeFeeder m_if) {
        this.m_if = m_if;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize(){
        previousIntakeState = m_if.isIntakeDown();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute(){
        m_if.toggleIntakeState();
        if(!m_if.isIntakeDown()){
            m_if.startIntake();
        }else{
            m_if.stopIntake();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished(){
        if (m_if.isIntakeDown() != previousIntakeState) {
            return true;
        } else {
            return false;
        }
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted){
        // if(!m_if.isIntakeDown()){
        //     m_if.stopIntake();
        // }
    }
}