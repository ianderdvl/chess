package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */

public class ChessGame
{

    // Enum to represent team colors
    public enum TeamColor
    {
        BLACK,
        WHITE
    }

    // Current turn of the game
    private TeamColor currentTurn;

    // Chessboard for the game
    private ChessBoard board;

    // Constructor to initialize a new chess game
    public ChessGame()
    {
        this.currentTurn = TeamColor.WHITE; // Set initial turn to white
        this.board = new ChessBoard(); // Initialize the chessboard
        board.resetBoard(); // Set up the board with initial positions
    }

    // Method to get the current team's turn
    public TeamColor getTeamTurn()
    {
        return this.currentTurn;
    }

    // Method to set the current team's turn
    public void setTeamTurn(TeamColor team)
    {
        this.currentTurn = team;
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    // Method to get valid moves for a piece at a given position
    public Collection<ChessMove> validMoves(ChessPosition startPosition)
    {
        ChessPiece currentPiece = board.getPiece(startPosition);
        if (currentPiece == null)
        {
            return null;
        }

        Collection<ChessMove> validMoves = new ArrayList<>();
        Collection<ChessMove> possibleMoves = currentPiece.pieceMoves(board, startPosition);

        for (ChessMove move : possibleMoves)
        {
            ChessBoard tempBoard = board.clone();
            testMove(move);
            if (!isInCheck(currentPiece.teamColor()))
            {
                validMoves.add(move);
            }
            board = tempBoard;
        }
        return validMoves;
    }

    // Method to test a move
    public void testMove(ChessMove move)
    {
        ChessPiece piece = board.getPiece(move.getStartPosition());
        if (piece == null)
        {
            return;
        }

        if (move.getPromotionPiece() == null)
        {
            board.addPiece(move.getEndPosition(), piece);
        }
        else
        {
            board.addPiece(move.getEndPosition(), new ChessPiece(piece.teamColor(), move.getPromotionPiece()));
        }
        board.addPiece(move.getStartPosition(), null);
    }

    // Method to make a move
    public void makeMove(ChessMove move) throws InvalidMoveException
    {
        ChessPiece piece = board.getPiece(move.getStartPosition());
        if (piece == null)
        {
            throw new InvalidMoveException("No piece at the start position.");
        }

        if (!isValidPosition(move.getStartPosition()) || !isValidPosition(move.getEndPosition()))
        {
            throw new InvalidMoveException("Invalid move positions.");
        }

        if (piece.teamColor() != getTeamTurn())
        {
            throw new InvalidMoveException("Not your turn!");
        }

        if (!validMoves(move.getStartPosition()).contains(move))
        {
            throw new InvalidMoveException("Invalid move.");
        }

        if (move.getPromotionPiece() == null)
        {
            board.addPiece(move.getEndPosition(), piece);
        }
        else
        {
            board.addPiece(move.getEndPosition(), new ChessPiece(piece.teamColor(), move.getPromotionPiece()));
        }
        board.addPiece(move.getStartPosition(), null);

        switchTurn();
    }

    // Method to check if a position is valid
    private boolean isValidPosition(ChessPosition position)
    {
        int row = position.getRow();
        int col = position.getColumn();
        return row > 0 && row <= 8 && col > 0 && col <= 8;
    }

    // Method to switch turns
    private void switchTurn()
    {
        if (currentTurn == TeamColor.WHITE)
        {
            setTeamTurn(TeamColor.BLACK);
        }
        else
        {
            setTeamTurn(TeamColor.WHITE);
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor)
    {
        TeamColor opponentColor = (teamColor == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;
        ChessPosition kingPosition = findKing(teamColor);
        if (kingPosition == null)
        {
            return false;
        }

        for (ChessPosition pos : getTeamPositions(opponentColor))
        {
            ChessPiece piece = board.getPiece(pos);
            for (ChessMove move : piece.pieceMoves(board, pos))
            {
                if (move.getEndPosition().equals(kingPosition))
                {
                    return true;
                }
            }
        }
        return false;
    }

    // Method to get positions of all pieces of a team
    public Collection<ChessPosition> getTeamPositions(TeamColor color)
    {
        Collection<ChessPosition> positions = new ArrayList<>();
        for (int row = 1; row <= 8; row++)
        {
            for (int col = 1; col <= 8; col++)
            {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(position);
                if (piece != null && piece.teamColor() == color)
                {
                    positions.add(position);
                }
            }
        }
        return positions;
    }

    // Method to find the king's position of a team
    public ChessPosition findKing(TeamColor color)
    {
        for (int row = 1; row <= 8; row++)
        {
            for (int col = 1; col <= 8; col++)
            {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(position);
                if (piece != null && piece.teamColor() == color && piece.pieceType() == ChessPiece.PieceType.KING)
                {
                    return position;
                }
            }
        }
        return null;
    }

    // Method to check if a team is in checkmate
    public boolean isInCheckmate(TeamColor teamColor)
    {
        if (!isInCheck(teamColor))
        {
            return false;
        }
        for (ChessPosition pos : getTeamPositions(teamColor))
        {
            if (!validMoves(pos).isEmpty())
            {
                return false;
            }
        }
        return true;
    }

    // Method to check if a team is in stalemate
    public boolean isInStalemate(TeamColor teamColor)
    {
        if (isInCheck(teamColor))
        {
            return false;
        }
        for (ChessPosition pos : getTeamPositions(teamColor))
        {
            if (!validMoves(pos).isEmpty())
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        throw new RuntimeException("Not implemented");
    }
}
