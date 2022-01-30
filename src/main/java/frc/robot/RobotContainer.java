// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.commands.auto.doNothingCommand;
import frc.robot.subsystems.DriveSubsystem;
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
        // The robot's subsystems
        private final DriveSubsystem m_robotDrive = new DriveSubsystem();
        private final TrajectoryManager m_trajectoryManager = new TrajectoryManager();

        public static final SendableChooser<Command> m_chooser = new SendableChooser<>();

        /**
         * The container for the robot. Contains subsystems, OI devices, and commands.
         */
        public RobotContainer() {
                // Configure the button bindings
                OI.configureButtonBindings(m_robotDrive);

                // Configure default commands
                // Set the default drive command to split-stick arcade drive
                m_robotDrive.setDefaultCommand(
                                // A split-stick arcade command, with forward/backward controlled by the left
                                // hand, and turning controlled by the right.
                                new RunCommand(
                                                () -> m_robotDrive.arcadeDrive(
                                                                -OI.getDriverLeftY(), OI.getDriverRightX()),
                                                m_robotDrive));

                m_chooser.addOption("s - curve", Ramsete(m_trajectoryManager.makeSTrajectory()));
                m_chooser.addOption("drive straight", Ramsete(m_trajectoryManager.driveStraightTrajectory()));
                m_chooser.setDefaultOption("do nothing", new doNothingCommand());
                SmartDashboard.putData("Auto Chooser", m_chooser);
        }

        /**
         * Use this to pass the autonomous command to the main {@link Robot} class.
         *
         * @return the command to run in autonomous
         */
        public Command getAutonomousCommand() {
                System.out.println(m_chooser.toString());
                return m_chooser.getSelected();
        }

        //ISSUE IN HERE?
        public Command Ramsete(Trajectory exampleTrajectory) {
                RamseteCommand ramseteCommand = new RamseteCommand(
                                exampleTrajectory,
                                m_robotDrive::getPose,
                                new RamseteController(AutoConstants.kRamseteB, AutoConstants.kRamseteZeta),
                                new SimpleMotorFeedforward(
                                                DriveConstants.ksVolts,
                                                DriveConstants.kvVoltSecondsPerMeter,
                                                DriveConstants.kaVoltSecondsSquaredPerMeter),
                                DriveConstants.kDriveKinematics,
                                m_robotDrive::getWheelSpeedsAuto,
                                new PIDController(DriveConstants.kPDriveVel,
                                                  DriveConstants.KIDriveVel, 
                                                  DriveConstants.KDDriveVel),
                                new PIDController(DriveConstants.kPDriveVel, 
                                                  DriveConstants.KIDriveVel, 
                                                  DriveConstants.KDDriveVel),
                                // RamseteCommand passes volts to the callback
                                m_robotDrive::tankDriveVolts,
                                m_robotDrive);

                // Reset odometry to the starting pose of the trajectory.
                m_robotDrive.resetOdometry(exampleTrajectory.getInitialPose());

                // Run path following command, then stop at the end.
                return ramseteCommand.andThen(() -> m_robotDrive.tankDriveVolts(0, 0));
        }

        public DriveSubsystem getDriveSubsystem(){
                return m_robotDrive;
        }

        public void sendToDashboard() {
                m_robotDrive.sendToDashboard();
        }
}
