package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * @author FTC 1002 (Matthew Sklar)
 * @version 1.0
 * @since 2016-5-1
 */
public class HowLongHardware extends OpMode {
    /**
     * Wheel Motors
     */
    public DcMotor FRW, FLW, BRW, BLW;

    /**
     * Hanging Motors
     */
    public DcMotor RHM, LHM;

    /**
     * Hanging power
     */
    public double hangingPower;

    /**
     * Called when the robot initializes
     */
    @Override public void init() {
        // Initialize wheel motors
        FRW = hardwareMap.dcMotor.get("FRW");
        FLW = hardwareMap.dcMotor.get("FLW");
        BRW = hardwareMap.dcMotor.get("BRW");
        BLW = hardwareMap.dcMotor.get("BLW");

        // Initialize hanging motors
        RHM = hardwareMap.dcMotor.get("RHM");
        LHM = hardwareMap.dcMotor.get("LHM");

        // Reverse wheel motors on right side to correct flipping
        FRW.setDirection(DcMotor.Direction.REVERSE);
        BRW.setDirection(DcMotor.Direction.REVERSE);

        // Reverse RHM to correct flipping
        RHM.setDirection(DcMotor.Direction.REVERSE);

        // Set the hanging power
        hangingPower = .5f;
    }

    /**
     * Called when the robot starts
     */
    @Override public void start() { super.start(); }

    /**
     * Called repeatedly as the robot runs
     */
    @Override public void loop() { }

    /**
     * Called when the robot stops
     */
    @Override public void stop() { super.stop(); }

    /**
     * Set the drive power of the robot
     *
     * @param rightSpeed FRW and BRW power
     * @param leftSpeed FLW and BLW power
     */
    public void SetDrivePower(double rightSpeed, double leftSpeed) {
        FRW.setPower(rightSpeed);
        FLW.setPower(leftSpeed);
        BRW.setPower(rightSpeed);
        BLW.setPower(leftSpeed);
    }

    /**
     * Set the hang power of the robot
     *
     * @param speed HRM and HLM power
     */
    public void SetHangingPower(double speed) {
        RHM.setPower(speed);
        LHM.setPower(speed);
    }
}
