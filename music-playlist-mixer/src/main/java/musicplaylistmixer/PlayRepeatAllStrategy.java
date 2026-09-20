package musicplaylistmixer;

import java.util.List;

public class PlayRepeatAllStrategy implements PlayStrategy {

    @Override
    public int next(int currentIndex, List<Song> songs) {
        return 0;
    }
}
