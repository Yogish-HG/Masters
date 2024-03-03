package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
//
//        You may assume that each input would have exactly one solution, and you may not use the same element twice.
//
//        You can return the answer in any order.
//
//
//
//        Example 1:
//
//        Input: nums = [2,7,11,15], target = 9
//        Output: [0,1]
//        Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
//        Example 2:
//
//        Input: nums = [3,2,4], target = 6
//        Output: [1,2]
public class Main {

    public static int[] twoSum(int[] nums, int target) {
        ArrayList<Integer> arr = new ArrayList<>();
        for(int i = 0; i< nums.length-1; i++) {
            arr.add(i);
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    arr.add(j);
                    break;
                }
            }
            if(arr.size() == 2){
                break;
            }
            if(arr.size() == 1){
                arr.clear();
            }
        }
        if(arr.size() ==0){
            return new int[]{};
        }
        return new int[]{arr.get(0), arr.get(1)};
    }

//    Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
//
//    Notice that the solution set must not contain duplicate triplets.
//    Example 1:
//
//    Input: nums = [-1,0,1,2,-1,-4]
//    Output: [[-1,-1,2],[-1,0,1]]
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> resInt = new ArrayList<>();
        for(int i =0; i<nums.length-2 ; i++){
            for(int j = i+1; j < nums.length-1; j++){
                int Res = nums[i] + nums[j];
                for(int k=j+1;k<nums.length;k++ ){
                    if(Res + nums[k] == 0){

                        ArrayList<Integer> arrList = new ArrayList<>();
                        arrList.add(nums[i]);
                        arrList.add(nums[j]);
                        arrList.add(nums[k]);
                        boolean res = eliminateDuplicates(resInt, arrList);
                        if(!res){
                            resInt.add(arrList);
                        }
                    }
                }

            }
        }
        return resInt;
    }

    public static boolean eliminateDuplicates(List<List<Integer>>listInt, List<Integer> TobeChecked){
        boolean[] ToBeCheckedChecker = {false, false, false};
        boolean[] listIntChecker = {false, false, false};

        for(List<Integer> LI : listInt){
            for(int i = 0; i < 3; i++){
                for(int j =0 ; j< 3 ; j ++){
                    if(Objects.equals(TobeChecked.get(i), LI.get(j))){
                        if(!ToBeCheckedChecker[i] && !listIntChecker[j]){
                            ToBeCheckedChecker[i] = true;
                            listIntChecker[j] = true;
                        }
                    }
                }

            }
            boolean finalFlag = true;
            for(int i = 0; i < 3; i++){
                if (!ToBeCheckedChecker[i]) {
                    finalFlag = false;
                    break;
                }
            }
            if(finalFlag){
                return true;
            }
            else{
                ToBeCheckedChecker[0] = false;
                ToBeCheckedChecker[1] = false;
                ToBeCheckedChecker[2] = false;
                listIntChecker[0] = false;
                listIntChecker[1] = false;
                listIntChecker[2] = false;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        int[] newInt = {2, 7, 11, 15}; //9
        int[] newInt2 = {3, 2, 4}; //4

        int[] nums = {-1,0,1,2,-1,-4, };

//        System.out.println(Arrays.toString(twoSum(newInt2, 7)));
        System.out.println(threeSum(nums));
    }
}