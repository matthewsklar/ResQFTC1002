package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by CircuitRunnersFTC on 1/13/2016.
 */
public class HowLongAutoBlueClose extends HowLongHardware {
    /**
     * Autonomous states
     */
    private enum AutonomousState { SETUP, TEST, FIRSTMOVE, FIRSTTURN, SECONDMOVE, SECONDTURN, MOVEUPRAMP, END;}

    /**
     * The current state of autonomous
     */
    private AutonomousState autonomousState;

    /**
     * Called when the robot initializes
     */
    @Override public void init() {
        super.init();

        autonomousState = AutonomousState.TEST;
    }

    /**
     * Loop repeatedly called during TeleOp while the robot is running.
     */
    @Override public void loop() {
        switch (autonomousState) {
            case SETUP:
                RunWithEncoders();
                ResetEncoders();

                autonomousState = AutonomousState.FIRSTMOVE;
                break;
            case TEST:
                RunWithEncoders();
                ResetEncoders();

                Turn(50, 90);

                autonomousState = AutonomousState.END;
            case FIRSTMOVE:
                Move(50, 1000); //Guess?
                Stop();
                ResetEncoders();

                autonomousState = AutonomousState.FIRSTTURN;
                break;
            case FIRSTTURN:
                Turn(50, 45);
                Stop();
                ResetEncoders();

                autonomousState = AutonomousState.SECONDMOVE;
            case SECONDMOVE:
                Move(50, 500); //Guess?
                Stop();
                ResetEncoders();

                autonomousState = AutonomousState.SECONDTURN;
            case SECONDTURN:
                Turn(50, 90);
                Stop();
                ResetEncoders();

                autonomousState = AutonomousState.MOVEUPRAMP;
            case MOVEUPRAMP:
                Move(50, 1000);
                Stop();
                ResetEncoders();

                autonomousState = AutonomousState.END;
            case END:
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
