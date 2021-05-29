package org.sebi;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ScoreEvent {

    private String playerId;

    private String playerName;

    private String ghost;

    

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerID(String playerId) {
        this.playerId = playerId;
    }

    public String getGhost() {
        return ghost;
    }

    public void setGhost(String ghost) {
        this.ghost = ghost;
    }

    public ScoreEvent(String playerId, String playerName, String ghost) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.ghost = ghost;
    }

    public ScoreEvent() {
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

}
