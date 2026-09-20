package musicplaylistmixer;

import java.util.List;

public class MusicPlayer {
    private final Playlist playlist;
    private int currentIndex;
    private String mode;
    private PlayStrategy playStrategy;

    public MusicPlayer(Playlist playlist, PlayStrategy playStrategy) {
        this.playlist = playlist;
        this.currentIndex = 0;
        this.playStrategy = playStrategy;
    }

    public void setMode(String mode) {
        this.mode = mode;
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
