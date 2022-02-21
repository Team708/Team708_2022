package frc.robot.subsystems.climber;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase{

    DoubleSolenoid climbingArm; // - 3
    DoubleSolenoid PTO; // - 2

    DigitalInput hangSwitch2, hangSwitch3;

    public Climber(PneumaticHub hub2, PneumaticHub hub3){
        climbingArm = new DoubleSolenoid(hub3.getModuleNumber(), PneumaticsModuleType.REVPH, 5, 4); //ADD TO CONSTANTS
        climbingArm.set(DoubleSolenoid.Value.kForward);

        PTO = new DoubleSolenoid(hub2.getModuleNumber(), PneumaticsModuleType.REVPH, 4, 3); //ADD TO CONSTANTS
        PTO.set(DoubleSolenoid.Value.kForward);

        //ON MXP
        hangSwitch2 = new DigitalInput(12);
        hangSwitch3 = new DigitalInput(13);
    }

    public void extendClimbingArm(){
        climbingArm.set(DoubleSolenoid.Value.kReverse);
    }

    public void retractClimbingArm(){
        climbingArm.set(DoubleSolenoid.Value.kForward);
    }

    public void sendToDashboard(){
        SmartDashboard.putBoolean("Switch 2", hangSwitch2.get());
        SmartDashboard.putBoolean("Switch 3", hangSwitch3.get());
    }
    
}
