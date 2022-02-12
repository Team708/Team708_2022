package frc.robot.subsystems.intakeFeeder;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class IntakeFeeder extends SubsystemBase {


    // intake
    private CANSparkMax m_intakeMotor;
    private Solenoid m_intakeSolenoid;
    private DigitalInput m_intakeBallSensor;

    private boolean isIntakeDown;
    private double intakeMotorSpeed;

    int solenoidChannel = 1; // set actual solenoid channel later

    // feeder
    private CANSparkMax m_feederMotor;
    private DigitalInput m_feederBallSensor;
    private double feederMotorSpeed;

    private int direction; // intake/feeder direction

    int feederCANid = 0; // set actual feeder CAN id later
    

    public IntakeFeeder() { 
        // intake
        m_intakeMotor = new CANSparkMax(DriveConstants.kIntakeMotorPort, MotorType.kBrushless);
        m_intakeSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, solenoidChannel);
        isIntakeDown = false; // intake starts up
        intakeMotorSpeed = .5; // set proper motor speed later
        direction = 1;
        m_intakeBallSensor = new DigitalInput(0); // change input channel later

        // feeder
        m_feederMotor = new CANSparkMax(feederCANid, MotorType.kBrushless);
        feederMotorSpeed = .5; // set proper motor speed later
        m_feederBallSensor = new DigitalInput(0); // change input channel later
        
    }

    /**
     * Intake
     */

    // lower intake
    public void intakeDown() {
        m_intakeSolenoid.set(true);
        isIntakeDown = m_intakeSolenoid.get();
    }

    // raise intake
    public void intakeUp() {
        stopIntake();
        m_intakeSolenoid.set(false);
        isIntakeDown = m_intakeSolenoid.get();
    }

    // toggle intake position
    public void toggleIntakeState() {
        // stop intake before bringing up
        if (m_intakeSolenoid.get()) {
            stopIntake();
        }
        m_intakeSolenoid.toggle();
        isIntakeDown = m_intakeSolenoid.get();
    }

    // start intake motor
    public void startIntake() {
        m_intakeMotor.set(intakeMotorSpeed);
    }

    // stop intake motor
    public void stopIntake() {
        m_intakeMotor.set(0);
    }

    // detects if ball is present in intake
    public boolean isBallInIntake() {
        return !m_intakeBallSensor.get();
    }



    

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
      * Feeder
      */


    // start feeder motor
    public void startFeeder() {
        m_feederMotor.set(feederMotorSpeed);
    }

    // stop feeder motor
    public void stopFeeder() {
        m_feederMotor.set(0);
    }

    // detects if ball is present in feeder
    public boolean isBallInFeeder() {
        return !m_feederBallSensor.get();
    }


    /**
     * General Use
     */

    // toggle intake/feeder direction
    public void toggleIntakeFeeder() {
        m_feederMotor.set(-feederMotorSpeed);
        m_intakeMotor.set(-intakeMotorSpeed);
        direction = -direction;
    }

    // returns intake/feeder direction
    public int getDirection() {
        return direction;
    }

    // returns intake state
    public boolean isIntakeDown() {
        return isIntakeDown;
    }

    // logic for automatically running/stopping intake and feeder
    public void intakeFeederLogic() {
        // starts feeder if ball is not present and stops if it is
        if (!isBallInFeeder()) {
            startFeeder();
        } else {
            stopFeeder();
        }

        // starts intake is ball is not present and stops if it is
        if (!isBallInIntake()) {
            startIntake();
        } else {
            stopIntake();
        }


    }

}
