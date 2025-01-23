package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

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

    // Method to add a pawn move to the list of moves
    private void addPawnMove(Collection<ChessMove> moves, ChessPosition startPos, ChessPosition endPos, int forwardRow)
    {

        if (forwardRow == 1 || forwardRow == 8)
        {

            moves.add(new ChessMove(startPos, endPos, PieceType.QUEEN));
            moves.add(new ChessMove(startPos, endPos, PieceType.BISHOP));
            moves.add(new ChessMove(startPos, endPos, PieceType.ROOK));
            moves.add(new ChessMove(startPos, endPos, PieceType.KNIGHT));
        }
        else
        {

            moves.add(new ChessMove(startPos, endPos, null));
        }
    }

    // Method to get possible moves for a bishop
    private Collection<ChessMove> getBishopMoves(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color)
    {

        Collection<ChessMove> bishopMoves = new ArrayList<>();
        bishopMoves.addAll(getMovesLoop(board, pos, color, 1, 1));
        bishopMoves.addAll(getMovesLoop(board, pos, color, 1, 2));
        bishopMoves.addAll(getMovesLoop(board, pos, color, 2, 2));
        bishopMoves.addAll(getMovesLoop(board, pos, color, 2, 1));
        return bishopMoves;
    }

    // Method to get possible moves for a rook
    private Collection<ChessMove> getRookMoves(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color)
    {

        Collection<ChessMove> rookMoves = new ArrayList<>();
        rookMoves.addAll(getMovesLoop(board, pos, color, 1, 0));
        rookMoves.addAll(getMovesLoop(board, pos, color, 0, 1));
        rookMoves.addAll(getMovesLoop(board, pos, color, 2, 0));
        rookMoves.addAll(getMovesLoop(board, pos, color, 0, 2));
        return rookMoves;
    }

    // Method to get possible moves for a queen
    private Collection<ChessMove> getQueenMoves(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color)
    {

        Collection<ChessMove> queenMoves = new ArrayList<>();
        queenMoves.addAll(getRookMoves(board, pos, color));
        queenMoves.addAll(getBishopMoves(board, pos, color));
        return queenMoves;
    }

    // Method to get possible moves for a knight
    private Collection<ChessMove> getKnightMoves(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color)
    {

        Collection<ChessMove> knightMoves = new ArrayList<>();
        Collection<ChessPosition> potentialMoves = new ArrayList<>();

        potentialMoves.add(new ChessPosition(pos.getRow() + 2, pos.getColumn() + 1));
        potentialMoves.add(new ChessPosition(pos.getRow() + 2, pos.getColumn() - 1));
        potentialMoves.add(new ChessPosition(pos.getRow() - 2, pos.getColumn() + 1));
        potentialMoves.add(new ChessPosition(pos.getRow() - 2, pos.getColumn() - 1));
        potentialMoves.add(new ChessPosition(pos.getRow() + 1, pos.getColumn() + 2));
        potentialMoves.add(new ChessPosition(pos.getRow() + 1, pos.getColumn() - 2));
        potentialMoves.add(new ChessPosition(pos.getRow() - 1, pos.getColumn() + 2));
        potentialMoves.add(new ChessPosition(pos.getRow() - 1, pos.getColumn() - 2));

        for (ChessPosition move : potentialMoves)
        {

            if (isValidPosition(move.getRow(), move.getColumn()))
            {

                if (board.getPiece(move) == null || board.getPiece(move).getTeamColor() != color)
                {

                    knightMoves.add(new ChessMove(pos, move, null));
                }
            }
        }
        return knightMoves;
    }

    // Method to get possible moves for a king
    private Collection<ChessMove> getKingMoves(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color)
    {

        Collection<ChessMove> kingMoves = new ArrayList<>();
        Collection<ChessPosition> potentialMoves = new ArrayList<>();

        potentialMoves.add(new ChessPosition(pos.getRow(), pos.getColumn() + 1));
        potentialMoves.add(new ChessPosition(pos.getRow(), pos.getColumn() - 1));
        potentialMoves.add(new ChessPosition(pos.getRow() + 1, pos.getColumn()));
        potentialMoves.add(new ChessPosition(pos.getRow() - 1, pos.getColumn()));
        potentialMoves.add(new ChessPosition(pos.getRow() + 1, pos.getColumn() + 1));
        potentialMoves.add(new ChessPosition(pos.getRow() + 1, pos.getColumn() - 1));
        potentialMoves.add(new ChessPosition(pos.getRow() - 1, pos.getColumn() + 1));
        potentialMoves.add(new ChessPosition(pos.getRow() - 1, pos.getColumn() - 1));

        for (ChessPosition move : potentialMoves)
        {

            if (isValidPosition(move.getRow(), move.getColumn()))
            {

                if (board.getPiece(move) == null || board.getPiece(move).getTeamColor() != color)
                {

                    kingMoves.add(new ChessMove(pos, move, null));
                }
            }
        }
        return kingMoves;
    }

    // Method to validate the position on the board
    private boolean isValidPosition(int row, int col)
    {

        return row >= 1 && row <= 8 && col >= 1 && col <= 8;
    }

    // Method to get moves in a loop for bishops and rooks
    private Collection<ChessMove> getMovesLoop(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color,
                                               int rowDirection, int colDirection)
    {

        Collection<ChessMove> moves = new ArrayList<>();
        int currentRow = pos.getRow();
        int currentCol = pos.getColumn();

        while (true)
        {

            if (rowDirection == 1) {

                currentRow ++;
            }
            if (rowDirection == 2) {

                currentRow --;
            }
            if (colDirection == 1) {

                currentCol ++;
            }
            if (colDirection == 2) {

                currentCol --;
            }

            if (!isValidPosition(currentRow, currentCol)) {

                break;
            }

            ChessPosition newPos = new ChessPosition(currentRow, currentCol);
            ChessPiece pieceAtNewPos = board.getPiece(newPos);

            if (pieceAtNewPos != null)
            {

                if (pieceAtNewPos.getTeamColor() != color)
                {

                    moves.add(new ChessMove(pos, newPos, null));
                }
                break;
            }
            else
            {

                moves.add(new ChessMove(pos, newPos, null));
            }
        }
        return moves;
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
        ChessPiece other = (ChessPiece) obj;
        return teamColor == other.teamColor && pieceType == other.pieceType;
    }

    @Override
    public int hashCode()
    {

        return Objects.hash(teamColor, pieceType);
    }

    @Override
    public ChessPiece clone()
    {

        try
        {

            return (ChessPiece) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            
            throw new InternalError(e);
        }
    }
}