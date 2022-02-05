package frc.robot;

import frc.robot.Constants.ControllerConstants;
import frc.robot.commands.Drivetrain.TurnToDegreePID;
import frc.robot.commands.Drivetrain.TurnToDegreeVision;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Drivetrain.TurnToDegreeVision;

public class OI {

	// Gamepads
	public final static XboxController driverGamepad = new XboxController(ControllerConstants.kDriverControllerPort); // Driver

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
		// return deadBand(driverGamepad.getLeftX(), ControllerConstants.kDriverDeadBandRightX);
		return deadBand(driverGamepad.getRightX(), ControllerConstants.kDriverDeadBandRightX);
	}

	public static double getDriverLeftY() {
		return deadBand(driverGamepad.getLeftY(), ControllerConstants.kDriverDeadBandLeftY);
	}

	public static double getDriverRightY() {
		// return deadBand(driverGamepad.getLeftY(), ControllerConstants.kDriverDeadBandRightY);
		return deadBand(driverGamepad.getRightY(), ControllerConstants.kDriverDeadBandRightY);
	}

	public static void configureButtonBindings(DriveSubsystem m_robotDrive, Limelight m_limeLight) {
		// Drive at half speed when the right bumper is held
		new JoystickButton(driverGamepad, Button.kRightBumper.value)
				.whenPressed(() -> m_robotDrive.shiftGearHigh())
				.whenReleased(() -> m_robotDrive.shiftGearLow());

		new JoystickButton(driverGamepad, Button.kLeftBumper.value)
				.whenPressed(() -> m_robotDrive.dropWheels())
				.whenReleased(() -> m_robotDrive.liftWheels());
		new JoystickButton(driverGamepad, Constants.ControllerConstants.IncrementPipelineButton)
		.whenPressed(m_limeLight::incrementPipeline, m_limeLight);
		// new JoystickButton(driverGamepad, Button.kA.value).whenPressed(m_limeLight::turnToTarget, m_limeLight);
		new JoystickButton(driverGamepad, Button.kA.value)
		.whenPressed(new TurnToDegreeVision(.7, m_robotDrive, m_limeLight));
		new JoystickButton(driverGamepad, Button.kB.value)
		.whenPressed(new TurnToDegreePID(.7, m_robotDrive, m_limeLight));
		new JoystickButton(driverGamepad, Button.kX.value)
		.whenPressed(m_robotDrive::resetEncoders);
	}

	    //Feeder 3:1, Shooter 1:1
}