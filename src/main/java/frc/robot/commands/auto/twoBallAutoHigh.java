package frc.robot.commands.auto;

import frc.robot.commands.intakeFeeder.IntakeFeederTillBall;
import frc.robot.commands.groups.AimShootBumper;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.DriveCurvatureToEncoder;
import frc.robot.commands.groups.AimShootTarmac;
import frc.robot.commands.intakeFeeder.DeployIntake;
import frc.robot.commands.intakeFeeder.IntakeFeederIn;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;
import frc.robot.commands.groups.Aim;
import frc.robot.subsystems.vision.*;
import frc.robot.subsystems.shooter.*;
import frc.robot.commands.drivetrain.DropOmnisCommand;
import frc.robot.commands.drivetrain.SetBrakeMode;


public class twoBallAutoHigh extends SequentialCommandGroup{

  public twoBallAutoHigh(DriveSubsystem m_robotDrive, Limelight m_Limelight, Shooter m_shooter, IntakeFeeder m_if) {


//    shoots two balls into high goal from Tarmac -- close quadrant


    addCommands(
            new SetBrakeMode(m_robotDrive, true),
            new DeployIntake(m_if),
            
            new ParallelCommandGroup(
              new IntakeFeederIn(m_if),
              new DriveCurvatureToEncoder(.5, 0.0, false, 1.5, m_robotDrive),   //.3
              new WaitCommand(1.0) //delay could come out
            ),

            // new DriveCurvatureToEncoder(-.5, -1.0, true, -.4, m_robotDrive),
            new AimShootTarmac(m_Limelight, m_robotDrive, m_shooter, m_if)

            // new DropOmnisCommand(m_robotDrive)
        );    

  }  
}