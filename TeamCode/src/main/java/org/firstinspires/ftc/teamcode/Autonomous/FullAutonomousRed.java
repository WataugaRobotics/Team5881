
package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.HardwareAndMethods;

@Autonomous(name="Full Autonomous Red", group="Tungsteel 2019-20")
//@Disabled
public class FullAutonomousRed extends LinearOpMode {
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

        //Find skystone
        robot.mechanumSimpleAuto(0f, 0.5f, 0.5f, 0f, 1900);
        double offset = 0;
        for(int i = 0; i < 3; i++){
            sleep(250);
            if(i != 0){
                robot.mechanumSimpleAuto(-0.5f, 0f, 0.5f, 0f, 1000);
                offset += 525;
            }
            if(robot.hsvGetColor(0) >= 120) i = 3;
        }

        //Get skystone
        robot.mechanumSimpleAuto(0f, 0.5f, 0.5f, 0f, 600);
        robot.claw.setPosition(1);
        sleep(750);

        //Drive to build platform
        robot.mechanumSimpleAuto(0f, -0.5f, 0.5f, 0f, 800);
        robot.mechanumSimpleAuto(0f, 0f, 0.5f, -90f, 1500);
        robot.mechanumSimpleAuto(0f, 0.5f, 0.5f, -90f, 5000 + offset);
        robot.mechanumSimpleAuto(0f, 0f, 0.5f, 0f, 1500);

        //Place skystone and grab build platform
        robot.claw.setPosition(.75);
        sleep(500);
        robot.claw.setPosition(1);
        sleep(500);
        robot.liftAuto(1f, 800);
        robot.mechanumSimpleAuto(0f, 0.5f, 0.5f, 0f, 1250);
        robot.claw.setPosition(0);
        robot.platformRight.setPosition(0);
        robot.platformLeft.setPosition(1);
        sleep(1000);

        //Drag build platform
        robot.mechanumSimpleAuto(0f, -0.5f, 0.25f, -30f, 1250);
        robot.mechanumSimpleAuto(0f, -0.5f, 0.75f, -90f, 2250);
        robot.mechanumSimpleAuto(0f, 0.5f, 0.5f, -90f, 1600);
        robot.platformRight.setPosition(1);
        robot.platformLeft.setPosition(0);
        sleep(1000);

        //Park under bridge
        robot.liftAuto(-1f, 750);
        robot.mechanumSimpleAuto(0.1f, -1f, 0.5f, -90f, 1600);

        while(opModeIsActive()) {
            printStatus();
        }

    }

    public void printStatus(){
        telemetry.addLine("Color Sensor ||")
                .addData(" H: ", robot.hsvGetColor(0))
                .addData(" S: ", robot.hsvGetColor(1))
                .addData(" V: ", robot.hsvGetColor(2));
        telemetry.addLine("Distance Sensor ||")
                .addData(" CM: ", robot.distanceSensor.getDistance(DistanceUnit.CM));
        telemetry.addLine("Lift Pos ||")
                .addData(" ", robot.getLiftPosition());
        telemetry.addLine("IMU Z||")
                .addData(" ", robot.imuGetZ());

        telemetry.update();
    }
}

