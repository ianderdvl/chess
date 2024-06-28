package chess;

import java.util.Arrays;
import java.lang.Object;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * the signature of the existing methods.
 */
public class ChessBoard implements Cloneable {
    private ChessPiece[][] squares = new ChessPiece[8][8];

    // Constructor: initializes the chessboard with empty squares
    public ChessBoard() {
        clearBoard();
    }

    // Helper method to clear the board by setting all squares to null
    private void clearBoard() {
        for (ChessPiece[] row : squares) {
            Arrays.fill(row, null);
        }
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        int rowIndex = position.getRow() - 1;
        int colIndex = position.getColumn() - 1;
        squares[rowIndex][colIndex] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that position
     */
    public ChessPiece getPiece(ChessPosition position) {
        int rowIndex = position.getRow() - 1;
        int colIndex = position.getColumn() - 1;
        return squares[rowIndex][colIndex];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        clearBoard(); // Clear the board first

        placeWhitePieces(); // Place all white pieces on the board
        placeBlackPieces(); // Place all black pieces on the board
    }

    // Helper method to place all white pieces in their starting positions
    private void placeWhitePieces() {
        placeMajorPieces(0, ChessGame.TeamColor.WHITE);
        placePawns(1, ChessGame.TeamColor.WHITE);
    }

    // Helper method to place all black pieces in their starting positions
    private void placeBlackPieces() {
        placeMajorPieces(7, ChessGame.TeamColor.BLACK);
        placePawns(6, ChessGame.TeamColor.BLACK);
    }

    // Helper method to place major pieces (Rook, Knight, Bishop, Queen, King) on the board
    private void placeMajorPieces(int row, ChessGame.TeamColor color) {
        squares[row][0] = new ChessPiece(color, ChessPiece.PieceType.ROOK);
        squares[row][7] = new ChessPiece(color, ChessPiece.PieceType.ROOK);
        squares[row][1] = new ChessPiece(color, ChessPiece.PieceType.KNIGHT);
        squares[row][6] = new ChessPiece(color, ChessPiece.PieceType.KNIGHT);
        squares[row][2] = new ChessPiece(color, ChessPiece.PieceType.BISHOP);
        squares[row][5] = new ChessPiece(color, ChessPiece.PieceType.BISHOP);
        squares[row][3] = new ChessPiece(color, ChessPiece.PieceType.QUEEN);
        squares[row][4] = new ChessPiece(color, ChessPiece.PieceType.KING);
    }

    // Helper method to place pawns on the board
    private void placePawns(int row, ChessGame.TeamColor color) {
        for (int col = 0; col < 8; col++) {
            squares[row][col] = new ChessPiece(color, ChessPiece.PieceType.PAWN);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ChessBoard that = (ChessBoard) obj;
        return Arrays.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }

    @Override
    public ChessBoard clone() {
        try {
            ChessBoard cloned = (ChessBoard) super.clone();
            cloned.squares = new ChessPiece[8][8];
            for (int i = 0; i < squares.length; i++) {
                for (int j = 0; j < squares[i].length; j++) {
                    if (squares[i][j] != null) {
                        cloned.squares[i][j] = squares[i][j].clone();
                    }
                }
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // This should not happen
        }
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "squares=" + Arrays.deepToString(squares) +
                '}';
    }
}
