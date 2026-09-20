package musicplaylistmixer.services.search;

import musicplaylistmixer.Song;

public class ArtistCriteria implements SearchCriteria {

    @Override
    public boolean search(Song song) {
        return false;
    }

}
