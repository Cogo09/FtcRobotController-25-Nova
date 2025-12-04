package org.firstinspires.ftc.teamcode.SUBS;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

public class PowerSUB {
    private DcMotor intakeR;
    private DcMotor intakeL;
    private DcMotor gunmotorR;
    private DcMotor gunmotorL;

    public enum gunSTATE {ON, OFF, IDLE}
//!help
    private PowerSUB.gunSTATE gunStateVar = PowerSUB.gunSTATE.IDLE;

    public void gunon() {
        gunStateVar = gunSTATE.ON;
    }

    public void gunoff() {
        gunStateVar = gunSTATE.OFF;
    }

    public enum intakeSTATE {ON, OFF, IDLE}

    private PowerSUB.intakeSTATE intakeStateVar = PowerSUB.intakeSTATE.IDLE;

    public void intakeon() {
        intakeStateVar = intakeSTATE.ON;
    }

    public void intakeoff() {
        intakeStateVar = intakeSTATE.OFF;
    }
    private HardwareMap hwmap = null;


    //this is where you put all enums and variables
    public PowerSUB(HardwareMap hwMap) {
        this.hwmap = hwMap;
        gunmotorL = hwMap.get(DcMotor.class,"gunmotorR");
        gunmotorR = hwmap.get(DcMotor.class,"gunmotorR");
        intakeR = hwMap.get(DcMotor.class,"intakeR");
        intakeL = hwMap.get(DcMotor.class, "intakeL");
        intakeL.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeR.setDirection(DcMotorSimple.Direction.FORWARD);
        gunmotorL.setDirection(DcMotorSimple.Direction.FORWARD);
        gunmotorR.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void update() {
        // this is where you put your state machines and all power functions (call this in our main code)
        switch (intakeStateVar) {
            case ON:
                intakeR.setPower(1);
                intakeL.setPower(1);
                break;
            case OFF:
                intakeR.setPower(0);
                intakeL.setPower(0);
                break;
            case IDLE:

                break;
        }

        switch (gunStateVar) {
            case ON:
                gunmotorR.setPower(1);
                gunmotorL.setPower(1);
                break;
            case  OFF:
                gunmotorL.setPower(0);
                gunmotorR.setPower(0);
                break;
            case IDLE:

                break;
        }
    }

    // this is where you put your update functions to switch between states
    public void telemetry(Telemetry telemetry) {}
        // add telemetry data here
        public Action MotorAction(PowerSUB powersub, List<Runnable> funcs) {
            return new MotorAction(powersub, funcs);
        }

        class MotorAction implements Action {
            List<Runnable> funcs;
            private PowerSUB powersub;

            public MotorAction(PowerSUB powersub, List<Runnable> funcs) {
                this.funcs = funcs;
                this.powersub = powersub;
            }

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                for (Runnable func : funcs) {
                    func.run();
                }
                powersub.update();// removes the need for the update to be run after simply updating a claw

                return false;
            }
    }
}