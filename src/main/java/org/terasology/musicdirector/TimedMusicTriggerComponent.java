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

import org.terasology.engine.entitySystem.Component;

/**
 * A music trigger that is based on a daily time interval.
 */
public final class TimedMusicTriggerComponent implements Component  {

    public float dailyStart;
    public float dailyEnd;

    public String assetUri;

    @Override
    public String toString() {
        return String.format("MusicTrigger [%.2f...%.2f -> '%s']", dailyStart, dailyEnd, assetUri);
    }
}
