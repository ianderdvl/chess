package chess;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.Gson;

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
            if (!isInCheck(currentPiece.getTeamColor()))
            {
                validMoves.add(move);
            }
            board = tempBoard;
        }
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
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
