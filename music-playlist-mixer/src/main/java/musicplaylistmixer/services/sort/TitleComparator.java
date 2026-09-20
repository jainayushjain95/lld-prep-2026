package musicplaylistmixer.services.sort;

import musicplaylistmixer.Song;

import java.util.Comparator;

public class TitleComparator implements Comparator<Song> {

    @Override
    public int compare(Song first, Song second) {
        return first.getTitle().compareToIgnoreCase(second.getTitle());
    }
}
