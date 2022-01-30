package frc.robot;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import java.util.List;

public class TrajectoryManager {

    /**
     * Trajectory auto for driving straight - WORKS
     * START: [x = 0, y = 0, θ = 0°]
     * LIST: {[x = 1, y = 0, θ = 0°], [x = 2, y = 0, θ = 0°]}
     * END: [x = 3, y = 0, θ = 0°]
     * @return A new trajectory for driving straight
     */
    public Trajectory driveStraightTrajectory() {
        // Create a voltage constraint to ensure we don't accelerate too fast
        var autoVoltageConstraint = new DifferentialDriveVoltageConstraint(
                new SimpleMotorFeedforward(
                        DriveConstants.ksVolts,
                        DriveConstants.kvVoltSecondsPerMeter,
                        DriveConstants.kaVoltSecondsSquaredPerMeter),
                DriveConstants.kDriveKinematics,
                10);

        // Create config for trajectory
        TrajectoryConfig config = new TrajectoryConfig(
                AutoConstants.kMaxSpeedMetersPerSecond,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared)
                        // Add kinematics to ensure max speed is actually obeyed
                        .setKinematics(DriveConstants.kDriveKinematics)
                        // Apply the voltage constraint
                        .addConstraint(autoVoltageConstraint);

        // An example trajectory to follow. All units in meters.
        Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
                new Pose2d(0, 0, new Rotation2d(0)),
                List.of(new Translation2d(1, 0), new Translation2d(2, 0)),
                new Pose2d(3, 0, new Rotation2d(0)),
                config);

        return exampleTrajectory;
    }



    /**
     * Trajectory auto for driving in an s - NOT WORKS
     * START: [x = 0, y = 0, θ = 0°]
     * LIST: {[x = 1, y = 0, θ = 0°], [x = 2, y = 0, θ = 0°]}
     * END: [x = 3, y = 0, θ = 0°]
     * @return A new trajectory for driving straight
     */
    // public Trajectory makeSTrajectory() {

    //     // Create a voltage constraint to ensure we don't accelerate too fast
    //     var autoVoltageConstraint = new DifferentialDriveVoltageConstraint(
    //             new SimpleMotorFeedforward(
    //                     DriveConstants.ksVolts,
    //                     DriveConstants.kvVoltSecondsPerMeter,
    //                     DriveConstants.kaVoltSecondsSquaredPerMeter),
    //             DriveConstants.kDriveKinematics,
    //             10);

    //     // Create config for trajectory
    //     TrajectoryConfig config = new TrajectoryConfig(
    //             AutoConstants.kMaxSpeedMetersPerSecond,
    //             AutoConstants.kMaxAccelerationMetersPerSecondSquared)
    //                     // Add kinematics to ensure max speed is actually obeyed
    //                     .setKinematics(DriveConstants.kDriveKinematics)
    //                     // Apply the voltage constraint
    //                     .addConstraint(autoVoltageConstraint);

    //     // An example trajectory to follow. All units in meters.
    //     Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
    //             // Start at the origin facing the +X direction
    //             new Pose2d(0, 0, new Rotation2d(0)),
    //             // Pass through these two interior waypoints, making an 's' curve path
    //             List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
    //             // End 3 meters straight ahead of where we started, facing forward
    //             new Pose2d(3, 0, new Rotation2d(0)),
    //             // Pass config
    //             config);

    //     return exampleTrajectory;
    // }

    public Trajectory makeSTrajectory() {

        // Create a voltage constraint to ensure we don't accelerate too fast
        var autoVoltageConstraint = new DifferentialDriveVoltageConstraint(
                new SimpleMotorFeedforward(
                        DriveConstants.ksVolts,
                        DriveConstants.kvVoltSecondsPerMeter,
                        DriveConstants.kaVoltSecondsSquaredPerMeter),
                DriveConstants.kDriveKinematics,
                10);

        // Create config for trajectory
        TrajectoryConfig config = new TrajectoryConfig(
                AutoConstants.kMaxSpeedMetersPerSecond,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared)
                        // Add kinematics to ensure max speed is actually obeyed
                        .setKinematics(DriveConstants.kDriveKinematics)
                        // Apply the voltage constraint
                        .addConstraint(autoVoltageConstraint);

        // An example trajectory to follow. All units in meters.
        Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
                // Start at the origin facing the +X direction
                new Pose2d(0, 0, new Rotation2d(0)),
                // Pass through these two interior waypoints, making an 's' curve path
                List.of(new Translation2d(2, 0), new Translation2d(3, 1), new Translation2d(4, 2)),
                // End 3 meters straight ahead of where we started, facing forward
                new Pose2d(6, 2, new Rotation2d(0)),
                // Pass config
                config);

        return exampleTrajectory;
    }
}
