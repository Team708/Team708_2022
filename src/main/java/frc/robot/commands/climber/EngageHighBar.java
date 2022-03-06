package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.subsystems.climber.Climber;
import frc.robot.subsystems.drive.DriveSubsystem;

public class EngageHighBar extends CommandBase{
    
    DriveSubsystem m_driveSubsystem;
    Climber m_climber;

    public EngageHighBar(DriveSubsystem m_driveSubsystem, Climber m_climber){
        this.m_driveSubsystem = m_driveSubsystem;
        this.m_climber = m_climber;

        addRequirements(m_driveSubsystem);
        addRequirements(m_climber);
    }

    @Override
    public void initialize(){
        m_driveSubsystem.setMotorAmps();
        m_climber.activatePTO();
        m_climber.resetQuadrature();
    }

    @Override
    public void execute(){
        if (Math.abs(OI.getClimberLeftY()) > Constants.ControllerConstants.kClimberDeadBandLeftY)
            m_driveSubsystem.arcadeDrive(OI.getClimberLeftY(), 0.0);
        else
            m_driveSubsystem.arcadeDrive(Constants.ClimberConstants.kClimberArmDownSpeed, 0.0);

    }

    @Override
    public boolean isFinished(){
        // if (!m_climber.hangSwitch2_engaged() && !m_climber.hangSwitch3_engaged()){
        //     return true;
        // }else{
        //     return Math.abs(m_climber.getQuadrature()) > Constants.ClimberConstants.kClimberQuadDown;
        // }
        return !m_climber.hangSwitch2_engaged() && !m_climber.hangSwitch3_engaged();
    }

    @Override
    public void end(boolean interrupted){

    }

}
