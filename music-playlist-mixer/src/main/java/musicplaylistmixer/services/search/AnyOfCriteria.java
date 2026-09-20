package musicplaylistmixer.services.search;

import musicplaylistmixer.Song;

import java.util.List;

public class AnyOfCriteria implements SearchCriteria {

    private final List<SearchCriteria> searchCriteria;

    public AnyOfCriteria(List<SearchCriteria> searchCriteria) {
        if(searchCriteria == null || searchCriteria.isEmpty()) {
            throw new IllegalArgumentException("Search Criteria must have some criteria");
        }
        this.searchCriteria = searchCriteria;
    }

    @Override
    public boolean matches(Song song) {
        for(SearchCriteria searchCriteria1 : searchCriteria) {
            if(searchCriteria1.matches(song)) {
                return true;
            }
        }
        return false;
    }

}
