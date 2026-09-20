package musicplaylistmixer.services.play;

import musicplaylistmixer.Song;

import java.util.List;

public interface PlayStrategy {
    int next(int currentIndex, List<Song> songs);
}
