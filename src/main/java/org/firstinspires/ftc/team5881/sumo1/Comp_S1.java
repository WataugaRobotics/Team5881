package org.firstinspires.ftc.team5881.sumo1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Comp_S1", group = "Auto")
// @Disabled
public class Comp_S1 extends LinearOpMode {
    HW_S1 robot = new HW_S1();

    // Constants

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        robot.colorSensor.enableLed(true);

        waitForStart();
            sleep(5000);
            robot.drive.move(0.5, 0.5);
            sleep(500);
            robot.drive.move(0, 0.5);
            sleep(500);
        while (opModeIsActive()) {
            robot.drive.move(0.1, 0.1);
            while (robot.colorSensor.alpha() < 20 && opModeIsActive()) {
                sleep(5);
            }
            robot.drive.move(-1, -1);
            sleep(250);
            robot.drive.move(0, 0.5);
            sleep(500);
        }
    }
}
