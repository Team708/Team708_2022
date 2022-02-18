package frc.robot.subsystems.intakeFeeder;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class IntakeFeeder extends SubsystemBase {


    // intake
    private CANSparkMax m_intakeMotor;
    private DoubleSolenoid m_intakeSolenoid;
    private SparkMaxPIDController intakePIDCOntroller;

    private boolean isIntakeDown;
    private double intakeMotorSpeed;


    // feeder
    private CANSparkMax m_feederMotor;
    private SparkMaxPIDController feederPIDController;

    private double feederMotorSpeed;

    private int maxVelocity;

    private int direction; // intake/feeder direction

    //IF USING VOLTAGE
    // private double normVoltage = 12.54; //Assign to legitimate feeder typical voltage
    // private double threshold = 0.5; //Redefine through testing

    //IF USING SENSORS
    private DigitalInput dIOFeeder;
    private DigitalInput dIOIntake;
    

    public IntakeFeeder(DigitalInput dIO1, DigitalInput dIO2) {

        this.dIOFeeder = dIO1;
        this.dIOIntake = dIO2;

        // intake
        m_intakeMotor = new CANSparkMax(DriveConstants.kIntakeMotorPort, MotorType.kBrushless);
        m_intakeMotor.setIdleMode(IdleMode.kBrake);

        intakePIDCOntroller = m_intakeMotor.getPIDController();
        intakePIDCOntroller.setP(DriveConstants.kP);
        intakePIDCOntroller.setI(DriveConstants.kI);
        intakePIDCOntroller.setD(DriveConstants.kD);
        intakePIDCOntroller.setFF(DriveConstants.kFF);
        intakePIDCOntroller.setIZone(DriveConstants.kIZone);
        intakePIDCOntroller.setOutputRange(DriveConstants.kMin, DriveConstants.kMax);

        
        // m_intakeSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, DriveConstants.kIntakeSolenoidPort);
        m_intakeSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, DriveConstants.kIntakeSolenoidPortForward, DriveConstants.kIntakeSolenoidPortReverse);
        isIntakeDown = false; // intake starts up
        intakeMotorSpeed = .8; // set proper motor speed later
        direction = 1;

        // feeder
        m_feederMotor = new CANSparkMax(DriveConstants.kFeederMotorPort, MotorType.kBrushless);
        m_feederMotor.setIdleMode(IdleMode.kBrake);
        feederMotorSpeed = .8; // set proper motor speed later

        feederPIDController = m_feederMotor.getPIDController();
        feederPIDController.setP(DriveConstants.kP);
        feederPIDController.setI(DriveConstants.kI);
        feederPIDController.setD(DriveConstants.kD);
        feederPIDController.setFF(DriveConstants.kFF);
        feederPIDController.setIZone(DriveConstants.kIZone);
        feederPIDController.setOutputRange(DriveConstants.kMin, DriveConstants.kMax);

        maxVelocity = 5600;
        
    }

    // Intake

    /**
     * lowers intake
     */
    public void intakeDown() {
        m_intakeSolenoid.set(DoubleSolenoid.Value.kForward);
        isIntakeDown = true;
    }

    /**
     * stops intake motor and raises intake
     */
    public void intakeUp() {
        // stops intake before raising
        stopIntake();
        m_intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
        isIntakeDown = false;
    }

    /**
     * toggles intake position
     */
    public void toggleIntakeState() {
        m_intakeSolenoid.toggle();
        if (m_intakeSolenoid.get() == DoubleSolenoid.Value.kForward) {
            isIntakeDown = true;
        } else if (m_intakeSolenoid.get() == DoubleSolenoid.Value.kReverse) {
            isIntakeDown = false;
        }
    }

    /**
     * starts intake motor
     */
    public void startIntake() {
        // m_intakeMotor.set(intakeMotorSpeed);
        intakePIDCOntroller.setReference(maxVelocity * intakeMotorSpeed, CANSparkMax.ControlType.kVelocity);
        
    }

    /**
     * stop intake motor
     */
    public void stopIntake() {
        // m_intakeMotor.set(0);
        intakePIDCOntroller.setReference(0, CANSparkMax.ControlType.kVelocity);
    }

    // Feeder

    /**
     * Feeder should automatically pull ball from feeder into shooter
     * Out of contact with bottom wheel, in contact with top wheel
     * CANNOT contact shooter wheel
     * 
     * Detect using voltage if top wheel is in contact, POSSIBLY detect voltage of the bottom
     * motor to see same thing; 
     * 
     * If top is contacted and bottom isnt, stop both
     * Ball shouldn't contact more than one thing at a time
     * Ball should not collide - two booleans for voltage detection/sensor detection(?)
     */


    /**
     * starts feeder motor
     */
    public void startFeeder() {
        // m_feederMotor.set(feederMotorSpeed);
        feederPIDController.setReference(maxVelocity * feederMotorSpeed, CANSparkMax.ControlType.kVelocity);
    }

    /**
     * stops feeder motor
     */
    public void stopFeeder() {
        // m_feederMotor.set(0);
        feederPIDController.setReference(0, CANSparkMax.ControlType.kVelocity);
    }

    // General use

    /**
     * toggles intake/feeder direction
     */
    public void toggleIntakeFeeder() {
        direction = -direction;
        // m_feederMotor.set(feederMotorSpeed * direction);
        // m_intakeMotor.set(intakeMotorSpeed * direction);
        feederPIDController.setReference((feederMotorSpeed * direction) * maxVelocity, CANSparkMax.ControlType.kVelocity);
        intakePIDCOntroller.setReference((intakeMotorSpeed * direction) * maxVelocity, CANSparkMax.ControlType.kVelocity);
    }

    /**
     * returns intake/feeder direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * returns intake state
     */
    public boolean isIntakeDown() {
        return isIntakeDown;
    }

    /**
     * returns true if ball is detected in feeder
     */
    public boolean feederContactingBall() {
        return !dIOFeeder.get();
    }

    /**
     * returns true if ball is detected in intake
     */
    public boolean intakeContactingBall() {
        return !dIOIntake.get();
    }

    /**
     * returns true if both intake and feeder sensors detect balls
     */
    public boolean twoBallsPresent() {
        if (intakeContactingBall() && feederContactingBall()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * returns number of balls present in system
     */
    public int getNumberOfBalls() {
        if (intakeContactingBall() && feederContactingBall()) {
            return 2;
        } else {
            return 1;
        }
    }

}