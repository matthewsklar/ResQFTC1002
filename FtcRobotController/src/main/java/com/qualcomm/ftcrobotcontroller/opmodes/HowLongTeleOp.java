package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.util.Range;

/**
 * @author FTC 1002 (Matthew Sklar)
 * @version 2.0
 * @since 2016-5-1
 */
public class HowLongTeleOp extends HowLongHardware {
    /**
     * Wing servo values
     */
    private final double[] wingServoValues = { 0, .5, 1 };

    /**
     * Shield servo values
     */
    private final double[] shieldServoValues = { 0, .5, 1 };

    /**
     * Dunk servo values
     */
    private final double[] dunkServoValues = { .5, .7 };

    /**
     * Drive coefficient value
     */
    private double driveCoefficient;

    /**
     * Toggle buttons
     */
    private boolean reverseDriveButtonToggleOn, rightWingToggleOn, leftWingToggleOn, shieldToggleOn, dunkToggleOn, slowToggleOn;

    private float shieldValue = 0;

    private float sInterval = .1f;

    /**
     * 4-2 lift
     * 3-1 right front wheel
     * 3-2 left front wheel
     */

    boolean half = false;

    /**
     * Called when the robot initializes
     */
    @Override public void init() {
        super.init();

        FSS.setPosition(shieldServoValues[0]);
        BSS.setPosition(shieldServoValues[2]);

        RWS.setPosition(wingServoValues[0]);
        LWS.setPosition(wingServoValues[0]);

        Dunk.setPosition(dunkServoValues[0]);

        driveCoefficient = -1.0;

        reverseDriveButtonToggleOn = false;
        rightWingToggleOn = false;
        leftWingToggleOn = false;
        shieldToggleOn = false;
        dunkToggleOn = false;
        slowToggleOn = false;
    }

    /**
     * Loop repeatedly called during TeleOp while the robot is running.
     */
    @Override public void loop() {
        // Scale values from analog sticks into range readable by motors
        float rightDriveY = (float)scaleInput(Range.clip(gamepad1.right_stick_y, -1, 1));
        float leftDriveY = (float)scaleInput(Range.clip(gamepad1.left_stick_y, -1, 1));

        // Reverse drive button press status
        boolean reverseDrive = gamepad1.right_bumper; //Guess?

        // Hanging button press statuses
        boolean hangingButtonForward = gamepad1.a; //Guess?
        boolean hangingButtonBackward = gamepad1.b; //Guess?

        // Wing button press statuses
        boolean rightWingServo = gamepad2.b; //Guess?
        boolean leftWingServo = gamepad2.a; //Guess?

        // Shield button press status
        boolean shieldUpServo = gamepad2.right_bumper; //Guess?
        boolean shieldDownServo = gamepad2.left_bumper; //Guess?

        // Dunk button press status
        boolean dunkServo = gamepad2.left_bumper; //Guess?

        // Slow the drive
        boolean slowButton = gamepad1.left_bumper;

        // Pivot button press statuses
        boolean pivotUp = gamepad1.dpad_up; //Guess?
        boolean pivotDown = gamepad1.dpad_down; //Guess?

        // Reverse drive train direction toggle button
        if (reverseDrive) { //Guess?
            if (!reverseDriveButtonToggleOn) driveCoefficient *= -1.0;

            reverseDriveButtonToggleOn = true;
        } else reverseDriveButtonToggleOn = false;

        // Set the robot's drive motors' powers
        SetDrivePower(rightDriveY * driveCoefficient, leftDriveY * driveCoefficient);

        // Set the robot's hanging motors' power if the hanging button is pressed
        if (hangingButtonForward) SetHangingPower(hangingPower);
        else if (hangingButtonBackward) SetHangingPower(-hangingPower);
        else SetHangingPower(0);

        // Toggle wing servos
        if (rightWingServo) {
            if (!rightWingToggleOn) {
                if (RWS.getPosition() == wingServoValues[0]) RWS.setPosition(wingServoValues[1]);
                else RWS.setPosition(wingServoValues[0]);
            }

            rightWingToggleOn = true;
        } else rightWingToggleOn = false;

        if (leftWingServo) {
            if (!leftWingToggleOn) {
                if (LWS.getPosition() == wingServoValues[0]) LWS.setPosition(.4);
                else LWS.setPosition(wingServoValues[0]);
            }

            leftWingToggleOn = true;
        } else leftWingToggleOn = false;

        // Toggle shield servos
        if (shieldUpServo) {
            if (!shieldToggleOn) {
                if (shieldValue + sInterval <= 1) shieldValue += sInterval;
                /*if (FSS.getPosition() == shieldServoValues[0]) {
                    FSS.setPosition(shieldServoValues[1]);
                    BSS.setPosition(shieldServoValues[1]);
                } else {
                    FSS.setPosition(shieldServoValues[0]);
                    BSS.setPosition(shieldServoValues[2]);
                }*/

                shieldToggleOn = true;
            } else if (!shieldDownServo) shieldToggleOn = false;
        }

        if (shieldDownServo) {
            if (!shieldToggleOn) {
                if (shieldValue - sInterval >= 0) shieldValue -= sInterval;

                shieldToggleOn = true;
            }
        } else if (!shieldUpServo) shieldToggleOn = false;

        FSS.setPosition(shieldValue);
        BSS.setPosition(1 - shieldValue);

        if (slowButton) {
            if (!slowToggleOn) {
                if (half) driveCoefficient *= 2;
                else driveCoefficient /= 2;

                half = !half;

                slowToggleOn = true;
            }
        } else slowToggleOn = false;

        // Toggle dunk servos
        Dunk.setPosition(.5 +(gamepad2.right_trigger / 4) - gamepad2.left_trigger / 4);
        /*
        if (dunkServo) {
            if (!dunkToggleOn) {
                if (Dunk.getPosition() == dunkServoValues[1]) Dunk.setPosition(dunkServoValues[0]);
                else Dunk.setPosition(dunkServoValues[1]);

                dunkToggleOn = true;
            }
        } else dunkToggleOn = false;*/

        if (pivotUp) Pivot.setPower(.3);
        else if (pivotDown) Pivot.setPower(-.2);
        else Pivot.setPower(0);
    }

    /**
     * Called when the robot stops
     */
    @Override public void stop() { }

    /**
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    private double scaleInput(double dVal) {
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