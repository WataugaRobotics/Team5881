package org.firstinspires.ftc.team5881;

/*
 * FTC 5881, Tungsteel
 * 2018
 */

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 *
 * <p>This class can be used to define all the specific hardware for a single robot. In this case
 * that robot is a holonomic drive bot with 4 motors only This hardware class assumes the following
 * device names have been configured on the robot:
 *
 * <p>Motor channel: Front Right drive motor: "mtrFR" Motor channel: Back Right drive motor: "mtrBR"
 * Motor channel: Back Left drive motor: "mtrBL" Motor channel: Front Left drive motor: "mtrFL"
 */
public class HW5881_HoloX {
    /* OpMode members. */
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    public DcMotor mtrLift = null;
    public DcMotor mtrHolderL = null;
    public DcMotor mtrHolderR = null;
    public Servo jewelArm = null;
    public HoloX drive = new HoloX(hwMap, "mtrFR", "mtrFL", "mtrBR", "mtrBL");

    /* Constructor */
    public HW5881_HoloX() {}

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        drive.init();
        mtrLift = hwMap.dcMotor.get("mtrLift");
        mtrHolderL = hwMap.dcMotor.get("mtrHolderL");
        mtrHolderR = hwMap.dcMotor.get("mtrHolderR");
        jewelArm = hwMap.servo.get("jewelArm");

        // Set motor direction
        mtrLift.setDirection(DcMotor.Direction.FORWARD);
        mtrHolderL.setDirection(DcMotor.Direction.FORWARD);
        mtrHolderR.setDirection(DcMotor.Direction.FORWARD);
        jewelArm.setDirection(Servo.Direction.FORWARD);

        // Set zero-power behavior (BRAKE or FLOAT)
        mtrLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mtrHolderL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mtrHolderR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        mtrLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mtrHolderL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mtrHolderR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Set all motors to zero power and servo to initial position
        mtrLift.setPower(0);
        mtrHolderL.setPower(0);
        mtrHolderR.setPower(0);
        jewelArm.setPosition(0);
    }
}
