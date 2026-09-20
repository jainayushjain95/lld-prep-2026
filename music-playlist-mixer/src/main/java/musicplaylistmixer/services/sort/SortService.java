package musicplaylistmixer.services.sort;

import musicplaylistmixer.entities.Song;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortService {
    public static List<Song> sort(List<Song> songs, Comparator<Song> comparator) {
        if(comparator == null) {
            throw new IllegalArgumentException("Comparators cant be empty");
        }
        if(songs != null && !songs.isEmpty()) {
            List<Song> list = new ArrayList<>(songs);
            list.sort(comparator);
            return list;
        }
        return songs;
    }
}
