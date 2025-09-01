public Class Solution{
    public static long numberOfInversions(int[] nums) {
        int n = nums.length;
        long count = mergeSort(nums,0,n-1);
        return count;
    }
    
    public static long mergeSort(int[] nums,int l, int h){
        long count =0;
        if(l>=h)    return count;
        int mid=(l+h)/2;
        count+=mergeSort(nums,l,mid);
        count+=mergeSort(nums,mid+1,h);
        count+=merge(nums,l,mid,h);
        return count;
    }
    public static long merge(int[] nums, int l,int m, int h){
        int temp[] =  new int[h-l+1];
        long cnt = 0;
        int i=l,j=m+1,k=0;
        
        while(i<m+1 && j<h+1){
            if(nums[i]<=nums[j]){
                temp[k]=nums[i];
                k++;
                i++;
            }
            else if(nums[i]>nums[j]){
                cnt+=m-i+1;
                temp[k]=nums[j];
                k++;
                j++;
            }
        }
        
        while(i<m+1){
                temp[k]=nums[i];
                k++;
                i++;
        }
        
        while(j<h+1){
                temp[k]=nums[j];
                k++;
                j++;
        }
        
        int y=0;
        for(int x=l;x<=h;x++) nums[x]=temp[y++];
        
        return cnt;
    }
	
}