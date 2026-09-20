package musicplaylistmixer.services.play;

import musicplaylistmixer.entities.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PlayShuffleStrategy implements PlayStrategy {

    private final Random random;
    private List<Integer> shuffledIndices;
    private int positionInShuffle;

    public PlayShuffleStrategy(Random random) {
        this.random = random;
        this.positionInShuffle = -1;
    }

    @Override
    public int next(int currentIndex, List<Song> songs) {
        if(songs.isEmpty()) {
            return -1;
        }

        setShuffledList(songs.size());
        int nextPositionInShuffle = positionInShuffle + 1;
        if(nextPositionInShuffle >= shuffledIndices.size()) {
            return -1;
        }
        positionInShuffle = nextPositionInShuffle;
        return shuffledIndices.get(positionInShuffle);
    }

    private void setShuffledList(int noOfSongs) {
        if(shuffledIndices == null || shuffledIndices.size() != noOfSongs) {
            shuffledIndices = new ArrayList<>();
            for(int i = 0; i < noOfSongs; i++) {
                shuffledIndices.add(i);
            }
            Collections.shuffle(shuffledIndices, random);
        }
    }
}
