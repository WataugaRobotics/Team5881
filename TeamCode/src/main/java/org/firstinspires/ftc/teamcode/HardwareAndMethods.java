package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.PIDCoefficients;


import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

public class HardwareAndMethods {
    //Declares variables
    public DcMotor leftFront = null;
    public DcMotor leftBack = null;
    public DcMotor rightFront = null;
    public DcMotor rightBack = null;
    public DcMotor lift = null;

    static final float COUNTS_PER_MOTOR_REV = 280f; //Neverest 40
    static final float WHEEL_DIAMETER_INCHES = 3.93701f; // For figuring circumference
    static final float COUNTS_PER_INCH = COUNTS_PER_MOTOR_REV / (WHEEL_DIAMETER_INCHES * 3.1415f);

    public float speedMod = 1f;
    public float liftPeakH, liftPeakL = 0f;

    public Servo platformRight = null;
    public Servo platformLeft = null;
    public Servo claw = null;
    public Servo capstone = null;

    public float peak;
    BNO055IMU imu;
    BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

    ColorSensor colorSensor;
    public DistanceSensor distanceSensor;
    float hsvValues[] = {0F, 0F, 0F};
    final double COLOR_SCALE_FACTOR = 255;

    HardwareMap hwMap = null;

    private ElapsedTime runtime = new ElapsedTime();

    public HardwareAndMethods(){
        //Set up parameters for imu
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
    }

    public void init(HardwareMap ahwMap, String type) {  //type = "auto" or "tele"
        hwMap = ahwMap;

        // Initialize the hardware variables
        leftFront  = hwMap.get(DcMotor.class, "leftFront");
        leftBack = hwMap.get(DcMotor.class, "leftBack");
        rightFront = hwMap.get(DcMotor.class, "rightFront");
        rightBack = hwMap.get(DcMotor.class, "rightBack");
        lift = hwMap.get(DcMotor.class, "lift");

        platformRight = hwMap.get(Servo.class, "platformRight");
        platformLeft = hwMap.get(Servo.class, "platformLeft");
        claw = hwMap.get(Servo.class, "claw");
        capstone = hwMap.get(Servo.class, "capstone");

        imu = hwMap.get(BNO055IMU.class, "imu");
        colorSensor = hwMap.get(ColorSensor.class, "colorDistance");
        distanceSensor = hwMap.get(DistanceSensor.class, "colorDistance");

        // Set motor directions
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.REVERSE);

        // Set motor modes
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if(type == "auto"){
            leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        //Set servo positions
        platformRight.setPosition(1);
        platformLeft.setPosition(0);
        claw.setPosition(0);
        capstone.setPosition(0);

        //Set imu parameters
        imu.initialize(parameters);

    }

    public void start(){
        //Start tracking imu acceleration
        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
    }



    //Control Motors
    public void lift(float u, float d){
        double liftPower = Range.clip(u + d, -1.0, 1.0);
        lift.setPower(liftPower);
    }

    public void liftAuto(float power, double timeMilliseconds){
        double startTime = runtime.milliseconds();
        double endTime = startTime + timeMilliseconds;

        while(runtime.milliseconds() < endTime){
            if(runtime.milliseconds() < startTime + 250 || runtime.milliseconds() > endTime - 250) {
                lift.setPower(power / 2f);
            }else{
                lift.setPower(power);
            }
        }

        lift.setPower(0);
    }

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

    public void mechanumSimpleAuto(float xTargetPower, float yTargetPower, float rMaxPower, float targetAngle, double timeMilliseconds){
        float angleOffset;
        float rotation;

        double startTime = runtime.milliseconds();
        double endTime = startTime + timeMilliseconds;

        while(runtime.milliseconds() < endTime){
            Orientation orientation = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            angleOffset = orientation.firstAngle - targetAngle;
            if(angleOffset < -10){
                rotation = rMaxPower;
            }else if(angleOffset > 10){
                rotation = -rMaxPower;
            }else{
                rotation = map(angleOffset, -10, 10, 0.5f, -0.5f);
            }

            if(runtime.milliseconds() < startTime + 250) {
                mechanum(-xTargetPower / 2f, -yTargetPower / 2f, rotation);
            }else if(runtime.milliseconds() > endTime - 250){
                mechanum(-xTargetPower / 2f, -yTargetPower / 2f, rotation);
            }else{
                mechanum(-xTargetPower, -yTargetPower, rotation);
            }
        }

        mechanum(0f, 0f, 0f);
    }

    public void mechanumColorDistanceAuto(float xTargetPower, float yTargetPower, float targetAngle, float distanceCM){
        float angleOffset;
        float rotation;

        double startTime = runtime.milliseconds();

        while(distanceSensor.getDistance(DistanceUnit.CM) > distanceCM || distanceSensor.getDistance(DistanceUnit.CM) == distanceSensor.distanceOutOfRange){
            Orientation orientation = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            angleOffset = orientation.firstAngle - targetAngle;
            if(angleOffset < -10){
                rotation = 0.5f;
            }else if(angleOffset > 10){
                rotation = -0.5f;
            }else{
                rotation = map(angleOffset, -10, 10, 0.5f, -0.5f);
            }

            if(runtime.milliseconds() < startTime + 250) {
                mechanum(-xTargetPower / 2f, -yTargetPower / 2f, rotation);
            }else if(distanceSensor.getDistance(DistanceUnit.CM) < distanceCM + 2 && distanceSensor.getDistance(DistanceUnit.CM) > distanceCM - 2){
                mechanum(-xTargetPower / 2f, -yTargetPower / 2f, rotation);
            }else{
                mechanum(-xTargetPower, -yTargetPower, rotation);
            }
        }

        mechanum(0f, 0f, 0f);
    }


    //Kyle's lift position idea
    public void changeLiftPos(float pos){
        float mappedLiftPos = map(pos, 0, 97, 0, 10735);

        while(getLiftPosition() > mappedLiftPos + 1 || getLiftPosition() < mappedLiftPos - 1) {
            if (getLiftPosition() > 0) {
                lift.setPower(-1);
            } else if (getLiftPosition() < 00) {
                lift.setPower(0);
            }
        }
    }


    //find out maximum and minimum of the lift
    public float liftMaximum() {
        if(lift.getCurrentPosition() > liftPeakH){
            liftPeakH = lift.getCurrentPosition();
        }
        return liftPeakH;
    }
    public float liftMinimum() {
        if(lift.getCurrentPosition() < liftPeakL){
            liftPeakL = lift.getCurrentPosition();
        }
        return liftPeakL;
    }


    public float map(float input, float inputMin, float inputMax, float outputMin, float outputMax){
        return (input - inputMin) / (inputMax - inputMin) * (outputMax - outputMin) + outputMin;
    }
    
    public float hsvGetColor(int hsvIndex){
        Color.RGBToHSV((int) (colorSensor.red() * COLOR_SCALE_FACTOR), (int) (colorSensor.green() * COLOR_SCALE_FACTOR), (int) (colorSensor.blue() * COLOR_SCALE_FACTOR), hsvValues);
        return hsvValues[hsvIndex];
    }

    public float imuGetZ(){
        Orientation orientation = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return orientation.firstAngle;
    }

    public float getLiftPosition(){
        return lift.getCurrentPosition();
    }
    public float getLiftPositionCm(){
        return  map(getLiftPosition(), 0, 97, 0, 10735);
    }


}
