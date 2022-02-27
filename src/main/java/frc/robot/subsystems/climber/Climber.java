package frc.robot.subsystems.climber;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase{

    DoubleSolenoid climbingArm; // - PCM 3
    DoubleSolenoid PTO;         // - PCM 2
    Solenoid climberSafteyPin;  // - PCM 3

    DigitalInput hangSwitch2, hangSwitch3;

    boolean climbingArmOut, ClimberSafteyPinOut, PTOEngaged;

    public Climber(PneumaticHub hub2, PneumaticHub hub3){
        climbingArm = new DoubleSolenoid(hub3.getModuleNumber(), PneumaticsModuleType.REVPH, 5, 4); //ADD TO CONSTANTS
        extendClimbingArm();

        PTO = new DoubleSolenoid(hub2.getModuleNumber(), PneumaticsModuleType.REVPH, 4, 3); //ADD TO CONSTANTS
        releaseClimbingArm();

        climberSafteyPin = new Solenoid(hub2.getModuleNumber(), PneumaticsModuleType.REVPH, 6); //ADD TO CONSTANTS
        stopCimber();

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

    public void startClimber(){
        climberSafteyPin.set(true);
        ClimberSafteyPinOut = true;
    }

    public void stopCimber(){
        climberSafteyPin.set(false);
        ClimberSafteyPinOut = false;
    }

    public void activateClimbingArm(){
        PTO.set(DoubleSolenoid.Value.kReverse);
        PTOEngaged = true;
    }

    public void releaseClimbingArm(){
        PTO.set(DoubleSolenoid.Value.kForward);
        PTOEngaged = false;
    }    

    public boolean hangSwitch2_engaged(){
        return hangSwitch2.get();
    }    

    public boolean hangSwitch3_engaged(){
        return hangSwitch3.get();
    }    

    public void sendToDashboard(){
        SmartDashboard.putBoolean("Climber Arm Switch 2", hangSwitch2_engaged());
        SmartDashboard.putBoolean("Climber Arm Switch 3",  hangSwitch3_engaged());

        SmartDashboard.putBoolean("Climber Safety Pin", ClimberSafteyPinOut);

        SmartDashboard.putBoolean("PTO Engaged", PTOEngaged);
        SmartDashboard.putBoolean("Climbing Arm Out", climbingArmOut);
    }
    
}
