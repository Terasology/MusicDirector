// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.musicdirector;

import org.terasology.gestalt.entitysystem.component.Component;

/**
 * A music trigger that is based on a daily time interval.
 */
public final class TimedMusicTriggerComponent implements Component<TimedMusicTriggerComponent>  {

    public float dailyStart;
    public float dailyEnd;

    public String assetUri;

    @Override
    public String toString() {
        return String.format("MusicTrigger [%.2f...%.2f -> '%s']", dailyStart, dailyEnd, assetUri);
    }

    @Override
    public void copy(TimedMusicTriggerComponent other) {
        this.dailyStart = other.dailyStart;
        this.dailyEnd = other.dailyEnd;
        this.assetUri = other.assetUri;
    }
}
