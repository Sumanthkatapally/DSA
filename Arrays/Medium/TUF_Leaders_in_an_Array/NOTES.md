**clear notes** for the *Leaders in an Array* problem and the different optimal solutions we discussed. 

---

## 📌 Leaders in an Array — Problem Notes

* **Definition:** An element is a *leader* if it is strictly greater than **all elements to its right**.
* **Always true:** The rightmost element is always a leader.
* **Order requirement:** Leaders must appear in the same order as in the input array.
* **Constraints:** Need `O(n)` solution since scanning all pairs (brute force) would be `O(n²)`.

---

## ✅ Optimal Approach (Right-to-Left Scan + Reverse)

* Traverse array **from right to left**.
* Keep track of the **maximum so far** (`rightMax`).
* If `nums[i] > rightMax`, it is a leader.
* Add leaders to a list (they will be in reverse order).
* Finally, **reverse the list** to restore left-to-right order.

**Complexity:**

* Time → `O(n)` (single pass + reverse).
* Space → `O(k)` (number of leaders, ≤ n).

---

## ✨ Variations (All Optimal `O(n)`)

1. **Suffix-Max Array**

   * Precompute the maximum of the suffix for each index.
   * Leaders are those `nums[i] > sufMax[i+1]`.
   * Avoids reverse, but uses `O(n)` extra space.

2. **Deque / Add-First Approach**

   * Add leaders to a **deque from the front** while scanning right-to-left.
   * Natural order is preserved → no need to reverse.
   * Extra structure: `Deque`.

3. **Two-Pass Count + Fill**

   * Pass 1: Count how many leaders.
   * Pass 2: Fill a fixed-size array of leaders **from right to left**.
   * Output already in left-to-right order.
   * No `reverse()` needed.

4. **In-Place (if mutation allowed)**

   * Overwrite leaders at the start of the original array while scanning from right.
   * Slice the portion at the end.
   * Extra space `O(1)` beyond output.

---

## 📊 Trade-offs

* **Right-to-left + reverse** → simplest, clean code, widely used.
* **Deque** → avoids reversing, but slightly less common.
* **Suffix-max** → intuitive but extra memory.
* **Two-pass** → avoids reverse, but slightly longer code.
* **In-place** → most memory efficient, but modifies input array.

---

👉 Bottom line:
Your solution (`O(n)` + `reverse`) is already **optimal and clean**. Alternatives just remove the `reverse()` step or tweak space trade-offs.

---

Link: https://takeuforward.org/plus/dsa/problems/leaders-in-an-array