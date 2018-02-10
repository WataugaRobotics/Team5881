/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * This file illustrates the concept of driving a path based on time.
 * <p>
 * The code is structured as a LinearOpMode
 * <p>
 * Start positions are laid out with the relic drop zones, looking down, and proceeding
 * clockwise
 * Two Drop Zone Mats
 * Position 4                Position 1
 * Position 3                Position 2
 */

@Autonomous(name = "Auto_Position_1_Blue", group = "Autonomous")
//@Disabled
public class Auto_Position_1_Blue extends LinearOpMode {

    /* Declare OpMode members. */
    Hardware_Holo_4mtr_X robot = new Hardware_Holo_4mtr_X();
    private ElapsedTime runtime = new ElapsedTime();

    float DRIVE_SPEED = 1f;
    float PIVOT_SPEED = 0.2f; //this is the factor that adjusts the speed for pivoting to knock the jewel ball off in auto

    //For blue ball
    //float red_high = 20;
    float red_low = 10;
    //float blue_high = 55;
    float blue_low = 10;
    //float green_high = 40;
    //float green_low = 11;

    boolean blue_found = false;
    boolean red_found = false;
    double gamepad1LeftY = (0);  //keep the first negative 1 to be consistent with gamepad operation
    double gamepad1LeftX = 0;
    double gamepad1RightX = (0);
    double FrontRight = -gamepad1LeftX - gamepad1RightX;
    double FrontLeft = gamepad1LeftY - gamepad1RightX;
    double BackRight = gamepad1LeftX - gamepad1RightX;
    double BackLeft = -gamepad1LeftY - gamepad1RightX;

    ColorSensor sensorColor;

    @Override
    public void runOpMode() {

        // get a reference to the color sensor.
        sensorColor = hardwareMap.get(ColorSensor.class, "sensorColor");

        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F, 0F, 0F};

        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

        // sometimes it helps to multiply the raw RGB values with a scale factor
        // to amplify/attentuate the measured values.
        final double SCALE_FACTOR = 255;

        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);
        robot.holder.setPosition(0);


        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // convert the RGB values to HSV values.
        // multiply by the SCALE_FACTOR.
        // then cast it back to int (SCALE_FACTOR is a double)
        Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                (int) (sensorColor.green() * SCALE_FACTOR),
                (int) (sensorColor.blue() * SCALE_FACTOR),
                hsvValues);
        // Step through each leg of the path, ensuring that the Auto mode has not been stopped along the way

        //combination of drive motors based on simulated gamepad entry
        // left stick controls direction
        // right stick X controls rotation
/***********************************************************************/
//Step move servo down
        double Step_Time = 4;
        //Set servo down so the sensor can read the color
        robot.holder.setPosition(0.8);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < Step_Time)) {
            telemetry.addData("Move servo down", "%2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

/***********************************************************************/
//Step sense color
        Step_Time = 5;  //seconds
        //if ((sensorColor.red()>red_low) && (sensorColor.red()<red_high) && (sensorColor.blue()>blue_low) && (sensorColor.blue()<blue_high) && (sensorColor.green()>green_low) && (sensorColor.green()<green_high))
        if ((sensorColor.red() > red_low) && (sensorColor.red() > sensorColor.blue())) {
            red_found = true;
            blue_found = false;
        } else if ((sensorColor.blue() > blue_low) && (sensorColor.red() < sensorColor.blue())) {
            blue_found = true;
            red_found = false;
        } else {
            red_found = false;
            blue_found = false;
        }

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < Step_Time)) {
            telemetry.addData("Sensing color", "%2.5f S Elapsed", runtime.seconds());
            telemetry.addData("Red  ", sensorColor.red());
            telemetry.addData("Green", sensorColor.green());
            telemetry.addData("Blue ", sensorColor.blue());
            telemetry.addData("blue_found  ", blue_found);
            telemetry.addData("red_found  ", red_found);
            telemetry.update();
        }

