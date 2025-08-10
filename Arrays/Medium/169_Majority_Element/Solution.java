class Solution {
    public int majorityElement(int[] nums) {
        int canditate=0,count =0;
        for(int x:nums){
            if(count == 0){
                canditate=x;
                count++;
            }
            else{
            count+=canditate==x?1:-1;
            }
       }
        return canditate;
    }
}