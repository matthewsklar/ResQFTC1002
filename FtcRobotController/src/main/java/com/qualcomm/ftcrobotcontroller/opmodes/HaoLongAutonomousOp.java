package com.qualcomm.ftcrobotcontroller.opmodes;

import android.view.ViewDebug;

import java.util.concurrent.TimeUnit;

/**
 * @author FTC 1002
 * @version 1.1
 * @since 2015-11-30
 */
public class HaoLongAutonomousOp extends HaoLongHardware {
    private int encoderPer90 = 100;
    long startTime = System.currentTimeMillis();

    @Override public void start() {
        super.start();
    }

    @Override public void loop() {
        if (((System.currentTimeMillis()) - startTime) >= 10000) Stop();
        else SetDrivePower(.75f, .75f);

       /* telemetry.addData("Left Encoder Value", LeftEncoderCount());
        telemetry.addData("Right Encoder Value", RightEncoderCount());
        switch (state) {
            case START:
                break;
            case MOVEAROUNDSTARTCORNER:
                CheckEncoders();

                Move(.5, 3, State.TURNAROUNDSTARTCORNER);
                break;
            case TURNAROUNDSTARTCORNER:
                CheckEncoders();

                if (!LeftEncoderCountReached(TurnEncoderValueGoal(45))) {
                    Turn(45, -1);
                } else {
                    Stop();
                    encoder = false;
                    ResetEncoders();
                    state = State.MOVEPARALLELTOPARKINGRAMP;
                }
                break;
            case MOVEPARALLELTOPARKINGRAMP:
                CheckEncoders();

                Move(.5, 10, State.TURNTOWARDPARKINGRAMP);
                /*if (!LeftEncoderCountReached(10)) {
                    Move(.5);
                } else {
                    Stop();
                    encoder = false;
                    ResetEncoders();
                    state = State.TURNTOWARDPARKINGRAMP;
                }*/
              /*  break;
            case TURNTOWARDPARKINGRAMP:
                CheckEncoders();

                if (!LeftEncoderCountReached(TurnEncoderValueGoal(90))) {
                    Turn(90, -1);
                } else {
                    Stop();
                    encoder = false;
                    ResetEncoders();
                    state = State.MOVEUPRAMP;
                }
                break;
            case MOVEUPRAMP:
                CheckEncoders();

                Move(.5, 10, State.DONOTHING);
                break;
            default:
                break;
        }*/

    }

    private void CheckEncoders() {
        if (!encoder) {
            encoder = true;
            RunWithEncoders();
        }
    }

    private int TurnEncoderValueGoal(int degrees) {
        return Math.round((encoderPer90 / 90) * degrees);
    }
}