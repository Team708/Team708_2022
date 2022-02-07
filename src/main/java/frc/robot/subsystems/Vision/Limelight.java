package frc.robot.subsystems.Vision;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import com.ctre.phoenix.led.CANdle;

public class Limelight extends SubsystemBase {

    /**
     * Reference to the Limelight NetworkTable
     */
    private NetworkTable limeLight;

    /**
     * Enumerator for whichever NetworkTable
     */
    public enum PIPELINE {
        RETRO_REFLECTIVE,
        RED_BALL,
        BLUE_BALL;
    }

    /**
     * Current Pipeline being accessed by the program
     */
    private PIPELINE currPipeline = PIPELINE.RETRO_REFLECTIVE;

    /**
     * CANdle Object referenced throughout the system
     * 
     * @see CANdleSystem
     */
    private CANdle m_candle;

    /**
     * Constructor for LimeLight class
     * 
     */
    public Limelight() {
        limeLight = NetworkTableInstance.getDefault().getTable("limelight");
        setPipeline(currPipeline);
        m_candle = CANdleSystem.getInstance();
    }

    /**
     * Method to set the current pipeline
     * 
     * @param p Pipeline to set the current pipeline to.
     */
    public void setPipeline(PIPELINE p) {
        NetworkTableEntry pipelineEntry = limeLight.getEntry("pipeline");
        if (p.equals(PIPELINE.BLUE_BALL)) {
            currPipeline = PIPELINE.BLUE_BALL;
            pipelineEntry.setNumber(1);
        } else if (p.equals(PIPELINE.RED_BALL)) {
            currPipeline = PIPELINE.RED_BALL;
            pipelineEntry.setNumber(2);
        } else {
            currPipeline = PIPELINE.RETRO_REFLECTIVE;
            pipelineEntry.setNumber(0);
        }
    }

    /**
     * Method to get the current pipeline.
     * 
     * @return Current pipeline
     */
    public PIPELINE getPipeline() {
        return currPipeline;
    }

    /**
     * A method used to increment the current pipeline
     * PIPELINE ORDER:
     * RETRO_REFLECTIVE -> [0]
     * BLUE_BALL -> [1]
     * RED_BALL -> [2]
     */
    public void incrementPipeline() {
        if (currPipeline.equals(PIPELINE.BLUE_BALL)) {
            setPipeline(PIPELINE.RED_BALL);
            currPipeline = PIPELINE.RED_BALL;
        } else if (currPipeline.equals(PIPELINE.RED_BALL)) {
            setPipeline(PIPELINE.RETRO_REFLECTIVE);
            currPipeline = PIPELINE.RETRO_REFLECTIVE;
        } else {
            setPipeline(PIPELINE.BLUE_BALL);
            currPipeline = PIPELINE.BLUE_BALL;
        }
    }

    /**
     * Get the Tc Network Table - references color under crosshair
     * 
     * @return NetworkTableEntry of color under crosshair
     */
    public NetworkTableEntry getTc() {
        return limeLight.getEntry("tc");
    }

    /**
     * Gets the the Tv Network Table - 1 or 0 value determining if a valid target is
     * found
     * 
     * @return NetworkTableEntry of if a valid target is found
     */
    public NetworkTableEntry getTv() {
        return limeLight.getEntry("tv");
    }

    /**
     * Gets the Tx Network Table - Horizontal Offset from Crosshair to Target
     * 
     * @return Horizontal Offsert from Crosshair to Target
     */
    public NetworkTableEntry getTx() {
        return limeLight.getEntry("tx");
    }

    /**
     * Gets the Ty Network Table - Vertical Offset from Crosshair to Target
     * 
     * @return Vertical Offset from Crosshair to Target
     */
    public NetworkTableEntry getTy() {
        return limeLight.getEntry("ty");
    }

    /**
     * Gets the Ta Network Table - Percent area of the target compared to the screen
     * 
     * @return Percent area of the target
     */
    public NetworkTableEntry getTa() {
        return limeLight.getEntry("ta");
    }

    /**
     * @return The HSV value of the pixels behind the center camera
     */
    public Number[] getColorUnderCrosshair() {
        return getTc().getNumberArray(new Number[] { 0, 0, 0 });
    }

    /**
     * @return A boolean if there is a valid target on the screen
     */
    public boolean areValidTargets() {
        return getTv().getNumber(0).intValue() == 1;
    }

    /**
     * @return Returns the horizontal distance between the target and crosshair
     */
    public double getX() {
        return getTx().getDouble(0.0);
    }

    /**
     * @return Returns the vertical distance between the target and crosshair
     */
    public double getY() {
        return getTy().getDouble(0.0);
    }

    /**
     * @return Gets the percent area of the target relative to the screen
     */
    public double getA() {
        return getTa().getDouble(0.0);
    }

    @Override
    public void periodic() {
        if (areValidTargets()) {
            if (currPipeline.equals(PIPELINE.RETRO_REFLECTIVE)) {
                m_candle.setLEDs(0, 255, 0); // WHEN RETRO IS DETECTED, SET LED TO GREEN
            } else if (currPipeline.equals(PIPELINE.BLUE_BALL)) {
                m_candle.setLEDs(0, 0, 255); // WHEN BLUE IS DETECTED, SET LED TO BLUE
            } else if (currPipeline.equals(PIPELINE.RED_BALL)) {
                m_candle.setLEDs(255, 0, 0); // WHEN RED IS DETECTED, SET LED TO RED
            } else {
                m_candle.setLEDs(150, 75, 0); // IF PIPELINE ERROR, SET COLOR TO BROWN
            }
        } else {
            // Maybe decide to make brown regardless of targets, just dependent on pipeline
            m_candle.setLEDs(255, 255, 255); // IF NO VALID TARGETS, SET COLOR TO BROWN
        }
        // System.out.println(Arrays.toString(getColorUnderCrosshair()));
    }

}