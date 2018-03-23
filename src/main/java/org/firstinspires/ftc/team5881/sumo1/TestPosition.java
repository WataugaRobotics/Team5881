package org.firstinspires.ftc.team5881.sumo1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "TestPosition", group = "Auto")
// @Disabled
public class TestPosition extends LinearOpMode {
    HW_S1 robot = new HW_S1();

    // Constants

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        waitForStart();
        robot.drive.moveDistance(10, 0.2);
        while (opModeIsActive()) {
            idle();
        }
    }
}
