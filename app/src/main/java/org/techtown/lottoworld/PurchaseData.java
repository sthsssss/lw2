package org.techtown.lottoworld;

public class PurchaseData {

    // For List Setting
    PurchaseData(int type,int n1,int n2,int n3,int n4,int n5,int n6,int round,int rank){
        this.type = type;
        this.rank = rank;
        this.round = round;
        this.nums[0] = n1;
        this.nums[1] = n2;
        this.nums[2] = n3;
        this.nums[3] = n4;
        this.nums[4] = n5;
        this.nums[5] = n6;
    }
    // For Sticker Setting
    PurchaseData(int type,int n1,int n2,int n3,int n4,int n5,int n6,int bn,int round,int sticker){
        this.round = round;
        this.type = type;
        this.nums[0] = n1;
        this.nums[1] = n2;
        this.nums[2] = n3;
        this.nums[3] = n4;
        this.nums[4] = n5;
        this.nums[5] = n6;
        this.nums[6] = bn;
    }
    // 101 이면 스티커 102 이면 리스트
    int type;
    int round; // 회차
    String date; // 당첨일
    int rank; // 순위
    int[] nums = new int[7]; // 번호 배열
}
