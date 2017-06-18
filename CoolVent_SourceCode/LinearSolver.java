/* 20090616 This code suffered a major cleanup by MAMB.
LinearSolver.java

 * Created on 2005ï¿½ï¿½5ï¿½ï¿½17ï¿½ï¿½, ï¿½ï¿½ï¿½ï¿½10:40
 * This class is to solve linear equations:
 */
//package mmpn1;
import java.lang.Math.*;
import java.io.*;
import java.util.Arrays; //FADE added this

public class LinearSolver implements java.io.Serializable{

    public static double[] solvedx;
    public static double TINY;

    //** Creates a new instance of LinearSolver */
    public LinearSolver(int LayerNum) {
        solvedx = new double[LayerNum + 1];
        TINY = 1E-20;
    }

    public LinearSolver() {
    }

    public static void TDM(int nx, double bb[], double dtmp[], double aa[], double cc[]) { //FADE: nx is TMassNumber-1, 
        //Thomas Algorithm to solve the triangle matrix linear equations 
        int i, j;
        double r;
        double[] dd = new double[nx + 1];

        for (i = 1; i <= nx; ++i) {
            dd[i] = dtmp[i];
        }
        
        for (i = 2; i <= nx; ++i) {
            r = bb[i] / dd[i - 1];
            dd[i] = dd[i] - r * aa[i - 1];
            cc[i] = cc[i] - r * cc[i - 1];
        }

        // back substitution
        cc[nx] = cc[nx] / dd[nx]; //FADE: x_n = d'_n

        for (i = 2; i <= nx; ++i) {
            j = nx - i + 1; //FADE: "Backward index" because of back substitution
            cc[j] = (cc[j] - aa[j] * cc[j + 1]) / dd[j]; //FADE: x_i = d'_i-c'_i*x_{i+1}
        }

        //solution stored in cc
//        for (i = 1; i <= nx; i++) {
//            solvedx[i] = cc[i];
//        }
    }

    public static void ludcmp(double a[][], int n, int[] indx, double d) {
        //LU decomposition.
        //Given matrix a[1...n][1...n]

        int i, imax, j, k;
        double big, dum, sum, temp;
        double[] vv = new double[n + 1];

        d = 1.0;

        imax = 1; //initialization 

        for (i = 1; i <= n; i++) {
            big = 0.0;
            for (j = 1; j <= n; j++) {
                if ((temp = Math.abs(a[i][j])) > big) {
                    big = temp;
                }
            }
            if (big == 0.0) {
//                System.err.println("Singular matrix in routine ludcmp");	
                return;
            }

            vv[i] = 1.0 / big;
        }

        for (j = 1; j <= n; j++) {
            for (i = 1; i < j; i++) {
                sum = a[i][j];
                for (k = 1; k < i; k++) {
                    sum -= a[i][k] * a[k][j];
                }
                a[i][j] = sum;
            }
            big = 0.0;
            for (i = j; i <= n; i++) {
                sum = a[i][j];
                for (k = 1; k < j; k++) {
                    sum -= a[i][k] * a[k][j];
                }
                a[i][j] = sum;
                if ((dum = vv[i] * Math.abs(sum)) >= big) {
                    big = dum;
                    imax = i;
                }
            }

            if (j != imax) {
                for (k = 1; k <= n; k++) {
                    dum = a[imax][k];
                    a[imax][k] = a[j][k];
                    a[j][k] = dum;
                }
                d = -(d);
                vv[imax] = vv[j];
            }
            indx[j] = imax;

            if (a[j][j] == 0.0) {
                a[j][j] = TINY;
            }
            if (j != n) {
                dum = 1.0 / (a[j][j]);
                for (i = j + 1; i <= n; i++) {
                    a[i][j] *= dum;
                }
            }
        }
    }
    
    public static void lubksb(double a[][], int n, int[] indx, double b[]) {
//Back substitution
        int i, ii = 0, ip, j;
        double sum;

        for (i = 1; i <= n; i++) {
            ip = indx[i];
            sum = b[ip];
            b[ip] = b[i];
            if (ii != 0) {
                for (j = ii; j <= i - 1; j++) {
                    sum -= a[i][j] * b[j];
                }
            } else if (sum != 0) {
                ii = i;
            }
            b[i] = sum;
        }
        for (i = n; i >= 1; i--) {
            sum = b[i];
            for (j = i + 1; j <= n; j++) {
                sum -= a[i][j] * b[j];
            }
            b[i] = sum / a[i][i];
        }
    }

