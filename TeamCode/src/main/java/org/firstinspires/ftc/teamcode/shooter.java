package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Intake Ramp and Shooter Control", group="Linear Opmode")
public class shooter extends LinearOpMode {

    private DcMotor intake = null;
    private DcMotor ramp = null;
    private DcMotor shooter = null;

    @Override
    public void runOpMode() {
        // Initialize all hardware variables from the hardware map
        intake = hardwareMap.get(DcMotor.class, "intake");
        ramp = hardwareMap.get(DcMotor.class, "ramp");
        shooter = hardwareMap.get(DcMotor.class, "shooter");

        // Optional: Set motor directions if they spin the wrong way
        // intake.setDirection(DcMotor.Direction.FORWARD);
        // ramp.setDirection(DcMotor.Direction.FORWARD);
        // shooter.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            // 1. Intake and Ramp Control (Hold A to run, B to reverse)
            if (gamepad1.a) {
                intake.setPower(1.0);
                ramp.setPower(1.0);
            } else if (gamepad1.b) {
                intake.setPower(-1.0);
                ramp.setPower(-1.0);
            } else {
                intake.setPower(0.0);
                ramp.setPower(0.0);
            }

            // 2. Shooter Control (Hold X to run the shooter motor)
            if (gamepad1.x) {
                shooter.setPower(1.0); // Adjust speed here (e.g., 1.0 for max speed)
            } else {
                shooter.setPower(0.0);
            }

            // Telemetry updates for Driver Station
            telemetry.addData("Intake Power", intake.getPower());
            telemetry.addData("Ramp Power", ramp.getPower());
            telemetry.addData("Shooter Power", shooter.getPower());
            telemetry.update();
        }
    }
}