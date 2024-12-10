package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.teamcode.robot.MainConfig;
import org.firstinspires.ftc.teamcode.robotcorelib.math.control.SimplePID;
import org.firstinspires.ftc.teamcode.robotcorelib.opmode.OpModePipeline;
import org.firstinspires.ftc.teamcode.robotcorelib.robot.Robot;
import org.firstinspires.ftc.teamcode.robotcorelib.util.RobotRunMode;
@TeleOp (name = "ServoTest")
public class ServoTest extends OpModePipeline {
    public Servo servo;
    private double servoPos = 0.3;

    public void init() {
        runMode = RobotRunMode.TELEOP;
        subsystems = new MainConfig();
        super.init();

        servo = hardwareMap.servo.get("clawServo");

    }


    @Override
    public void loop() {
        Robot.update();
        servoPos += gamepad1.left_stick_y * 0.005;
        servoPos = Range.clip(servoPos, 0.0, 1.0);
        servo.setPosition(servoPos);

        if (gamepad1.a) {
            servo.setPosition(0.0); // Move to minimum position
        } else if (gamepad1.b) {
            servo.setPosition(1.0); // Move to maximum position
        }


        telemetry.addData("servo pos", servoPos);
        telemetry.addData("Actual Position", servo.getPosition());

        if (servo != null) {
            telemetry.addData("Servo Initialized", "Yes");
        } else {
            telemetry.addData("Servo Initialized", "No");
        }

        telemetry.update();
    }
}