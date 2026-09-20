package musicplaylistmixer;

import musicplaylistmixer.utilities.CommonUtility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Playlist {
    private String name;
    private final List<PlaylistEntry> playlistEntries;


    public Playlist(String name) {
        if(CommonUtility.isBlank(name)) {
            throw new IllegalArgumentException("name cant be empty");
        }
        this.playlistEntries = new ArrayList<>();
        this.name = name;
    }


    public void addSong(Song song) {
        if(song == null) {
            throw new IllegalArgumentException("Non existent Song cant be added");
        }
        addSong(song, null);
    }

    public void addSong(Song song, String nickname) {
        playlistEntries.add(new PlaylistEntry(song, nickname));
    }

    public boolean addSongIfAbsent(Song song) {
        if(!hasSong(song)) {
            addSong(song);
            return true;
        }
        return false;
    }

    public boolean addSongIfAbsent(Song song, String nickname) {
        if(!hasSong(song)) {
            addSong(song, nickname);
            return true;
        }
        return false;
    }

    public boolean removeSong(Song song) {
        boolean removed = false;
        Iterator<PlaylistEntry> iterator = playlistEntries.iterator();
        while(iterator.hasNext()) {
            PlaylistEntry playlistEntry = iterator.next();
            if(playlistEntry.getSong().equals(song)) {
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
        for(PlaylistEntry playlistEntry : playlistEntries) {
            songs.add(playlistEntry.getSong());
        }
        return songs;
    }

    public String getName() {
        return name;
    }

    public void rename(String name) {
        if(CommonUtility.isBlank(name)) {
            throw new IllegalArgumentException("name cant be empty");
        }
        this.name = name;
    }

    private boolean hasSong(Song song) {
        for(PlaylistEntry playlistEntry : playlistEntries) {
            if(playlistEntry.getSong().equals(song)) {
                return true;
            }
        }
        return false;
    }
}
