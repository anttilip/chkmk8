package com.anttilip.chkm8.model;

/**
 * Enum class for players colors, WHITE and BLACK.
 */
public enum Player {
    WHITE(0),
    BLACK(1);

    private final int value;

    Player(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }

    /**
     * Given a player, returns the players opponent.
     * @param player Given player whose opponent is returned.
     * @return Opponent of a given player.
     */
    public static Player getOther(Player player) {
        return (player == WHITE) ? BLACK : WHITE;
    }
}
