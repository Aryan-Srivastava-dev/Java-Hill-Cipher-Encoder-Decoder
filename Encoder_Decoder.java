import java.util.Scanner;

public class Encoder_Decoder {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] A = {{2, 1}, {1, 2}};

        System.out.print("Press 1 to encode and 2 to decode: ");
        int choice = sc.nextInt();
        if(choice == 1)
            System.out.print("Enter a String to Encode: ");
        else if(choice == 2)
            System.out.print("Enter a String to Decode: ");
        else {
            System.out.println("Enter a valid choice");
            return;
        }
        String message = sc.next();

        int len = message.length();
        int row = A.length;
        int col = len/row + len%row;

        int[][] E = new int[row][col];
        int k=0;
        for(int i=0; i<col; i++) {
            for(int j=0; j<row; j++) {
                if(k >= len)
                    E[j][i] = 0;
                else
                    E[j][i] = Character.toUpperCase(message.charAt(k++)) - 'A' + 1;
            }
        }

        if(choice == 1)
            System.out.println(encode(E, A));
        else
            System.out.println(decode(E, A));
    }

    private static String encode(int[][] E, int[][] A) {
        int[][] D = multiply(A, E);
        String ans = "";

        reduce(D);

        for(int i=0; i<E[0].length; i++) {
            for(int j=0; j<E.length; j++) {
                ans += (char)(D[j][i] + 64);
            }
        }

        return ans;
    }

    private static String decode(int[][] D, int[][] A) {
        int detA = Determinant.solveDeterminant(A, A.length);
        if(detA == 0 || gcd(Math.max(detA, 26), Math.min(detA, 26)) > 1)
            return "The key matrix is not invertible, cannot decode.";
        int[][] E = multiply(inverse(A), D);
        String ans = "";

        reduce(E);

        for(int i=0; i<E[0].length; i++) {
            for(int j=0; j<E.length; j++) {
                ans += (char)(E[j][i] + 64);
            }
        }

        return ans;
    }

    private static void reduce(int[][] res) {
        for(int i=0; i<res.length; i++) {
            for(int j=0; j<res[i].length; j++) {
                if(res[i][j] < 0) {
                    int num = -1 * res[i][j];
                    int multiplier = num / 26 + num % 26;
                    res[i][j] += 26*multiplier;
                }
                res[i][j] %= 26;
                if(res[i][j] == 0)
                    res[i][j] = 26;
            }
        }
    }

    private static int[][] multiply(int[][] A, int[][] B) {
        int[][] res = new int[A.length][B[0].length];

        for(int i=0; i<A.length; i++) {
            for(int j=0; j<B[0].length; j++) {
                int sum = 0;
                for(int k=0; k<B.length; k++) {
                    sum += A[i][k] * B[k][j];
                }
                res[i][j] = sum;
            }
        }

        return res;
    }

    private static int[][] inverse(int[][] arr) {
        int len = arr.length;
        int[][] res = new int[len][len];
        int[][] temp = new int[len-1][len-1];
        int idxRow, idxCol = 0;
        int det = Determinant.solveDeterminant(arr, len);

        for(int row = 0; row < len; row++) {
            for (int col = 0; col < len; col++) {
                idxRow = 0;
                for (int i = 0; i < len; i++) {
                    for (int j = 0; j < len; j++) {
                        if(i != row && j != col) {
                            temp[idxRow][idxCol++] = arr[i][j];
                        }
                        if(idxCol == len-1) {
                            idxRow++;
                            idxCol = 0;
                        }
                    }
                }
                res[row][col] = (int)Math.pow(-1, row+col) * Determinant.solveDeterminant(temp, len-1);
            }
        }

        int var;
        for(int i=0; i<len; i++) {
            for(int j=i; j<len; j++) {
                var = res[i][j];
                res[i][j] = res[j][i];
                res[j][i] = var;
            }
        }

        int multiInvDet = findMultiplicativeInv(det);
        for(int i=0; i<len; i++) {
            for(int j=0; j<len; j++) {
                res[i][j] *= multiInvDet;
            }
        }

        reduce(res);

        return res;
    }

    private static int findMultiplicativeInv(int num) {
        for(int i=1; i<26; i++) {
            if((num * i) % 26 == 1)
                return i;
        }

        return 0;
    }

    private static int gcd(int a, int b) {
        if(b == 0)
            return a;

        return gcd(b, a%b);
    }
}
