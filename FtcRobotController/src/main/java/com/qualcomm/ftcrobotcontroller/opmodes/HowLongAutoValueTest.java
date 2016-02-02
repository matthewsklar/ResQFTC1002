package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by CircuitRunnersFTC on 2/2/2016.
 */
public class HowLongAutoValueTest extends HowLongHardware {
    /**
     * Autonomous states
     */
    private enum AutonomousState { SETUP, TURN90, END;}

    /**
     * The current state of autonomous
     */
    private AutonomousState autonomousState;

    /**
     * Called when the robot initializes
     */
    @Override public void init() {
        super.init();

        autonomousState = AutonomousState.SETUP;
    }

    /**
     * Loop repeatedly called during TeleOp while the robot is running.
     */
    @Override public void loop() {
        switch (autonomousState) {
            case SETUP:
                ResetEncoders();

                autonomousState = AutonomousState.TURN90;
                break;
            case TURN90:
                RunWithEncoders();

                if (Turn(50, 90)) {
                    ResetEncoders();

                    autonomousState = AutonomousState.END;
                }
                break;
            case END:
                break;
            default:
                break;
        }

        telemetry.addData ("0", "Encoder: " + LeftEncoderCount());
    }

    /**
     * Called when the robot stops
     */
    @Override public void stop() { }
}
