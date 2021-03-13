import java.util.LinkedList;

public class DP1 {

  public static void print1D(int[] arr){
    for(int v : arr){
      System.out.print(v+" ");
    }
    System.out.println();
  }

  //Fib DP
  public static int fib_tab(int n) {
    int[] dp = new int[n + 1];

    for (int i = 0; i <= n; i++) {
      if (i <= 1) {
        dp[i] = i;
        continue;
      }
      dp[i] = dp[i - 1] + dp[i - 2];
    }
    return dp[n];
  }

  // Optimize version of Fabonacci
  public static int fib_optimiz(int n) {
    int a = 0;
    int b = 1;
    int sum = 0;
    for (int i = 1; i <= n; i++) {
      sum = a + b;
      a = b;
      b = sum;
    }
    return a;
  }

  public static void fib() {
    System.out.println(fib_tab(5));
    System.out.println(fib_optimiz(0));
  }

  public static int srcToDest_rec(int sr, int sc, int dr, int dc) {
    if (sc == dc && sr == dr) return 1;

    int count = 0;
    if (sc + 1 <= dc) {
      count += srcToDest_rec(sr, sc + 1, dr, dc);
    }
    if (sr + 1 <= dr) {
      count += srcToDest_rec(sr + 1, sc, dr, dc);
    }
    if (sc + 1 <= dc && sr + 1 <= dr) {
      count += srcToDest_rec(sr + 1, sc + 1, dr, dc);
    }
    return count;
  }

  public static int srcToDest_mem(int sr, int sc, int dr, int dc, int[][] dp) {
    if (sc == dc && sr == dr) return dp[sc][dc] = 1;

    if (dp[sr][sc] != 0) return dp[sr][sc];

    int count = 0;

    if (sc + 1 <= dc) {
      count += srcToDest_rec(sr, sc + 1, dr, dc);
    }
    if (sr + 1 <= dr) {
      count += srcToDest_rec(sr + 1, sc, dr, dc);
    }
    if (sc + 1 <= dc && sr + 1 <= dr) {
      count += srcToDest_rec(sr + 1, sc + 1, dr, dc);
    }
    return dp[sr][sc] = count;
  }

  public static int srcToDest_tab(int SR, int SC, int dr, int dc, int[][] dp) {
    for (int sr = dr; sr >= SR; sr--) {
      for (int sc = dc; sc >= SC; sc--) {
        if (sc == dc && sr == dr) {
          dp[sr][sc] = 1;
          continue;
        }

        int count = 0;
        if (sc + 1 <= dc) {
          count += dp[sr][sc + 1]; //srcToDest_rec(sr, sc + 1, dr, dc);
        }
        if (sr + 1 <= dr) {
          count += dp[sr + 1][sc]; //srcToDest_rec(sr + 1, sc, dr, dc);
        }
        if (sc + 1 <= dc && sr + 1 <= dr) {
          count += dp[sr + 1][sc + 1]; //srcToDest_rec(sr + 1, sc + 1, dr, dc);
        }
        dp[sr][sc] = count;
      }
    }
    return dp[SR][SC];
  }

  public static int srcToDestInfinteStep_tab(
    int SR,
    int SC,
    int dr,
    int dc,
    int[][] dp
  ) {
    for (int sr = dr; sr >= SR; sr--) {
      for (int sc = dc; sc >= SC; sc--) {
        if (sc == dc && sr == dr) {
          dp[sr][sc] = 1;
          continue;
        }

        int count = 0;
        for (int jump = 0; sc + jump <= dc; jump++) {
          count += dp[sr][sc + jump]; //srcToDest_rec(sr, sc + 1, dr, dc);
        }
        for (int jump = 0; sr + jump <= dr; jump++) {
          count += dp[sr + jump][sc]; //srcToDest_rec(sr + 1, sc, dr, dc);
        }
        for (int jump = 0; sc + jump <= dc && sr + jump <= dr; jump++) {
          count += dp[sr + jump][sc + jump]; //srcToDest_rec(sr + 1, sc + 1, dr, dc);
        }
        dp[sr][sc] = count;
      }
    }
    return dp[SR][SC];
  }

