package frc.robot.subsystems.intakeFeeder;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class IntakeFeeder extends SubsystemBase {


    // intake
    private CANSparkMax m_intakeMotor;
    private Solenoid m_intakeSolenoid;

    private boolean isIntakeDown;
    private double intakeMotorSpeed;


    // feeder
    private CANSparkMax m_feederMotor;

    private double feederMotorSpeed;


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
        m_intakeSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, DriveConstants.kIntakeSolenoidPort);
        isIntakeDown = false; // intake starts up
        intakeMotorSpeed = .5; // set proper motor speed later
        direction = 1;

        // feeder
        m_feederMotor = new CANSparkMax(DriveConstants.kFeederMotorPort, MotorType.kBrushless);
        feederMotorSpeed = .5; // set proper motor speed later
        
    }

    // Intake

    /**
     * lowers intake
     */
    public void intakeDown() {
        m_intakeSolenoid.set(true);
        isIntakeDown = m_intakeSolenoid.get();
    }

    /**
     * stops intake motor and raises intake
     */
    public void intakeUp() {
        // stops intake before raising
        stopIntake();
        m_intakeSolenoid.set(false);
        isIntakeDown = m_intakeSolenoid.get();
    }

    /**
     * toggles intake position
     */
    public void toggleIntakeState() {
        m_intakeSolenoid.toggle();
        isIntakeDown = m_intakeSolenoid.get();
    }

    /**
     * starts intake motor
     */
    public void startIntake() {
        m_intakeMotor.set(intakeMotorSpeed);
    }

    /**
     * stop intake motor
     */
    public void stopIntake() {
        m_intakeMotor.set(0);
    }

    /**
     * returns intake motor speed
     */
    public double getIntakeSpeed() {
        return m_intakeMotor.get();
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
        m_feederMotor.set(feederMotorSpeed);
    }

    /**
     * stops feeder motor
     */
    public void stopFeeder() {
        m_feederMotor.set(0);
    }

    /**
     * toggles intake/feeder direction
     */
    public void toggleIntakeFeeder() {
        m_feederMotor.set(-feederMotorSpeed);
        m_intakeMotor.set(-intakeMotorSpeed);
        direction = -direction;
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
     * returns feeder motor speed
     */
    public double getFeederSpeed() {
        return m_feederMotor.get();
    }

    /**
     * returns true if ball is detected in feeder
     */
    public boolean feederContactingBall(){
        return !dIOFeeder.get();
    }

    /**
     * returns true if ball is detected in intake
     */
    public boolean intakeContactingBall(){
        return !dIOIntake.get();
    }

}
