package org.firstinspires.ftc.teamcode.HARDWARES;

import static com.qualcomm.robotcore.eventloop.opmode.OpMode.blackboard;
import static org.firstinspires.ftc.teamcode.HARDWARES.UPPERPOWERFILE.upperpowerbound;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TimeTurn;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


import org.ejml.equation.IntegerSequence;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.SUBS.SERVOSUB;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.gentrifiedApps.gentrifiedAppsUtil.classes.Scribe;

import java.util.List;
//import org.firstinspires.ftc.teamcode.SUBS.CLAWSUB;

public class HARDWARECONFIG {
    boolean slowmode = false;
    Telemetry telemetry = null;
    LinearOpMode opMode = null;
    public SERVOSUB servosub = null;
    //public org.firstinspires.ftc.teamcode.SUBS.ARMSUB armSub = null;
    DcMotor frontLeftMotor = null;
    DcMotor backLeftMotor = null;
    DcMotor frontRightMotor = null;
    DcMotor backRightMotor = null;
   // Limelight3A limelight = null;
    DcMotor gunmotorR = null;
    DcMotor gunmotorL = null;
    DcMotor intakeR = null;
    DcMotor intakeL = null;
    double heading = 0;
    double distance = 0;
    Pose2d startPose = null;
    private IMU imu = null;      // Control/Expansion Hub IMU
    MecanumDrive drive = null;
    FtcDashboard dash = null;
    double x = 0;
    double y = 0;




    double color = 0;

    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;
    private RevColorSensorV3 colorSensor;

    ElapsedTime elapsedTime = null;

    public HARDWARECONFIG(LinearOpMode om, HardwareMap hwmap, Boolean auto) {
        initrobot(hwmap, om, auto);
        servosub = new SERVOSUB(hwmap);

    }

    void initrobot(HardwareMap hwmap, LinearOpMode om, Boolean auto) {
        opMode = om;//
        telemetry = om.telemetry;
        //clawsub = new CLAWSUB(hwmap);
       // armSub = new org.firstinspires.ftc.teamcode.SUBS.ARMSUB(hwmap, auto);
        frontLeftMotor = hwmap.dcMotor.get("frontLeftMotor");
        backLeftMotor = hwmap.dcMotor.get("backLeftMotor");
        frontRightMotor = hwmap.dcMotor.get("frontRightMotor");
        backRightMotor = hwmap.dcMotor.get("backRightMotor");
        gunmotorR = hwmap.dcMotor.get("gunmotorR");
        gunmotorL = hwmap.dcMotor.get("gunmotorL");
        intakeR = hwmap.dcMotor.get("intakeR");
        intakeL = hwmap.dcMotor.get("intakeL");
        dash = FtcDashboard.getInstance();;

        drive = new MecanumDrive(hwmap, (Pose2d) blackboard.getOrDefault(currentpose, new Pose2d(0,0,0)));

//         limelight = hwmap.get(Limelight3A.class, "limelight");
//        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeL.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeR.setDirection(DcMotorSimple.Direction.FORWARD);
        gunmotorL.setDirection(DcMotorSimple.Direction.FORWARD);
        gunmotorR.setDirection(DcMotorSimple.Direction.REVERSE);

        aprilTag = new AprilTagProcessor.Builder()

                // The following default settings are available to un-comment and edit as needed.
                .setDrawAxes(true)
                .setDrawCubeProjection(false)
                .setDrawTagOutline(true)
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .setTagLibrary(AprilTagGameDatabase.getDecodeTagLibrary())
                .setOutputUnits(DistanceUnit.INCH, AngleUnit.RADIANS)

                // == CAMERA CALIBRATION ==
                // If you do not manually specify calibration parameters, the SDK will attempt
                // to load a predefined calibration for your camera.
                .setLensIntrinsics(572.066, 572.066, 327.923, 257.789)
                // ... these parameters are fx, fy, cx, cy.


                .build();
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
            builder.setCamera(hwmap.get(WebcamName.class, "Webcam 1"));
        builder.addProcessor(aprilTag);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();
        FtcDashboard.getInstance().startCameraStream(visionPortal,60);

        elapsedTime = new ElapsedTime();
    }
    public int getrandomization(){
        //apriltags need to find the green 1 for first pos 2 for second pos 3 for third.
        //21,22,23
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        for (AprilTagDetection detection : currentDetections) {
            if (detection.id >= 21 && detection.id <=23) {
                return detection.id - 20;

            }

        }return 0;
    }
    public static String currentpose = "currentpose";


