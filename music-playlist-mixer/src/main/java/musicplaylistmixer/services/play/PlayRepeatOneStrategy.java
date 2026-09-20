package musicplaylistmixer.services.play;

import musicplaylistmixer.entities.Song;

import java.util.List;

public class PlayRepeatOneStrategy implements PlayStrategy {

    @Override
    public int next(int currentIndex, List<Song> songs) {
        if(songs.isEmpty()) {
            return -1;
        }
        if(currentIndex == -1) {
            return 0;
        }
        return currentIndex;
    }

}
