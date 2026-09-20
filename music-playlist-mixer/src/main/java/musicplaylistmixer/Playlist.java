package musicplaylistmixer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Playlist {
    private String name;
    private final List<Song> songs;


    public Playlist(String name) {
        this.songs = new ArrayList<>();
        this.name = name;
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void addSongIfAbsent(Song song) {
        if(!songs.contains(song)) {
            addSong(song);
        }
    }

    public boolean removeSong(Song song) {
        return songs.remove(song);
    }

    public List<Song> getSongs() {
        return Collections.unmodifiableList(songs);
    }

    public String getName() {
        return name;
    }

    public void rename(String name) {
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name cant be empty");
        }
        this.name = name;
    }
}