  public static void srcToDest() {
    int n = 2, m = 2;
    int[][] dp = new int[n + 1][m + 1];
    // System.out.println(srcToDest_rec(0, 0, n, m));
    // System.out.println(srcToDest_mem(0, 0, n, m, dp));
    // System.out.println(srcToDest_tab(0, 0, n, m, dp));
    System.out.println(srcToDestInfinteStep_tab(0, 0, n, m, dp));
  }

  public static int stepByDice_rec(int src, int des) {
    if (src == des) {
      return 1;
    }

    int count = 0;
    for (int dice = 1; src + dice <= des && dice <= 6; dice++) {
      count += stepByDice_rec(src + dice, des);
    }
    return count;
  }

  public static int stepByDice_mem(int src, int des, int dp[]) {
    if (src == des) {
      return dp[src] = 1;
    }

    if (dp[src] != 0) return dp[src];

    int count = 0;
    for (int dice = 1; src + dice <= des && dice <= 6; dice++) {
      count += stepByDice_rec(src + dice, des);
    }
    return dp[src] = count;
  }

  public static int stepByDice_tab(int SRC, int des, int dp[]) {
    for (int src = des; src >= SRC; src--) {
      if (src == des) {
        dp[src] = 1;
        continue;
      }

      int count = 0;
      for (int dice = 1; src + dice <= des && dice <= 6; dice++) {
        count += dp[src + dice]; //stepByDice_rec(src + dice, des);
      }
      dp[src] = count;
    }
    return dp[SRC];
  }

  public static int stepByDice_optimiz(
    int SRC,
    int des,
    LinkedList<Integer> list
  ) {
    for (int src = des; src >= SRC; src--) {
      if (src >= des - 1) {
        list.addFirst(1);
        continue;
      }

      if (list.size() <= 6) {
        int v = list.getFirst() * 2;
        list.addFirst(v);
      } else {
        int v = (list.getFirst() * 2) - list.getLast();
        list.addFirst(v);
        list.removeLast();
      }
    }
    return list.getFirst();
  }

  public static void stepByDice() {
    int n = 10;
    int[] dp = new int[n + 1];
    LinkedList<Integer> list = new LinkedList<>();
    // System.out.println(stepByDice_rec(0, n));
    // System.out.println(stepByDice_mem(0, n, dp));
    // System.out.println(stepByDice_tab(0, n, dp));
    // System.out.println(stepByDice_optimiz(0, n, list));
    // System.out.println(list);
  }

  public static int stepByArray_rec(int sr, int des, int[] steps, int[] dp) {
    if (sr == des) return dp[sr] = 1;

    if (dp[sr] != 0) return dp[sr];
    int count = 0;
    for (int itr = 0; itr < steps.length && steps[itr] + sr <= des; itr++) {
      count += stepByArray_rec(sr + steps[itr], des, steps, dp);
    }
    return dp[sr] = count;
  }

  public static int stepByArray_tab(int SR, int des, int[] steps, int[] dp) {
    for (int sr = des; sr >= 0; sr--) {
      if (sr == des) {
        dp[sr] = 1;
        continue;
      }
      int count = 0;
      for (int itr = 0; itr < steps.length && steps[itr] + sr <= des; itr++) {
        count += dp[sr + steps[itr]]; // stepByArray_rec(sr + steps[itr], des, steps, dp);
      }
      dp[sr] = count;
    }
    return dp[SR];
  }

  public static int stepByArray_optimiz(
    int SR,
    int des,
    int[] steps,
    int[] dp
  ) {
    for (int sr = des; sr >= 0; sr--) {
      if (sr == des) {
        dp[sr] = 1;
        continue;
      }
      int count = 0;
      for (int itr = 0; itr < steps.length && steps[itr] + sr <= des; itr++) {
        count += dp[sr + steps[itr]]; // stepByArray_rec(sr + steps[itr], des, steps, dp);
      }
      dp[sr] = count;
    }
    return dp[SR];
  }

  public static void stepByArray() {
    int n = 10;
    int[] steps = { 2, 3, 4, 6 };
    int[] dp = new int[n + 1];
    LinkedList<Integer> list = new LinkedList<>();
    // System.out.println(stepByArray_rec(0, n, steps, dp));
    System.out.println(stepByArray_tab(0, n, steps, dp));
    print1D(dp);
  }

  public static void main(String[] args) {
    // fib();
    // srcToDest();
    stepByDice();
    stepByArray();
  }
}
