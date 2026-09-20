package musicplaylistmixer.services.search;

import musicplaylistmixer.Song;

public class TitleCriteria implements SearchCriteria{

    @Override
    public boolean search(Song song) {
        return false;
    }

}
