package frc.robot.commands.groups;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.intakeFeeder.ShootFeeder;
import frc.robot.commands.intakeFeeder.ShootIntake;
import frc.robot.commands.intakeFeeder.StartFeeder;
import frc.robot.commands.intakeFeeder.StartIntake;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;

public class AutoShoot extends SequentialCommandGroup{

    public AutoShoot(IntakeFeeder m_intakeFeeder){
        addCommands(
            new ParallelCommandGroup(
                new ShootFeeder(m_intakeFeeder),
                new ShootIntake(m_intakeFeeder)
            )
        );
    }
    
}
