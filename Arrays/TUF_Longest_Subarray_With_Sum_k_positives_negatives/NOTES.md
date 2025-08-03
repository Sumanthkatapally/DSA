---

# 1. Problem Variants

## A. Longest contiguous subarray with sum = K, **only positive integers**

* **Input:** array `nums` of positive integers, target `k`.
* **Goal:** length of the longest contiguous subarray whose sum is exactly `k`.

## B. Longest contiguous subarray with sum = K, **arbitrary integers (can include negatives and zeros)**

* Sliding-window fails because sum is not monotonic. Need prefix-sum + hashmap.

---

# 2. Algorithm Details

## A. Positives-only: Sliding Window

### Key insight

With positive integers, extending the window increases the sum; shrinking decreases it. So maintain a window `[left..right]` with running sum and adjust to keep it ≤ k, checking equality.

### Pseudocode

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

### Java implementation

```java
public static int longestSubarrayPositiveOnly(int[] nums, int k) {
    if (nums == null) return 0;
    int left = 0, sum = 0, maxLen = 0;
    for (int right = 0; right < nums.length; right++) {
        sum += nums[right];
        while (sum > k && left <= right) {
            sum -= nums[left++];
        }
        if (sum == k) {
            maxLen = Math.max(maxLen, right - left + 1);
        }
    }
    return maxLen;
}
```

### Complexity

* Time: O(n)
* Space: O(1)

### Edge cases

* `k = 0` with zeros in array → runs of zeros are correctly captured.
* No subarray sums to `k` → returns 0.
* Single element equal to `k` → length 1.
* Entire array sums to `k` → full length.
* Large `k` greater than total sum → 0.

### Common pitfalls

* Trying to generalize this logic to arrays with negatives (will break).
* Not updating `maxLen` immediately when sum == k, leading to needing final special-case logic.

---

## B. Arbitrary integers: Prefix Sum + HashMap

### Key idea

Define prefix sum `P[i] = nums[0] + ... + nums[i]`. A subarray `(j+1)..i` sums to `k` iff `P[i] - P[j] == k` ⇒ `P[j] == P[i] - k`. Track earliest occurrences of prefix sums so that when you see `P[i]`, you can ask if `P[i] - k` was seen before; that gives a candidate subarray, and using the earliest occurrence maximizes its length.

### Correct Java implementation

```java
public static int longestSubarray(int[] nums, int k) {
    int sum = 0;
    int max = 0; // or -1 if you want a sentinel for "not found"
    HashMap<Integer, Integer> firstIndex = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        sum += nums[i];
        if (sum == k) {
            max = Math.max(max, i + 1);
        }
        if (firstIndex.containsKey(sum - k)) {
            max = Math.max(max, i - firstIndex.get(sum - k));
        }
        firstIndex.putIfAbsent(sum, i); // keep earliest index for this prefix sum
    }
    return max;
}
```

### Why `putIfAbsent`?

You want the **earliest** index where a prefix sum was seen, because later when `sum - k` matches that prefix sum, subtracting the earliest gives the **longest** subarray. If you overwrite with a later index, you might miss a longer valid span.

#### Example for `putIfAbsent` necessity

Array: `[2, 3, 2, -2, 0, 1, 1, 1, 1, 1, 2]`, target `k = 7`
Suppose at some point prefix sum `5` appears at index 1, then again at index 4. Later prefix sum becomes `12`.
We check `12 - 7 = 5`:

* If we stored first occurrence (index 1), subarray length is `10 - 1 = 9`.
* If we had overwritten and kept index 4 instead, length would be `10 - 4 = 6` (shorter).

So use `putIfAbsent` to avoid overwriting earlier occurrences.

### Complexity

* Time: O(n) average (hashmap lookups)
* Space: O(n) for prefix sums stored

### Edge cases

* `k = 0`: finds longest subarray summing to zero, including those with negative/positive cancellation.
* Single element equals `k`: captured via `sum == k`.
* Entire array sums to `k`: captured.
* No such subarray: returns 0 (or -1 if you initialize `max = -1`).
* Negative, positive, zero values all supported.

### Common mistakes in naive/incomplete attempt

* Checking `if (sum > k && map.containsKey(sum - k))`: wrong, because valid subarrays may exist even if `sum <= k` (due to negatives). The `sum > k` guard must be removed.
* Overwriting earlier prefix sums (use `putIfAbsent`).
* Not handling the case where prefix sum itself equals `k` (i.e., subarray from start).

---

# 3. Sample Test Cases

### (A) Positives-only

1. `nums = [1,5,7,2,3]`, `k=12` → longest is `[7,2,3]`, length = 3
2. `nums = [1,2,3]`, `k=7` → none, return 0
3. `nums = [0,0,0]`, `k=0` → all zeros, return 3
4. `nums = [4]`, `k=4` → single match, return 1
5. `nums = [1,1,1,1]`, `k=2` → longest length 2 (e.g., `[1,1]`)

### (B) Arbitrary integers (includes negatives)

1. `nums = [1000, -500, 2000, -1500, 500, -1000, 3000, -2500, 4000, -3500, 5000, -4500, 6000, -5500, 7000, -6500, 8000, -7500, 9000, -8500]`, `k=5000`

   * Longest subarray is indices `0..8` (length 9): `[1000, -500, 2000, -1500, 500, -1000, 3000, -2500, 4000]` sums to 5000.
   * Also single `[5000]` at index 10, etc., but length 9 is max.

2. `nums = [1, -1, 1, -1, 1, -1]`, `k=0` → entire array can be chunked; longest zero-sum subarray could be length 6.

3. `nums = [5, -2, 3, 1]`, `k=6` → subarray `[5, -2, 3]` sums to 6, length 3.

4. `nums = [2, 4, -2, 1]`, `k=5` → `[4, -2, 1, 2]` (if wrap, but no wrapping here) check accordingly.

---

# 4. Variants & Extensions

* **Return the subarray itself**: track best `start` and `end` when updating `max`.
* **Return earliest longest** vs. latest longest: with prefix sums and `putIfAbsent` you get the earliest starting point for a given length.
* **Count of subarrays summing to K**: use hashmap tracking frequency of prefix sums; increment answer by `freq.get(sum - k)` each iteration.
* **Maximum sum ≤ K (positives or mixed)**: different problem—requires data structures like balanced BST over prefix sums (for arbitrary) or two pointers with prefix sums (for positives).
* **If you want “longest with sum exactly k” but want to return `-1` when none exists**: initialize `max = -1`.

---

# 5. Implementation Tips

* Always handle the prefix sum equal to `k` separately or combined via `if (sum == k)` check before hashmap lookup.
* Use `putIfAbsent` to preserve earliest index of a prefix sum.
* For large input sizes, both algorithms are linear, so they scale well.
* If input may be null, guard against NPE: e.g., `if (nums == null) return 0;`
* Write unit tests covering positives-only, negatives, zeros, no solution, full-array match, and multiple candidate windows.

---

# 6. Summary of Differences

| Feature                   | Positives-only (sliding window) | Arbitrary integers (prefix sum + map) |
| ------------------------- | ------------------------------- | ------------------------------------- |
| Supports negatives        | ❌ No                            | ✅ Yes                                 |
| Time complexity           | O(n)                            | O(n) expected                         |
| Space complexity          | O(1)                            | O(n)                                  |
| Core structure            | Two pointers window             | Prefix sum map                        |
| Need to store prefix sums | No                              | Yes (earliest index)                  |

---

Link:https://takeuforward.org/plus/dsa/problems/longest-subarray-with-sum-k
