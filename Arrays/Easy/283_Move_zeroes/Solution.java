class Solution {
    public void moveZeroes(int[] nums) {
        int i =0,c=0;;
        while(i<nums.length){
            if(nums[i]!=0){
                nums[c]=nums[i];
                c++;
                i++;
            }
            else
                i++;
        }
        for(int j=c;j<nums.length;j++){
            nums[j] =0;
        }
    }
}