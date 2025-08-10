Detailed notes for your **Two Sum** solution:

---

## **Problem**:

Given an integer array `nums` and an integer `target`, return **indices** of the two numbers such that they add up to `target`.
You may assume that each input has exactly **one solution**, and you may not use the same element twice.

---

## **Approach Used**:

**HashMap-based Single Pass** — **O(n)** time, **O(n)** space.

---

## **Logic**:

1. Use a `HashMap` to store numbers as keys and their indices as values.
2. For each element in `nums`:

   * Compute the **complement**: `target - current`.
   * If the complement is already in the map, we found the pair → return both indices.
   * Otherwise, add the current number and its index to the map.
3. If no pair is found, return an empty array.

---

## **Why it works efficiently**:

* **Lookup in HashMap** is **O(1)** average time.
* By checking the complement **before** adding the current number, we ensure we don’t use the same element twice.
* Only one pass is required.

---

## **Dry Run Example**:

`nums = [2, 7, 11, 15], target = 9`

| i | current | complement | Map before check | Found? | Map after insertion |
| - | ------- | ---------- | ---------------- | ------ | ------------------- |
| 0 | 2       | 7          | {}               | No     | {2=0}               |
| 1 | 7       | 2          | {2=0}            | Yes    | return \[0, 1]      |

---

## **Code Walkthrough**:

```java
class Solution {
    public static int[] twoSum(int[] nums, int target) {
        HashMap<Integer,Integer> h = new HashMap<>();
        
        for(int i = 0; i < nums.length; i++) {
            int current = nums[i];
            int complement = target - current;
            
            if(h.containsKey(complement)) {
                return new int[]{ h.get(complement), i };
            }
            
            h.put(current, i);
        }
        
        return new int[0]; // if no solution (problem guarantees one)
    }
}
```

---

## **Complexity**:

* **Time**: `O(n)` — single pass through array.
* **Space**: `O(n)` — HashMap stores at most `n` elements.

---

Link: https://leetcode.com/problems/two-sum/description/