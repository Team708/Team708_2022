// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import java.util.List;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final class DriveConstants {
    public static final int kLeftMotor1Port   = 12;
    public static final int kLeftMotor2Port   = 11;
    public static final int kRightMotor1Port  = 14;
    public static final int kRightMotor2Port  = 15;

    public static int kShiftHSolenoidPort = 0;
    public static int kShiftLSolenoidPort = 1;
    public static int kDriveSolenoidPort = 6;

    public static int kIntakeSolenoidPortForward = 1; //fix port number
    public static int kIntakeSolenoidPortReverse = 0; //fix port number

    public static int kIntakeMotorPort = 21; // fix port number

    public static int kFeederMotorPort = 23; // fix port number

    public static final double kTrackwidthMeters = 0.6604;
    public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(
        kTrackwidthMeters);

    public static final int kEncoderCPR = 42; // 1024
    public static final double kWheelDiameterMeters = 0.105; // .15
    public static final double kEncoderDistancePerPulse =
        // Assumes the encoders are directly mounted on the wheel shafts
        (kWheelDiameterMeters * Math.PI) / (double) kEncoderCPR;

    public static final double kPDriveVel = 10;

    public static final boolean kLeftEncoderInverted = true;
    public static final boolean kRightEncoderInverted = false;
  }

  public static final class ControllerConstants {

    public static final int kDriverControllerPort = 0;
    public static final int kOperatorControllerPort = 1;

    public static final double kDriverDeadBandLeftX = 0.1;
    public static final double kDriverDeadBandRightX = 0.2;
    public static final double kDriverDeadBandLeftY = 0.1;
    public static final double kDriverDeadBandRightY = 0.2;

  }

  public static final class ShooterConstants{

    public static final int kShooterShootMotor = 0;
    public static final double kShooterWheelSpeed = 3900;

  }

  public static final class AutoConstants {
    public static final double kMaxSpeedMetersPerSecond = 3; // 5.48; //TIM - 18ft/s
    public static final double kMaxAccelerationMetersPerSecondSquared = 3; // 2.24; //TIM - No idea

    // Reasonable baseline values for a RAMSETE follower in units of meters and
    // seconds
    public static final double kRamseteB = 2;
    public static final double kRamseteZeta = 0.7;
  }

  public static final class VisionProcessorConstants {

    public static final int CANdleID = 1;

    public static final int kVisionLedOn = 0;
    public static final int kVisionLedOff = 1;

    // (50*38)/(12*20) - Lowspeed
    // (44*38)/(12*26) - Highspeed

  }

  public static final class TrajectoryConstants {
    public static final Trajectory driveStraightTrajectory() {

      TrajectoryConfig config = new TrajectoryConfig(
          AutoConstants.kMaxSpeedMetersPerSecond,
          AutoConstants.kMaxAccelerationMetersPerSecondSquared)
              .setKinematics(DriveConstants.kDriveKinematics);

      Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
          new Pose2d(0, 0, new Rotation2d(0)),
          List.of(new Translation2d(1, 0), new Translation2d(2, 0)),
          new Pose2d(3, 0, new Rotation2d(0)),
          config);

      return exampleTrajectory;
    }

    public static final Trajectory makeSTrajectory() {

      TrajectoryConfig config = new TrajectoryConfig(
          AutoConstants.kMaxSpeedMetersPerSecond,
          AutoConstants.kMaxAccelerationMetersPerSecondSquared)
              .setKinematics(DriveConstants.kDriveKinematics);

      Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
          new Pose2d(0, 0, new Rotation2d(0)),
          List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
          new Pose2d(3, 0, new Rotation2d(0)),
          config);

      return exampleTrajectory;
    }
  }
}
