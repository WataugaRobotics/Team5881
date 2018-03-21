package org.firstinspires.ftc.team5881;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * This file illustrates the concept of driving a path based on time.
 *
 * <p>The code is structured as a LinearOpMode
 *
 * <p>Start positions are laid out with the relic drop zones, looking down, and proceeding clockwise
 * Two Drop Zone Mats Position 4 Position 1 Position 3 Position 2
 */
@Autonomous(name = "Auto_Position_1_Blue", group = "Autonomous")
public class Auto_Position_1_Blue extends LinearOpMode {

    /* Declare OpMode members. */
    HW5881_HoloX robot = new HW5881_HoloX();
    private ElapsedTime runtime = new ElapsedTime();

    final float DRIVE_SPEED = 1f;
    final float PIVOT_SPEED = 0.2f; // speed for pivoting to knock the jewel ball off in auto

    // For blue ball
    // final float RED_HIGH = 20;
    final float RED_LOW = 10;
    // final float BLUE_HIGH = 55;
    final float BLUE_LOW = 10;
    // final float GREEN_HIGH = 40;
    // final float GREEN_LOW = 11;

    boolean blue_found = false;
    boolean red_found = false;

    ColorSensor sensorColor;

    @Override
    public void runOpMode() {

        // Setup Hardware
        robot.init(hardwareMap);
        sensorColor = hardwareMap.colorSensor.get("sensorColor");
        robot.jewelArm.setPosition(0);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run"); //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step move servo down
        double Step_Time = 4;
        // Set servo down so the sensor can read the color
        robot.jewelArm.setPosition(0.8);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < Step_Time)) {
            telemetry.addData("Move servo down", "%2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        /** ******************************************************************** */
        // Step sense color
        Step_Time = 5; // seconds
        if ((sensorColor.red() > RED_LOW) && (sensorColor.red() > sensorColor.blue())) {
            red_found = true;
            blue_found = false;
        } else if ((sensorColor.blue() > BLUE_LOW) && (sensorColor.red() < sensorColor.blue())) {
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

        /** ******************************************************************** */
        // Step move ball off
        Step_Time = 0.1; // seconds

        // turn toward red ball
        if ((blue_found) && (red_found == false)) {
            robot.drive.move(0, 0, -PIVOT_SPEED, DRIVE_SPEED); // knock off red ball
        } else if ((red_found) && (blue_found == false)) {
            robot.drive.move(0, 0, PIVOT_SPEED, DRIVE_SPEED); // knock off red ball
        } else { // uncertain, do nothing
            robot.drive.stop();
        }

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < Step_Time)) {
            telemetry.addData("Hit ball off", "%2.5f S Elapsed", runtime.seconds());
            telemetry.addData("gampad1rightX", "%2.5f S Elapsed", gamepad1RightX);
            telemetry.addData("pivot*drivespeed", "%2.5f S Elapsed", PIVOT_SPEED * DRIVE_SPEED);
            telemetry.update();
            telemetry.update();
        }

        /** ******************************************************************** */
        // Step move servo up
        Step_Time = 2;
        // Set servo down so the sensor can read the color
        robot.jewelArm.setPosition(0);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < Step_Time)) {
            telemetry.addData("Servo Up", "%2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // step turn off motors
        // write the values to the motors
        robot.drive.stop();

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }

    public void hold(double time, String action) {
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < time)) {
            telemetry.addData(action, "%2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }
}
