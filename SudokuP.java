import java.io.PrintStream;
import java.util.Random;

public class SudokuP
{
  public SudokuP() {}
  
  private static int[][] a = new int[9][9];
  
  public static char[][] puzzle() {
    int i = 1;
    
    generate();
    randomGen(1);
    randomGen(0);
    
    Random localRandom = new Random();
    int[] arrayOfInt = { 0, 3, 6 };
    for (int m = 0; m < 2; m++) {
      int j = arrayOfInt[localRandom.nextInt(arrayOfInt.length)];
      int k;
      do { k = arrayOfInt[localRandom.nextInt(arrayOfInt.length)];
      } while (j == k);
      if (i == 1) {
        rowChange(j, k);
      } else {
        colChange(j, k);
      }
      i++;
    }
    
    //this randomly removes values from the randomly generated sudoku board
    drillHoles();

    char[][] arrayOfChar = new char[9][9];
    for (int n = 0; n < 9; n++) {
      for (int i1 = 0; i1 < 9; i1++) {
        if (a[n][i1] == 0) {
          arrayOfChar[n][i1] = 46;
        } else {
          arrayOfChar[n][i1] = Integer.toString(a[n][i1]).charAt(0);
        }
      }
    }
    return arrayOfChar;
  }
  

  private static void generate()
  {
    int i = 1;int j = 1;
    for (int k = 0; k < 9; k++) {
      i = j;
      for (int m = 0; m < 9; m++) {
        if (i <= 9) {
          a[k][m] = i;
          i++;
        } else {
          i = 1;
          a[k][m] = i;
          i++;
        }
      }
      j = i + 3;
      if (i == 10) j = 4;
      if (j > 9) j = j % 9 + 1;
    }
  }
  
  private static void randomGen(int paramInt) {
    int k = 2;int m = 0;
    Random localRandom = new Random();
    for (int n = 0; n < 3; n++) {
      int i = localRandom.nextInt(k - m + 1) + m;
      int j;
      do { j = localRandom.nextInt(k - m + 1) + m;
      } while (i == j);
      k += 3;m += 3;
      if (paramInt == 1) {
        permutationRow(i, j);
      } else if (paramInt == 0) {
        permutationCol(i, j);
      }
    }
  }
  
  private static void permutationRow(int paramInt1, int paramInt2) {
    for (int j = 0; j < 9; j++) {
      int i = a[paramInt1][j];
      a[paramInt1][j] = a[paramInt2][j];
      a[paramInt2][j] = i;
    }
  }
  
  private static void permutationCol(int paramInt1, int paramInt2)
  {
    for (int j = 0; j < 9; j++) {
      int i = a[j][paramInt1];
      a[j][paramInt1] = a[j][paramInt2];
      a[j][paramInt2] = i;
    }
  }
  
  private static void rowChange(int paramInt1, int paramInt2)
  {
    for (int j = 1; j <= 3; j++) {
      for (int k = 0; k < 9; k++) {
        int i = a[paramInt1][k];
        a[paramInt1][k] = a[paramInt2][k];
        a[paramInt2][k] = i;
      }
      paramInt1++;
      paramInt2++;
    }
  }
  
  private static void colChange(int paramInt1, int paramInt2)
  {
    for (int j = 1; j <= 3; j++) {
      for (int k = 0; k < 9; k++) {
        int i = a[k][paramInt1];
        a[k][paramInt1] = a[k][paramInt2];
        a[k][paramInt2] = i;
      }
      paramInt1++;
      paramInt2++;
    }
  }
  
  private static void drillHoles()
  {
    Random localRandom = new Random();
    for (int k = 0; k < 9; k++) {
      int i = localRandom.nextInt(6) + 1;
      for (int m = 0; m < i; m++) {
        int j = localRandom.nextInt(9);
        a[k][j] = 0;
      }
    }
  }
  
  public static void print() {
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        System.out.print(a[i][j] + ", ");
      }
      System.out.println();
    }
  }
}