package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.robot.MainConfig;
import org.firstinspires.ftc.teamcode.robotcorelib.opmode.OpModePipeline;
import org.firstinspires.ftc.teamcode.robotcorelib.robot.Robot;
import org.firstinspires.ftc.teamcode.robotcorelib.util.RobotRunMode;

@TeleOp(name = "alanTestOpmode")
public class alanTestOpmode extends OpModePipeline {
    MainConfig subsystems = new MainConfig();

    @Override
    public void init() {
        super.subsystems = subsystems;
        runMode = RobotRunMode.TELEOP;
        telemetry.update();
        super.init();
    }

    public void loop() {
        Robot.update();         // Update robot and subsystems

//        // Run intake
//        subsystems.intake.run(
//                gamepad1.left_trigger,       // Roll decrement
//                gamepad1.right_trigger,      // Roll increment
//                gamepad1.left_bumper,        // 90-degree roll
//                gamepad1.right_bumper        // Open/close claw
//        );

        // Run lift controls
        double manualLift = -gamepad2.left_stick_y;      // Manual lift control
        boolean goToTop = gamepad2.right_bumper;         // Move to top position
        boolean goToBottom = gamepad2.left_bumper;       // Move to bottom position
        subsystems.lift2.run(manualLift, goToTop, goToBottom); // Update lift with gamepad inputs

//        // Run arm controls
//        boolean x = gamepad2.a;
//        boolean circle = gamepad2.b;
//        boolean square = gamepad2.x;
//        boolean triangle = gamepad2.y;
//        subsystems.arm.run(x, circle, square, triangle);

        // Run mecanum drivetrain
        subsystems.drivetrain.mecanumDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

        // Display lift telemetry
       // subsystems.lift2.telemetry(telemetry);
       // subsystems.intake.addTelemetry(telemetry); // Display telemetry

        telemetry.update();
    }
}
