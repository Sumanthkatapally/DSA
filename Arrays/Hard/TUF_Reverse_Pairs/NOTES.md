# 1) Problem in one line

Count pairs `(i, j)` with `0 ≤ i < j < n` and `nums[i] > 2 * nums[j]`.

* Example: `[6,4,1,2,7]` → **3**: `(6,1)`, `(6,2)`, `(4,1)`.
* Unlike standard inversions (`nums[i] > nums[j]`), the **factor 2** makes counting trickier.

# 2) Optimal approach (merge sort + two-pointer count)

We use a modified merge sort:

1. **Divide** into two halves.
2. **Conquer**: recursively count in left and right halves.
3. **Combine**: **before merging**, count cross pairs with a two-pointer sweep over the two **sorted** halves:

   * For each `i` in left half, advance `j` in right half while `(long)left[i] > 2L * right[j]`.
   * The number of valid `j` for this `i` is `j - (m + 1)` (if right half starts at `m+1`).
   * **Do not reset `j`** when `i` increases. Since left is sorted, `left[i]` grows, so `j` only moves forward (amortized linear time).
4. **Merge** the two halves normally to keep them sorted.

### Why not just “change the if” in a normal inversion merge?

Because counting only when you pull from the right (like classic inversions) ties counting to `a[i] > a[j]`. With reverse pairs, `a[i] > 2*a[j]` can be **true even when `a[i] ≤ a[j]`** (very common with negatives). You must do the **separate counting pass** over the sorted halves.

# 3) Correctness (sketch)

* After recursively sorting halves, for a fixed `i` the set `{ j | a[i] > 2*a[j] }` in the right half is a **prefix** (since right is sorted).
* As `i` increases, `a[i]` is non-decreasing, so the prefix for the next `i` can only **extend**; hence `j` moves monotonically right.
* We count every cross pair exactly once before merging, and we add the counts from left, right, and cross ⇒ total.

# 4) Complexity

* **Time:** `O(n log n)` (merge sort + linear two-pointer count per level)
* **Space:** `O(n)` extra (merge buffer) + recursion stack `O(log n)`
  (Reuse one temp array across merges for better constants.)

# 5) Java template (clean & fast)

```java
public class ReversePairsCounter {
    public static long reversePairs(int[] nums) {
        if (nums == null || nums.length < 2) return 0L;
        int[] tmp = new int[nums.length];              // reuse buffer
        return sortAndCount(nums, 0, nums.length - 1, tmp);
    }

    private static long sortAndCount(int[] a, int l, int h, int[] tmp) {
        if (l >= h) return 0L;
        int m = l + (h - l) / 2;
        long cnt = 0;
        cnt += sortAndCount(a, l, m, tmp);             // left
        cnt += sortAndCount(a, m + 1, h, tmp);         // right
        cnt += countCross(a, l, m, h);                 // cross count
        merge(a, l, m, h, tmp);                        // then merge
        return cnt;
    }

    // 1) Cross-pair count (two-pointer)
    private static long countCross(int[] a, int l, int m, int h) {
        long cnt = 0;
        int i = l, j = m + 1;
        while (i <= m) {
            while (j <= h && (long) a[i] > 2L * a[j]) j++;  // avoid overflow
            cnt += (j - (m + 1));                            // all right indices in [m+1, j-1]
            i++;
        }
        return cnt;
    }

    // 2) Standard merge (ascending)
    private static void merge(int[] a, int l, int m, int h, int[] tmp) {
        int i = l, j = m + 1, k = 0;
        while (i <= m && j <= h) {
            if (a[i] <= a[j]) tmp[k++] = a[i++];
            else              tmp[k++] = a[j++];
        }
        while (i <= m) tmp[k++] = a[i++];
        while (j <= h) tmp[k++] = a[j++];
        System.arraycopy(tmp, 0, a, l, k);
    }
}
```

# 6) Overflow & negatives (important!)

* Always compare as `(long)a[i] > 2L * a[j]`.
  Example overflow trap with 32-bit `int`:
  `a[i]=2147483647, a[j]=1073741824 → 2*a[j]=2147483648` (overflows to negative),
  which would falsely count the pair if you don’t cast.
* **Negatives are common pitfalls.** Reverse pairs can exist even when `a[i] ≤ a[j]`. E.g., `[-3, -2]`: `-3 > 2*(-2) = -4` (true).

# 7) Why your “single line change” fails (counter-examples)

Counting only inside the usual merge branch (`if (a[i] > a[j]) cnt += m - i + 1`) with the modified condition `a[i] > 2*a[j]` **misses**:

* `[-1, -1]` → true pair `(0,1)` since `-1 > -2` but you don’t count (because `a[i] > a[j]` is false).
* `[-3, -2]` → true pair `(0,1)` since `-3 > -4`, also missed.
* It also breaks with overflow if not using `long`.

# 8) Alternative optimal method (Fenwick/BIT with coordinate compression)

Also `O(n log n)` time, `O(n)` space. Idea: scan `j` left→right, and for each `nums[j]`, count how many prior elements `> 2*nums[j]`.

Steps:

1. Build a sorted list of unique values containing **all `nums[i]` and `2*nums[i]`** (as `long`s), map them to ranks.
2. Maintain a BIT over ranks of past `nums[i]`.
3. For each `nums[j]`:

   * Let `t = 2*nums[j]`. Query how many seen ranks are **strictly greater** than rank(`t`) → add to count.
   * Update BIT at rank(`nums[j]`) by +1.

This avoids recursion and is great when you already have BIT utilities.

# 9) Edge cases to test

* Empty / size 1 → `0`
* All equal positives: `[3,3,3]` → `0`
* All equal negatives: `[-2,-2,-2]` → every pair counts? `-2 > -4` true → `3` (choose 2)
* Mixed signs: `[0,-1]` → `0 > -2` ⇒ true → `1`
* Large ints near overflow: `[2147483647, 1073741824]` → should be `0` (only safe with `long` casting)
* Known LC test: `[2,4,3,5,1]` → `3`

# 10) Practical tips

* Reuse one temp array across merges (lower GC, faster).
* Keep the **counting pass separate** from the merge; do it **before** merging.
* Use `l + (h - l) / 2` for mid to avoid rare overflow.
* If performance matters, make methods `static` and avoid unnecessary boxing.

---

**Bottom line:** The merge-sort + two-pointer counting (with `long` comparisons) is the canonical **optimal** solution: **`O(n log n)` time, `O(n)` extra space**.

Link: https://takeuforward.org/plus/dsa/problems/reverse-pairs
