class Solution {
    public int maxProduct(int[] nums) {
        int n = nums.length;
        int max=nums[0];
        int minp=nums[0],maxp=nums[0];
        for(int i=1;i<n;i++){
            int c = nums[i];
            if(c<0){
                int temp = minp;
                minp=maxp;
                maxp=temp;
            }
            maxp=Math.max(c,maxp*c);
            minp=Math.min(c,minp*c);
            max = Math.max(max,maxp);
        }
        return max;        
    }
}