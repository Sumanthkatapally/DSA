public class Solution{
    public static int countOccurrences(int[] nums, int x) {
        int f = getFirstOccurence(nums,x);
        if(f==-1) return 0;
        int l = getLastOccurence(nums,x);
       return l-f+1;
    }
    
    public static int getFirstOccurence(int[] nums,int x){
        int l=0,h=nums.length-1,res=-1;
        while(l<=h){
            int mid = l+(h-l)/2;
            if(nums[mid]==x){
                res=mid;
                h=mid-1;
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
        while(l<=h){
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