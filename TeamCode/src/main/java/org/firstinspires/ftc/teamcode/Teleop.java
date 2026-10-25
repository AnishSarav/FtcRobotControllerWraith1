package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "Wraith Master TeleOp")
public class Teleop extends LinearOpMode {

    // Drive Motors
    private DcMotorEx frontLeft;
    private DcMotorEx frontRight;
    private DcMotorEx backLeft;
    private DcMotorEx backRight;

    // Mechanism Motors
    private DcMotorEx intake;
    private DcMotorEx ramp;
    private DcMotorEx shooter;

    @Override
    public void runOpMode() {
        // Initialize Drive Motors
        frontLeft  = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft   = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight  = hardwareMap.get(DcMotorEx.class, "backRight");

        // Initialize Mechanism Motors
        intake  = hardwareMap.get(DcMotorEx.class, "intake");
        ramp    = hardwareMap.get(DcMotorEx.class, "ramp");
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");

        // Motors on the left side are mounted mirrored, so reverse them
        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        backLeft.setDirection(DcMotorEx.Direction.REVERSE);

        telemetry.addData("Status", "Initialized. Ready to rumble!");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // --- 1. MECANUM DRIVE LOGIC ---
            double drive  = gamepad1.left_stick_y;
            double strafe =  gamepad1.left_stick_x;
            double turn   =  gamepad1.right_stick_x;

            double fl = drive + strafe + turn;
            double fr = drive - strafe - turn;
            double bl = drive - strafe + turn;
            double br = drive + strafe - turn;

            double max = Math.max(1.0, Math.max(
                    Math.max(Math.abs(fl), Math.abs(fr)),
                    Math.max(Math.abs(bl), Math.abs(br))));

            frontLeft.setPower(fl / max);
            frontRight.setPower(fr / max);
            backLeft.setPower(bl / max);
            backRight.setPower(br / max);

            // --- 2. INTAKE & RAMP LOGIC (Using A and B buttons) ---
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

            // --- 3. SHOOTER LOGIC (Using X button) ---
            if (gamepad1.x) {
                shooter.setPower(1.0);
            } else {
                shooter.setPower(0.0);
            }

            // --- TELEMETRY ---
            telemetry.addData("Drive Power", "fl: %.2f, fr: %.2f", fl/max, fr/max);
            telemetry.addData("Intake Power", intake.getPower());
            telemetry.addData("Shooter Power", shooter.getPower());
            telemetry.update();
        }
    }
}