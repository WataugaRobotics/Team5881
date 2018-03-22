package org.firstinspires.ftc.team5881.sumo1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "TestPosition", group = "Auto")
// @Disabled
public class TestPosition extends LinearOpMode {
    HW_S1 robot = new HW_S1();

    // Constants

    @Override
    public void runOpMode() {
        DcMotor left = hardwareMap.dcMotor.get("mtrL");

        waitForStart();
        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        left.setTargetPosition(560 * 2);
        left.setPower(50);

        while (opModeIsActive()) {
            idle();
        }
    }
}
