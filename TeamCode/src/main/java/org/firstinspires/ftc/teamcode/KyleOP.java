package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="KyleOP", group="Test Code")
@Disabled
public class KyleOP extends OpMode {
    //Create HardwareAndMethods instance called robot
    private HardwareAndMethods robot = new HardwareAndMethods();

    // Declares variables
    private boolean startPressed = false;
    private boolean platformChanged = false, platformOn = false;
    private boolean clawChanged = false, clawOn = false;
    private boolean capstoneChanged = false, capstoneOn = false;

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
    public void start(){
        robot.start();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        telemetry.addData("imuGetZ", robot.imuGetZ());
        telemetry.update();

        //Speed mod
        if(gamepad1.start && !startPressed){
            startPressed = true;
            if(robot.speedMod == 1f) {
                robot.speedMod = 2f;
            }else{
                robot.speedMod = 1f;
            }
        }else if(!gamepad1.start){
            startPressed = false;
        }

        //Platform servos
        if(gamepad1.b && !platformChanged) {
            robot.platformRight.setPosition(platformOn ? 1 : 0);
            robot.platformLeft.setPosition(platformOn ? 0 : 1);
            platformOn = !platformOn;
            platformChanged = true;
        } else if(!gamepad1.b) platformChanged = false;

        //Claw servo
        if(gamepad1.y && !clawChanged) {
            robot.claw.setPosition(clawOn ? 1 : 0);
            clawOn = !clawOn;
            clawChanged = true;
        } else if(!gamepad1.y) clawChanged = false;

        //Capstone servo
        if(gamepad1.x && !capstoneChanged) {
            robot.capstone.setPosition(capstoneOn ? 1 : 0);
            capstoneOn = !capstoneOn;
            capstoneChanged = true;
        } else if(!gamepad1.x) capstoneChanged = false;

        // Calls mechanum method
        // Mechanum uses the left stick to drive in the x,y directions, and the right stick to turn
        robot.mechanum(-gamepad1.left_stick_x, gamepad1.left_stick_y, -gamepad1.right_stick_x);

        //Calls lift method
        robot.lift(gamepad1.right_trigger, -gamepad1.left_trigger);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}

