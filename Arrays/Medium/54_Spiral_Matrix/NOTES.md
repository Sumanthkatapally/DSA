**LeetCode 54 — Spiral Matrix**.

# Problem statement

**Given** an `n × m` matrix `matrix`, **return** all elements of the matrix in **spiral order** (clockwise), starting from the top-left corner.

**Example**

```
Input:
[
 [1, 2, 3],
 [4, 5, 6],
 [7, 8, 9]
]

Output: [1, 2, 3, 6, 9, 8, 7, 4, 5]
```

# Key ideas

There are two canonical ways to solve this:

1. **Boundary (four-pointer) method** — optimal
   Maintain `top`, `bottom`, `left`, `right` as the current outer “ring” you’re traversing. After you traverse an edge, shrink the boundary inward. This is **O(n·m)** time with **O(1)** extra space (besides the output).

2. **Direction vector + visited** — simpler to reason about, handles all shapes uniformly
   Walk the grid with a direction array (right, down, left, up). When you hit a boundary or a visited cell, turn right. Needs a `visited` matrix → **O(n·m)** time and **O(n·m)** space.

Most interviewers expect the **boundary method**.

---

# Boundary (four-pointer) method

## Intuition

On each “layer”:

1. Traverse **top row** left → right.
2. Traverse **right column** top → bottom.
3. Traverse **bottom row** right → left (**only if** there’s still a distinct bottom row).
4. Traverse **left column** bottom → top (**only if** there’s still a distinct left column).

Then move inward: `top++`, `right--`, `bottom--`, `left++`. Stop when the boundaries cross.

## Why we guard steps 3 & 4

After steps 1 and 2, the remaining submatrix might have collapsed to:

* a **single row** (`top > bottom` after incrementing `top`), or
* a **single column** (`left > right` after decrementing `right`).

So:

* Check `if (top <= bottom)` before traversing the bottom row.
* Check `if (left <= right)` before traversing the left column.

These prevent **double-visiting** elements when a single row/column remains.

## Pseudocode

```
top = 0, bottom = n-1
left = 0, right = m-1
res = []

while top <= bottom and left <= right:
    // top row
    for j from left to right: res.add(matrix[top][j])
    top++

    // right column
    for i from top to bottom: res.add(matrix[i][right])
    right--

    // bottom row (if valid)
    if top <= bottom:
        for j from right down to left: res.add(matrix[bottom][j])
        bottom--

    // left column (if valid)
    if left <= right:
        for i from bottom down to top: res.add(matrix[i][left])
        left++

return res
```

## Correctness sketch (loop invariant)

At the start of each loop:

* All elements **outside** the rectangle `top..bottom × left..right` have been added in spiral order.
* The next four traversals will cover the perimeter of this rectangle without overlap (thanks to the guards), shrinking the rectangle.
* Termination when `top > bottom` or `left > right` implies every element has been visited exactly once.

## Complexity

* **Time:** `O(n·m)` — each element is added once.
* **Space:** `O(1)` extra (excluding the output list).

## Java implementation (clean + robust)

```java
import java.util.*;

class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return Collections.emptyList();

        int n = matrix.length, m = matrix[0].length;
        List<Integer> res = new ArrayList<>(n * m);

        int top = 0, bottom = n - 1, left = 0, right = m - 1;

        while (top <= bottom && left <= right) {
            // 1) top row
            for (int j = left; j <= right; j++) res.add(matrix[top][j]);
            top++;

            // 2) right column
            for (int i = top; i <= bottom; i++) res.add(matrix[i][right]);
            right--;

            // 3) bottom row (if still valid)
            if (top <= bottom) {
                for (int j = right; j >= left; j--) res.add(matrix[bottom][j]);
                bottom--;
            }

            // 4) left column (if still valid)
            if (left <= right) {
                for (int i = bottom; i >= top; i--) res.add(matrix[i][left]);
                left++;
            }
        }

        return res;
    }
}
```

## Dry run (non-square example)

```
matrix =
1  2  3  4
5  6  7  8
9 10 11 12
```

* top row → `1 2 3 4`
* right col → `8 12`
* bottom row (valid) → `11 10 9`
* left col (valid) → `5`
  Next layer:
* top row → `6 7`
* right col → (none, already consumed)
* bottom row (valid) → (none, same row)
* left col (valid) → (none)
  Result: `[1,2,3,4,8,12,11,10,9,5,6,7]`

---

# Direction vector + visited (alternative)

## Idea

Walk the grid with directions: right `(0,1)`, down `(1,0)`, left `(0,-1)`, up `(-1,0)`. Keep a `visited[n][m]`. If the next cell is out of bounds or visited, rotate to the next direction.

## Code (Java)

```java
import java.util.*;

class SolutionAlt {
    public List<Integer> spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return Collections.emptyList();
        int n = matrix.length, m = matrix[0].length;
        boolean[][] seen = new boolean[n][m];
        int[] dr = {0, 1, 0, -1}; // right, down, left, up
        int[] dc = {1, 0, -1, 0};

        List<Integer> res = new ArrayList<>(n * m);
        int r = 0, c = 0, dir = 0;

        for (int k = 0; k < n * m; k++) {
            res.add(matrix[r][c]);
            seen[r][c] = true;
            int nr = r + dr[dir], nc = c + dc[dir];
            if (nr < 0 || nr >= n || nc < 0 || nc >= m || seen[nr][nc]) {
                dir = (dir + 1) % 4; // turn right
                nr = r + dr[dir];
                nc = c + dc[dir];
            }
            r = nr; c = nc;
        }
        return res;
    }
}
```

* **Time:** `O(n·m)`
* **Space:** `O(n·m)` (visited)

---

# Edge cases to test

* Empty matrix `[]` → `[]`
* Single element `[[x]]` → `[x]`
* Single row `[[1,2,3]]` → `[1,2,3]`
* Single column `[[1],[2],[3]]` → `[1,2,3]`
* Rectangular `2×3`, `3×2` (like the dry run above)
* Matrices with duplicates/zeros (logic should not assume distinctness)

---

# Common pitfalls

* Forgetting to **guard** steps 3 and 4 → duplicates when only one row/column remains.
* Changing `left/right/top/bottom` **too early** (must adjust after finishing each edge).
* Off-by-one mistakes in loop ranges (`<=` vs `<`).
* Missing early return for empty input.

---

# Variants you might be asked next

* **Spiral Matrix II (LC 59):** generate an `n×n` matrix filled 1..n² in spiral order (use boundary method to fill).
* **Spiral Matrix III (LC 885):** spiral from an arbitrary start in an `R×C` grid; use direction vectors and step counts.

---

# TL;DR

* Use **four boundaries**; traverse edges in order; **shrink** inward.
* Add guards for the **bottom row** and **left column**.
* Time `O(n·m)`, extra space `O(1)` (excluding output).


Link: https://leetcode.com/problems/spiral-matrix/description/