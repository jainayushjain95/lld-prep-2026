package musicplaylistmixer.services.play;

import musicplaylistmixer.constants.Mode;

import java.util.Random;

public class PlayStrategyFactory {
    public static PlayStrategy create(Mode mode, Random random) {
        if(mode.equals(Mode.SEQUENTIAL)) {
            return new PlaySequentialStrategy();
        }
        if(mode.equals(Mode.SHUFFLE)) {
            return new PlayShuffleStrategy(random);
        }
        if(mode.equals(Mode.REPEAT_ONE)) {
            return new PlayRepeatOneStrategy();
        }
        if(mode.equals(Mode.REPEAT_ALL)) {
            return new PlayRepeatAllStrategy();
        }
        throw new IllegalArgumentException("Invalid mode");
    }
}
