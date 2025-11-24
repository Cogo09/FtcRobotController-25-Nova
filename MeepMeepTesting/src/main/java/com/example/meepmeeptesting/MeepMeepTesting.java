package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

    public class MeepMeepTesting {
        public static void main(String[] args) {
            MeepMeep meepMeep = new MeepMeep(800);

            RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                    // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                    .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                    .build();

            myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(56, 10, 0))
                            .turnTo(Math.toRadians(180))
                            .lineToX(35)
                            .turnTo(Math.toRadians(90))
                            .lineToY(40)
                            .lineToY(10)
                            .turnTo(Math.toRadians(180))
                            .lineToX(-15)
                            .turnTo(Math.toRadians(180))
                    .build());

            meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_OFFICIAL)
                    .setDarkMode(true)
                    .setBackgroundAlpha(0.95f)
                    .addEntity(myBot)
                    .start();
        }
    }
