package org.firstinspires.ftc.teamcode.Backup;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.HardwareAndMethods;

@TeleOp(name="Kyle 2p TeleOP", group="Tungsteel 2019-20")
@Disabled
public class KyleTwoTeleopBackup extends OpMode {
    //Create HardwareAndMethods instance called robot
    private HardwareAndMethods robot = new HardwareAndMethods();

    // Declares variables
    private boolean startPressed = false;
    private boolean platformChanged = false, platformOn = false;
    private boolean clawChanged = false, clawOn = false;
    private boolean liftChangedDown = false, liftChangedUp = false;
    private boolean capstoneChanged = false, capstoneOn = false, capstoneAutoChanged = false;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        // Initialize the hardware variables
        robot.init(hardwareMap, "tele");

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
        robot.start();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        //Speed mod
        if (gamepad1.start && !startPressed) {
            startPressed = true;
            if (robot.speedMod == 1f) {
                robot.speedMod = 2f;
            } else {
                robot.speedMod = 1f;
            }
        } else if (!gamepad1.start) {
            startPressed = false;
        }

        //Platform servos
        if (gamepad1.x && !platformChanged) {
            robot.platformRight.setPosition(platformOn ? 1 : 0);
            robot.platformLeft.setPosition(platformOn ? 0 : 1);
            platformOn = !platformOn;
            platformChanged = true;
        } else if (!gamepad1.x) platformChanged = false;

        //Claw servo
        if (gamepad2.x && !clawChanged) {
            robot.claw.setPosition(clawOn ? 0 : 1);
            clawOn = !clawOn;
            clawChanged = true;
        } else if (!gamepad2.x) clawChanged = false;

        //Capstone servo auto
        if (gamepad2.y && !capstoneChanged) {

            robot.liftAuto(-.5f, 1175);
        } else if (!gamepad2.y) capstoneChanged = false;


        //Capstone servo not auto
        if(gamepad2.b && !capstoneChanged) {
            robot.capstone.setPosition(capstoneOn ? 0 : 1);
            capstoneOn = !capstoneOn;
            capstoneChanged = true;
        } else if(!gamepad2.b) capstoneChanged = false;




            // Calls mechanum method
            // Mechanum uses the left stick to drive in the x,y directions, and the right stick to turn
            robot.mechanum(-gamepad1.left_stick_x, gamepad1.left_stick_y, -gamepad1.right_stick_x);

            //Calls lift method
            robot.lift(gamepad2.right_trigger, -gamepad2.left_trigger);

            telemetry.addLine("Color Sensor ||")
                    .addData(" H: ", robot.hsvGetColor(0))
                    .addData(" S: ", robot.hsvGetColor(1))
                    .addData(" V: ", robot.hsvGetColor(2));
            telemetry.addLine("Distance Sensor ||")
                    .addData(" CM: ", robot.distanceSensor.getDistance(DistanceUnit.CM));
            telemetry.addLine("Lift Pos ||")
                    .addData("Current Position", robot.getLiftPosition())
                    .addData("Highest Position", robot.liftMaximum());
            telemetry.addLine("IMU Z||")
                    .addData(" ", robot.imuGetZ());


        }

        /*
         * Code to run ONCE after the driver hits STOP
         */
        @Override
        public void stop () {
        }

    }


