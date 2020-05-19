/*
// Time Complexity : O(NW) where N is number of values and W is weight O(2^N) for brute-force
// Space Complexity : O(NW)
// Did this code successfully run on Leetcode : yes (leetcode playground)
// Any problem you faced while coding this : no

/*
Approach:
=========
Here [10,20,30] are given weights and we need to maximize total weight by using these weights. 2 constraints weight blocks and total weight of Knapsack. If recursive tree is drawn, we can observe there are overlapping subproblems. use dp in this case.
1. Initialize dp matrix of N(number of weights) and W(weight of knapsack) 
2. Calculate max value by using these conditions:
    i. If index less than weight of item, copy from previous index
    ii. If not, update knapsack[i][j] with max of weight_value + total_value_weight_steps_back and previous_step
3. Return knapsack max value at the last row, last column of matrix
*/

public class Main {

    //Brute-force
    public static int zeroOneKnapsack(int[] wt, int[] profit, int cap) {
        if(cap == 0) 
            return 0; 
        return helper(wt, profit, cap, 0); 
    }
    
    private static int helper(int[] wt, int[] profit, int cap, int index) {
        //base
        if( cap <= 0 || index >= wt.length)
            return 0; 
        //logic
        int choose = 0;
        if(wt[index] <= cap)
            choose = profit[index] + helper(wt, profit, cap - wt[index], index+1); 
        int notChoose = helper(wt, profit, cap, index+1); 
        return Math.max(choose, notChoose); 
    }

    //top-down approach
    public static int knapsackTD(int[] wt, int[] profit, int capacity) {
        // 2 changing arguments, capacity and index, so 2d array
        int[][] dp = new int[profit.length+1][capacity+1]; 
        int result = helperTD(wt, profit, capacity, 0, dp);
        return result;
    }
    
    private static int helperTD(int[] wt, int[] profit, int cap, int index, int[][] dp) {
        //base
        if(cap <= 0 || index >= profit.length)
            return 0; 
        //logic
        if(dp[index][cap] != 0)
            return dp[index][cap]; 
        int profit1 = 0, profit2 = 0; 
        if(wt[index] <= cap) 
            profit1 = profit[index] + helperTD(wt, profit, cap - wt[index], index + 1, dp); 
        profit2 = helperTD(wt, profit, cap, index+1, dp); 
        dp[index][cap] = Math.max(profit1, profit2); 
        return dp[index][cap];
    }
    
    //bottom-up
    public static int knapsackBU(int[] wt, int[] profit, int capacity) {
        if(capacity == 0) return 0; 
        int[][] dp = new int[wt.length+1][capacity+1]; 
        
        for(int i = 1; i <= wt.length; i++) {
            for(int j = 1; j <= capacity; j++) {
                if(j < wt[i-1]) {
                    dp[i][j] = dp[i-1][j]; 
                }
                else
                    dp[i][j] = Math.max(dp[i-1][j], profit[i-1] + dp[i-1][j-wt[i-1]]);
            }
        }
        return dp[wt.length][capacity]; 
    }

    public static void main(String[] args) {
       validateInput(); 
    }
    
    public static void validateInput() {
        int[] wt = new int[]{2,3,1,4}; 
        int[] profit = new int[]{4,5,3,7}; 
        assert knapsackTD(wt, profit, 5) == 10 : "10"; 
        assert knapsackTD(wt, profit, 7) == 14: "14"; 
        assert knapsackTD(wt, profit, 0) == 0: "0"; 
        assert knapsackTD(wt, profit, 3) == 7: "7"; 
        
        assert knapsackBU(wt, profit, 5) == 10 : "10 BU"; 
        assert knapsackBU(wt, profit, 7) == 14: "14 BU"; 
        assert knapsackBU(wt, profit, 0) == 0: "0 BU"; 
        assert knapsackBU(wt, profit, 3) == 7: "7 BU"; 
        
    }
}