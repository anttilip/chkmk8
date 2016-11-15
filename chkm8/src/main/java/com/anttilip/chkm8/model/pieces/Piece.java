package com.anttilip.chkm8.model.pieces;

import com.anttilip.chkm8.model.Board;
import com.anttilip.chkm8.model.Player;
import com.anttilip.chkm8.model.Position;
import java.util.List;

public abstract class Piece {

    protected Position position;
    protected final Player player;

    public Piece(Position position, Player player) {
        this.player = player;
        this.position = position;
    }

    public void move(Position newPosition, Board board) {
        this.position = newPosition;
    }

    public abstract List<Position> getAllowedMoves(Board board, boolean selfCheckAllowed);

    public boolean moveLeadsToSelfCheck(Position target, Board board) {
        Board boardCopy = board.copy();
        boardCopy.movePiece(boardCopy.getPiece(this.position), target);
        return boardCopy.isCheck(this.player);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Player getPlayer() {
        return this.player;
    }

    @Override
    public String toString() {
        return this.player + " " + this.getClass().getSimpleName();
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash = 5 * hash + this.position.hashCode();
        hash = 19 * hash + ((this.player == Player.WHITE) ? 11 : 21);
        return 31 * hash + this.getClass().hashCode();
    }

//    @Override
//    public boolean equals(Object o) {
//        if (!(o instanceof Piece)) {
//            return false;
//        }
//        Piece other = (Position) o;
//
//        return other.x == this.x && other.y == this.y;
//    }

    @Override
    public abstract boolean equals(Object o);

    public abstract Piece copy();
}
