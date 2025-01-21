package chess;

import java.util.ArrayList;
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
    // Method to get possible moves for the piece
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition currentPosition)
    {
        Collection<ChessMove> possibleMoves = new ArrayList<>();
        ChessPiece pieceAtPos = board.getPiece(currentPosition);

        if (pieceAtPos != null)
        {
            switch (pieceAtPos.getPieceType())
            {
                case PAWN:
                    possibleMoves.addAll(getPawnMoves(board, currentPosition, pieceAtPos.getTeamColor()));
                    break;

                case BISHOP:
                    possibleMoves.addAll(getBishopMoves(board, currentPosition, pieceAtPos.getTeamColor()));
                    break;

                case ROOK:
                    possibleMoves.addAll(getRookMoves(board, currentPosition, pieceAtPos.getTeamColor()));
                    break;

                case QUEEN:
                    possibleMoves.addAll(getQueenMoves(board, currentPosition, pieceAtPos.getTeamColor()));
                    break;

                case KNIGHT:
                    possibleMoves.addAll(getKnightMoves(board, currentPosition, pieceAtPos.getTeamColor()));
                    break;

                case KING:
                    possibleMoves.addAll(getKingMoves(board, currentPosition, pieceAtPos.getTeamColor()));
                    break;
            }
        }
        return possibleMoves;
    }

    // Method to get possible moves for a pawn
    private Collection<ChessMove> getPawnMoves(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color)
    {
        Collection<ChessMove> pawnMoves = new ArrayList<>();
        int forwardRow = (color == ChessGame.TeamColor.WHITE) ? pos.getRow() + 1 : pos.getRow() - 1;
        int col = pos.getColumn();

        if (isValidPosition(forwardRow, col + 1))
        {
            ChessPosition newPos = new ChessPosition(forwardRow, col + 1);
            if (board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() != color)
            {
                addPawnMove(pawnMoves, pos, newPos, forwardRow);
            }
        }

        if (isValidPosition(forwardRow, col - 1))
        {
            ChessPosition newPos = new ChessPosition(forwardRow, col - 1);
            if (board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() != color)
            {
                addPawnMove(pawnMoves, pos, newPos, forwardRow);
            }
        }

        if (isValidPosition(forwardRow, col) && board.getPiece(new ChessPosition(forwardRow, col)) == null)
        {
            addPawnMove(pawnMoves, pos, new ChessPosition(forwardRow, col), forwardRow);
        }

        if (color == ChessGame.TeamColor.WHITE && pos.getRow() == 2 &&
                isValidPosition(forwardRow + 1, col) &&
                board.getPiece(new ChessPosition(forwardRow + 1, col)) == null &&
                board.getPiece(new ChessPosition(forwardRow, col)) == null)
        {
            pawnMoves.add(new ChessMove(pos, new ChessPosition(forwardRow + 1, col), null));
        }

        if (color == ChessGame.TeamColor.BLACK && pos.getRow() == 7 &&
                isValidPosition(forwardRow - 1, col) &&
                board.getPiece(new ChessPosition(forwardRow - 1, col)) == null &&
                board.getPiece(new ChessPosition(forwardRow, col)) == null)
        {
            pawnMoves.add(new ChessMove(pos, new ChessPosition(forwardRow - 1, col), null));
        }
        return pawnMoves;
    }
}
