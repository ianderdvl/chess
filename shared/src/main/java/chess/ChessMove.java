package chess;

import java.util.Objects;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */

public class ChessMove
{

    /**
     * @return ChessPosition of starting location
     */
    private final ChessPosition initialPosition;

    /**
     * @return ChessPosition of ending location
     */
    private final ChessPosition finalPosition;

    /**
     * @return ChessPosition of ending location
     */
    private final ChessPiece.PieceType promotedPiece;

    // Constructor to initialize a chess move
    public ChessMove(ChessPosition initialPosition, ChessPosition finalPosition, ChessPiece.PieceType promotedPiece)
    {

        this.initialPosition = initialPosition; // Initialize start position
        this.finalPosition = finalPosition; // Initialize end position
        this.promotedPiece = promotedPiece; // Initialize promotion piece
    }

    // Method to get the starting position of the move
    public ChessPosition getStartPosition()
    {

        return initialPosition;
    }

    // Method to get the ending position of the move
    public ChessPosition getEndPosition()
    {

        return finalPosition;
    }

    // Method to get the promotion piece type
    public ChessPiece.PieceType getPromotionPiece()
    {

        return promotedPiece;
    }

    @Override
    public boolean equals(Object obj)
    {

        if (this == obj)
        {

            return true;
        }

        if (obj == null || getClass() != obj.getClass())
        {

            return false;
        }

        ChessMove other = (ChessMove) obj;
        return Objects.equals(initialPosition, other.initialPosition) &&
                Objects.equals(finalPosition, other.finalPosition) &&
                promotedPiece == other.promotedPiece;
    }

    @Override
    public int hashCode()
    {

        return Objects.hash(initialPosition, finalPosition, promotedPiece);
    }
}

