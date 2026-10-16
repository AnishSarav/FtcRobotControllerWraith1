package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Intake and Ramp Control", group="Linear Opmode")
public class IntakeRampControl extends LinearOpMode {

    private DcMotor intake = null;
    private DcMotor ramp = null;

    @Override
    public void runOpMode() {
        // Initialize the hardware variables from the hardware map
        intake = hardwareMap.get(DcMotor.class, "intake");
        ramp = hardwareMap.get(DcMotor.class, "ramp");

        // Optional: Set motor directions if they spin the wrong way
        // intake.setDirection(DcMotor.Direction.FORWARD);
        // ramp.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {

            // Let's use a button, for example, holding down the A button to run both motors
            if (gamepad1.a) {
                intake.setPower(1.0);  // Run intake forward at full speed
                ramp.setPower(1.0);    // Run ramp forward at full speed
            } else if (gamepad1.b) {
                // Optional: Reverse them if you need to spit balls out
                intake.setPower(-1.0);
                ramp.setPower(-1.0);
            } else {
                // Turn both off when no button is pressed
                intake.setPower(0.0);
                ramp.setPower(0.0);
            }

            // Alternatively, if you want to control intake speed with a trigger (like right trigger):
            /*
            double intakePower = gamepad1.right_trigger;
            intake.setPower(intakePower);
            ramp.setPower(intakePower); // Ramp matches whatever power the intake has
            */

            telemetry.addData("Intake Power", intake.getPower());
            telemetry.addData("Ramp Power", ramp.getPower());
            telemetry.update();
        }
    }
}