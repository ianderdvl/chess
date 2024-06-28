package chess;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.Gson;

/**
 * A class that manages a chess game, making moves on a board.
 * <p>
 * Note: You can add to this class, but you may not alter
 * the signature of the existing methods.
 */
public class ChessGame {
    private TeamColor currentTurn;
    private ChessBoard board;

    // Constructor: initializes a new ChessGame with the default setup
    public ChessGame() {
        this.currentTurn = TeamColor.WHITE;  // White always starts
        this.board = new ChessBoard();
        board.resetBoard();
    }

    /**
     * Gets the team whose turn it is.
     *
     * @return The team whose turn it is.
     */
    public TeamColor getTeamTurn() {
        return this.currentTurn;
    }

    /**
     * Sets which team's turn it is.
     *
     * @param team The team whose turn it is.
     */
    public void setTeamTurn(TeamColor team) {
        this.currentTurn = team;
    }

    /**
     * Enum representing the two possible teams in a chess game.
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets valid moves for a piece at the given location.
     *
     * @param startPosition The position of the piece to get valid moves for.
     * @return A collection of valid moves for the piece at the startPosition, or null if no piece is at the startPosition.
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece currentPiece = board.getPiece(startPosition);
        if (currentPiece == null) {
            return null;  // No piece at the start position
        }

        Collection<ChessMove> validMoves = new ArrayList<>();
        Collection<ChessMove> possibleMoves = currentPiece.pieceMoves(board, startPosition);

        // Check each possible move to see if it puts the player in check
        for (ChessMove move : possibleMoves) {
            ChessBoard tempBoard = board.clone();
            testMove(move);  // Make the hypothetical move
            if (!isInCheck(currentPiece.getTeamColor())) {
                validMoves.add(move);
            }
            board = tempBoard;  // Restore the original board state
        }
        return validMoves;
    }

    /**
     * Makes a hypothetical move without any error checking.
     * Used for testing potential moves.
     *
     * @param move The move to test.
     */
    public void testMove(ChessMove move) {
        ChessPiece piece = board.getPiece(move.getStartPosition());
        if (piece == null) return;  // No piece to move

        if (move.getPromotionPiece() == null) {
            board.addPiece(move.getEndPosition(), piece);
        } else {
            board.addPiece(move.getEndPosition(), new ChessPiece(piece.getTeamColor(), move.getPromotionPiece()));
        }
        board.addPiece(move.getStartPosition(), null);  // Clear the start position
    }

    /**
     * Makes a move in a chess game.
     *
     * @param move The move to perform.
     * @throws InvalidMoveException If the move is invalid.
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece piece = board.getPiece(move.getStartPosition());
        if (piece == null) {
            throw new InvalidMoveException("No piece at the start position");
        }

        // Validate move positions
        if (!isValidPosition(move.getStartPosition()) || !isValidPosition(move.getEndPosition())) {
            throw new InvalidMoveException("Invalid move positions");
        }

        // Validate turn
        if (piece.getTeamColor() != getTeamTurn()) {
            throw new InvalidMoveException("Not your turn!");
        }

        // Validate the move
        if (!validMoves(move.getStartPosition()).contains(move)) {
            throw new InvalidMoveException("Invalid move");
        }

        // Make the move
        if (move.getPromotionPiece() == null) {
            board.addPiece(move.getEndPosition(), piece);
        } else {
            board.addPiece(move.getEndPosition(), new ChessPiece(piece.getTeamColor(), move.getPromotionPiece()));
        }
        board.addPiece(move.getStartPosition(), null);  // Clear the start position

        // Change team turn
        switchTurn();
    }

    // Helper method to check if a position is valid on the chessboard
    private boolean isValidPosition(ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        return row > 0 && row <= 8 && col > 0 && col <= 8;
    }

    // Helper method to switch the turn to the other team
    private void switchTurn() {
        if (currentTurn == TeamColor.WHITE) {
            setTeamTurn(TeamColor.BLACK);
        } else {
            setTeamTurn(TeamColor.WHITE);
        }
    }

    /**
     * Determines if the given team is in check.
     *
     * @param teamColor The team to check for being in check.
     * @return True if the specified team is in check, otherwise false.
     */
    public boolean isInCheck(TeamColor teamColor) {
        TeamColor opponentColor = (teamColor == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;
        ChessPosition kingPosition = findKing(teamColor);
        if (kingPosition == null) {
            return false;  // King not found
        }

        // Check if any opponent piece can move to the king's position
        for (ChessPosition pos : getTeamPositions(opponentColor)) {
            ChessPiece piece = board.getPiece(pos);
            for (ChessMove move : piece.pieceMoves(board, pos)) {
                if (move.getEndPosition().equals(kingPosition)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets all positions occupied by the specified team's pieces.
     *
     * @param color The team color to get positions for.
     * @return A collection of positions occupied by the team's pieces.
     */
    public Collection<ChessPosition> getTeamPositions(TeamColor color) {
        Collection<ChessPosition> positions = new ArrayList<>();
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(position);
                if (piece != null && piece.getTeamColor() == color) {
                    positions.add(position);
                }
            }
        }
        return positions;
    }

    /**
     * Finds the position of the king for the specified team.
     *
     * @param color The team color to find the king for.
     * @return The position of the king, or null if the king is not found.
     */
    public ChessPosition findKing(TeamColor color) {
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(position);
                if (piece != null && piece.getTeamColor() == color && piece.getPieceType() == ChessPiece.PieceType.KING) {
                    return position;
                }
            }
        }
        return null;
    }

    /**
     * Determines if the given team is in checkmate.
     *
     * @param teamColor The team to check for checkmate.
     * @return True if the specified team is in checkmate, otherwise false.
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (!isInCheck(teamColor)) {
            return false;  // Not in check
        }
        // Check if there are any valid moves to get out of check
        for (ChessPosition pos : getTeamPositions(teamColor)) {
            if (!validMoves(pos).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines if the given team is in stalemate.
     * Stalemate is defined as having no valid moves while not being in check.
     *
     * @param teamColor The team to check for stalemate.
     * @return True if the specified team is in stalemate, otherwise false.
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (isInCheck(teamColor)) {
            return false;  // Cannot be in stalemate if in check
        }
        // Check if there are any valid moves
        for (ChessPosition pos : getTeamPositions(teamColor)) {
            if (!validMoves(pos).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Sets the chessboard for this game.
     *
     * @param board The new board to use.
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard.
     *
     * @return The current chessboard.
     */
    public ChessBoard getBoard() {
        return this.board;
    }

    /**
     * Returns a JSON representation of the ChessGame.
     *
     * @return The JSON representation of the ChessGame.
     */
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
