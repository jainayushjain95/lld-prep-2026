package musicplaylistmixer.services.sort;

import musicplaylistmixer.Song;

import java.util.Comparator;

public class DurationComparator implements Comparator<Song> {

    @Override
    public int compare(Song first, Song second) {
        return Integer.compare(first.getDurationSecs(), second.getDurationSecs());
    }
}
