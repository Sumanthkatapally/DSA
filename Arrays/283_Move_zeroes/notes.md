Interview‑ready notes for **Move Zeroes (LeetCode 283)** specifically for *your* implementation (which **is optimal**).

---

## 1. Problem Summary

Rearrange array *in place* so that all `0` values move to the **end**, while **preserving the relative order** of all non‑zero elements. Return nothing (mutation only).
Example: `[0,1,0,3,12] → [1,3,12,0,0]`.

---

## 2. Core Idea (Stable Compaction + Fill)

Use a **write pointer** `c` to compact all non‑zero elements at the front in their original order during a single pass. After that, fill the remaining tail with zeros.

---

## 3. Your Algorithm Steps

1. Initialize `c = 0`.
2. Single pass with index `i`:

   * If `nums[i] != 0`, copy it forward: `nums[c] = nums[i]`, increment `c`.
3. After pass, indices `[0 .. c-1]` hold all non‑zeros (stable order).
4. Second loop: set `nums[c .. end]` to `0`.

**Code Pattern:**

```java
int c = 0;
for (int i = 0; i < n; i++) {
    if (nums[i] != 0) nums[c++] = nums[i];
}
for (int j = c; j < n; j++) nums[j] = 0;
```

---

## 4. Correctness Reasoning

* Each non‑zero keeps its relative order because you append them in the sequence encountered.
* All zeros end up after position `c-1` because you explicitly write zeros only after all non‑zeros are placed.
* In‑place: only constant extra variables.

---

## 5. Complexity

* **Time:** `O(n)` (one scan + one tail fill).
* **Space:** `O(1)` extra.
  These are optimal; every element must be inspected at least once.

---

## 6. Edge Cases

| Input                         | Result Explanation                                                 |
| ----------------------------- | ------------------------------------------------------------------ |
| `[0,0,0]`                     | First pass writes nothing; fill sets all zeros (already zeros).    |
| `[1,2,3]`                     | `c` advances to `n`; second loop does nothing.                     |
| `[0]` / `[5]`                 | Trivial; logic still works.                                        |
| Interleaved zeros `[0,1,0,2]` | Non‑zeros compact to front → `[1,2,?,?]`, then fill → `[1,2,0,0]`. |

---

## 7. Operation Efficiency

* Each non‑zero written exactly once.
* Zeros only written in the fill loop.
* Total writes ≤ `n + (#zeros)` ≤ `2n` → linear, acceptable.
* (Optional micro‑optimization: skip self‑assignment when `i == c`.)

---

## 8. Comparison to Swap Variant

| Approach            | Writes                                       | Stability | Notes                                                                  |
| ------------------- | -------------------------------------------- | --------- | ---------------------------------------------------------------------- |
| Your “write & fill” | Overwrite each non‑zero once; fill zeros     | Stable    | Simple to reason about                                                 |
| Swap (two pointers) | Swaps when encountering non‑zero after zeros | Stable    | Slightly fewer writes if many leading non‑zeros but adds swap overhead |

Your method is **fully acceptable** and commonly presented as the clean solution.

---

## 9. Interview One-Liner

“Walk once compacting non‑zeros to the front with a write pointer, then fill the rest with zeros—O(n) time, O(1) space, stable.”

---

## 10. Key Takeaway Sentence

“Stable in‑place compaction of non‑zeros followed by a zero fill gives optimal O(n)/O(1) performance.”

---

Link: https://leetcode.com/problems/move-zeroes/