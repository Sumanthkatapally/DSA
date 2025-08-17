**LeetCode 121 – Best Time to Buy and Sell Stock**.

---

## **LeetCode 121 – Best Time to Buy and Sell Stock**

**Problem:**
Given an array `prices[]`, where `prices[i]` is the price of a stock on day `i`, find the **maximum profit** you can achieve from **one buy and one sell**.
You must buy before you sell. If no profit is possible, return `0`.

---

### **Approach – Single Pass (O(n) time, O(1) space)**

**Idea:**

* Keep track of the **lowest price** seen so far (`minPrice`).
* For each price:

  1. **Update minPrice** if the current price is lower.
  2. **Calculate potential profit** = `current price - minPrice`.
  3. Keep track of the **maximum profit** found so far.

---

### **Algorithm Steps**

1. Initialize:

   * `minPrice = Integer.MAX_VALUE` → ensures first price will replace it.
   * `profit = 0` → initial profit is zero.
2. Loop through each day:

   * If `prices[i] < minPrice` → update `minPrice` (found a cheaper day to buy).
   * Else → calculate `prices[i] - minPrice` and update `profit` if it’s greater than current `profit`.
3. Return `profit`.

---

### **Why It Works**

* The **best day to buy** must be before the selling day.
* We find the lowest price so far and check profit opportunities on the go.
* We avoid nested loops (brute force O(n²)).

---

### **Complexity**

* **Time:** O(n) → single loop over prices.
* **Space:** O(1) → only two variables (`minPrice`, `profit`).

---

### **Example Walkthrough**

**Input:** `prices = [7,1,5,3,6,4]`

| Day | Price | minPrice | Profit Candidate | Max Profit |
| --- | ----- | -------- | ---------------- | ---------- |
| 0   | 7     | 7        | -                | 0          |
| 1   | 1     | 1        | -                | 0          |
| 2   | 5     | 1        | 4                | 4          |
| 3   | 3     | 1        | 2                | 4          |
| 4   | 6     | 1        | 5                | 5          |
| 5   | 4     | 1        | 3                | 5          |

**Answer:** 5 (Buy at 1, sell at 6)

---

### **Common Pitfalls**

* Not checking that **buy day is before sell day** (handled here by using minPrice from earlier days only).
* Forgetting to return 0 when prices are strictly decreasing.

---

Link: https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
