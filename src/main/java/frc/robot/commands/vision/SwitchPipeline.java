package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.vision.Limelight;

public class SwitchPipeline extends CommandBase{
    
    Limelight m_limeLight;

    public SwitchPipeline(Limelight m_limeLight){
        this.m_limeLight = m_limeLight;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        m_limeLight.incrementPipeline();
    }

    @Override
    public boolean isFinished(){
        return true;
    }

    @Override
    public void end(boolean interrupted){

    }

}
