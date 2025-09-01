Class Solution{
	    public static int[] findMissingRepeatingNumbers(int[] nums) {
        long s=0,ss=0;
        int n = nums.length;
        for(int i=0;i<nums.length;i++){
            s+=nums[i];
            ss+=1L* nums[i]*nums[i];
        }
        long totalSum =1L* (n)*(n+1)/2;
        long totalSquareSum =1L* ((n)*(n+1)*(2L*n+1))/6;
        long diff = totalSum-s;
        long squareDiff = totalSquareSum-ss;
        long sumMr = squareDiff/diff;
        long m = (diff+sumMr)/2;
        long r = m-diff;
       return new int[] { (int) r, (int) m };
    }
}