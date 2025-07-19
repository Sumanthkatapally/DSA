# DSA

# Complexity Cheat Sheet

Practical guide to decide **what time / space complexity is required** for coding interview problems (LeetCode / similar) based on **input constraints** and common **patterns**.

---

## 1. Size ➜ Target Complexity

| Max *n* (single array / string) | Safe Target                    | Borderline                  | Usually Too Slow                              |
| ------------------------------- | ------------------------------ | --------------------------- | --------------------------------------------- |
| ≤ 30                            | Anything                       | —                           | —                                             |
| ≤ 200                           | `O(n^2)`                       | `O(n^3)`                    | Exponential unless required                   |
| ≤ 1,000                         | `O(n^2)`                       | —                           | `O(n^3)`                                      |
| ≤ 5,000                         | `O(n log n)`                   | Light `O(n^2)` with pruning | Heavy `O(n^2)`                                |
| ≤ 10,000                        | `O(n log n)`                   | —                           | Plain `O(n^2)` (\~1e8 ops)                    |
| ≤ 100,000 (1e5)                 | `O(n)` / `O(n log n)`          | —                           | `O(n^2)` (1e10 ops)                           |
| ≤ 1,000,000 (1e6)               | Tight `O(n)`                   | —                           | `O(n log n)` (borderline in Python), `O(n^2)` |
| > 1e6                           | Specialized linear / sublinear | —                           | Anything more than linear                     |

**Rule of Thumb:** \~**1e8 simple operations** ≈ 1–2 seconds in C++/Java; Python comfortable limit ≈ **1e7**.

---

## 2. Multi-Dimensional / Product Constraints

| Situation                   | Acceptable               | Warning                         |
| --------------------------- | ------------------------ | ------------------------------- |
| `n * m ≤ 1e5`               | `O(n * m)`               | Avoid extra nested loops inside |
| `n * m ≤ 1e6`               | Need light per‑cell work | No triple nesting               |
| Graph: `n ≤ 2e5`, `m ≤ 2e5` | `O(n + m)` BFS/DFS       | `O(n * m)` adjacency matrix     |

---

## 3. Quick Mapping (Common Problems)

| Problem Type / Task          | Ideal Pattern        | Complexity                            |
| ---------------------------- | -------------------- | ------------------------------------- |
| Dedup sorted array           | Two pointers         | `O(n)`                                |
| Move zeroes / partition      | Two pointers         | `O(n)`                                |
| Union of two *sorted* arrays | Merge scan           | `O(n + m)`                            |
| Merge unsorted lists         | Insert + sort        | `O(n log n)`                          |
| 2Sum unsorted                | HashMap              | `O(n)`                                |
| 2Sum sorted                  | Two pointers         | `O(n)`                                |
| Kth in two sorted arrays     | Binary partition     | `O(log (n+m))`                        |
| Longest substring w/o repeat | Sliding window       | `O(n)`                                |
| Count inversions             | Merge sort variant   | `O(n log n)`                          |
| Top-K frequent elements      | Hash + Heap/Bucket   | `O(n log k)` or `O(n)`                |
| Shortest path unweighted     | BFS                  | `O(n + m)`                            |
| Dijkstra (positive weights)  | Min-heap             | `O(m log n)`                          |
| Cycle detection linked list  | Fast/slow pointers   | `O(n)`                                |
| Subset enumeration (n ≤ 20)  | Bitmask              | `O(n * 2^n)`                          |
| DP grid `n * m ≤ 1e6`        | Table DP             | `O(n*m)`                              |
| Segment tree operations      | Tree build + queries | Build `O(n)`, Query/Update `O(log n)` |

---

## 4. Patterns ➜ Typical Complexity

| Pattern                                  | Complexity            |
| ---------------------------------------- | --------------------- |
| Single pass / prefix sums / two pointers | `O(n)`                |
| Sorting + pass                           | `O(n log n)`          |
| Hash lookups                             | `O(1)` average        |
| Binary search (array)                    | `O(log n)`            |
| Binary search on answer                  | `O(log Range * f(n))` |
| Heap push/pop                            | `O(log n)`            |
| Balanced BST operations                  | `O(log n)`            |
| Union-Find (amortized)                   | ≈ `O(α(n))`           |
| Monotonic stack (span / next greater)    | `O(n)`                |
| BFS/DFS (graph)                          | `O(n + m)`            |
| Bitmask DP subsets                       | `O(n * 2^n)`          |
| Digit DP (numbers ≤ 10^18)               | `O(len * states)`     |

