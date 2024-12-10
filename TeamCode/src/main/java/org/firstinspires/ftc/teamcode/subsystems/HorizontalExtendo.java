package org.firstinspires.ftc.teamcode.subsystems;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.robotcorelib.util.Subsystem;

public class HorizontalExtendo extends Subsystem {

    private Servo linkServoLeft;
    private Servo linkServoRight;

    // Define servo limits
    private final double fullRetractLeft = 0.45;
    private final double fullRetractRight = 0.55;
    private final double fullExtendLeft = 0.1;
    private final double fullExtendRight = 0.9;

    // Initialize current positions
    private double leftServoPos = fullRetractLeft;
    private double rightServoPos = fullRetractRight;

    @Override
    public void init() {
        linkServoLeft = hardwareMap.servo.get("linkServoLeft");
        linkServoRight = hardwareMap.servo.get("linkServoRight");

        // Set initial positions
        linkServoLeft.setPosition(leftServoPos);
        linkServoRight.setPosition(rightServoPos);
    }

    // GamePad2 right trigger to extend, left trigger to retract
    // gamePad2 right bumper to full extend, left bumper to fully retract
    public void run(double rightTriggerValue, double leftTriggerValue,
                    boolean leftBumper, boolean rightBumper) {

        // Check if full retract or extend is triggered
        if (leftBumper) {
            moveToPosition(fullRetractLeft, fullRetractRight);  // Fully retract
            leftServoPos = fullRetractLeft;
            rightServoPos = fullRetractRight;
        }
        else if (rightBumper) {
            moveToPosition(fullExtendLeft, fullExtendRight);    // Fully extend
            leftServoPos = fullExtendLeft;
            rightServoPos = fullExtendRight;
        }
        else {
            // Incrementally extend
            if (rightTriggerValue > 0) {
                leftServoPos = Math.max(fullExtendLeft, leftServoPos - 0.01 * rightTriggerValue);
                rightServoPos = Math.min(fullExtendRight, rightServoPos + 0.01 * rightTriggerValue);
                moveToPosition(leftServoPos, rightServoPos);
            }
            // Incrementally retract
            if (leftTriggerValue > 0) {
                leftServoPos = Math.min(fullRetractLeft, leftServoPos + 0.01 * leftTriggerValue);
                rightServoPos = Math.max(fullRetractRight, rightServoPos - 0.01 * leftTriggerValue);
                moveToPosition(leftServoPos, rightServoPos);
            }
        }
    }

    // Method to set servo positions
    private void moveToPosition(double leftPosition, double rightPosition) {
        linkServoLeft.setPosition(leftPosition);
        linkServoRight.setPosition(rightPosition);
    }

    public double getCurrentLinkagePos(){
        return leftServoPos;
    }
}
