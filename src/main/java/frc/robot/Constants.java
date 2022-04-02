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


  public static final class
                       DriveConstants {
    public static final int kLeftMotor1Port   = 11;
    public static final int kLeftMotor2Port   = 12;
    public static final int kRightMotor1Port  = 14;
    public static final int kRightMotor2Port  = 15;

    public static int kShiftHSolenoidPort = 1; //5
    public static int kShiftLSolenoidPort = 0; //4
    public static int kDriveSolenoidPort  = 2; //2

    //CLIMBER: 3 - off, 4 - on
    //ALSO CLIMBER: 4 - in, 5 - out, 6 - climber pin (single), 7 - break (single)

    //PID Values
    public static final double kP       = 1.5;
    public static final double kI       = 0.0000001;
    public static final double kD       = 0.00000006;
    public static final double kFF      = 0.000002;
    public static final double kIZone   = 0;
    public static final double kMin     = -1;
    public static final double kMax     = 1;

    public static final double kTrackwidthMeters = 0.6604;
    public static final DifferentialDriveKinematics kDriveKinematics = 
                                new DifferentialDriveKinematics(kTrackwidthMeters);

    public static final int kEncoderCPR                 = 42;  //(1672 / 312) * 42; // 1024 //42
    public static final double kWheelDiameterMeters     = 0.102; // .15 //.015 //0.05
    public static final double kWheelRadiusFromCenter   = 0.52;
    public static final double kEncoderRatio            =  .11;  //.1285;  // .119;  //drive train multiplier
    //public static final double kEncoderDistancePerPulse =
        // Assumes the encoders are directly mounted on the wheel shafts
        //(kWheelDiameterMeters * Math.PI) / (double) kEncoderCPR;
    
    public static final double kDriveEncoderDistancePerPulse = 
        ((kEncoderCPR * kEncoderRatio) / Math.PI
                            * (kWheelDiameterMeters) / 3.056814908981323);
                            //  * (kWheelDiameterMeters) / 2.6000);
    
    public static final double kPDriveVel = 10;

    public static final boolean kLeftEncoderInverted  = true;
    public static final boolean kRightEncoderInverted = false;

    public static final double kCountsPerDegree = .0075;  //vision rotaion multiplier
  }

  public static final class IntakeFeederConstants {
    public static int kIntakeSolenoidPortForward = 1;
    public static int kIntakeSolenoidPortReverse = 0;

    public static int kIntakeMotorPort = 21; // fix port number

    public static int kFeederMotorPort = 23; // fix port number

    //INTAKE
    public static final double kiP       = 0.053;   
    public static final double kiI       = 0.0;     
    public static final double kiD       = 0.00006; 
    public static final double kiFF      = 0.001;   
    public static final double kiIZone   = 0;
    public static final double kiMin     = -1;
    public static final double kiMax     = 1;

    //FEEDER
    public static final double kfP       = 0.00060;  //.0006
    public static final double kfI       = 0.001;    //.001
    public static final double kfD       = 0.0007;   //.00006
    public static final double kfFF      = 0.000;    //0.0
    public static final double kfIZone   = 0;
    public static final double kfMin     = -1;
    public static final double kfMax     = 1;
  }

  public static final class ControllerConstants {

    public static final int kDriverControllerPort   = 0;
    public static final int kOperatorControllerPort = 1;
    public static final int kClimberControllerPort  = 2;
    public static final int kAdaptiveControllerPort = 3;

    public static final double kDriverDeadBandLeftX  = 0.1;
    public static final double kDriverDeadBandRightX = 0.2;
    public static final double kDriverDeadBandLeftY  = 0.1;
    public static final double kDriverDeadBandRightY = 0.2;

    public static final double kClimberDeadBandLeftY  = .3;
    public static final double kClimberDeadBandRightY = .3;
    

  }

  public static final class ClimberConstants {
    // public static final double kClimberArmDownSpeed    = 0.8;
    // public static final double kClimberArmUpSpeed      = -.8; 
    // public static final double kClimberArmDownDistance = .5;    // in meters - 1.35
    // public static final double kClimberArmUpDistance   = 1.20;   // in meters
    // public static final double kClimberOffBar          = 0.50;   // in meters
    // public static final double kClimberArmDownTrav     = 0.9;    // pull it off high bar to swing on T bar
    // public static final double kClimberQuadDown        = 30000;  // raw encoder ticks
    // public static final double kClimberfullextend      = 30000;  // raw encoder ticks


  }


  public static final class ShooterConstants{

    //Mappings
    public static final int kShooterShootMotor  = 31;
    public static final int kShooterFollowMotor = 32;

    public static final int kShooterSolenoidDown = 2;
    public static final int kShooterSolenoidUp   = 3;

    //Speed constants
    public static final double kShooterEjectSpeed     = 5600; // 2000;

    public static final double kShooterLowClose       = 1500; //hood up  1450 
    public static final double kShooterHighFar        = 2900;  //hood down
    public static final double kShooterHighBumper     = 2400;  //hood down  2375
    public static final double kShooterStafetyZone    = 2800;  //hood up  3000
    public static final double kShooterFeederStation  = 3450;  //hood up 3600
    

    public static final double kShooterReverseSpeed   = -4000; // -2000
    public static final double kThreshold             = 0.75;     // 0.98; at HH

    //PID Values
    public static final double kP       = 0.00015;  //.00015
    public static final double kI       = 0.000001;
    public static final double kD       = 0.006; //.008 .0017 .00006
    public static final double kFF      = 0.000;  //.0002 TODO
    public static final double kIZone   = 0;
    public static final double kMin     = -1;
    public static final double kMax     = 1;

    // public static final double kP       = 0.00035;  //.0005
    // public static final double kI       = 0.00000;
    // public static final double kD       = 0.0014; // .00006
    // public static final double kFF      = 0.0002;
    // public static final double kIZone   = 0;
    // public static final double kMin     = -1;
    // public static final double kMax     = 1;
  }

  public static final class AutoConstants {
    public static final double kMaxSpeedMetersPerSecond = 6; // 3   5.48; //TIM - 18ft/s  4
    public static final double kMaxAccelerationMetersPerSecondSquared = 4; // 3    2.24; //TIM - No idea 3

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
          // List.of(new Translation2d(1, 0), new Translation2d(2,0)),
          List.of(new Translation2d(1, 0)),
          new Pose2d(1, 0, new Rotation2d(0)),
          // new Pose2d(3, 0, new Rotation2d(0)),
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
          // List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
          List.of(new Translation2d(1, 1)),
          // new Pose2d(3, 0, new Rotation2d(0)),
          new Pose2d(1, 1, new Rotation2d(0)),
          config);

      return exampleTrajectory;
    }
  }
}
