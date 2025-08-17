public class Solution
{
	public static void main(String[] args) {
	int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    rotateMatrix(matrix);
	}
	
    public static void rotateMatrix(int[][] matrix) {
        int n=matrix.length,m=matrix[0].length;
        transposeMatrix(matrix,n,m);
        reverseRows(matrix,n,m);
        printMatrix(matrix);
    }
    
    public static void transposeMatrix(int[][] matrix,int n,int m){

        for(int i=0;i<n;i++){
            for(int j=i+1;j<m;j++){
                swap(matrix,i,j,j,i);
            }
        }
    }
    
    public static void reverseRows(int[][] matrix,int n,int m){
        for(int i=0;i<n;i++){
            int f=0,l=m-1;
            while(f<l){
                swap(matrix,i,f,i,l);
                f++;
                l--;
            }
        }
    }
    
    public static void swap(int[][] matrix,int i1,int j1,int i2,int j2){
        int temp = matrix[i1][j1];
        matrix[i1][j1]=matrix[i2][j2];
        matrix[i2][j2]=temp;
    }
    
    public static void printMatrix(int[][] m){
        for(int i=0;i<m.length;i++){
            for(int j=0;j<m[0].length;j++){
                System.out.print(m[i][j]+" ");
            }
            System.out.println();
        }
    }
}
