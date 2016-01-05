package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.util.Range;

/**
 * @author FTC 1002 (Matthew Sklar)
 * @version 1.0
 * @since 2016-5-1
 */
public class HowLongTeleOp extends HowLongHardware {
    /**
     * Called when the robot initializes
     */
    @Override public void init() {
        super.init();
    }

    /**
     * Loop repeatedly called during TeleOp while the robot is running.
     */
    @Override public void loop() {
        // Scale values from analog sticks into range readable by motors
        float rightDriveY = (float)scaleInput(Range.clip(gamepad1.right_stick_y, -1, 1));
        float leftDriveY = (float)scaleInput(Range.clip(gamepad1.left_stick_y, -1, 1));

        // If the hanging button is pressed
        boolean hangingButton = gamepad1.a;

        // Set the robot's drive motors' powers
        SetDrivePower(rightDriveY, leftDriveY);

        // Set the robot's hanging motors' power if the hanging button is pressed
        SetHangingPower(hangingButton ? hangingPower : 0);
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
