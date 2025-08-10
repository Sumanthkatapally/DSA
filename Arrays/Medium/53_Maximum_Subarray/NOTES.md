**LeetCode 53 – Maximum Subarray** using your Kadane’s algorithm code, plus the **prefix sum version** for tracking indices.

---

## **LeetCode 53 – Maximum Subarray**

### Problem

Given an integer array `nums`, find the **contiguous subarray** (containing at least one number) which has the **largest sum** and return its sum.

---

## **I. Kadane’s Algorithm (Sum Only)**

### **Core Idea**

* Move through the array once, **accumulating sum** of the current subarray.
* If the running sum becomes **negative**, reset it to zero (start fresh).
* Always keep track of the **max sum seen so far**.

### **Algorithm Steps**

1. **Initialize**

   * `maxsum = Integer.MIN_VALUE` → ensures we handle all-negative arrays correctly.
   * `cursum = 0` → running sum of the current subarray.
2. **Iterate over nums**:

   * Add current element to `cursum`.
   * Update `maxsum = Math.max(maxsum, cursum)`.
   * If `cursum < 0`, reset `cursum = 0`.
3. **Return** `maxsum`.

### **Why It Works**

* If the running sum is negative, keeping it will only make any future sum smaller.
* The reset allows us to start a new subarray from the next element.

### **Time & Space Complexity**

* **Time:** O(n) → one pass over the array.
* **Space:** O(1) → uses only constant extra space.

---

## **II. Kadane’s Algorithm with Indices (Prefix Sum Variant)**

**Why Use This?**

* Sometimes interviewers also ask for the **subarray itself**, not just the sum.

### **Modified Approach**

* Keep track of:

  * `start` → start index of current subarray.
  * `end` → end index of best subarray found.
  * `tempStart` → tentative start index (when we reset the sum).

### **Pseudocode**

```java
public int[] maxSubArrayWithIndices(int[] nums) {
    int maxsum = Integer.MIN_VALUE;
    int cursum = 0;
    int start = 0, end = 0, tempStart = 0;

    for (int i = 0; i < nums.length; i++) {
        cursum += nums[i];

        if (cursum > maxsum) {
            maxsum = cursum;
            start = tempStart;
            end = i;
        }

        if (cursum < 0) {
            cursum = 0;
            tempStart = i + 1;
        }
    }

    // maxsum is the sum, [start, end] is the range
    return new int[]{maxsum, start, end};
}
```

---

## **III. Example Walkthrough**

**Input:**

```
nums = [-2,1,-3,4,-1,2,1,-5,4]
```

**Process:**

```
i    nums[i]   cursum   maxsum   start   end
0    -2        -2       -2       0       0   (reset cursum)
1     1         1        1       1       1
2    -3        -2        1       1       1   (reset cursum)
3     4         4        4       3       3
4    -1         3        4       3       3
5     2         5        5       3       5
6     1         6        6       3       6
7    -5         1        6       3       6
8     4         5        6       3       6
```

**Answer:**
Max sum = `6`
Subarray = `[4, -1, 2, 1]` (indices 3 to 6)

---

## **IV. Common Pitfalls**

1. **All-negative arrays**

   * If you reset to `0` at the start without initializing `maxsum` to a very small number, you'll get wrong results.
   * Example: `[-3, -1, -2]` → Answer should be `-1`, not `0`.

2. **Forgetting index tracking** when the problem asks for the subarray itself.

3. **Confusing Kadane’s with prefix sums**

   * Kadane’s is essentially a rolling prefix sum, but with resets when the sum is negative.

---

## **V. When to Use**

* **Maximum sum contiguous subarray** problems.
* Can be extended to:

  * **2D Maximum Subarray** (applied row-wise with column compression).
  * **Circular Array** version (Kadane’s twice: once normal, once for min subarray).

---

Link: https://leetcode.com/problems/maximum-subarray/description/
