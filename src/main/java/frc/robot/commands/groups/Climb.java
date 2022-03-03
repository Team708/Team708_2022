package frc.robot.commands.groups;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.climber.ClimberArmDown;
import frc.robot.commands.climber.ClimberArmUp;
import frc.robot.subsystems.climber.Climber;
import frc.robot.subsystems.drive.DriveSubsystem;

public class Climb extends SequentialCommandGroup{

    public Climb(DriveSubsystem m_driveSubsystem, Climber m_climber){
        addCommands(
            //LOOP COMPONENT OF CLIMB
            //Bring arm down
            new ClimberArmDown(m_driveSubsystem, m_climber),
            new WaitCommand(2.0),
            //HIT SWITCHES
            //GO up distance
            new ClimberArmUp(m_driveSubsystem, m_climber),
            new WaitCommand(2.0),
            //arm out
            new RunCommand(m_climber::extendClimbingArm, m_climber),
            new WaitCommand(2.0),
            //release PTO
            new RunCommand(m_climber::releasePTO, m_climber),
            new WaitCommand(2.0)
            //bring arm in
            // new RunCommand(m_climber::retractClimbingArm, m_climber)
            //
        );
    }

}
