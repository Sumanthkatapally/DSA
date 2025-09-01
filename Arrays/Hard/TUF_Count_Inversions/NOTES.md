Merge-sort solution is the standard **optimal** way to count inversions for arbitrary integers.


# What’s an inversion?

For an array `nums`, a pair `(i, j)` is an inversion if `i < j` and `nums[i] > nums[j]`.

* Sorted ascending ⇒ `0` inversions
* Sorted descending ⇒ maximum `n*(n-1)/2`

For Attached example `[3, -1, 2, -2, 0]`, the inversions are:

* `(3, -1), (3, 2), (3, -2), (3, 0), (-1, -2), (2, -2), (2, 0)` → **7** total.

# Why merge sort works (intuition)

1. **Divide:** split the array into left and right halves.
2. **Conquer:** recursively count inversions within each half.
3. **Combine (key idea):** when merging two **sorted** halves, each time you pick `right[j]` before `left[i]`, it beats **all remaining** elements in left: that’s **`(mid - i + 1)`** new inversions at once.

Thus, total inversions = inversions in left + inversions in right + cross inversions found during merge.

# Attached code: what’s good and what to tweak

✅ Correct structure:

* You now add counts from left, right, and merge:
  `count += mergeSort(left) + mergeSort(right) + merge(...)`
* You use `long` for the count (good: inversions can exceed `Integer.MAX_VALUE` for large `n`).

✅ Merge logic:

* When `nums[i] <= nums[j]`, copy left; else copy right and add `m - i + 1` to the count.

🔧 Small cleanup tips (optional):

* Use `i <= m` and `j <= h` instead of `i < m + 1` / `j < h + 1` for readability.
* Replace the `else if (nums[i] > nums[j])` with just `else` because it’s the only remaining case.
* Avoid repeated temp allocations: allocate one `temp` array once and pass it down for better constants.

# Correctness sketch (loop invariant)

During `merge(l, m, h)`:

* Left part `nums[l..m]` and right part `nums[m+1..h]` are individually sorted.
* When `nums[i] <= nums[j]`, placing `nums[i]` creates **no** new inversions (left element is small enough).
* When `nums[i] > nums[j]`, `nums[j]` is smaller than **all remaining** `nums[i..m]`, so we add `(m - i + 1)` cross inversions in one shot.
* Merging maintains sorted order and counts all cross inversions exactly once.

# Complexity

* **Time:** `O(n log n)` (optimal for comparison-based counting)
* **Extra space:** `O(n)` for the merge buffer + recursion stack `O(log n)`.
  (Reusing a single temp keeps it `O(n)`, not `O(n log n)` allocations.)

# Edge cases & pitfalls

* **Large n:** Always use `long` for the count; max inversions is `n*(n-1)/2`.
* **Duplicates/negatives:** Fully supported; use `<=` in merge to keep stability and avoid overcounting.
* **Empty/size 1:** Should return `0` (Attached base case handles it).
* **Performance:** Reusing a single `temp` array reduces GC pressure.

# Polished version with a single temp buffer

```java
public class Inversions {
    public static long numberOfInversions(int[] a) {
        if (a == null || a.length < 2) return 0L;
        int[] tmp = new int[a.length];
        return mergeSort(a, 0, a.length - 1, tmp);
    }

    private static long mergeSort(int[] a, int l, int h, int[] tmp) {
        if (l >= h) return 0L;
        int m = l + (h - l) / 2;
        long cnt = 0;
        cnt += mergeSort(a, l, m, tmp);
        cnt += mergeSort(a, m + 1, h, tmp);
        cnt += merge(a, l, m, h, tmp);
        return cnt;
    }

    private static long merge(int[] a, int l, int m, int h, int[] tmp) {
        int i = l, j = m + 1, k = 0;
        long inv = 0;

        while (i <= m && j <= h) {
            if (a[i] <= a[j]) {
                tmp[k++] = a[i++];
            } else {
                inv += (m - i + 1);  // all remaining a[i..m] > a[j]
                tmp[k++] = a[j++];
            }
        }
        while (i <= m) tmp[k++] = a[i++];
        while (j <= h) tmp[k++] = a[j++];
        System.arraycopy(tmp, 0, a, l, k);
        return inv;
    }
}
```

# Alternative optimal methods

* **Fenwick Tree (BIT) / Segment Tree**: `O(n log n)` time, `O(n)` space.
  Steps: coordinate-compress values (handles negatives), then scan from right to left: for each `x`, query how many seen values are `< x`, add to count, then update frequency for `x`.

* **Order-statistics balanced BST** (library support varies): similar idea, also `O(n log n)`.

(These don’t beat merge sort asymptotically; they’re just different tools depending on what’s convenient.)

# Quick self-check routine (for debugging)

For tiny arrays, verify against a brute-force `O(n^2)` counter to ensure Attached merge solution is correct.

---

**Bottom line:** Above approach is the canonical optimal solution: **`O(n log n)` time, `O(n)` space**. Reuse a temp buffer if you want it even cleaner and faster in practice.

Link: https://takeuforward.org/plus/dsa/problems/count-inversionsN
