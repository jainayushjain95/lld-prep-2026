package musicplaylistmixer;

import java.util.List;

public class PlaySequentialStrategy implements PlayStrategy {

    @Override
    public int next(int currentIndex, List<Song> songs) {
        return 0;
    }
}
