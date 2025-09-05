# Floor and Ceil in Sorted Array - Detailed Notes

## 📝 Problem Statement

**Given:** A sorted array `nums` and an integer `x`

**Find:** The floor and ceiling of `x` in the array

**Definitions:**
- **Floor of x**: Largest element ≤ x
- **Ceiling of x**: Smallest element ≥ x
- If no floor or ceiling exists, return -1

**Return Format:** `[floor, ceiling]`

---

## 🎯 Examples & Understanding

### Example 1
```
Input: nums = [3, 4, 4, 7, 8, 10], x = 5
Output: [4, 7]

Visualization:
[3, 4, 4, 7, 8, 10]
         ↑     ↑
      floor   ceil
      (≤5)    (≥5)
```

### Example 2
```
Input: nums = [3, 4, 4, 7, 8, 10], x = 8
Output: [8, 8]

When x exists in array, both floor and ceil are x itself
```

### Example 3 (Edge Cases)
```
Input: nums = [3, 4, 7, 8], x = 1
Output: [-1, 3]
(No floor exists, ceiling is 3)

Input: nums = [3, 4, 7, 8], x = 10
Output: [8, -1]
(Floor is 8, no ceiling exists)

Input: nums = [], x = 5
Output: [-1, -1]
(Empty array case)
```

---

## 🧠 Approach Analysis

### ❌ Brute Force Approach
```java
// O(n) Time, O(1) Space
public int[] bruteForce(int[] nums, int x) {
    int floor = -1, ceil = -1;
    
    // Find floor: scan from left, keep updating largest ≤ x
    for (int num : nums) {
        if (num <= x) floor = num;
        else break;
    }
    
    // Find ceil: scan from left, find first ≥ x
    for (int num : nums) {
        if (num >= x) {
            ceil = num;
            break;
        }
    }
    
    return new int[]{floor, ceil};
}
```

### ✅ Optimal Approach: Binary Search
```java
// O(log n) Time, O(1) Space - OPTIMAL
```

---

## 🔍 Binary Search Solution

### Core Algorithm
```java
public static int[] getFloorAndCeil(int[] nums, int x) {
    if (nums == null || nums.length == 0) {
        return new int[]{-1, -1};
    }
    
    int left = 0, right = nums.length - 1;
    int floor = -1, ceil = -1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2; // Overflow-safe
        
        if (nums[mid] == x) {
            return new int[]{x, x}; // Found exact match
        } 
        else if (nums[mid] < x) {
            floor = nums[mid];      // Potential floor found
            left = mid + 1;         // Search right half
        } 
        else {
            ceil = nums[mid];       // Potential ceiling found
            right = mid - 1;        // Search left half
        }
    }
    
    return new int[]{floor, ceil};
}
```

### 🎯 Algorithm Walkthrough

**Step-by-step for nums = [3, 4, 4, 7, 8, 10], x = 5:**

```
Iteration 1: left=0, right=5, mid=2
nums[2] = 4 < 5
→ floor = 4, search right half
→ left = 3

Iteration 2: left=3, right=5, mid=4  
nums[4] = 8 > 5
→ ceil = 8, search left half
→ right = 3

Iteration 3: left=3, right=3, mid=3
nums[3] = 7 > 5  
→ ceil = 7, search left half
→ right = 2

Loop ends (left > right)
Return [4, 7]
```

---

## 🔄 Two Separate Binary Searches (Alternative)

```java
public int[] getFloorAndCeilSeparate(int[] nums, int x) {
    return new int[]{findFloor(nums, x), findCeil(nums, x)};
}

private int findFloor(int[] nums, int x) {
    int left = 0, right = nums.length - 1;
    int floor = -1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] <= x) {
            floor = nums[mid];
            left = mid + 1; // Look for larger floor
        } else {
            right = mid - 1;
        }
    }
    return floor;
}

private int findCeil(int[] nums, int x) {
    int left = 0, right = nums.length - 1;
    int ceil = -1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] >= x) {
            ceil = nums[mid];
            right = mid - 1; // Look for smaller ceiling
        } else {
            left = mid + 1;
        }
    }
    return ceil;
}
```

---

## 🧪 Edge Cases & Test Cases

### Critical Edge Cases
1. **Empty array**: `nums = [], x = 5` → `[-1, -1]`
2. **Single element - match**: `nums = [5], x = 5` → `[5, 5]`
3. **Single element - no match**: `nums = [3], x = 5` → `[3, -1]`
4. **All elements smaller**: `nums = [1, 2, 3], x = 5` → `[3, -1]`
5. **All elements larger**: `nums = [7, 8, 9], x = 5` → `[-1, 7]`
6. **Duplicate elements**: `nums = [4, 4, 4], x = 4` → `[4, 4]`

