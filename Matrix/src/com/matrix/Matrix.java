package com.matrix;

/**
 * @author Vadim Makarov
 */
public class Matrix {
    private Complex[][] field;
    private int sizeM;
    private int sizeN;

    /**
     * Empty signature constructor
     */
    public Matrix() throws MatrixError{
        this(0,0);
    }

    /**
     * Constructor
     * @param sizeM the number of the rows
     * @param sizeN the number of the columns
     */
    public Matrix(int sizeM, int sizeN) throws MatrixError {
        if (sizeN < 1 || sizeM < 1) {
            throw new MatrixError("The wrong size matrix");
        }
        this.sizeM = sizeM;
        this.sizeN = sizeN;
        this.field = new Complex[sizeM][sizeN];
    }

    /**
     * Constructor
     * @param complexField the two-dimensional array of complex numbers
     */
    public Matrix(Complex[][] complexField) throws MatrixError {
        this.sizeN = complexField[0].length;
        this.sizeM = complexField.length;
        if (sizeN < 1 || sizeM < 1) {
            throw new MatrixError("The wrong size matrix");
        }

        this.field = complexField;
    }

    /**
     * Constructor
     * @param intField the two-dimensional array of integer numbers
     */
    public Matrix(int[][] intField) {
        this.sizeN = intField[0].length;
        this.sizeM = intField.length;
        Complex[][] complexField = new Complex[sizeM][sizeN];
        for (int i = 0; i < sizeM; i++) {
            for (int j = 0; j < sizeN; j++) {
                complexField[i][j] = new Complex(intField[i][j], 0);
            }
        }
        field = complexField;
    }

    /**
     * Prints matrix
     */
    public void print() {
        for (int i = 0; i < sizeM; i++) {
            for (int j = 0; j < sizeN; j++) {
                field[i][j].print();
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Addition operation
     * @param secondMatrix the second argument for binary operation
     * @return Summation result
     * @throws MatrixError if your Matrix has an error
     */
    public Matrix plus(Matrix secondMatrix) throws MatrixError {
        if (sizeM != secondMatrix.sizeM || sizeN != secondMatrix.sizeN) {
            throw new MatrixError("Incompatible matrix sizes");
        }
        Matrix result = new Matrix(new Complex[sizeM][sizeN]);
        for (int i = 0; i < sizeM; i++) {
            for (int j = 0; j < sizeN; j++) {
                result.field[i][j] = field[i][j].plus(secondMatrix.field[i][j]);
            }
        }
        return result;
    }

    /**
     * Subtraction operation
     * @param secondMatrix the second argument for binary operation
     * @return Subtraction result
     * @throws MatrixError if your Matrix has an error
     */
    public Matrix minus(Matrix secondMatrix) throws MatrixError {
        if (sizeM != secondMatrix.sizeN) {
            throw new MatrixError("Incompatible matrix sizes");
        }
        Matrix result = new Matrix(new Complex[sizeM][sizeN]);
        for (int i = 0; i < sizeM; i++) {
            for (int j = 0; j < sizeN; j++) {
                result.field[i][j] = field[i][j].minus(secondMatrix.field[i][j]);
            }
        }
        return result;
    }

    /**
     * Multiplication operation
     * @param secondMatrix the second argument for binary operation
     * @return Multiplication result
     * @throws MatrixError if your Matrix has an error
     */
    public Matrix multiply(Matrix secondMatrix) throws MatrixError {
        if (sizeM != secondMatrix.sizeN) {
            throw new MatrixError("Incompatible matrix sizes");
        }
        Matrix result = new Matrix(new Complex[sizeM][secondMatrix.sizeN]);
        for (int i = 0; i < sizeM; i++) {
            for (int j = 0; j < secondMatrix.sizeN; j++) {
                Complex sum = new Complex(0, 0);
                for (int k = 0; k < secondMatrix.sizeM; k++) {
                    sum = sum.plus(this.field[i][k].multiply(secondMatrix.field[k][j]));
                }
                result.field[i][j] = sum;
            }
        }
        return result;
    }

    /**
     * Matrix transpose operation
     * @return Transposed matrix
     */
    public Matrix transpose() throws MatrixError {
        Matrix result = new Matrix(new Complex[sizeN][sizeM]);
        for (int i = 0; i < sizeM; i++) {
            for (int j = 0; j < sizeN; j++) {
                result.field[j][i] = field[i][j];
            }
        }
        return result;
    }

    /**
     * Сalculates the determinant
     * @return determinant
     * @throws MatrixError if your Matrix has an error
     */
    public Complex findDeterminant() throws MatrixError {
        if (sizeM != sizeN) {
            throw new MatrixError("Matrix is not square");
        }
        if (sizeM == 1) {
            return field[0][0];
        } else {
            Complex result = new Complex(0, 0);
            for (int i = 0; i < sizeN; i++) {
                Matrix miniMatrix = new Matrix(new Complex[sizeM-1][sizeN-1]);
                int indexM = 0;
                int indexN = 0;
                for (int j = 0; j < sizeN; j++) {
                    if (i == j){
                        continue;
                    }
                    for (int k = 1; k < sizeM; k++) {
                        miniMatrix.field[indexM][indexN] = field[k][j];
                        indexM++;
                    }
                    indexM = 0;
                    indexN++;
                }
                if (i % 2 == 0){
                    result = result.plus(miniMatrix.findDeterminant().multiply(field[0][i]));
                } else {
                    result = result.minus(miniMatrix.findDeterminant().multiply(field[0][i]));
                }
            }
            return result;
        }


    }

    /**
     * Returns one element of the matrix
     * @param m the row index
     * @param n the column index
     * @return element[m][n]
     */
    public Complex getElement(int m, int n){
        return this.field[m][n];
    }

    /**
     * Puts the value in the matrix
     * @param number the complex value
     * @param m the row index
     * @param n the column index
     */
    public void setElement(Complex number, int m, int n){
        this.field[m][n] = number;
    }

    /**
     * Puts the value in the matrix
     * @param number the integer value
     * @param m the row index
     * @param n the column index
     */
    public void setElement(int number, int m, int n){
        this.field[m][n] = new Complex(number,0);
    }

    /**
     * Error connected with Matrix
     */
    static class MatrixError extends Exception {
        MatrixError(String message) {
            super(message);
        }
    }

}
