package musicplaylistmixer.services.search;

import musicplaylistmixer.Song;

public interface SearchCriteria {
    boolean matches(Song song);
}
