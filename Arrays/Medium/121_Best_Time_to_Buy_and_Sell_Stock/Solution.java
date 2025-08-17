class Solution {
    public int maxProfit(int[] prices) {
        int minPrice=Integer.MAX_VALUE,profit=0;
        for(int i=0;i<prices.length;i++){
            if(minPrice>prices[i]){
                minPrice=prices[i];
            }
            else{
                profit = Math.max(profit,prices[i]-minPrice);
            }
        }
        return profit;
    }
}
/*
Track the lowest price so far (minPrice).
For each day, check if selling today gives a better profit than before.
Update max profit if today’s price - minPrice is higher.
Time complexity: O(n)
Space Complexity: O(1)
*/