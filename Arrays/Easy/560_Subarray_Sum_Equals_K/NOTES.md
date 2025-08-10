Here are comprehensive notes for **“Subarray Sum Equals K”** (LeetCode 560) — problem, intuition, approaches, pitfalls, edge cases, complexity, and examples.

---

## Problem Statement

Given an integer array `nums` and an integer `k`, return the number of **contiguous subarrays** whose sum equals `k`.

---

## Key Identity / Intuition

Let `prefixSum[i]` be the sum of elements from `nums[0]` through `nums[i]`.
Then the sum of a subarray `nums[i..j]` is:

```
sum(nums[i..j]) = prefixSum[j] - prefixSum[i-1]
```

So:

```
prefixSum[j] - k = prefixSum[i-1]
```

Meaning: for each `j`, count how many earlier prefix sums equal `prefixSum[j] - k`. Each such earlier occurrence corresponds to a distinct subarray ending at `j` with sum `k`.

---

## Efficient Approach: Prefix Sum + Frequency Map

### Overview

* Maintain a running `sum` (i.e., current prefix sum) as you iterate the array.
* Keep a map `freq` from prefix sum value → how many times it has occurred so far.
* At each index:

  1. Update running `sum` with `nums[i]`.
  2. Compute `needed = sum - k`.
  3. Add `freq.get(needed)` to the answer (if any). That counts all previous starting points producing a subarray ending here with sum `k`.
  4. Increment `freq[sum]` (record that this prefix sum has now been seen one more time).

### Initialization

* `freq` must start with `{0:1}`.
  This accounts for subarrays starting at index 0 where `prefixSum[j] == k` because then `needed = prefixSum[j] - k = 0`, and we want to count that.

### Pseudocode

```
count = 0
sum = 0
freq = map with freq[0] = 1

for each num in nums:
    sum += num
    needed = sum - k
    if freq contains needed:
        count += freq[needed]
    freq[sum] = freq.get(sum, 0) + 1

return count
```

---

## Detailed Example

### Example: `nums = [1, 2, 3, -2, 1]`, `k = 3`

Subarrays summing to 3: `[1,2]`, `[2,3,-2]`, `[3]` → total 3.

Walkthrough:

* Start: `sum=0`, `count=0`, `freq={0:1}`
* Process `1`: `sum=1`, `needed= -2` (none), update `freq` → `{0:1,1:1}`
* Process `2`: `sum=3`, `needed=0` → `freq[0]=1` → `count=1`, update `freq` → `{0:1,1:1,3:1}`
* Process `3`: `sum=6`, `needed=3` → `freq[3]=1` → `count=2`, update `freq` → `{0:1,1:1,3:1,6:1}`
* Process `-2`: `sum=4`, `needed=1` → `freq[1]=1` → `count=3`, update `freq` → `{...,4:1}`
* Process `1`: `sum=5`, `needed=2` → none, update `freq` → `{...,5:1}`

Final `count = 3`.

---

## Edge Cases to Consider

* **All zeros with `k=0`**: e.g., `[0,0,0]` → large number of overlapping subarrays. Correct algorithm counts multiplicity. (Answer is `n*(n+1)/2`.)
* **Negative numbers**: Works naturally; no assumption about monotonic sums.
* **Single element equal to `k`**: Handled via initial `{0:1}`.
* **Empty array**: Return 0.
* **Large values / potential integer overflow**: In languages with bounded integers, ensure prefix sum type can hold accumulated value (e.g., use `long` in Java if inputs can be large).

---

## Common Mistakes / Pitfalls

* **Storing only existence or index** instead of frequency: leads to undercounting when the same prefix sum appears multiple times.
  E.g., treating presence of `sum - k` as a boolean adds just 1 instead of “how many times” it appeared.
* **Forgetting to initialize `freq[0]=1`**: misses subarrays starting at index 0.
* **Updating the map before querying**: if you increment `freq[sum]` before checking `sum - k`, you might count current element incorrectly as a preceding prefix.
* **Using `putIfAbsent` to store a prefix sum’s index**: that only saves the first occurrence and ignores multiplicity.

---

## Complexity

* **Time:** O(n) — single pass over array, constant work per element.
* **Space:** O(n) in worst case (distinct prefix sums stored in the map).

---

## Test Cases

1. Simple:

   * `nums = [1, 1, 1]`, `k = 2` → subarrays: `[1,1]` twice → result `2`.
2. Mixed with negatives:

   * `nums = [1, -1, 1, -1, 1]`, `k = 0` → result `6`.
3. Starts at zero:

   * `nums = [3, 0, 0]`, `k = 3` → subarrays: `[3]`, `[3,0]`, `[3,0,0]` → result `3`.
4. All zeros:

   * `nums = [0,0,0]`, `k = 0` → `6` (n=3 → 3\*4/2).
5. No match:

   * `nums = [1,2,3]`, `k = 7` → result `0`.

---

## Variations / Extensions

* Count subarrays with sum **at most** or **at least** `k`: need different data structures (e.g., prefix sums + balanced BST / Fenwick for cumulative counts).
* Count subarrays with sum equal to `k` in **2D** matrix: reduce to repeated 1D prefix-sum+map per pair of rows.

---

## Summary

* Use prefix sums to convert a subarray sum query into a difference of two prefix sums.
* Track frequencies of seen prefix sums so you can instantly know how many valid starts exist for each end.
* Initialize with `{0:1}` to handle subarrays starting at index 0.
* Update counts before adding the current prefix sum to the map to avoid self-counting.

---

Link: https://leetcode.com/problems/subarray-sum-equals-k/description/
