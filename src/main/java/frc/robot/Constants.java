// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.XboxController;

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
    // public static final int kLeftMotor2Port   = 1;
    public static final int kRightMotor1Port  = 14;
    // public static final int kRightMotor2Port  = 3;

    public static int kShiftHSolenoidPort = 0;
    public static int kShiftLSolenoidPort = 1;
    public static int kDriveSolenoidPort = 6;
    public static int kIntakeSolenoidPort = 6;
    
    public static int kIntakeMotorPort = 21;

        public static final double kTrackwidthMeters = 0.6604;
        public static final double kWheelRadiusFromCenter = 0.3; //TODO EDIT
    public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(
        kTrackwidthMeters);

    //1 rev of wheel is .33 meters

    public static final int kEncoderCPR                 = 42; //1024, 406
    public static final double kWheelDiameterMeters     = 0.105; //.15
    public static final double kEncoderDistancePerPulse =
                    // Assumes the encoders are directly mounted on the wheel shafts
                              (kWheelDiameterMeters * Math.PI) / (double) kEncoderCPR;

    // These are example values only - DO NOT USE THESE FOR YOUR OWN ROBOT!
    // These characterization values MUST be determined either experimentally or
    // theoretically
    // for *your* robot's drive.
    // The Robot Characterization Toolsuite provides a convenient tool for obtaining
    // these
    // values for your robot.
    public static final double ksVolts                      = 0.22;
    public static final double kvVoltSecondsPerMeter        = 1.98;
    public static final double kaVoltSecondsSquaredPerMeter = 0.2;

    // Example value only - as above, this must be tuned for your drive!
    public static final double kPDriveVel = 48; //8.5, 16, 24
    public static final double KIDriveVel = 0; //0
    public static final double KDDriveVel = 0; //

    //Might be same values as another thing we will see:
    public static final double kDriveP                   = 0;
    public static final double kDriveI                   = 0;
    public static final double kDriveD                   = 0;
    public static final double kTurnToleranceDeg         = 1;
    public static final double kTurnRateToleranceDegPerS = 5; //?
  }

  public static final class ControllerConstants {

    public static final int kDriverControllerPort     = 0;
    public static final int kOperatorControllerPort   = 1;

    public static final double kDriverDeadBandLeftX   = 0.1;
    public static final double kDriverDeadBandRightX  = 0.2;
    public static final double kDriverDeadBandLeftY   = 0.1;
    public static final double kDriverDeadBandRightY  = 0.2;

    public static final int IncrementPipelineButton = XboxController.Button.kRightBumper.value;

  }

  public static final class AutoConstants {
    public static final double kMaxSpeedMetersPerSecond               = 3; //5.48; //TIM - 18ft/s
    public static final double kMaxAccelerationMetersPerSecondSquared = 3; //2.24; //TIM - No idea

    // Reasonable baseline values for a RAMSETE follower in units of meters and
    // seconds
    public static final double kRamseteB    = 2;
    public static final double kRamseteZeta = 0.7;
  }

  public static final class VisionProcessorConstants {

    public static final int CANdleID = 1;

    public static final int kVisionLedOn  = 0;
    public static final int kVisionLedOff = 1;

    //(50*38)/(12*20) - Lowspeed
    //(44*38)/(12*26) - Highspeed

  }
}
