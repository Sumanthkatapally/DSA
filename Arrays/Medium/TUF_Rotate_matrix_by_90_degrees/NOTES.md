# Matrix Rotation by 90 Degrees - Detailed Notes

## Current Solution Analysis (90° Clockwise)

### Algorithm Overview
Your solution uses a **two-step approach** to rotate the matrix 90° clockwise:
1. **Transpose** the matrix (swap elements across main diagonal)
2. **Reverse each row** (flip horizontally)

### Step-by-Step Breakdown

#### Step 1: Transpose Matrix
```java
public static void transposeMatrix(int[][] matrix,int n,int m){
    for(int i=0;i<n;i++){
        for(int j=i+1;j<m;j++){  // Note: j starts from i+1
            swap(matrix,i,j,j,i);
        }
    }
}
```
- **What it does**: Swaps `matrix[i][j]` with `matrix[j][i]`
- **Key insight**: `j` starts from `i+1` to avoid swapping twice
- **Effect**: Reflects matrix across main diagonal

#### Step 2: Reverse Each Row
```java
public static void reverseRows(int[][] matrix,int n,int m){
    for(int i=0;i<n;i++){
        int f=0,l=m-1;
        while(f<l){
            swap(matrix,i,f,i,l);
            f++;
            l--;
        }
    }
}
```
- **What it does**: Reverses elements in each row
- **Two-pointer approach**: `f` (front) and `l` (last) move toward center
- **Effect**: Flips matrix horizontally

### Example Transformation
```
Original:     After Transpose:    After Reverse Rows:
1 2 3         1 4 7              7 4 1
4 5 6   →     2 5 8        →     8 5 2
7 8 9         3 6 9              9 6 3
```

### Complexity Analysis
- **Time Complexity**: O(n²) - Each element visited once during transpose + once during row reversal
- **Space Complexity**: O(1) - In-place rotation, only using constant extra space
- **Optimal**: Yes! This is the most efficient approach for in-place rotation

---

## Counter-Clockwise Rotation (90° CCW)

### Method 1: Reverse Order of Operations
For **counter-clockwise** rotation, simply **swap the order**:
1. **Reverse each row first**
2. **Then transpose**

```java
public static void setZeroes90CCW(int[][] matrix) {
    int n=matrix.length,m=matrix[0].length;
    reverseRows(matrix,n,m);     // Step 1: Reverse rows
    transposeMatrix(matrix,n,m); // Step 2: Transpose
    printMatrix(matrix);
}
```

### Method 2: Alternative Approach
Keep the same order but **reverse columns instead of rows**:
1. **Transpose** the matrix
2. **Reverse each column** (instead of reversing rows)

```java
public static void reverseColumns(int[][] matrix,int n,int m){
    for(int j=0;j<m;j++){        // For each column
        int t=0,b=n-1;           // top and bottom pointers
        while(t<b){
            swap(matrix,t,j,b,j); // Swap vertically
            t++;
            b--;
        }
    }
}
```

### Counter-Clockwise Example
```
Original:     Method 1 (Reverse→Transpose):    Method 2 (Transpose→Reverse Cols):
1 2 3         3 2 1                           1 4 7
4 5 6   →     6 5 4        →    3 6 9        2 5 8        →    3 6 9
7 8 9         9 8 7             2 5 8        3 6 9             2 5 8
                                1 4 7                          1 4 7
```

---

## Key Insights

### Why This Works (Mathematical Explanation)
- **Clockwise 90°**: `(i,j) → (j, n-1-i)`
- **Counter-clockwise 90°**: `(i,j) → (m-1-j, i)`

The two-step process achieves these transformations:
- **Transpose**: `(i,j) → (j,i)`
- **Reverse rows**: `(i,j) → (i, m-1-j)`
- **Combined**: `(i,j) → (j, m-1-i)` ✓ (Clockwise)

### Advantages of This Approach
1. **In-place**: No extra matrix needed
2. **Intuitive**: Easy to understand and remember
3. **Optimal**: O(n²) time, O(1) space
4. **Flexible**: Easy to modify for different rotation directions

### Common Mistake to Avoid
❌ **Don't start `j` from 0 in transpose**:
```java
for(int j=0;j<m;j++){ // WRONG - will swap twice!
```
✅ **Start `j` from `i+1`**:
```java
for(int j=i+1;j<m;j++){ // CORRECT
```

---

## Summary

Your solution is **optimal and elegant**! The method name `setZeroes` seems like a copy-paste error (should be `rotateMatrix90Clockwise`), but the logic is perfect. For counter-clockwise rotation, simply reverse the order of operations or use column reversal instead of row reversal.

If it is n*m Matrix we should use extra space otherwise we get index out of bounf if we have to do it in inplace.

Link: https://takeuforward.org/plus/dsa/problems/rotate-matrix-by-90-degrees