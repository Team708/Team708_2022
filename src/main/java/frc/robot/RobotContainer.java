// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.commands.auto.doNothingCommand;
import frc.robot.commands.auto.oneBallAutoHigh;
import frc.robot.commands.auto.twoBallAutoHigh;
import frc.robot.commands.auto.twoBallAutoFar;
import frc.robot.commands.auto.threeBallFeederAuto;
import frc.robot.commands.drivetrain.DriveCurvatureToEncoder;
import frc.robot.commands.groups.Aim;
import frc.robot.subsystems.climber.Climber;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.vision.CANdleSystem;
import frc.robot.subsystems.vision.Limelight;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
import com.ctre.phoenix.led.CANdle;

import frc.robot.commands.auto.oneBallAutoLow;
import frc.robot.commands.auto.twoBallAutoFarBumper;
import frc.robot.commands.auto.threeBallAuto;
import frc.robot.commands.auto.twoBallAutoLow;
import frc.robot.commands.auto.fiveBallAuto;
import frc.robot.commands.drivetrain.TurnToTargetSetPoint;

import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.MjpegServer;
import edu.wpi.first.cscore.UsbCamera;
*/

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

        //Pneumatics
        private final PneumaticHub hub2 = new PneumaticHub(2);
        private final PneumaticHub hub3 = new PneumaticHub(3);
        // private final Compressor compressor = new Compressor(hub2.getModuleNumber(), PneumaticsModuleType.REVPH);

        //Sensors - ON MXP
        private final DigitalInput dIOFeeder = new DigitalInput(11); 
        private final DigitalInput dIOIntake = new DigitalInput(10);

        // The robot's subsystems
        private final DriveSubsystem m_robotDrive = new DriveSubsystem(hub2);
        private final Shooter m_shooter = new Shooter(hub3);
        private final Climber m_climber = new Climber(hub2, hub3);

        private final CANdleSystem m_candleSystem = new CANdleSystem();
        private final Limelight m_limelight = new Limelight(m_candleSystem, m_robotDrive);

        private final IntakeFeeder m_intakeFeeder = new IntakeFeeder(dIOIntake, dIOFeeder, hub3);

        public static final SendableChooser<Command> m_chooser = new SendableChooser<>();


        /**
         * The container for the robot. Contains subsystems, OI devices, and commands.
         */
        public RobotContainer() {
                // Configure the button bindings
                OI.configureButtonBindings(m_robotDrive, m_limelight, m_shooter, m_intakeFeeder, m_climber);

                // Configure default commands
                // Set the default drive command to split-stick arcade drive
                // m_shooter.setDefaultCommand(new RunCommand(() -> m_shooter.stopShooter(), m_shooter));
                // m_robotDrive.setDefaultCommand(
                //         // A split-stick arcade command, with forward/backward controlled by the left
                //         // hand, and turning controlled by the right.
                //         new RunCommand(() -> m_robotDrive.arcadeDrive(
                //                                         -OI.getDriverLeftY(), OI.getDriverRightX()),m_robotDrive));

                m_chooser.setDefaultOption("do nothing", new doNothingCommand());
                m_chooser.addOption("Drive Past Tarmac", new DriveCurvatureToEncoder(.6, 0, false, 3.0, m_robotDrive));
                
                m_chooser.addOption("One Ball Auto High",       new oneBallAutoHigh(m_robotDrive, m_limelight, m_shooter, m_intakeFeeder) );
                // m_chooser.addOption("One Ball Auto Low ",       new oneBallAutoLow(m_robotDrive, m_limelight, m_shooter, m_intakeFeeder) );
                
                m_chooser.addOption("Two   Ball Close High",    new twoBallAutoHigh(m_robotDrive, m_limelight, m_shooter, m_intakeFeeder) );
                // m_chooser.addOption("Two   Ball Close Low ",    new twoBallAutoLow(m_robotDrive, m_limelight, m_shooter, m_intakeFeeder) );
                // m_chooser.addOption("Three Ball Close ",        new threeBallAuto(m_robotDrive, m_limelight, m_shooter, m_intakeFeeder) );
                m_chooser.addOption("Three Ball Feeder ",        new threeBallFeederAuto(m_robotDrive, m_limelight, m_shooter, m_intakeFeeder) );
                // m_chooser.addOption("Five  Ball Close ",        new fiveBallAuto(m_robotDrive, m_limelight, m_shooter, m_intakeFeeder) );
                
                m_chooser.addOption("Two   Ball Far High  ",    new twoBallAutoFar(m_robotDrive, m_limelight, m_shooter, m_intakeFeeder) );
                // m_chooser.addOption("Two   Ball Far Low   ",    new twoBallAutoFarBumper(m_robotDrive, m_limelight, m_shooter, m_intakeFeeder) );
                
                m_chooser.addOption("Turn To Target", new  Aim(m_limelight, m_robotDrive));
                // m_chooser.addOption("curveDrive", new DriveCurvatureToEncoder(.4, .2, false, 1, m_robotDrive));
                // m_chooser.addOption("s - curve w/coordinate ", Ramsete(TrajectoryConstants.makeSTrajectory()));

                // UsbCamera usbCamera = new UsbCamera("USB Camera", 0);
                // MjpegServer mjpegServer = new MjpegServer("Serve_USB Camera", 1181);
                // mjpegServer.setSource(usbCamera);
                // mjpegServer.setResolution(800, 448);
                // mjpegServer.setFPS(20);

                SmartDashboard.putData("Auto Chooser", m_chooser);
        }

        public Command getAutonomousCommand() {
                return m_chooser.getSelected();
        }

        public Command Ramsete(Trajectory exampleTrajectory) {
                RamseteCommand ramseteCommand = new RamseteCommand(
                                exampleTrajectory,
                                m_robotDrive::getPose,
                                new RamseteController(AutoConstants.kRamseteB, AutoConstants.kRamseteZeta),
                                new SimpleMotorFeedforward(0.2, 2, 0.2),
                                DriveConstants.kDriveKinematics,
                                m_robotDrive::getWheelSpeeds,
                                new PIDController(DriveConstants.kPDriveVel, 0, 0),
                                new PIDController(DriveConstants.kPDriveVel, 0, 0),
                                // RamseteCommand passes volts to the callback
                                m_robotDrive::tankDriveVolts,
                                m_robotDrive);

                // Reset odometry to the starting pose of the trajectory.
                m_robotDrive.resetOdometry(exampleTrajectory.getInitialPose());

                // Run path following command, then stop at the end.
                return ramseteCommand.andThen(() -> m_robotDrive.tankDriveVolts(0, 0));
        }

        public DriveSubsystem getDriveSubsystem() {
                return m_robotDrive;
        }

        public void sendToDashboard() {
                m_robotDrive.sendToDashboard();
                m_shooter.sendToDashboard();
                m_climber.sendToDashboard();
                m_intakeFeeder.sendToDashboard();
                // m_limelight.sendToDashboard();
                // m_candleSystem.sendToDashboard();

        }
}
