package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by CircuitRunnersFTC on 1/13/2016.
 */
public class HowLongAutonomous extends HowLongHardware {
    /**
     * Autonomous states
     */
    private enum AutonomousState { SETUP, MOVETOBUTTON, TURNPARALLELTOBUTTON, FIRSTCOLORSCAN, SECONDCOLORSCAN, TURNTOWARDBUTTON, MOVETOSECONDBUTTON, PRESSBUTTON, END;}
    /**
     * Alliance color to help with knowing which color to scan (0:blue; 1:red)
     */
    private final int allianceColor = 0;

    /**
     * Alliance color to help with knowing which color to scan (0:blue; 1:red)
     */
    private final int enemyColor = 1;

    /**
     * The RGB value of a color required to recognize it as the color.
     */
    private final int colorThreshold = 150; //Guess?

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
                RunWithEncoders();
                ResetEncoders();

                autonomousState = AutonomousState.MOVETOBUTTON;
                break;
            case MOVETOBUTTON:
                Move(50, 1000); //Guess?
                Stop();
                ResetEncoders();

                autonomousState = AutonomousState.TURNPARALLELTOBUTTON;
                break;
            case TURNPARALLELTOBUTTON:
                Turn(50, 45); //Guess?
                Stop();
                ResetEncoders();

                autonomousState = AutonomousState.FIRSTCOLORSCAN;
                break;
            case FIRSTCOLORSCAN:
                if (CorrectColor(allianceColor, colorThreshold)) {
                    autonomousState = AutonomousState.TURNTOWARDBUTTON;
                } else autonomousState = AutonomousState.MOVETOSECONDBUTTON;
                break;
            case SECONDCOLORSCAN:
                if (CorrectColor(enemyColor, colorThreshold)) {
                    autonomousState = AutonomousState.TURNTOWARDBUTTON;
                } else autonomousState = AutonomousState.END;
                break;
            case TURNTOWARDBUTTON:
                Turn(50, 45); //Guess?
                Stop();
                ResetEncoders();

                autonomousState = AutonomousState.PRESSBUTTON;
                break;
            case MOVETOSECONDBUTTON:
                Move(50, 100); //Guess?
                Stop();
                ResetEncoders();

                autonomousState = AutonomousState.SECONDCOLORSCAN;
                break;
            case PRESSBUTTON:
                Move(50, 100); //Guess?
                Stop();
                ResetEncoders();

                autonomousState = autonomousState.END;
                break;
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