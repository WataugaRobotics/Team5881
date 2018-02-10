package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by robot on 1/13/2018.
 */

@Autonomous(name = "Blue Auto 2", group = "Autonomous")
public class Blue_Auto2 extends Comp_Auto2 {
    @Override
    public void init() {
        robot.init(hardwareMap);
        scannerUp();
    }

    @Override
    public void start() {
        scannerDown();
        if (getColor() == -1) {
            moveForTime(0.1, 0.1, 0.1, 0.1, 1);
            scannerUp();
            moveForTime(-0.1, -0.1, -0.1, -0.1, 1);
        } else if (getColor() == 1) {
            moveForTime(-0.1, -0.1, -0.1, -0.1, 1);
            scannerUp();
            moveForTime(0.1, 0.1, 0.1, 0.1, 1);
        } else {
            scannerUp();
        }
        moveForTime(-1, 0, -1, 0, 1);
        moveForTime(1, 0, 1, 0, 0.1);
    }
}
