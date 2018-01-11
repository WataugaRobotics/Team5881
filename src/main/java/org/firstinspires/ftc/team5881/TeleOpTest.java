package org.firstinspires.ftc.team5881;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/** ClassDoc */
@TeleOp(name = "Test", group = "")
public class TeleOpTest extends OpMode {
    HW5881 robot = new HW5881();

    // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {
        robot.init(hardwareMap);
        telemetry.addData("Status", "Hardware Initialized");
    }

    // Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
    @Override
    public void init_loop() {}

    // Code to run ONCE when the driver hits PLAY
    @Override
    public void start() {}

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {
        // Telemetry?
    }

    // Code to run ONCE after the driver hits STOP
    @Override
    public void stop() {}
}
