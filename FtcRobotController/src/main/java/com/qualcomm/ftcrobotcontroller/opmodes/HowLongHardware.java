package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

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
     * Wing Servos
     */
    public Servo RWS, LWS;

    /**
     * Hanging power
     */
    public double hangingPower;

    /**
     * Color sensor
     */
    public ColorSensor colorSensor;

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

        // Initialize wing servos
        RWS = hardwareMap.servo.get("RWS");
        LWS = hardwareMap.servo.get("LWS");

        // Initialize color sensor
        colorSensor = hardwareMap.colorSensor.get("CS");

        // Reverse wheel motors on right side to correct flipping
        FRW.setDirection(DcMotor.Direction.REVERSE);
        BRW.setDirection(DcMotor.Direction.REVERSE);

        // Reverse RHM to correct flipping
        RHM.setDirection(DcMotor.Direction.REVERSE);

        // Reverse RWS to correct flipping
        RWS.setDirection(Servo.Direction.REVERSE);

        // Set the hanging power
        hangingPower = .5f;                         // Guess
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
     * @param speed RHM and LHM power
     */
    public void SetHangingPower(double speed) {
        RHM.setPower(speed);
        LHM.setPower(speed);
    }

    /**
     * Turn on encoders
     */
    public void RunWithEncoders() {
        FLW.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        FRW.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    /**
     * Turn off encoders
     */
    public void RunWithoutEncoders() {
        if (FLW.getMode() == DcMotorController.RunMode.RESET_ENCODERS) {
            FLW.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        }

        if (FRW.getMode() == DcMotorController.RunMode.RESET_ENCODERS) {
            FRW.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        }
    }

    /**
     * Reset encoders
     */
    public void ResetEncoders() {
        FLW.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        FRW.setMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

    /**
     * Get the left encoder's value
     *
     * @return Left encoder value
     */
    public int LeftEncoderCount() {
        return FLW.getCurrentPosition();
    }

    /**
     * Get the right encoder's value
     *
     * @return Right encoder value
     */
    public int RightEncoderCount() {
        return FRW.getCurrentPosition();
    }

    /**
     * Get if the left encoder value has been reached
     *
     * @param count Encoder value target
     * @return If the left encoder value has reached the target
     */
    public boolean LeftEncoderCountReached(int count) {
        if (LeftEncoderCount() >= count) return true;

        return false;
    }

    /**
     * Get if the right encoder value has been reached
     *
     * @param count Encoder value target
     * @return If the right encoder value has reached the target
     */
    public boolean RightEncoderCountReached(int count) {
        if (RightEncoderCount() >= count) return true;

        return false;
    }
}