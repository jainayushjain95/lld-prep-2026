package musicplaylistmixer;

import musicplaylistmixer.services.search.PlayStrategy;

import java.util.List;

public class MusicPlayer {
    private final Playlist playlist;
    private int currentIndex;
    private PlayStrategy playStrategy;

    public MusicPlayer(Playlist playlist, PlayStrategy playStrategy) {
        this.playlist = playlist;
        this.currentIndex = -1;
        this.playStrategy = playStrategy;
    }

    public void setPlayStrategy(PlayStrategy playStrategy) {
        this.playStrategy = playStrategy;
    }

    public Song next() {
        List<Song> songs = playlist.getSongs();
        int nextIndex = playStrategy.next(currentIndex, songs);
        if(nextIndex < 0) {
            return null;
        }
        currentIndex = nextIndex;
        return songs.get(currentIndex);
    }
}
