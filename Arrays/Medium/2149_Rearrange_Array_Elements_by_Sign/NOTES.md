**LeetCode 2149: Rearrange Array Elements by Sign**, including what to do when positives and negatives are not equal in count.

---

# Problem recap

* Input: integer array `nums`, all values are nonzero.
* Original constraint on LeetCode: equal count of positives and negatives, and the result must start with a positive and alternate.
* Your follow-up: handle the more general case where counts may differ.

---

# Core goals

1. Place positives at even indices and negatives at odd indices.
2. Preserve relative order within positives and within negatives only if required.
3. Choose the right trade-off between time and space.

---

# Approach A: Two buckets, then merge (stable, O(n) time, O(n) space)

**When to use**

* This is the cleanest solution for the original problem and the easiest way to support unequal counts.
* It preserves the relative order within positives and within negatives.

**Steps**

1. Scan once. Push positives into `pos[]`, negatives into `neg[]`.
2. Fill `res[]` by alternating from `pos` and `neg`, starting with a positive.
3. If one list runs out (when counts differ), append the remaining elements from the other list to the end.

**Complexity**

* Time: O(n)
* Space: O(n)
* Stability: Yes

**Sketch**

```java
int n = nums.length;
List<Integer> pos = new ArrayList<>(), neg = new ArrayList<>();
for (int x : nums) {
    if (x > 0) pos.add(x);
    else neg.add(x);
}

int[] res = new int[n];
int i = 0, p = 0, q = 0;

// alternate as far as both have elements
while (p < pos.size() && q < neg.size()) {
    res[i++] = pos.get(p++);
    if (q < neg.size()) res[i++] = neg.get(q++);
}

// if counts differ, append leftovers
while (p < pos.size()) res[i++] = pos.get(p++);
while (q < neg.size()) res[i++] = neg.get(q++);
```

**Notes for unequal counts**

* If the problem statement still requires starting with positive, keep the first pick from `pos`, then interleave while both lists have items.
* The tail will naturally be all positives or all negatives based on which list is longer.

---

# Approach B: In-place two-pointer swap (not stable, O(n) time, O(1) space)

**When to use**

* You want O(1) extra space and are OK with losing the original relative order.
* Works best when counts are equal or close. If counts differ, you can still place as many alternating pairs as possible, then the rest will remain as is.

**Idea**

* Maintain `pos` at the next even index that needs a positive and `neg` at the next odd index that needs a negative.
* Swap elements at `pos` and `neg` when both are wrong.

**Sketch**

```java
int pos = 0, neg = 1, n = nums.length;
while (pos < n && neg < n) {
    boolean posOk = nums[pos] > 0;
    boolean negOk = nums[neg] < 0;

    if (posOk) pos += 2;
    else if (negOk) neg += 2;
    else {
        int t = nums[pos]; nums[pos] = nums[neg]; nums[neg] = t;
        pos += 2; neg += 2;
    }
}
```

**Complexity**

* Time: O(n)
* Space: O(1)
* Stability: No

---

# Approach C: Stable in-place idea (O(n^2) time, O(1) space)

**When to use**

* Only if the interviewer explicitly asks for in-place and stable order.
* This is basically a stable partition done with rotations.

**Idea**

* Scan left to right. If index `i` expects a positive but finds a negative (or vice versa), find the next index `j` to the right with the needed sign, then rotate `[i..j]` right by one position to bring `nums[j]` to `i`.
* Each rotation is O(j − i). In the worst case, total is O(n^2).

**Why it works**

* Rotation preserves the relative order of the elements between `i` and `j − 1` and inserts the needed sign at `i`. Repeating this achieves a stable alternation.

---

# Correctness intuition

* Alternation is enforced by index parity: even positions for positives, odd positions for negatives.
* With buckets, you never run out of index bounds because you allocate exactly `n` slots and fill them in order.
* With in-place swapping, once a position is fixed you advance by 2, so you never disturb already fixed positions.

---

# Edge cases and pitfalls

1. Already alternating arrays

   * All approaches should leave them mostly unchanged.
2. All positives first, then all negatives (or vice versa)

   * Two-bucket solution is still O(n).
   * In-place stable rotation can degrade to O(n^2).
3. Unequal counts (your follow-up)

   * Two buckets handle this naturally: alternate until one side is empty, then append the rest.
   * In-place swap version stops alternating when one pointer passes `n`. You can leave the remainder as is, or decide a rule to place leftovers at the end.
4. Zeros

   * LeetCode 2149 guarantees nonzero, but if zeros appear in a variant, decide whether to treat them as positive, negative, or collect in a separate list and append later.

---

# Test set you can use

1. Equal counts, mixed:

   * `nums = [3,1,-2,-5,2,-4]`
   * One valid answer: `[3,-2,1,-5,2,-4]`
2. Equal counts, already alternating:

   * `nums = [5,-1,6,-2,7,-3]`