---

## 5. How to Decide (Fast Checklist)

1. **Read maximum constraints** (largest `n`, number of queries).
2. **Compute products/sums** (e.g., `n * m`, total across test cases).
3. **Match size to table** (decide target complexity).
4. **Discard slower approaches** immediately.
5. **Exploit properties**:

   * Already sorted? → Skip sorting; use two pointers / binary search.
   * Limited value range? → Counting array / bitset.
   * Need order + uniqueness? → Sort + dedup or ordered set.
   * Many queries? → Preprocess (prefix sums, sparse table, Fenwick tree).

---

## 6. Space Complexity Guidelines

| Hint in Problem   | Implication                                   |
| ----------------- | --------------------------------------------- |
| “In-place”        | Extra space should be `O(1)` (ignore output). |
| Large `n (1e6)`   | Avoid multiple large auxiliary arrays.        |
| “Sublinear space” | Use pointers / streaming / bit tricks.        |
| Graph edges large | Use adjacency list, not matrix.               |

**Memory Estimates:**

* `int` ≈ 4 bytes ➜ `1e5` ints ≈ 0.4 MB; `1e6` ints ≈ 4 MB.

---

## 7. Smell Tests (Reject Bad Ideas Early)

| Idea                | Question                   | If “Yes” → Rethink               |
| ------------------- | -------------------------- | -------------------------------- |
| Nested loops        | Are both up to 1e5?        | Too slow (`1e10` ops)            |
| Triple loop DP      | States > 1e7?              | Compress / optimize              |
| Sorting             | Are inputs already sorted? | Replace with linear merge        |
| Brute force subsets | Is `n > 25`?               | Needs pruning / DP               |
| BFS on huge grid    | Is `n*m > 1e6`?            | Optimize or avoid full traversal |

---

## 8. Micro Heuristics

| Constraint Phrase            | Translation                                    |
| ---------------------------- | ---------------------------------------------- |
| “n ≤ 10^5”                   | Use `O(n log n)` or `O(n)`                     |
| “Sum of n across T ≤ 10^5”   | Aggregate complexity must be linear/log-linear |
| “Values up to 10^9”          | Coordinate compression possible                |
| “K up to 10^5 queries”       | Preprocess + `O(1)`/`O(log n)` per query       |
| “All numbers small (≤ 1000)” | Counting arrays / DP over values               |

---

## 9. Example Walkthrough

**Problem:** “Union of two sorted arrays” with `n, m ≤ 1e5`.

* Target: `O(n + m)` (since inputs already sorted).
* Reject: Sort after concatenation (`O((n+m) log(n+m))`)—still acceptable but unnecessary.
* Choose: Two-pointer merge with dedup.

---

## 10. Mini Reference Card (Copy/Paste)

```
≤1e3  → n^2 OK
≤1e4  → prefer n log n
≤1e5  → n log n / n only
≤1e6  → tight n only
n*m   → keep ≤1e6 total ops
Sorted? → two pointers / binary search
Many queries? → preprocess
Small value range? → counting / bitset
```

---

## 11. Related Patterns & Their “Anchor Problems”

| Pattern                 | Anchor                                  |
| ----------------------- | --------------------------------------- |
| Two pointers            | #26 Remove Duplicates, #283 Move Zeroes |
| Sliding window          | #3 Longest Substring Without Repeating  |
| Monotonic stack         | #739 Daily Temperatures                 |
| Binary search on answer | #410 Split Array Largest Sum            |
| Prefix sum + hash       | #560 Subarray Sum Equals K              |
| Heap / Top-K            | #347 Top K Frequent Elements            |
| Union-Find              | #547 Number of Provinces                |
| Bitmask DP              | #78 / #90 Subsets (foundation)          |
| Merge technique         | #88 Merge Sorted Array                  |
| Graph BFS               | #542 01 Matrix                          |
| DP table                | #1143 LCS                               |

---

## 12. When in Doubt

> **Find the largest input dimension, map it to the table, enforce that target, and prune solution ideas that exceed it before writing code.**

---



