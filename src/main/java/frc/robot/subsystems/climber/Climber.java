package frc.robot.subsystems.climber;

import java.beans.Encoder;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxRelativeEncoder;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.drive.DriveSubsystem;

public class Climber extends SubsystemBase{

    DoubleSolenoid  climbingArm;    // - PCM 3
    DoubleSolenoid  PTO;            // - PCM 2
    Solenoid        climberBrake;   // - PCM 3

    DigitalInput hangSwitch2, hangSwitch3;

    boolean climbingArmOut, climberBrakeEngaged, PTOEngaged;

    edu.wpi.first.wpilibj.Encoder m_encoderA;

    public Climber(PneumaticHub hub2, PneumaticHub hub3){

        m_encoderA = new edu.wpi.first.wpilibj.Encoder(14, 15);

        climbingArm = new DoubleSolenoid(hub3.getModuleNumber(), PneumaticsModuleType.REVPH, 5, 4); //ADD TO CONSTANTS
        extendClimbingArm();

        PTO = new DoubleSolenoid(hub2.getModuleNumber(), PneumaticsModuleType.REVPH, 4, 3); //ADD TO CONSTANTS
        releasePTO();

        climberBrake = new Solenoid(hub3.getModuleNumber(), PneumaticsModuleType.REVPH, 7); //BRAKE  ADD TO CONSTANTS
        engageBrake();

        //ON MXP
        hangSwitch2 = new DigitalInput(12);
        hangSwitch3 = new DigitalInput(13);
    }

    public void extendClimbingArm(){
        climbingArm.set(DoubleSolenoid.Value.kReverse);
        climbingArmOut = true;
    }

    public void retractClimbingArm(){
        climbingArm.set(DoubleSolenoid.Value.kForward);
        climbingArmOut = false;
    }

    public void releaseBrake(){
        climberBrake.set(true);
        climberBrakeEngaged = false;
    }

    public void engageBrake(){
        climberBrake.set(false);
        climberBrakeEngaged = true;
    }

    public void activatePTO(){
        PTO.set(DoubleSolenoid.Value.kReverse);
        PTOEngaged = true;
    }

    public void releasePTO(){
        PTO.set(DoubleSolenoid.Value.kForward);
        PTOEngaged = false;
    }    

    public boolean hangSwitch2_engaged(){
        return hangSwitch2.get();
    }    

    public boolean hangSwitch3_engaged(){
        return hangSwitch3.get();
    }    

    public void resetQuadrature(){
        m_encoderA.reset();
    }

    public double getQuadrature(){
        return m_encoderA.getRaw();
    }

    public void sendToDashboard(){
        SmartDashboard.putBoolean("Climber Arm Switch 2", hangSwitch2_engaged());
        SmartDashboard.putBoolean("Climber Arm Switch 3",  hangSwitch3_engaged());

        SmartDashboard.putBoolean("Climber Brake Engaged", climberBrakeEngaged);

        SmartDashboard.putBoolean("PTO Engaged", PTOEngaged);
        SmartDashboard.putBoolean("Climbing Arm Out", climbingArmOut);

        SmartDashboard.putNumber("Climber True Encoder", m_encoderA.getRaw());
    }
    
}
