package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.robotcorelib.drive.DriveMode;
import org.firstinspires.ftc.teamcode.robotcorelib.drive.DrivetrainImpl;
import org.firstinspires.ftc.teamcode.robotcorelib.drive.DrivetrainVelocityMode;
import org.firstinspires.ftc.teamcode.robotcorelib.robot.Robot;
import org.firstinspires.ftc.teamcode.robotcorelib.util.JoystickCurve;
import org.firstinspires.ftc.teamcode.robotcorelib.util.RobotRunMode;
import org.firstinspires.ftc.teamcode.robotcorelib.util.Subsystem;
import org.firstinspires.ftc.teamcode.robotcorelib.util.hardware.HardwarePrecision;

import static org.firstinspires.ftc.teamcode.robotcorelib.math.utils.MathUtils.*;

public class Drivetrain extends Subsystem implements DrivetrainImpl {

    private DcMotorEx fl, fr, bl, br;
    public static final DriveMode driveMode = DriveMode.MECANUM;
    public static final DrivetrainVelocityMode velocityMode = DrivetrainVelocityMode.DRIVE_MOTOR_ENCODERS;


    @Override
    public void init() {
        fl = hardwareMap.get(DcMotorEx.class, "front_left");
        fr = hardwareMap.get(DcMotorEx.class, "front_right");
        bl = hardwareMap.get(DcMotorEx.class, "back_left");
        br = hardwareMap.get(DcMotorEx.class, "back_right");


        setDrivetrainMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setDrivetrainMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        if(Robot.runMode == RobotRunMode.AUTONOMOUS) {
            setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } else {
            setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }


            fl.setDirection(DcMotorSimple.Direction.FORWARD);
            fr.setDirection(DcMotorSimple.Direction.REVERSE);
            bl.setDirection(DcMotorSimple.Direction.FORWARD);
            br.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void mecanumDrive(double forward, double strafe, double turn) {
        double multiplier = 2.0 / Math.sqrt(2.0);
        forward = joystickCurve(forward, JoystickCurve.MODIFIED_CUBIC);
        strafe = joystickCurve(strafe, JoystickCurve.MODIFIED_CUBIC);
        turn = joystickCurve(turn, JoystickCurve.MODIFIED_CUBIC);

        double theta = Math.atan2(forward, strafe) - Math.PI / 4.0;
//        turn = joystickCurve(turn, JoystickCurve.MODIFIED_CUBIC);

        double magnitude = Math.abs(forward) + Math.abs(strafe) + Math.abs(turn);
        if(magnitude > 1) {
            forward *= 1 / magnitude;
            strafe *= 1 / magnitude;
            turn *= -1 / magnitude;
        }

        // Godly Math Trick: sin(x+pi/4) = cos(x-pi/4)
        double speed = multiplier * Math.hypot(strafe, forward);

        double flSpeed = speed * Math.cos(theta) + turn;
        double frSpeed = speed * Math.sin(theta) - turn;
        double blSpeed = speed * Math.sin(theta) + turn;
        double brSpeed = speed * Math.cos(theta) - turn;


        setPowers(flSpeed, frSpeed, blSpeed, brSpeed);
    }

    //This method is what we are going to use if we have any extra drivetrain control enhancements that aren't just the normal driving methods.
    //The reason for this 2nd method is so we have a barebones method available to us at all times for other use cases not specific to teleop driving
    public void drive() {}

    /*
     * All setPowers() methods should follow the {fl, fr, bl, br} motor order all the time
     */
    @Override
    public void setPowers(double[] powers) {
        DcMotor[] motors = getMotorsAsList();
        for (int i = 0; i < 4; i++) {
            if(shouldHardwareUpdate(powers[i], motors[i].getPower(), HardwarePrecision.HIGH)) {
                motors[i].setPower(powers[i]);
            }
        }
    }

    public void setPowers(double fl, double fr, double bl, double br) {
        setPowers(new double[] { fl, fr, bl, br });
    }

    public void setDrivetrainMode(DcMotor.RunMode runMode) {
        fl.setMode(runMode);
        fr.setMode(runMode);
        bl.setMode(runMode);
        br.setMode(runMode);
    }

    public DcMotor[] getMotorsAsList() {
        return new DcMotor[] { fl, fr, bl, br };
    }


    @Override
    public DriveMode getDriveMode() {
        return driveMode;
    }

    public DrivetrainVelocityMode getVelocityControlMode() {
        return velocityMode;
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior zeroPowerBehavior) {
        fl.setZeroPowerBehavior(zeroPowerBehavior);
        fr.setZeroPowerBehavior(zeroPowerBehavior);
        bl.setZeroPowerBehavior(zeroPowerBehavior);
        br.setZeroPowerBehavior(zeroPowerBehavior);
    }

}
