# Count Occurrences in Sorted Array

## Problem Statement
Given a **sorted array** of integers `arr` and an integer `target`, determine how many times `target` appears in the array.

**Input:** Sorted array + target value  
**Output:** Count of occurrences (integer)  
**Constraint:** Must be efficient for large arrays

---

## Key Insights

### Why This Problem is Special
1. **Array is sorted** → We can use binary search for O(log n) solution
2. **Count = Last Index - First Index + 1**
3. **Critical edge case**: Target not found → Count should be 0

### Naive vs Optimal Approach
```java
// Naive: O(n) - Linear scan
for (int i = 0; i < arr.length; i++) {
    if (arr[i] == target) count++;
}

// Optimal: O(log n) - Binary search for first/last positions
count = lastPos - firstPos + 1;
```

---

## Algorithm Strategy

### Core Concept: Two Binary Searches
1. **Find First Occurrence** → Leftmost position of target
2. **Find Last Occurrence** → Rightmost position of target
3. **Calculate Count** → `lastPos - firstPos + 1`

### Visual Example
```
Array: [1, 2, 3, 3, 3, 3, 4, 5]
Target: 3
Indices: 0  1  2  3  4  5  6  7

First occurrence: index 2
Last occurrence:  index 5
Count: 5 - 2 + 1 = 4 occurrences
```

---

## Implementation Breakdown

### Step 1: Find First Occurrence
```java
public static int getFirstOccurrence(int[] nums, int target) {
    int left = 0, right = nums.length - 1, result = -1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            result = mid;        // Store potential answer
            right = mid - 1;     // Keep searching LEFT for earlier occurrence
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return result; // Returns -1 if not found
}
```

**Key Points:**
- When found: Save index and continue searching left
- `right = mid - 1` ensures we find the leftmost occurrence

### Step 2: Find Last Occurrence
```java
public static int getLastOccurrence(int[] nums, int target) {
    int left = 0, right = nums.length - 1, result = -1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            result = mid;        // Store potential answer
            left = mid + 1;      // Keep searching RIGHT for later occurrence
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return result; // Returns -1 if not found
}
```

**Key Points:**
- When found: Save index and continue searching right
- `left = mid + 1` ensures we find the rightmost occurrence

### Step 3: Calculate Count (CRITICAL PART)
```java
public static int countOccurrences(int[] nums, int target) {
    int first = getFirstOccurrence(nums, target);
    int last = getLastOccurrence(nums, target);
    
    // CRITICAL: Handle "not found" case
    if (first == -1) {
        return 0; // Target not in array
    }
    
    return last - first + 1; // Count = range size
}
```

**🚨 Common Bug:** Forgetting the `first == -1` check!
- Without check: `-1 - (-1) + 1 = 1` (wrong!)
- With check: Returns `0` correctly when not found

---

## Step-by-Step Trace

### Example: arr = [2, 4, 4, 4, 6, 8], target = 4

#### Finding First Occurrence:
```
Initial: left=0, right=5, result=-1, array=[2,4,4,4,6,8]

Step 1: mid=2, nums[2]=4 == 4
        result=2, right=1 (search left for earlier)

Step 2: mid=0, nums[0]=2 < 4
        left=1

Step 3: mid=1, nums[1]=4 == 4
        result=1, right=0 (search left for earlier)

Step 4: left=1 > right=0 → STOP
First occurrence: 1
```

#### Finding Last Occurrence:
```
Initial: left=0, right=5, result=-1, array=[2,4,4,4,6,8]

Step 1: mid=2, nums[2]=4 == 4
        result=2, left=3 (search right for later)

Step 2: mid=4, nums[4]=6 > 4
        right=3

Step 3: mid=3, nums[3]=4 == 4
        result=3, left=4 (search right for later)

Step 4: left=4 > right=3 → STOP
Last occurrence: 3
```

#### Final Calculation:
```
first = 1, last = 3
first != -1, so target exists
Count = 3 - 1 + 1 = 3 ✓
```

---

## Edge Cases Analysis

### 1. Target Not Found
```java
arr = [1, 3, 5, 7], target = 4
first = -1, last = -1
Result: 0 (correct)

// Without the check:
// -1 - (-1) + 1 = 1 (WRONG!)
```

### 2. Empty Array
```java
arr = [], target = 5
first = -1, last = -1
Result: 0 (correct)
```

### 3. Single Element - Found
```java
arr = [5], target = 5
first = 0, last = 0
Count = 0 - 0 + 1 = 1 (correct)
```

### 4. Single Element - Not Found
```java
arr = [5], target = 3
first = -1, last = -1
Result: 0 (correct)
```

### 5. All Elements Same
```java
arr = [7, 7, 7, 7], target = 7
first = 0, last = 3
Count = 3 - 0 + 1 = 4 (correct)
```

### 6. Target at Boundaries
```java
// At start
arr = [2, 2, 5, 6], target = 2
first = 0, last = 1
Count = 1 - 0 + 1 = 2

// At end
arr = [1, 2, 8, 8], target = 8
first = 2, last = 3
Count = 3 - 2 + 1 = 2
```

### 7. Single Occurrence
```java
arr = [1, 2, 3, 4, 5], target = 3
first = 2, last = 2
Count = 2 - 2 + 1 = 1 (correct)
```

---

## Common Mistakes and Debugging

### ❌ Mistake 1: Wrong Loop Condition
```java
while (left < right) // WRONG - misses last element
```
**Fix:** `while (left <= right)`

