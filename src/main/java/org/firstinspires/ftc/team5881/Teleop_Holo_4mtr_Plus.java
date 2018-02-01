/**
 *
 * Created by Maddie, FTC Team 4962, The Rockettes
 * version 1.0 Aug 11, 2016
 *
 * modified by Duncan, FTC 5881, Tungsteel
 * version 1.1 Nov 27, 2016
 *
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;


/*Team 5881, Tungsteel
2017

Robot wheel mapping:
X FRONT X
X   F    X
X        X
X        X
X L    R X
X        X
X        X
X   B    X
X BACK  X
*/
@TeleOp(name = "Holonomic 4mtr Plus", group = "Drive Only")
//@Disabled
public class Teleop_Holo_4mtr_Plus extends OpMode {

    Hardware_Holo_4mtr_Plus robot = new Hardware_Holo_4mtr_Plus();

    double clawPos = 0;
    final double CLAW_SPEED = 0.005;

    @Override
    public void init() {

        robot.init(hardwareMap);
  /*
  * Use the hardwareMap to get the hardware by name. Note
  * that the names of the devices must match the names used when you
  * configured your robot and created the configuration file.
  */

    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        robot.holder.setPosition(0);

    }

    @Override
    public void loop() {


        // left stick controls direction
        // right stick X controls rotation

        float gamepad1LeftY = -gamepad1.left_stick_y;
        float gamepad1LeftX = gamepad1.left_stick_x;
        float gamepad1RightX = gamepad1.right_stick_x;
        float speed_reduction = 1f;

        //((gamepad1LeftY<0.5) && (gamepad1LeftY>-0.5))&&((gamepad1LeftX<0.5) && (gamepad1LeftX>-0.5))&&((gamepad1RightX<0.5) && (gamepad1RightX>-0.5))
        if (gamepad1.left_bumper) {
            speed_reduction = 0.3f;
        } else {
            speed_reduction = 1f;
        }


        // holonomic formulas
        float Front = (- gamepad1LeftX - gamepad1RightX)*speed_reduction;
        float Right = (gamepad1LeftY - gamepad1RightX)*speed_reduction;
        float Back =  (gamepad1LeftX - gamepad1RightX)*speed_reduction;
        float Left = (- gamepad1LeftY - gamepad1RightX)*speed_reduction;

        // clip the right/left values so that the values never exceed +/- 1
        Front = Range.clip(Front, -1, 1);
        Right = Range.clip(Right, -1, 1);
        Back = Range.clip(Back, -1, 1);
        Left = Range.clip(Left, -1, 1);

        // write the values to the motors
        robot.mtrF.setPower(Front);
        robot.mtrR.setPower(Right);
        robot.mtrB.setPower(Back);
        robot.mtrL.setPower(Left);
        robot.mtrLift.setPower(0.0);
        robot.mtrHolder.setPower(0.0);

        if (gamepad1.a)
            robot.mtrLift.setPower(0.3);
        else if (gamepad1.y)
            robot.mtrLift.setPower(-0.5);
        else
            robot.mtrLift.setPower(0.0);

        if (gamepad1.x && robot.holder.getPosition() < 1 - CLAW_SPEED) {
            clawPos += CLAW_SPEED;
        } else if (gamepad1.b && robot.holder.getPosition() > 0 + CLAW_SPEED) {
            clawPos -= CLAW_SPEED;
        }
        robot.holder.setPosition(clawPos);

        if (gamepad1.x)
            robot.mtrHolder.setPower(0.5);
        else if (gamepad1.b)
            robot.mtrHolder.setPower(-0.5);
        else
            robot.mtrHolder.setPower(0.0);

    }



    @Override
    public void stop() {

    }
}

