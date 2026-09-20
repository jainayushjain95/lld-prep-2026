package musicplaylistmixer.services.search;

import musicplaylistmixer.Song;
import musicplaylistmixer.utilities.CommonUtility;

import java.util.ArrayList;
import java.util.List;

public class AllOfCriteria implements SearchCriteria {

    private final List<SearchCriteria> searchCriteria;

    public AllOfCriteria(List<SearchCriteria> searchCriteria) {
        if(searchCriteria == null || searchCriteria.isEmpty()) {
            throw new IllegalArgumentException("Search Criteria must have some criteria");
        }
        this.searchCriteria = new ArrayList<>(searchCriteria);
    }

    @Override
    public boolean matches(Song song) {
        for(SearchCriteria criteria : searchCriteria) {
            if(!criteria.matches(song)) {
                return false;
            }
        }
        return true;
    }

}
