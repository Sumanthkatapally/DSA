Interview‑ready notes for **LeetCode 268 – Missing Number** tailored to *your* summation solution.

---

## 1. Problem Statement

Given an array `nums` of length `n` containing **distinct integers** from the range `[0, n]` **with exactly one number missing**, return the missing number.

**Example**
`nums = [3,0,1]` (n=3) → numbers should be `{0,1,2,3}` → missing = `2`.

---

## 2. Key Insight

The complete set `0 + 1 + 2 + ... + n` has a known arithmetic series sum:

$$
\text{expected} = \frac{n (n+1)}{2}
$$

If you subtract the **actual sum** of array elements from this expected sum, the remainder is the missing number.

---

## 3. Your Algorithm (Sum Formula)

```java
public int missingNumber(int[] nums) {
    int n = nums.length;
    int expected = n * (n + 1) / 2;
    int actual = 0;
    for (int x : nums) actual += x;
    return expected - actual;
}
```

**Steps:**

1. Compute `expected = n*(n+1)/2`.
2. Accumulate `actual` sum of `nums`.
3. Return `expected - actual`.

---

## 4. Correctness Reasoning

Because array elements are a permutation of all numbers *except one*, every number appears exactly once except the missing one, so the difference of the two sums isolates that missing value.

---

## 5. Complexity

* **Time:** `O(n)` (single pass).
* **Space:** `O(1)` extra.

Optimal: must read all elements at least once.

---

## 6. Edge Cases

| Case                                   | Explanation                                |
| -------------------------------------- | ------------------------------------------ |
| Missing `0` (e.g., `[1,2,3]` when n=3) | Sum difference yields `0`.                 |
| Missing `n` (e.g., `[0,1,2]` when n=3) | Expected sum bigger; difference gives `3`. |
| Single element `[0]` (n=1)             | Missing is `1`.                            |
| Single element `[1]` (n=1)             | Missing is `0`.                            |

---

## 7. Alternative Optimal Methods (Same O(n)/O(1))

| Method              | Idea                                                         | Pros                                    | Cons                                                                                                                     |
| ------------------- | ------------------------------------------------------------ | --------------------------------------- | ------------------------------------------------------------------------------------------------------------------------ |
| Sum formula (yours) | Use arithmetic series                                        | Simple                                  | Potential (rare) 32‑bit overflow if `n` near 65k+? (In Java int safe until \~65k? Actually safe up to n≈65535? See note) |
| XOR method          | XOR all indices 0..n and all elements; leftover is missing   | Avoids overflow                         | Slightly less intuitive                                                                                                  |
| Gauss incremental   | Start `missing = n`; for each `i` add `i` subtract `nums[i]` | One-pass without separate sum variables | Just a rearranged form of sum/xor                                                                                        |

**Overflow Note:** In Java `int` handles up to \~2.1e9. `n*(n+1)/2` may overflow when `n ≈ 65,535` is NOT the limit; actual overflow risk begins when `n*(n+1)/2 > 2,147,483,647` → `n ≈ 65,535`? (Check: 65,535*65,536 /2 ≈ 2,147,450,880 < 2,147,483,647, safe; next n=65,536 gives 65,536*65,537/2 ≈ 2,147,516,416 > INT\_MAX). LeetCode constraints keep `n ≤ 10^4` typically, so safe. If worried, cast to `long`.

---

## 8. (Optional) Safe Version with `long`

```java
public int missingNumber(int[] nums) {
    int n = nums.length;
    long expected = (long)n * (n + 1) / 2;
    long actual = 0;
    for (int x : nums) actual += x;
    return (int)(expected - actual);
}
```

---

## 9. Interview One‑Liner

“Use the formula n(n+1)/2 minus the actual sum; the difference is the missing value in O(n) time and O(1) space.”

---

## 10. Key Takeaway Sentence

“Arithmetic series sum minus array sum isolates the single missing number efficiently.”

---

## 11. Related / Variants

* **Missing Number (XOR version)**: `int miss = n; for i=0..n-1: miss ^= i ^ nums[i]; return miss;`
* **Find All Missing Numbers (multiple missing)**: Needs marking via index sign or extra structure (problem #448).
* **First Missing Positive**: Requires partition + in-place index marking (#41) — different pattern.

---
Link:https://leetcode.com/problems/missing-number/description/
