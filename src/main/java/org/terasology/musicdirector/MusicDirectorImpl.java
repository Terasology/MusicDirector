// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.musicdirector;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.engine.audio.AudioManager;
import org.terasology.engine.audio.StreamingSound;
import org.terasology.engine.entitySystem.systems.BaseComponentSystem;
import org.terasology.engine.entitySystem.systems.RegisterSystem;
import org.terasology.engine.logic.console.commandSystem.annotations.Command;
import org.terasology.engine.registry.In;
import org.terasology.engine.registry.Share;
import org.terasology.engine.utilities.Assets;

import java.util.Comparator;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Play different music assets that are enqueued according to their priority.
 */
@RegisterSystem
@Share(MusicDirector.class)
public class MusicDirectorImpl extends BaseComponentSystem implements MusicDirector {

    private static final Logger logger = LoggerFactory.getLogger(MusicDirectorImpl.class);

    private static final TriggerComparator COMP = new TriggerComparator();

    private final Queue<PlaylistEntry> playList = new PriorityQueue<>(COMP);
    private final Queue<PlaylistEntry> history = Lists.newLinkedList();

    @In
    private AudioManager audioManager;

    private PlaylistEntry currentEntry;
    private StreamingSound currentSound;

    @Override
    public void enqueue(String assetUri, MusicPriority priority) {

        PlaylistEntry prev = find(assetUri);

        PlaylistEntry entry = new PlaylistEntry(assetUri, priority);

        if (prev != null) {
            if (!prev.equals(entry)) {
                playList.remove(prev);
                playList.add(entry);
                logger.info("Updated {} with {}", prev, entry);
            } else {
                // already exists
                return;
            }
        } else {
            logger.info("Enqueued {}", assetUri);
            playList.add(entry);
        }

        checkTriggers();
    }

    @Override
    public void dequeue(String assetUri) {
        PlaylistEntry entry = find(assetUri);
        if (entry != null) {
            playList.remove(entry);
            logger.info("Removed {}", entry.getAssetUri());
        }
    }

    private PlaylistEntry find(String assetUri) {
        for (PlaylistEntry entry : playList) {
            if (assetUri.equalsIgnoreCase(entry.getAssetUri())) {
                return entry;
            }
        }

        return null;
    }

    private void checkTriggers() {

        if (playList.isEmpty()) {
            return;
        }

        PlaylistEntry nextEntry = playList.peek();

        if (currentEntry == null || COMP.compare(nextEntry, currentEntry) > 0) {

            String uri = nextEntry.getAssetUri();
            Optional<StreamingSound> optSound = Assets.getMusic(uri);

            if (optSound.isPresent()) {
                if (currentSound != null) {
                    //                    currentSound.stop();
                }

                currentSound = optSound.get();
                currentEntry = nextEntry;

                logger.info("Starting to play '{}'", uri);
                audioManager.playMusic(currentSound, interrupted -> {
                    logger.info("Song '{}' ended", currentEntry.getAssetUri());
                    playList.remove(currentEntry);    // remove head
                    history.add(currentEntry);
                    currentEntry = null;
                    currentSound = null;
                    checkTriggers();
                });
            } else {
                logger.warn("Asset {} could not be retrieved", uri);
                playList.remove();
                checkTriggers();
            }
        }
    }

    @Command(shortDescription = "Show current playlist")
    public String showPlaylist() {
        StringBuilder sb = new StringBuilder();

        sb.append("In the queue:\n");
        Joiner.on('\n').appendTo(sb, playList);
        sb.append("\n");
        sb.append(currentEntry != null ? "Currently playing '" + currentEntry.getAssetUri() + "'" : "Not playing");

        return sb.toString();
    }

    private static class TriggerComparator implements Comparator<PlaylistEntry> {

        @Override
        public int compare(PlaylistEntry o1, PlaylistEntry o2) {
            MusicPriority prio1 = o1.getPriority();
            MusicPriority prio2 = o2.getPriority();
            return Integer.compare(prio1.getValue(), prio2.getValue());
        }

    }
}
