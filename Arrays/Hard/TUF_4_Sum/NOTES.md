# 4Sum Problem - Complete Detailed Notes

## Problem Statement

Given an array `nums` of `n` integers and an integer `target`, return an array of all **unique quadruplets** `[nums[a], nums[b], nums[c], nums[d]]` such that:
- `0 ≤ a, b, c, d < n`
- `a`, `b`, `c`, and `d` are **distinct**
- `nums[a] + nums[b] + nums[c] + nums[d] == target`

The solution set must not contain duplicate quadruplets.

### Examples
```
Input: nums = [1,0,-1,0,-2,2], target = 0
Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]

Input: nums = [2,2,2,2,2], target = 8
Output: [[2,2,2,2]]

Input: nums = [], target = 0
Output: []
```

---

## Approach 1: Brute Force Solution

### Algorithm
```java
public static List<List<Integer>> fourSumBruteForce(int[] nums, int target) {
    Set<List<Integer>> resultSet = new HashSet<>();
    int n = nums.length;
    
    for (int i = 0; i < n - 3; i++) {
        for (int j = i + 1; j < n - 2; j++) {
            for (int k = j + 1; k < n - 1; k++) {
                for (int l = k + 1; l < n; l++) {
                    long sum = (long)nums[i] + nums[j] + nums[k] + nums[l];
                    
                    if (sum == target) {
                        List<Integer> quad = Arrays.asList(nums[i], nums[j], nums[k], nums[l]);
                        Collections.sort(quad);
                        resultSet.add(quad);
                    }
                }
            }
        }
    }
    
    return new ArrayList<>(resultSet);
}
```

### Analysis
**Time Complexity**: O(n⁴)
- Four nested loops, each running up to n times
- Sorting each quadruplet: O(1) since only 4 elements

**Space Complexity**: O(k) where k is number of unique quadruplets
- Set storage for avoiding duplicates

**Pros**:
- Simple and straightforward
- Easy to understand
- Guaranteed to find all solutions

**Cons**:
- Extremely inefficient for large inputs
- Quartic time complexity
- Still needs duplicate handling

---

## Approach 2: HashMap-Based Solution

### Algorithm
```java
public static List<List<Integer>> fourSumHashMap(int[] nums, int target) {
    Set<List<Integer>> resultSet = new HashSet<>();
    int n = nums.length;
    
    // Fix first two elements
    for (int i = 0; i < n - 3; i++) {
        for (int j = i + 1; j < n - 2; j++) {
            Set<Integer> seen = new HashSet<>();
            
            // Find remaining two elements
            for (int k = j + 1; k < n; k++) {
                long complement = (long)target - nums[i] - nums[j] - nums[k];
                
                if (complement >= Integer.MIN_VALUE && complement <= Integer.MAX_VALUE) {
                    if (seen.contains((int)complement)) {
                        List<Integer> quad = Arrays.asList(nums[i], nums[j], nums[k], (int)complement);
                        Collections.sort(quad);
                        resultSet.add(quad);
                    }
                }
                seen.add(nums[k]);
            }
        }
    }
    
    return new ArrayList<>(resultSet);
}
```

### Analysis
**Time Complexity**: O(n³) average case
- Two outer loops: O(n²)
- Inner loop with hash operations: O(n)
- Hash operations: O(1) average

**Space Complexity**: O(n + k)
- Hash set for each iteration: O(n)
- Result set: O(k)

**Pros**:
- Better than brute force
- Uses efficient hash lookups
- Good for unsorted arrays

**Cons**:
- Higher space complexity
- Hash collisions can degrade performance
- Still processes duplicates

---

## Approach 3: Optimal Two-Pointer Solution

