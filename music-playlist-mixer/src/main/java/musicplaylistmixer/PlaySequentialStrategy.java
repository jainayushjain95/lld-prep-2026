package musicplaylistmixer;

import java.util.List;

public class PlaySequentialStrategy implements PlayStrategy {

    @Override
    public int next(int currentIndex, List<Song> songs) {
        int nextIndex = currentIndex + 1;
        if(nextIndex >= songs.size()) {
            return -1;
        }
        return nextIndex;
    }
}
