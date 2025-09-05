# Search in Rotated Sorted Array - Complete Guide

## Problem Statement

Given an integer array `nums`, sorted in ascending order (with distinct values) and a target value `k`. The array is rotated at some pivot point that is unknown. Find the index at which `k` is present and if `k` is not present return -1.

**Examples:**
- Input: `nums = [4, 5, 6, 7, 0, 1, 2]`, `k = 0` → Output: `4`
- Input: `nums = [4, 5, 6, 7, 0, 1, 2]`, `k = 3` → Output: `-1`

## Problem Understanding

**What does "rotated" mean?**
- Original sorted array: `[0, 1, 2, 4, 5, 6, 7]`
- Rotated at index 4: `[4, 5, 6, 7, 0, 1, 2]`
- The array is split and the second part is moved to the front

**Key Properties:**
- The array consists of two sorted subarrays
- At most one "break point" exists where `nums[i] > nums[i+1]`
- At least one half of any subarray will always be properly sorted

## Algorithm Strategy

1. **Find the middle element**
2. **Determine which half is sorted**
   - If `nums[left] <= nums[mid]`: left half is sorted
   - Otherwise: right half is sorted
3. **Check if target is in the sorted half**
   - If yes: search in that half
   - If no: search in the other half
4. **Repeat until found or exhausted**

## Optimal Solution

```java
public static int search(int[] nums, int k) {
    int l = 0, h = nums.length - 1;
    
    while (l <= h) {
        int mid = l + (h - l) / 2;
        
        // Found the target
        if (nums[mid] == k) return mid;
        
        // Determine which half is sorted
        if (nums[l] <= nums[mid]) {
            // Left half is sorted
            if (k >= nums[l] && k < nums[mid]) {
                h = mid - 1;  // Target is in left half
            } else {
                l = mid + 1;  // Target is in right half
            }
        } else {
            // Right half is sorted
            if (k > nums[mid] && k <= nums[h]) {
                l = mid + 1;  // Target is in right half
            } else {
                h = mid - 1;  // Target is in left half
            }
        }
    }
    
    return -1;  // Target not found
}
```

## Detailed Walkthrough

### Example 1: Target Found
```
nums = [4, 5, 6, 7, 0, 1, 2], k = 0

Step 1: l=0, h=6, mid=3
[4, 5, 6, 7, 0, 1, 2]
 ^        ^        ^
 l      mid        h
- nums[mid] = 7 ≠ 0
- nums[l]=4 ≤ nums[mid]=7 ✓ (left sorted)
- k=0 ≥ nums[l]=4? No → go right
- l = mid+1 = 4

Step 2: l=4, h=6, mid=5  
[4, 5, 6, 7, 0, 1, 2]
             ^  ^  ^
             l mid h
- nums[mid] = 1 ≠ 0
- nums[l]=0 ≤ nums[mid]=1 ✓ (left sorted)
- k=0 ≥ nums[l]=0 ✓ and k=0 < nums[mid]=1 ✓ → go left
- h = mid-1 = 4

Step 3: l=4, h=4, mid=4
[4, 5, 6, 7, 0, 1, 2]
             ^
           l,mid,h
- nums[mid] = 0 = k ✓ → Return 4
```

### Example 2: Left Half Sorted Case
```
nums = [4, 5, 6, 7, 0, 1, 2], k = 5

Step 1: l=0, h=6, mid=3
- nums[l]=4 ≤ nums[mid]=7 ✓ (left half [4,5,6,7] is sorted)
- k=5 ≥ nums[l]=4 ✓ and k=5 < nums[mid]=7 ✓ → target in left half
- h = mid-1 = 2

Step 2: l=0, h=2, mid=1
- nums[mid] = 5 = k ✓ → Return 1
```

### Example 3: Right Half Sorted Case
```
nums = [6, 7, 0, 1, 2, 4, 5], k = 4

Step 1: l=0, h=6, mid=3
- nums[l]=6 > nums[mid]=1 → right half [1,2,4,5] is sorted
- k=4 > nums[mid]=1 ✓ and k=4 ≤ nums[h]=5 ✓ → target in right half
- l = mid+1 = 4

Step 2: l=4, h=6, mid=5
- nums[mid] = 4 = k ✓ → Return 5
```

## Critical Boundary Conditions

### Why we use `k < nums[mid]` instead of `k <= nums[mid]`:
- We already checked `nums[mid] == k` at the start
- Including `nums[mid]` in range would create logical inconsistency
- We want strict inequalities to avoid including the checked middle element

### Why we use `nums[l] <= nums[mid]`:
- Handles the case where `l == mid` (single element)
- In a properly sorted portion, equal values indicate no rotation in that segment

## Edge Cases

### Edge Case 1: Array with 2 elements
```
nums = [3, 1], k = 1

l=0, h=1, mid=0
nums[0]=3 ≠ k=1
nums[l]=3 <= nums[mid]=3 ✓
k=1 >= nums[0]=3 ✗ → go to right half
l = mid+1 = 1

l=1, h=1, mid=1
nums[1]=1 == k=1 ✓ → Return 1
```

### Edge Case 2: No rotation (fully sorted)
```
nums = [1, 2, 3, 4, 5], k = 3
Works like normal binary search since left half is always sorted
```

### Edge Case 3: Single element
```
nums = [1], k = 1
l=0, h=0, mid=0
nums[0]=1 == k=1 ✓ → Return 0
```

## Common Mistakes to Avoid

1. **Wrong boundary conditions**: Using `<=` instead of `<` when checking ranges
2. **Incorrect sorted half detection**: Not handling the `nums[l] <= nums[mid]` case properly
3. **Off-by-one errors**: Incorrect mid calculation or boundary updates
4. **Including already checked middle**: Using inclusive bounds after checking `nums[mid]`

## Complexity Analysis

**Time Complexity**: O(log n)
- Each iteration eliminates half the search space
- Same as standard binary search

**Space Complexity**: O(1)
- Only using a few variables regardless of input size
- Iterative approach (no recursion stack)

## Key Insights

1. **Always check `nums[mid] == k` first**
2. **Determine which half is sorted using `nums[l] <= nums[mid]`**
3. **Use strict inequalities when checking if target is in sorted range**
4. **The unsorted half will always contain the rotation point**
5. **At least one half will always be completely sorted**

## Alternative Approaches

### 1. Find Pivot First (Less Efficient)
```java
// First find the rotation point, then do binary search
// Time: O(log n) + O(log n) = O(log n), but with higher constant factor
```

### 2. Linear Search (Brute Force)
```java
// Time: O(n), Space: O(1)
for (int i = 0; i < nums.length; i++) {
    if (nums[i] == k) return i;
}
return -1;
```

## Related Problems
1. **Search in Rotated Sorted Array II** (with duplicates)
2. **Find Minimum in Rotated Sorted Array**
3. **Find Peak Element**
4. **Search a 2D Matrix**

## Template for Similar Problems

```java
public int binarySearchInRotatedArray(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) return mid;
        
        // Determine which side is sorted
        if (nums[left] <= nums[mid]) {
            // Left side is sorted
            if (target >= nums[left] && target < nums[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        } else {
            // Right side is sorted
            if (target > nums[mid] && target <= nums[right]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
    }
    
    return -1;
}