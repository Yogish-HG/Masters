package org.example;

public class ContArray {

//    Given a binary array nums, return the maximum length of a contiguous subarray with an equal number of 0 and 1.
//    Example 1:
//
//    Input: nums = [0,1]
//    Output: 2
//    Explanation: [0, 1] is the longest contiguous subarray with an equal number of 0 and 1.
//    Example 2:
//
//    Input: nums = [0,1,0]
//    Output: 2
//    Explanation: [0, 1] (or [1, 0]) is a longest contiguous subarray with equal number of 0 and 1.

    public static int subArrayMax(int[] nums) {
        int Total = 0;
        for (int i = 0; i < nums.length; i++) {
            int Zs = 0;
            int Os = 0;
            for (int j = i; j < nums.length; j++) {
                if (nums[j] == 0) {
                    Zs++;
                } else {
                    Os++;
                }
                if (Zs == Os && Total < Zs*2) {
                    Total = Zs*2;
                }
            }
        }
        return Total;
    }

    public static void main(String[] args) {
        int[] arr = {0,1,0};
        System.out.println(subArrayMax(arr));
    }
}
