package musicplaylistmixer.entities;

import musicplaylistmixer.utilities.CommonUtility;

import java.util.*;

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
        if(name == null) {
            throw new IllegalArgumentException("Name cant be empty");
        }
        if(!isNameAvailable(name)) {
            throw new IllegalArgumentException("Name already taken");
        }
        Playlist playlist = new Playlist(name, this);
        playlists.add(playlist);
        return playlist;
    }

    public boolean removePlaylist(Playlist playlist) {
        if(playlist != null) {
            return playlists.remove(playlist);
        }
        return false;
    }

    private boolean isNameAvailable(String name) {
        for(Playlist playlist : playlists) {
            if(playlist.getName().equalsIgnoreCase(name)) {
                return false;
            }
        }
        return true;
    }

    public List<Playlist> getPlaylists() {
        return Collections.unmodifiableList(playlists);
    }

    public Playlist mix(Playlist first, Playlist second, String nameOfMixedPlaylist) {
        if(first == null || second == null) {
            throw new IllegalArgumentException("Any playlist cant be empty");
        }
        Playlist mixed = createPlaylist(nameOfMixedPlaylist);
        for(PlaylistEntry playlistEntry : first.getPlaylistEntries()) {
            mixed.addSongIfAbsent(playlistEntry.getSong());
        }
        for(PlaylistEntry playlistEntry : second.getPlaylistEntries()) {
            mixed.addSongIfAbsent(playlistEntry.getSong());
        }
        return mixed;
    }
}
