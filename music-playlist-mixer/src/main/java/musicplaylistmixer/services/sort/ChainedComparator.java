package musicplaylistmixer.services.sort;

import musicplaylistmixer.Song;

import java.util.Comparator;
import java.util.List;

public class ChainedComparator implements Comparator<Song> {

    private final List<Comparator<Song>> comparators;

    public ChainedComparator(List<Comparator<Song>> comparators) {
        if(comparators == null || comparators.isEmpty()) {
            throw new IllegalArgumentException("Need at least one sorting criteria");
        }
        this.comparators = comparators;
    }

    @Override
    public int compare(Song first, Song second) {
        for(Comparator<Song> comparator : comparators) {
            int compare = comparator.compare(first, second);
            if(compare != 0) {
                return compare;
            }
        }
        return 0;
    }
}
