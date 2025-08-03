class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int n = nums.length;
        int max = 0,sum=0;
        for(int i=0;i<n;i++){
            if(nums[i]==0){
                max = Math.max(max,sum);
                sum=0;
            }
            else{
            sum+=1;
            }
        }
        return Math.max(max,sum);
    }
}