### ❌ Mistake 2: Forgetting to Store Result
```java
if (nums[mid] == target) {
    right = mid - 1; // WRONG - lost the index!
}
```
**Fix:** Always store before continuing search

### ❌ Mistake 3: The Critical Bug - Missing "Not Found" Check
```java
return last - first + 1; // WRONG when both are -1
```
**Fix:** Check `if (first == -1) return 0;`

### ❌ Mistake 4: Integer Overflow
```java
int mid = (left + right) / 2; // Can overflow with large arrays
```
**Fix:** `int mid = left + (right - left) / 2;`

### ❌ Mistake 5: Wrong Search Direction
```java
// In getFirstOccurrence
if (nums[mid] == target) {
    left = mid + 1; // WRONG - should search left!
}
```

---

## Complexity Analysis

### Time Complexity: **O(log n)**
- First binary search: O(log n)
- Last binary search: O(log n)  
- Total: O(log n) + O(log n) = O(log n)

### Space Complexity: **O(1)**
- Only using constant extra variables
- No recursion (iterative approach)
- No additional data structures

### Why This Beats Linear Search
```
For array size n = 1,000,000:
- Linear search: 1,000,000 operations worst case
- Binary search: ~20 operations worst case
- Speedup: 50,000x faster!
```

---

## Alternative Approaches

### Approach 1: Linear Scan (Not Optimal)
```java
// Time: O(n), Space: O(1)
int count = 0;
for (int num : arr) {
    if (num == target) count++;
    else if (num > target) break; // Early termination
}
```
**Pros:** Simple to understand  
**Cons:** O(n) time complexity

### Approach 2: Single Binary Search + Linear Expansion
```java
// Find any occurrence, then expand left and right
int pos = Arrays.binarySearch(arr, target);
if (pos < 0) return 0;
// Expand left and right from pos
```
**Pros:** Uses built-in binary search  
**Cons:** Still O(n) in worst case (when all elements are target)

### Approach 3: Built-in Collections Methods
```java
// Convert to list and use Collections
List<Integer> list = Arrays.stream(arr).boxed().collect(toList());
return Collections.frequency(list, target);
```
**Pros:** One-liner  
**Cons:** O(n) time, O(n) extra space

---

## Variations and Extensions

### Related Problems
1. **Find First and Last Position** - Direct application
2. **Search in Rotated Sorted Array** - Modified binary search
3. **Find Peak Element** - Similar binary search logic
4. **Count Smaller Elements** - Uses similar range-finding technique

### Possible Variations
1. **Count elements in range [x, y]**
   ```java
   return countLessOrEqual(y) - countLessOrEqual(x-1);
   ```

2. **Count elements greater than x**
   ```java
   return arr.length - getFirstOccurrence(arr, x+1);
   ```

3. **Kth occurrence of element**
   ```java
   int first = getFirstOccurrence(arr, target);
   return (first == -1 || first + k - 1 >= arr.length) ? -1 : first + k - 1;
   ```

---

## Interview Strategy

### What Interviewers Test
1. **Recognition** that sorted array suggests binary search
2. **Understanding** of the mathematical relationship: count = last - first + 1  
3. **Edge case handling** - especially the "not found" case
4. **Implementation** of modified binary search

### Key Points to Mention
- **Why O(log n)**: "Since array is sorted, we can use binary search"
- **Mathematical insight**: "Count equals the range size"
- **Critical edge case**: "Must handle when target doesn't exist"
- **Space efficiency**: "Only O(1) extra space needed"

### Common Follow-up Questions
1. **"What if array has duplicates in random order?"**  
   → Need O(n) linear scan, can't use binary search

2. **"How to count elements in range [a, b]?"**  
   → Use same technique: `count(b) - count(a-1)`

3. **"What about very large arrays that don't fit in memory?"**  
   → External sorting + streaming approach

4. **"Can you do this recursively?"**  
   → Yes, but iterative is better for space efficiency

### Implementation Tips for Interviews
1. **Start with the math**: Explain count = last - first + 1
2. **Handle edge cases first**: Check for empty array, not found
3. **Write clean binary search**: Use standard template
4. **Test with examples**: Walk through a simple case
5. **Mention time/space complexity**

---

## Testing Strategy

### Comprehensive Test Cases
```java
// Basic functionality
testCount([1,2,3,3,3,4], 3, 3);

// Not found
testCount([1,2,4,5], 3, 0);

// Single element
testCount([5], 5, 1);
testCount([5], 3, 0);

// All same
testCount([2,2,2,2], 2, 4);

// Boundaries  
testCount([1,1,2,3], 1, 2);
testCount([1,2,3,3], 3, 2);

// Empty array
testCount([], 1, 0);

// Large arrays
int[] large = new int[100000];
Arrays.fill(large, 42);
testCount(large, 42, 100000);
```

### Edge Case Checklist
- ✅ Empty array
- ✅ Single element (found/not found)  
- ✅ All elements same
- ✅ Target not in array
- ✅ Target at start/end
- ✅ Single occurrence
- ✅ Large arrays

---

## Summary

**Key Takeaways:**
1. **Use sorted property** → Binary search for O(log n)
2. **Find range** → First and last occurrences  
3. **Calculate count** → `last - first + 1`
4. **Handle edge case** → Check if target exists (`first != -1`)
5. **Two modified binary searches** → Continue searching after finding target

**The Golden Rule:** Always check if the element was found before calculating the count!