package frc.robot;

import frc.robot.Constants.ControllerConstants;
import frc.robot.commands.drivetrain.TurnToTargetDegrees;
import frc.robot.commands.intakeFeeder.ToggleIntakeFeeder;
import frc.robot.commands.drivetrain.TurnToTargetEncoder;

import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.vision.Limelight;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class OI {

	// Gamepads
	public static final XboxController driverGamepad = new XboxController(ControllerConstants.kDriverControllerPort); // Driver
	public static final XboxController operatorGamepad = new XboxController(ControllerConstants.kOperatorControllerPort);

	/*
	 * Driver JoystickButton
	 */

	public OI() {

	}

	private static double deadBand(double val, double deadband) {
		return (Math.abs(val) > Math.abs(deadband)) ? val : 0.0;
	}

	public static double getDriverLeftX() {
		return deadBand(driverGamepad.getLeftX(), ControllerConstants.kDriverDeadBandLeftX);
	}

	public static double getDriverRightX() {
		// return deadBand(driverGamepad.getLeftX(),
		// ControllerConstants.kDriverDeadBandRightX);
		return deadBand(driverGamepad.getRightX(), ControllerConstants.kDriverDeadBandRightX);
	}

	public static double getDriverLeftY() {
		return deadBand(driverGamepad.getLeftY(), ControllerConstants.kDriverDeadBandLeftY);
	}

	public static double getDriverRightY() {
		// return deadBand(driverGamepad.getLeftY(),
		// ControllerConstants.kDriverDeadBandRightY);
		return deadBand(driverGamepad.getRightY(), ControllerConstants.kDriverDeadBandRightY);
	}

	public static void configureButtonBindings(DriveSubsystem m_robotDrive, 
											   Limelight m_limeLight, 
											   Shooter m_shooter,
											   IntakeFeeder m_intakeFeeder) {
		// Drive at half speed when the right bumper is held
		new JoystickButton(driverGamepad, Button.kRightBumper.value)
				.whenPressed(() -> m_robotDrive.shiftGearHigh())
				.whenReleased(() -> m_robotDrive.shiftGearLow());

		new JoystickButton(driverGamepad, Button.kLeftBumper.value)
				.whenPressed(() -> m_robotDrive.dropWheels())
				.whenReleased(() -> m_robotDrive.liftWheels());

		new JoystickButton(driverGamepad, Button.kY.value)
				.whenPressed(() -> m_shooter.shoot())
				.whenReleased(() -> m_shooter.stopShooter());

		new JoystickButton(driverGamepad, Button.kX.value)
				.whenPressed(new TurnToTargetDegrees(m_robotDrive, m_limeLight));

		new JoystickButton(operatorGamepad, Button.kA.value)
		.whenPressed(new ToggleIntakeFeeder(m_intakeFeeder));
//				.whenPressed(new TurnToTargetEncoder(.6, m_robotDrive, m_limeLight));
	}
}