package musicplaylistmixer;
import java.time.LocalDateTime;

public class PlaylistEntry {
    private final Song song;
    private final LocalDateTime addedAt;
    private String nickname;

    public PlaylistEntry(Song song) {
        this(song, null);
    }

    public PlaylistEntry(Song song, String nickname) {
        this.song = song;
        this.addedAt = LocalDateTime.now();
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Song getSong() {
        return song;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }
}