    public Action Turn(){
        return drive.actionBuilder(drive.localizer.getPose())
                .turnTo(getheadingfromAT()).build();
    }
    public void lockit(){
        TelemetryPacket p = new TelemetryPacket();
        Action t = Turn();
        t.run(p);
        dash.sendTelemetryPacket(p);
    }

    public double getrangefromAT(){
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        for (AprilTagDetection detection : currentDetections) {
            if (detection.id == 20 || detection.id == 24){
                return detection.ftcPose.range;
            }

        }return -1;

    }
    public double getheadingfromAT(){
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        for (AprilTagDetection detection : currentDetections) {
            if (detection.id == 20 || detection.id ==24){
                double degrees = detection.ftcPose.pitch;
                Scribe.getInstance().logData(degrees);
                return degrees;
            }

        }return -999999999;
    }
    public double getx(){
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        for (AprilTagDetection detection : currentDetections) {
            return detection.ftcPose.x;
        }return -99999;
    }
    public double gety(){
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        for (AprilTagDetection detection : currentDetections) {
            return detection.ftcPose.y;
        }return -99999;
    }

    public void buildtelemetry() {
        telemetry.addData("slowmode", slowmode);
        telemetry.addData("heading",heading);
        telemetry.addData("distance",distance);
        telemetry.addData("Power",upperpowerbound);
        telemetry.addData("x",x);
        telemetry.addData("y",y);
       // armSub.telemetry(telemetry);
        telemetry.update();
    }

    boolean touchpadwpressed = false;

