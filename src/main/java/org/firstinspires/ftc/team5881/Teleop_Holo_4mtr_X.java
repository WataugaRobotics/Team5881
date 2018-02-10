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
X       X
X FL FR X
X       X
X       X
X       X
X BL BR X
X       X
X BACK  X
*/
@TeleOp(name = "Holo 4mtr X", group = "Drive Only")
//@Disabled
public class Teleop_Holo_4mtr_X extends OpMode {


    Hardware_Holo_4mtr_X robot = new Hardware_Holo_4mtr_X();

    //double clawPos = 0;
    //final double CLAW_SPEED = 0.005;

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
    }
    float speed_reduction = 1f;
    @Override
    public void loop() {

        // left stick controls direction
        // right stick X controls rotation
        float gamepad1LeftY = -gamepad1.left_stick_y;
        float gamepad1LeftX = gamepad1.left_stick_x;
        float gamepad1RightX = gamepad1.right_stick_x;
        //float speed_reduction;

        //make sure color sensor servo is in safe position
        robot.holder.setPosition(0);

        //drive speed reduction
        //set by fine control using joysticks
        /*if (((gamepad1LeftY<0.7) && (gamepad1LeftY>-0.7))&&((gamepad1LeftX<0.7) && (gamepad1LeftX>-0.7))&&((gamepad1RightX<0.7) && (gamepad1RightX>-0.7))) {
                 speed_reduction = 0.3f;  //see range clip below, sets upper and lower bound on motor
        }else    speed_reduction = 1f;
        */
        //allow user to set a speed reduction
        /*this resets too quickly
        if ((gamepad1.left_bumper) && (speed_reduction==0.3)) {
            speed_reduction = 1f;
        } else if ((gamepad1.left_bumper) && (speed_reduction==1)) {
            speed_reduction = 0.3f;
        }
        */
        //user of gamepad1 or 2 may hold bumper down for slow speed
        if ((gamepad1.left_bumper) || (gamepad2.left_bumper)) {
            speed_reduction = 0.3f;
        } else
            speed_reduction = 1f;

        //drive system holonomic formulas
        float FrontRight = (gamepad1LeftY - gamepad1LeftX - gamepad1RightX);
        float BackRight = (gamepad1LeftY + gamepad1LeftX - gamepad1RightX);
        float BackLeft = (-gamepad1LeftY + gamepad1LeftX - gamepad1RightX);
        float FrontLeft = (-gamepad1LeftY - gamepad1LeftX - gamepad1RightX);

        // clip the right/left values so that the values never exceed +/- 1
        FrontRight = Range.clip(FrontRight, -speed_reduction, speed_reduction);
        BackRight = Range.clip(BackRight, -speed_reduction, speed_reduction);
        BackLeft = Range.clip(BackLeft, -speed_reduction, speed_reduction);
        FrontLeft = Range.clip(FrontLeft, -speed_reduction, speed_reduction);

        // write the values to the motors
        robot.mtrFR.setPower(FrontRight);
        robot.mtrBR.setPower(BackRight);
        robot.mtrBL.setPower(BackLeft);
        robot.mtrFL.setPower(FrontLeft);
        //robot.mtrLift.setPower(0.0);
      //  robot.mtrHolder.setPower(0.0);

        //lifter system
        if ((gamepad1.a)||(gamepad2.a)) {//lift down
            robot.mtrLift.setPower(0.3);
        }else if ((gamepad1.y)||(gamepad2.y)) { //lift up
            robot.mtrLift.setPower(-0.5);
        }else
            robot.mtrLift.setPower(0.0);

        //glyph pincher system
        if ((gamepad1.x)||(gamepad2.x)) {  //open arm
            robot.mtrHolder.setPower(0.3);
        }else if ((gamepad1.b)||(gamepad2.b)) {  //close arm
            robot.mtrHolder.setPower(-0.3);
        }else
            robot.mtrHolder.setPower(0.0);


        //telemetry.addData("FrontRight",  "%.2f", FrontRight);
        //telemetry.addData("BackRight", "%.2f", BackRight);
        //telemetry.addData("FrontLeft",  "%.2f", FrontLeft);
        //telemetry.addData("BackLeft", "%.2f", BackLeft);
        telemetry.addData("speed_reduction", "%.2f", speed_reduction);
        telemetry.addData("gamepad1LeftY", "%.2f", gamepad1LeftY);
        telemetry.addData("gamepad1LeftX", "%.2f", gamepad1LeftX);
        telemetry.addData("gamepad1RightX", "%.2f", gamepad1RightX);
    }

    @Override
    public void stop() {

    }
}

