
package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.HardwareAndMethods;

@Autonomous(name="Platform Autonomous Blue", group="Tungsteel 2019-20")
//@Disabled
public class PlatformAutonomousBlue extends LinearOpMode {
    //Create HardwareAndMethods instance called robot
    private HardwareAndMethods robot = new HardwareAndMethods();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap, "auto");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        printStatus();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        robot.mechanumSimpleAuto(-0.35f, 0.5f, 0.5f, 0f, 1750);
        robot.mechanumSimpleAuto(0f, 0.5f, 0.5f, 0f, 1000);
        robot.platformRight.setPosition(0);
        robot.platformLeft.setPosition(1);
        sleep(1000);
        //robot.mechanumSimpleAuto(0f, -0.5f, 0f, 2000);
        robot.mechanumSimpleAuto(0f, -0.4f, 0.5f, 90f, 2250);
        robot.mechanumSimpleAuto(0f, 0.5f, 0.5f, 90f, 1000);
        robot.platformRight.setPosition(1);
        robot.platformLeft.setPosition(0);
        sleep(1000);
        robot.mechanumSimpleAuto(0f, -0.5f, 0.5f, 90f, 3000);
        while(opModeIsActive()) {
            printStatus();
        }

    }

    public void printStatus(){
        telemetry.addLine("Color Sensor ||")
                .addData(" H: ", robot.hsvGetColor(0))
                .addData(" S: ", robot.hsvGetColor(1))
                .addData(" V: ", robot.hsvGetColor(2));
        telemetry.addLine("Lift Pos ||")
                .addData(" ", robot.getLiftPosition());
        telemetry.addLine("IMU Z||")
                .addData(" ", robot.imuGetZ());

        telemetry.update();
    }
}

