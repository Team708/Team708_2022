package frc.robot.subsystems.shooter;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase{
    
    //3:1 reduction
    //1:1 for Shooter - might modify
    //2 Pistons operate concurrently

    //Shooter Motor 1
    private CANSparkMax shooterMotorP;
    public RelativeEncoder shooterEncoder;
    private SparkMaxPIDController shooterPIDController;

    private CANSparkMax shooterMotorF;

    public double targetSpeed = 0;

    public Shooter() {

        shooterMotorP = new CANSparkMax(ShooterConstants.kShooterShootMotor, MotorType.kBrushless);

        shooterMotorP.setInverted(false);
        shooterMotorP.setIdleMode(IdleMode.kCoast);

        shooterEncoder = shooterMotorP.getEncoder();
        shooterPIDController = shooterMotorP.getPIDController();
    
        shooterPIDController.setP(.0005);
        shooterPIDController.setI(0.0000002);
        shooterPIDController.setD(0); // .00006
        shooterPIDController.setFF(.0002);
        shooterPIDController.setIZone(0);
        shooterPIDController.setOutputRange(-1, 1);

    }

    public void stopShooter() {
        targetSpeed = 0;
        shooterMotorP.stopMotor();
    }

    public void stopShooterPID(){
        targetSpeed = 0;
        shooterPIDController.setReference(0, CANSparkMax.ControlType.kVelocity);
    }
    
    public void shoot() {
        targetSpeed = ShooterConstants.kShooterWheelSpeed;
        shooterPIDController.setReference(ShooterConstants.kShooterWheelSpeed, CANSparkMax.ControlType.kVelocity);
    }

    public boolean isShooterAtSpeed() {
        if ((Math.abs(shooterEncoder.getVelocity()) > (targetSpeed) * 0.9)){
            return true;
        }else{
            return false;
        }
    }

    public void sendToDashboard() {
        SmartDashboard.putNumber("Shooter velocity", shooterEncoder.getVelocity());
        SmartDashboard.putBoolean("Shooter Target Speed Achieved", isShooterAtSpeed());
    }

}
