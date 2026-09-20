package musicplaylistmixer.entities;
import java.time.LocalDateTime;

public class PlaylistEntry {
    private final Song song;
    private final LocalDateTime addedAt;
    private String nickname;
    private final User addedBy;

    public PlaylistEntry(Song song, User addedBy) {
        this(song, null, addedBy);
    }

    public PlaylistEntry(Song song, String nickname, User addedBy) {
        this.song = song;
        this.addedBy = addedBy;
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
