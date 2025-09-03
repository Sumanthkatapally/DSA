**Lower Bound** in a sorted array.
---

## 🔹 Problem Statement

* You are given a **sorted array** `nums` and a value `x` (here called `target`).
* You must find the **Lower Bound** of `x`.

**Definition (Lower Bound):**
The **smallest index `i`** such that `nums[i] >= x`.

* If such an index exists → return `i`.
* If not → return `nums.length`.

---

## 🔹 Example Walkthrough

### Example 1

```java
nums = [1, 2, 4, 4, 7], target = 4
```

* `nums[0] = 1 < 4`
* `nums[1] = 2 < 4`
* `nums[2] = 4 >= 4` ✅ (first index where value ≥ target)
* Answer = **2**

### Example 2

```java
nums = [1, 2, 4, 4, 7], target = 5
```

* First element ≥ 5 is `7` at index 4
* Answer = **4**

### Example 3

```java
nums = [1, 2, 4, 4, 7], target = 8
```

* No element ≥ 8
* Answer = `nums.length = 5`

---

## 🔹 Your Code Walkthrough

```java
public static int search(int[] nums, int target) {
    int f = 0, l = nums.length - 1, ans = nums.length;
    while (f <= l) {
        int mid = (f + l) / 2;
        if (nums[mid] >= target) {
            ans = mid;   // potential answer
            l = mid - 1; // move left to find smaller index
        } else {
            f = mid + 1; // move right
        }
    }
    return ans;
}
```

✅ Correct
✅ Efficient
✅ Optimal

---

## 🔹 Why It Works

* You are using **Binary Search** with a twist:

  * Instead of stopping when you "find" the target,
    you **keep track of the earliest index** where `nums[mid] >= target`.
* If such an index exists, it will be stored in `ans`.
* Otherwise, `ans` stays equal to `nums.length`.

---

## 🔹 Time & Space Complexity

* **Time Complexity:** `O(log n)`

  * Because the search space halves in each iteration.
* **Space Complexity:** `O(1)`

  * Only a few variables used.

---

## 🔹 Edge Cases to Test

1. Empty array → should return `0` (since `nums.length = 0`).
2. Target smaller than all elements.
   Example: `nums = [5, 6, 7], target = 2` → answer = `0`.
3. Target greater than all elements.
   Example: `nums = [5, 6, 7], target = 10` → answer = `3`.
4. Multiple equal elements.
   Example: `nums = [2, 2, 2, 2], target = 2` → answer = `0`.

---

## 🔹 Why Is This Optimal?

* The **best possible algorithm** for this problem is **Binary Search** (`O(log n)`).
* A linear scan would take `O(n)`, which is slower.
* Your solution achieves the optimal bound with no extra memory.

---

✨ **Summary:**
The solution is **optimal** for finding the **Lower Bound**. It runs in `O(log n)` and uses `O(1)` space. The key idea is storing `ans` whenever `nums[mid] >= target` and continuing left to find the smallest valid index.

---

Link: https://takeuforward.org/plus/dsa/problems/lower-bound-