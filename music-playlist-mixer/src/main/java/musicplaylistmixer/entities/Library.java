package musicplaylistmixer.entities;

import java.util.*;

public class Library {
    private final Map<String, Song> songs;

    public Library() {
        this.songs = new LinkedHashMap<>();
    }


    public void addSong(Song song) {
        if(song == null) {
            throw new IllegalArgumentException("Song cant be null");
        }
        songs.put(song.getId(), song);
    }


    public Song findById(String id) {
        return songs.get(id);
    }


    public List<Song> getAllSongs() {
        return new ArrayList<>(songs.values());
    }
}
