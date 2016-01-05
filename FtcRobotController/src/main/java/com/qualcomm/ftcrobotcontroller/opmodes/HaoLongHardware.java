package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Set;

/**
 * @author FTC 1002
 * @version 1.0
 * @since 2015-11-30
 */
public class HaoLongHardware extends OpMode {
    public enum State { START, MOVEAROUNDSTARTCORNER, TURNAROUNDSTARTCORNER, MOVEPARALLELTOPARKINGRAMP, TURNTOWARDPARKINGRAMP, MOVEUPRAMP, DONOTHING }

    public State state = State.MOVEAROUNDSTARTCORNER;

    /**
     * Wheel Motors
     */
    DcMotor FRW, FLW, BRW, BLW;

    /**
     * Hanging Motors
     */
    DcMotor hangingPower, hangingPincer, hangingPivot, hangingPivot2;

    /**
     * Wing Servos
     */
    Servo RWing, LWing, pivotBrake, hangBrake, servoPlease;

    public boolean encoder = false;

    /**
     * Called when the robot initializes
     * Contains code required in all programs during initialization
     */
    @Override public void init() {
        // Initialize wheel motors
        FRW = hardwareMap.dcMotor.get("FRW");
        FLW = hardwareMap.dcMotor.get("FLW");
        BRW = hardwareMap.dcMotor.get("BRW");
        BLW = hardwareMap.dcMotor.get("BLW");

        // Initialize wing servos
        RWing = hardwareMap.servo.get("RWing");
        LWing = hardwareMap.servo.get("LWing");
        /*pivotBrake = hardwareMap.servo.get("pivotBrake");
        hangBrake = hardwareMap.servo.get("hangBrake");*/
        servoPlease = hardwareMap.servo.get("servo");

        // Initialize hanging motors
        hangingPower = hardwareMap.dcMotor.get("hangingPower");
        hangingPincer = hardwareMap.dcMotor.get("hangingPincer");
        hangingPivot = hardwareMap.dcMotor.get("hangingPivot");
        hangingPivot2 = hardwareMap.dcMotor.get("hangingPivot2");

        // Reverse wheel motors on right side to correct flipping
        FRW.setDirection(DcMotor.Direction.REVERSE);
        BRW.setDirection(DcMotor.Direction.REVERSE);

        // And reverse the second pivot motor so they both get the same commands
        hangingPivot2.setDirection(DcMotor.Direction.REVERSE);
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
    @Override public void loop() { }

    /**
     * Called when the robot stops
     * Contains code required in all programs during stop
     */
    @Override public void stop() { }

    /**
     * Autonomous move that moves each wheel at the same speed
     */
    public void Move(double speed, int count, State nextState) {
        if (!LeftEncoderCountReached(count)) {
            SetDrivePower(speed, speed);
        } else {
            Stop();
            encoder = false;
            ResetEncoders();
            state = nextState;
        }
    }

    /**
     * Turns the robot (1:right;-1:left)
     */
    public void Turn(double speed, int direction) {
        SetDrivePower(speed * direction, -speed * direction);
    }

    /**
     * Stops the robot
     */
    public void Stop() {
        SetDrivePower(0, 0);
    }

    /**
     * Set values for motors to drive robot with tank drive system.
     * @param leftPower     The power for the left wheel motors
     * @param rightPower    The power for the right wheel motors
     */
    public void SetDrivePower(double leftPower, double rightPower) {
        FRW.setPower(rightPower);
        FLW.setPower(leftPower);
        BRW.setPower(-rightPower);
        BLW.setPower(-leftPower);
    }

    public void RunWithEncoders() {
        FLW.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        FRW.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }

    public void RunWithoutEncoders() {
        if (FLW.getMode() == DcMotorController.RunMode.RESET_ENCODERS) {
            FLW.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        }

        if (FRW.getMode() == DcMotorController.RunMode.RESET_ENCODERS) {
            FRW.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        }
    }

    public void ResetEncoders() {
        FLW.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        FRW.setMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

    public int LeftEncoderCount() {
        return FLW.getCurrentPosition();
    }

    public int RightEncoderCount() {
        return FRW.getCurrentPosition();
    }

    public boolean LeftEncoderCountReached(int count) {
        if (LeftEncoderCount() >= count) return true;

        return false;
    }

    public boolean RightEncoderCountReached(int count) {
        if (RightEncoderCount() >= count) return true;

        return false;
    }

    public void SetRWing(double degree) {
        RWing.setPosition(degree);
    }

    public void SetLWing(double degree) {
        LWing.setPosition(degree);
    }
}