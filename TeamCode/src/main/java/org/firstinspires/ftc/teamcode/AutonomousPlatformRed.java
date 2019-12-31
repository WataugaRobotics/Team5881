package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="Platform Red", group="Tungsteel 2019-20")
//@Disabled
public class AutonomousPlatformRed extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFront = null;
    private DcMotor leftBack = null;
    private DcMotor rightFront = null;
    private DcMotor rightBack = null;
    private DcMotor lift = null;
    private int speedMod = 2;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftFront  = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        lift = hardwareMap.get(DcMotor.class, "lift");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.REVERSE);

        // Set lift motor modes
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        runtime.reset();

        mechanumAuto(0, -1, 0, 3100);
        mechanumAuto(-1, 0, 0, 1250);
        sleep(7000);
        mechanumAuto(0, 1, -0.35, 6000);
        mechanumAuto(0, -1, 0, 100);
        mechanumAuto(1, 0, 0, 1250);
        mechanumAuto(-1, 0, 0, 400);
        sleep(7000);
        mechanumAuto(0, -1, 0, 4000);


        speedMod = 1;

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            //Speed mod
            if(gamepad1.a){
                speedMod = 1;
            }else if(gamepad1.b){
                speedMod = 2;
            }

            // Mechanum uses the left stick to drive in the x,y directions, and the right stick to turn
            mechanum(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);

            //Control for the lift
            double u = gamepad1.right_trigger;
            double d = -gamepad1.left_trigger;
            double liftPower = Range.clip(u + d, -1.0, 1.0);
            lift.setPower(liftPower);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }

    public void mechanum(double xVal, double yVal, double rVal){
        double x = -xVal;
        double y = -yVal;
        double r = -rVal;

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

    public void mechanumAuto(double x, double y, double r, long t){
        mechanum(x, y, r);
        sleep(t);
        mechanum(0, 0, 0);
    }
}
