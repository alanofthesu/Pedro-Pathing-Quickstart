package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robotcorelib.util.Subsystem;

public class Intake extends Subsystem {

    private Servo claw;
    private Servo clawRoll;
    private Servo clawPitch;

    private boolean clawOpen = false;
    private final double CLAW_OPEN_POSITION = 0.3;
    private final double CLAW_CLOSED_POSITION = 0.1;

    private final double ROLL_INCREMENT = 0.01;  // Small adjustment for roll with triggers
    private final double ROLL_90_POSITION = 0.75; // Position for a 90-degree roll

    @Override
    public void init() {
        claw = hardwareMap.get(Servo.class, "clawServo");
        clawRoll = hardwareMap.get(Servo.class, "clawSpinServo");
        clawPitch = hardwareMap.get(Servo.class, "clawUpDownServo");

        claw.setPosition(CLAW_CLOSED_POSITION); // Start with the claw closed
        clawRoll.setPosition(0.5); // Start roll at midpoint
        clawPitch.setPosition(0.5); // Start pitch at midpoint
    }

    public void run(double leftTrigger, double rightTrigger, boolean leftBumper, boolean rightBumper) {
        // Adjust claw roll with triggers
        double currentRollPosition = clawRoll.getPosition();
        double rollAdjustment = rightTrigger * ROLL_INCREMENT - leftTrigger * ROLL_INCREMENT;
        clawRoll.setPosition(clamp(currentRollPosition + rollAdjustment, 0.0, 1.0));

        // Set claw roll to 90 degrees with left bumper
        if (leftBumper) {
            clawRoll.setPosition(ROLL_90_POSITION);
        }

        // Toggle claw open/close with right bumper
        if (rightBumper) {
            clawOpen = !clawOpen;
            claw.setPosition(clawOpen ? CLAW_OPEN_POSITION : CLAW_CLOSED_POSITION);
        }
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public String getClawStatus() {
        return clawOpen ? "Open" : "Closed";
    }

    public void addTelemetry(Telemetry telemetry) {
        telemetry.addData("Claw Status", getClawStatus());
        telemetry.addData("Claw Roll Position", clawRoll.getPosition());
        telemetry.addData("Claw Pitch Position", clawPitch.getPosition());
        telemetry.update();
    }
}
