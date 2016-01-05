package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by CircuitRunnersFTC on 12/11/2015.
 */
public class Test extends OpMode {
    /**
     * Wheel Motors
     */
    DcMotor FRW, FLW;

    /**
     * Called when the robot initializes
     * Contains code required in all programs during initialization
     */
    @Override public void init() {
        // Initialize wheel motors
        FRW = hardwareMap.dcMotor.get("FRW");
        FLW = hardwareMap.dcMotor.get("FLW");

        // Reverse wheel motors on right side to correct flipping
        FRW.setDirection(DcMotor.Direction.REVERSE);
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
        FRW.setPower(1);
        FLW.setPower(1);
    }

    /**
     * Called when the robot stops
     * Contains code required in all programs during stop
     */
    @Override public void stop() { }
}
