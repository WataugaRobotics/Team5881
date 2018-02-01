package org.firstinspires.ftc.team5881;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/** ClassDoc */
@TeleOp(name = "Basic X", group = "TeleOp")
public class TeleXBasic extends OpMode {

    //get motors w/o hw class (for testing)//
    public DcMotor mtrFR = null;
    public DcMotor mtrFL = null;
    public DcMotor mtrBR = null;
    public DcMotor mtrBL = null;

    // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {
        //get motors w/o hw class (for testing)//
        mtrFR = hardwareMap.dcMotor.get("mtrFR");
        mtrFL = hardwareMap.dcMotor.get("mtrFL");
        mtrBR = hardwareMap.dcMotor.get("mtrBR");
        mtrBL = hardwareMap.dcMotor.get("mtrBL");
    }

    // Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
    @Override
    public void init_loop() {
    }

    // Code to run ONCE when the driver hits PLAY
    @Override
    public void start() {}

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {
        //get x, y, c values from joystick
        double x = -gamepad1.left_stick_x;
        double y = -gamepad1.left_stick_y;
        double c = -gamepad1.right_stick_x;

        //set motor powers
        mtrFL.setPower(Range.clip((y + x + c), -1.0, 1.0));
        mtrBL.setPower(Range.clip((y - x + c), -1.0, 1.0));
        mtrFR.setPower(Range.clip((-y + x + c), -1.0, 1.0));
        mtrBR.setPower(Range.clip((-y - x + c), -1.0, 1.0));
    }

    // Code to run ONCE after the driver hits STOP
    @Override
    public void stop() {
        mtrFL.setPower(0);
        mtrBL.setPower(0);
        mtrFR.setPower(0);
        mtrBR.setPower(0);
    }
}
