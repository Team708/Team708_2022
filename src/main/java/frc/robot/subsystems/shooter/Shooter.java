package frc.robot.subsystems.shooter;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
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

    private Solenoid solenoidLeft;
    private Solenoid solenoidRight;

    public double targetSpeed = 0;

    private boolean hoodUp = false;
    private boolean followerUp = false;

    /**
     * Shooter Constructor
     */
    public Shooter() {

        shooterMotorPrimary = new CANSparkMax(ShooterConstants.kShooterShootMotor, MotorType.kBrushless);

        // shooterMotorPrimary.setInverted(false);
        shooterMotorPrimary.setIdleMode(IdleMode.kCoast);

        shooterMotorFollower = new CANSparkMax(ShooterConstants.kShooterFollowMotor, MotorType.kBrushless);

        // shooterMotorFollower.setInverted(false);
        // shooterMotorFollower.setIdleMode(IdleMode.kCoast);
        // shooterMotorFollower.follow(shooterMotorPrimary); 
        
        //Give shooterMotorFollower encoder?

        shooterEncoder = shooterMotorPrimary.getEncoder();
        shooterPIDController = shooterMotorPrimary.getPIDController();
    
        shooterMotorPrimary.setInverted(false);
        shooterMotorPrimary.setIdleMode(IdleMode.kCoast);
        
        shooterPIDController.setP(ShooterConstants.kP);
        shooterPIDController.setI(ShooterConstants.kI);
        shooterPIDController.setD(ShooterConstants.kD);
        shooterPIDController.setFF(ShooterConstants.kFF);
        shooterPIDController.setIZone(ShooterConstants.kIZone);
        shooterPIDController.setOutputRange(ShooterConstants.kMin, ShooterConstants.kMax);
    
        hoodUp = solenoidLeft.get();
        followerUp = solenoidRight.get();
        if(hoodUp != followerUp) System.out.println("ERR"); //TESTING
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

    public void fullSpeed(){
        targetSpeed = ShooterConstants.kShooterWheelSpeed;
        shooterMotorPrimary.set(1.0);
        System.out.println(shooterMotorPrimary.getBusVoltage());
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
        if(!hoodUp && !followerUp){
            solenoidLeft.set(true); //SEE IF TRUE MEANS EXTENDED OR RETRACTED
            solenoidRight.set(true);
            hoodUp = true;
            followerUp = true;
        }
    }

    public void shooterHoodDown(){
        if(hoodUp && followerUp){
            solenoidLeft.set(false);
            solenoidRight.set(false);
        }
    }

    public boolean getHoodUp(){
        return solenoidLeft.get() && solenoidRight.get();
    } 

    public void sendToDashboard() {
        SmartDashboard.putNumber("Shooter velocity", shooterEncoder.getVelocity());
        SmartDashboard.putBoolean("Shooter Target Speed Achieved", isShooterAtSpeed());
        SmartDashboard.putBoolean("Left Solenoid", solenoidLeft.get());
        SmartDashboard.putBoolean("Right Solenoid", solenoidRight.get());
    }

}
