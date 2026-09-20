package musicplaylistmixer.services.sort;

import musicplaylistmixer.Song;

import java.util.Comparator;

public class ArtistComparator implements Comparator<Song> {

    @Override
    public int compare(Song first, Song second) {
        return first.getArtist().compareToIgnoreCase(second.getArtist());
    }
}
