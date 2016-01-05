package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import java.lang.Override;
import java.util.Timer;
import java.util.TimerTask;

public class ShrTeOp extends OpMode {
    DcMotor wheelLeft, wheelRight;                  // Wheel Motors
    DcMotor catapult;                               // Catapult Motors
    // TODO fix back to with wheels when better motor controllers
    long catapultTime = 350;
    float catapultSpeed = 0.75f;

    boolean timerStarted = false;
    boolean timerReturnStarted = false;

    Timer timer;

    public ShrTeOp() { }

    @Override
    public void init() {
        //wheelRight = hardwareMap.dcMotor.get("motor_1");
        //wheelLeft = hardwareMap.dcMotor.get("motor_2");

        catapult = hardwareMap.dcMotor.get("motor_2");

        //wheelRight.setDirection(DcMotor.Direction.REVERSE);

        timer = new Timer();
    }

    @Override
    public void loop() {
        float leftY = gamepad1.left_stick_y;
        float rightY = (gamepad1.right_trigger - .5f) * 2;

        leftY = (float)scaleInput(Range.clip(leftY, -1, 1));
        rightY = (float)scaleInput(Range.clip(rightY, -1, 1));

        /*wheelLeft.setPower(leftY);
        wheelRight.setPower(rightY);*/

        if (gamepad1.y) {
            if (!timerStarted) {
                catapult.setPower(catapultSpeed);

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        catapult.setPower(0);

                        timerStarted = false;
                        timerReturnStarted = true;
                    }
                }, catapultTime);

                timerStarted = true;
            }
        }

        if (timerReturnStarted) {
            catapult.setPower(-catapultSpeed);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    catapult.setPower(0);
                }
            }, catapultTime);

            timerReturnStarted = false;
        }

        telemetry.addData("SLOTH", "Sid the Sloth");
        telemetry.addData("left tgt pwr", "left  pwr: " + String.format("%.2f", leftY));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", rightY));
    }

    @Override
    public void stop() { }

    /**
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

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