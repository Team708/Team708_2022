package frc.robot.commands.groups;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.shooter.HoodUp;
import frc.robot.commands.shooter.ShootHighGoalFar;
import frc.robot.commands.shooter.ShootLowGoalClose;
import frc.robot.commands.vision.TurnTowardsTarget;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.vision.Limelight;

public class Aim extends SequentialCommandGroup{
    
    public Aim(Limelight m_limeLight, DriveSubsystem m_driveSubsystem, Shooter m_shooter){
        addCommands(
            new ParallelCommandGroup(
                // new TurnTowardsTarget(m_limeLight, m_driveSubsystem),
                new HoodUp(m_shooter),
                new ShootHighGoalFar(m_shooter)
            )
            .withTimeout(4.0)
        );
    }

}
