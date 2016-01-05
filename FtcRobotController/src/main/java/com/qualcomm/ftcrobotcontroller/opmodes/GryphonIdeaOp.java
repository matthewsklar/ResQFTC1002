package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Matthew on 10/5/2015.
 */
public class GryphonIdeaOp extends OpMode {
    DcMotor FRW, FLW, BRW, BLW;                     // Wheel Motors
    DcMotor hangingR, hangingL;                     // Hanging Motors

    @Override
    public void init() {
        // Initialize wheel motors
        FRW = hardwareMap.dcMotor.get("FRW");
        FLW = hardwareMap.dcMotor.get("FLW");
        BRW = hardwareMap.dcMotor.get("BRW");
        BLW = hardwareMap.dcMotor.get("BLW");

        // Initialize hanging motors
        hangingR = hardwareMap.dcMotor.get("hangingR");
        hangingL = hardwareMap.dcMotor.get("hangingL");

        // Reverse wheel motors on right side to correct flipping
        FRW.setDirection(DcMotor.Direction.REVERSE);
        BRW.setDirection(DcMotor.Direction.REVERSE);

        hangingR.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        float leftY = gamepad1.left_stick_y;
        float rightY = (gamepad1.right_trigger - .5f) * 2;

        boolean hangUp = gamepad1.a;
        boolean hangDown = gamepad1.x;

        leftY = (float)scaleInput(Range.clip(leftY, -1, 1));
        rightY = (float)scaleInput(Range.clip(rightY, -1, 1));

        if (hangUp) {
            hangingR.setPower(.5);
            hangingL.setPower(.5);
        } else if (hangDown) {
            hangingR.setPower(-.5);
            hangingL.setPower(-.5);
        } else {
            hangingR.setPower(0);
            hangingL.setPower(0);
        }

        FRW.setPower(rightY);
        FLW.setPower(leftY);
        BRW.setPower(rightY);
        BLW.setPower(leftY);
    }

    @Override
    public void stop() { }

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
