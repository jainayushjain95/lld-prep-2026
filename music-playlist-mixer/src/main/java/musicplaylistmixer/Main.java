package musicplaylistmixer;

import musicplaylistmixer.entities.Library;
import musicplaylistmixer.entities.MusicPlayer;
import musicplaylistmixer.entities.Playlist;
import musicplaylistmixer.entities.Song;
import musicplaylistmixer.entities.User;
import musicplaylistmixer.services.play.PlayRepeatAllStrategy;
import musicplaylistmixer.services.play.PlayRepeatOneStrategy;
import musicplaylistmixer.services.play.PlaySequentialStrategy;
import musicplaylistmixer.services.play.PlayShuffleStrategy;
import musicplaylistmixer.services.search.AllOfCriteria;
import musicplaylistmixer.services.search.AnyOfCriteria;
import musicplaylistmixer.services.search.ArtistCriteria;
import musicplaylistmixer.services.search.GenreCriteria;
import musicplaylistmixer.services.search.SearchCriteria;
import musicplaylistmixer.services.search.SearchService;
import musicplaylistmixer.services.search.TitleCriteria;
import musicplaylistmixer.services.sort.ArtistComparator;
import musicplaylistmixer.services.sort.ChainedComparator;
import musicplaylistmixer.services.sort.DurationComparator;
import musicplaylistmixer.services.sort.TitleComparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Song kesariya = new Song.Builder("s1", "Kesariya", "Arijit Singh", 268)
                .album("Brahmastra")
                .genre("Bollywood")
                .releaseYear(2022)
                .language("Hindi")
                .build();

        Song tumHiHo = new Song.Builder("s2", "Tum Hi Ho", "Arijit Singh", 262)
                .album("Aashiqui 2")
                .genre("Bollywood")
                .releaseYear(2013)
                .language("Hindi")
                .build();

        Song malang = new Song.Builder("s3", "Malang", "Ved Sharma", 241)
                .album("Malang")
                .genre("Rock")
                .build();

        Song kesariyaLive = new Song.Builder("s4", "Kesariya", "Arijit Singh", 310)
                .album("Live at Wembley")
                .genre("Live")
                .build();

        Song apnaBanaLe = new Song.Builder("s5", "Apna Bana Le", "Arijit Singh", 267)
                .build();

        Library library = new Library();
        library.addSong(kesariya);
        library.addSong(tumHiHo);
        library.addSong(malang);
        library.addSong(kesariyaLive);
        library.addSong(apnaBanaLe);

        User ayush = new User("u1", "Ayush");

        Playlist gym = ayush.createPlaylist("Gym");
        gym.addSong(kesariya);
        gym.addSong(tumHiHo);
        gym.addSong(malang);
        gym.addSong(kesariyaLive);
        gym.addSong(apnaBanaLe);

        System.out.println("=== 1. SEQUENTIAL to exhaustion ===");
        MusicPlayer sequentialPlayer = new MusicPlayer(gym, new PlaySequentialStrategy());
        Song song = sequentialPlayer.next();

        while (song != null) {
            System.out.println(song);
            song = sequentialPlayer.next();
        }

        System.out.println();
        System.out.println("=== 2. REPEAT_ALL, 8 calls ===");
        MusicPlayer repeatAllPlayer = new MusicPlayer(gym, new PlayRepeatAllStrategy());

        for (int i = 0; i < 8; i++) {
            System.out.println(repeatAllPlayer.next());
        }

        System.out.println();
        System.out.println("=== 3. REPEAT_ONE from fresh player, 5 calls ===");
        MusicPlayer repeatOnePlayer = new MusicPlayer(gym, new PlayRepeatOneStrategy());

        for (int i = 0; i < 5; i++) {
            System.out.println(repeatOnePlayer.next());
        }

        System.out.println();
        System.out.println("=== 4. SHUFFLE seeded 42, 6 calls ===");
        MusicPlayer shufflePlayer = new MusicPlayer(gym, new PlayShuffleStrategy(new Random(42)));

        for (int i = 0; i < 6; i++) {
            System.out.println(shufflePlayer.next());
        }

        System.out.println();
        System.out.println("=== 5. SEARCH: artist contains 'arij' ===");
        printSongs(SearchService.search(gym.getSongs(), new ArtistCriteria("arij")));

        System.out.println();
        System.out.println("=== 6. SEARCH: title contains 'kesariya' ===");
        printSongs(SearchService.search(gym.getSongs(), new TitleCriteria("kesariya")));

        System.out.println();
        System.out.println("=== 7. SEARCH: genre is exactly 'Bollywood' ===");
        printSongs(SearchService.search(gym.getSongs(), new GenreCriteria("Bollywood")));

        System.out.println();
        System.out.println("=== 8. SEARCH: artist 'Arijit' AND genre 'Bollywood' ===");
        List<SearchCriteria> arijitBollywood = new ArrayList<>();
        arijitBollywood.add(new ArtistCriteria("Arijit"));
        arijitBollywood.add(new GenreCriteria("Bollywood"));
        printSongs(SearchService.search(gym.getSongs(), new AllOfCriteria(arijitBollywood)));

        System.out.println();
        System.out.println("=== 9. SEARCH: artist 'Arijit' AND (genre 'Bollywood' OR 'Live') ===");
        List<SearchCriteria> genreOptions = new ArrayList<>();
        genreOptions.add(new GenreCriteria("Bollywood"));
        genreOptions.add(new GenreCriteria("Live"));

        List<SearchCriteria> nested = new ArrayList<>();
        nested.add(new ArtistCriteria("Arijit"));
        nested.add(new AnyOfCriteria(genreOptions));
        printSongs(SearchService.search(gym.getSongs(), new AllOfCriteria(nested)));

        System.out.println();
        System.out.println("=== 10. SEARCH the LIBRARY, not a playlist ===");
        printSongs(SearchService.search(library.getAllSongs(), new ArtistCriteria("Ved")));

        System.out.println();
        System.out.println("=== 11. SORT by title ===");
        printSongs(gym.sortedBy(new TitleComparator()));

        System.out.println();
        System.out.println("=== 12. SORT by duration ===");
        printSongs(gym.sortedBy(new DurationComparator()));

        System.out.println();
        System.out.println("=== 13. SORT by artist, then title ===");
        List<Comparator<Song>> levels = new ArrayList<>();
        levels.add(new ArtistComparator());
        levels.add(new TitleComparator());
        printSongs(gym.sortedBy(new ChainedComparator(levels)));

        System.out.println();
        System.out.println("=== 14. Stored order is UNCHANGED ===");
        printSongs(gym.getSongs());

        System.out.println();
        System.out.println("=== 15. MIX two playlists ===");

        Playlist lateNight = ayush.createPlaylist("Late Night");
        lateNight.addSong(tumHiHo);
        lateNight.addSong(kesariyaLive);
        lateNight.addSong(apnaBanaLe);

        System.out.println("Gym before:");
        printSongs(gym.getSongs());
        System.out.println("Late Night before:");
        printSongs(lateNight.getSongs());

        Playlist roadTrip = ayush.mix(gym, lateNight, "Road Trip");

        System.out.println("Mixed result (expect 5 songs, no duplicates):");
        printSongs(roadTrip.getSongs());

        System.out.println("Gym after (must be unchanged):");
        printSongs(gym.getSongs());
        System.out.println("Late Night after (must be unchanged):");
        printSongs(lateNight.getSongs());

        System.out.println("User's playlists (expect Gym, Late Night, Road Trip):");
        for (Playlist playlist : ayush.getPlaylists()) {
            System.out.println(playlist.getName());
        }
    }


    private static void printSongs(List<Song> songs) {

        if (songs.isEmpty()) {
            System.out.println("(no matches)");
            return;
        }

        for (Song song : songs) {
            System.out.println(song.getId() + " - " + song.getTitle() + " - " + song.getArtist() + " - " + song.getGenre());
        }
    }
}