public class Solution{
    public static int[] searchRange(int[] nums, int x) {
        int f = getFirstOccurence(nums,x);
        int l = getLastOccurence(nums,x);
       return new int[]{f,l};
    }
    
    public static int getFirstOccurence(int[] nums,int x){
        int l=0,h=nums.length-1,res=-1;
        while(l<h){
            int mid = l+(h-l)/2;
            if(nums[mid]==x){
                res=mid;
                h=mid;
            }
            else if(nums[mid]<x){
                l = mid+1;
            }
            else h=mid-1;
        }
        return res;
    }
    
    public static int getLastOccurence(int[] nums,int x){
        int l=0,h=nums.length-1,res=-1;
        while(l<h){
            int mid = l+(h-l)/2;
            if(nums[mid]==x){
                res=mid;
                l=mid+1;
            }
            else if(nums[mid]<x){
                l = mid+1;
            }
            else h=mid-1;
        }
        return res;
    }
}