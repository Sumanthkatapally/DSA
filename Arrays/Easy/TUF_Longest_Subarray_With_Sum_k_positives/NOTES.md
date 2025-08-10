Here are concise notes for the “longest subarray with sum = K” (positives-only) problem:

---

## Problem

Given an array of **only positive** integers and a target sum `k`, find the **length** of the longest contiguous subarray whose elements sum to exactly `k`.

## Key insight (positives-only)

Because all numbers are positive, the running sum is monotonic when you extend the window to the right. You can maintain a sliding window `[left..right]`:

* Expand `right` adding `nums[right]` to `sum`.
* While `sum > k`, shrink from the left (`sum -= nums[left++]`).
* If `sum == k`, record window length (`right - left + 1`).

This yields O(n) time and O(1) space.

## Clean algorithm (pseudocode)

```
left = 0
sum = 0
maxLen = 0
for right in 0..n-1:
    sum += nums[right]
    while sum > k and left <= right:
        sum -= nums[left]
        left++
    if sum == k:
        maxLen = max(maxLen, right - left + 1)
return maxLen
```

## Complexity

* Time: O(n) — each element enters and leaves the window at most once.
* Space: O(1)

## Edge cases / behavior

* `k = 0`: works if there are zeros; longest run of zeros will be found. If no zero, returns 0.
* Single element equal to `k`: captured correctly.
* No subarray sums to `k`: returns 0 (or `-1` if you choose that sentinel instead).
* Entire array sums to `k`: window grows to full length.
* Large `k` greater than total sum: returns 0.

## Assumptions & limitations

* **Only positive integers**: sliding window logic relies on this. With negatives, the shrink/expand logic breaks because sum can decrease when expanding.
* If negatives are allowed, you need a different approach (prefix-sum + hashmap to track earliest occurrence of a prefix sum).

## Variants / extensions

* Return the subarray itself: track the best `left` and `right` indices when updating `maxLen`.
* If you want the **maximum sum ≤ k**, you’d need a different structure (e.g., balanced BST over prefix sums).
* With arbitrary integers (including negatives), find longest subarray with sum = k: use a hashmap of prefix sum → earliest index and scan once.

## Sample tests

1. `nums = [1,5,7,2,3], k=12` → subarray `[5,7]` or `[7,2,3]` length = 2 or 3; longest is 3.
2. `nums = [1,2,3], k=7` → none, return 0.
3. `nums = [0,0,0], k=0` → length = 3.
4. `nums = [4], k=4` → length = 1.
5. `nums = [1,2,3,4,5], k=9` → `[2,3,4]` length = 3.

## Improvements over original code

* Remove redundant branching; unify addition of `nums[right]`.
* Update `maxLen` immediately when `sum == k` (no special trailing-case logic needed).
* Add null check if defensive (`if nums == null`).
* Optionally expose sentinel (`-1`) if “no such subarray” should be distinguishable.

---

Link:https://takeuforward.org/plus/dsa/problems/longest-subarray-with-sum-k