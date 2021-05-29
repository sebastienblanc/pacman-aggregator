package org.sebi;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ScoreAggregate {
    
    public String playerId;
    
    public String playerName;

    public int inky = 0;
    public int blinky = 0;
    public int pinky = 0;
    public int clyde = 0;
    public int score = 0;

    public ScoreAggregate updateFrom(ScoreEvent event){
        playerId = event.getPlayerId();
        playerName = event.getPlayerName();
        if(event.getGhost().equals("blinky")){
            blinky++;
            score++;
        }
        else if(event.getGhost().equals("pinky")){
            pinky++;
            score++;
        }
        else if(event.getGhost().equals("clyde")){
            clyde++;
            score++;
        }
        else if(event.getGhost().equals("inky")){
            inky++;
            score++;
        }
        return this;
    }

}
