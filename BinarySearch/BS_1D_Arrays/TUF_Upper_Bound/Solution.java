public class Solution{
    public static int search(int[] nums, int target) {
       int f=0, l = nums.length-1,ans=nums.length;
       while(f<=l){
           int mid = (f+l)/2;
           if(nums[mid]>target){
               ans = mid;
               l=mid-1;
           }
           else{
               f=mid+1;
           }
       }
       return ans;
    } 
}