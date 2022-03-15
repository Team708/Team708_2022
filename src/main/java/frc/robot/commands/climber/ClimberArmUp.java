package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.subsystems.climber.Climber;
import frc.robot.subsystems.drive.DriveSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ClimberArmUp extends CommandBase{
    
    DriveSubsystem m_driveSubsystem;
    Climber m_climber;
    double dist_off_bar;
    double distance;
    double speed;

    public ClimberArmUp(DriveSubsystem m_driveSubsystem, Climber m_climber, double distance, double speed){
        this.m_driveSubsystem   = m_driveSubsystem;
        this.m_climber          = m_climber;
        this.distance           = distance;
        this.speed              = speed;

        addRequirements(m_driveSubsystem);
        addRequirements(m_climber);
    }

    @Override
    public void initialize(){
        if (distance != 0)
            dist_off_bar = m_climber.getQuadrature() + distance;
        else
            dist_off_bar = m_climber.getClimberExtended() + 2000; // threshold from top

        SmartDashboard.putNumber("Climber - in ArmUp: move to", dist_off_bar);

    }

    @Override
    public void execute(){
           m_driveSubsystem.arcadeDrive(speed, 0);
    }

    @Override
    public boolean isFinished(){
        
            SmartDashboard.putNumber("Climber - in ArmUp: finished", distance);

            return m_climber.getQuadrature() <= distance;
    }

    @Override
    public void end(boolean interrupted){

    }

}
