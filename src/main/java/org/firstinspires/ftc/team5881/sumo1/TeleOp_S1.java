package org.firstinspires.ftc.team5881.sumo1;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TeleOp_S1", group = "TeleOp")
// @Disabled
public class TeleOp_S1 extends OpMode {
    HW_S1 robot = new HW_S1();

    // Constants

    @Override
    public void init() {
        robot.init(hardwareMap); // Init HW & Stop
    }

    @Override
    public void init_loop() {}

    @Override
    public void start() {}

    @Override
    public void loop() {
        robot.drive.move(-gamepad1.left_stick_y, -gamepad1.right_stick_y);
    }

    @Override
    public void stop() {}
}
