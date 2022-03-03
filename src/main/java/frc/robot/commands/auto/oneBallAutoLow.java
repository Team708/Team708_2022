package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.groups.AimShootTarmac;
import frc.robot.commands.intakeFeeder.DeployIntake;
import frc.robot.commands.intakeFeeder.IntakeFeederIn;
import frc.robot.commands.intakeFeeder.IntakeFeederTillBall;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.DriveCurvatureToEncoder;
import frc.robot.commands.drivetrain.DropOmnisCommand;
import frc.robot.commands.groups.AimShootBumper;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;
import frc.robot.subsystems.vision.*;
import frc.robot.subsystems.shooter.*;

public class oneBallAutoLow extends SequentialCommandGroup{

  public oneBallAutoLow(DriveSubsystem m_robotDrive, Limelight m_Limelight, Shooter m_shooter, IntakeFeeder m_if) {

    //drives over the line, back to goal and shoots low
    
    addCommands(
            new DriveCurvatureToEncoder(.4,  0, false, 1.17, m_robotDrive),
            new DriveCurvatureToEncoder(-.4, 0, false, -2.47, m_robotDrive),
            new AimShootBumper(m_Limelight, m_robotDrive, m_shooter, m_if),
            
            new DropOmnisCommand(m_robotDrive)
        );    
    
  }  
}