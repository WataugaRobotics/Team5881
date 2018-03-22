/**
 * Created by Maddie, FTC Team 4962, The Rockettes version 1.0 Aug 11, 2016
 *
 * <p>modified by Duncan, FTC 5881, Tungsteel version 1.1 Nov 27, 2016
 *
 * <p>modified by Ben, FTC 5881, Tungsteel version 1.2 Feb 10, 2018
 */
package org.firstinspires.ftc.team5881;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

/*
Team 5881, Tungsteel
2017

Robot wheel mapping:
X FRONT X
X       X
X FL FR X
X       X
X       X
X       X
X BL BR X
X       X
X BACK  X
*/
@TeleOp(name = "HoloX+Btns", group = "TeleOp")
@Disabled
public class Teleop_Holo_4mtr_X extends OpMode {
    HW5881_HoloX robot = new HW5881_HoloX();

    // Constants
    double REDUCED_DRIVE_SPEED = 0.3;
    double LIFT_SPEED_DOWN = 0.3;
    double LIFT_SPEED_UP = 0.5;
    double CLAW_SPEED = 0.3;

    @Override
    public void init() {
        // Initialize HW & Stop
        robot.init(hardwareMap);
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {}

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {}

    @Override
    public void loop() {
        // make sure color sensor servo is in safe position
        robot.jewelArm.setPosition(0);

        // Drive control
        // left stick controls direction
        // right stick X controls rotation
        // gamepad1 or 2 left bumper => hold for precision (slow) mode
        if ((gamepad1.left_bumper) || (gamepad2.left_bumper)) {
            robot.drive.move(
                    gamepad1.left_stick_x,
                    -gamepad1.left_stick_y,
                    gamepad1.right_stick_x,
                    REDUCED_DRIVE_SPEED);
        } else {
            robot.drive.move(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);
        }

        // Gamepad 1 or 2: a => lift down; y => lift up
        if ((gamepad1.a) || (gamepad2.a)) { // lift down
            robot.mtrLift.setPower(LIFT_SPEED_DOWN);
        } else if ((gamepad1.y) || (gamepad2.y)) { // lift up
            robot.mtrLift.setPower(-LIFT_SPEED_UP);
        } else {
            robot.mtrLift.setPower(0.0);
        }

        // Gamepad 1 or 2: x => open claw; b => close claw
        if ((gamepad1.x) || (gamepad2.x)) { // open arm
            robot.mtrHolderL.setPower(CLAW_SPEED);
            robot.mtrHolderR.setPower(CLAW_SPEED);
        } else if ((gamepad1.b) || (gamepad2.b)) { // close arm
            robot.mtrHolderL.setPower(-CLAW_SPEED);
            robot.mtrHolderR.setPower(-CLAW_SPEED);
        } else {
            robot.mtrHolderL.setPower(0.0);
            robot.mtrHolderR.setPower(0.0);
        }
    }

    @Override
    public void stop() {}
}
