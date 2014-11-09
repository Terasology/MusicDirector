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

import java.util.Comparator;

/**
 * Compares two triggers based on their priority values
 * @author Martin Steiger
 */
class TriggerComparator implements Comparator<MusicTrigger> {

    @Override
    public int compare(MusicTrigger o1, MusicTrigger o2) {
        MusicPriority prio1 = o1.getPriority();
        MusicPriority prio2 = o2.getPriority();
        return Integer.compare(prio1.getValue(), prio2.getValue());
    }

}
