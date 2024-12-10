package org.firstinspires.ftc.teamcode.teleop;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.MainConfig;
import org.firstinspires.ftc.teamcode.robotcorelib.opmode.OpModePipeline;
import org.firstinspires.ftc.teamcode.robotcorelib.robot.Robot;
import org.firstinspires.ftc.teamcode.robotcorelib.util.RobotRunMode;


@TeleOp(name = "LiftTest")

public class LiftTest extends OpModePipeline{
    MainConfig subsystems = new MainConfig(); // Initialize MainConfig

    @Override
    public void init() {
        super.subsystems = subsystems;
        runMode = RobotRunMode.TELEOP;
        telemetry.update();
        super.init();
    }

    @Override
    public void loop() {
        Robot.update();

        // Run lift using the controller input
        double manualLift = gamepad2.left_stick_y;      // Manual lift control
        boolean goToTop = gamepad2.right_bumper;         // Move to top position
        boolean goToBottom = gamepad2.left_bumper;       // Move to bottom position
        subsystems.lift2.run(manualLift, goToTop, goToBottom); // Update lift with gamepad inputs

        telemetry.addData("Joystick Input", manualLift);

        subsystems.lift2.telemetry(telemetry);
        telemetry.update();
    }
}
