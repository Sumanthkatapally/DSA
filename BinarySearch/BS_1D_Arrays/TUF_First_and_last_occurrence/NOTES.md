# Find First and Last Position of Element in Sorted Array

## Problem Statement
Given an array of integers `nums` sorted in **non-decreasing order**, find the starting and ending position of a given `target` value.

**Requirements:**
- If target is not found, return `[-1, -1]`
- Time complexity must be O(log n)

---

## Key Insights

### Why Simple Linear Search Won't Work
- Linear search: O(n) time complexity
- Problem specifically asks for O(log n) → Must use **Binary Search**

### The Challenge
- Standard binary search finds **any** occurrence
- We need **first** and **last** occurrence
- Solution: **Two modified binary searches**

---

## Algorithm Approach

### Core Strategy
1. **Find First Occurrence**: When target found, continue searching LEFT
2. **Find Last Occurrence**: When target found, continue searching RIGHT

### Visual Example
```
Array: [5, 7, 7, 8, 8, 8, 10]
Target: 8
Indices: 0  1  2  3  4  5   6

First occurrence at index 3
Last occurrence at index 5
Result: [3, 5]
```

---

## Implementation Details

### Modified Binary Search for First Occurrence

```java
public static int getFirstOccurrence(int[] nums, int target) {
    int left = 0, right = nums.length - 1, result = -1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            result = mid;           // Store potential answer
            right = mid - 1;        // Continue searching LEFT
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return result;
}
```

**Key Points:**
- When found: `result = mid` and `right = mid - 1`
- Continue searching in left half to find earlier occurrences

### Modified Binary Search for Last Occurrence

```java
public static int getLastOccurrence(int[] nums, int target) {
    int left = 0, right = nums.length - 1, result = -1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            result = mid;           // Store potential answer
            left = mid + 1;         // Continue searching RIGHT
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return result;
}
```

**Key Points:**
- When found: `result = mid` and `left = mid + 1`
- Continue searching in right half to find later occurrences

---

## Step-by-Step Trace

### Example: nums = [5,7,7,8,8,10], target = 8

#### Finding First Occurrence:
```
Initial: left=0, right=5, result=-1
Step 1: mid=2, nums[2]=7 < 8 → left=3
Step 2: mid=4, nums[4]=8 == 8 → result=4, right=3
Step 3: mid=3, nums[3]=8 == 8 → result=3, right=2
Step 4: left=3 > right=2 → STOP
First occurrence: 3
```

#### Finding Last Occurrence:
```
Initial: left=0, right=5, result=-1
Step 1: mid=2, nums[2]=7 < 8 → left=3
Step 2: mid=4, nums[4]=8 == 8 → result=4, left=5
Step 3: mid=5, nums[5]=10 > 8 → right=4
Step 4: left=5 > right=4 → STOP
Last occurrence: 4
```

**Final Result: [3, 4]**

---

## Edge Cases and Handling

### 1. Target Not Found
```java
nums = [1,2,3,5,6], target = 4
// Both functions return -1
// Result: [-1, -1]
```

### 2. Empty Array
```java
nums = [], target = 5
// Both functions return -1
// Result: [-1, -1]
```

### 3. Single Element Array
```java
// Found
nums = [5], target = 5 → [0, 0]
// Not found  
nums = [5], target = 3 → [-1, -1]
```

### 4. Target at Boundaries
```java
// At start
nums = [2,2,3,4], target = 2 → [0, 1]
// At end
nums = [1,2,4,4], target = 4 → [2, 3]
```

### 5. All Elements Same
```java
nums = [3,3,3,3], target = 3 → [0, 3]
```

---

## Common Mistakes to Avoid

### ❌ Wrong Loop Condition
```java
while (left < right)  // WRONG - might miss last element
```
**Correct:** `while (left <= right)`

### ❌ Not Storing Result
```java
if (nums[mid] == target) {
    right = mid - 1;  // WRONG - lost the found index
}
```
**Correct:** Store result first, then continue searching

### ❌ Integer Overflow
```java
int mid = (left + right) / 2;  // Can overflow
```
**Correct:** `int mid = left + (right - left) / 2;`

---

## Complexity Analysis

### Time Complexity: **O(log n)**
- Two binary searches
- Each binary search: O(log n)
- Total: O(log n) + O(log n) = O(log n)

### Space Complexity: **O(1)**
- Only using constant extra variables
- No recursion stack (iterative approach)

---

## Alternative Approaches

### 1. Linear Search (Not Optimal)
```java
// Time: O(n), Space: O(1)
for (int i = 0; i < nums.length; i++) {
    if (nums[i] == target) {
        first = i;
        break;
    }
}
```

### 2. Built-in Binary Search + Expansion
```java
// Use Arrays.binarySearch() then expand left/right
// Still O(n) worst case for expansion
```

### 3. Single Binary Search with Range Finding
```java
// Find any occurrence, then expand
// Worst case: O(n) when all elements are target
```

---

## Practice Variations

### Related Problems
1. **Find Peak Element** - Similar binary search modifications
2. **Search in Rotated Array** - Combined with rotation logic
3. **Find Minimum in Rotated Array** - Modified binary search conditions

### Extension Questions
1. What if array allows duplicates in different pattern?
2. How to find Kth occurrence?
3. What about 2D sorted matrix?

---

## Interview Tips

### What Interviewers Look For
1. **Recognition** that O(log n) means binary search
2. **Understanding** of how to modify binary search
3. **Edge case handling** - empty arrays, not found, etc.
4. **Code clarity** - clean, readable implementation

### Common Follow-ups
- "What if the array is very large and doesn't fit in memory?"
- "Can you do this with only one pass through the array?"
- "How would you handle duplicate targets with different values?"

### Key Points to Mention
- Time complexity reasoning
- Why two separate binary searches
- Edge case considerations
- Space optimization (iterative vs recursive)