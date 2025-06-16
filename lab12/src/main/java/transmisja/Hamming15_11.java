package transmisja;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;

public class Hamming15_11 {
    static double[][] P = {
            {1, 1, 0, 0}, {1, 0, 1, 0}, {0, 1, 1, 0}, {1, 1, 1, 0},
            {1, 0, 0, 1}, {0, 1, 0, 1}, {1, 1, 0, 1}, {0, 0, 1, 1},
            {1, 0, 1, 1}, {0, 1, 1, 1}, {1, 1, 1, 1}
    };

    public static int[] encode(int[] bits_in) {
        int k = 11;
        int m = 4;
        int n = 15;

        double[][] G = new double[k][n];

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < m; j++) {
                G[i][j] = P[i][j];
            }
            for (int j = 0; j < k; j++) {
                G[i][m + j] = (i == j) ? 1 : 0;
            }
        }

        double[][] bits = new double[bits_in.length/11][11];
        for (int i = 0; i < bits_in.length / 11; i++) {
            for (int j = 0; j < 11; j++) {
                bits[i][j] = bits_in[i * 11 + j];
            }
        }
        DMatrixRMaj G_DMatrix = new DMatrixRMaj(G);

        DMatrixRMaj b = new DMatrixRMaj(bits);
        DMatrixRMaj c = new DMatrixRMaj(bits_in.length / 11, n);
        CommonOps_DDRM.mult(b, G_DMatrix, c);

        for (int i = 0; i < c.numRows; i++) {
            for (int j = 0; j < c.numCols; j++) {
                c.set(i, j, c.get(i, j) % 2);
            }
        }

        int[] bits_out = new int[c.numRows * c.numCols];
        for (int i = 0; i < c.numRows; i++) {
            for (int j = 0; j < c.numCols; j++) {
                bits_out[i * c.numCols + j] = (int) c.get(i, j);;
            }
        }
        return bits_out;
    }

    public static int[] decode(int[] bits_in) {
        int k = 11;
        int m = 4;
        int n = 15;

        DMatrixRMaj P_D = new DMatrixRMaj(P);
        DMatrixRMaj P_T = new DMatrixRMaj(P_D.numCols, P_D.numRows);
        CommonOps_DDRM.transpose(P_D, P_T);

        double[][] H = new double[n-k][n];

        for (int i = 0; i < n-k; i++) {
            for (int j = 0; j < n-k; j++) {
                H[i][j] = (i == j) ? 1 : 0;
            }
            for (int j = 0; j < k; j++) {
                H[i][n - k + j] = P_T.get(i, j);
            }
        }
        DMatrixRMaj c = new DMatrixRMaj(bits_in.length / n, n);
        for (int i = 0; i < bits_in.length / n; i++) {
            for (int j = 0; j < 15; j++) {
                c.set(i,j,bits_in[i * 15 + j]);
            }
        }

        DMatrixRMaj H_DMatrix = new DMatrixRMaj(H);
        DMatrixRMaj H_T = new DMatrixRMaj(H_DMatrix.numCols, H_DMatrix.numRows);
        CommonOps_DDRM.transpose(H_DMatrix,H_T);
        DMatrixRMaj s = new DMatrixRMaj(bits_in.length / n, n-k);
        CommonOps_DDRM.mult(c, H_T, s);

        for (int i = 0; i < s.numRows; i++) {
            for (int j = 0; j < s.numCols; j++) {
                s.set(i, j, s.get(i, j) % 2);
            }
        }

//        for (int i = 0; i < s.numRows; i++) {
//            int S = 0;
//            for (int j = 0; j < s.numCols; j++) {
//                S += ((int)s.get(i, j)) << j;
//            }
//            if (S != 0) {
//                if((int)c.get(i,S-1) == 0){
//                    c.set(i,S-1,1);
//                }
//                else {
//                    c.set(i,S-1,0);
//                }
//            }
//        }

        for (int i = 0; i < s.numRows; i++) {
            for (int j = 0; j < n; j++) {
                boolean match = true;
                for (int l = 0; l < m; l++) {
                    if ((int) s.get(i, l) != (int) H_DMatrix.get(l, j)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    c.set(i, j, 1 - (int) c.get(i, j));
                    break;
                }
            }
        }
        int[] bits_out = new int[c.numRows * k];

        for (int i = 0; i < c.numRows; i++) {
            for (int j = 0; j < k; j++) {
                bits_out[i * k + j] = (int) c.get(i, m + j);
            }
        }

        return bits_out;

    }
}
