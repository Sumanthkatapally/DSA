Search X in a Sorted Array
---

## 🔹 Problem Statement

You are given:

* A sorted array `nums` of size `n`.
* A target value `X` (here `target`).
* You must determine whether `target` exists in the array, and if so, return its index; otherwise return `-1`.

---

## 🔹 Why Binary Search?

* **Linear Search** would take `O(n)` time (checking elements one by one).
* Since the array is **sorted**, we can use **Binary Search** to reduce time complexity.
* Binary Search eliminates half of the search space in each step.

---

## 🔹 Approach Explanation

1. Initialize two pointers:

   * `f = 0` (first index)
   * `l = nums.length - 1` (last index)

2. While `f <= l`:

   * Compute middle index:
     `mid = (f + l) / 2`
   * Compare:

     * If `nums[mid] == target` → return `mid`.
     * If `nums[mid] > target` → search left half (`l = mid - 1`).
     * If `nums[mid] < target` → search right half (`f = mid + 1`).

3. If not found → return `-1`.

---

## 🔹 Code Walkthrough (your solution)

```java
public class Solution {
    public static int search(int[] nums, int target) {
        int f = 0, l = nums.length - 1;
        while (f <= l) {
            int mid = (f + l) / 2;   // middle element
            if (nums[mid] == target) 
                return mid;          // found
            else if (nums[mid] > target)
                l = mid - 1;         // search left
            else
                f = mid + 1;         // search right
        }
        return -1;                   // not found
    }
}
```

✅ Correct
✅ Efficient
✅ Optimal

---

## 🔹 Time & Space Complexity

* **Time Complexity:** `O(log n)`

  * Because the array size halves each iteration.
* **Space Complexity:** `O(1)`

  * No extra space used apart from a few variables.

---

## 🔹 Edge Cases

1. Empty array (`nums.length == 0`) → return `-1`.
2. Target smaller than all elements → should return `-1`.
3. Target greater than all elements → should return `-1`.
4. Target at the beginning or end of the array → works correctly.

---

## 🔹 Why is it Optimal?

* You cannot do better than `O(log n)` for searching in a sorted array without extra preprocessing (like hash maps or balanced BSTs).
* Your solution matches the **optimal theoretical complexity** for this problem.

---

✨ **Summary:**
The solution is the **optimal Binary Search implementation** for searching an element in a sorted array. It achieves `O(log n)` time and `O(1)` space, which is the best possible.

---

Link: https://takeuforward.org/plus/dsa/problems/search-x-in-sorted-array
