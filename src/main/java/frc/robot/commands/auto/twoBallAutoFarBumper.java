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

public class twoBallAutoFarBumper extends SequentialCommandGroup{

  public twoBallAutoFarBumper(DriveSubsystem m_robotDrive, Limelight m_Limelight, Shooter m_shooter, IntakeFeeder m_if) {


    // shoots 2 balls from far quadrant  -- low goal bumper shot
    addCommands(
            new DeployIntake(m_if),
            
            new ParallelCommandGroup(
              new IntakeFeederIn(m_if),
              new DriveCurvatureToEncoder(.3, -.5, false, 1.0, m_robotDrive)
            ),

            new DriveCurvatureToEncoder(-.3, .25, false, -2.0, m_robotDrive),
            new AimShootBumper(m_Limelight, m_robotDrive, m_shooter, m_if)
        );    
  }  
}