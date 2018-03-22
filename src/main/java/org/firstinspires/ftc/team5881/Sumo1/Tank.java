package org.firstinspires.ftc.team5881.Sumo1;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

/** Created by benbroce on 3/22/18. */
public class Tank {
    private DcMotor[] motors = null;

    /**
     * Constructs a tank drive with a hwMap & the String names of 2 motors.
     *
     * @param hwMap a HardwareMap with which to access the motors
     * @param mtrL the String name of the Left motor
     * @param mtrR the String name of the Right motor
     */
    public Tank(HardwareMap hwMap, String mtrL, String mtrR) {
        this.motors = new DcMotor[] {hwMap.dcMotor.get(mtrL), hwMap.dcMotor.get(mtrR)};
    }

    public Tank(HardwareMap hwMap, String[] motors) {
        this.motors = new DcMotor[motors.length];
        for (int i = 0; i < motors.length; i++) {
            this.motors[i] = hwMap.dcMotor.get(motors[i]);
        }
    }

    /**
     * Moves the drive base
     *
     * @param l left power/speed [-1 to 1]
     * @param r right power/speed [-1 to 1]
     */
    public void move(double l, double r) {
        move(l, r, 1.0);
    }

    /**
     * Moves the drive base at a reduced power/speed
     *
     * @param l left power/speed [-1 to 1]
     * @param r right power/speed [-1 to 1]
     * @param reduction power/speed scale reduction [0 to 1]
     */
    public void move(double l, double r, double reduction) {
        for (int i = 0; i < motors.length; i++) {
            if (i % 2 == 0) {
                motors[i].setPower(Range.clip((l), -reduction, reduction));
            } else {
                motors[i].setPower(Range.clip((r), -reduction, reduction));
            }
        }
    }

    /** Stops all drive motors */
    public void stop() {
        for (DcMotor mtr : motors) {
            mtr.setPower(0);
        }
    }

    /**
     * Initialization routine for drive motors
     *
     * @param zeroPowerBehavior DCMotor.ZeroPowerBehavior.[BRAKE or FLOAT]
     * @param runMode DcMotor.RunMode.[RUN_USING_ENCODERS or RUN_WITHOUT_ENCODERS]
     */
    public void init(DcMotor.ZeroPowerBehavior zeroPowerBehavior, DcMotor.RunMode runMode) {
        for (int i = 0; i < motors.length; i++) {
            if (i % 2 == 0) {
                motors[i].setDirection(DcMotor.Direction.REVERSE);
            } else {
                motors[i].setDirection(DcMotor.Direction.FORWARD);
            }
        }
        for (DcMotor mtr : motors) {
            mtr.setZeroPowerBehavior(zeroPowerBehavior);
            mtr.setMode(runMode);
        }
        stop(); // stop drive
    }
}
