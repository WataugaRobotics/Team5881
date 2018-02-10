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

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * This file illustrates the concept of driving a path based on time.
 *
 * The code is structured as a LinearOpMode
 *
 * Start positions are laid out with the relic drop zones, looking down, and proceeding
 * clockwise
 *             Two Drop Zone Mats
 * Position 4                Position 1
 * Position 3                Position 2
 */

//Superclass for Competition 1 Autonomous
//@Disabled
public class Comp_Auto extends LinearOpMode {
    /* Declare OpMode members. */
    Hardware_Holo_4mtr_Plus robot = new Hardware_Holo_4mtr_Plus();
    private ElapsedTime     runtime = new ElapsedTime();

    final int RED_THRESH = 127;
    final int GREEN_THRESH = 127;
    final int BLUE_THRESH = 127;

    @Override
    public void runOpMode() {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }

        public void moveForTime(double front, double right, double back, double left, double secs) {
        // write the values to the motors
        robot.mtrF.setPower(-Range.clip(front, -1, 1));
        robot.mtrR.setPower(Range.clip(right, -1, 1));
        robot.mtrB.setPower(Range.clip(back, -1, 1));
        robot.mtrL.setPower(-Range.clip(left, -1, 1));

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < secs)) {
            telemetry.addData("Path", "%2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }

    public void stopMotors() {
        robot.mtrF.setPower(0);
        robot.mtrR.setPower(0);
        robot.mtrB.setPower(0);
        robot.mtrL.setPower(0);
    }

    public int getColor() {
        telemetry.addData("R", robot.colorSensor.red());
        telemetry.addData("G", robot.colorSensor.green());
        telemetry.addData("B", robot.colorSensor.blue());
        telemetry.update();
        if (robot.colorSensor.red() >= RED_THRESH
                && robot.colorSensor.blue() < BLUE_THRESH
                && robot.colorSensor.green() < GREEN_THRESH) {
            return 1;
        } else if (robot.colorSensor.blue() >= BLUE_THRESH
                && robot.colorSensor.red() < RED_THRESH
                && robot.colorSensor.green() < GREEN_THRESH) {
            return -1;
        } else {
            return 0;
        }
    }

    public void scannerUp() {
        robot.scanner.setPosition(robot.SENSOR_UP);
        sleep(1500);
        //idle();
    }

    public void scannerDown() {
        robot.scanner.setPosition(robot.SENSOR_DOWN);
        sleep(1500);
        //idle();
    }
}
