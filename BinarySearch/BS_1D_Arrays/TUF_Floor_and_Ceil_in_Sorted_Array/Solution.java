public class Solution{
    public static int[] getFloorAndCeil(int[] nums, int x) {
        int l=0,h=nums.length-1,f=-1,c=-1;
        while(l<=h){
            int mid = l+(h-l)/2;
            if(nums[mid]==x){
                return new int[]{x,x};
            }
            else if(nums[mid]<x){
                f = nums[mid];
                l=mid+1;
            }
            else{
                c=nums[mid];
                h=mid-1;
            }
        }
       return new int[]{f,c};
    }
}