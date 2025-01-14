package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */

public class ChessPosition
{

    // Row on the chessboard
    private final int rank;

    // Column on the chessboard
    private final int file;

    // Constructor to initialize a chess position
    public ChessPosition(int rank, int file)
    {
        this.rank = rank; // Initialize the rank
        this.file = file; // Initialize the file
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */

    public int getRow()
    {
        return rank;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */

    public int getColumn()
    {
        return file;
    }

    @Override
    public String toString()
    {
        return "[" + rank + "][" + file + "]"; // Return string representation of the position
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
        ChessPosition other = (ChessPosition) obj;
        return rank == other.rank && file == other.file; // Compare the rank and file
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(rank, file); // Generate hash code based on rank and file
    }
}
