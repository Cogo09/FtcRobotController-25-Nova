package org.firstinspires.ftc.teamcode.SUBS;

import static org.firstinspires.ftc.teamcode.UTILITIES.UTIL.setpose;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.HARDWARES.SERVOUTIL;

import java.util.List;

public class SERVOSUB {
    private final Servo leftelevatorservo;
    private final Servo middleelevatorservo;
    private final Servo rightelevatorservo;


    public enum LeftELEState {UP, DOWN, IDLE}

    private LeftELEState LeftELEStateVar = LeftELEState.IDLE;

    public void LELEup() {
        LeftELEStateVar = LeftELEState.UP;
    }

    public void LELEdown() {
        LeftELEStateVar = LeftELEState.DOWN;
    }

    public void LELEIDLE() {
        LeftELEStateVar = LeftELEState.IDLE;
    }

    public enum RightELEState {UP, DOWN, IDLE}

    private RightELEState RightELEStateVar = RightELEState.IDLE;

    public void RELEup() {
        RightELEStateVar = RightELEState.UP;
    }

    public void RELEdown() {
        RightELEStateVar = RightELEState.DOWN;
    }

    public void RELEIDLE() {
        RightELEStateVar = RightELEState.IDLE;
    }

    public enum MiddleELEState {UP, DOWN, IDLE}

    private MiddleELEState MiddleELEStateVar = MiddleELEState.IDLE;

    public void MELEup() {
        MiddleELEStateVar = MiddleELEState.UP;
    }

    public void MELEdown() {
        MiddleELEStateVar = MiddleELEState.DOWN;
    }

    public void MELEIDLE() {
        MiddleELEStateVar = MiddleELEState.IDLE;
    }



    //this is where you put all enums and variables
    public SERVOSUB(HardwareMap hwMap) {
        leftelevatorservo = hwMap.get(Servo.class, "leftelevatorservo");
        middleelevatorservo = hwMap.get(Servo.class, "middleelevatorservo");
        rightelevatorservo = hwMap.get(Servo.class, "rightelevatorservo");
        leftelevatorservo.setDirection(Servo.Direction.FORWARD);
        middleelevatorservo.setDirection(Servo.Direction.FORWARD);
        rightelevatorservo.setDirection(Servo.Direction.REVERSE);

    }

    public void update() {
        // this is where you put your state machines and all power functions (call this in our main code)

        switch (LeftELEStateVar) {
            case UP:
                setpose(leftelevatorservo, SERVOUTIL.leftelevatorservoup);
                break;
            case DOWN:
                setpose(leftelevatorservo, SERVOUTIL.leftelevatorservodown);
                break;
            case IDLE:

                break;
        }
        switch (RightELEStateVar) {
                case UP:
                setpose(rightelevatorservo, SERVOUTIL.rightelevatorservoup);
                break;
            case DOWN:
                setpose(rightelevatorservo, SERVOUTIL.rightelevatorservodown);
                break;
            case IDLE:

                break;
        }
        switch (MiddleELEStateVar) {
            case UP:
                setpose(middleelevatorservo, SERVOUTIL.middleelevatorservoup);
                break;
            case DOWN:
                setpose(middleelevatorservo, SERVOUTIL.middleelevatorservodown);
                break;
            case IDLE:

                break;
        }


    }

    // this is where you put your update functions to switch between states
    public void telemetry(Telemetry telemetry) {
        // add telemetry data here

    }

    public Action ServoAction(SERVOSUB servosub, List<Runnable> funcs) {
        return new ServoAction(servosub,funcs);
    }

    class ServoAction implements Action {
        List<Runnable> funcs;
        private SERVOSUB servosub;

        public ServoAction(SERVOSUB servosub, List<Runnable> funcs) {
            this.funcs = funcs;
            this.servosub = servosub;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            for (Runnable func : funcs) {
                func.run();
            }
            //SERVOSUB.update();// removes the need for the update to be run after simply updating a claw

            return false;
        }
    }
}