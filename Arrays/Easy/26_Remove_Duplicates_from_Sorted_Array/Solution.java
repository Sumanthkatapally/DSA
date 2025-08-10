class Solution {
    public int removeDuplicates(int[] nums) {
        int n = nums.length;
        if(n==1)
            return 1;
        int i = 1;
        int j=0;
        while(i<n){
            if(nums[i-1]==nums[i])
                i++;
            else{
                j++;
                nums[j] = nums[i];
                i++;
            }
        }
        return j+1;
    }
}