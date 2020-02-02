package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

int botDiameter = __; //distance from corner to corner
int botCirc = botDiameter * π;
int maxTics = __; //add max motor tics here
//int deg = maxTics / 360;

int wheelDiameter = __; //add wheel diameter here
@TeleOp(name="KyleStuff", group="Tungsteel 2019-20")
//@Disabled
public class EncoderExample extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
        leftFront  = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        //set motor directions
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.REVERSE);

        //sets mode to use encoders
        leftFront.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        leftBack.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightFront.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS); //setMode() is used instead of setChannelMode(), which is now deprecated
        rightBack.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        //reset encoders
        leftFront.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        leftBack.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightFront.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightBack.setMode(DcMotorController.RunMode.RESET_ENCODERS);



        waitForStart();

        leftFront.setTargetPosition(go(90)); //Sets motor to move 1440 ticks (1440 is one rotation for Tetrix motors)
        rightFront.setTargetPosition(go(90));

        setAll(.5);

        while(leftFront.getCurrentPosition() < leftFront.getTargetPosition() || rightFront.getCurrentPosition() < rightFront.getTargetPosition() || leftBack.getCurrentPosition() < leftBack.getTargetPosition() || rightBack.getCurrentPosition() < rightBack.getTargetPosition()) { //While target has not been reached
            waitOneFullHardwareCycle(); //Needed within all loops
        }

        setAll(0);

    }




    public int go(int cent){

        int oneC = wheeldiameter/maxtics;
        int cms = cent * oneC;
        return cms;
    }
    public void moveY(int goC){
        leftFront.setTargetPosition(go(goC));
        rightFront.setTargetPosition(go(goC));
        leftBack.setTargetPosition(go(goC));
        rightBack.setTargetPosition(go(goC));
        reset();
    }
    public void moveX(int goC){
        //Going Right is positive, left is negative
        leftFront.setTargetPosition(go(-goC));
        rightFront.setTargetPoition(go(goC));
        leftBack.setTargetPosition(go(goC));
        rightBack.setTargetPosition(go(-goC));
        reset();
    }
    public void rotate(int goD){
        //Positive is Counter clockwise, negaitve is clockwise
        leftFront.setTargetPosition(go((goD/360) * botCirc));
        leftBack.setTargetPosition(go((goD/360) * botCirc));

        rightFront.setTargetPosition(go(-(goD/360)* botCirc));
        rightBack.setTargetPosition(go(-(goD/360)* botCirc));
        reset();
    }

    public void setAll(int speed){
        leftBack.setPower(speed);
        rightBack.setPower(speed);
        leftFront.setPower(speed);
        rightBack.setPower(speed);
    }

    void reset(){
        //Ensures encoders are zero
        while(leftBack.getCurrentPosition() != 0 || rightBack.getCurrentPosition() != 0 || leftFront.getCurrentPosition() != 0 || rightFront.getCurrentPosition() != 0) {
            leftFront.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            leftBack.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            rightFront.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            rightBack.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            waitOneFullHardwareCycle(); //Needed within all loops
        }
    }
}