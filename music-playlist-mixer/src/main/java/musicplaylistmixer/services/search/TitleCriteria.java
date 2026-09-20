package musicplaylistmixer.services.search;

import musicplaylistmixer.Song;
import musicplaylistmixer.utilities.CommonUtility;

public class TitleCriteria implements SearchCriteria{

    private final String title;

    public TitleCriteria(String title) {
        if(CommonUtility.isBlank(title)) {
            throw new IllegalArgumentException("Search term title cant be null");
        }
        this.title = title.toLowerCase();
    }

    @Override
    public boolean matches(Song song) {
        return song.getTitle().toLowerCase().contains(title);
    }

}
