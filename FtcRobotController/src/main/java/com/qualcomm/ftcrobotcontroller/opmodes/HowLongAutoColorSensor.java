package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * @author FTC 1002 (Matthew Sklar)
 * @version 1.0
 * @since 2016-1-2
 */
public class HowLongAutoColorSensor extends HowLongHardware {
    /**
     * Autonomous states
     */
    private enum AutonomousState {
        SETUP,
        MOVEFIRSTBLOCK,
        TURNTOWARDBOX, MOVETOWARDBOX, TURNPARALLELTOBOX, MOVETOFIRSTBOX,
        DUNK, FIRSTCOLOR, SECONDCOLOR, PUSHBUTTON,
        MOVETOSECONDCOLOR, MOVEBACKTOFIRSTCOLOR,
        BACKUP, BACKTURN, REVERSE, TURNTOWARDRAMP, MOVEUPRAMP, END;
    }

    /**
     * RGB value of corresponding color needed to be recognized as the color
     */
    private final int THRESHOLD = 150;

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

                autonomousState = AutonomousState.MOVEFIRSTBLOCK;
                break;
            case MOVEFIRSTBLOCK:
                RunWithEncoders();

                if (Move(75, 1000)) {   //Guess?
                    ResetEncoders();

                    autonomousState = AutonomousState.TURNTOWARDBOX;
                }
                break;
            case TURNTOWARDBOX:
                RunWithEncoders();

                if (Turn(50, 45)) {
                    ResetEncoders();

                    autonomousState = AutonomousState.MOVETOWARDBOX;
                }
                break;
            case MOVETOWARDBOX:
                RunWithEncoders();

                if (Move(75, 3000)) {   //Guess?
                    ResetEncoders();

                    autonomousState = AutonomousState.TURNPARALLELTOBOX;
                }
                break;
            case TURNPARALLELTOBOX:
                RunWithEncoders();

                if (Turn(50, -45)) {
                    ResetEncoders();

                    autonomousState = AutonomousState.MOVETOFIRSTBOX;
                }
                break;
            case MOVETOFIRSTBOX:
                RunWithEncoders();

                if (Move(75, 500)) {
                    ResetEncoders();

                    autonomousState = AutonomousState.DUNK;
                }
                break;
            case DUNK:
                Dunk.setPosition(.5);

                customWait(3000);

                Dunk.setPosition(-.5);

                customWait(2750);

                Dunk.setPosition(0);

                autonomousState = AutonomousState.FIRSTCOLOR;

                break;
            case FIRSTCOLOR:
                if (CorrectColor(0, THRESHOLD)) autonomousState = AutonomousState.PUSHBUTTON;
                else autonomousState = AutonomousState.MOVETOSECONDCOLOR;

                break;
            case PUSHBUTTON:
                // TODO: Add code
                break;
            case MOVETOSECONDCOLOR:
                RunWithEncoders();

                if (Move(50,1000)) {
                    ResetEncoders();
                    autonomousState = AutonomousState.SECONDCOLOR;
                }
                break;
            case SECONDCOLOR:
                if (CorrectColor(1, THRESHOLD)) autonomousState = AutonomousState.PUSHBUTTON;
                else autonomousState = AutonomousState.MOVEBACKTOFIRSTCOLOR;

                break;
            case MOVEBACKTOFIRSTCOLOR:
                RunWithEncoders();

                if (Move(-50, 1000)) {
                    ResetEncoders();
                    autonomousState = AutonomousState.BACKUP;
                }
                break;
            case BACKUP:
                RunWithEncoders();

                if (Move(-50, 500)) {
                    ResetEncoders();
                    autonomousState = AutonomousState.BACKTURN;
                }
                break;
            case BACKTURN:
                RunWithEncoders();

                if (Turn(50, 45)) {
                    ResetEncoders();

                    autonomousState = AutonomousState.REVERSE;
                }
                break;
            case REVERSE:
                RunWithEncoders();

                if (Move(-50, 500)) {
                    ResetEncoders();
                    autonomousState = AutonomousState.BACKTURN;
                }
                break;
            case TURNTOWARDRAMP:
                RunWithEncoders();

                if (Turn(50, 90)) {
                    ResetEncoders();

                    autonomousState = AutonomousState.MOVEUPRAMP;
                }
                break;
            case MOVEUPRAMP:
                RunWithEncoders();

                if (Move (50, 1000)) {
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
