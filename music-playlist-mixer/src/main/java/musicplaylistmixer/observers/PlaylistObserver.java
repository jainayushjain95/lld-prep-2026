package musicplaylistmixer.observers;

import musicplaylistmixer.entities.Playlist;
import musicplaylistmixer.entities.Song;
import musicplaylistmixer.entities.User;

public interface PlaylistObserver {
    void onSongAdded(Playlist playlist, Song song, User addedBy);
}
