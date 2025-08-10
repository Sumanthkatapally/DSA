import java.util.*;
public class Solution
{
	public static void main(String[] args) {
		int nums[] = {1, 5,7,2,3};
		int k =12;
		System.out.println(longestSubarray(nums,k));
		}

		public static int longestSubarray(int[] nums, int k) {
			int i=0,j=0,s=0;
			int max =0;
			while(j<nums.length) {
				while(i<j && s>k) {
					s-=nums[i];
					i++;
				}
				if(s==k) {
					max=Math.max(max,j-i);
					s+=nums[j];
					j++;
				}
				else {
					s+=nums[j];
					j++;
				}
			}
			return s==k?Math.max(max,j-i):max;
		}
}