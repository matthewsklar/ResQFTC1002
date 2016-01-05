package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.util.Range;

/**
 * @author FTC 1002
 * @version 1.1
 * @since 2015-9-28
 */
public class HaoLongTeleOp extends HaoLongHardware {
    private int speedMultiplier;

    private float powerHangingSpeed;
    private float pincerHangingSpeed;
    private float pivotHangingSpeed;

    // Speed from joystick for hanging
    private float hangMove;
    private float hangPivot;
    // Speed to apply when joystick is released
    private float keepMove;
    private float keepPivot;

    private boolean speedReversePressed;
    private boolean speedChangePressed;
    private boolean hangBrakePressed;
    private boolean hangPivotBrakePressed;
    private boolean hangPivotPressed;

    private float[] wingDegrees = { 0, 90 };

    @Override
    public void init() {
        super.init();

        speedMultiplier = 1;

        powerHangingSpeed = .7f;
        pincerHangingSpeed = .3f;
        pivotHangingSpeed = .25f;

        speedReversePressed = false;
        speedChangePressed = false;
        hangPivotBrakePressed = false;
        hangBrakePressed = false;
        hangPivotPressed = false;

        // No retention on the claw yet
        keepMove = 0.0f;
        keepPivot = 0;

        hangPivot = .75f;
        servoPlease.setPosition(.5);
        LWing.setPosition(.5);
        RWing.setPosition(.5);
    }

    /**
     * Loop repeatedly called during TeleOp while the robot is running.
     */
    @Override
    public void loop() {
        // Scale values from analog sticks into range readable by motors
        float leftY = (float)scaleInput(Range.clip(gamepad1.left_stick_y, -1, 1));
        float rightY = (float)scaleInput(Range.clip(gamepad1.right_stick_y, -1, 1));
        float hangMove = -(float)scaleInput(Range.clip(gamepad2.right_stick_y, -1, 1));
        float hangPincher = -(float)scaleInput(Range.clip(gamepad2.left_stick_y, -.5, .5));

        //hangingPower.setPower(hangMove);
        // Control motors for hanging the robot
        if (hangMove > 0.1) hangingPower.setPower(hangMove);
        else if (hangMove < -0.1) hangingPower.setPower(hangMove);
        else hangingPower.setPower(0);

        if (gamepad1.right_bumper) servoPlease.setPosition(.5);
        else if (gamepad1.left_bumper) servoPlease.setPosition(0);

        hangingPincer.setPower(hangPincher);

        if (gamepad2.right_bumper) {
            if (!hangPivotPressed) {
                hangPivotPressed = true;
                if (hangPivot == .75f) hangPivot = 1;
                else if (hangPivot == 1) hangPivot = .75f;
            }
        } else if (hangPivotPressed) hangPivotPressed = false;

        // Control motors for pivoting the hanger
        // Power is based on how far the joystick is moved
        if (gamepad2.dpad_up) {
            hangingPivot.setPower(-hangPivot);
            hangingPivot2.setPower(-hangPivot);
        } else if (gamepad2.dpad_down) {
            hangingPivot.setPower(hangPivot);
            hangingPivot2.setPower(hangPivot);
        } else {
            // Stop the pivot motors
            hangingPivot.setPower(0);
            hangingPivot2.setPower(0);
        }

        // Control servos for controlling the wings
        // *** X Y for left, A B for right
        if (gamepad2.x) LWing.setPosition(0.0);
        else if (gamepad2.y) LWing.setPosition(1.0);
        else LWing.setPosition(0.5);

        if (gamepad2.a) RWing.setPosition(1.0);
        else if (gamepad2.b) RWing.setPosition(0.0);
        else RWing.setPosition(0.5);

        // Reverse the tank drive direction
        if (gamepad1.start) {
            if (!speedReversePressed) {
                speedMultiplier *= -1;
                speedReversePressed = true;
            }
        } else if (speedReversePressed) speedReversePressed = false;

        SetDrivePower(speedMultiplier * rightY, speedMultiplier * leftY);

        telemetry.addData("Drive Right", speedMultiplier * rightY);
        telemetry.addData("Drive Left", speedMultiplier * leftY);
    }

    @Override
    public void stop() { }

    private boolean toggle(boolean toggleVar) {
        if (!toggleVar) {

        }

        return false;
    }

    /**
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInput(double dVal) {
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);
        if (index < 0) index = -index;
        else if (index > 16) index = 16;

        double dScale = 0.0;
        if (dVal < 0) dScale = -scaleArray[index];
        else dScale = scaleArray[index];

        return dScale;
    }
}