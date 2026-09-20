package musicplaylistmixer.services.play;

import musicplaylistmixer.entities.Song;

import java.util.List;

public class PlayRepeatAllStrategy implements PlayStrategy {

    @Override
    public int next(int currentIndex, List<Song> songs) {
        if (songs.isEmpty()) {
            return -1;
        }
        return (currentIndex + 1) % songs.size();
    }
}
