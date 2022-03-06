package frc.robot.commands.groups;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.drivetrain.TurnTowardsTarget;
import frc.robot.commands.intakeFeeder.FeederReverse;
import frc.robot.commands.intakeFeeder.IntakeFeederOut;
import frc.robot.commands.intakeFeeder.ShootBall;
import frc.robot.commands.intakeFeeder.ShootFeeder;
import frc.robot.commands.intakeFeeder.ShootIntake;
import frc.robot.commands.intakeFeeder.StopFeeder;
import frc.robot.commands.intakeFeeder.StopIntake;
import frc.robot.commands.shooter.HoodUp;
import frc.robot.commands.shooter.ShootHighGoalFar;
import frc.robot.commands.shooter.ShootFeederStation;
import frc.robot.commands.shooter.StopShooter;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.vision.Limelight;

public class AimShootFeeder extends SequentialCommandGroup{
    
    public AimShootFeeder(Limelight m_limeLight, DriveSubsystem m_driveSubsystem, Shooter m_shooter, IntakeFeeder m_if){
        m_if.directionIn();
        addCommands(
            new ParallelCommandGroup(
                new TurnTowardsTarget(m_limeLight, m_driveSubsystem).withTimeout(1),
                new FeederReverse(m_if) .withTimeout(0.4)
                                        .andThen(new StopFeeder(m_if)),
                new ShootFeederStation(m_shooter)
                ),

            new ParallelCommandGroup(
                    // new WaitCommand(1),
                    new ShootBall(m_if, m_shooter).withTimeout(1.0)
                ),
            new StopShooter(m_shooter)
        );
    }

}
