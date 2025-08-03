class Solution {
    public int[] unionArray(int[] nums1, int[] nums2) {
        int i=0,j=0,k=0;
        int[] nums3 =  new int[nums1.length+nums2.length];
        while(i<nums1.length && j<nums2.length){
            int e;
            if(nums1[i]<nums2[j]){
                e = nums1[i++];
            }
            else if(nums1[i]>nums2[j]){
                e = nums2[j++];
            }
            else{
                e = nums1[i++];
                j++;
            }
            if(k==0 || nums3[k-1]!=e){
                nums3[k++]=e;
            }
 
        }
        while(i<nums1.length){
            int e = nums1[i++];
            if(k==0 || nums3[k-1]!=e){
                nums3[k++]=e;
            }
        }
        while(j<nums2.length){
            int e = nums2[j++];
            if(k==0 || nums3[k-1]!=e){
                nums3[k++]=e;
            }
        }
        return Arrays.copyOf(nums3,k);
    }
}