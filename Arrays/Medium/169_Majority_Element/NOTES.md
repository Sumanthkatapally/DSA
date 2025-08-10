## **1) Boyer–Moore Voting Algorithm**

**Time Complexity:** O(n) **Space Complexity:** O(1)

### **Intuition**

If an element occurs more than `n/2` times, it will outnumber all other elements combined.
If you cancel out different elements in pairs, the majority element will still be left in the end.

### **Steps**

1. Initialize `candidate = 0`, `count = 0`.
2. Traverse array:

   * If `count == 0`, set `candidate = current element`.
   * If `current == candidate` → `count++`, else `count--`.
3. After loop, `candidate` will hold the majority element (if guaranteed to exist).
4. If not guaranteed, run a second pass to count occurrences and confirm.

### **Example**

Input: `[2, 2, 1, 1, 1, 2, 2]`

* Pick `2` → count=1
* See another `2` → count=2
* See `1` → count=1
* See `1` → count=0 (reset)
* Pick `1` → count=1
* See `2` → count=0 (reset)
* Pick `2` → count=1 → candidate=2 (final answer)

### **Pros**

* Best possible time & space.
* Very elegant.

### **Cons**

* Needs second pass if majority is not guaranteed.

---

## **2) HashMap Counting**

**Time Complexity:** O(n) **Space Complexity:** O(n)

### **Intuition**

Use a hash map to store frequency counts for each element. If a count exceeds `n/2`, that’s the majority.

### **Steps**

1. Create an empty `HashMap<Integer, Integer>`.
2. For each element:

   * Increment its count in the map.
   * If count > n/2 → return that element.
3. If no majority found, return -1 (if problem doesn’t guarantee one).

### **Example**

Input: `[3, 3, 4, 2, 3, 3, 3]`

* Map after processing: `{3=5, 4=1, 2=1}`
* `3` occurs 5 times (> 7/2 = 3.5) → majority is 3.

### **Pros**

* Easy to implement.
* Works when no guarantee of majority element.

### **Cons**

* Extra O(n) space.
* Slightly slower in practice than Boyer–Moore.

---

## **3) Sorting + Middle Element**

**Time Complexity:** O(n log n) **Space Complexity:** O(1) (if in-place sort)

### **Intuition**

If an element appears more than n/2 times, it will always occupy the middle position after sorting.

### **Steps**

1. Sort the array.
2. Return `nums[n/2]`.

### **Example**

Input: `[2, 2, 1, 1, 1, 2, 2]`

* Sorted: `[1, 1, 1, 2, 2, 2, 2]`
* n/2 = 3 → nums\[3] = `2` → majority is 2.

### **Pros**

* Extremely simple code (2 lines).
* No need for extra data structures.

### **Cons**

* Slower than O(n) solutions.
* Sorting is unnecessary overhead if we only need the majority.

---

✅ **Summary Table**

| Approach                 | Time       | Space | Guaranteed Majority? | Notes            |
| ------------------------ | ---------- | ----- | -------------------- | ---------------- |
| Boyer–Moore Voting       | O(n)       | O(1)  | Yes (else verify)    | Optimal, elegant |
| HashMap Counting         | O(n)       | O(n)  | No                   | Simple, clear    |
| Sorting + Middle Element | O(n log n) | O(1)  | Yes                  | Shortest code    |

---

Link: https://leetcode.com/problems/majority-element/