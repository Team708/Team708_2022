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
import frc.robot.subsystems.climber.Climber;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.vision.Limelight;

public class Climb extends SequentialCommandGroup{
    
    public Climb(DriveSubsystem m_driveSubsystem, Climber m_Climber){
        
        
    }

}
