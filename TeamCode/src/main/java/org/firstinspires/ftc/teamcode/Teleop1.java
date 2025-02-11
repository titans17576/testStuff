package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.usb.RobotArmingStateNotifier;
import com.qualcomm.robotcore.util.Range;

import java.util.HashMap;

@TeleOp(name="Teleop1")
public class Teleop1 extends LinearOpMode {
    @Override
    public void runOpMode(){
        robot R = new robot(hardwareMap);

        Gamepad currentGamepad1 = new Gamepad();
        Gamepad previousGamepad1 = new Gamepad();

        turretFSM TurretFSM = new turretFSM(R, telemetry, currentGamepad1, previousGamepad1);
        liftFSM LiftFSM = new liftFSM(R, telemetry, currentGamepad1, previousGamepad1);
        armFSM ArmFSM = new armFSM(R, telemetry, currentGamepad1, previousGamepad1);
        clawFSM ClawFSM = new clawFSM(R, telemetry, currentGamepad1, previousGamepad1);

        driveControls DriveControls = new driveControls(R, currentGamepad1,previousGamepad1);
        R.leftSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        R.rightSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        waitForStart();

        DriveControls.initialize();

        while(opModeIsActive()){
            // Previous gamepad implementation code
            previousGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);

            // Turret finite state machine update
            TurretFSM.teleopUpdate();

            // Lift finite state machine update
            LiftFSM.teleopUpdate();

            // Extension arm finite state machine update
            ArmFSM.teleopUpdate();

            // Claw finite state machine update
            ClawFSM.teleopUpdate();

            // Drive control update
            DriveControls.drive();

            // Update telemetry data
            telemetry.update();
        }
    }
}
;