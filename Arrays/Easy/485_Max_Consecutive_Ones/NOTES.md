Interview‑ready notes tailored to *your* implementation.

---

## 1. Problem Statement

Given a binary array `nums` (elements are `0` or `1`), return the **length of the longest contiguous sequence of 1s**.

**Example**
`[1,1,0,1,1,1]` → longest run = `3`.

---

## 2. Core Idea

Scan once. Maintain a running count (`sum`) of current consecutive 1s.

* When you see a `1`: increment `sum`.
* When you see a `0`: update `max` with `sum` (if larger) and reset `sum = 0`.
  After the loop, take `max(max, sum)` to capture a run that ends at the last element.

---

## 3. Your Algorithm (Code Recap)

```java
public int findMaxConsecutiveOnes(int[] nums) {
    int max = 0, sum = 0;
    for (int v : nums) {
        if (v == 1) {
            sum++;
        } else {
            max = Math.max(max, sum);
            sum = 0;
        }
    }
    return Math.max(max, sum);
}
```

---

## 4. Correctness Reasoning

* `sum` always holds the length of the current uninterrupted 1-run.
* Each time a `0` breaks the run, you compare and store the best seen so far (`max`).
* Final `Math.max(max, sum)` handles the case where the array ends with 1s (no trailing zero to trigger the update).

---

## 5. Complexity

| Aspect | Value              |
| ------ | ------------------ |
| Time   | `O(n)` single pass |
| Space  | `O(1)` extra       |

Optimal: you must inspect every element at least once.

---

## 6. Edge Cases

| Input       | Result | Explanation                    |
| ----------- | ------ | ------------------------------ |
| `[0,0,0]`   | `0`    | No 1s.                         |
| `[1,1,1]`   | `3`    | Single run across whole array. |
| `[1]`       | `1`    | Trivial single element.        |
| `[0]`       | `0`    | Trivial single element.        |
| `[1,0,1,1]` | `2`    | Longest final run length 2.    |

---

## 7. Possible Micro-Refinements (Optional, Not Required)

* Slight style tweak: update `max` *after* loop only once by moving `Math.max` outside. (You already handle final run with last return.)
* Early exit is not useful; must scan full array.

---

## 8. Common Pitfalls Avoided

* Forgetting to update `max` after the loop (you handle with final `Math.max`).
* Resetting `sum` incorrectly (you reset only on zero).
* Using extra arrays or unnecessary data structures.

---

## 9. Interview One-Liner

“Single pass: count current streak of 1s, reset on 0, and keep track of the maximum streak — O(n) time, O(1) space.”

---

## 10. Key Takeaway Sentence

“Maintain a running streak length and update a global maximum whenever a zero breaks the streak; this yields an optimal O(n)/O(1) solution.”

---

## 11. Related Variants

| Variant                                           | Difference              | Typical Approach                                |
| ------------------------------------------------- | ----------------------- | ----------------------------------------------- |
| **Max Consecutive Ones II** (flip at most one 0)  | Allow one zero flip     | Sliding window counting zeros                   |
| **Max Consecutive Ones III** (flip up to k zeros) | Allow k flips           | Sliding window with zero count                  |
| Longest subarray of 1s after deleting one element | Must delete one element | Sliding window / tracking previous zero segment |

(Your solution is specifically for the base problem with *no flips*.)

---

Link:https://leetcode.com/problems/max-consecutive-ones/
