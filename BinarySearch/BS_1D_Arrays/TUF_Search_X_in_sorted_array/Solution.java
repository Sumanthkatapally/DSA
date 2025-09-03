public class Solution{
    public static int search(int[] nums, int target) {
       int f=0, l = nums.length-1;
       while(f<=l){
           int mid = (f+l)/2;
           if(nums[mid]==target)    return mid;
           else if(nums[mid]>target){
               
               l=mid-1;
           }
           else{
               f=mid+1;
           }
       }
       return -1;
    } 
}