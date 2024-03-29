package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.drivetrain.DriveCurvatureToEncoder;
import frc.robot.commands.drivetrain.DropOmnisCommand;
import frc.robot.commands.drivetrain.SetBrakeMode;
import frc.robot.commands.groups.AimShootTarmac;
import frc.robot.commands.groups.Aim;
import frc.robot.commands.groups.AimShootBumper;
import frc.robot.commands.groups.AimShootBumperHigh;
import frc.robot.commands.groups.AimShootFeeder;
import frc.robot.commands.intakeFeeder.DeployIntake;
import frc.robot.commands.intakeFeeder.IntakeFeederIn;
import frc.robot.commands.intakeFeeder.IntakeFeederTillBall;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;
import frc.robot.subsystems.vision.*;
import frc.robot.subsystems.shooter.*;

public class threeBallFeederAuto extends SequentialCommandGroup{

  public threeBallFeederAuto(DriveSubsystem m_robotDrive, Limelight m_Limelight, Shooter m_shooter, IntakeFeeder m_if) {

    addCommands(
            new twoBallAutoHigh(m_robotDrive, m_Limelight, m_shooter, m_if),
            
            new DriveCurvatureToEncoder(.5, .9, true, .45, m_robotDrive),  //turn towards feeder
            new WaitCommand(.1),

            new DriveCurvatureToEncoder(.7, 0, false, 3.0, m_robotDrive),  // 3.0 red 3.1 blue  //drive to feeder
            new WaitCommand(.2),

            new DriveCurvatureToEncoder(.4, -.3, false, 1.2, m_robotDrive),  //this is for red //turn to goal
            new WaitCommand(.2),  //for red auto

            new ParallelCommandGroup(    //drive curve into feeder to get 2 balls
                  new ParallelRaceGroup(
                    new IntakeFeederIn(m_if),
                    new WaitCommand(4.0)
                  ),

                  new DriveCurvatureToEncoder(.4, 0, false, .15, m_robotDrive)  //this is for blue
                  // new DriveCurvatureToEncoder(.4, -.3, false, 1.2, m_robotDrive),  //this goes in parallel for blue //turn to goal

            ),
            
            new WaitCommand(.2),

            new DriveCurvatureToEncoder(-.45, 0, false, -.75, m_robotDrive),  //lets drive up a bit to goal
            
            new Aim(m_Limelight, m_robotDrive),
            new WaitCommand(1.0),
            new AimShootFeeder(m_Limelight, m_robotDrive, m_shooter, m_if)

        );    
  }  
}