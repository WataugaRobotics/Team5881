package org.firstinspires.ftc.team5881;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/** ClassDoc */
public class HW5881 {
    public DcMotor mtrFR = null;
    public DcMotor mtrFL = null;
    public DcMotor mtrBR = null;
    public DcMotor mtrBL = null;

    public void init() {
        mtrFR = hwMap.dcMotor.get("mtrFR");
        mtrFL = hwMap.dcMotor.get("mtrFL");
        mtrBR = hwMap.dcMotor.get("mtrBR");
        mtrBL = hwMap.dcMotor.get("mtrBL");
    }

    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap;
    }
}