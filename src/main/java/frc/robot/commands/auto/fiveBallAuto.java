package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.drivetrain.DriveCurvatureToEncoder;
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

public class fiveBallAuto extends SequentialCommandGroup{

  public fiveBallAuto(DriveSubsystem m_robotDrive, Limelight m_Limelight, Shooter m_shooter, IntakeFeeder m_if) {

    addCommands(
         // new WaitCommand(2), 
            new DeployIntake(m_if),
            
            new ParallelCommandGroup(
              new IntakeFeederIn(m_if),
              new DriveCurvatureToEncoder(.3, .3, false, 1, m_robotDrive)
            ),

            new DriveCurvatureToEncoder(-.4, -.15, false, -2.3, m_robotDrive),
            new AimShootBumper(m_Limelight, m_robotDrive, m_shooter, m_if),

            new ParallelCommandGroup(
              new IntakeFeederTillBall(m_if),
              new DriveCurvatureToEncoder(.3, -.2, false, 1.6, m_robotDrive)
            ),
            
            new DriveCurvatureToEncoder(.5, 1.0, true, .07, m_robotDrive),
            new AimShootTarmac(m_Limelight, m_robotDrive, m_shooter, m_if),

            new DriveCurvatureToEncoder(.5, 1.0, true, .8, m_robotDrive),
            new DriveCurvatureToEncoder(.8, -.2, false, 9, m_robotDrive)
        );    
  }  
}