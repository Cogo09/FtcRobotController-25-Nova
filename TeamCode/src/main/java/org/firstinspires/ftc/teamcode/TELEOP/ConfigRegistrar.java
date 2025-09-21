package org.firstinspires.ftc.teamcode.TELEOP;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;

import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;

import org.gentrifiedApps.gentrifiedAppsUtil.config.ConfigMaker;
import org.gentrifiedApps.gentrifiedAppsUtil.config.ConfigCreator;

public final class ConfigRegistrar {

    static ConfigMaker config = new ConfigMaker("Generated_ROBOTO")
            
            .addMotor("gunmotor", ConfigMaker.ModuleType.CONTROL_HUB, ConfigMaker.MotorType.goBILDA5201SeriesMotor, 0)
.addMotor("gunmotorl", ConfigMaker.ModuleType.CONTROL_HUB, ConfigMaker.MotorType.goBILDA5201SeriesMotor, 1);

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
    