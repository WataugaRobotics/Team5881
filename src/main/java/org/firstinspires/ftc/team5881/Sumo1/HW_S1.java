package org.firstinspires.ftc.team5881.Sumo1;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class HW_S1 {
    // OpMode members
    HardwareMap hwMap = null;

    public Tank drive = null;

    // Constructor
    public HW_S1() {}

    // Init hardware interfaces
    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap; // save reference to Hardware map
        drive = new Tank(hwMap, "mtrL", "mtrR");
        drive.init(DcMotor.ZeroPowerBehavior.BRAKE, DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
