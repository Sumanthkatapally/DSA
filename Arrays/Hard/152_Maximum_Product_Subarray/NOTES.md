# Maximum Product Subarray 

## Problem Statement
Given an integer array `nums`, find a contiguous subarray within the array (containing at least one number) that has the largest product, and return the product.

**Example:**
- Input: `[2,3,-2,4]` → Output: `6` (subarray `[2,3]`)
- Input: `[-2,0,-1]` → Output: `0` (subarray `[0]`)

## Key Insights

### Why Standard Kadane's Algorithm Doesn't Work
Unlike maximum sum subarray where we only need to track one value, the product problem is more complex because:
- **Negative numbers** can turn a small negative product into a large positive product
- **Zero** resets everything
- We need to track both maximum and minimum products at each position

### The Two-Pointer Approach
The algorithm maintains two variables at each step:
- `maxp`: Maximum product ending at current position
- `minp`: Minimum product ending at current position

## Algorithm Walkthrough

### Core Logic
```java
// When current number is negative, swap max and min
if(c < 0) {
    int temp = minp;
    minp = maxp;
    maxp = temp;
}

// Update max and min products
maxp = Math.max(c, maxp * c);
minp = Math.min(c, minp * c);

// Update global maximum
max = Math.max(max, maxp);
```

### Why Swap When Negative?
When we encounter a negative number:
- Previous **maximum** × negative = new **minimum**
- Previous **minimum** × negative = new **maximum**

This swap ensures we correctly track the potential for future large positive products.

## Step-by-Step Example
**Input:** `[2, 3, -2, 4]`

| i | nums[i] | Before | Swap? | maxp calculation | minp calculation | maxp | minp | max |
|---|---------|--------|-------|------------------|------------------|------|------|-----|
| 0 | 2 | - | - | - | - | 2 | 2 | 2 |
| 1 | 3 | maxp=2, minp=2 | No | max(3, 2×3)=6 | min(3, 2×3)=3 | 6 | 3 | 6 |
| 2 | -2 | maxp=6, minp=3 | Yes | max(-2, 3×-2)=-2 | min(-2, 6×-2)=-12 | -2 | -12 | 6 |
| 3 | 4 | maxp=-2, minp=-12 | No | max(4, -2×4)=4 | min(4, -12×4)=-48 | 4 | -48 | 6 |

**Result:** 6

## Algorithm Analysis

### Time Complexity: O(n)
- Single pass through the array
- Constant time operations at each step

### Space Complexity: O(1)
- Only uses a few variables regardless of input size

## Edge Cases Handled

1. **All negative numbers:** `[-2, -3, -1]` → Result: 1 (single element)
2. **Contains zero:** `[2, 0, 3]` → Resets products at zero
3. **Single element:** `[5]` → Result: 5
4. **Even negative count:** `[-2, -3]` → Result: 6 (both negatives)
5. **Odd negative count:** `[-2, -3, -1]` → Result: 2 (skip one negative)

## Why This Solution is Optimal

### Correctness
- Considers all possible subarrays implicitly
- Handles negative numbers correctly through swapping
- Maintains both maximum and minimum to catch future opportunities

### Efficiency
- No nested loops needed
- No extra space for storing intermediate results
- Single pass solution

### Key Innovation
The swapping mechanism when encountering negative numbers is the crucial insight that makes this algorithm work elegantly and efficiently.

This solution demonstrates how sometimes we need to track multiple states (max and min) to solve optimization problems involving products or when the sign of numbers affects the optimal choice.

Link: https://leetcode.com/problems/maximum-product-subarray/