    public void dobulk() {//
        heading = getheadingfromAT();
        x = getx();
        y = gety();
        distance = getrangefromAT();
        double y = -opMode.gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double x = opMode.gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
        double rx = opMode.gamepad1.right_stick_x;
        boolean touchpadpressed = opMode.gamepad1.touchpad;
        if (touchpadpressed && !touchpadwpressed) {
            slowmode = !slowmode;
        }
        touchpadwpressed = touchpadpressed;
        double slowmodemultiplier = 1.5;


        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double multiplier = 1;
        if (slowmode) {
            multiplier = slowmodemultiplier;
        }
        double frontLeftPower = ((y + x + rx) / denominator) * multiplier;
        double backLeftPower = ((y - x + rx) / denominator) * multiplier;
        double frontRightPower = ((y - x - rx) / denominator) * multiplier;
        double backRightPower = ((y + x - rx) / denominator) * multiplier;
        double gunmotorPower = Range.clip(opMode.gamepad1.right_trigger, -1, 1);
        double gunmotorPowerL = Range.clip(opMode.gamepad1.right_trigger, -1, 1);



        double armpower = 0;


//           double clawpower = 0.5;
//        if (opMode.gamepad1.right_bumper) {
//            clawsub.setClawCLOSE();   // keep 90 always
//        } else if (opMode.gamepad1.left_bumper) {
//            clawsub.setClawOPEN();  //  keep at 60 increase  to open less
//        }
//        if (opMode.gamepad1.y) {
//            clawsub.setPrimeTOP();// press b first ALWAYS.
//        } else if (opMode.gamepad1.a) {
//            clawsub.setPrimeBOTTOM();//change degrees in small increments.
//        } else if (opMode.gamepad1.b) {
//            clawsub.setPrimeMIDDLE();// keep for halfway
//        } //drive forward down and open simultaniously
//        if (opMode.gamepad1.x) {
//            clawsub.setPrimeLOW();
//
//        }
//        if (opMode.gamepad2.a) {
//            clawsub.setHangBOTTOM();
//
//        }
//        if (opMode.gamepad2.b) {
//            clawsub.setHangMIDDLE();
//        }
//        if (opMode.gamepad2.y) {
//            clawsub.setHangTOP();
//        }
//        if (opMode.gamepad2.right_bumper) {
//            clawsub.setUClawOPEN();
//        } else if (opMode.gamepad2.left_bumper) {
//            clawsub.setUClawCLOSE();
//
//        }

//        if (opMode.gamepad2.dpad_up) {
//            armSub.setUptarget(2200);
//        } else if (opMode.gamepad2.dpad_down) {
//            armSub.setUptarget(100);//k
//        }
//        if (opMode.gamepad2.dpad_right) {
//            armSub.setUptarget(900);
//            clawsub.setHangBOTTOM();
//        }
//        if (opMode.gamepad2.dpad_left) {
//            armSub.setUptarget(280);
//            clawsub.setHangMIDDLE();
//        }


        if (opMode.gamepad1.right_bumper) {
            intakeR.setDirection(DcMotorSimple.Direction.REVERSE);
            intakeR.setPower(1);
            intakeL.setDirection(DcMotorSimple.Direction.FORWARD);
            intakeL.setPower(1);
        }
        else if (opMode.gamepad1.left_bumper){
            intakeR.setDirection(DcMotorSimple.Direction.FORWARD);
            intakeR.setPower(1);
            intakeL.setDirection(DcMotorSimple.Direction.REVERSE);
            intakeL.setPower(1);
        }

        else {
            intakeL.setPower(0);
            intakeR.setPower(0);
        }



//        if (getrangefromAT() >=100 && getrangefromAT()<=140){
//            upperpowerbound = 1;
//        } else if (getrangefromAT() >=70 && getrangefromAT()<=99) {
//            upperpowerbound = 0.7;
//        } else {
//            upperpowerbound = 1;
//        }

        if (opMode.gamepad2.right_trigger > 0) {
            gunmotorR.setDirection(DcMotorSimple.Direction.REVERSE);

            gunmotorR.setPower(upperpowerbound);
            gunmotorL.setDirection(DcMotorSimple.Direction.FORWARD);
            gunmotorL.setPower(upperpowerbound);
        } else {
            gunmotorR.setPower(0);
            gunmotorL.setPower(0);
        }
        if (opMode.gamepad2.left_trigger>0){
            gunmotorR.setDirection(DcMotorSimple.Direction.FORWARD);
            gunmotorR.setPower(0.5);
            gunmotorL.setDirection(DcMotorSimple.Direction.REVERSE);
            gunmotorL.setPower(0.5);
        }
        if (opMode.gamepad1.left_trigger > 0){

        }
        if (opMode.gamepad2.left_bumper){
            lockit();
            //LOCKIT SHOULD TURN TO AT HEADING>
        }

        if (opMode.gamepad2.dpad_left) {
            servosub.LELEup();
        }else if (opMode.gamepad2.dpad_down) {
            servosub.LELEdown();
        }

        if (opMode.gamepad2.dpad_right) {
            servosub.RELEdown();
        }else if (opMode.gamepad2.dpad_down) {
            servosub.RELEup();
        }

        if (opMode.gamepad2.dpad_up) {
            servosub.MELEdown();
        }else if (opMode.gamepad2.dpad_down){
            servosub.MELEup();
        }
        if (opMode.gamepad2.y){
            servosub.Bootup();
        }else{
            servosub.Bootdown();
        }


        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);
        gunmotorR.setPower(gunmotorPower);
        gunmotorL.setPower(gunmotorPowerL);

        servosub.update();
        //armSub.update();

        buildtelemetry();

    }
}

