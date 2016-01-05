package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * @author FTC 1002
 * @version 1.0
 * @since 2015-11-30
 */
public class ServoTest extends HaoLongHardware {
    @Override public void init() {
        pivotBrake.setPosition(0.0);
        hangBrake.setPosition(0);
    }

    /**
     * Called when the robot starts
     * Contains code required in all programs during start
     */
    @Override public void start() { }

    /**
     * Called repeatedly while the robot is running
     * Contains code required in all programs during loop
     */
    @Override public void loop() {
        //pivotBrake.setPosition(0.0);
        //hangBrake.setPosition(0);
        telemetry.addData("Pivot Brake", pivotBrake.getPosition());
        telemetry.addData("Hang Brake", hangBrake.getPosition());
    }

    /**
     * Called when the robot stops
     * Contains code required in all programs during stop
     */
    @Override public void stop() { }
}