package frc.robot.subsystems.intakeFeeder;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeFeederConstants;

import frc.robot.Constants.DriveConstants;
import edu.wpi.first.wpilibj.Relay.Value;
import com.ctre.phoenix.led.ColorFlowAnimation.Direction;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class IntakeFeeder extends SubsystemBase {


    // intake
    private CANSparkMax m_intakeMotor;
    private DoubleSolenoid m_intakeSolenoid;
    
    private boolean isIntakeDown;

    private SparkMaxPIDController intakePIDCOntroller;
    private double intakeMotorSpeed;


    // feeder
    private CANSparkMax m_feederMotor;

    private SparkMaxPIDController feederPIDController;
    private double feederMotorSpeed;
    private int maxVelocity;

    private int direction; // intake/feeder direction

    private DigitalInput dIOFeeder;
    private DigitalInput dIOIntake;

    //Pneumatics
    private PneumaticHub hub3;
    

    public IntakeFeeder(DigitalInput dIO0, DigitalInput dIO1, PneumaticHub hub3) {

        this.dIOIntake = dIO0;
        this.dIOFeeder = dIO1;
        this.hub3 = hub3;

        // intake
        m_intakeMotor = new CANSparkMax(IntakeFeederConstants.kIntakeMotorPort, MotorType.kBrushless);
        m_intakeMotor.setIdleMode(IdleMode.kCoast);
        m_intakeMotor.setInverted(true);
        m_intakeMotor.setSmartCurrentLimit(40);

        // intakePIDCOntroller = m_intakeMotor.getPIDController();
        // intakePIDCOntroller.setP(IntakeFeederConstants.kiP);
        // intakePIDCOntroller.setI(IntakeFeederConstants.kiI);
        // intakePIDCOntroller.setD(IntakeFeederConstants.kiD);
        // intakePIDCOntroller.setFF(IntakeFeederConstants.kiFF);
        // intakePIDCOntroller.setIZone(IntakeFeederConstants.kiIZone);
        // intakePIDCOntroller.setOutputRange(IntakeFeederConstants.kiMin, IntakeFeederConstants.kiMax);

        
        // m_intakeSolenoid = new Solenoid(PneumaticsModuleType.REVPH, DriveConstants.kIntakeSolenoidPort);
        m_intakeSolenoid = new DoubleSolenoid(hub3.getModuleNumber(), 
        PneumaticsModuleType.REVPH,
        IntakeFeederConstants.kIntakeSolenoidPortForward, 
        IntakeFeederConstants.kIntakeSolenoidPortReverse);
        m_intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
        isIntakeDown = m_intakeSolenoid.get().equals(DoubleSolenoid.Value.kReverse); // intake starts up

        direction        = 1;

        // feeder
        m_feederMotor = new CANSparkMax(IntakeFeederConstants.kFeederMotorPort, MotorType.kBrushless);
        m_feederMotor.setIdleMode(IdleMode.kBrake);  //kBrake
        m_feederMotor.setInverted(true);
        m_feederMotor.setSmartCurrentLimit(40);
        feederMotorSpeed = 1.0; // set proper motor speed later

        //TODO ON NOW
        feederPIDController = m_feederMotor.getPIDController();
        feederPIDController.setP(IntakeFeederConstants.kfP);
        feederPIDController.setI(IntakeFeederConstants.kfI);
        feederPIDController.setD(IntakeFeederConstants.kfD);
        feederPIDController.setFF(IntakeFeederConstants.kfFF);
        feederPIDController.setIZone(IntakeFeederConstants.kfIZone);
        feederPIDController.setOutputRange(IntakeFeederConstants.kfMin, IntakeFeederConstants.kfMax);

        maxVelocity = 5600; //5600
        
    }

    // @Override
    // public void periodic(){
    //     if(m_intakeSolenoid.get().equals(DoubleSolenoid.Value.kReverse)){
    //         stopIntake();
    //     }
    // }

    // Intake

    /**
     * lowers intake
     */
    public void intakeDown() {
        m_intakeSolenoid.set(DoubleSolenoid.Value.kForward);
        isIntakeDown = true;
        direction = 1;
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

    public void off(){  //stopIntake  or intakeoff
        m_feederMotor.set(0);
        m_intakeMotor.set(0);
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
        m_intakeMotor.set(0.8 * direction); //.7
    }

    /**
     * stop intake motor
     */
    public void stopIntake() {
        m_intakeMotor.set(0);
    }

    public void reverseFeeder(){
        m_feederMotor.set(-1.0); //Testing
    }

    public void reverseIntake(){
        m_intakeMotor.set(-1.0);
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
        m_feederMotor.set(1.0 * direction);
    }


    public void feederForward(){
        m_feederMotor.set(1.0);
    }
    
    public void feederShoot(){
        m_feederMotor.set(.5);
        // m_feederMotor.getPIDController().setReference(2800, ControlType.kVelocity);
    }

    public void feederShootBumper(){
        m_feederMotor.set(.4);
    }

    public void feederBackward(){
        m_feederMotor.set(-.8);
    }



    public void intakeForward(){
        m_intakeMotor.set(.6);
    }    
    
    public void intakeShoot(){
        m_intakeMotor.set(.7);
    }

    public void intakeBackward(){
        m_intakeMotor.set(-1.0);
    }

    /**
     * stops feeder motor
     */
    public void stopFeeder() {
        m_feederMotor.set(0);
    }

    // General use

    /**
     * toggles intake/feeder direction
     */
    public void toggleIntakeFeeder() {
        direction = -direction;
        m_intakeMotor.set(1.0 * direction);
        m_feederMotor.set(1.0 * direction);
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
        if (intakeContactingBall() && feederContactingBall())
            return true;
        else
            return false;
        }    
        
    public boolean oneBallPresent() {
            if (intakeContactingBall() || feederContactingBall()) {
                return true;
            } else {
                return false;
            }
    }

    /**
     * Sets intake and feeder direction to in
     */
    public void directionIn() {
        direction = 1;
    }

    /**
     * Sets intake and feeder direction to out
     */
    public void directionOut() {
        direction = -1;
    }

    public void sendToDashboard(){
        SmartDashboard.putBoolean("Feeder Sensor", feederContactingBall());
        SmartDashboard.putBoolean("Intake Sensor", intakeContactingBall());
        // SmartDashboard.putNumber("Feeder Speed", m_feederMotor.getEncoder().getVelocity());
        // SmartDashboard.putNumber("Intake Speed", m_intakeMotor.getEncoder().getVelocity());
    }

}