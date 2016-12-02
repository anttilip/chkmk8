package com.anttilip.chkm8.controller;

import com.anttilip.chkm8.model.ChessState;
import com.anttilip.chkm8.model.Position;
import com.anttilip.chkm8.model.pieces.Piece;
import com.anttilip.chkm8.view.ChessView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class ChessController {

    private static final int POINTER = 0;
    private final ChessState state;
    private final PlayerInputProcessor playerInputProcessor;
    private Piece currentlyPressedPiece;

    /**
     * Constructor for main controller class.
     *
     * Player manipulates model through this class.
     * @param state ChessState that wraps the model.
     */
    public ChessController(ChessState state) {
        this.state = state;
        this.playerInputProcessor = new PlayerInputProcessor();
        Gdx.input.setInputProcessor(playerInputProcessor);
        this.currentlyPressedPiece = null;
    }

    public Piece getCurrentlyPressedPiece() {
        return currentlyPressedPiece;
    }

    /**
     * Reads user input and moves pieces according to players input.
     */
    public void update() {
        if (playerInputProcessor.isDragged()) {
            // Left mouse key is down
            Vector2 clickPos = playerInputProcessor.getLastClickPosition();
            Piece dragged = state.getPieceAt(ChessView.screenToBoardPosition(clickPos));
            if (dragged != null && state.getCurrentPlayer() == dragged.getPlayer()) {
                // Piece is being clicked
                this.currentlyPressedPiece = dragged;
            }
        } else if (this.currentlyPressedPiece != null) {
            // Piece was just released
            Position releasePos = ChessView.screenToBoardPosition(playerInputProcessor.getLastClickPosition());
            for (Position pos : state.getGetPiecesAllowedMoves(this.currentlyPressedPiece)) {
                if (pos.equals(releasePos)) {
                    // If piece was released in allowed square, move piece to that square
                    state.move(this.currentlyPressedPiece, pos);
                }
            }
            currentlyPressedPiece = null;
        }
    }

    /**
     * Check if given piece is being dragged by player.
     * @param piece Piece for which dragging is checked.
     * @return Returns boolean value of piece being dragged.
     */
    public boolean pieceIsDragged(Piece piece) {
        return piece.equals(this.currentlyPressedPiece);
    }

    public int getDraggedX() {
        return Gdx.input.getX(POINTER);
    }

    public int getDraggedY() {
        return Gdx.input.getY(POINTER);
    }
}
