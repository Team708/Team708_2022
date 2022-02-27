package frc.robot.commands.intakeFeeder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;

/**
 * Command to start the intake
 */
public class ShootIntake extends CommandBase {
    private IntakeFeeder m_intakeFeeder;

    public ShootIntake (IntakeFeeder m_intakeFeeder) {
        this.m_intakeFeeder = m_intakeFeeder;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize(){
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute(){
        // if(m_intakeFeeder.isIntakeDown()){

//        how do you shoot if you call this when the intake is in?
            m_intakeFeeder.intakeShoot();
        // }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted){
        // m_intakeFeeder.stopFeeder();
        m_intakeFeeder.stopIntake();
    }
}
