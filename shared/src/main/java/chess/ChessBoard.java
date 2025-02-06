package chess;

import java.util.Arrays;

public class ChessBoard implements Cloneable
{

    // 2D array to represent the chessboard squares
    private ChessPiece[][] boardSquares = new ChessPiece[8][8];

    // Constructor to initialize the chessboard
    public ChessBoard()
    {

        initializeBoard(); // Set up the board upon initialization
    }

    // Method to initialize the board with null pieces
    private void initializeBoard()
    {

        for (ChessPiece[] row : boardSquares)
        {

            Arrays.fill(row, null); // Fill each row with null
        }
    }

    // Method to add a piece to the board at a specific position
    public void addPiece(ChessPosition position, ChessPiece piece)
    {

        int rowIdx = position.getRow() - 1; // Convert to 0-based index
        int colIdx = position.getColumn() - 1; // Convert to 0-based index
        boardSquares[rowIdx][colIdx] = piece; // Place the piece on the board
    }

    // Method to get a piece from a specific position on the board
    public ChessPiece getPiece(ChessPosition position)
    {

        int rowIdx = position.getRow() - 1; // Convert to 0-based index
        int colIdx = position.getColumn() - 1; // Convert to 0-based index
        return boardSquares[rowIdx][colIdx]; // Return the piece at the position
    }

    // Method to reset the board to the initial setup
    public void resetBoard()
    {

        initializeBoard();
        placeWhitePieces();
        placeBlackPieces();
    }

    // Method to place white pieces on the board
    private void placeWhitePieces()
    {

        placeMajorPieces(0, ChessGame.TeamColor.WHITE);
        placePawns(1, ChessGame.TeamColor.WHITE);
    }

    // Method to place black pieces on the board
    private void placeBlackPieces()
    {

        placeMajorPieces(7, ChessGame.TeamColor.BLACK);
        placePawns(6, ChessGame.TeamColor.BLACK);
    }

    // Method to place major pieces (rook, knight, bishop, queen, king) on the board
    private void placeMajorPieces(int row, ChessGame.TeamColor color)
    {

        boardSquares[row][0] = new ChessPiece(color, ChessPiece.PieceType.ROOK);
        boardSquares[row][7] = new ChessPiece(color, ChessPiece.PieceType.ROOK);
        boardSquares[row][1] = new ChessPiece(color, ChessPiece.PieceType.KNIGHT);
        boardSquares[row][6] = new ChessPiece(color, ChessPiece.PieceType.KNIGHT);
        boardSquares[row][2] = new ChessPiece(color, ChessPiece.PieceType.BISHOP);
        boardSquares[row][5] = new ChessPiece(color, ChessPiece.PieceType.BISHOP);
        boardSquares[row][3] = new ChessPiece(color, ChessPiece.PieceType.QUEEN);
        boardSquares[row][4] = new ChessPiece(color, ChessPiece.PieceType.KING);
    }

    // Method to place pawns on the board
    private void placePawns(int row, ChessGame.TeamColor color)
    {

        for (int col = 0; col < 8; col++)
        {

            boardSquares[row][col] = new ChessPiece(color, ChessPiece.PieceType.PAWN);
        }
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
        ChessBoard otherBoard = (ChessBoard) obj;
        return Arrays.deepEquals(boardSquares, otherBoard.boardSquares);
    }

    @Override
    public int hashCode()
    {

        return Arrays.deepHashCode(boardSquares);
    }

    @Override
    public ChessBoard clone()
    {

        try
        {

            ChessBoard clonedBoard = (ChessBoard) super.clone();
            clonedBoard.boardSquares = new ChessPiece[8][8];
            for (int i = 0; i < boardSquares.length; i++)
            {

                for (int j = 0; j < boardSquares[i].length; j++)
                {

                    if (boardSquares[i][j] != null)
                    {

                        ChessPiece originalPiece = boardSquares[i][j];
                        clonedBoard.boardSquares[i][j] = new ChessPiece(originalPiece.teamColor(), originalPiece.pieceType());
                    }
                }
            }
            return clonedBoard;
        }
        catch (CloneNotSupportedException e)
        {

            throw new AssertionError();
        }
    }

    @Override
    public String toString()
    {

        return "ChessBoard{" + "boardSquares=" + Arrays.deepToString(boardSquares) + '}';
    }
}