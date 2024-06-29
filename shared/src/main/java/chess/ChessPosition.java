package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board.
 * <p>
 * Note: You can add to this class, but you may not alter
 * the signature of the existing methods.
 */
public class ChessPosition {
    private final int row;  // The row number of the position (1-8)
    private final int col;  // The column number of the position (1-8)

    /**
     * Constructor for creating a ChessPosition.
     *
     * @param row The row number of the position (1-8).
     * @param col The column number of the position (1-8).
     */
    public ChessPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Gets the row number of this position.
     *
     * @return The row number (1 for the bottom row).
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column number of this position.
     *
     * @return The column number (1 for the leftmost column).
     */
    public int getColumn() {
        return col;
    }

    /**
     * Returns a string representation of the ChessPosition.
     *
     * @return A string in the format "[row][col]".
     */
    @Override
    public String toString() {
        return "[" + row + "][" + col + "]";
    }

    /**
     * Checks if this ChessPosition is equal to another object.
     *
     * @param o The object to compare with.
     * @return True if the objects are equal, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // Same instance
        if (o == null || getClass() != o.getClass()) return false;  // Null or different class
        ChessPosition that = (ChessPosition) o;
        return row == that.row && col == that.col;  // Compare row and column
    }

    /**
     * Computes the hash code for this ChessPosition.
     *
     * @return The hash code of this ChessPosition.
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
