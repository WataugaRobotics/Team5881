
package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.HardwareAndMethods;

@Autonomous(name="Park Autonomous Left", group="Tungsteel 2019-20")
//@Disabled
public class ParkAutoLeft extends LinearOpMode {
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
        robot.mechanumSimpleAuto(-1f, -0.1f, 0.5f, 0f, 1000);

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

