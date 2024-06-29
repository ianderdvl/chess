package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece.
 * <p>
 * Note: You can add to this class, but you may not alter
 * the signature of the existing methods.
 */
public class ChessPiece implements Cloneable {

    private final ChessGame.TeamColor pieceColor;  // The color of the piece (WHITE or BLACK)
    private final ChessPiece.PieceType type;  // The type of the piece (KING, QUEEN, BISHOP, KNIGHT, ROOK, PAWN)

    /**
     * Constructor for creating a ChessPiece.
     *
     * @param pieceColor The color of the piece.
     * @param type       The type of the piece.
     */
    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * Enum representing the various types of chess pieces.
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * Gets the team color of this chess piece.
     *
     * @return The team color (WHITE or BLACK).
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * Gets the type of this chess piece.
     *
     * @return The type of the piece (KING, QUEEN, BISHOP, KNIGHT, ROOK, PAWN).
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the valid moves for this chess piece.
     * Does not consider moves that would leave the king in check.
     *
     * @param board      The chessboard on which the piece is located.
     * @param myPosition The current position of the piece.
     * @return A collection of valid moves.
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new ArrayList<>();
        ChessPiece currentPiece = board.getPiece(myPosition);

        if (currentPiece != null) {
            switch (currentPiece.getPieceType()) {
                case PAWN:
                    validMoves.addAll(getPawnMoves(board, myPosition, currentPiece.getTeamColor()));
                    break;
                case BISHOP:
                    validMoves.addAll(getBishopMoves(board, myPosition, currentPiece.getTeamColor()));
                    break;
                case ROOK:
                    validMoves.addAll(getRookMoves(board, myPosition, currentPiece.getTeamColor()));
                    break;
                case QUEEN:
                    validMoves.addAll(getQueenMoves(board, myPosition, currentPiece.getTeamColor()));
                    break;
                case KNIGHT:
                    validMoves.addAll(getKnightMoves(board, myPosition, currentPiece.getTeamColor()));
                    break;
                case KING:
                    validMoves.addAll(getKingMoves(board, myPosition, currentPiece.getTeamColor()));
                    break;
            }
        }

