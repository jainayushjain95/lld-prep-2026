package musicplaylistmixer;

import java.util.List;

public class PlaySequentialStrategy implements PlayStrategy {

    @Override
    public int next(int currentIndex, List<Song> songs) {
        if(songs.isEmpty()) {
            return -1;
        }
        if(currentIndex >= songs.size()) {
            currentIndex = 0;
        }
        return currentIndex;
    }
}
