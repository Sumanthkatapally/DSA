# 3Sum Problem - Complete Solution Analysis

## Problem Statement
Find all unique triplets in an array that sum to zero: `nums[i] + nums[j] + nums[k] = 0` where `i ≠ j ≠ k`.

---

## 1. Brute Force Solution

### Algorithm
```java
public static List<List<Integer>> threeSumBruteForce(int[] nums) {
    Set<List<Integer>> set = new HashSet<>();
    int n = nums.length;
    
    for(int i = 0; i < n-2; i++) {
        for(int j = i+1; j < n-1; j++) {
            for(int k = j+1; k < n; k++) {
                if(nums[i] + nums[j] + nums[k] == 0) {
                    List<Integer> temp = Arrays.asList(nums[i], nums[j], nums[k]);
                    Collections.sort(temp);  // Sort to handle duplicates
                    set.add(temp);
                }
            }
        }
    }
    
    return new ArrayList<>(set);
}
```

### Detailed Analysis

**Core Logic**:
- Use three nested loops to check every possible triplet
- Check if sum equals zero
- Use Set with sorted triplets to eliminate duplicates

**Time Complexity**: O(n³ + k log k) where k is number of valid triplets
- Three nested loops: O(n³)
- Sorting each triplet: O(log 3) = O(1)
- Total: **O(n³)**

**Space Complexity**: O(k) for storing results + O(k) for the set = **O(k)**

**Pros**:
- Simple and intuitive approach
- Easy to understand and implement
- Guaranteed to find all solutions

**Cons**:
- Very inefficient for large inputs
- Cubic time complexity makes it impractical
- Still needs duplicate handling

---

## 2. Better Solution (HashMap Approach)

### Algorithm Breakdown

```java
public static List<List<Integer>> threeSum(int[] nums) {
    Set<List<Integer>> set = new HashSet<>();
    int n = nums.length;
    
    for(int i = 0; i < n-2; i++) {              // Fix first element
        Set<Integer> h = new HashSet<>();        // Hash set for current iteration
        
        for(int j = i+1; j < n; j++) {          // Second element
            int e = -(nums[i] + nums[j]);        // Required third element
            
            if(h.contains(e)) {                  // If third element exists
                List<Integer> temp = Arrays.asList(nums[i], nums[j], e);
                Collections.sort(temp);          // Sort for duplicate handling
                set.add(temp);                   // Add to set (handles duplicates)
            }
            h.add(nums[j]);                      // Add current element to hash set
        }
    }
    
    return new ArrayList<>(set);
}
```

### Detailed Analysis

**Core Strategy**:
1. **Fix First Element**: Outer loop fixes `nums[i]`
2. **Find Complement**: For each `nums[j]`, calculate required third element: `e = -(nums[i] + nums[j])`
3. **Hash Lookup**: Check if required element exists in previously seen elements
4. **Duplicate Handling**: Use Set with sorted triplets

**Step-by-Step Execution**:
```
For nums = [-1, 0, 1, 2, -1, -4]:

i=0, nums[i]=-1:
  h = {}
  j=1, nums[j]=0, e=-(-1+0)=1, h doesn't contain 1, h={0}
  j=2, nums[j]=1, e=-(-1+1)=0, h contains 0 ✓, found [-1,1,0], h={0,1}
  j=3, nums[j]=2, e=-(-1+2)=-1, h doesn't contain -1, h={0,1,2}
  j=4, nums[j]=-1, e=-(-1+-1)=2, h contains 2 ✓, found [-1,-1,2], h={0,1,2,-1}
  j=5, nums[j]=-4, e=-(-1+-4)=5, h doesn't contain 5, h={0,1,2,-1,-4}
```

**Time Complexity**: O(n²)
- Outer loop: O(n)
- Inner loop: O(n)
- Hash operations: O(1) average
- Sorting triplets: O(1) since only 3 elements
- **Total: O(n²)**

**Space Complexity**: O(n + k)
- Hash set for each iteration: O(n)
- Result set: O(k) where k is number of unique triplets
- **Total: O(n + k)**

**Pros**:
- Significant improvement from O(n³) to O(n²)
- Uses efficient hash lookups
- Automatic duplicate handling via Set

**Cons**:
- Higher space complexity due to hash sets
- Still processes some duplicates (sorts and adds to set)
- Set operations have overhead

**Key Insight**: 
The hash set `h` only contains elements we've seen in current iteration (`nums[j]` values), ensuring we don't use same index twice.

---

## 3. Optimal Solution (Two Pointers)

### Algorithm Breakdown

