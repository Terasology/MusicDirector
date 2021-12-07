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

import com.google.common.collect.Sets;
import com.google.common.math.DoubleMath;
import com.google.common.math.LongMath;
import org.terasology.engine.entitySystem.entity.EntityRef;
import org.terasology.engine.entitySystem.entity.lifecycleEvents.BeforeDeactivateComponent;
import org.terasology.engine.entitySystem.entity.lifecycleEvents.OnActivatedComponent;
import org.terasology.engine.entitySystem.systems.BaseComponentSystem;
import org.terasology.engine.entitySystem.systems.RegisterSystem;
import org.terasology.engine.registry.CoreRegistry;
import org.terasology.engine.world.WorldProvider;
import org.terasology.engine.world.time.WorldTime;
import org.terasology.engine.world.time.WorldTimeEvent;
import org.terasology.gestalt.entitysystem.event.ReceiveEvent;

import java.math.RoundingMode;
import java.util.Collection;

/**
 * Enqueues triggers that are
 * provided by (auto-created) entities with
 * {@link TimedMusicTriggerComponent} components.
 */
@RegisterSystem
public class TimedTriggerSystem extends BaseComponentSystem {

    private final Collection<TimedMusicTriggerComponent> triggers = Sets.newLinkedHashSet();

    private MusicDirector manager;

    private WorldTime worldTime;

    @Override
    public void initialise() {
        super.initialise();

        // msteiger: I think we cannot rely on the @In annotation, because
        //           MusicDirector could be created after this class
        manager = CoreRegistry.get(MusicDirector.class);

        WorldProvider worldProvider = CoreRegistry.get(WorldProvider.class);
        worldTime = worldProvider.getTime();
    }

    @ReceiveEvent(components = TimedMusicTriggerComponent.class)
    public void onRegisterAsset(OnActivatedComponent event, EntityRef entity) {

        TimedMusicTriggerComponent trigger = entity.getComponent(TimedMusicTriggerComponent.class);
//        DisplayNameComponent displayName = entity.getComponent(DisplayNameComponent.class);

        triggers.add(trigger);
    }


    @ReceiveEvent(components = TimedMusicTriggerComponent.class)
    public void onUnregisterAsset(BeforeDeactivateComponent event, EntityRef entity) {

        TimedMusicTriggerComponent trigger = entity.getComponent(TimedMusicTriggerComponent.class);

        triggers.remove(trigger);
    }


    @ReceiveEvent
    public void onTimeEvent(WorldTimeEvent event, EntityRef worldEntity) {

        for (TimedMusicTriggerComponent trigger : triggers) {
            if (isTriggered(trigger)) {
                manager.enqueue(trigger.assetUri, MusicPriority.LOW);
            } else {
                manager.dequeue(trigger.assetUri);
            }
        }
    }

    public boolean isTriggered(TimedMusicTriggerComponent trigger) {

        long time = worldTime.getMilliseconds();
        long timeInDay = LongMath.mod(time, WorldTime.DAY_LENGTH);

        long startInMs = DoubleMath.roundToLong(trigger.dailyStart * WorldTime.DAY_LENGTH, RoundingMode.HALF_UP);
        long endInMs = DoubleMath.roundToLong(trigger.dailyEnd * WorldTime.DAY_LENGTH, RoundingMode.HALF_UP);

        if (startInMs < endInMs) {
            // -----[xxxxxxxxxx]------
            return timeInDay >= startInMs && timeInDay <= endInMs;
        } else {
            // xxxxx]----------[xxxxxx
            return timeInDay >= startInMs || timeInDay <= endInMs;
        }
    }
}
