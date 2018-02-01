package org.firstinspires.ftc.teamcode;

/*
*
* FTC 5881, Tungsteel
* 2017
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
 * This hardware class assumes the following device names have been configured on the robot:
 *
 * Motor channel:  Front Right drive motor: "mtrFR"
 * Motor channel:  Back Right drive motor: "mtrBR"
 * Motor channel:  Back Left drive motor: "mtrBL"
 * Motor channel:  Front Left drive motor: "mtrFL"

 */
public class Hardware_Holo_4mtr_X
{
    /* Public OpMode members. */
    public DcMotor mtrFR  = null;
    public DcMotor mtrBR  = null;
    public DcMotor mtrBL  = null;
    public DcMotor mtrFL  = null;
    public DcMotor mtrLift  = null;
    public DcMotor mtrHolder  = null;
    public Servo holder   = null;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public Hardware_Holo_4mtr_X(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        mtrFR = hwMap.dcMotor.get("mtrFR");
        mtrBR = hwMap.dcMotor.get("mtrBR");
        mtrBL = hwMap.dcMotor.get("mtrBL");
        mtrFL = hwMap.dcMotor.get("mtrFL");
        mtrLift = hwMap.dcMotor.get("mtrLift");
        mtrHolder = hwMap.dcMotor.get("mtrHolder");
        holder = hwMap.servo.get("holder");

        //Set motor direction
        mtrFR.setDirection(DcMotor.Direction.FORWARD);
        mtrBR.setDirection(DcMotor.Direction.FORWARD);
        mtrBL.setDirection(DcMotor.Direction.FORWARD);
        mtrFL.setDirection(DcMotor.Direction.FORWARD);
        mtrLift.setDirection(DcMotor.Direction.FORWARD);
        mtrHolder.setDirection(DcMotor.Direction.FORWARD);
        holder.setDirection(Servo.Direction.FORWARD);


        // Set all motors to zero power and servo to initial position
        mtrFR.setPower(0);
        mtrBR.setPower(0);
        mtrBL.setPower(0);
        mtrFL.setPower(0);
        mtrLift.setPower(0);
        mtrHolder.setPower(0);
        holder.setPosition(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        mtrFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mtrBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mtrBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mtrFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

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


