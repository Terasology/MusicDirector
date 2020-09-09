// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.musicdirector;

import org.terasology.engine.entitySystem.Component;

/**
 * A music trigger that is based on a daily time interval.
 */
public final class TimedMusicTriggerComponent implements Component {

    public float dailyStart;
    public float dailyEnd;

    public String assetUri;

    @Override
    public String toString() {
        return String.format("MusicTrigger [%.2f...%.2f -> '%s']", dailyStart, dailyEnd, assetUri);
    }
}
