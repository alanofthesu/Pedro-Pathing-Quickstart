package org.firstinspires.ftc.teamcode.robot;

import org.firstinspires.ftc.teamcode.robotcorelib.robot.RobotConfig;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
//import org.firstinspires.ftc.teamcode.subsystems.HorizontalExtendo;
//import org.firstinspires.ftc.teamcode.subsystems.Intake;
//import org.firstinspires.ftc.teamcode.subsystems.Lift;
//import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Lift2;

public class MainConfig extends RobotConfig {

    public Drivetrain drivetrain;
    //public Intake intake;
    //public Lift lift;
    //public Arm arm;
    //public HorizontalExtendo horizontalExtendo;
    public Lift2 lift2;

    @Override
    public void init() {
        subsystems.clear();
        drivetrain = new Drivetrain();
       // intake = new Intake();
     //   lift = new Lift(hardwareMap);
       // arm = new Arm();
        //horizontalExtendo = new HorizontalExtendo();
        lift2 = new Lift2();
    }
}