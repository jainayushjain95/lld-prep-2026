package musicplaylistmixer.services.search;

import musicplaylistmixer.Song;

public class GenreCriteria implements SearchCriteria {

    private final String genre;

    public GenreCriteria(String genre) {
        if(genre == null || genre.isBlank()) {
            throw new IllegalArgumentException("Search term genre cant be null");
        }
        this.genre = genre.toLowerCase();
    }

    @Override
    public boolean matches(Song song) {
        return song.getGenre().toLowerCase().contains(genre);
    }

}
