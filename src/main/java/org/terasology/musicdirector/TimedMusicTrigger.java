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

import java.math.RoundingMode;

import org.terasology.asset.AssetUri;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.WorldProvider;
import org.terasology.world.time.WorldTime;

import com.google.common.math.DoubleMath;
import com.google.common.math.LongMath;

/**
 * TODO Type description
 * @author Martin Steiger
 */
public class TimedMusicTrigger implements MusicTrigger {

    private final float dailyStart;
    private final float dailyEnd;

    private final WorldTime worldTime;
    private final AssetUri asset;

    public TimedMusicTrigger(AssetUri uri, float dailyStart, float dailyEnd) {
        this.dailyStart = dailyStart;
        this.dailyEnd = dailyEnd;
        this.asset = uri;

        WorldProvider worldProvider = CoreRegistry.get(WorldProvider.class);
        worldTime = worldProvider.getTime();
    }

    @Override
    public boolean isTriggered() {

        long time = worldTime.getMilliseconds();
        long timeInDay = LongMath.mod(time, WorldTime.DAY_LENGTH);

        long startInMs = DoubleMath.roundToLong(dailyStart * WorldTime.DAY_LENGTH, RoundingMode.HALF_UP);
        long endInMs = DoubleMath.roundToLong(dailyEnd * WorldTime.DAY_LENGTH, RoundingMode.HALF_UP);

        return timeInDay > startInMs && timeInDay < endInMs;
    }

    @Override
    public AssetUri getAssetUri() {
        return asset;
    }

    @Override
    public MusicPriority getPriority() {
        return MusicPriority.LOW;
    }

    @Override
    public String toString() {
        return String.format("MusicTrigger [%2f..%2f -> %s]" + dailyStart, dailyEnd, asset);
    }
}
