package musicplaylistmixer;

import java.util.Objects;

public final class Song {
    private final String id;
    private final String title;
    private final String artist;
    private final int durationSecs;

    private final String album;
    private final String genre;
    private final Integer releaseYear;
    private final String language;

    private Song(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.artist = builder.artist;
        this.album = builder.album;
        this.durationSecs = builder.durationSecs;
        this.genre = builder.genre;
        this.releaseYear = builder.releaseYear;
        this.language = builder.language;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public int getDurationSecs() {
        return durationSecs;
    }

    public String getGenre() {
        return genre;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(id, song.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Song{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", durationSecs=" + durationSecs +
                ", album='" + album + '\'' +
                ", genre='" + genre + '\'' +
                ", releaseYear=" + releaseYear +
                ", language='" + language + '\'' +
                '}';
    }

    public static class Builder {
        private final String id;
        private final String title;
        private final String artist;
        private final int durationSecs;

        private String album;
        private String genre;
        private Integer releaseYear;
        private String language;

        public Builder(String id, String title, String artist, int durationSecs) {
            if(id == null || id.isBlank() || title == null || title.isBlank()) {
                throw new IllegalArgumentException("Title and Id must not be empty");
            }
            if(durationSecs <= 0) {
                throw new IllegalArgumentException("duration cant be less than 1 second");
            }
            this.id = id;
            this.title = title;
            this.artist = artist;
            this.durationSecs = durationSecs;
        }

        public Builder album(String album) {
            this.album = album;
            return this;
        }

        public Builder genre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder releaseYear(Integer releaseYear) {
            this.releaseYear = releaseYear;
            return this;
        }

        public Builder language(String language) {
            this.language = language;
            return this;
        }

        public Song build() {
           return new Song(this);
        }

    }
}
