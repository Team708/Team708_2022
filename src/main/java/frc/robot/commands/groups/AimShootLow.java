package frc.robot.commands.groups;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.intakeFeeder.FeederReverse;
import frc.robot.commands.intakeFeeder.IntakeFeederOut;
import frc.robot.commands.intakeFeeder.ShootFeeder;
import frc.robot.commands.intakeFeeder.ShootIntake;
import frc.robot.commands.intakeFeeder.StopFeeder;
import frc.robot.commands.intakeFeeder.StopIntake;
import frc.robot.commands.shooter.HoodDown;
import frc.robot.commands.shooter.HoodUp;
import frc.robot.commands.shooter.ShootHighGoalFar;
import frc.robot.commands.shooter.ShootLowGoalClose;
import frc.robot.commands.vision.TurnTowardsTarget;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.vision.Limelight;

public class AimShootLow extends SequentialCommandGroup{
    
    public AimShootLow(Limelight m_limeLight, 
                       DriveSubsystem m_driveSubsystem, 
                       Shooter m_shooter, 
                       IntakeFeeder m_if){
        m_if.directionIn();
        addCommands(
            new ParallelCommandGroup(
                // new TurnTowardsTarget(m_limeLight, m_driveSubsystem),
                new HoodDown(m_shooter),
                new ShootLowGoalClose(m_shooter),
                new FeederReverse(m_if)
                    .withTimeout(0.4)
                    .andThen(new StopFeeder(m_if)),
                new SequentialCommandGroup(
                    new WaitCommand(1.5),
                    new ParallelCommandGroup(
                        new ShootIntake(m_if),
                        new ShootFeeder(m_if)
                    )
                )
            )
            .withTimeout(4.0)
            // .andThen(new StopFeeder(m_if), new StopIntake(m_if))
        );
    }

}
