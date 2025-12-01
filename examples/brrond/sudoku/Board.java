package brrond.sudoku;

import java.util.HashSet;
import java.util.Set;

public class Board {
    int size;
    int[] arr;

    public Board(int size) {
        this.size = size;
        arr = new int[size * size];
    }

    public boolean isSolved() {
        // Iterate all rows/columns
        for (int i = 0; i < size; i++) {

            Set<Integer> uniqueInRow = new HashSet<>();
            Set<Integer> uniqueInColumn = new HashSet<>();

            // Iterate all columns/rows
            for (int j = 0; j < size; j++) {

                // Get elements from row/column
                int elementInRow = arr[i * size + j];
                int elementInColumn = arr[j * size + i];

                // Check value
                if (elementInRow < 1 || elementInRow > size || elementInColumn < 1 || elementInColumn > size) {
                    return false;
                }

                // Check uniqueness
                if (uniqueInRow.contains(elementInRow)) {
                    return false;
                }
                if (uniqueInColumn.contains(elementInColumn)) {
                    return false;
                }

                uniqueInRow.add(elementInRow);
                uniqueInColumn.add(elementInColumn);
            }

            // Check the size of uniques
            if (uniqueInRow.size() != size || uniqueInColumn.size() != size) {
                return false;
            }
        }

        // Check if it's possible to check blocks
        // 4x4
        // 9x9
        // etc.
        int blockSize = (int) Math.sqrt(size);
        if (blockSize * blockSize == size) {

            // Iterate over all blocks
            // number of blocks == blockSize
            for (int i = 0; i < blockSize; i++) {
                for (int j = 0; j < blockSize; j++) {

                    Set<Integer> uniqueInBlock = new HashSet<>();

                    // Iterate over all elements in one block
                    for (int k = 0; k < blockSize; k++) {
                        for (int l = 0; l < blockSize; l++) {

                            // Get the element
                            int element = arr[i * size * blockSize + j * blockSize + k * size + l];

                            // Check uniqueness
                            if (uniqueInBlock.contains(element)) {
                                return false;
                            }

                            uniqueInBlock.add(element);
                        }
                    }

                    // Check the size of uniques
                    if (uniqueInBlock.size() != size) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            stringBuilder.append(arr[i]).append(" ");
            if ((i + 1) % size == 0) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }
}
