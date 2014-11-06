/*
 * Copyright 2014 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.terasology.musicdirector;

import javax.vecmath.Vector3f;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.asset.Assets;
import org.terasology.audio.AudioManager;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.logic.players.LocalPlayer;
import org.terasology.registry.In;
import org.terasology.world.time.WorldTimeEvent;

/**
 * Play different music assets based on world time
 * @author Martin Steiger
 */
@RegisterSystem
public class MusicDirectorSystem extends BaseComponentSystem {

    private static final Logger logger = LoggerFactory.getLogger(MusicDirectorSystem.class);

    @In
    private LocalPlayer localPlayer;

    @In
    private AudioManager audioManager;

    @ReceiveEvent
    public void onTimeEvent(WorldTimeEvent event, EntityRef worldEntity) {

        // SUNRISE
        if (event.matchesDaily(0.35f)) {
            logger.debug("Playing sunrise theme..");

            if (getPlayerPosition().y < 50) {
                audioManager.playMusic(Assets.getMusic("LegacyMusic:SpacialWinds"));
            } else if (getPlayerPosition().y > 175) {
                audioManager.playMusic(Assets.getMusic("ChrisVolume1OST:Heaven"));
            } else {
                audioManager.playMusic(Assets.getMusic("LegacyMusic:Sunrise"));
            }
        }

        // AFTERNOON
        if (event.matchesDaily(0.50f)) {
            logger.debug("Playing afternoon theme..");

            if (getPlayerPosition().y < 50) {
                audioManager.playMusic(Assets.getMusic("ChrisVolume1OST:DwarfForge"));
            } else if (getPlayerPosition().y > 175) {
                audioManager.playMusic(Assets.getMusic("LegacyMusic:SpaceExplorers"));
            } else {
                audioManager.playMusic(Assets.getMusic("LegacyMusic:Afternoon"));
            }
        }

        // SUNSET
        if (event.matchesDaily(0.65f)) {
            logger.debug("Playing sunset theme..");

            if (getPlayerPosition().y < 50) {
                audioManager.playMusic(Assets.getMusic("ChrisVolume1OST:OrcFortress"));
            } else if (getPlayerPosition().y > 175) {
                audioManager.playMusic(Assets.getMusic("ChrisVolume1OST:PeacefulWorld"));
            } else {
                audioManager.playMusic(Assets.getMusic("LegacyMusic:Sunset"));
            }
        }

        // NIGHT
        if (event.matchesDaily(0.85f)) {
            logger.debug("Playing night theme..");

            if (getPlayerPosition().y < 50) {
                audioManager.playMusic(Assets.getMusic("LegacyMusic:CreepyCaves"));
            } else if (getPlayerPosition().y > 175) {
                audioManager.playMusic(Assets.getMusic("ChrisVolume1OST:ShootingStars"));
            } else {
                audioManager.playMusic(Assets.getMusic("LegacyMusic:Dimlight"));
            }
        }

        // NIGHT
        if (event.matchesDaily(0.00f)) {
            logger.debug("Playing night theme..");

            if (getPlayerPosition().y < 50) {
                audioManager.playMusic(Assets.getMusic("LegacyMusic:CreepyCaves"));
            } else if (getPlayerPosition().y > 175) {
                audioManager.playMusic(Assets.getMusic("ChrisVolume1OST:NightTheme"));
            } else {
                audioManager.playMusic(Assets.getMusic("LegacyMusic:OtherSide"));
            }
        }

        // BEFORE SUNRISE
        if (event.matchesDaily(0.15f)) {
            logger.debug("Playing before sunrise theme..");

            if (getPlayerPosition().y < 50) {
                audioManager.playMusic(Assets.getMusic("LegacyMusic:CreepyCaves"));
            } else if (getPlayerPosition().y > 175) {
                audioManager.playMusic(Assets.getMusic("ChrisVolume1OST:Heroes"));
            } else {
                audioManager.playMusic(Assets.getMusic("LegacyMusic:Resurface"));
            }
        }

    }

    private Vector3f getPlayerPosition() {
        return localPlayer.getPosition();
    }
}
