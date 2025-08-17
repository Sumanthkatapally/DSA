solution is the classic optimal approach for LeetCode 73 using the first row and first column as in-place markers. It runs in **O(n·m)** time and **O(1)** extra space.

Here are detailed notes for interviews:

# What the problem wants

Given an `n × m` matrix, if any cell is `0`, set its entire **row** and **column** to `0`. Do it **in place** with **constant extra space**.

# Core idea (marker trick)

* Use the **first row** and **first column** as marker arrays.
* On the first pass, when you see a `0` at `(i, j)`:

  * Set `matrix[i][0] = 0` to mark the row.
  * Set `matrix[0][j] = 0` to mark the column.
* But there’s ambiguity at `matrix[0][0]` (shared by first row & first column). So we keep a separate flag for whether the **first column** should be zeroed.
  In your code, that’s `col0` (initialized to `-1`, set to `0` if any zero appears in column 0).

# Why your control flow is correct

1. **First sweep (top-left → bottom-right)**

   * Record which rows/cols need zeroing by writing to `matrix[i][0]` and `matrix[0][j]`.
   * Special handling: if `j == 0`, set `col0 = 0` so we remember to zero the first column later.

2. **Second sweep (exclude first row/col)**

   * For each `(i, j)` with `i ≥ 1` and `j ≥ 1`, set to zero if **its row OR column marker is zero**:
     `if (matrix[i][0] == 0 || matrix[0][j] == 0) -> matrix[i][j] = 0`

3. **Finalize first row & first column**

   * If `matrix[0][0] == 0`, zero the **entire first row**.
   * If `col0 == 0`, zero the **entire first column**.

This ordering prevents you from destroying marker information before you use it.

# Time & space complexity

* **Time:** `O(n·m)` — each cell is touched a constant number of times.
* **Extra space:** `O(1)` — only scalar flags beyond the input matrix.

# Edge cases your code handles

* **No zeros at all** → nothing changes.
* **All zeros** → markers set, everything remains zero.
* **Single row or single column** → flags ensure correct finalization.
* **Zeros in (0, j) or (i, 0)** → first row/col get properly zeroed using `matrix[0][0]` and `col0`.

# Small dry-run

Input:

```
[ [1, 2, 0, 4],
  [5, 6, 7, 8],
  [0,10,11,12] ]
```

First pass marks:

* `(0,2)=0` ⇒ mark row 0 and col 2 → `matrix[0][2]=0` (already 0)
* `(2,0)=0` ⇒ mark row 2 and col 0 → `matrix[2][0]=0`, `col0=0`

Matrix after marking (only markers guaranteed correct; other entries unchanged is fine):

```
[ [1, 2, 0, 4],
  [5, 6, 7, 8],
  [0,10,11,12] ]
```

Second pass (i≥1, j≥1):

* Any cell with `matrix[i][0]==0` (row 2) or `matrix[0][j]==0` (col 2) becomes 0.

Result before first row/col finalization:

```
[ [1, 2, 0, 4],
  [5, 6, 0, 8],
  [0, 0, 0, 0] ]
```

Finalize:

* `matrix[0][0] != 0` → first row not auto-zeroed by this rule
* `col0==0` → zero first column

Final:

```
[ [0, 2, 0, 4],
  [0, 6, 0, 8],
  [0, 0, 0, 0] ]
```

# Minor notes / optional polish

* Your `matrix[i][j] != 0` guard in the second pass isn’t necessary; writing `0` again is harmless. Removing it slightly simplifies code.
* Use a boolean instead of `int col0 = -1` for clarity.

# Cleaned-up version (same logic)

```java
class Solution {
    public void setZeroes(int[][] matrix) {
        int n = matrix.length, m = matrix[0].length;
        boolean firstColZero = false;

        // 1) Mark rows and columns
        for (int i = 0; i < n; i++) {
            if (matrix[i][0] == 0) firstColZero = true;
            for (int j = 1; j < m; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        // 2) Apply marks to inner submatrix
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        // 3) First row
        if (matrix[0][0] == 0) {
            for (int j = 0; j < m; j++) matrix[0][j] = 0;
        }

        // 4) First column
        if (firstColZero) {
            for (int i = 0; i < n; i++) matrix[i][0] = 0;
        }
    }
}
```

# Alternatives (for context)

* **Extra arrays/sets:** Keep `rowsToZero` and `colsToZero` sets/arrays → simpler to reason about but **O(n+m)** space.
* **BFS/DFS from zeros:** Overkill and not in place; risks repeated work.

# Common pitfalls (you avoided them)

* Zeroing the first row/column too early (destroys markers).
* Forgetting to distinguish first row vs first column (using only `matrix[0][0]` without an extra flag).
* Iterating the second pass in the wrong range (must skip first row/col until the end).

Link: https://leetcode.com/problems/set-matrix-zeroes/
