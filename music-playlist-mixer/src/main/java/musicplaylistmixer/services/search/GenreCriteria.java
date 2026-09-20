package musicplaylistmixer.services.search;

import musicplaylistmixer.Song;
import musicplaylistmixer.utilities.CommonUtility;

public class GenreCriteria implements SearchCriteria {

    private final String genre;

    public GenreCriteria(String genre) {
        if(CommonUtility.isBlank(genre)) {
            throw new IllegalArgumentException("Search term genre cant be null");
        }
        this.genre = genre;
    }

    @Override
    public boolean matches(Song song) {
        return !CommonUtility.isBlank(song.getGenre()) && song.getGenre().equalsIgnoreCase(genre);
    }

}
