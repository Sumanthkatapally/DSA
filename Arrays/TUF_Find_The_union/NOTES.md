Interview‑ready notes for **“Union of Two Sorted Arrays (unique elements)”** tailored to *your exact code*.

---

## 1. Problem Definition

Given two **sorted (non‑decreasing)** integer arrays `nums1` and `nums2`, produce a **sorted array containing each distinct value that appears in either array exactly once** (set union). Return the resulting array.

**Example**
`nums1 = [1,2,4]`, `nums2 = [2,2,3,5]` → Result: `[1,2,3,4,5]`.

---

## 2. Core Idea (Merge + On‑the‑Fly Dedup)

Use **two pointers** (`i` for `nums1`, `j` for `nums2`) similar to the *merge* step of merge sort. At each step, take the smaller element; if equal, advance both pointers but add only one copy. Prevent duplicates by checking the last inserted element in the result.

---

## 3. Algorithm Walkthrough (Your Variables)

* `i`, `j`: read positions in `nums1`, `nums2`.
* `k`: length / next insertion index in temporary array `nums3`.
* `nums3`: preallocated with max possible size (`len1 + len2`) to store unique union.

**Main Loop:**
While both arrays have remaining elements:

1. Pick candidate `e`:

   * If `nums1[i] < nums2[j]` → `e = nums1[i++]`
   * Else if `nums1[i] > nums2[j]` → `e = nums2[j++]`
   * Else (equal) → `e = nums1[i++]` and also `j++` (skip duplicate in second array)
2. Append `e` to `nums3` **only if** `k == 0 || nums3[k-1] != e` (dedup guard).

**Tail Loops:**
Process remaining elements of `nums1`, then `nums2`, each with the same dedup guard.

**Return:**
`Arrays.copyOf(nums3, k)` trims unused capacity to produce the exact sized result.

---

## 4. Correctness Reasoning

* Because inputs are sorted, once you choose the smaller head element you won’t miss any earlier unseen values.
* Equal elements across arrays are consumed together (both pointers advanced), ensuring a single copy.
* Internal duplicates inside an individual array appear contiguously; the `nums3[k-1] != e` check suppresses them.
* Result stays sorted because merge order is preserved.

---

## 5. Complexity

| Aspect | Cost                                                                                                                                                        |
| ------ | ----------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Time   | `O(n + m)` (each pointer advances monotonically)                                                                                                            |
| Space  | `O(n + m)` for temporary `nums3` (output size upper bound). Returning a new array is allowed since “union” inherently may need up to `n + m` unique values. |

This is optimal: any algorithm must at least read all inputs (`Θ(n + m)`).

---

## 6. Edge Cases & Behavior

| Input Case                                   | Outcome                                                    |
| -------------------------------------------- | ---------------------------------------------------------- |
| One array empty                              | Returns copy of the other (dedup still works).             |
| Both empty                                   | Returns empty array.                                       |
| All elements identical (`[5,5]` & `[5,5,5]`) | Returns `[5]`.                                             |
| No overlap (`[1,2]`, `[3,4]`)                | Returns `[1,2,3,4]`.                                       |
| Large duplicate blocks                       | Each unique value appended once due to last-element check. |

---

## 7. Potential Micro-Optimizations (Optional)

1. **Skip internal duplicates earlier** (advance `i` or `j` past runs) – saves some redundant comparisons but not big asymptotic gain.
2. **Use `ArrayList<Integer>`** then convert to array for readability; current preallocation is already efficient.

*Not required for correctness or complexity.*

---

## 8. Comparison to Alternative Approaches

| Approach                   | Time                    | Extra Space         | Notes                                   |
| -------------------------- | ----------------------- | ------------------- | --------------------------------------- |
| Your two-pointer merge     | `O(n + m)`              | `O(n + m)` (result) | Exploits sorted order; stable           |
| Concatenate + sort + dedup | `O((n+m) log(n+m))`     | `O(n + m)`          | Slower due to sorting again             |
| HashSet insert then sort   | `O(n + m) + O(U log U)` | `O(U)`              | Loses original exploit of sorted inputs |
| TreeSet insert             | `O((n+m) log U)`        | `O(U)`              | Simpler code but extra log factor       |

Your approach is best for already sorted arrays.

---

## 9. Interview One-Liner Explanation

“Merge the two sorted arrays with two pointers, append the smaller element each time (or one copy if equal), and suppress duplicates by comparing with the last inserted element—linear time, no extra data structures beyond the output buffer.”

---

## 10. Key Takeaway Sentence

“Linear two-pointer merge plus a last-insert check yields the sorted unique union in `O(n + m)` time.”

---

## 11. Cleaned (Optional) Version for Notes

```java
public int[] unionArray(int[] a, int[] b) {
    int n1 = a.length, n2 = b.length;
    int[] temp = new int[n1 + n2];
    int i = 0, j = 0, k = 0;

    while (i < n1 && j < n2) {
        int e;
        if (a[i] < b[j]) {
            e = a[i++];
        } else if (a[i] > b[j]) {
            e = b[j++];
        } else {
            e = a[i++];
            j++;
        }
        if (k == 0 || temp[k - 1] != e)
            temp[k++] = e;
    }
    while (i < n1) {
        int e = a[i++];
        if (k == 0 || temp[k - 1] != e) temp[k++] = e;
    }
    while (j < n2) {
        int e = b[j++];
        if (k == 0 || temp[k - 1] != e) temp[k++] = e;
    }
    return Arrays.copyOf(temp, k);
}
```

---

## 12. Related Variants

* **Intersection of Two Sorted Arrays (unique)**: Similar scan; only add when `a[i] == b[j]`.
* **Intersection with counts**: Advance both and add duplicates as many times as they appear (min frequency).
* **K-way union**: Merge pairwise or use min-heap; maintain dedup check with last output value.

---

Link: https://takeuforward.org/plus/dsa/problems/union-of-two-sorted-arrays