### Comprehensive Test Suite
```java
public static void runTests() {
    // Basic cases
    test([3, 4, 4, 7, 8, 10], 5, [4, 7]);
    test([3, 4, 4, 7, 8, 10], 8, [8, 8]);
    test([3, 4, 4, 7, 8, 10], 4, [4, 4]);
    
    // Edge cases
    test([], 5, [-1, -1]);
    test([5], 5, [5, 5]);
    test([3], 5, [3, -1]);
    test([7], 5, [-1, 7]);
    test([1, 2, 3], 5, [3, -1]);
    test([7, 8, 9], 5, [-1, 7]);
    
    // Boundary cases
    test([1, 3, 8, 10, 15], 1, [1, 1]);  // First element
    test([1, 3, 8, 10, 15], 15, [15, 15]); // Last element
    test([1, 3, 8, 10, 15], 0, [-1, 1]);   // Before first
    test([1, 3, 8, 10, 15], 20, [15, -1]);  // After last
}
```

---

## 📊 Complexity Analysis

| Approach | Time | Space | Notes |
|----------|------|-------|-------|
| **Brute Force** | O(n) | O(1) | Linear scan |
| **Binary Search (Single)** | O(log n) | O(1) | **Optimal** |
| **Binary Search (Separate)** | O(log n) | O(1) | 2 separate searches |

### Why Binary Search?
- **Sorted array** → Perfect for binary search
- **Divide and conquer** reduces search space by half each iteration
- **Logarithmic time** vs linear time for large arrays

---

## 🐛 Common Pitfalls & Debugging

### 1. Integer Overflow
```java
// ❌ Problematic
int mid = (left + right) / 2;

// ✅ Safe
int mid = left + (right - left) / 2;
```

### 2. Infinite Loops
```java
// Make sure to update left/right in each iteration
if (nums[mid] < x) {
    left = mid + 1;  // ✅ Move boundary
    // left = mid;   // ❌ Infinite loop risk
}
```

### 3. Off-by-One Errors
```java
// Use <= not < in while condition
while (left <= right) { // ✅ Correct
// while (left < right) { // ❌ May miss elements
```

### 4. Return Value Mix-up
```java
// Floor first, then ceiling
return new int[]{floor, ceil}; // ✅ Correct order
// return new int[]{ceil, floor}; // ❌ Wrong order
```

---

## 🎯 Key Insights & Patterns

### Pattern Recognition
This problem combines:
- **Binary Search** on sorted arrays
- **Lower Bound** (ceiling) - first element ≥ x
- **Upper Bound** (floor) - last element ≤ x

### Related Problems
1. **Search Insert Position** (LeetCode 35)
2. **First and Last Position** (LeetCode 34) 
3. **Lower Bound / Upper Bound** implementations
4. **Binary Search variations**

### Memory Aids
- **Floor** → "**F**alls below or equal" (≤)
- **Ceiling** → "**C**limbs above or equal" (≥)
- When `nums[mid] < x` → potential floor, search **right**
- When `nums[mid] > x` → potential ceiling, search **left**

---

## 🚀 Optimization Notes

### Space Optimizations
- Current solution is already O(1) space
- Could use iterative vs recursive (already done)

### Time Optimizations  
- Single pass binary search (current approach) vs two separate searches
- Early termination when exact match found

### Code Readability
```java
// More descriptive variable names
int leftBoundary = 0, rightBoundary = nums.length - 1;
int floorCandidate = -1, ceilingCandidate = -1;
```

---

## 📚 Interview Tips

### What Interviewers Look For
1. **Recognition** of binary search opportunity (sorted array)
2. **Correct implementation** of binary search template  
3. **Edge case handling** (empty array, no floor/ceil exists)
4. **Optimization awareness** (overflow-safe mid calculation)
5. **Clear explanation** of algorithm logic

### Common Follow-ups
- "What if array has duplicates?" (Already handled)
- "Can you do this in O(1) space?" (Yes, current solution)
- "What if we need to find floor/ceil of multiple queries?" (Preprocess or use each query independently)
- "How would you handle a very large array?" (Same algorithm, mention overflow safety)

### Code Interview Strategy
1. **Clarify** problem requirements and edge cases
2. **Discuss** brute force first, then optimize
3. **Code** the binary search solution
4. **Test** with provided examples and edge cases
5. **Analyze** time/space complexity