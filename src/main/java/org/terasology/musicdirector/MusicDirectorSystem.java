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

import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.asset.AssetUri;
import org.terasology.asset.Assets;
import org.terasology.audio.AudioEndListener;
import org.terasology.audio.AudioManager;
import org.terasology.audio.StreamingSound;
import org.terasology.engine.module.ClassFinder;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.logic.console.Command;
import org.terasology.registry.In;
import org.terasology.world.time.WorldTimeEvent;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Play different music assets through triggers that are
 * provided by {@link MusicRegistrator}s.
 * @author Martin Steiger
 */
@RegisterSystem
public class MusicDirectorSystem extends BaseComponentSystem {

    private static final Logger logger = LoggerFactory.getLogger(MusicDirectorSystem.class);

    @In
    private AudioManager audioManager;

    @In
    private ClassFinder classFinder;

    private StreamingSound current;

    private final Collection<MusicTrigger> triggers = Sets.newLinkedHashSet();

    private final Queue<MusicTrigger> playList = new PriorityQueue<MusicTrigger>(new TriggerComparator());

    private final Queue<AssetUri> history = Lists.newLinkedList();

    @Override
    public void initialise() {
        super.initialise();

        for (Class<? extends MusicRegistrator> registratorClazz : classFinder.getSubtypesOf(MusicRegistrator.class)) {
            try {
                MusicRegistrator reg = registratorClazz.newInstance();
                triggers.addAll(reg.getTriggers());
            }
            catch (Exception e) {
                logger.error("Could not create an instance of {} using its default constructor", registratorClazz.getName());
            }
        }
    }

    @ReceiveEvent
    public void onTimeEvent(WorldTimeEvent event, EntityRef worldEntity) {

        for (MusicTrigger trigger : triggers) {
            if (trigger.isTriggered()) {
                if (!playList.contains(trigger)) {
                    playList.add(trigger);
                }
            }
        }

        if (current == null && !playList.isEmpty()) {
            MusicTrigger best = playList.poll();

            AssetUri uri = best.getAssetUri();
            current = Assets.get(uri, StreamingSound.class);

            if (current != null) {
                logger.info("Starting to play {}", uri);
                audioManager.playMusic(current, new AudioEndListener() {

                    @Override
                    public void onAudioEnd() {
                        history.add(uri);
                        current = null;
                    }
                });
            } else {
                logger.warn("Asset {} could not be retrieved", uri);
            }
        }
    }

    @Command(shortDescription = "Show current playlist")
    public String showPlaylist() {
        StringBuilder sb = new StringBuilder();
        int index = 1;
        for (MusicTrigger entry : playList) {
            sb.append(index + " - " + entry.getAssetUri());
            sb.append("\n");
            index++;
        }

        return sb.toString();
    }
}
