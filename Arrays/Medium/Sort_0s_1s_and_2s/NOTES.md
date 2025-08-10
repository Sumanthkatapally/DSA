Detailed notes for your **sort012** solution:

---

## **Problem**:

Given an array `nums` containing only `0`, `1`, and `2`, sort it in-place in ascending order.
This is also called the **Dutch National Flag Problem**.

---

## **Approach Used**:

**Three-Pointer / Dutch National Flag Algorithm** — **O(n)** time, **O(1)** space.

---

## **Pointer Meaning**:

* `low` → Boundary for **0’s** (all indices before `low` are 0).
* `mid` → Current element being inspected.
* `high` → Boundary for **2’s** (all indices after `high` are 2).

---

## **Logic**:

1. Start with `low = 0`, `mid = 0`, `high = nums.length - 1`.
2. Traverse until `mid <= high`:

   * **If `nums[mid] == 0`**
     → Swap `nums[mid]` and `nums[low]`.
     → Increment both `low` and `mid` (because swapped element at `mid` is already processed).
   * **If `nums[mid] == 1`**
     → `mid++` (no swap needed, 1’s stay in the middle).
   * **If `nums[mid] == 2`**
     → Swap `nums[mid]` and `nums[high]`.
     → Decrement `high` (don’t increment `mid` immediately because the swapped value needs to be checked).

---

## **Dry Run Example**:

`nums = [2, 0, 2, 1, 1, 0]`

| low | mid | high | nums                | Action                                             |
| --- | --- | ---- | ------------------- | -------------------------------------------------- |
| 0   | 0   | 5    | \[2, 0, 2, 1, 1, 0] | swap mid & high → \[0, 0, 2, 1, 1, 2], high--      |
| 0   | 0   | 4    | \[0, 0, 2, 1, 1, 2] | swap mid & low → \[0, 0, 2, 1, 1, 2], low++, mid++ |
| 1   | 1   | 4    | \[0, 0, 2, 1, 1, 2] | swap mid & low → \[0, 0, 2, 1, 1, 2], low++, mid++ |
| 2   | 2   | 4    | \[0, 0, 2, 1, 1, 2] | swap mid & high → \[0, 0, 1, 1, 2, 2], high--      |
| 2   | 2   | 3    | \[0, 0, 1, 1, 2, 2] | mid++                                              |
| 2   | 3   | 3    | \[0, 0, 1, 1, 2, 2] | mid++                                              |

---

## **Code Explanation**:

```java
if (nums[mid] == 1) {
    mid++; // 1’s are already in the right place
}
else if (nums[mid] == 0) {
    swap(nums, mid, low);
    low++;
    mid++;
}
else { // nums[mid] == 2
    swap(nums, mid, high);
    high--;
}
```

---

## **Complexity**:

* **Time**: `O(n)` — Each element is processed at most once.
* **Space**: `O(1)` — In-place swapping, no extra data structures.

---
Link: https://www.geeksforgeeks.org/problems/sort-an-array-of-0s-1s-and-2s4231/1