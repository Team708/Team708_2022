package frc.robot.commands.groups;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.drivetrain.TurnTowardsTarget;
import frc.robot.commands.intakeFeeder.FeederReverse;
import frc.robot.commands.intakeFeeder.IntakeFeederOut;
import frc.robot.commands.intakeFeeder.ShootFeeder;
import frc.robot.commands.intakeFeeder.ShootIntake;
import frc.robot.commands.intakeFeeder.StopFeeder;
import frc.robot.commands.intakeFeeder.StopIntake;
import frc.robot.commands.shooter.HoodDown;
import frc.robot.commands.intakeFeeder.ShootBall;
import frc.robot.commands.intakeFeeder.ShootBallClose;
import frc.robot.commands.shooter.HoodUp;
import frc.robot.commands.shooter.ShootBumperShot;
import frc.robot.commands.shooter.StopShooter;
import frc.robot.commands.shooter.ShootHighGoalFar;
import frc.robot.commands.shooter.ShootFeederStation;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.vision.Limelight;

public class BumperEjectHigh extends SequentialCommandGroup{
    
    public BumperEjectHigh(Limelight m_limeLight, DriveSubsystem m_driveSubsystem, Shooter m_shooter, IntakeFeeder m_if){
        m_if.directionIn();
        addCommands(
            new ParallelCommandGroup(
                //new FeederReverse(m_if).withTimeout(0.4)
                //                        .andThen(new StopFeeder(m_if)),
                new ShootBumperShot(m_shooter)
            ),
            
            new ShootFeeder(m_if).withTimeout(0.5),
            new StopShooter(m_shooter)
        );
    }

}
