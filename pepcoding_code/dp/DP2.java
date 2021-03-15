import java.util.Arrays;

class DP2 {

  static int mod = (int) 1e9 + 7;

  public static void print1D(int[] arr) {
    for (int v : arr) {
      System.out.print(v + " ");
    }
    System.out.println();
  }

  public static long friendPairsPrint(String res, String friends) {
    //   System.out.print(res+" "+friends);

    if (friends.length() <= 0) {
      System.out.println(res);
      return 1;
    }

    long count = 0;
    count +=
      friendPairsPrint(res + friends.charAt(0) + " ", friends.substring(1));

    if (friends.length() > 1) for (int i = 1; i < friends.length(); i++) {
      // System.out.println("red");
      String temp = friends
        .substring(1)
        .replaceFirst(friends.charAt(i) + "", "");
      count +=
        friendPairsPrint(
          res + friends.charAt(0) + friends.charAt(i) + " ",
          temp
        );
    }
    return count;
  }

  public static void friendPairsPrint() {
    System.out.println(friendPairsPrint("", "ABCDEF"));
  }

  static int maxGold(int n, int m, int M[][]) {
    int max = Integer.MIN_VALUE;

    int[][] dir = { { -1, 1 }, { 0, 1 }, { 1, 1 } };
    int[][] dp = new int[M.length][M[0].length];

    for (int[] arr : dp) Arrays.fill(arr, -1);

    // for (int i = 0; i < n; i++) {
    //   max = Math.max(max, maxGold_rec(i, 0, M, dp));
    // }
    // System.out.println(max);

    maxGold_tab(M,dir,dp);

    return maxGold_optimiz(M, dir);
  }

  static int maxGold_rec(int i, int j, int M[][], int[][] dp) {
    // System.out.println(i + " " + j);

    if (j == M[0].length - 1) {
      return dp[i][j] = M[i][j];
    }

    if (dp[i][j] != -1) return dp[i][j];
    int max = 0;

    if (i - 1 >= 0 && j + 1 < M[0].length) max =
      Math.max(max, maxGold_rec(i - 1, j + 1, M, dp));

    if (j + 1 < M[0].length) max = Math.max(max, maxGold_rec(i, j + 1, M, dp));

    if (i + 1 < M[0].length && j + 1 < M[0].length) max =
      Math.max(max, maxGold_rec(i + 1, j + 1, M, dp));

    return dp[i][j] = max + M[i][j];
  }

  static int maxGold_tab(int M[][], int[][] dir, int[][] dp) {
    int n = M.length;
    int m = M[0].length;

    for (int j = m - 1; j >= 0; j--) {
      for (int i = n - 1; i >= 0; i--) {
        int max = 0;
        for (int d = 0; d < dir.length; d++) {
          if (
            i + dir[d][0] >= 0 && i + dir[d][0] < n && j + dir[d][1] < m
          ) max = Math.max(max, dp[i + dir[d][0]][j + dir[d][1]]);
        }

        dp[i][j] = max + M[i][j];
      }
    }
    int maxGold = 0;
    for (int[] arr : dp) {
      print1D(arr);
      maxGold = Math.max(maxGold, arr[0]);
    }
    return maxGold;
  }

  static int maxGold_optimiz(int M[][], int[][] dir) {
    int n = M.length;
    int m = M[0].length;
    int[] sls = new int[n];
    for (int j = m - 1; j >= 0; j--) {
      int[] temp = new int[n];
      for (int i = n - 1; i >= 0; i--) {
       
          int max = 0;
          for (int d = 0; d < dir.length; d++) {
            if (i + dir[d][0] >= 0 && i + dir[d][0] < n) max =
              Math.max(max, sls[i + dir[d][0]]);
          }
          temp[i] = max + M[i][j];
        
      }
      sls = temp;
      print1D(temp);
    }
    int maxGold = 0;
    for (int v : sls) maxGold = Math.max(maxGold, v);
    return maxGold;
  }

  static void maxGold() {
    int[][] M = {
      { 77, 15, 93, 35, 86, 92, 49 },
      { 21, 62, 27, 90, 59, 63, 26 },
      { 40, 26, 72, 36, 11, 68, 67 },
      { 29, 82, 30, 62, 23, 67, 35 },
    };

    // int[][] M = {
    //   { 1, 3, 1, 5 },
    //   { 2, 2, 4, 1 },
    //   { 5, 0, 2, 3 },
    //   { 0, 6, 1, 2 },
    // };

    System.out.println(maxGold(4, 4, M));
  }

  public static long countFriendsPairings(int n, long[] dp) {
    if (n <= 1) {
      return dp[n] = 1;
    }
    if (dp[n] != 0) return dp[n];
    long single = countFriendsPairings(n - 1, dp);
    long pairs = (long) (countFriendsPairings(n - 2, dp) * (n - 1));
    return dp[n] = (single % mod + pairs % mod) % mod;
  }

  public static long countFriendsPairings_tab(int N, long[] dp) {
    for (int n = 0; n <= N; n++) {
      if (n <= 1) {
        dp[n] = 1;
        continue;
      }
      long single = dp[n - 1]; // countFriendsPairings(n - 1, dp);
      long pairs = dp[n - 2] * (n - 1); // (long) (countFriendsPairings(n - 2, dp) * (n - 1));
      dp[n] = (single % mod + pairs % mod) % mod;
    }
    return dp[N];
  }

  public static void countFriendsPairings() {
    int n = 4;
    long[] dp = new long[n + 1];
    System.out.println(countFriendsPairings(n, dp));
    // System.out.println(countFriendsPairings_optimiz(n));
    // for (long v : dp) System.out.print(v + " ");
    // System.out.println();
  }

  public static void main(String[] args) {
    // countFriendsPairings();
    // friendPairsPrint();
    maxGold();
  }
}
