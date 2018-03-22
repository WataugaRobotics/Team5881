package org.firstinspires.ftc.team5881.Sumo1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name = "Test", group = "Auto")
// @Disabled
public class Test extends OpMode {
    HW_S1 robot = new HW_S1();

    // Constants

    @Override
    public void init() {
        robot.init(hardwareMap); // Init HW & Stop
    }

    @Override
    public void init_loop() {}

    @Override
    public void start() {
        robot.drive.move(10, 10);
    }

    @Override
    public void loop() {}

    @Override
    public void stop() {}
}
