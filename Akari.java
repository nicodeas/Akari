
import java.util.Scanner;

public class Akari {
    private String filename; // the name of the puzzle file
    private int size; // the board is size x size
    private Space[][] board; // the board is a square grid of spaces of various types

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Hello! Welcome to Akari! Please enter puzzle number");
        int n = scan.nextInt();
        System.out.println("Enjoy!");
        scan.close();
        long start = System.currentTimeMillis();
        new AkariViewer(n);
        long end = System.currentTimeMillis();
        System.out.println("time taken = " + ((double) (end - start) / 1000));
    }

    /**
     * Constructor for objects of class Akari. Creates and initialises the fields
     * with the contents of filename. The layout of a puzzle file is described on
     * the LMS page; you may assume the layout is always correct.
     */
    public Akari(String filename) {
        this.filename = filename;
        FileIO fio = new FileIO(filename);

        size = Integer.parseInt(fio.getLines().get(0));
        board = new Space[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                board[i][j] = Space.EMPTY;

            }
        }

        String black = fio.getLines().get(1);
        for (String s : breakUp(black)) {
            setSpace(s, Space.BLACK);
        }

        String zero = fio.getLines().get(2);
        for (String s : breakUp(zero)) {
            setSpace(s, Space.ZERO);
        }

        String one = fio.getLines().get(3);
        for (String s : breakUp(one)) {
            setSpace(s, Space.ONE);
        }

        String two = fio.getLines().get(4);
        for (String s : breakUp(two)) {
            setSpace(s, Space.TWO);
        }

        String three = fio.getLines().get(5);
        for (String s : breakUp(three)) {
            setSpace(s, Space.THREE);
        }

        String four = fio.getLines().get(6);
        for (String s : breakUp(four)) {
            setSpace(s, Space.FOUR);
        }
        // TODO 3
    }

    /**
     * Uses the example file from the LMS page.
     */
    public Akari() {
        this("Puzzles/p7-e7.txt");
    }

    public String[] breakUp(String s) {
        String[] broken_up = s.split(" ");
        return broken_up;
    }

    public void setSpace(String s, Space sp) {
        if (!s.equals("")) {
            int z = parseIndex(s.charAt(0));

            for (int i = 1; i < s.length(); i++) {
                int x = parseIndex(s.charAt(i));
                board[z][x] = sp;
            }
        }
    }

    /**
     * Returns the name of the puzzle file.
     */
    public String getFilename() {
        // TODO 1a
        return filename;
    }

    /**
     * Returns the size of the puzzle.
     */
    public int getSize() {
        // TODO 1b
        return size;
    }

    /**
     * Returns true iff k is a legal index into the board.
     */
    public boolean isLegal(int k) {
        // TODO 5
        return (k >= 0 && k < size);
    }

    /**
     * Returns true iff r and c are both legal indices into the board.
     */
    public boolean isLegal(int r, int c) {
        // TODO 6

        return (isLegal(r) && isLegal(c));
    }

    /**
     * Returns the contents of the square at r,c if the indices are legal, o/w
     * throws an illegal argument exception.
     */
    public Space getBoard(int r, int c) {
        if (isLegal(r, c)) {
            return board[r][c];
        } else
            throw new IllegalArgumentException("Indices are not legal");
        // TODO 7
    }

    /**
     * Returns the int equivalent of x. If x is a digit, returns 0 -> 0, 1 -> 1,
     * etc; if x is an upper-case letter, returns A -> 10, B -> 11, etc; o/w throws
     * an illegal argument exception.
     */
    public static int parseIndex(char x) {
        // TODO 2
        if ((x >= '0' && x <= '9') || (x >= 'A' && x <= 'Z')) {
            int z = 0;
            if (Character.isDigit(x)) {
                z = x - '0';
            }
            if (Character.isUpperCase(x)) {
                z = x - 'A' + 10;
            }
            return z;
        } else
            throw new IllegalArgumentException("character is not legal");
    }

    /**
     * Performs a left click on the square at r,c if the indices are legal, o/w does
     * nothing. If r,c is empty, a bulb is placed; if it has a bulb, that bulb is
     * removed.
     */
    public void leftClick(int r, int c) {
        if (isLegal(r, c)) {
            if (Space.isMutable(board[r][c])) {
                if (board[r][c] == Space.EMPTY)
                    board[r][c] = Space.BULB;
                else
                    board[r][c] = Space.EMPTY;
            }
            // TODO 8
        }
    }

    /**
     * Sets all mutable squares on the board to empty.
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (Space.isMutable(board[i][j])) {
                    board[i][j] = Space.EMPTY;
                }
            }
        }
        // TODO 4
    }

    /**
     * Returns the number of bulbs adjacent to the square at r,c. Throws an illegal
     * argument exception if r,c are illegal coordinates.
     */
    public int numberOfBulbs(int r, int c) {
        if (isLegal(r, c)) {
            int n = 0;

            if (isLegal(r + 1, c) && board[r + 1][c] == Space.BULB)
                n++;
            if (isLegal(r - 1, c) && board[r - 1][c] == Space.BULB)
                n++;
            if (isLegal(r, c + 1) && board[r][c + 1] == Space.BULB)
                n++;
            if (isLegal(r, c - 1) && board[r][c - 1] == Space.BULB)
                n++;
            // TODO 14
            return n;
        } else
            throw new IllegalArgumentException("indices are not legal");
    }

    /**
     * Returns true iff the square at r,c is lit by a bulb elsewhere. Throws an
     * illegal argument exception if r,c are illegal coordinates.
     */
    public boolean canSeeBulb(int r, int c) {
        if (isLegal(r, c)) {
            boolean b = false;
            for (int i = 1; i < size - c; i++) {
                if (board[r][c + i] == Space.BULB)
                    b = true;
                if (!Space.isMutable(board[r][c + i]))
                    break;
            }

            for (int i = 1; i <= c; i++) {
                if (board[r][c - i] == Space.BULB)
                    b = true;
                if (!Space.isMutable(board[r][c - i]))
                    break;
            }

            for (int i = 1; i < size - r; i++) {
                if (board[r + i][c] == Space.BULB)
                    b = true;
                if (!Space.isMutable(board[r + i][c]))
                    break;
            }

            for (int i = 1; i <= r; i++) {
                if (board[r - i][c] == Space.BULB)
                    b = true;
                if (!Space.isMutable(board[r - i][c]))
                    break;
            }
            return b;
        } else
            throw new IllegalArgumentException("indices are not legal");
        // TODO 15
    }

    /**
     * Returns an assessment of the state of the puzzle, either "Clashing bulb at
     * r,c", "Unlit square at r,c", "Broken number at r,c", or three ticks, as on
     * the LMS page. r,c must be the coordinates of a square that has that kind of
     * error. If there are multiple errors on the board, you may return any one of
     * them.
     */
    public String isSolution() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == Space.BULB && canSeeBulb(i, j)) {
                    return "Clashing bulb at " + i + "," + j;
                }

                if (board[i][j] == Space.EMPTY && !(canSeeBulb(i, j))) {
                    return "Unlit square at " + i + "," + j;
                }

                if (board[i][j] == Space.ZERO && !(numberOfBulbs(i, j) == 0)) {
                    return "Broken number at " + i + "," + j;
                }
                if (board[i][j] == Space.ONE && !(numberOfBulbs(i, j) == 1)) {
                    return "Broken number at " + i + "," + j;
                }
                if (board[i][j] == Space.TWO && !(numberOfBulbs(i, j) == 2)) {
                    return "Broken number at " + i + "," + j;
                }
                if (board[i][j] == Space.THREE && !(numberOfBulbs(i, j) == 3)) {
                    return "Broken number at " + i + "," + j;
                }
                if (board[i][j] == Space.FOUR && !(numberOfBulbs(i, j) == 4)) {
                    return "Broken number at " + i + "," + j;
                }

            }
        }
        // TODO 16
        return "\u2713\u2713\u2713";
    }
}
