package org.firstinspires.ftc.teamcode.TELEOP;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;

import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;

import org.gentrifiedApps.gentrifiedAppsUtil.config.ConfigMaker;
import org.gentrifiedApps.gentrifiedAppsUtil.config.ConfigCreator;

public final class ConfigRegistrar {

    static ConfigMaker config = new ConfigMaker("Generated_ROBOTO")
            .addCamera("Webcam 1","UC684")
            .addModule(ConfigMaker.ModuleType.EXPANSION_HUB, "Expansion Hub 2",2)
            .addMotor("frontLeftMotor", ConfigMaker.ModuleType.CONTROL_HUB, ConfigMaker.MotorType.goBILDA5201SeriesMotor, 0)
            .addMotor("backLeftMotor", ConfigMaker.ModuleType.CONTROL_HUB, ConfigMaker.MotorType.goBILDA5201SeriesMotor, 1)
            .addMotor("frontRightMotor", ConfigMaker.ModuleType.CONTROL_HUB, ConfigMaker.MotorType.goBILDA5201SeriesMotor, 2)
            .addMotor("backRightMotor", ConfigMaker.ModuleType.CONTROL_HUB, ConfigMaker.MotorType.goBILDA5201SeriesMotor, 3)
            .addDevice("colorSensor", ConfigMaker.ModuleType.CONTROL_HUB, ConfigMaker.DeviceType.RevColorSensorV3,0)
            .addDevice("leftelevatorservo", ConfigMaker.ModuleType.EXPANSION_HUB, ConfigMaker.DeviceType.Servo,0)
            .addDevice("middleelevatorservo", ConfigMaker.ModuleType.EXPANSION_HUB, ConfigMaker.DeviceType.Servo,1)
            .addDevice("rightelevatorservo", ConfigMaker.ModuleType.EXPANSION_HUB, ConfigMaker.DeviceType.Servo,2)
            .addDevice("bootkickerservo",ConfigMaker.ModuleType.EXPANSION_HUB, ConfigMaker.DeviceType.Servo,3)
            .addMotor("gunmotorR", ConfigMaker.ModuleType.EXPANSION_HUB, ConfigMaker.MotorType.goBILDA5202SeriesMotor, 2)
            .addMotor("gunmotorL", ConfigMaker.ModuleType.EXPANSION_HUB, ConfigMaker.MotorType.goBILDA5202SeriesMotor, 3)
            .addMotor("intakeL", ConfigMaker.ModuleType.EXPANSION_HUB, ConfigMaker.MotorType.goBILDA5201SeriesMotor, 0)
            .addMotor("intakeR", ConfigMaker.ModuleType.EXPANSION_HUB, ConfigMaker.MotorType.goBILDA5201SeriesMotor, 1)
            .addMotor("gunmotorR", ConfigMaker.ModuleType.EXPANSION_HUB, ConfigMaker.MotorType.goBILDA5201SeriesMotor, 2)
            .addDevice("imu",ConfigMaker.ModuleType.EXPANSION_HUB,ConfigMaker.DeviceType.AdafruitBNO055IMU,1)
            .addMotor("gunmotorL", ConfigMaker.ModuleType.EXPANSION_HUB, ConfigMaker.MotorType.goBILDA5201SeriesMotor, 3);

    static boolean isEnabled = true;
    private ConfigRegistrar() {
    }

    private static OpModeMeta metaForClass(Class<? extends OpMode> cls) {
        return new OpModeMeta.Builder()
                .setName(cls.getSimpleName())
                .setGroup("Config")
                .setFlavor(OpModeMeta.Flavor.TELEOP)
                .build();
    }

    @OpModeRegistrar
    public static void register(OpModeManager manager) {
        if (!isEnabled) return;
        manager.register(metaForClass(ConfigCreator.class), new ConfigCreator(config));
    }
}
    