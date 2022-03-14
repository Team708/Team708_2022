package frc.robot.commands.groups;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.climber.ActivatePTO;
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
            new ActivatePTO(m_climber),
            new WaitCommand(.2),

            // pullup to bar
            new EngageClimberArm(m_driveSubsystem, m_climber, 1000), //2000 speed= 1.0  switches or ecoder value
            new WaitCommand(.1),

            // to next bar
            new ClimberArmUp(m_driveSubsystem, m_climber, -7000),   //5500  speed= -.8
            new WaitCommand(.1),                                   
            new ExtendClimbingArm(m_climber),                       //arm out               
            new WaitCommand(.5),
            new ClimberArmUp(m_driveSubsystem, m_climber, -35000),  //38000   speed=  -.8
            new WaitCommand(0.5),
            new ClimberArmDown(m_driveSubsystem, m_climber, 4500),  // speed= .6  --dyn offset
            new WaitCommand(1.0),
            new RetractClimbingArm(m_climber)                       // arm in           
            // new WaitCommand(2.0)

            //lock brake at end of climb
            // new EngageBreak(m_climber)                          // lock the arm from retracting
        );
    }

}
