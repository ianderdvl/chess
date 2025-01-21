package chess;

import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    // Type of the chess piece
    private final ChessPiece.PieceType pieceType;

    // Color of the chess piece
    private final ChessGame.TeamColor teamColor;

    // Constructor to initialize a chess piece
    public ChessPiece(ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType)
    {
        this.teamColor = teamColor; // Initialize the team color
        this.pieceType = pieceType; // Initialize the piece type
    }

    // Enum to represent piece types
    public enum PieceType
    {
        PAWN,
        KNIGHT,
        BISHOP,
        ROOK,
        QUEEN,
        KING
    }

    // Method to get the piece type
    public PieceType getPieceType()
    {
        return pieceType;
    }

    // Method to get the team color of the piece
    public ChessGame.TeamColor getTeamColor()
    {
        return teamColor;
    }
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
    }
}
