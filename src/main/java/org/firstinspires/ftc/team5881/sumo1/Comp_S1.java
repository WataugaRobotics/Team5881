package org.firstinspires.ftc.team5881.sumo1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

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
        // Drive out of box, turn
        sleep(5000);
        robot.drive.moveDistance(0.75, 0.25);
        robot.drive.init(DcMotor.ZeroPowerBehavior.FLOAT, DcMotor.RunMode.RUN_USING_ENCODER);
        robot.drive.move(-0.5, 0.5);
        sleep(300);

        // Strike, back, turn, repeat
        while (opModeIsActive()) {
            robot.drive.move(0.2, 0.2);
            while (robot.colorSensor.alpha() < 20 && opModeIsActive()) {
                sleep(2);
            }
            robot.drive.move(-1, -1);
            sleep(250);
            robot.drive.move(-0.5, 0.5);
            sleep(300);
        }
    }
}
