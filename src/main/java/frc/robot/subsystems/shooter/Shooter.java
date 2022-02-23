package frc.robot.subsystems.shooter;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase{
    
    //3:1 reduction
    //1:1 for Shooter - might modify
    //2 Pistons operate concurrently

    //Shooter Motor 1
    private CANSparkMax shooterMotorPrimary;
    public RelativeEncoder shooterEncoder;
    private SparkMaxPIDController shooterPIDController;

    private CANSparkMax shooterMotorFollower;

    private DoubleSolenoid hoodSolenoid;

    public double targetSpeed = 0;
    public double settargetSpeed = 0;

    /**
     * Shooter Constructor
     */
    public Shooter(PneumaticHub hub3) {

        shooterMotorPrimary = new CANSparkMax(ShooterConstants.kShooterShootMotor, MotorType.kBrushless);
        // shooterMotorPrimary.setInverted(false);
        shooterMotorPrimary.setIdleMode(IdleMode.kCoast);
        shooterMotorPrimary.setInverted(false);

        shooterEncoder = shooterMotorPrimary.getEncoder();

        shooterPIDController = shooterMotorPrimary.getPIDController();
        shooterPIDController.setP(ShooterConstants.kP);
        shooterPIDController.setI(ShooterConstants.kI);
        shooterPIDController.setD(ShooterConstants.kD);
        shooterPIDController.setFF(ShooterConstants.kFF);
        shooterPIDController.setIZone(ShooterConstants.kIZone);
        shooterPIDController.setOutputRange(ShooterConstants.kMin, ShooterConstants.kMax);


        shooterMotorFollower = new CANSparkMax(ShooterConstants.kShooterFollowMotor, MotorType.kBrushless);
        // shooterMotorFollower.setInverted(false);
        shooterMotorFollower.setIdleMode(IdleMode.kCoast);
        shooterMotorFollower.follow(shooterMotorPrimary, true); 
        
        //Give shooterMotorFollower encoder?
        hoodSolenoid = new DoubleSolenoid(hub3.getModuleNumber(), PneumaticsModuleType.REVPH, 
            ShooterConstants.kShooterSolenoidDown, ShooterConstants.kShooterSolenoidUp);
        hoodSolenoid.set(DoubleSolenoid.Value.kForward);

        settargetSpeed = Constants.ShooterConstants.kShooterHighFar;
    }

    @Override
    public void periodic(){
    }

    /**
     * Method to stop the shooter not using PID
     */
    public void stopShooter() {
        targetSpeed = 0;
        shooterMotorPrimary.stopMotor();
    }

    /**
     * Stops the shooter using PID
     */
    public void stopShooterPID(){
        targetSpeed = 0;
        shooterPIDController.setReference(0, CANSparkMax.ControlType.kVelocity);
    }
    
    /**
     * Sets the shooter up to speed and shoots using PID
     */
    public void shoot() {
        targetSpeed = ShooterConstants.kShooterWheelSpeed;
        shooterPIDController.setReference(ShooterConstants.kShooterWheelSpeed, CANSparkMax.ControlType.kVelocity);
    }

    public void shootAtVelocity(double velocity){
        targetSpeed = velocity;
        shooterPIDController.setReference(velocity, CANSparkMax.ControlType.kVelocity);
    }

    public void fullSpeed(){
        targetSpeed = ShooterConstants.kShooterWheelSpeed;
        shooterMotorPrimary.set(1.0);
        // System.out.println(shooterMotorPrimary.getBusVoltage());
    }

    public void reverseShooter(){
        // targetSpeed = ShooterConstants.kShooterReverseSpeed;
        // shooterPIDController.setReference(ShooterConstants.kShooterReverseSpeed, CANSparkMax.ControlType.kVelocity);
        shooterMotorPrimary.set(-.6);
    }

    /**
     * Gets if the shooter is up to speed
     * @return If the shooter is up to speed
     */
    public boolean isShooterAtSpeed() {
        if ((Math.abs(shooterEncoder.getVelocity()) > (targetSpeed) * ShooterConstants.kThreshold)){
            return true;
        }else{
            return false;
        }
    }

    public void shooterHoodUp(){
        hoodSolenoid.set(Value.kReverse); //SEE IF TRUE MEANS EXTENDED OR RETRACTED
    }

    public void shooterHoodDown(){
        hoodSolenoid.set(Value.kForward);
    }

    public void toggleShooterHood(){
        if(!hoodSolenoid.get().equals(Value.kForward)){
            shooterHoodDown();
        }else{
            shooterHoodUp();
        }
    }

    public Value getHoodUp(){
        return hoodSolenoid.get();
    } 

    public void eject(){
        targetSpeed = ShooterConstants.kShooterWheelSpeed;
        shooterPIDController.setReference(ShooterConstants.kShooterEjectSpeed, CANSparkMax.ControlType.kVelocity);
    }

    public CANSparkMax getEncoder(){
        return (CANSparkMax) shooterEncoder;
    }

    public SparkMaxPIDController getPID(){
        return shooterPIDController;
    }

    public void sendToDashboard() {
        settargetSpeed = SmartDashboard.getNumber("Set Shooter velocity", 0);
        SmartDashboard.putNumber("Shooter velocity", shooterEncoder.getVelocity());
        SmartDashboard.putBoolean("Shooter Target Speed Achieved", isShooterAtSpeed());
        SmartDashboard.putNumber("Set Shooter velocity", settargetSpeed);
    }

}
