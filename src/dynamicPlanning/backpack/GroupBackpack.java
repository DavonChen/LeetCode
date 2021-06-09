package dynamicPlanning.backpack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author davon
 */
public class GroupBackpack {
    public static void main(String[] args) {
        // 物品分组 0;  1,2;  3,4,5;  6,7,8,9;
        List<Integer> group1 = Arrays.asList(0);
        List<Integer> group2 = Arrays.asList(1, 2);
        List<Integer> group3 = Arrays.asList(3, 4, 5);
        List<Integer> group4 = Arrays.asList(6, 7, 8, 9);
        ArrayList<List<Integer>> groups = new ArrayList<List<Integer>>(){{
           add(group1);
           add(group2);
           add(group3);
           add(group4);
        }};
        // 物品体积
        int[] cArr = {3, 1, 7, 6, 2, 4, 8, 7, 9, 5};
        // 物品价值
        int[] wArr = {3, 2, 6, 4, 5, 10, 9, 3, 7, 6};
        // 背包容量
        int limit = 6;
        groupBackpack(groups, cArr, wArr, limit, true);
        groupBackpack(groups, cArr, wArr, limit, false);
        groupBackpackOptimize(groups, cArr, wArr, limit, true);
        groupBackpackOptimize(groups, cArr, wArr, limit, false);
    }

    // 使用二维数组存储状态, loopIFirst 表示是否先循环 i
    public static int groupBackpack(ArrayList<List<Integer>> groups, int[] cArr, int[] wArr, int limit, boolean loopIFirst) {
        int[][] dp = new int[groups.size() + 1][limit + 1];
        List<Integer> group;
        for (int k = 0; k < groups.size(); k++) {
            group = groups.get(k);
            if (loopIFirst) {
                for (Integer i : group) {
                    for (int v = limit; v >= 0; v--) {
                        if (v >= cArr[i]) {
                            dp[k + 1][v] = Math.max(Math.max(dp[k][v], dp[k][v - cArr[i]] + wArr[i]), dp[k + 1][v]);
                        } else {
                            dp[k + 1][v] = Math.max(dp[k][v], dp[k + 1][v]);
                        }
                    }
                }
            } else {
                for (int v = limit; v >= 0; v--) {
                    for (Integer i : group) {
                        if (v >= cArr[i]) {
                            dp[k + 1][v] = Math.max(Math.max(dp[k][v], dp[k][v - cArr[i]] + wArr[i]), dp[k + 1][v]);
                        } else {
                            dp[k + 1][v] = Math.max(dp[k][v], dp[k + 1][v]);
                        }
                    }
                }
            }
        }

        System.out.println("\n==========groupBackpack : loopIFirst = " + loopIFirst + "==========");
        for (int[] ints : dp) {
            System.out.println(Arrays.toString(ints));
        }
        return dp[groups.size()][limit];
    }

    // 使用一维数组存储状态
    public static int groupBackpackOptimize(ArrayList<List<Integer>> groups, int[] cArr, int[] wArr, int limit, boolean loopIFirst) {
        int[] dp = new int[limit + 1];
        List<Integer> group;
        for (int k = 0; k < groups.size(); k++) {
            group = groups.get(k);
            if (loopIFirst) {
                for (Integer i : group) {
                    for (int v = limit; v >= 0; v--) {
                        if (v >= cArr[i]) {
                            dp[v] = Math.max(dp[v], dp[v - cArr[i]] + wArr[i]);
                        }
                    }
                }
            } else {
                for (int v = limit; v >= 0; v--) {
                    for (Integer i : group) {
                        if (v >= cArr[i]) {
                            dp[v] = Math.max(dp[v], dp[v - cArr[i]] + wArr[i]);
                        }
                    }
                }
            }

        }

        System.out.println("\n==========groupBackpackOptimize : loopIFirst = " + loopIFirst + " ==========");
        System.out.println(Arrays.toString(dp));
        return dp[limit];
    }

}
