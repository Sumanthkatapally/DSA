**Detailed notes** for the `Longest Consecutive Sequence` solution 

---

# 📌 Problem: Longest Consecutive Sequence (LeetCode 128)

### **Statement**

Given an unsorted array of integers `nums`, return the length of the longest sequence of consecutive integers.

* A consecutive sequence means numbers that appear one after another without gaps (`x, x+1, x+2, ...`).
* The order in the array does not matter.

**Example:**
Input: `[100, 4, 200, 1, 3, 2]`
Output: `4` → sequence `[1,2,3,4]`

---

# 🛠️ Approach: HashSet + Sequence Starts

### **Key Idea**

* Use a **HashSet** for `O(1)` average lookup time.
* A number `x` can only start a sequence if `x-1` is **not** in the set.
  (Otherwise, `x` is somewhere in the middle of another sequence.)
* From each sequence start, keep walking forward (`x+1`, `x+2`, …) until it ends.
* Keep track of the longest sequence length found.

---

# ✅ Algorithm Steps

1. **Edge case check:** If array is empty → return `0`.
2. **Build HashSet:** Insert all elements from `nums` into a `HashSet`.

   * Ensures uniqueness (ignores duplicates).
   * Provides `O(1)` lookups.
3. **Iterate over set:** For each element `c` in the set:

   * If `(c-1)` is *not* in the set → `c` is a sequence start.
   * Initialize `cnt = 1` and `cur = c`.
   * While `(cur+1)` exists in the set → extend the sequence (`cur++`, `cnt++`).
   * Update `max = Math.max(max, cnt)`.
4. **Return `max`** as the length of the longest consecutive sequence.

---

# ⏱️ Complexity Analysis

* **Time Complexity:**

  * `O(n)` on average.
  * Each element is added to the set (`O(n)`), and each element is visited at most once in the inner loop across the whole run.
* **Space Complexity:**

  * `O(n)` for storing elements in the set.

---

# 📝 Code (Clean Version)

```java
import java.util.*;

class Solution {
    public int longestConsecutive(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;

        Set<Integer> set = new HashSet<>(n * 2);
        for (int x : nums) set.add(x);

        int max = 0;
        for (int x : set) {
            if (!set.contains(x - 1)) { // start of sequence
                int cur = x, cnt = 1;
                while (set.contains(cur + 1)) {
                    cur++;
                    cnt++;
                }
                max = Math.max(max, cnt);
            }
        }
        return max;
    }
}
```

---

# ⚖️ Why It’s Optimal

* **Sorting approach:** Another solution is sorting (`O(n log n)`) and scanning.
* **HashSet approach:** Achieves `O(n)` expected time, which is the theoretical best (every element must be looked at at least once).
* **Space tradeoff:** Uses extra memory `O(n)` for the set, but avoids sorting cost.

---

# 📊 Example Dry Run

Input: `[100, 4, 200, 1, 3, 2]`

* Build set → `{1, 2, 3, 4, 100, 200}`
* Iterate:

  * `100`: start? (`99` not in set) → length = 1
  * `4`: start? (`3` is in set) → skip
  * `200`: start? (`199` not in set) → length = 1
  * `1`: start? (`0` not in set) → walk: `1 → 2 → 3 → 4` → length = 4 → update `max = 4`
  * `3`: start? (`2` in set) → skip
  * `2`: start? (`1` in set) → skip
* Answer = **4**

---

# ⚡ Edge Cases

* Empty input: `[] → 0`
* Single element: `[7] → 1`
* Duplicates: `[1, 2, 2, 3] → 3` (sequence `[1,2,3]`)
* Negative numbers: `[-2, -1, 0, 1] → 4`

---

👉 So yes, your solution is **optimal**.

Link: https://leetcode.com/problems/longest-consecutive-sequence/
