package frc.robot.commands.intakeFeeder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;

public class ToggleIntakeSolenoid extends CommandBase {
    private IntakeFeeder m_if;
    private boolean previousIntakeState;

    public ToggleIntakeSolenoid(IntakeFeeder m_if) {
        this.m_if = m_if;
    }

    @Override
    public void initialize(){
        previousIntakeState = m_if.isIntakeDown();
    }

    @Override
    public void execute(){
        m_if.toggleIntakeState();
    }

    @Override
    public boolean isFinished(){
        if (m_if.isIntakeDown() != previousIntakeState) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted){
        System.out.println("Intake Solenoid Toggled");
    }
}