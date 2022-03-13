package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.subsystems.climber.Climber;
import frc.robot.subsystems.drive.DriveSubsystem;

public class ClimberArmUp extends CommandBase{
    
    DriveSubsystem m_driveSubsystem;
    Climber m_climber;
    double distance;

    public ClimberArmUp(DriveSubsystem m_driveSubsystem, Climber m_climber, double distance){
        this.m_driveSubsystem = m_driveSubsystem;
        this.m_climber = m_climber;
        this.distance = distance;

        addRequirements(m_driveSubsystem);
        addRequirements(m_climber);
    }

    @Override
    public void initialize(){
        m_driveSubsystem.setMotorAmps();
        m_climber.activatePTO();
        // m_driveSubsystem.resetEncoders();
        m_climber.resetQuadrature();
    }

    @Override
    public void execute(){
        if (Math.abs(OI.getClimberRightY()) > Constants.ControllerConstants.kClimberDeadBandLeftY)
           m_driveSubsystem.arcadeDrive(OI.getClimberRightY(), 0.0);
        else
           m_driveSubsystem.arcadeDrive(Constants.ClimberConstants.kClimberArmUpSpeed, 0);
    }

    @Override
    public boolean isFinished(){
        // return m_climber.hangSwitch2_engaged() && m_climber.hangSwitch3_engaged();
            // return Math.abs(m_driveSubsystem.getLeftEncoder().getPosition()) > distance
            //     || Math.abs(m_driveSubsystem.getRightEncoder().getPosition()) > distance;
            return Math.abs(m_climber.getQuadrature()) > distance;
    }

    @Override
    public void end(boolean interrupted){

    }

}
