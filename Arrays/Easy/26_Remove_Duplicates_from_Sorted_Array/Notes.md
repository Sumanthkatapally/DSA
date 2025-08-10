Interview‑ready notes for **LeetCode 26 – Remove Duplicates from Sorted Array**, tailored to *your* two‑pointer solution.

---

## 1. Problem Statement

Given a **sorted** integer array `nums`, remove duplicates **in place** so that each unique value appears exactly once. Return the new length `k`; the first `k` positions of `nums` must store the unique elements in their original relative order. Extra space allowed: **O(1)**.

**Example**
Input: `[0,0,1,1,1,2,2,3,3,4]` → Output length `5`, mutated prefix `[0,1,2,3,4, ...]`.

---

## 2. Core Insight

Because the array is already **sorted non‑decreasing**, duplicates appear in contiguous runs. You can keep a *write pointer* for the end of the unique prefix and a *read pointer* scanning the array once.

---

## 3. Your Algorithm (Two Pointers: Read / Write)

Variables:

* `i` (read pointer) scans from index `1` to `n-1`.
* `j` (write pointer) marks index of **last unique element** stored so far (starts at `0`).

Process:

1. Initialize `j = 0`.
2. For each `i` from `1` to `n-1`:

   * If `nums[i]` differs from `nums[i-1]`, we found a new unique value:

     * Increment `j`.
     * Write it: `nums[j] = nums[i]`.
3. Return `j + 1` (count of uniques).

Your code implements exactly this logic.

---

## 4. Correctness Reasoning

* Sorted order guarantees every time `nums[i] != nums[i-1]`, `nums[i]` has not appeared earlier.
* Writing each new distinct value at `++j` builds a compact prefix of unique values, preserving order.
* All operations are in-place, using only constant extra variables.

---

## 5. Complexity

* **Time:** `O(n)` single pass.
* **Space:** `O(1)` extra.
* This is the **optimal** time and space bound (must read each element at least once; extra space restricted).

---

## 6. Edge Cases

| Case                                         | Behavior                                           |
| -------------------------------------------- | -------------------------------------------------- |
| `n = 0` (not shown, but LeetCode ensures ≥1) | Should handle if added: return 0.                  |
| `n = 1` (e.g., `[5]`)                        | Early return 1 (your explicit check).              |
| All duplicates `[2,2,2]`                     | `j` stays 0 → returns 1, prefix `[2]`.             |
| All unique `[1,2,3]`                         | Each step writes element onto itself; returns `3`. |
| Mixed runs `[1,1,2,2,2,3,3,4]`               | Writes unique sequence `[1,2,3,4]`.                |

---

## 7. Potential Micro-Refinement

Your early `if (n == 1) return 1;` is fine; you can omit it because the loop naturally handles it (loop won’t run, `j=0`, return `j+1=1`). Not a performance issue—just stylistic.

---

## 8. Annotated Version (Slightly Refactored)

```java
public int removeDuplicates(int[] nums) {
    int n = nums.length;
    if (n == 0) return 0;        // defensive (LeetCode usually n >= 1)
    int j = 0;                   // last unique index
    for (int i = 1; i < n; i++) {
        if (nums[i] != nums[i - 1]) {
            j++;
            nums[j] = nums[i];
        }
    }
    return j + 1;
}
```

---

## 9. Explanation for Interview

“I maintain a write pointer to the end of the unique segment. I scan once; whenever the current element differs from its predecessor (since array is sorted), it’s a new unique value, so I advance the write pointer and store it there. Time O(n), space O(1), stable order.”

---

## 10. Common Pitfalls

* Forgetting to handle (or at least think about) `n = 0`.
* Returning `j` instead of `j+1` (off‑by‑one in length).
* Using a hash set (unnecessary extra space, violates O(1) requirement).
* Attempting to delete elements (shifting) instead of simple overwrite.

---

## 11. Related Pattern / Variants

* **LeetCode 80:** Allow at most two occurrences (modify condition).
* **Remove Element (27):** Similar write pointer but condition based on a target value.
* **Move Zeroes (283):** Write pointer compaction pattern.
* **Deduplicate unsorted array:** Requires set; cannot do in O(1) extra space reliably.

---

## 12. Key Takeaway Sentence

“Leverage sorted order: scan once, copy each first occurrence of a new value to the next write slot; return size of the unique prefix.”

---

Link: https://leetcode.com/problems/remove-duplicates-from-sorted-array/
