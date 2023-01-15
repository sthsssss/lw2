package org.techtown.lottoworld;

public class MadeNumQuery{
    long id;
    String date;// 당첨일

    int[] nums = new int[7]; // 보너스 넘버까지 7개
    int total;
    int even; // 짝수만 지정하고 홀수는 6 - even 으로 코딩

    public void setId(long id){ this.id = id;}
    public long getId(){return  id;}

    public int[] getNums() {
        return nums;
    }

    public void setNums(int[] nums) {
        this.nums = nums;
    }

    public MadeNumQuery() {
    }

    public MadeNumQuery(long id, String date, int[] nums) {
        this.id = id;
        this.date = date;
        this.nums = nums;
    }

    public int getTotal() {
        total = 0;
        for(int i = 0; i < 6; i++){
            total += nums[i];
        }
        return total;
    }

    public int getEven() {
        even = 0;
        for(int i = 0; i < 6; i++){
            if(nums[i] % 2 ==0){
                even ++;
            }
        }
        return even;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String numberString(){ // 당첨번호 보너스를 제외한 6개를 붙인 문자열
        StringBuilder sb = new StringBuilder(); // well estimated buffer
        int i;
        for(i = 0;i < 6; i++){
            sb.append(nums[i]);
            if(i != 5){
                sb.append("  ");
            }
        }
        return sb.toString();
    }
}