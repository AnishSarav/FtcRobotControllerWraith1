package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Shooter Only Control", group="Linear Opmode")
public class shooter extends LinearOpMode {

    private DcMotorEx shooter = null;

    @Override
    public void runOpMode() {
        // Initialize the shooter motor from the hardware map
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");

        // Optional: Set motor direction if it spins the wrong way
        // shooter.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Status", "Shooter Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            // Hold X to run the shooter motor, let go to stop
            if (gamepad1.x) {
                shooter.setPower(1.0); // Adjust speed here (e.g., 1.0 for max speed)
            } else {
                shooter.setPower(0.0);
            }

            // Telemetry update for Driver Station
            telemetry.addData("Shooter Power", shooter.getPower());
            telemetry.update();
        }
    }
}