        return validMoves;
    }

    /**
     * Calculates all the valid moves for a pawn.
     *
     * @param board      The chessboard on which the pawn is located.
     * @param position   The current position of the pawn.
     * @param teamColor  The color of the pawn.
     * @return A collection of valid pawn moves.
     */
    private Collection<ChessMove> getPawnMoves(ChessBoard board, ChessPosition position, ChessGame.TeamColor teamColor) {
        Collection<ChessMove> pawnMoves = new ArrayList<>();
        int forwardRow = (teamColor == ChessGame.TeamColor.WHITE) ? position.getRow() + 1 : position.getRow() - 1;
        int currentCol = position.getColumn();

        // Diagonal capture moves
        if (isValidPosition(forwardRow, currentCol + 1)) {
            ChessPosition newPosition = new ChessPosition(forwardRow, currentCol + 1);
            if (board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() != teamColor) {
                addPawnMove(pawnMoves, position, newPosition, forwardRow);
            }
        }
        if (isValidPosition(forwardRow, currentCol - 1)) {
            ChessPosition newPosition = new ChessPosition(forwardRow, currentCol - 1);
            if (board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() != teamColor) {
                addPawnMove(pawnMoves, position, newPosition, forwardRow);
            }
        }

        // Forward move
        if (isValidPosition(forwardRow, currentCol) && board.getPiece(new ChessPosition(forwardRow, currentCol)) == null) {
            addPawnMove(pawnMoves, position, new ChessPosition(forwardRow, currentCol), forwardRow);
        }

        // Double forward move for first move
        if (teamColor == ChessGame.TeamColor.WHITE && position.getRow() == 2 && isValidPosition(forwardRow + 1, currentCol) &&
                board.getPiece(new ChessPosition(forwardRow + 1, currentCol)) == null && board.getPiece(new ChessPosition(forwardRow, currentCol)) == null) {
            pawnMoves.add(new ChessMove(position, new ChessPosition(forwardRow + 1, currentCol), null));
        }
        if (teamColor == ChessGame.TeamColor.BLACK && position.getRow() == 7 && isValidPosition(forwardRow - 1, currentCol) &&
                board.getPiece(new ChessPosition(forwardRow - 1, currentCol)) == null && board.getPiece(new ChessPosition(forwardRow, currentCol)) == null) {
            pawnMoves.add(new ChessMove(position, new ChessPosition(forwardRow - 1, currentCol), null));
        }

        return pawnMoves;
    }

    /**
     * Adds a pawn move to the collection, including promotion if necessary.
     *
     * @param moves      The collection of moves to add to.
     * @param startPos   The starting position of the move.
     * @param endPos     The ending position of the move.
     * @param forwardRow The row number the pawn moves to.
     */
    private void addPawnMove(Collection<ChessMove> moves, ChessPosition startPos, ChessPosition endPos, int forwardRow) {
        if (forwardRow == 1 || forwardRow == 8) {
            moves.add(new ChessMove(startPos, endPos, PieceType.QUEEN));
            moves.add(new ChessMove(startPos, endPos, PieceType.BISHOP));
            moves.add(new ChessMove(startPos, endPos, PieceType.KNIGHT));
            moves.add(new ChessMove(startPos, endPos, PieceType.ROOK));
        } else {
            moves.add(new ChessMove(startPos, endPos, null));
        }
    }

    /**
     * Calculates all the valid moves for a bishop.
     *
     * @param board      The chessboard on which the bishop is located.
     * @param position   The current position of the bishop.
     * @param teamColor  The color of the bishop.
     * @return A collection of valid bishop moves.
     */
    private Collection<ChessMove> getBishopMoves(ChessBoard board, ChessPosition position, ChessGame.TeamColor teamColor) {
        Collection<ChessMove> bishopMoves = new ArrayList<>();
        bishopMoves.addAll(getMovesLoop(board, position, teamColor, 1, 1));
        bishopMoves.addAll(getMovesLoop(board, position, teamColor, 1, 2));
        bishopMoves.addAll(getMovesLoop(board, position, teamColor, 2, 2));
        bishopMoves.addAll(getMovesLoop(board, position, teamColor, 2, 1));
        return bishopMoves;
    }

    /**
     * Calculates all the valid moves for a rook.
     *
     * @param board      The chessboard on which the rook is located.
     * @param position   The current position of the rook.
     * @param teamColor  The color of the rook.
     * @return A collection of valid rook moves.
     */
    private Collection<ChessMove> getRookMoves(ChessBoard board, ChessPosition position, ChessGame.TeamColor teamColor) {
        Collection<ChessMove> rookMoves = new ArrayList<>();
        rookMoves.addAll(getMovesLoop(board, position, teamColor, 1, 0));
        rookMoves.addAll(getMovesLoop(board, position, teamColor, 0, 1));
        rookMoves.addAll(getMovesLoop(board, position, teamColor, 2, 0));
        rookMoves.addAll(getMovesLoop(board, position, teamColor, 0, 2));
        return rookMoves;
    }

    /**
     * Calculates all the valid moves for a queen.
     *
     * @param board      The chessboard on which the queen is located.
     * @param position   The current position of the queen.
     * @param teamColor  The color of the queen.
     * @return A collection of valid queen moves.
     */
    private Collection<ChessMove> getQueenMoves(ChessBoard board, ChessPosition position, ChessGame.TeamColor teamColor) {
        Collection<ChessMove> queenMoves = new ArrayList<>();
        queenMoves.addAll(getRookMoves(board, position, teamColor));
        queenMoves.addAll(getBishopMoves(board, position, teamColor));
        return queenMoves;
    }

    /**
     * Calculates all the valid moves for a knight.
     *
     * @param board      The chessboard on which the knight is located.
     * @param position   The current position of the knight.
     * @param teamColor  The color of the knight.
     * @return A collection of valid knight moves.
     */
    private Collection<ChessMove> getKnightMoves(ChessBoard board, ChessPosition position, ChessGame.TeamColor teamColor) {
        Collection<ChessMove> knightMoves = new ArrayList<>();
        Collection<ChessPosition> possibleMoves = new ArrayList<>();
        possibleMoves.add(new ChessPosition(position.getRow() + 2, position.getColumn() + 1));
        possibleMoves.add(new ChessPosition(position.getRow() + 2, position.getColumn() - 1));
        possibleMoves.add(new ChessPosition(position.getRow() - 2, position.getColumn() + 1));
        possibleMoves.add(new ChessPosition(position.getRow() - 2, position.getColumn() - 1));
        possibleMoves.add(new ChessPosition(position.getRow() + 1, position.getColumn() + 2));
        possibleMoves.add(new ChessPosition(position.getRow() + 1, position.getColumn() - 2));
        possibleMoves.add(new ChessPosition(position.getRow() - 1, position.getColumn() + 2));
        possibleMoves.add(new ChessPosition(position.getRow() - 1, position.getColumn() - 2));

        for (ChessPosition pos : possibleMoves) {
            if (isValidPosition(pos.getRow(), pos.getColumn())) {
                if (board.getPiece(pos) == null || board.getPiece(pos).getTeamColor() != teamColor) {
                    knightMoves.add(new ChessMove(position, pos, null));
                }
            }
        }
        return knightMoves;
    }

    /**
     * Calculates all the valid moves for a king.
     *
     * @param board      The chessboard on which the king is located.
     * @param position   The current position of the king.
     * @param teamColor  The color of the king.
     * @return A collection of valid king moves.
     */
    private Collection<ChessMove> getKingMoves(ChessBoard board, ChessPosition position, ChessGame.TeamColor teamColor) {
        Collection<ChessMove> kingMoves = new ArrayList<>();
        Collection<ChessPosition> possibleMoves = new ArrayList<>();
        possibleMoves.add(new ChessPosition(position.getRow(), position.getColumn() + 1));
        possibleMoves.add(new ChessPosition(position.getRow(), position.getColumn() - 1));
        possibleMoves.add(new ChessPosition(position.getRow() - 1, position.getColumn()));
        possibleMoves.add(new ChessPosition(position.getRow() + 1, position.getColumn()));
        possibleMoves.add(new ChessPosition(position.getRow() + 1, position.getColumn() + 1));
        possibleMoves.add(new ChessPosition(position.getRow() + 1, position.getColumn() - 1));
        possibleMoves.add(new ChessPosition(position.getRow() - 1, position.getColumn() + 1));
        possibleMoves.add(new ChessPosition(position.getRow() - 1, position.getColumn() - 1));

        for (ChessPosition pos : possibleMoves) {
            if (isValidPosition(pos.getRow(), pos.getColumn())) {
                if (board.getPiece(pos) == null || board.getPiece(pos).getTeamColor() != teamColor) {
                    kingMoves.add(new ChessMove(position, pos, null));
                }
            }
        }
        return kingMoves;
    }

    /**
     * Checks if a position is valid on the chessboard.
     *
     * @param row The row number.
     * @param col The column number.
     * @return True if the position is valid, otherwise false.
     */
    private boolean isValidPosition(int row, int col) {
        return row >= 1 && row <= 8 && col >= 1 && col <= 8;
    }

    /**
     * Calculates all valid moves for a piece in a given direction until blocked.
     *
     * @param board     The chessboard on which the piece is located.
     * @param position  The current position of the piece.
     * @param teamColor The color of the piece.
     * @param rowOp     The row operation (0: no change, 1: add, 2: subtract).
     * @param colOp     The column operation (0: no change, 1: add, 2: subtract).
     * @return A collection of valid moves in the specified direction.
     */
    private Collection<ChessMove> getMovesLoop(ChessBoard board, ChessPosition position, ChessGame.TeamColor teamColor, int rowOp, int colOp) {
        Collection<ChessMove> moves = new ArrayList<>();
        int currentRow = position.getRow();
        int currentCol = position.getColumn();

        while (true) {
            if (rowOp == 1) currentRow++;
            if (rowOp == 2) currentRow--;
            if (colOp == 1) currentCol++;
            if (colOp == 2) currentCol--;

            if (!isValidPosition(currentRow, currentCol)) break;

            ChessPosition newPosition = new ChessPosition(currentRow, currentCol);
            ChessPiece pieceAtNewPosition = board.getPiece(newPosition);

            if (pieceAtNewPosition != null) {
                if (pieceAtNewPosition.getTeamColor() != teamColor) {
                    moves.add(new ChessMove(position, newPosition, null));
                }
                break;
            } else {
                moves.add(new ChessMove(position, newPosition, null));
            }
        }

        return moves;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ChessPiece other = (ChessPiece) obj;
        return pieceColor == other.pieceColor && type == other.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    public ChessPiece clone() {
        try {
            return (ChessPiece) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }
}
