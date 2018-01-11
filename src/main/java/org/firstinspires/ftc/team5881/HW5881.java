package org.firstinspires.ftc.team5881;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/** ClassDoc */
public class HW5881 {
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap;
    }
}