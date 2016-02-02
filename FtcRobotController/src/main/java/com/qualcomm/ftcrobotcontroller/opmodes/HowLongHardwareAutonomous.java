package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * @author FTC 1002 (Matthew Sklar)
 * @version 1.0
 * @since 2016-1-2
 */
public class HowLongHardwareAutonomous extends LinearOpMode {
    /**
     * Encoder count for a 90 degree turn
     */
    private final int degreesPer90 = 1500; //Guess?

    /**
     * Wheel Motors
     */
    public DcMotor FRW, FLW, BRW, BLW;

    /**
     * Hanging Motors
     */
    public DcMotor RHM, LHM;

    /**
     * Wing Servos
     */
    public Servo RWS, LWS;

    /**
     * Shield Servos
     */
    public Servo FSS, BSS;

    /**
     * Dunk Servos
     */
    public Servo Dunk;

    /**
     * Hanging power
     */
    public double hangingPower;

    /**
     * Color sensor
     */
    public ColorSensor colorSensor;

    /**
     * Called when the robot initializes
     */
    public void Initialize() {
        // Initialize wheel motors
        FRW = hardwareMap.dcMotor.get("FRW");
        FLW = hardwareMap.dcMotor.get("FLW");
        BRW = hardwareMap.dcMotor.get("BRW");
        BLW = hardwareMap.dcMotor.get("BLW");

        // Initialize hanging motors
        RHM = hardwareMap.dcMotor.get("RHM");
        //LHM = hardwareMap.dcMotor.get("LHM");

        // Initialize wing servos
        RWS = hardwareMap.servo.get("RWS");
        LWS = hardwareMap.servo.get("LWS");

        // Initialize shield servos
        FSS = hardwareMap.servo.get("FSS");
        BSS = hardwareMap.servo.get("BSS");

        // Initialize dunk servo
        Dunk = hardwareMap.servo.get("Dunk");
/*
        // Initialize color sensor
        colorSensor = hardwareMap.colorSensor.get("CS");
*/
        // Reverse wheel motors on right side to correct flipping
        FLW.setDirection(DcMotor.Direction.REVERSE);
        BRW.setDirection(DcMotor.Direction.REVERSE);

        // Reverse RWS to correct flipping
        RWS.setDirection(Servo.Direction.REVERSE);

        // Reverse FSS to correct flipping
        FSS.setDirection(Servo.Direction.REVERSE);

        // Set the hanging power
        hangingPower = 1.0;                         // Guess
    }

    @Override public void runOpMode() throws InterruptedException {
        Initialize();

        waitForStart();


    }
}