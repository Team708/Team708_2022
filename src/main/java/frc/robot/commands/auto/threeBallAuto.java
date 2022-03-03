package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.drivetrain.DriveCurvatureToEncoder;
import frc.robot.commands.drivetrain.DropOmnisCommand;
import frc.robot.commands.groups.AimShootTarmac;
import frc.robot.commands.groups.AimShootBumper;
import frc.robot.commands.intakeFeeder.DeployIntake;
import frc.robot.commands.intakeFeeder.IntakeFeederIn;
import frc.robot.commands.intakeFeeder.IntakeFeederTillBall;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;
import frc.robot.subsystems.vision.*;
import frc.robot.subsystems.shooter.*;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class threeBallAuto extends SequentialCommandGroup{

  public threeBallAuto(DriveSubsystem m_robotDrive, Limelight m_Limelight, Shooter m_shooter, IntakeFeeder m_if) {

    addCommands(



            new DeployIntake(m_if),
            
            new ParallelCommandGroup(
              new IntakeFeederIn(m_if),
              new DriveCurvatureToEncoder(.4, .4, false, 1.4, m_robotDrive)
            ),

            new DriveCurvatureToEncoder(-.4, -.3, false, -2.6, m_robotDrive),
            new AimShootBumper(m_Limelight, m_robotDrive, m_shooter, m_if),

            new ParallelCommandGroup(
              new IntakeFeederTillBall(m_if),
              new DriveCurvatureToEncoder(.4, -.40, false, 1.80, m_robotDrive)
            ),
            
            new DriveCurvatureToEncoder(.4, .9, true, .4, m_robotDrive),
            new AimShootTarmac(m_Limelight, m_robotDrive, m_shooter, m_if),

            new DropOmnisCommand(m_robotDrive)
        );    
  }  
}