package musicplaylistmixer.services.search;

import musicplaylistmixer.entities.Song;

public interface SearchCriteria {
    boolean matches(Song song);
}
