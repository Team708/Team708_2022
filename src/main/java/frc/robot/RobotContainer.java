// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.TrajectoryConstants;
import frc.robot.commands.auto.doNothingCommand;
import frc.robot.commands.drivetrain.DriveCurvatureToEncoder;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.vision.Limelight;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

        //Sensors
        private final DigitalInput dIOFeeder = new DigitalInput(4); //Change channel
        private final DigitalInput dIOIntake = new DigitalInput(3); //Change channel

        // The robot's subsystems
        private final DriveSubsystem m_robotDrive = new DriveSubsystem();
        private final Shooter m_shooter = new Shooter();

        private final Limelight m_limelight = new Limelight();

        private final IntakeFeeder m_intakeFeeder = new IntakeFeeder(dIOFeeder, dIOIntake);

        public static final SendableChooser<Command> m_chooser = new SendableChooser<>();

        /**
         * The container for the robot. Contains subsystems, OI devices, and commands.
         */
        public RobotContainer() {
                // Configure the button bindings
                OI.configureButtonBindings(m_robotDrive, m_limelight, m_shooter, m_intakeFeeder);

                // Configure default commands
                // Set the default drive command to split-stick arcade drive
                m_shooter.setDefaultCommand(new RunCommand(() -> m_shooter.stopShooter(), m_shooter));
                m_robotDrive.setDefaultCommand(
                                // A split-stick arcade command, with forward/backward controlled by the left
                                // hand, and turning controlled by the right.
                                new RunCommand(
                                                () -> m_robotDrive.arcadeDrive(
                                                                -OI.getDriverLeftY(), OI.getDriverRightX()),
                                                m_robotDrive));

                m_chooser.addOption("s - curve w/coordinate ", Ramsete(TrajectoryConstants.makeSTrajectory()));
                m_chooser.addOption("drive straight", Ramsete(TrajectoryConstants.driveStraightTrajectory()));
                m_chooser.addOption("curveDrive", new DriveCurvatureToEncoder(.4, .2, false, 1, m_robotDrive));
                m_chooser.setDefaultOption("do nothing", new doNothingCommand());
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
        }
}
