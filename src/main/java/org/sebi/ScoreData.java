package org.sebi;

public class ScoreData implements Comparable<ScoreData> {
    public String playerID;
    public String playerName;

    public int inky = 0;
    public int blinky = 0;
    public int pinky = 0;
    public int clyde = 0;
    public int score = 0;

    private ScoreData(String playerID, String playerName, int inky, int blinky, int pinky, int clyde, int score) {
        this.playerID = playerID;
        this.playerName = playerName;
        this.inky = inky;
        this.blinky = blinky;
        this.pinky = pinky;
        this.clyde = clyde;
        this.score = score;
    }

    public static ScoreData from(ScoreAggregate aggregate) {
        return new ScoreData(
            aggregate.playerId,
            aggregate.playerName,
            aggregate.inky,
            aggregate.blinky,
            aggregate.pinky,
            aggregate.clyde,
            aggregate.score
        );
    }
    @Override
    public int compareTo(ScoreData o) {
        Integer thisInteger = Integer.valueOf(this.score);
        return thisInteger.compareTo(Integer.valueOf(o.score));
    }
   
}

