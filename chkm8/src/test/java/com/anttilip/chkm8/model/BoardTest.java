package com.anttilip.chkm8.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.anttilip.chkm8.model.pieces.Piece;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class BoardTest {
    private Board board;
    
    public BoardTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        board = Board.createBoard();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void checkNotNull() {
        assertFalse(board == null);
    }
    
    @Test
    public void initialPieceCount() {
        assertTrue(board.getPieces().size() == 32);
    }
    
    @Test
    public void noPieceIsOutsideWhitePawnRow() {
        assertTrue(board.getPiece(8, 1) == null);
    }
    
    @Test
    public void noPieceIsOutsideWhiteKingRow() {
        assertTrue(board.getPiece(8, 0) == null);
    }
    
    @Test
    public void noPieceIsOutsideBlackPawnRow() {
        assertTrue(board.getPiece(8, 6) == null);
    }
    
    @Test
    public void noPieceIsOutsideBlackKingRow() {
        assertTrue(board.getPiece(8, 7) == null);
    }
    
    @Test
    public void initialWhitePawnPosition() {
        assertTrue(board.getPiece(3, 1).toString().equals("WHITE Pawn"));
    }
    
    @Test
    public void initialWhiteKingPosition() {
        assertTrue(board.getPiece(4, 0).toString().equals("WHITE King"));
    }
    
    @Test
    public void initialBlackKingPawnPosition() {
        assertTrue(board.getPiece(6, 6).toString().equals("BLACK Pawn"));
    }
    
    @Test
    public void initialBlackQueenPosition() {
        assertTrue(board.getPiece(3, 7).toString().equals("BLACK Queen"));
    }
    
    @Test
    public void movePieceMovesPiece() {
        Piece whiteRook = board.getPiece(0, 0);
        board.movePiece(whiteRook, new Position(4, 4));
        assertTrue(board.getPiece(4, 4) == whiteRook);
    }
    
    @Test
    public void movePieceLeavesOldPositionEmpty() {
        Piece whiteRook = board.getPiece(0, 0);
        board.movePiece(whiteRook, new Position(4, 4));
        assertTrue(board.getPiece(0, 0) == null);
    }
    
    @Test
    public void eatenPieceIsRemoved() {
        Piece whiteRook = board.getPiece(0, 0);
        Piece blackQueen = board.getPiece(3, 7);
        board.movePiece(whiteRook, blackQueen.getPosition());
        assertFalse(board.getPieces().contains(blackQueen));
    }

    @Test
    public void boardCopyIsSameAsOriginal() {
        assertTrue(board.copy().equals(board));
    }

    @Test
    public void boardCopyIsntSameAsOther() {
        Board copy = board.copy();
        board.movePiece(board.getPiece(3, 1), new Position(3, 2));
        assertFalse(copy.equals(board));
    }

    @Test
    public void boardCopyIsntSameAsOther2() {
        String other = "Not the same";
        assertFalse(board.equals(other));
    }

    @Test
    public void hashCodeWithSameIsSame() {
        assertTrue(board.hashCode() == board.copy().hashCode());
    }

}