```java
public static List<List<Integer>> threeSum(int[] nums) {
    int n = nums.length;
    List<List<Integer>> result = new ArrayList<>();
    if(n < 3) return result;                    // Edge case
    
    Arrays.sort(nums);                          // Sort array first
    
    for(int i = 0; i < n-2; i++) {             // Fix first element
        if(i > 0 && nums[i] == nums[i-1])      // Skip duplicates for i
            continue;
            
        int j = i + 1, k = n - 1;              // Two pointers
        
        while(j < k) {
            int sum = nums[i] + nums[j] + nums[k];
            
            if(sum < 0) {                       // Need larger sum
                j++;
            }
            else if(sum > 0) {                  // Need smaller sum
                k--;
            }
            else {                              // Found valid triplet
                result.add(Arrays.asList(nums[i], nums[j], nums[k]));
                j++;
                k--;
                
                // Skip duplicates for j and k
                while(j < k && nums[j] == nums[j-1]) j++;
                while(j < k && nums[k] == nums[k+1]) k--;
            }
        }
    }
    
    return result;
}
```

### Detailed Analysis

**Core Strategy**:
1. **Sort Array**: Enables two-pointer technique and duplicate skipping
2. **Fix First Element**: Outer loop sets `nums[i]`
3. **Two Pointers**: Use `j` (left) and `k` (right) to find pairs that sum to `-nums[i]`
4. **Smart Movement**: 
   - If sum < 0: increase sum by moving `j` right
   - If sum > 0: decrease sum by moving `k` left
   - If sum = 0: found solution, record and move both pointers

**Step-by-Step Execution**:
```
For nums = [-1, 0, 1, 2, -1, -4]:
After sorting: [-4, -1, -1, 0, 1, 2]

i=0, nums[i]=-4:
  j=1, k=5: sum = -4+(-1)+2 = -3 < 0, j++
  j=2, k=5: sum = -4+(-1)+2 = -3 < 0, j++
  j=3, k=5: sum = -4+0+2 = -2 < 0, j++
  j=4, k=5: sum = -4+1+2 = -1 < 0, j++
  j=5, k=5: j >= k, exit

i=1, nums[i]=-1:
  j=2, k=5: sum = -1+(-1)+2 = 0 ✓, found [-1,-1,2]
  j=3, k=4: sum = -1+0+1 = 0 ✓, found [-1,0,1]
  j=4, k=4: j >= k, exit

i=2, nums[i]=-1: Skip (duplicate of previous)
```

**Time Complexity**: O(n²)
- Sorting: O(n log n)
- Outer loop: O(n)
- Two pointers: O(n) for each i
- **Total: O(n log n + n²) = O(n²)**

**Space Complexity**: O(1)
- Only using constant extra space (not counting output)
- **Optimal space usage**

### Advanced Optimizations in Optimal Solution

**1. Duplicate Skipping**:
```java
// Skip duplicate values for i
if(i > 0 && nums[i] == nums[i-1]) continue;

// Skip duplicate values for j and k after finding solution
while(j < k && nums[j] == nums[j-1]) j++;
while(j < k && nums[k] == nums[k+1]) k--;
```

**2. Early Termination Opportunities**:
```java
// Can add this optimization
if(nums[i] > 0) break;  // If smallest remaining > 0, no solution possible
```

**3. Boundary Checks**:
```java
if(n < 3) return result;  // Need at least 3 elements
```

---

## Comprehensive Comparison

| Aspect | Brute Force | Better (HashMap) | Optimal (Two Pointers) |
|--------|-------------|------------------|------------------------|
| **Time Complexity** | O(n³) | O(n²) | O(n²) |
| **Space Complexity** | O(k) | O(n + k) | O(1) |
| **Preprocessing** | None | None | O(n log n) sorting |
| **Duplicate Handling** | Set + Sort | Set + Sort | Smart skipping |
| **Memory Usage** | Low | High | Lowest |
| **Code Complexity** | Simple | Medium | Medium |
| **Interview Score** | Poor | Good | Excellent |

## When to Use Each Approach

**Brute Force**:
- Very small datasets (n < 20)
- Learning/teaching purposes
- When simplicity is paramount

**Better (HashMap)**:
- When you can't modify input array
- When space complexity isn't critical
- As stepping stone to optimal solution

**Optimal (Two Pointers)**:
- Production code
- Large datasets
- Technical interviews
- When space efficiency matters

## Key Learning Points

1. **Sorting Enables Optimization**: The optimal solution's power comes from sorting
2. **Two Pointers Pattern**: Classic technique for sorted array problems
3. **Duplicate Handling**: Different approaches have different strategies
4. **Space-Time Tradeoffs**: HashMap uses more space but avoids sorting
5. **Problem Transformation**: 3Sum → 2Sum with fixed element

The optimal solution represents the best balance of time efficiency, space efficiency, and code clarity for the 3Sum problem.

Link: https://takeuforward.org/plus/dsa/problems/3-sum