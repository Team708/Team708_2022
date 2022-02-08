package frc.robot.commands.intakeFeeder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;

public class ReverseIntakeFeeder extends CommandBase{
    
    private IntakeFeeder m_if;
    private int previousDirection;

    public ReverseIntakeFeeder(IntakeFeeder m_if){
        this.m_if = m_if;
    }

    @Override
    public void initialize(){
        previousDirection = m_if.getDirection();
    }

    @Override
    public void execute(){
        m_if.toggleIntakeFeeder();
    }

    @Override
    public boolean isFinished(){
        if (m_if.getDirection() != previousDirection) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted){
        System.out.println("Intake and Feeder toggled");
    }

}
