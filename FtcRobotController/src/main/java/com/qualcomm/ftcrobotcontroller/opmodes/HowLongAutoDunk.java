package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by CircuitRunnersFTC on 1/13/2016.
 */
public class HowLongAutoDunk extends HowLongHardware {
    /**
     * Autonomous states
     */
    private enum AutonomousState { SETUP, MOVESTRAIGHT, DUNKSTART, DUNKEND, END;}

    private long original = 0;
    /**
     * The current state of autonomous
     */
    private AutonomousState autonomousState;

    /**
     * Called when the robot initializes
     */
    @Override public void init() {
        super.init();

        //Dunk.setPosition(0);

        autonomousState = AutonomousState.SETUP;
    }

    /**
     * Loop repeatedly called during TeleOp while the robot is running.
     */
    @Override public void loop() {
        switch (autonomousState) {
            case SETUP:
                //RunWithEncoders();
                //ResetEncoders();

                autonomousState = AutonomousState.MOVESTRAIGHT;
                break;
            case MOVESTRAIGHT:
                //Move(50, 5);
                //Stop();
                //ResetEncoders();
                SetDrivePower(.75, .75);
                if (original == 0) original = System.currentTimeMillis();

                if (System.currentTimeMillis() - original > 1800) {
                    Stop();
                    original = 0;
                    autonomousState = AutonomousState.DUNKSTART;
                }
                //telemetry.addData ("0", "Encoder: " + LeftEncoderCount());
                /*if (LeftEncoderCountReached(200)) {
                    Stop();


                    ResetEncoders();
                    autonomousState = AutonomousState.END;
                }*/

                break;
            case DUNKSTART:
                //Dunk.setPosition(.5);
                SetDrivePower(.75, -.75);
                if (original == 0) original = System.currentTimeMillis();

                if (System.currentTimeMillis() - original > 1350) {
                    stop();
                    original = 0;
                    autonomousState = AutonomousState.DUNKEND;

                }
                break;
            case DUNKEND:
                //Dunk.setPosition(0);
                SetDrivePower(.75, .75);
                if (original == 0) original = System.currentTimeMillis();

                if (System.currentTimeMillis() - original > 2500) {
                    stop();
                    original = 0;
                    autonomousState = AutonomousState.END;
                }
                break;
            case END:
                Stop();
                break;
            default:
                break;
        }
    }

    /**
     * Called when the robot stops
     */
    @Override public void stop() { }
}
