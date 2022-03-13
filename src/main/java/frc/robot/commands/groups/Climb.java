package frc.robot.commands.groups;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.climber.ClimberArmDown;
import frc.robot.commands.climber.ClimberArmDownTraversal;
import frc.robot.commands.climber.ClimberArmUp;
import frc.robot.commands.climber.EngageBreak;
import frc.robot.commands.climber.EngageClimberArm;
import frc.robot.commands.climber.EngageHighBar;
import frc.robot.commands.climber.ExtendClimbingArm;
import frc.robot.commands.climber.ReleasePTO;
import frc.robot.commands.climber.RetractClimbingArm;
import frc.robot.commands.drivetrain.ShiftLowCommand;
import frc.robot.subsystems.climber.Climber;
import frc.robot.subsystems.drive.DriveSubsystem;

public class Climb extends SequentialCommandGroup{

    public Climb(DriveSubsystem m_driveSubsystem, Climber m_climber){
        addCommands(
            //LOOP COMPONENT OF CLIMB

            new ShiftLowCommand(m_driveSubsystem),

            // to med bar

            //Bring arm down
            //pull arm down till HIT SWITCHES or encoders
            new EngageClimberArm(m_driveSubsystem, m_climber),  //hanging on med bar
            new WaitCommand(.1),

            //to High Bar
            
            //GO up distance, to release middle claw from bar
            new ClimberArmUp(m_driveSubsystem, m_climber, 15000),      //lift mid hook off bar
            new WaitCommand(.5),
            //arm out to reach for higher bar
            new ExtendClimbingArm(m_climber),                   //open arm out to reach for high bar
            new WaitCommand(1.0),

            new ClimberArmUp(m_driveSubsystem, m_climber, 27000),      //  force arm up if its stuck
            //release PTO to raisearm to higher bar
            new ReleasePTO(m_climber),                          //  raise arm up to high bar
            new WaitCommand(1.0),
            //bring arm in to catch bar
            new RetractClimbingArm(m_climber),                  //  engage high bar with mid hook
            new WaitCommand(1.0)
            // //pull arm off lower bar
            // new ClimberArmDown(m_driveSubsystem, m_climber), //  hanging on high bar -- do we still need this??
            // new WaitCommand(0.1)

            // // to traversal

            // //pull arm down till HIT SWITCHES or some encoder number 
            // //    (whatever it was before minus the armdown amount )
            // // new EngageHighBar(m_driveSubsystem, m_climber),  //  hanging on high bar
            // new EngageClimberArm(m_driveSubsystem, m_climber),  //  engage high bara with outter hooks
            // new WaitCommand(.1),

            // //GO up distance, to release middle claw from bar
            // new ClimberArmUp(m_driveSubsystem, m_climber, 0.7),      //  extend mid hook off high bar
            // new WaitCommand(.1),

            // //arm out to reach higher bar
            // new ExtendClimbingArm(m_climber),                   // bring arm out 
            // new WaitCommand(1.0),
            // //release PTO to raisearm to higher bar
            // new ClimberArmUp(m_driveSubsystem, m_climber, 0.7),      //  force arm up if its stuck
            // new ReleasePTO(m_climber),                          //  raise arm to high bar
            // new WaitCommand(1.0),

            // //bring arm in to catch bar
            // new RetractClimbingArm(m_climber),                  //  engage mid rm with t bar
            // new WaitCommand(1.0),

            // //pull arm off lower bar
            // new ClimberArmDownTraversal(m_driveSubsystem, m_climber),  //hanging on t bar
            // // new WaitCommand(.5),

            // //lock brake at end of climb
            // new EngageBreak(m_climber)                          // lock the arm from retracting
        );
    }

}
