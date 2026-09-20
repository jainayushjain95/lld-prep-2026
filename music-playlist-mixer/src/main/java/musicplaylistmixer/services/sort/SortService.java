package musicplaylistmixer.services.sort;

import musicplaylistmixer.Song;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortService {
    public static List<Song> sort(List<Song> songs, Comparator<Song> comparator) {
        List<Song> list = new ArrayList<>(songs);
        list.sort(comparator);
        return list;
    }
}