    public static void LUSolver(double a[][], int n, int[] indx, double[] b, double d) {
        //System.out.println("A "+Arrays.deepToString(a)+" Zonenumber "+n+" Index "+Arrays.toString(indx)+" B "+Arrays.toString(b)+ " d "+d);
        ludcmp(a, n, indx, d);
        lubksb(a, n, indx, b);
    }
    
    public static void Jacob(double[][] a, int n, double[] b, double[] x0, int maxiter, double[] xnew) {
//Jacobian iterative solver: programmed by Jinchao Yuan on 06/13/2006
        
        double[] xold = new double[n + 1];
        double tolerance = 1E-6;

        boolean continueflag = false;

        //initialization
        for (int i = 1; i < n; ++i) {
            xold[i] = x0[i];
        }
        for (int k = 1; k <= maxiter; ++k) {
            continueflag = false;

            for (int i = 1; i <= n; ++i) {
                xnew[i] = b[i];

                for (int j = 1; j < i; ++j) {
                    xnew[i] -= xold[j] * a[i][j];
                }
                for (int j = i + 1; j <= n; ++j) {
                    xnew[i] -= xold[j] * a[i][j];
                }
                xnew[i] /= a[i][i];

                if ((xnew[i] - xold[i]) > tolerance || (xnew[i] - xold[i]) < -tolerance) {
                    continueflag = continueflag || true;
                }
            }

            for (int i = 1; i <= n; ++i) {
                xold[i] = xnew[i];
            }
            if (continueflag == false) {
                return;
            }
        }
        System.out.println("solver gained Maxmium iteration = " + maxiter);
    }
    
    public static void main(String[] args)     {
        int n_X = 3;
       int[] indx = new int[n_X + 1];
        double d = 1.0;
        double[][] A = new double[n_X + 1][n_X + 1];
        double[] b = new double[n_X + 1];

        A[1][1] = 5;
        A[1][2] = 1;
        A[1][3] = 1;
        A[2][1] = -1;
        A[2][2] = 5;
        A[2][3] = 1;
        A[3][1] = 2;
        A[3][2] = 2;
        A[3][3] = -9;

        b[1] = 6;
        b[2] = 2;
        b[3] = 3;


        LinearSolver LUD = new LinearSolver();

        System.out.println("Use Jacob Iteration:");
        double[] solution = new double[n_X + 1];
        LUD.Jacob(A, n_X, b, new double[n_X], 100, solution);
        for (int i = 1; i <= n_X; i++) {
            System.out.println("x[" + i + "]= " + solution[i]);
        }
        LUD.ludcmp(A, n_X, indx, d);	//here indx is output

        System.out.println("d =" + d);
        for (int i = 1; i <= n_X; i++) {
            //System.out.println ("x["+ i +"]= " + b[i]);
            for (int j = 1; j <= n_X; j++) {
                System.out.println("A[" + i + "][" + j + "]= " + A[i][j]);
            }
        }

        LUD.lubksb(A, n_X, indx, b);	//here indx is input generated by ludcmp


        for (int i = 1; i <= n_X; i++) {
            //System.out.println ("x["+ i +"]= " + b[i]);
            for (int j = 1; j <= n_X; j++) {
                System.out.println("A[" + i + "][" + j + "]= " + A[i][j]);
            }
        }
        for (int i = 1; i <= n_X; i++) {
            System.out.println("x[" + i + "]= " + b[i]);
        }

        System.out.println("d =" + d);

        int i3 = 0;
        int j3 = 0;
        for (int i = 1; i <= 1000; i++) {
            if (Math.IEEEremainder(i, 10) == 3) {
                i3++;
            }

            if (Math.IEEEremainder(i, 100) >= 30 && Math.IEEEremainder(i, 100) <= 39) {
                i3++;
//				if(Math.IEEEremainder(i,10)==3)
//					i3--;
            }


            if (Math.IEEEremainder(i, 1000) >= 300 && Math.IEEEremainder(i, 1000) <= 399) {
                i3++;
                if (Math.IEEEremainder(i, 10) == 3) {
                    j3++;
                }

                if (Math.IEEEremainder(i, 100) >= 30 && Math.IEEEremainder(i, 100) <= 39) {
                    j3++;
                }
            }
        }
        System.out.println(i3 - j3);
    }
}
