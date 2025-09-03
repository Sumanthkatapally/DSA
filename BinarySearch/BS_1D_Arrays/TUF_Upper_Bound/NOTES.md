**Upper Bound** of a number in a sorted array.

---

## 🔹 Problem Statement

You are given:

* A sorted array `nums` of size `n`
* An integer `x` (called `target` here)

You need to return the **Upper Bound** of `x`.

**Definition (Upper Bound):**
The smallest index `i` such that:

```
nums[i] > x
```

If no such index exists → return `n` (the size of the array).

---

## 🔹 Example Walkthrough

### Example 1

```java
nums = [1, 2, 2, 3], target = 2
```

* `nums[0] = 1` (≤ 2)
* `nums[1] = 2` (≤ 2)
* `nums[2] = 2` (≤ 2)
* `nums[3] = 3 > 2` ✅ (first index > target)
* Answer = **3**

### Example 2

```java
nums = [1, 2, 4, 6], target = 4
```

* First index > 4 is index 3 (`nums[3] = 6`)
* Answer = **3**

### Example 3

```java
nums = [2, 4, 6, 8], target = 10
```

* No element > 10
* Answer = `n = 4`

---

## 🔹 Your Code Walkthrough

```java
public static int search(int[] nums, int target) {
    int f = 0, l = nums.length - 1, ans = nums.length;
    while (f <= l) {
        int mid = (f + l) / 2;
        if (nums[mid] > target) {
            ans = mid;    // potential candidate
            l = mid - 1;  // look left to find smaller index
        } else {
            f = mid + 1;  // move right if nums[mid] <= target
        }
    }
    return ans;
}
```

### ✅ How It Works

* Keep track of `ans` whenever `nums[mid] > target`
* Continue moving **left** to find the first such index
* If no `nums[mid] > target`, return `n`

---

## 🔹 Time & Space Complexity

* **Time Complexity:** `O(log n)`

  * Binary Search halves the search space each iteration
* **Space Complexity:** `O(1)`

  * Only a few extra variables used

---

## 🔹 Edge Cases to Test

1. **Empty array** → return `0`

   ```java
   nums = [], target = 5 → output = 0
   ```
2. **Target smaller than all elements**

   ```java
   nums = [3, 4, 5], target = 1 → output = 0
   ```
3. **Target greater than all elements**

   ```java
   nums = [1, 2, 3], target = 5 → output = 3
   ```
4. **All elements equal to target**

   ```java
   nums = [2, 2, 2, 2], target = 2 → output = 4
   ```
5. **Target exists multiple times**

   ```java
   nums = [1, 2, 2, 3], target = 2 → output = 3
   ```

---

## 🔹 Why Is This Optimal?

* A naive **linear scan** would take `O(n)` time
* Using **Binary Search**, you reduce it to `O(log n)` which is the **best possible** for sorted arrays
* Uses constant space → **O(1)**

---

✨ **Summary:**
The solution is **optimal**. It uses **Binary Search** to find the **first index where `nums[i] > target`** in `O(log n)` time and `O(1)` space. If no such index exists, it correctly returns `nums.length`.

---
Link: https://takeuforward.org/plus/dsa/problems/upper-bound
