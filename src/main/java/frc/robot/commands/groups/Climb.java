package frc.robot.commands.groups;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.climber.ClimberArmDown;
import frc.robot.commands.climber.ClimberArmUp;
import frc.robot.commands.climber.EngageClimberArm;
import frc.robot.subsystems.climber.Climber;
import frc.robot.subsystems.drive.DriveSubsystem;

public class Climb extends SequentialCommandGroup{

    public Climb(DriveSubsystem m_driveSubsystem, Climber m_climber){
        addCommands(
            //LOOP COMPONENT OF CLIMB

            // to med bar

            //Bring arm down
            //pull arm down till HIT SWITCHES or encoders
            new EngageClimberArm(m_driveSubsystem, m_climber),  //hanging on med bar
            new WaitCommand(1.0),

            //to High Bar
            
            //GO up distance, to release middle claw from bar
            new ClimberArmUp(m_driveSubsystem, m_climber),
            new WaitCommand(1.0),
            //arm out to reach for higher bar
            new RunCommand(m_climber::extendClimbingArm, m_climber),
            new WaitCommand(1.0),
            //release PTO to raisearm to higher bar
            new RunCommand(m_climber::releasePTO, m_climber),
            new WaitCommand(2.0),
            //bring arm in to catch bar
            new RunCommand(m_climber::retractClimbingArm, m_climber),
            //pull arm off lower bar
            new ClimberArmDown(m_driveSubsystem, m_climber),  //hanging on high bar
            new WaitCommand(2.0),


            // to traversal

            // //pull arm down till HIT SWITCHES or encoders
            // new EngageClimberArm(m_driveSubsystem, m_climber),  //hanging on high bar
            // new WaitCommand(1.0),
            // //GO up distance, to release middle claw from bar
            // new ClimberArmUp(m_driveSubsystem, m_climber),
            // new WaitCommand(1.0),
            // //arm out to reach higher bar
            // new RunCommand(m_climber::extendClimbingArm, m_climber),
            // new WaitCommand(1.0),
            // //release PTO to raisearm to higher bar
            // new RunCommand(m_climber::releasePTO, m_climber),
            // new WaitCommand(2.0),
            // //bring arm in to catch bar
            // new RunCommand(m_climber::retractClimbingArm, m_climber),
            // //pull arm off lower bar
            // new ClimberArmDown(m_driveSubsystem, m_climber),  //hanging on high bar
            // new WaitCommand(2.0),

            //lock brake at end of climb
            new RunCommand(m_climber::engageBrake, m_climber)
        );
    }

}
