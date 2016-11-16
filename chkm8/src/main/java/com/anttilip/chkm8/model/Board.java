package com.anttilip.chkm8.model;

import com.anttilip.chkm8.model.pieces.Queen;
import com.anttilip.chkm8.model.pieces.Bishop;
import com.anttilip.chkm8.model.pieces.Rook;
import com.anttilip.chkm8.model.pieces.Piece;
import com.anttilip.chkm8.model.pieces.Pawn;
import com.anttilip.chkm8.model.pieces.King;
import com.anttilip.chkm8.model.pieces.Knight;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public static final int BOARD_SIZE = 8;
    private final List<Piece> pieces;
    private Position enPassantPosition;

    public Board(List<Piece> pieces) {
        this.pieces = pieces;
        this.enPassantPosition = null;
    }

    public void movePiece(Piece piece, Position target) {
        // If target position contains a piece, it will be eaten and removed
        if (this.getPiece(target) != null) {
            this.getPiece(target).kill(this);
        }

        // Remove en passant since no one attacked it this turn
        if (enPassantPosition != null && !target.equals(this.enPassantPosition)) {
            enPassantPosition = null;
        }
        // Move piece to its new position
        piece.move(target, this);
    }

    public List<Position> getAllowedMoves(Piece piece) {
        return piece.getAllowedMoves(this, false);
    }

    public Piece getPiece(Position position) {
        for (Piece piece : this.pieces) {
            if (piece.getPosition().equals(position)) {
                return piece;
            }
        }
        return null;
    }

    public Piece getPiece(int x, int y) {
        return getPiece(new Position(x, y));
    }

    public List<Piece> getPieces() {
        return this.pieces;
    }

    public List<Piece> getPieces(Player player) {
        List<Piece> playersPieces = new ArrayList<>();
        for (Piece piece : this.pieces) {
            if (piece.getPlayer() == player) {
                playersPieces.add(piece);
            }
        }
        return playersPieces;
    }

    public King getKing(Player player) {
        for (Piece piece : this.pieces) {
            if (piece instanceof King && piece.getPlayer() == player) {
                return (King) piece;
            }
        }
        return null;
    }

    public Position getEnPassantPosition() {
        return this.enPassantPosition;
    }

    public void setEnPassantPosition(Position enPassantPosition) {
        this.enPassantPosition = enPassantPosition;
    }

    public boolean isOccupied(Position position) {
        return getPiece(position) != null;
    }


    public boolean isCheck(Player player) {
        King king = this.getKing(player);
        Player other = Player.getOther(player);
        for (Piece piece : this.getPieces(other)) {
            if (piece.getAllowedMoves(this, true).contains(king.getPosition())) {
                return true;
            }
        }
        return false;
    }

    public static Board createBoard() {
        List<Piece> pieces = new ArrayList<>();

        for (Player player : Player.values()) {
            // Create pawns
            for (int x = 0; x < BOARD_SIZE; x++) {
                pieces.add(new Pawn(new Position(x, 1 + (BOARD_SIZE - 3) * player.getValue()), player));
            }

            // Create Rooks
            pieces.add(new Rook(new Position(0, 7 * player.getValue()), player));
            pieces.add(new Rook(new Position(BOARD_SIZE - 1, (BOARD_SIZE - 1) * player.getValue()), player));

            // Create Knights
            pieces.add(new Knight(new Position(1, 7 * player.getValue()), player));
            pieces.add(new Knight(new Position(BOARD_SIZE - 2, (BOARD_SIZE - 1) * player.getValue()), player));

            // Create Bishops
            pieces.add(new Bishop(new Position(2, 7 * player.getValue()), player));
            pieces.add(new Bishop(new Position(BOARD_SIZE - 3, (BOARD_SIZE - 1) * player.getValue()), player));

            // Create Queen
            pieces.add(new Queen(new Position(3, (BOARD_SIZE - 1) * player.getValue()), player));

            // Create King
            pieces.add(new King(new Position(4, (BOARD_SIZE - 1) * player.getValue()), player));

        }

        return new Board(pieces);
    }

    public Board copy() {
        List<Piece> piecesCopy = new ArrayList<>();
        for (Piece orig : this.pieces) {
            piecesCopy.add(orig.copy());
        }
        Board copy = new Board(piecesCopy);
        copy.enPassantPosition = this.enPassantPosition;
        return copy;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        for (Piece piece : this.pieces) {
            hash = 7 * hash + piece.hashCode();
        }
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Board)) {
            return false;
        }
        Board other = (Board) o;
        for (Piece piece : other.getPieces()) {
            if (!this.pieces.contains(piece)) {
                return false;
            }
        }
        return other.getPieces().size() == this.pieces.size();
    }
}