### Algorithm
```java
public static List<List<Integer>> fourSum(int[] nums, int target) {
    int n = nums.length;
    List<List<Integer>> result = new ArrayList<>();
    
    if (n < 4) return result;
    Arrays.sort(nums);
    
    for (int i = 0; i < n - 3; i++) {
        // Skip duplicates for i
        if (i > 0 && nums[i] == nums[i-1]) continue;
        
        for (int j = i + 1; j < n - 2; j++) {
            // Skip duplicates for j
            if (j > i + 1 && nums[j] == nums[j-1]) continue;
            
            int k = j + 1, l = n - 1;
            
            while (k < l) {
                long sum = (long)nums[i] + nums[j] + nums[k] + nums[l];
                
                if (sum < target) {
                    k++;
                } else if (sum > target) {
                    l--;
                } else {
                    result.add(Arrays.asList(nums[i], nums[j], nums[k], nums[l]));
                    k++;
                    l--;
                    
                    // Skip duplicates for k and l
                    while (k < l && nums[k] == nums[k-1]) k++;
                    while (k < l && nums[l] == nums[l+1]) l--;
                }
            }
        }
    }
    
    return result;
}
```

### Detailed Step-by-Step Analysis

#### Core Strategy
1. **Sort the array** to enable two-pointer technique
2. **Fix first two elements** with nested loops (i, j)
3. **Use two pointers** (k, l) to find remaining two elements
4. **Skip duplicates** at all four positions to avoid duplicate quadruplets

#### Example Execution
```
Input: nums = [1, 0, -1, 0, -2, 2], target = 0
After sorting: [-2, -1, 0, 0, 1, 2]

i=0, nums[i]=-2:
  j=1, nums[j]=-1, need sum = 0-(-2)-(-1) = 3:
    k=2, l=5: sum = -2+(-1)+0+2 = -1 < 0, k++
    k=3, l=5: sum = -2+(-1)+0+2 = -1 < 0, k++  
    k=4, l=5: sum = -2+(-1)+1+2 = 0 ✓ → [-2,-1,1,2]
    
  j=2, nums[j]=0, need sum = 0-(-2)-0 = 2:
    k=3, l=5: sum = -2+0+0+2 = 0 ✓ → [-2,0,0,2]
    k=4, l=4: k >= l, exit
    
  j=3, nums[j]=0: Skip (duplicate)

i=1, nums[i]=-1:
  j=2, nums[j]=0, need sum = 0-(-1)-0 = 1:
    k=3, l=5: sum = -1+0+0+2 = 1 > 0, l--
    k=3, l=4: sum = -1+0+0+1 = 0 ✓ → [-1,0,0,1]
    
Result: [[-2,-1,1,2], [-2,0,0,2], [-1,0,0,1]]
```

### Analysis
**Time Complexity**: O(n³)
- Sorting: O(n log n)
- Two outer loops: O(n²)
- Two pointers for each pair: O(n)
- Total: O(n log n + n³) = O(n³)

**Space Complexity**: O(1)
- Only constant extra space (excluding output)

**Key Optimizations**:
1. **Efficient duplicate skipping**
2. **Two-pointer technique reduces innermost complexity**
3. **Sorted array enables smart pointer movement**

---

## Advanced Optimizations

### 1. Early Termination
```java
// Add these checks for further optimization
if (4 * nums[i] > target) break;                    // Minimum possible > target
if (nums[i] + 3 * nums[n-1] < target) continue;     // Maximum possible < target

if (nums[i] + nums[j] + 2 * nums[n-1] < target) continue;  // For j loop
if (nums[i] + 3 * nums[j] > target) break;                 // For j loop
```

### 2. Overflow Prevention
```java
// Always use long for sum calculation
long sum = (long)nums[i] + nums[j] + nums[k] + nums[l];
```

### 3. Edge Case Handling
```java
if (nums == null || nums.length < 4) return result;
```

---

## Complexity Comparison

| Approach | Time Complexity | Space Complexity | Best For |
|----------|-----------------|------------------|----------|
| **Brute Force** | O(n⁴) | O(k) | Learning, n < 20 |
| **HashMap** | O(n³) avg | O(n + k) | Unsorted arrays |
| **Two Pointers** | **O(n³)** | **O(1)** | **Production code** |

