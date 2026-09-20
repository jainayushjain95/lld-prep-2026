package musicplaylistmixer.services.search;

import musicplaylistmixer.Song;

import java.util.ArrayList;
import java.util.List;

public class SearchService {

    public static List<Song> search(List<Song> songs, SearchCriteria criteria) {

        if(criteria == null) {
            throw new IllegalArgumentException("Criteria cant be empty");
        }
        List<Song> matchedSongs = new ArrayList<>();
        if(songs != null && !songs.isEmpty()) {
            for(Song song : songs) {
                if(criteria.matches(song)) {
                    matchedSongs.add(song);
                }
            }
        }
        return matchedSongs;
    }
}
