package org.firstinspires.ftc.team5881;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

/** Created by benbroce on 2/10/18. */
public class HoloX {
    private DcMotor mtrFR = null;
    private DcMotor mtrBR = null;
    private DcMotor mtrBL = null;
    private DcMotor mtrFL = null;

    public HoloX(HardwareMap hwMap, String mtrFR, String mtrFL, String mtrBR, String mtrBL) {
        this.mtrFR = hwMap.dcMotor.get("mtrFR");
        this.mtrFL = hwMap.dcMotor.get("mtrFL");
        this.mtrBR = hwMap.dcMotor.get("mtrBR");
        this.mtrBL = hwMap.dcMotor.get("mtrBL");
    }

    public void move(double x, double y, double c) {
        move(x, y, c, 1.0);
    }

    public void move(double x, double y, double c, double reduction) {
        // drive system holonomic formulas (w/ clipping)
        mtrFR.setPower(Range.clip((y - x - c), -reduction, reduction));
        mtrBR.setPower(Range.clip((y + x - c), -reduction, reduction));
        mtrFL.setPower(Range.clip((-y - x - c), -reduction, reduction));
        mtrBL.setPower(Range.clip((-y + x - c), -reduction, reduction));
    }

    public void stop() {
        mtrFR.setPower(0);
        mtrBR.setPower(0);
        mtrBL.setPower(0);
        mtrFL.setPower(0);
    }

    public void init() {
        // Set motor direction
        mtrFR.setDirection(DcMotor.Direction.FORWARD);
        mtrFL.setDirection(DcMotor.Direction.FORWARD);
        mtrBR.setDirection(DcMotor.Direction.FORWARD);
        mtrBL.setDirection(DcMotor.Direction.FORWARD);

        // Set zero-power behavior (BRAKE or FLOAT)
        mtrFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        mtrBR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        mtrBL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        mtrFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        mtrFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mtrBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mtrBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mtrFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Set all motors to zero power
        stop();
    }
}
