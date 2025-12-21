public class Determinant {
    public static void main(String[] args) {
        int[][] arr = {{3, 2, -2}, {1, 4, -5}, {2, 1, -3}};
        System.out.println(solveDeterminant(arr, 2));
    }

    public static int solveDeterminant(int[][] arr, int n) {
        if(n == 1) {
            return arr[0][0];
        }
        if(n == 2) {
            return (arr[0][0] * arr[1][1]) - (arr[0][1] * arr[1][0]);
        }

        int[][] temp = new int[arr.length-1][arr.length-1];
        int sum = 0;

        for(int k=0; k<arr.length; k++) {
            for (int i = 1, ii = 0; i < arr.length; i++, ii++) {
                for (int j = 0, jj = 0; j < arr.length; j++) {
                    if (j != k)
                        temp[ii][jj++] = arr[i][j];
                }
            }
            sum += (int) Math.pow(-1, k) * arr[0][k] * solveDeterminant(temp, n - 1);
        }

        return sum;
    }
}
