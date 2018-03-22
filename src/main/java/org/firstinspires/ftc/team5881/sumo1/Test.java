package org.firstinspires.ftc.team5881.sumo1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Test", group = "Auto")
// @Disabled
public class Test extends LinearOpMode {
    HW_S1 robot = new HW_S1();

    // Constants

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        waitForStart();
        while (opModeIsActive()) {
            robot.drive.move(0.1, 0.1);
            sleep(2000);
            robot.drive.move(1, 1);
            sleep(2000);
        }
    }
}
