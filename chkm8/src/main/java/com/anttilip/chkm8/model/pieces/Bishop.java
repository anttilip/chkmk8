package com.anttilip.chkm8.model.pieces;

import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;

/**
 * Represents a bishop, extends the abstract Piece class.
 */
public class Bishop extends Piece {
    /**
     * Bishops allowed move directions.
     */
    private static final Position[] MOVE_DIRECTIONS = {
        new Position(1, 1), // Up-Right
        new Position(-1, 1), // Up-Left
        new Position(-1, -1), // Down-Left
        new Position(1, -1) // Down-Right
    };

    /**
     * Constructor for Bishop.
     * @param position Position of the bishop
     * @param player Player to whom bishop belongs to
     */
    public Bishop(Position position, Player player) {
        super(position, player, true);
    }

    @Override
     public Position[] getMoveDirections() {
        return MOVE_DIRECTIONS;
    }

    @Override
    public Piece copy() {
        return new Bishop(this.position.copy(), this.player);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Bishop)) {
            return false;
        }
        Bishop other = (Bishop) o;
        return other.position.equals(this.position) && other.player == this.player;
    }

}
