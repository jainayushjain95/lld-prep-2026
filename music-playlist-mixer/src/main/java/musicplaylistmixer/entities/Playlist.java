package musicplaylistmixer.entities;

import musicplaylistmixer.observers.PlaylistObserver;
import musicplaylistmixer.services.sort.SortService;
import musicplaylistmixer.utilities.CommonUtility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Playlist {

    private String name;
    private final List<PlaylistEntry> playlistEntries;
    private final User owner;
    private final Set<User> collaborators;
    private final List<PlaylistObserver> observers;


    public Playlist(String name, User owner) {
        if (owner == null) {
            throw new IllegalArgumentException("Playlist cant exist without owner");
        }
        if (CommonUtility.isBlank(name)) {
            throw new IllegalArgumentException("name cant be empty");
        }

        this.observers = new ArrayList<>();
        this.collaborators = new HashSet<>();
        this.owner = owner;
        this.playlistEntries = new ArrayList<>();
        this.name = name;
        this.collaborators.add(owner);
    }

    public void addObserver(PlaylistObserver observer) {
        if (observer == null) {
            throw new IllegalArgumentException("Observer cant be null");
        }
        observers.add(observer);
    }

    public boolean removeObserver(PlaylistObserver observer) {
        return observers.remove(observer);
    }

    public List<User> getCollaborators() {
        return new ArrayList<>(collaborators);
    }

    public boolean addCollaborator(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cant be null");
        }
        return collaborators.add(user);
    }

    public boolean removeCollaborator(User user) {
        if (user == null || user.equals(owner)) {
            return false;
        }
        return collaborators.remove(user);
    }

    public void addSong(Song song, User addedBy) {
        addSong(song, null, addedBy);
    }

    public void addSong(Song song, String nickname, User addedBy) {
        if (song == null) {
            throw new IllegalArgumentException("Non existent Song cant be added");
        }
        if (addedBy == null) {
            throw new IllegalArgumentException("addedBy cant be null");
        }
        if (!canAdd(addedBy)) {
            throw new IllegalArgumentException(addedBy.getName() + " is not allowed to add to " + name);
        }
        playlistEntries.add(new PlaylistEntry(song, nickname, addedBy));
        notifySongAdded(song, addedBy);
    }

    public boolean addSongIfAbsent(Song song, User addedBy) {
        return addSongIfAbsent(song, null, addedBy);
    }

    public boolean addSongIfAbsent(Song song, String nickname, User addedBy) {
        if (hasSong(song)) {
            return false;
        }
        addSong(song, nickname, addedBy);
        return true;
    }

    public boolean removeSong(Song song) {
        boolean removed = false;
        Iterator<PlaylistEntry> iterator = playlistEntries.iterator();
        while (iterator.hasNext()) {
            PlaylistEntry playlistEntry = iterator.next();
            if (playlistEntry.getSong().equals(song)) {
                iterator.remove();
                removed = true;
            }
        }
        return removed;
    }

    public boolean removeEntry(PlaylistEntry playlistEntry) {
        return playlistEntries.remove(playlistEntry);
    }

    public List<PlaylistEntry> getPlaylistEntries() {
        return Collections.unmodifiableList(playlistEntries);
    }

    public List<Song> getSongs() {
        List<Song> songs = new ArrayList<>();
        for (PlaylistEntry playlistEntry : playlistEntries) {
            songs.add(playlistEntry.getSong());
        }
        return songs;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public void rename(String name) {
        if (CommonUtility.isBlank(name)) {
            throw new IllegalArgumentException("name cant be empty");
        }
        this.name = name;
    }

    public List<Song> sortedBy(Comparator<Song> comparator) {
        return SortService.sort(this.getSongs(), comparator);
    }

    private boolean canAdd(User user) {
        return collaborators.contains(user);
    }

    private void notifySongAdded(Song song, User addedBy) {
        for (PlaylistObserver observer : observers) {
            observer.onSongAdded(this, song, addedBy);
        }
    }

    private boolean hasSong(Song song) {
        for (PlaylistEntry playlistEntry : playlistEntries) {
            if (playlistEntry.getSong().equals(song)) {
                return true;
            }
        }
        return false;
    }
}