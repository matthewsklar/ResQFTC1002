package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by CircuitRunnersFTC on 1/13/2016.
 */
public class HowLongAutonomous extends HowLongHardware {
    /**
     * The RGB value of a color required to recognize it as the color.
     */
    private final int colorThreshold = 150; //Guess?

    /**
     * Called when the robot initializes
     */
    @Override public void init() { super.init(); }

    /**
     * Loop repeatedly called during TeleOp while the robot is running.
     */
    @Override public void loop() { }

    /**
     * Called when the robot stops
     */
    @Override public void stop() { }

    /**
     * Analyzes the specified color in respect to threshold
     *
     * @param color The color of the light (0:blue, 1:red)
     * @return If the color is greater than the threshold
     */
    public boolean CorrectColor(int color) {
        switch (color) {
            case 0:
                return (colorSensor.blue() > colorThreshold);
            case 1:
                return (colorSensor.red() > colorThreshold);
            default:
                return false;
        }
    }
}
