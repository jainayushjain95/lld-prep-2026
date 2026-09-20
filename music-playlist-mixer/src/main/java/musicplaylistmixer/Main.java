package musicplaylistmixer;

import musicplaylistmixer.services.play.PlayRepeatAllStrategy;
import musicplaylistmixer.services.play.PlayRepeatOneStrategy;
import musicplaylistmixer.services.play.PlaySequentialStrategy;
import musicplaylistmixer.services.play.PlayShuffleStrategy;

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

        Song untitledDemo = new Song.Builder("s5", "Apna Bana Le", "Arijit Singh", 267)
                .build();

        Playlist gym = new Playlist("Gym");
        gym.addSong(kesariya);
        gym.addSong(tumHiHo);
        gym.addSong(malang);
        gym.addSong(kesariyaLive);
        gym.addSong(untitledDemo);

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
    }

}

