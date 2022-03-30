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

            new ClimberArmUp(m_driveSubsystem, m_climber, -2000, -.3, true),  //5500, -.6   //noswing=true,  delta off current position
            new WaitCommand(.2),        //springs do your thing                                  
            new ExtendClimbingArm(m_climber),                                           //arm out               
            new WaitCommand(1.0),       //waiting for piston

            new ClimberArmUp(m_driveSubsystem, m_climber, -30000, -2, false),  //-33000, -2  //noswing=false,  uses value for fully extended arm
            new WaitCommand(.2),        //springs do your thing                                  
            

            new ActivatePTO(m_climber),
            new WaitCommand(.2),    // make sure arm doesn't fall 
            new ClimberArmDown(m_driveSubsystem, m_climber, 8500, .8), //8000 .8 <=works           //delta off current position 
            new WaitCommand(.2),  //.5
            new RetractClimbingArm(m_climber)                                         // arm in           
        );
    }

}
