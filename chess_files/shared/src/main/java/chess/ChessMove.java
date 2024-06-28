package chess;

import java.util.Objects;

/**
 * Represents moving a chess piece on a chessboard.
 * <p>
 * Note: You can add to this class, but you may not alter
 * the signature of the existing methods.
 */
public class ChessMove {
    private final ChessPosition startPosition;  // The starting position of the move
    private final ChessPosition endPosition;    // The ending position of the move
    private final ChessPiece.PieceType promotionPiece;  // The piece type for pawn promotion, if applicable

    /**
     * Constructor for creating a ChessMove.
     *
     * @param startPosition   The starting position of the move.
     * @param endPosition     The ending position of the move.
     * @param promotionPiece  The piece type to promote a pawn to, if applicable (can be null).
     */
    public ChessMove(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promotionPiece) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.promotionPiece = promotionPiece;
    }

    /**
     * Gets the starting position of the move.
     *
     * @return The starting position as a ChessPosition object.
     */
    public ChessPosition getStartPosition() {
        return startPosition;
    }

    /**
     * Gets the ending position of the move.
     *
     * @return The ending position as a ChessPosition object.
     */
    public ChessPosition getEndPosition() {
        return endPosition;
    }

    /**
     * Gets the type of piece to promote a pawn to, if pawn promotion is part of this move.
     *
     * @return The type of piece for promotion, or null if no promotion is involved.
     */
    public ChessPiece.PieceType getPromotionPiece() {
        return promotionPiece;
    }

    /**
     * Checks if this ChessMove is equal to another object.
     *
     * @param obj The object to compare with.
     * @return True if the objects are equal, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;  // Same instance
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;  // Null or different class
        }

        ChessMove other = (ChessMove) obj;
        return Objects.equals(startPosition, other.startPosition) &&
               Objects.equals(endPosition, other.endPosition) &&
               Objects.equals(promotionPiece, other.promotionPiece);
    }

    /**
     * Computes the hash code for this ChessMove.
     *
     * @return The hash code of this ChessMove.
     */
    @Override
    public int hashCode() {
        return Objects.hash(startPosition, endPosition, promotionPiece);
    }
}
