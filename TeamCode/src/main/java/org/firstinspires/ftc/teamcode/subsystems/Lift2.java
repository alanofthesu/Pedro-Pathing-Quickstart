package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robotcorelib.util.Subsystem;

public class Lift2 extends Subsystem {
    private DcMotorEx liftMotor;
    private final int LIFT_TOP_POSITION = 300;  // Adjust based on lift's top position
    private final int LIFT_BOTTOM_POSITION = 0;  // Bottom position (assume 0 for base)
    private int targetPosition;

    @Override
    public void init() {
        liftMotor = hardwareMap.get(DcMotorEx.class, "liftMotor");


        // Reset the encoder and set motor to RUN_TO_POSITION mode
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setTargetPosition(0);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set PIDF coefficients for position control
        liftMotor.setPositionPIDFCoefficients(10.0); // Adjust PIDF coefficient as needed
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        targetPosition = LIFT_BOTTOM_POSITION; // Start at the bottom
    }

    public void run(double manualInput, boolean goToTop, boolean goToBottom) {
        if (Math.abs(manualInput) > 0.1) {
            // Manual control: set power directly
            liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            liftMotor.setDirection(DcMotor.Direction.REVERSE);
            liftMotor.setPower(manualInput);
            targetPosition = liftMotor.getCurrentPosition(); // Update target for hold mode
        } else if (goToTop || goToBottom) {
            // Automatic control: move to top or bottom position
            targetPosition = goToTop ? LIFT_TOP_POSITION : LIFT_BOTTOM_POSITION;
            clampTargetPosition();
            liftMotor.setTargetPosition(targetPosition);
            liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            liftMotor.setPower(1.0); // Set max power for PIDF control
        } else {
            // Hold the current position
            if (liftMotor.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
                targetPosition = liftMotor.getCurrentPosition();
                clampTargetPosition();
                liftMotor.setTargetPosition(targetPosition);
                liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            liftMotor.setPower(1.0); // Maintain position with PID control
        }
    }

    public void stop() {
        liftMotor.setPower(0);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void telemetry(Telemetry telemetry) {
        telemetry.addData("Lift Position", liftMotor.getCurrentPosition());
        telemetry.addData("Target Position", targetPosition);
        telemetry.addData("Motor Power", liftMotor.getPower());
    }

    private void clampTargetPosition() {
        targetPosition = Range.clip(targetPosition, LIFT_BOTTOM_POSITION, LIFT_TOP_POSITION);
    }
}
