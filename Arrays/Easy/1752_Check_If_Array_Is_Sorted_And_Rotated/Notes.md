Below are concise, interview‑ready notes for **LeetCode 1752 – Check if Array Is Sorted and Rotated**, tailored to *your* solution.

---

## 1. Problem Statement

Given an array `nums`, determine if it is **non‑decreasingly sorted** and then **rotated** *any number of times* (possibly zero).
Formally, there exists a non‑decreasing array `arr` and an integer `k` (0 ≤ k < n) such that rotating `arr` right (or equivalently left) by `k` positions yields `nums`.
Zero rotation (already sorted, not rotated) is considered valid.

**Examples**

* `[3,4,5,1,2]` → true (sorted base `[1,2,3,4,5]` rotated)
* `[2,1,3,4]` → false
* `[1,2,3]` → true (k = 0)
* `[1,1,1]` → true (all equal)

---

## 2. Core Insight

In a non‑decreasing array rotated arbitrarily, when scanning circularly you’ll see **at most one “drop”** where `nums[i] > nums[i+1]` (with wrap via modulo).

* If **0 drops** → array already sorted (valid).
* If **1 drop** → rotation point (valid).
* If **>1 drops** → cannot come from a single rotation of a non‑decreasing array (invalid).

Your code counts such drops.

---

## 3. Algorithm (Your Implementation)

```java
public boolean check(int[] nums) {
    int n = nums.length;
    int c = 0;
    for (int i = 0; i < n; i++) {
        if (nums[i] > nums[(i + 1) % n]) { // compare with next (circular)
            c++;
            if (c > 1) return false;
        }
    }
    return true;
}
```

**Steps:**

1. Loop `i = 0 .. n-1`; compare `nums[i]` to `nums[(i+1) % n]`.
2. Increment `c` for each violation of non‑decreasing order.
3. Early return `false` if `c > 1`.
4. Finish → return `true`.

---

## 4. Correctness Reasoning

* A rotation splits a sorted array into two blocks: tail `T` then head `H` (e.g., `T=[3,4,5]`, `H=[1,2]`).
* Inside each block, sequence is non‑decreasing; only **one junction** (`last(T) > first(H)`) breaks the order.
* More than one junction implies >1 “wraps” → impossible from a single rotation.

---

## 5. Edge Cases

| Case                       | Result       | Explanation                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
| -------------------------- | ------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Length 1 `[x]`             | true         | Trivially sorted/rotated.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| All equal `[2,2,2]`        | true         | No drops (`c = 0`).                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
| Already sorted `[1,2,3,4]` | true         | `c = 0` (k=0 allowed).                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              |
| Two drops e.g. `[2,1,3,0]` | false        | Drops at `2>1` and `3>0`.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| Descending `[3,2,1]`       | false        | Drops at `3>2` and `2>1` (c=2).                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
| `[1,3,2]`                  | true? **No** | Drops: `3>2` only → BUT check? Actually rotation of sorted `[1,2,3]` cannot produce `[1,3,2]` (order after pivot must wrap smallest segment to front). **However** the algorithm says true. Why? Because pattern allows 1 drop anywhere. **Why still valid?** Because `[1,3,2]` is NOT a rotation of non‑decreasing `[1,2,3]`. Wait—**Important:** This array has smallest element `1` not at position after drop (`3>2` drop between indices 1 & 2). After rotation, overall order around circle must be non‑decreasing starting at min. Starting from the minimum (index 0 = 1), sequence 1,3,2 is not non‑decreasing. **But the problem definition considers circular order; counting drops over circular traversal still yields 1 (3>2).** Actually `nums = [1,3,2]` *is* a rotation? Rotations of `[1,2,3]`: `[1,2,3]`, `[2,3,1]`, `[3,1,2]`. `[1,3,2]` not in list → should be false. **Why algorithm works anyway:** Circular scan: comparisons: 1≤3 (ok), 3>2 (drop1), 2≤1? 2 > 1 (second drop!) because wrap compare `2 > 1` is true. So there are **2 drops**. Thus returns false. (Clarifies reasoning.) |

---

## 6. Complexity

* **Time:** `O(n)` single pass.
* **Space:** `O(1)`.

Optimal—must examine each element at least once.

---

## 7. Robustness / Pitfalls

* Must use modulo for circular compare: `(i+1) % n`.
* Use `>` not `>=` because equal elements are allowed (non‑decreasing).
* Early exit on `c > 1` saves time on invalid arrays.
* Works for `n=1` because `(0+1)%1 = 0`; comparison is `nums[0] > nums[0]` false.

---

## 8. Alternative Check (Optional)

1. Find index of minimum value (first occurrence).
2. Traverse from there (circular) ensuring each next element ≥ previous.
3. Equivalent logic; slightly more work with duplicates → your count‑drops approach is simpler.

---

## 9. Interview One-Liner Explanation

“Scan circularly and count how many times order decreases (`nums[i] > nums[i+1]`). A sorted rotation has at most one such drop. More than one ⇒ false.”

---

## 10. Quick Memory Aid

**At most 1 descent in the circular pass.**

---

## 11. Related Problems / Patterns

* Rotate Array validation logic (uses circular comparisons).
* “Check if sorted and rotated” for strings (same 1‑break principle).
* Detect single inversion vs multiple inversions as a property test.

---

## 12. Key Takeaway Sentence

“An array is a rotation of a non‑decreasing array iff a circular scan shows no more than one strict decrease.”

---

Link: https://leetcode.com/problems/check-if-array-is-sorted-and-rotated/
