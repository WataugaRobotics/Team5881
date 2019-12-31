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

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOP", group="Tungsteel 2019-20")
//@Disabled
public class TeleOP extends OpMode {
    // Declare variables
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFront = null;
    private DcMotor leftBack = null;
    private DcMotor rightFront = null;
    private DcMotor rightBack = null;
    private DcMotor lift = null;

    private int speedMod = 2;
    private boolean startPressed = false;

    private Servo platform = null;
    private boolean platformDown = false;
    private boolean aPressed = false;

    private CRServo intakeRight = null;
    private CRServo intakeLeft = null;
    private boolean bumperPressed = false;
    private boolean intakeInOn = false;


    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftFront  = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        lift = hardwareMap.get(DcMotor.class, "lift");
        platform = hardwareMap.get(Servo.class, "platform");
        intakeRight = hardwareMap.get(CRServo.class, "intakeRight");
        intakeLeft = hardwareMap.get(CRServo.class, "intakeLeft");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.REVERSE);

        // Set lift motor modes
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
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
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        //Speed mod
        if(gamepad1.start && !startPressed){
            startPressed = true;
            if(speedMod == 1) {
                speedMod = 2;
            }else{
                speedMod = 1;
            }
        }else if(!gamepad1.start){
            startPressed = false;
        }

        //Platform servo
        if(gamepad1.a && !aPressed){
            aPressed = true;
            platformDown = !platformDown;
            if(platformDown){
                platform.setPosition(0);
            }else{
                platform.setPosition(1);
            }
        }else if(!gamepad1.a){
            aPressed = false;
        }

        //Intake
        if(gamepad1.right_bumper && !bumperPressed){
            bumperPressed = true;
            if(intakeInOn){
                intakeRight.setPower(1);
                intakeLeft.setPower(1);
            }else{
                intakeRight.setPower(0);
                intakeLeft.setPower(0);
            }
        }else if(!gamepad1.right_bumper){
            bumperPressed = false;
        }
        if

        // Mechanum uses the left stick to drive in the x,y directions, and the right stick to turn
        mechanum(-gamepad1.left_stick_x, gamepad1.left_stick_y, -gamepad1.right_stick_x);

        //Control for the lift
        double u = gamepad1.right_trigger;
        double d = -gamepad1.left_trigger;
        double liftPower = Range.clip(u + d, -1.0, 1.0);
        lift.setPower(liftPower);


        // Show the elapsed game time
        telemetry.addData("Status", "Run Time: " + runtime.toString());

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

    //CUSTOM METHODS
    public void mechanum(float x, float y, float r){
        double leftFrontPower = Range.clip(y + x + r, -1.0, 1.0) / speedMod;
        double leftBackPower = Range.clip(y - x + r, -1.0, 1.0) / speedMod;
        double rightFrontPower = Range.clip(y - x - r, -1.0, 1.0) / speedMod;
        double rightBackPower = Range.clip(y + x - r, -1.0, 1.0) / speedMod;

        // Send calculated power to wheels
        leftFront.setPower(leftFrontPower);
        leftBack.setPower(leftBackPower);
        rightFront.setPower(rightFrontPower);
        rightBack.setPower(rightBackPower);
    }
}
