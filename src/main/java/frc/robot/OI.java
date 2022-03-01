package frc.robot;

import frc.robot.Constants.ControllerConstants;
import frc.robot.commands.climber.EngageClimberArm;
import frc.robot.commands.drivetrain.TurnToTargetDegrees;
import frc.robot.commands.intakeFeeder.FeederReverse;
import frc.robot.commands.intakeFeeder.IntakeFeederIn;
import frc.robot.commands.intakeFeeder.IntakeFeederOut;
import frc.robot.commands.intakeFeeder.ShootFeeder;
import frc.robot.commands.intakeFeeder.StartIntake;
import frc.robot.commands.intakeFeeder.StopFeeder;
import frc.robot.commands.intakeFeeder.StopIntake;
import frc.robot.commands.intakeFeeder.ToggleIntakeFeeder;
import frc.robot.commands.intakeFeeder.ToggleIntakeSolenoid;
import frc.robot.commands.shooter.Eject;
import frc.robot.commands.shooter.ReverseShooter;
import frc.robot.commands.shooter.ToggleHood;
import frc.robot.commands.shooter.ShootFeederStation;
import frc.robot.commands.shooter.ShootSafteyZone;
import frc.robot.commands.shooter.StopShooter;
import frc.robot.commands.drivetrain.TurnToTargetEncoder;
import frc.robot.commands.drivetrain.TurnToTargetSetPoint;
import frc.robot.commands.drivetrain.TurnTowardsTarget;
import frc.robot.commands.groups.AimShootTarmac;
import frc.robot.commands.groups.AimShootBumper;
import frc.robot.commands.groups.AimShootFeeder;
import frc.robot.commands.groups.AimShootSafetyZone;
import frc.robot.commands.groups.AutoShoot;
import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.subsystems.climber.Climber;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.intakeFeeder.IntakeFeeder;
import frc.robot.subsystems.shooter.Shooter;

import frc.robot.subsystems.vision.Limelight;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class OI {

	// Gamepads
	public final static XboxController driverGamepad   = new XboxController(ControllerConstants.kDriverControllerPort); // Driver
	public final static XboxController operatorGamepad = new XboxController(ControllerConstants.kOperatorControllerPort);
	public final static XboxController climberGamepad  = new XboxController(ControllerConstants.kClimberControllerPort);

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

	public static double getClimberLeftY() {
		return deadBand(climberGamepad.getLeftY(), .3);
	}

	public static void configureButtonBindings(DriveSubsystem m_robotDrive, 
											   Limelight m_limeLight, 
											   Shooter m_shooter,
											   IntakeFeeder m_intakeFeeder,
											   Climber m_climber) {

		//DRIVER//

		// Drive at half speed when the right bumper is held
		new JoystickButton(driverGamepad, Button.kRightBumper.value)
				.whenPressed(() -> m_robotDrive.shiftGearHigh())
				.whenReleased(() -> m_robotDrive.shiftGearLow());

		new JoystickButton(driverGamepad, Button.kLeftBumper.value)
				.whenPressed(() -> m_robotDrive.liftWheels())
				.whenReleased(() -> m_robotDrive.dropWheels());

		new JoystickButton(driverGamepad, Button.kY.value)
				.whenPressed(new ToggleIntakeSolenoid(m_intakeFeeder));
				// .whenPressed(new StartIntake(m_intakeFeeder));
				// .whenReleased(new StopIntake(m_intakeFeeder));

		new JoystickButton(driverGamepad, Button.kA.value)
				.whenPressed(new ToggleHood(m_shooter));		
				
		new JoystickButton(driverGamepad, Button.kBack.value)
				.whenPressed(new StopIntake(m_intakeFeeder))
				.whenPressed(new StopFeeder(m_intakeFeeder));
		
		new JoystickButton(driverGamepad, Button.kX.value)
		        .whenPressed(new IntakeFeederIn(m_intakeFeeder));

		new JoystickButton(driverGamepad, Button.kB.value)
		        .whileHeld(new IntakeFeederOut(m_intakeFeeder));

		new JoystickButton(driverGamepad, Button.kStart.value)
				.whenPressed(new TurnToTargetSetPoint(m_robotDrive, m_limeLight)
				// .whenPressed(new TurnTowardsTarget(m_limeLight, m_robotDrive)
				// .whenPressed(() -> m_robotDrive.resetOdometry(new Pose2d()));
				.withTimeout(3.0));

		new JoystickButton(driverGamepad, Button.kBack.value)
				.whenPressed(() -> m_robotDrive.resetEncoders());
				
				
		//OPERATOR//
				
		
		new JoystickButton(operatorGamepad, Button.kLeftBumper.value)
				.whenPressed(new AimShootTarmac(m_limeLight, m_robotDrive, m_shooter, m_intakeFeeder));
		
		new JoystickButton(operatorGamepad, Button.kRightBumper.value)
				.whenPressed(new AimShootBumper(m_limeLight, m_robotDrive, m_shooter, m_intakeFeeder));
		
		new JoystickButton(operatorGamepad, Button.kB.value)
		        .whenPressed(new StopIntake(m_intakeFeeder))
				.whenPressed(new StopFeeder(m_intakeFeeder));

		new JoystickButton(operatorGamepad, Button.kA.value)
				.whenPressed(new ReverseShooter(m_shooter))
				.whenReleased(new StopShooter(m_shooter));

		new JoystickButton(operatorGamepad, Button.kX.value)
		        .whenPressed(new Eject(m_shooter))
				.whenReleased(new StopShooter(m_shooter));

				
		new JoystickButton(operatorGamepad, Button.kLeftStick.value)
				.whenPressed(new AimShootFeeder(m_limeLight, m_robotDrive, m_shooter, m_intakeFeeder));
				
				
		new JoystickButton(operatorGamepad, Button.kRightStick.value)
		        .whenPressed(new AimShootSafetyZone(m_limeLight, m_robotDrive, m_shooter, m_intakeFeeder));	
				
		new JoystickButton(operatorGamepad, Button.kBack.value)
				.whileHeld(new FeederReverse(m_intakeFeeder));


		//Climber//

		new JoystickButton(climberGamepad, Button.kA.value)
				.whenPressed(m_climber::activateClimbingArm);

		new JoystickButton(climberGamepad, Button.kStart.value)
				.whenPressed(m_climber::stopClimber);

		new JoystickButton(climberGamepad, Button.kY.value)
				.whenPressed(m_climber::startClimber);

		new JoystickButton(climberGamepad, Button.kB.value)
				.whenPressed(m_climber::retractClimbingArm);	

		new JoystickButton(climberGamepad, Button.kX.value)
				.whenPressed(m_climber::extendClimbingArm);	

		new JoystickButton(climberGamepad, Button.kBack.value)
				.whenPressed(m_climber::releaseClimbingArm);	
		
		new JoystickButton(climberGamepad, Button.kLeftStick.value)
				.whenPressed(new EngageClimberArm(m_robotDrive, m_climber));

	}
}