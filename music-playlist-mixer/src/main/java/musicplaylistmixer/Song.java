package musicplaylistmixer;

public class Song {
    private final String id;
    private final String title;
    private final String artist;
    private final String album;
    private final int durationSecs;
    private final String genre;

    public Song(String id, String title, String artist, String album, int durationSecs, String genre) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.durationSecs = durationSecs;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", durationSecs=" + durationSecs +
                ", genre='" + genre + '\'' +
                '}';
    }


}
