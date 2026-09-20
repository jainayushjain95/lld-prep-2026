package musicplaylistmixer;

public class MusicPlayer {
    private final Playlist playlist;
    private int currentIndex;
    private String mode;

    public MusicPlayer(Playlist playlist) {
        this.playlist = playlist;
        currentIndex = 0;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Song next() {
        if(mode.equals(""))
        return null;
    }
}
