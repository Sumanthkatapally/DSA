# Pascal Triangle Solution - Detailed Notes

This Java solution provides three different approaches to work with Pascal's Triangle, demonstrating mathematical optimization and efficient computation techniques.

## Overview
Pascal's Triangle is a triangular array where each number is the sum of the two numbers above it. The solution uses the mathematical property that each element equals C(n,r) = n!/(r!(n-r)!).

## Method 1: `getPascalValue(int r, int c)`
**Purpose**: Gets a specific element from Pascal's Triangle at row r, column c

**Key Insights**:
- Uses the combination formula C(n,r) = n!/(r!(n-r)!)
- Avoids factorial calculation by using iterative multiplication and division
- **Optimization**: Instead of computing factorials separately, it calculates the result incrementally

**Algorithm Breakdown**:
```java
int res = 1;
for(int i=0; i<c; i++){
    res = res*(r-i);    // Multiply by (r-i)
    res = res/(i+1);    // Divide by (i+1)
}
```

**Mathematical Logic**:
- For C(r,c), we need: r × (r-1) × (r-2) × ... × (r-c+1) / (1 × 2 × 3 × ... × c)
- The loop builds this fraction step by step to avoid large intermediate values

**Time Complexity**: O(c)
**Space Complexity**: O(1)

## Method 2: `getPascalRow(int r)`
**Purpose**: Generates an entire row of Pascal's Triangle

**Key Strategy**:
- Leverages the relationship between consecutive elements in a row
- Each element relates to the previous: `next = current × (r-i) / i`

**Algorithm Flow**:
1. Start with first element (always 1)
2. For each subsequent position, use the recurrence relation
3. Build the row incrementally without recalculating combinations from scratch

**Mathematical Relationship**:
- If current element is C(r-1, i-1), then next is C(r-1, i)
- C(r-1, i) = C(r-1, i-1) × (r-i) / i

**Time Complexity**: O(r)
**Space Complexity**: O(r) for the result list

## Method 3: `generate(int numRows)`
**Purpose**: Generates the entire Pascal's Triangle up to numRows

**Approach**:
- Calls `getPascalRow()` for each row from 1 to numRows
- Builds the complete triangle structure

**Time Complexity**: O(numRows²)
**Space Complexity**: O(numRows²)

## Important Implementation Details

### Index Handling
- The main method uses `getPascalValue(r-1, c-1)` because:
  - Mathematical Pascal's Triangle is typically 0-indexed
  - User input might be 1-indexed
  - The conversion ensures proper calculation

### Optimization Techniques
1. **Avoiding Large Factorials**: Instead of computing n!, r!, and (n-r)! separately
2. **Incremental Calculation**: Building results step by step
3. **Integer Division Safety**: Performing multiplication before division to maintain integer precision

### Edge Cases Handled
- First element of each row is always 1
- Empty triangle (numRows = 0) returns empty list
- Single row requests work correctly

## Mathematical Foundation
The solution is based on the binomial coefficient property:
- C(n,k) = C(n,k-1) × (n-k+1) / k
- This allows computing each element from the previous one efficiently

## Performance Benefits
- **Memory Efficient**: No storage of intermediate factorials
- **Computationally Efficient**: O(k) instead of O(n!) for individual elements
- **Numerically Stable**: Avoids large intermediate values that could cause overflow

## Usage Examples from Main Method
1. **Single Element**: Get element at row 5, column 3
2. **Single Row**: Get all elements in row 5
3. **Full Triangle**: Generate first 5 rows of Pascal's Triangle

This implementation demonstrates excellent algorithmic thinking by choosing mathematical optimization over brute-force calculation, making it suitable for larger inputs while maintaining code clarity.


Link: https://leetcode.com/problems/pascals-triangle/description/
Striver: https://www.youtube.com/watch?v=bR7mQgwQ_o8