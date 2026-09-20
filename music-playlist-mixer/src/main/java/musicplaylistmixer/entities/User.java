package musicplaylistmixer.entities;

import musicplaylistmixer.utilities.CommonUtility;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private final List<Playlist> playlists;
    private final String userId;

    public User(String userId, String name) {
        if(CommonUtility.isBlank(userId) || CommonUtility.isBlank(name)) {
            throw new IllegalArgumentException("Name and userid cant be empty");
        }
        this.name = name;
        this.playlists = new ArrayList<>();
        this.userId = userId;
    }

    public Playlist createPlaylist(String name) {
        if(isNameAvailable(name)) {
            throw new IllegalArgumentException("Name already taken");
        }
        Playlist playlist = new Playlist(name, this);
        playlists.add(playlist);
        return playlist;
    }

    public void removePlaylist(Playlist playlist) {
        if(playlist == null) {
            throw new IllegalArgumentException("Playlists cant be null");
        }
        if(!isNameAvailable(name)) {
            throw new IllegalArgumentException("No playlists exists with this name");
        }
        playlists.remove(playlist);
    }

    private boolean isNameAvailable(String name) {
        for(Playlist playlist : playlists) {
            if(playlist.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }
}
