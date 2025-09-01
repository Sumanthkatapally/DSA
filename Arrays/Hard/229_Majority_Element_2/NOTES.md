## 📌 Problem Restatement

Given an integer array `nums` of size `n`, find **all elements** that appear **more than ⌊ n/3 ⌋ times**.

* There can be at most **two such elements**:

  * Because if there were three or more, their total occurrences would exceed `n`.

---

## 📌 Naive Approaches

1. **HashMap Counting**

   * Count frequencies of each element.
   * Collect elements with count > n/3.
   * Time: O(n), Space: O(n).
   * Not optimal because extra space is required.

2. **Sorting**

   * Sort the array, then count runs.
   * Time: O(n log n), Space: O(1) or O(n) depending on sorting.
   * Also not optimal.

---

## 📌 Optimal Approach – Boyer–Moore Voting Algorithm (Extended for n/3)

The classic Boyer–Moore Voting algorithm finds the majority element (> n/2).
It can be generalized to find all elements > n/k. For k = 3, we keep **two candidates**.

### Idea

* Maintain two potential candidates (`cand1`, `cand2`) and their counts (`cnt1`, `cnt2`).
* Scan the array once to determine candidates.
* Then verify counts in a second pass.

---

## 📌 Step-by-Step Algorithm

### Step 1: Candidate Selection

For each number `x` in `nums`:

1. If `x == cand1` → increment `cnt1`.
2. Else if `x == cand2` → increment `cnt2`.
3. Else if `cnt1 == 0` → assign `cand1 = x`, `cnt1 = 1`.
4. Else if `cnt2 == 0` → assign `cand2 = x`, `cnt2 = 1`.
5. Else → decrement both `cnt1` and `cnt2`.

⚠️ Why this works:

* If a number is majority (> n/3), it cannot be eliminated entirely by this process.
* At the end, `cand1` and `cand2` are the only possible majority candidates.

---

### Step 2: Verification

* Reset counts `cnt1 = cnt2 = 0`.
* Count actual occurrences of `cand1` and `cand2`.
* Add to result if count > n/3.

---

## 📌 Correct Implementation

```java
import java.util.*;

class Solution {
    public static List<Integer> majorityElement(int[] nums) {
        List<Integer> res = new ArrayList<>();
        int n = nums.length;
        if (n == 0) return res;

        // Step 1: Find potential candidates
        int cand1 = 0, cand2 = 0;
        int cnt1 = 0, cnt2 = 0;

        for (int x : nums) {
            if (x == cand1) {
                cnt1++;
            } else if (x == cand2) {
                cnt2++;
            } else if (cnt1 == 0) {
                cand1 = x;
                cnt1 = 1;
            } else if (cnt2 == 0) {
                cand2 = x;
                cnt2 = 1;
            } else {
                cnt1--;
                cnt2--;
            }
        }

        // Step 2: Verify counts
        cnt1 = 0; cnt2 = 0;
        for (int x : nums) {
            if (x == cand1) cnt1++;
            else if (x == cand2) cnt2++;
        }

        int threshold = n / 3;
        if (cnt1 > threshold) res.add(cand1);
        if (cnt2 > threshold) res.add(cand2);

        return res;
    }
}
```

---

## 📌 Complexity Analysis

* **Time:** O(n)

  * One pass to choose candidates, one pass to verify.
* **Space:** O(1)

  * Only counters and candidates stored.
* **Result size:** ≤ 2 elements.

---

## 📌 Example Walkthrough

Input: `nums = [3,2,3]`

1. Pass 1 (candidate selection):

   * `cand1 = 3, cnt1 = 2`
   * `cand2 = 2, cnt2 = 1`
2. Pass 2 (verification):

   * Count of `3` = 2 → 2 > 1 → add `3`.
   * Count of `2` = 1 → not > 1 → ignore.
3. Output: `[3]`

---

## 📌 Key Notes & Pitfalls

1. There can be **0, 1, or 2** majority elements.
2. Must verify counts at the end (candidates are only potential).
3. Order of checks matters:

   * Always check equality before assigning empty candidate slots → prevents duplicates.
4. Explicit `x != cand1`/`x != cand2` guards are optional but make intent clearer.
5. This algorithm generalizes:

   * For > n/k, keep k−1 candidates.

---

✅ **Conclusion:**
The algorithm above is **optimal** for Majority Element II. It achieves O(n) time and O(1) space while handling edge cases like duplicates or no majority elements.

---(boyer moore)

Link: https://leetcode.com/problems/majority-element-ii/description/