/***********************************************************************/
//Step move ball off
        Step_Time = 0.1;  //seconds

        //simulate gamepad entries
        gamepad1LeftY = (0);  //keep the first negative 1 to be consistent with gamepad operation
        gamepad1LeftX = 0;
        if ((blue_found) && (red_found == false)) {
            gamepad1RightX = (-PIVOT_SPEED) * DRIVE_SPEED;  //knock off red ball
        } else if ((red_found) && (blue_found == false)) {
            gamepad1RightX = (PIVOT_SPEED) * DRIVE_SPEED;  //knock off red ball
        } else {  //uncertain, do nothing
            gamepad1RightX = 0;}


        // holonomic formulas
        FrontRight = (gamepad1LeftY - gamepad1LeftX - gamepad1RightX);
        BackRight = (gamepad1LeftY + gamepad1LeftX - gamepad1RightX);
        BackLeft = (-gamepad1LeftY + gamepad1LeftX - gamepad1RightX);
        FrontLeft = (-gamepad1LeftY - gamepad1LeftX - gamepad1RightX);

        // clip the right/left values so that the values never exceed +/- 1
        FrontRight = Range.clip(FrontRight, -1, 1);
        FrontLeft = Range.clip(FrontLeft, -1, 1);
        BackRight = Range.clip(BackRight, -1, 1);
        BackLeft = Range.clip(BackLeft, -1, 1);

        // write the values to the motors
        robot.mtrFR.setPower(FrontRight);
        robot.mtrFL.setPower(FrontLeft);
        robot.mtrBR.setPower(BackRight);
        robot.mtrBL.setPower(BackLeft);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < Step_Time)) {
            telemetry.addData("Hit ball off", "%2.5f S Elapsed", runtime.seconds());
            telemetry.addData("gampad1rightX", "%2.5f S Elapsed", gamepad1RightX);
            telemetry.addData("pivot*drivespeed", "%2.5f S Elapsed", PIVOT_SPEED * DRIVE_SPEED);
            telemetry.update();
            telemetry.update();
        }

/***********************************************************************/
//Step move servo up
        Step_Time = 2;
        //Set servo down so the sensor can read the color
        robot.holder.setPosition(0);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < Step_Time)) {
            telemetry.addData("Servo Up", "%2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }


/***********************************************************************/
//Step move back to center of balancing stone
/*        Step_Time = 1;
        gamepad1LeftY = (0);  //keep the first negative 1 to be consistent with gamepad operation
        gamepad1LeftX = 0;
        if ((blue_found) && (red_found == false)) {
            gamepad1RightX = (PIVOT_SPEED) * DRIVE_SPEED; //pivot back to center
        } else if ((red_found) && (blue_found == false)) {
            gamepad1RightX = (-PIVOT_SPEED) * DRIVE_SPEED;  //pivot back to center
        } else{
            gamepad1RightX = 0;}

        // holonomic formulas
        FrontRight = (gamepad1LeftY - gamepad1LeftX - gamepad1RightX);
        BackRight = (gamepad1LeftY + gamepad1LeftX - gamepad1RightX);
        BackLeft = (-gamepad1LeftY + gamepad1LeftX - gamepad1RightX);
        FrontLeft = (-gamepad1LeftY - gamepad1LeftX - gamepad1RightX);

        // clip the right/left values so that the values never exceed +/- 1
        FrontRight = Range.clip(FrontRight, -1, 1);
        FrontLeft = Range.clip(FrontLeft, -1, 1);
        BackRight = Range.clip(BackRight, -1, 1);
        BackLeft = Range.clip(BackLeft, -1, 1);

        // write the values to the motors
        robot.mtrFR.setPower(FrontRight);
        robot.mtrFL.setPower(FrontLeft);
        robot.mtrBR.setPower(BackRight);
        robot.mtrBL.setPower(BackLeft);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < Step_Time)) {
            telemetry.addData("Recenter", "%2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

/***********************************************************************/
//Step move to safe zone
 /*       Step_Time = 1;

        //simulate gamepad entries
        //gamepad1LeftY = (-1) * DRIVE_SPEED;
        gamepad1LeftY = (0) * DRIVE_SPEED;
        gamepad1LeftX = 0;
        gamepad1RightX = 0;

        // holonomic formulas
        FrontRight = (gamepad1LeftY - gamepad1LeftX - gamepad1RightX);
        BackRight = (gamepad1LeftY + gamepad1LeftX - gamepad1RightX);
        BackLeft = (-gamepad1LeftY + gamepad1LeftX - gamepad1RightX);
        FrontLeft = (-gamepad1LeftY - gamepad1LeftX - gamepad1RightX);

        // clip the right/left values so that the values never exceed +/- 1
        FrontRight = Range.clip(FrontRight, -1, 1);
        FrontLeft = Range.clip(FrontLeft, -1, 1);
        BackRight = Range.clip(BackRight, -1, 1);
        BackLeft = Range.clip(BackLeft, -1, 1);

        // write the values to the motors
        robot.mtrFR.setPower(FrontRight);
        robot.mtrFL.setPower(FrontLeft);
        robot.mtrBR.setPower(BackRight);
        robot.mtrBL.setPower(BackLeft);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < Step_Time)) {
            telemetry.addData("Move to safe zone", "%2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
*/
        //step turn off motors
        // write the values to the motors
        robot.mtrFR.setPower(0);
        robot.mtrFL.setPower(0);
        robot.mtrBR.setPower(0);
        robot.mtrBL.setPower(0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }
}
