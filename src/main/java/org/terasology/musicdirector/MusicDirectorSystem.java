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

import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.entity.lifecycleEvents.OnActivatedComponent;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.registry.CoreRegistry;

/**
 * Play different music assets through triggers that are
 * provided by (auto-created) entities with
 * {@link TimedMusicTrigger} components.
 * @author Martin Steiger
 */
@RegisterSystem
public class MusicDirectorSystem extends BaseComponentSystem {

    private MusicDirector manager;

    @Override
    public void initialise() {
        super.initialise();

        // msteiger: I think we cannot rely on the @In annotation, because
        //           MusicDirector could be created after this class
        manager = CoreRegistry.get(MusicDirector.class);
    }

    @ReceiveEvent(components = TimedMusicTriggerComponent.class)
    public void onRegisterAsset(OnActivatedComponent event, EntityRef entity) {

        MusicTrigger trigger = entity.getComponent(TimedMusicTriggerComponent.class);

//        DisplayNameComponent displayName = entity.getComponent(DisplayNameComponent.class);

        manager.register(trigger);
    }

}
