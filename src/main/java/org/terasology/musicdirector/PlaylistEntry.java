// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.musicdirector;

/**
 * A playlist entry - identified by an asset uri.
 */
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
    public String toString() {
        return "PlaylistEntry ['" + assetUri + "' at " + priority + " priority]";
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
        return priority == other.priority;
    }


}
