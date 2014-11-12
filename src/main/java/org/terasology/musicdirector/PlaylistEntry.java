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

final class PlaylistEntry {

    private final String assetUri;
    private final MusicPriority priority;

    PlaylistEntry(String assetUri, MusicPriority priority) {
        this.assetUri = assetUri;
        this.priority = priority;
    }

    public String getAssetUri() {
        return assetUri;
    }
    public MusicPriority getPriority() {
        return priority;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((assetUri == null) ? 0 : assetUri.hashCode());
        result = prime * result + ((priority == null) ? 0 : priority.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PlaylistEntry other = (PlaylistEntry) obj;
        if (assetUri == null) {
            if (other.assetUri != null) {
                return false;
            }
        } else if (!assetUri.equals(other.assetUri)) {
            return false;
        }
        if (priority != other.priority) {
            return false;
        }
        return true;
    }


}
