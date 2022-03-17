package frc.robot.commands.groups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.climber.ActivatePTO;
import frc.robot.commands.climber.ClimberArmDown;
import frc.robot.commands.climber.EngageClimberArm;
import frc.robot.commands.climber.ClimberArmUp;
import frc.robot.commands.climber.ExtendClimbingArm;
import frc.robot.commands.climber.RetractClimbingArm;
import frc.robot.commands.climber.SetClimberExtended;
import frc.robot.commands.drivetrain.ShiftLowCommand;
import frc.robot.subsystems.climber.Climber;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.commands.climber.ClimberArmDownTraversal;
import frc.robot.commands.climber.EngageBreak;
import frc.robot.commands.climber.EngageHighBar;
import frc.robot.commands.climber.ReleasePTO;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class Climb extends SequentialCommandGroup{

    public Climb(DriveSubsystem m_driveSubsystem, Climber m_climber){
        addCommands(
            //LOOP COMPONENT OF CLIMB

            new SetClimberExtended(m_climber),
            new ShiftLowCommand(m_driveSubsystem),
            new ActivatePTO(m_climber),
            
            new WaitCommand(.2),

            // pullup to bar

            new EngageClimberArm(m_driveSubsystem, m_climber, -1000, .8), //-6000 no load 1.0    // switches or encoder value
            new WaitCommand(.1),

            // to next bar

            // test 2 ------
            new ClimberArmUp(m_driveSubsystem, m_climber, -2000, -.3),  //5500, -.6     //delta off current position
            new WaitCommand(.2),        //springs do your thing                                  
            new ExtendClimbingArm(m_climber),                                           //arm out               
            new WaitCommand(1.0),       //waiting for piston

            new ClimberArmUp(m_driveSubsystem, m_climber, -35000, -2),    //0, -.6        //0 uses value for fully extended arm
            // new WaitCommand(.2),        //springs do your thing                                  
            // new ReleasePTO(m_climber),                                                  // really want this to work
            new WaitCommand(2.0),        //timing to extend and catch the bar
            // end test 2 ------


            // test 3  ****
            // new ActivatePTO(m_climber),
            // new WaitCommand(.2),    // make sure arm doesn't fall 
            new ClimberArmDown(m_driveSubsystem, m_climber, 8000, .7), //.6            //delta off current position 
            new WaitCommand(2.0),
            new RetractClimbingArm(m_climber),                                         // arm in           
            new WaitCommand(1.0)   // let it chill
            // end test 3 ****


            //lock brake at end of climb
            // new EngageBreak(m_climber)                                               // lock the arm from retracting
        );
    }

}
