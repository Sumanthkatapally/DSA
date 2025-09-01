**Find the Repeating and Missing Number** in an array of size `n` containing numbers `1..n` with exactly one number repeated once and one number missing.

---

# 1) Problem setup & notation

* Input: `nums[0..n-1]` with values in `1..n`.
* Exactly one value `r` is **repeated** (appears twice) and one value `m` is **missing** (appears 0 times).
* Goal: return `(repeating r, missing m)`.

We’ll use:

* `s = Σ nums[i]`
* `ss = Σ nums[i]^2`
* `S = Σ_{k=1..n} k = n(n+1)/2`
* `SS = Σ_{k=1..n} k^2 = n(n+1)(2n+1)/6`

Key relationships:

* `diff = m - r = S - s`
* `sqDiff = m^2 - r^2 = SS - ss = (m - r)(m + r) = diff * (m + r)`

From these:

* `sumMR = m + r = sqDiff / diff`
* Solve the two linear equations:

  * `m = (diff + sumMR) / 2`
  * `r = (sumMR - diff) / 2`

These divisions are **exact** (no rounding) because:

* `diff + sumMR = (m − r) + (m + r) = 2m` (even)
* `sqDiff = diff * (m + r)` by identity, so `sqDiff / diff` is an integer.

---

# 2) Math-based solution (O(n) time, O(1) extra space)

### Implementation notes

* Use `long` to avoid overflow when summing and squaring.
* **Do not** use `Math.pow` (returns double). Use integer multiplication.
* Force `long` early: `1L * n * (n + 1) / 2`, etc.

### Java

```java
public static int[] findRepeatingAndMissing(int[] nums) {
    int n = nums.length;
    long s = 0, ss = 0;

    for (int x : nums) {
        s += x;
        ss += 1L * x * x; // integer square, no floating point
    }

    long S  = 1L * n * (n + 1) / 2;
    long SS = 1L * n * (n + 1) * (2L * n + 1) / 6;

    long diff   = S - s;           // = m - r
    long sqDiff = SS - ss;         // = m^2 - r^2

    long sumMR = sqDiff / diff;    // = m + r

    long m = (diff + sumMR) / 2;   // missing
    long r = (sumMR - diff) / 2;   // repeating

    return new int[] { (int) r, (int) m }; // many platforms expect [repeating, missing]
}
```

**Complexity:**

* Time: `O(n)` (one pass)
* Space: `O(1)` (ignoring the output array)

**When to be careful:** If constraints allow very large `n` (e.g., near `10^9`), `SS` may overflow 64-bit; typical interview constraints (e.g., `n ≤ 2e5`) are fine with `long`.

---

# 3) XOR-based solution (also O(n) / O(1))

Avoids big-number arithmetic.

Idea:

1. `xr = 1 ^ 2 ^ ... ^ n ^ nums[0] ^ ... ^ nums[n-1] = m ^ r` (because all others cancel).
2. Let `rsb = xr & -xr` (rightmost set bit). This bit differs between `m` and `r`.
3. Partition both `1..n` and the array into two buckets by `rsb`. XOR each bucket separately → you’ll get two candidates `b1`, `b2` which are `{m, r}` in some order.
4. Determine which is repeating by scanning once.

### Java

```java
public static int[] findRepeatingAndMissingXor(int[] a) {
    int n = a.length;
    int xr = 0;
    for (int x : a) xr ^= x;
    for (int i = 1; i <= n; i++) xr ^= i;

    int rsb = xr & -xr; // rightmost set bit
    int b1 = 0, b2 = 0;

    for (int x : a)       if ((x & rsb) != 0) b1 ^= x; else b2 ^= x;
    for (int i = 1; i <= n; i++) if ((i & rsb) != 0) b1 ^= i; else b2 ^= i;

    // Decide which is repeating
    int countB1 = 0;
    for (int x : a) if (x == b1) countB1++;
    int repeat, missing;
    if (countB1 == 2) { repeat = b1; missing = b2; }
    else             { repeat = b2; missing = b1; }

    return new int[]{repeat, missing};
}
```

**Pros:** no overflow risk; neat bit trick.
**Cons:** requires the XOR partitioning logic and a final membership check.

---

# 4) “Index mark” / in-place method (modifies array)

If mutation is allowed and numbers are guaranteed in `1..n`:

* Traverse `nums`, for each value `v = abs(nums[i])`, go to index `v-1`:

  * If `nums[v-1] < 0`, then `v` is the **repeating** number.
  * Else, negate `nums[v-1]`.
* Second pass: the index where the value is **positive** corresponds to the **missing** number (`index + 1`).

```java
public static int[] findRepeatingAndMissingInPlace(int[] nums) {
    int r = -1, m = -1;
    for (int i = 0; i < nums.length; i++) {
        int v = Math.abs(nums[i]);
        if (nums[v - 1] < 0) r = v;
        else nums[v - 1] = -nums[v - 1];
    }
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] > 0) { m = i + 1; break; }
    }
    return new int[]{r, m};
}
```

**Pros:** O(n) time, O(1) extra space, very simple.
**Cons:** Mutates the input array.

---

# 5) Common pitfalls (and fixes)

* ❌ Writing algebra in code: `m-r = ...`, `(m-r)*(m+r) = ...` (invalid in Java).
  ✅ Use named temporaries `diff`, `sqDiff`, `sumMR`, then solve.
* ❌ Using `Math.pow(x,2)` → floating point, precision issues.
  ✅ Use `1L * x * x`.
* ❌ Overflow in `S`/`SS` due to `int` multiplication.
  ✅ Force `long` first: `1L * n * (n+1) / 2`, `1L * n * (n+1) * (2L*n + 1) / 6`.
* ❌ Returning `{0,0}` or mixing `(missing, repeating)` order.
  ✅ Return in the order your platform expects (often `[repeating, missing]`).
* ❌ Assuming negatives/zeros allowed.
  ✅ The classic problem uses values in `1..n`.

---

# 6) Quick example (math method)

`nums = [1, 2, 2, 4]`, `n=4`
`s = 9`, `ss = 1 + 4 + 4 + 16 = 25`
`S  = 10`, `SS = 30`
`diff   = S - s = 1 = m - r`
`sqDiff = SS - ss = 5 = m^2 - r^2 = (m - r)(m + r)`
`sumMR  = sqDiff / diff = 5`
`m = (diff + sumMR)/2 = (1+5)/2 = 3`
`r = (sumMR - diff)/2 = (5-1)/2 = 2` → **\[2, 3]**

---

# 7) Which method to use?

* **Math sums**: shortest to write; just be careful with `long`.
* **XOR**: safest vs overflow; elegant bit trick.
* **In-place marking**: fastest in practice, constant space, but mutates input.

All three are optimal **O(n) time / O(1) extra space** (ignoring output)