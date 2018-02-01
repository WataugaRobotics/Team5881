package org.firstinspires.ftc.teamcode;

/*
*
*FTC 5881, Tungsteel
*2017
*
*/

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a holonomic drive bot with 4 motors only
 *
 * This hardware class assumes the following device names have been configured on the robot:
 *
 * Motor channel:  Right drive motor: "mtrR"
 * Motor channel:  Front drive motor: "mtrF"
 * Motor channel:  Back drive motor: "mtrB"
 * Motor channel:  Left drive motor: "mtrL"
 */
public class Hardware_Holo_4mtr_Plus
{
    /* Public OpMode members. */
    public DcMotor mtrF  = null;
    public DcMotor mtrR  = null;
    public DcMotor mtrB  = null;
    public DcMotor mtrL  = null;
    public DcMotor mtrLift  = null;
    public DcMotor mtrHolder  = null;
    public Servo holder   = null;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public Hardware_Holo_4mtr_Plus(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        mtrF = hwMap.dcMotor.get("mtrF");
        mtrR = hwMap.dcMotor.get("mtrR");
        mtrB = hwMap.dcMotor.get("mtrB");
        mtrL = hwMap.dcMotor.get("mtrL");
        mtrLift = hwMap.dcMotor.get("mtrLift");
        mtrHolder = hwMap.dcMotor.get("mtrHolder");
        holder = hwMap.servo.get("holder");

        //set direction of motors
        mtrF.setDirection(DcMotor.Direction.FORWARD);
        mtrR.setDirection(DcMotor.Direction.FORWARD);
        mtrB.setDirection(DcMotor.Direction.FORWARD);
        mtrL.setDirection(DcMotor.Direction.FORWARD);
        mtrLift.setDirection(DcMotor.Direction.FORWARD);
        mtrHolder.setDirection(DcMotor.Direction.FORWARD);
        holder.setDirection(Servo.Direction.FORWARD);

        // Set all motors to zero power
        mtrF.setPower(0);
        mtrR.setPower(0);
        mtrB.setPower(0);
        mtrL.setPower(0);
        mtrLift.setPower(0);
        mtrHolder.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        mtrF.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mtrR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mtrB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mtrL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /***
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  Length of wait cycle in mSec.
     */
    public void waitForTick(long periodMs) {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0) {
            try {
                Thread.sleep(remaining);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}


