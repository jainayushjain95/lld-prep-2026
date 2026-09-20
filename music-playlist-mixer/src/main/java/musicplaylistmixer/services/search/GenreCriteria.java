package musicplaylistmixer.services.search;

import musicplaylistmixer.Song;

public class GenreCriteria implements SearchCriteria {

    @Override
    public boolean search(Song song) {
        return false;
    }
}
