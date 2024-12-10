package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.robotcorelib.util.Subsystem;

public class Arm extends Subsystem {

    private Servo armServoLeft;
    private Servo armServoRight;

    private final double INTAKE_POSITION = 0.2;
    private final double FRONT_DEPOSIT_POSITION = 0.0;
    private final double BACK_DEPOSIT_POSITION = 1.0;
    private final double MIDDLE_POSITION = 0.5;

    private int prevPos = 3;  // Initial position (MIDDLE)

    @Override
    public void init() {
        armServoLeft = hardwareMap.servo.get("armServoLeft");
        armServoRight = hardwareMap.servo.get("armServoRight");

        moveToPosition(MIDDLE_POSITION); // Start in middle position
    }

    private void moveToPosition(double position) {
        armServoLeft.setPosition(position);
        armServoRight.setPosition(1 - position);
    }

    public void run(boolean xButtonPressed, boolean squareButtonPressed,
                    boolean circleButtonPressed, boolean triangleButtonPressed) {

        if (xButtonPressed && prevPos != 1) {
            moveToPosition(INTAKE_POSITION);
            prevPos = 1;
        } else if (circleButtonPressed && prevPos != 2) {
            moveToPosition(FRONT_DEPOSIT_POSITION);
            prevPos = 2;
        } else if (squareButtonPressed && prevPos != 3) {
            moveToPosition(MIDDLE_POSITION);
            prevPos = 3;
        } else if (triangleButtonPressed && prevPos != 4) {
            moveToPosition(BACK_DEPOSIT_POSITION);
            prevPos = 4;
        }
    }

    public int getCurrentPosition() {
        return prevPos;
    }
}
