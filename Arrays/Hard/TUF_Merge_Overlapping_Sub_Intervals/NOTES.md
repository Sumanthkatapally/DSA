Detailed notes for **Merge Overlapping Intervals** using your Java approach.

# 1) Problem in one line

Given a list of intervals `[start, end]`, combine all intervals that overlap (and optionally those that just touch) and return the merged list.

# 2) Core idea (sort + sweep)

1. **Sort** intervals by `start`.
2. Keep a “current merged interval” `(f, l)` = first interval’s start/end.
3. For each next interval `(cf, cl)`:

   * If it **overlaps** the current one, extend: `l = max(l, cl)`.
   * If it **doesn’t overlap**, **push** `(f, l)` to `result`, and start a new current interval `(f, l) = (cf, cl)`.
4. After the loop, **push the last** `(f, l)`—it’s still pending.

Why push at the end? Because you only add when you encounter a gap; the final merged block won’t be followed by a gap, so you must push it after the loop.

# 3) Overlap condition (decide your semantics)

* **Closed intervals** `[a,b]` (LeetCode-style): treat `[1,3]` and `[3,5]` as overlapping/touching → merge them with
  `if (cf <= l) { l = Math.max(l, cl); }`
* **Half-open** `[a,b)`: `[1,3)` and `[3,5)` do **not** overlap → keep them separate with
  `if (cf < l) { l = Math.max(l, cl); }`

Pick one and be consistent. Most interview problems use the **closed** version.

# 4) Correct Java implementation (List\<List<Integer>>)

```java
public static List<List<Integer>> merge(List<List<Integer>> intervals) {
    List<List<Integer>> result = new ArrayList<>();
    if (intervals == null || intervals.isEmpty()) return result;

    intervals.sort(Comparator.comparingInt(a -> a.get(0)));

    int f = intervals.get(0).get(0);
    int l = intervals.get(0).get(1);

    for (int i = 1; i < intervals.size(); i++) {
        int cf = intervals.get(i).get(0);
        int cl = intervals.get(i).get(1);

        if (cf <= l) {                  // overlap or touch (closed intervals)
            l = Math.max(l, cl);        // extend the right bound
        } else {                        // gap -> flush current
            result.add(Arrays.asList(f, l));
            f = cf;
            l = cl;
        }
    }
    result.add(Arrays.asList(f, l));    // push the last pending merged interval
    return result;
}
```

### Example walkthrough

Input: `[[1,5],[3,6],[8,10],[15,18]]`
Sorted (already sorted). Start `(f,l)=(1,5)`:

* See `[3,6]` → `3 <= 5` overlap → `l = max(5,6) = 6` → `(1,6)`
* See `[8,10]` → `8 > 6` gap → push `(1,6)`, start `(8,10)`
* See `[15,18]` → gap → push `(8,10)`, start `(15,18)`
  End → push `(15,18)`
  Output: `[[1,6],[8,10],[15,18]]`

# 5) Complexity

* **Time:** `O(n log n)` due to sorting, `O(n)` scan.
* **Space:** `O(1)` extra beyond output (the output itself can hold up to `n` intervals in the worst case).

# 6) Common pitfalls (what goes wrong)

* **Shrinking instead of extending:** Using `l = cl` when overlapping (should be `l = Math.max(l, cl)`).
* **Strict overlap check:** Using `cf < l` when you meant to merge touching intervals; prefer `cf <= l` for closed-interval semantics.
* **Forgetting the last push:** Must add `(f,l)` after the loop.
* **Empty input:** Accessing `intervals.get(0)` without guarding.
* **Boxing overhead / fixed-size lists:** `Arrays.asList(f, l)` is fine for pairs you won’t modify, but note it’s a fixed-size list.

# 7) Variant with arrays (avoids boxing)

If you can use `int[][]`, it’s faster and cleaner:

```java
public static int[][] merge(int[][] intervals) {
    if (intervals == null || intervals.length == 0) return new int[0][2];

    Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
    List<int[]> res = new ArrayList<>();

    int f = intervals[0][0], l = intervals[0][1];

    for (int i = 1; i < intervals.length; i++) {
        int cf = intervals[i][0], cl = intervals[i][1];
        if (cf <= l) {
            l = Math.max(l, cl);
        } else {
            res.add(new int[]{f, l});
            f = cf; l = cl;
        }
    }
    res.add(new int[]{f, l});
    return res.toArray(new int[res.size()][]);
}
```

# 8) Edge cases to test quickly

* `[]` → `[]`
* `[[1,2]]` → `[[1,2]]`
* Full containment: `[[1,10],[2,3],[4,5]]` → `[[1,10]]`
* All disjoint: `[[1,2],[3,4],[5,6]]` → same (or merged if you use `<=`)
* Same starts: `[[1,3],[1,2]]` → `[[1,3]]`
* Touching: `[[1,3],[3,5]]` → `[[1,5]]` (closed), or `[[1,3],[3,5]]` (half-open)

# 9) Why the algorithm is correct (intuition)

After sorting by start:

* The current `(f,l)` is always the merge of all intervals seen so far that overlap.
* Any new interval either overlaps `(f,l)` (so the merged end must be the **max** of ends) or it starts after `l` (so we finish the current block and start a new one).
* This invariant ensures every merged block is output exactly once, and no overlaps are missed.