3. Unequal counts (more negatives):

   * `nums = [3,1,-2,-5,2,-4,-7]`
   * Positives `[3,1,2]`, negatives `[-2,-5,-4,-7]`
   * Result (stable, buckets): `[3,-2,1,-5,2,-4,-7]`
4. Unequal counts (more positives):

   * `nums = [4,2,6,-1,8,10]`
   * Result (stable, buckets): `[4,-1,2,6,8,10]` or `[4,-1,2,6,8,10]` depending on your merge rule
5. Single element:

   * `[5]` or `[-7]`
6. Large uniform block:

   * `[1,2,3,4,-1,-2,-3,-4]`

---

# What to implement based on your requirement

* If the counts may differ and you want correctness and stable order: **use the two buckets approach**.
* If you must keep O(1) space and do not care about order: **use the in-place two-pointer swap**.
* If an interviewer insists on both in-place and stable: **describe the rotation method** and mention its O(n^2) cost.

---

# Quick checklist for interviews

* Confirm whether counts are equal and whether the result must start with positive.
* Ask if relative order must be preserved.
* Choose from:

  * O(n) time, O(n) space, stable (buckets).
  * O(n) time, O(1) space, not stable (swap).
  * O(n^2) time, O(1) space, stable (rotation).



Here are clean, interview-ready notes for the variant where positives and negatives are **not** equal in count.

# Problem (unequal counts)

Given an array `nums` of nonzero integers, rearrange so signs alternate as much as possible. If one sign runs out, append the remaining elements of the other sign at the end. If the statement requires starting with positive, follow that; otherwise you can choose a start sign to maximize alternation.

# Goals

1. Alternate signs while both sides have elements.
2. Preserve relative order within positives and within negatives if possible.
3. Handle leftovers cleanly once one side is exhausted.

# Preferred approach: two buckets, then merge (stable, O(n) time, O(n) space)

Why: Simple, fast, and naturally handles unequal counts while keeping order.

Steps

1. Scan once. Push positives into `pos`, negatives into `neg`.
2. Decide start sign.

   * If required, start with positive.
   * If free to choose, start with the majority sign to maximize alternation.
3. Alternate from `pos` and `neg` while both have elements.
4. Append leftovers from whichever list still has elements.

Complexity

* Time O(n)
* Space O(n)
* Stable order within each sign

Reference code (Java)

```java
class Solution {
    public int[] rearrangeArrayUnequal(int[] nums) {
        List<Integer> pos = new ArrayList<>();
        List<Integer> neg = new ArrayList<>();
        for (int x : nums) {
            if (x > 0) pos.add(x); else neg.add(x);
        }

        int n = nums.length;
        int[] res = new int[n];

        boolean startWithPos;
        // Rule 1: force start with positive if required
        // Rule 2: otherwise pick the majority sign
        if (/* must start positive */ true) {
            startWithPos = true;
        } else {
            startWithPos = pos.size() >= neg.size();
        }

        int i = 0, p = 0, q = 0;
        boolean pickPos = startWithPos;

        while (p < pos.size() && q < neg.size()) {
            if (pickPos) res[i++] = pos.get(p++);
            else         res[i++] = neg.get(q++);
            pickPos = !pickPos;
        }
        while (p < pos.size()) res[i++] = pos.get(p++);
        while (q < neg.size()) res[i++] = neg.get(q++);
        return res;
    }
}
```

# In-place option when counts differ (O(1) extra space, not stable)

Idea

* Use two pointers `pos` (even index) and `neg` (odd index).
* Swap when you find mismatches.
* Stop when either pointer passes the end. The prefix will be alternated as much as possible; the tail will contain leftovers in arbitrary order.

When to use

* Only if space must be O(1) and you do not need to preserve order.

Complexity

* Time O(n)
* Space O(1)
* Not stable, and the leftover section at the end will not be strictly grouped unless you add more passes.

# Edge cases

1. All positives or all negatives: result equals the input (no alternation possible).
2. One side vastly larger: alternation continues until the smaller side ends; remaining elements are appended.
3. Zeros: original 2149 guarantees nonzero. In custom variants, decide a rule for zeros (treat as positive, as negative, or collect separately and append).

# Test cases

1. More negatives
   Input: `[3, 1, -2, -5, 2, -4, -7]`
   Pos `[3,1,2]`, Neg `[-2,-5,-4,-7]`
   Output (stable): `[3, -2, 1, -5, 2, -4, -7]`
2. More positives
   Input: `[4, 2, 6, -1, 8, 10]`
   Output (stable, start with positive): `[4, -1, 2, 6, 8, 10]`
3. Already alternating
   Input: `[5, -1, 6, -2, 7]`
   Output: same
4. Single element
   `[9]` or `[-3]`

# Quick interview checklist

* Confirm whether you must start with positive.
* Ask if preserving relative order is required.
* Choose:

  * Buckets + merge for correctness and stability.
  * In-place swap if O(1) space is mandatory and order does not matter.
* Mention the trade-off clearly.

Link: https://leetcode.com/problems/rearrange-array-elements-by-sign/