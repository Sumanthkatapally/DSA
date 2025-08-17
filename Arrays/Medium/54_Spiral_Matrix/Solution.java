class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        int n = matrix.length, m = matrix[0].length;
        List<Integer> result= new ArrayList<>(n*m);
        int t=0,b=n-1,l=0,r=m-1;
        while(t<=b && l<=r){
            for(int j=l;j<=r;j++)
                result.add(matrix[t][j]);
            t++;
            for(int i=t;i<=b;i++)
                result.add(matrix[i][r]);
            r--;
            if(t<=b){
                for(int j=r;j>=l;j--)
                    result.add(matrix[b][j]);
            b--;
            }
            if(l<=r){
                for(int i=b;i>=t;i--)
                    result.add(matrix[i][l]);
                l++;
            }
        }
        return result;
    }
}