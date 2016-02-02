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
     * Encoder count for a 90 degree turn
     */
    private final int degreesPer90 = 1500; //Guess?

    /**
     * Wheel Motors
     */
    public DcMotor FRW, FLW, BRW, BLW;

    /**
     * Hanging Motors
     */
    public DcMotor RHM, LHM;

    /**
     * Pivot Motor
     */
    public DcMotor Pivot;

    /**
     * Wing Servos
     */
    public Servo RWS, LWS;

    /**
     * Shield Servos
     */
    public Servo FSS, BSS;

    /**
     * Dunk Servos
     */
    public Servo Dunk;

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
        //LHM = hardwareMap.dcMotor.get("LHM");

        // Initialize pivot motor
        Pivot = hardwareMap.dcMotor.get("Pivot");

        // Initialize wing servos
        RWS = hardwareMap.servo.get("RWS");
        LWS = hardwareMap.servo.get("LWS");

        // Initialize shield servos
        FSS = hardwareMap.servo.get("FSS");
        BSS = hardwareMap.servo.get("BSS");

        // Initialize dunk servo
        Dunk = hardwareMap.servo.get("Dunk");
/*
        // Initialize color sensor
        colorSensor = hardwareMap.colorSensor.get("CS");
*/
        // Reverse wheel motors on right side to correct flipping
        FLW.setDirection(DcMotor.Direction.REVERSE);
        BRW.setDirection(DcMotor.Direction.REVERSE);

        // Reverse RWS to correct flipping
        RWS.setDirection(Servo.Direction.REVERSE);

        // Reverse FSS to correct flipping
        FSS.setDirection(Servo.Direction.REVERSE);

        // Set the hanging power
        hangingPower = 1.0;                         // Guess
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

    public void customWait(int time) {
        long original = System.currentTimeMillis();

        while (System.currentTimeMillis() - original < time) { }
    }

    /**
     * Move the robot
     *
     * @param speed Motor speed
     * @param count Distance
     * @return If the move is finished
     */
    public boolean Move(double speed, int count) {
        speed /= 100;

        if (!LeftEncoderCountReached(count)) {
            SetDrivePower(speed, speed);

            return false;
        }

        Stop();

        return true;
    }

    /**
     * Turn the robot
     *
     * @param speed Motor speed
     * @param degrees Degrees to turn
     * @return If the turn is finished
     */
    public boolean Turn(double speed, int degrees) {
        speed /= 100;

        if (!LeftEncoderCountReached(degrees * (degreesPer90 / 90))) {
            SetDrivePower(speed, -speed);

            return false;
        }

        Stop();

        return true;
    }

    /**
     * Stops the drive train
     */
    public void Stop() {
        SetDrivePower(0, 0);
    }

    /**
     * Analyzes the specified color in respect to threshold
     *
     * @param color The color of the light (0:blue, 1:red)
     * @return If the color is greater than the threshold
     */
    public boolean CorrectColor(int color, int colorThreshold) {
        switch (color) {
            case 0:
                return (colorSensor.blue() > colorThreshold);
            case 1:
                return (colorSensor.red() > colorThreshold);
            default:
                return false;
        }
    }

    /**
     * Set the hang power of the robot
     *
     * @param speed RHM and LHM power
     */
    public void SetHangingPower(double speed) {
        RHM.setPower(speed);
        //LHM.setPower(speed);
    }

    /**
     * Turn on encoders
     */
    public void RunWithEncoders() {
        BLW.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        BRW.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    /**
     * Turn off encoders
     */
    public void RunWithoutEncoders() {
        if (BLW.getMode() == DcMotorController.RunMode.RESET_ENCODERS) {
            BLW.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        }

        if (BRW.getMode() == DcMotorController.RunMode.RESET_ENCODERS) {
            BRW.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        }
    }

    /**
     * Reset encoders
     */
    public void ResetEncoders() {
        BLW.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        BRW.setMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

    /**
     * Get the left encoder's value
     *
     * @return Left encoder value
     */
    public int LeftEncoderCount() {
        return BLW.getCurrentPosition();
    }

    /**
     * Get the right encoder's value
     *
     * @return Right encoder value
     */
    public int RightEncoderCount() {
        return BRW.getCurrentPosition();
    }

    /**
     * Get if the left encoder value has been reached
     *
     * @param count Encoder value target
     * @return If the left encoder value has reached the target
     */
    public boolean LeftEncoderCountReached(int count) {
        if (Math.abs(LeftEncoderCount()) >= count) return true;

        return false;
    }

    /**
     * Get if the right encoder value has been reached
     *
     * @param count Encoder value target
     * @return If the right encoder value has reached the target
     */
    public boolean RightEncoderCountReached(int count) {
        if (Math.abs(RightEncoderCount()) >= count) return true;

        return false;
    }
}