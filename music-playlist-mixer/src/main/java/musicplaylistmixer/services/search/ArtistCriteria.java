package musicplaylistmixer.services.search;

import musicplaylistmixer.entities.Song;
import musicplaylistmixer.utilities.CommonUtility;

public class ArtistCriteria implements SearchCriteria {
    private final String artist;

    public ArtistCriteria(String artist) {
        if(CommonUtility.isBlank(artist)) {
            throw new IllegalArgumentException("Search term Artist cant be null");
        }
        this.artist = artist.toLowerCase();
    }

    @Override
    public boolean matches(Song song) {
        return song.getArtist().toLowerCase().contains(artist);
    }

}
