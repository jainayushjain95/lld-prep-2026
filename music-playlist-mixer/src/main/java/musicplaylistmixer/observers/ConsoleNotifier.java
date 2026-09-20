package musicplaylistmixer.observers;

import musicplaylistmixer.entities.Playlist;
import musicplaylistmixer.entities.Song;
import musicplaylistmixer.entities.User;

public class ConsoleNotifier implements PlaylistObserver{

    @Override
    public void onSongAdded(Playlist playlist, Song song, User addedBy) {
        System.out.println(addedBy.getName() + " added " + song.getTitle() + " song in " + playlist.getName() + " playlist");
    }

}
