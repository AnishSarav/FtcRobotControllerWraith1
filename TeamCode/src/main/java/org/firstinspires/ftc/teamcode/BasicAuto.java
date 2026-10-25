package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Leave/Park Auto", group = "Autonomous")
public class BasicAuto extends LinearOpMode {

    private DcMotorEx frontLeft, frontRight;
    private DcMotorEx backLeft, backRight;

    // 0 = Leave Auto, 1 = Park Auto
    private int autoMode = 0;

    @Override
    public void runOpMode() {

        frontLeft  = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft   = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight  = hardwareMap.get(DcMotorEx.class, "backRight");

        // Reverse left motors so forward movement corresponds to positive power
        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        backLeft.setDirection(DcMotorEx.Direction.REVERSE);

        // Set zero power behavior to BRAKE for crisp stops
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Init loop for selecting autonomous routine before pressing PLAY
        while (!isStarted() && !isStopRequested()) {

            if (gamepad1.dpad_left) {
                autoMode = 0;
            }

            if (gamepad1.dpad_right) {
                autoMode = 1;
            }

            telemetry.addLine("SELECT AUTONOMOUS");
            telemetry.addLine("--------------------------------");
            telemetry.addLine((autoMode == 0 ? ">> " : "   ") + "LEFT: Leave Auto");
            telemetry.addLine((autoMode == 1 ? ">> " : "   ") + "RIGHT: Park Auto");
            telemetry.addLine("--------------------------------");
            telemetry.addLine("Press PLAY to start!");
            telemetry.update();

            sleep(20);
        }

        // Main autonomous logic after start is pressed
        if (opModeIsActive()) {

            if (autoMode == 0) {
                // LEAVE AUTO: move forward
                driveFor(0.25, 0, 0, 0.8);

            } else {
                // PARK AUTO: move forward, then strafe left
                driveFor(0.25, 0, 0, 0.6);

                if (opModeIsActive()) {
                    driveFor(0, -0.25, 0, 0.8);
                }
            }
        }

        stopRobot();
    }

    private void driveFor(double drive, double strafe, double turn, double seconds) {

        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        while (opModeIsActive() && timer.seconds() < seconds) {

            // Standard Mecanum Kinematics
            double fl = drive + strafe + turn;
            double fr = drive - strafe - turn;
            double bl = drive - strafe + turn;
            double br = drive + strafe - turn;

            // Normalize powers if any magnitude exceeds 1.0
            double max = Math.max(Math.abs(fl), Math.max(Math.abs(fr),
                    Math.max(Math.abs(bl), Math.abs(br))));

            if (max > 1.0) {
                fl /= max;
                fr /= max;
                bl /= max;
                br /= max;
            }

            frontLeft.setPower(fl);
            frontRight.setPower(fr);
            backLeft.setPower(bl);
            backRight.setPower(br);

            idle();
        }

        stopRobot();
    }

    private void stopRobot() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
}