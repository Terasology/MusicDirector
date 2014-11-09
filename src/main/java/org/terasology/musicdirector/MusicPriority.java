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

/**
 * The priority at which music assets get played.
 * @author Martin Steiger
 */
public enum MusicPriority {

    LOW(0),

    MEDIUM(5),

    HIGH(10);

    private final int value;

    /**
     * @param prio the priority
     */
    private MusicPriority(int prio) {
        this.value = prio;
    }

    public int getValue() {
        return value;
    }
}