---

## Pattern Recognition: k-Sum Problems

The two-pointer approach follows a **reduction pattern**:

- **2Sum**: Two pointers on sorted array → O(n)
- **3Sum**: Fix 1 element + 2Sum → O(n²)  
- **4Sum**: Fix 2 elements + 2Sum → O(n³)
- **kSum**: Fix (k-2) elements + 2Sum → O(n^(k-1))

### Generic k-Sum Template
```java
public List<List<Integer>> kSum(int[] nums, int target, int k) {
    Arrays.sort(nums);
    return kSumHelper(nums, target, 0, k);
}

private List<List<Integer>> kSumHelper(int[] nums, long target, int start, int k) {
    List<List<Integer>> result = new ArrayList<>();
    
    if (k == 2) {
        return twoSum(nums, target, start);
    }
    
    for (int i = start; i < nums.length - k + 1; i++) {
        if (i > start && nums[i] == nums[i-1]) continue;
        
        List<List<Integer>> subResult = kSumHelper(nums, target - nums[i], i + 1, k - 1);
        
        for (List<Integer> list : subResult) {
            result.add(new ArrayList<>(Arrays.asList(nums[i])));
            result.get(result.size() - 1).addAll(list);
        }
    }
    
    return result;
}
```

---

## Common Pitfalls and Solutions

### 1. **Integer Overflow**
```java
// WRONG
int sum = nums[i] + nums[j] + nums[k] + nums[l];

// CORRECT  
long sum = (long)nums[i] + nums[j] + nums[k] + nums[l];
```

### 2. **Incorrect Duplicate Skipping**
```java
// WRONG - doesn't skip duplicates properly
if (nums[j] == nums[j-1]) continue;

// CORRECT - only skip if not first occurrence
if (j > i + 1 && nums[j] == nums[j-1]) continue;
```

### 3. **Missing Result Addition**
```java
// WRONG - forgot to add to result
List<Integer> temp = Arrays.asList(nums[i], nums[j], nums[k], nums[l]);

// CORRECT
List<Integer> temp = Arrays.asList(nums[i], nums[j], nums[k], nums[l]);
result.add(temp);
```

### 4. **Adding Sum Instead of Elements**
```java
// WRONG - adds the sum value
Arrays.asList(nums[i] + nums[j] + nums[k] + nums[l])

// CORRECT - adds individual elements
Arrays.asList(nums[i], nums[j], nums[k], nums[l])
```

---

## Interview Tips

### What Interviewers Look For
1. **Optimal approach identification** (Two pointers)
2. **Proper duplicate handling** at all positions
3. **Edge case consideration** (empty array, overflow)
4. **Code quality** and readability
5. **Pattern recognition** (relating to 3Sum, 2Sum)

### Discussion Points
- Time complexity analysis and why O(n³) is optimal
- Space-time tradeoffs between different approaches  
- How this extends to general k-Sum problems
- Real-world applications and constraints

### Follow-up Questions
- "How would you solve k-Sum for arbitrary k?"
- "What if we needed exactly k unique elements?"
- "How to handle very large numbers (BigInteger)?"
- "Can you optimize for specific targets (like target=0)?"

---

## Conclusion

The **two-pointer solution is optimal** for the 4Sum problem because:

✅ **Optimal time complexity**: O(n³) matches theoretical lower bound  
✅ **Minimal space usage**: O(1) auxiliary space  
✅ **Handles duplicates efficiently**: Smart skipping without extra data structures  
✅ **Scalable pattern**: Extends naturally to k-Sum problems  
✅ **Production ready**: Clean, maintainable, and robust code  

This approach demonstrates mastery of:
- Sorting-based optimization techniques
- Two-pointer algorithms  
- Duplicate handling strategies
- Integer overflow considerations
- Pattern recognition and generalization

Link: https://takeuforward.org/plus/dsa/problems/4-sum