package org.firstinspires.ftc.team5881;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/** ClassDoc */
@TeleOp(name="Hello", group="")
public class TeleOp extends OpMode {
    HW8190 robot = new HW8190();

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
