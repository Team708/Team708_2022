package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.drivetrain.DriveCurvatureToEncoder;
import frc.robot.commands.drivetrain.DropOmnisCommand;
import frc.robot.commands.drivetrain.SetBrakeMode;
import frc.robot.commands.groups.AimShootTarmac;
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

            new SetBrakeMode(m_robotDrive, true),

            new DeployIntake(m_if),
            
            new ParallelCommandGroup(
              new IntakeFeederIn(m_if),
              new DriveCurvatureToEncoder(.5, .3, false, 2.0, m_robotDrive)
            ),

            new DriveCurvatureToEncoder(-.5, -1.0, true, -.4, m_robotDrive),  //.4  two ball is still .4
            new AimShootTarmac(m_Limelight, m_robotDrive, m_shooter, m_if),

            new DriveCurvatureToEncoder(.5, 1.0, true, .20, m_robotDrive),  //.25  
            new WaitCommand(.1),

            new DriveCurvatureToEncoder(.7, 0, false, 2.5, m_robotDrive),
            new WaitCommand(.1),


            new ParallelCommandGroup(
            new IntakeFeederTillBall(m_if),
            new DriveCurvatureToEncoder(.5, -.3, false, 1.9, m_robotDrive)
            ),
            
            new WaitCommand(.2),

            new DriveCurvatureToEncoder(.5, .7, true, .1, m_robotDrive),
            new WaitCommand(.2),

            new DriveCurvatureToEncoder(-.5, 0, true, -1.4, m_robotDrive),
            new AimShootFeeder(m_Limelight, m_robotDrive, m_shooter, m_if)

            // new DropOmnisCommand(m_robotDrive)
        );    
  }  
}