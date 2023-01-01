package org.techtown.lottoworld;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter
{
    protected static final String TAG = "DataAdapter";

    // TODO : TABLE 이름을 명시해야함
    protected static final String TABLE_NAME = "tb_lotto_list";
    protected static final String TABLE_PURCHASED = "purchase_history_table";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    public DataAdapter(Context context) {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    public DataAdapter open() throws SQLException, java.sql.SQLException {
        try {
            mDbHelper.openDataBase();
        } catch (SQLException | java.sql.SQLException mSQLException) {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }
    public void close() {
        mDbHelper.close();
    }

    public int getLatestRound(){
        try {
            mDb = mDbHelper.getReadableDatabase();
            // Table 이름 -> antpool_bitcoin 불러오기
            String sql ="SELECT round FROM " + TABLE_NAME + " ORDER BY round DESC limit 1";
            int latestRound = 0;

            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur!=null)
            {
                mCur.moveToNext();
                latestRound = mCur.getInt(0);
            }
            return latestRound;
        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }
    public List getWinningData() {
        try {
            mDb = mDbHelper.getReadableDatabase();
            // Table 이름 -> antpool_bitcoin 불러오기
            String sql ="SELECT * FROM " + TABLE_NAME + " ORDER BY round DESC";

            // 모델 넣을 리스트 생성
            List winningList = new ArrayList();

            // TODO : 모델 선언
            NumberQuery numberQuery = null;

            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur!=null) {
                // 칼럼의 마지막까지
                while( mCur.moveToNext() ) {

                    // TODO : 커스텀 모델 생성
                    numberQuery = new NumberQuery();

                    // TODO : Record 기술
                    // round, date, 1st, 2nd, 3rd, 4th, 5th, 6th, bonus
                    numberQuery.setRound(mCur.getInt(0));
                    numberQuery.setDate(mCur.getString(1));
                    int first = mCur.getInt(2);
                    int second = mCur.getInt(3);
                    int third = mCur.getInt(4);
                    int fourth = mCur.getInt(5);
                    int fifth = mCur.getInt(6);
                    int sixth = mCur.getInt(7);
                    int bonus = mCur.getInt(8);
                    numberQuery.setNums(new int[]{first,second,third,fourth,fifth,sixth,bonus});

                    // 리스트에 넣기
                    winningList.add(numberQuery);
                }

            }
            return winningList;
        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public List getMadeNums()
    {
        try
        {
            mDb = mDbHelper.getReadableDatabase();
            // Table 이름 -> antpool_bitcoin 불러오기
            String sql ="SELECT * FROM tb_lotto_made;";

            // 모델 넣을 리스트 생성
            List<MadeNumQuery> numberQueryList = new ArrayList();

            // TODO : 모델 선언
            MadeNumQuery numberQuery = null;

            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur!=null)
            {
                // 칼럼의 마지막까지
                while( mCur.moveToNext() ) {

                    // TODO : 커스텀 모델 생성
                    numberQuery = new MadeNumQuery();
                    numberQuery.setId(mCur.getInt(0));
                    // TODO : Record 기술
                    // round, date, 1st, 2nd, 3rd, 4th, 5th, 6th, bonus
                    numberQuery.setDate(mCur.getString(1));

                    int first = mCur.getInt(2);
                    int second = mCur.getInt(3);
                    int third = mCur.getInt(4);
                    int fourth = mCur.getInt(5);
                    int fifth = mCur.getInt(6);
                    int sixth = mCur.getInt(7);
                    numberQuery.setNums(new int[]{first,second,third,fourth,fifth,sixth,0});

                    // 리스트에 넣기
                    numberQueryList.add(numberQuery);
                }

            }
            return numberQueryList;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }
    // api로 받아온 데이터를 insert 하는 함수
    public void insertLastestNumber(NumberQuery wn){
        mDb = mDbHelper.getWritableDatabase();
        int[] nums = wn.getNums();
        int round = wn.getRound();
        String date = wn.getDate();

        String query = "INSERT INTO tb_lotto_list"
                + " (round, date, '1st', '2nd', '3rd', '4th', '5th', '6th', bonus) "
                + " VALUES ( "
                + round + ", "
                + date + ", "
                + nums[0] + ", "
                + nums[1] + ", "
                + nums[2] + ", "
                + nums[3] + ", "
                + nums[4] + ", "
                + nums[5] + ", "
                + nums[6] + "); ";
        Log.d("insertLastestNumber" , query);
        mDb.execSQL(query);
    }

    public void insertMadeNum(NumberQuery wn){
        mDb = mDbHelper.getWritableDatabase();
        int[] nums = wn.getNums();
        String date = wn.getDate();
        String query = "INSERT INTO tb_lotto_made"
                + " (date, first, second, third, fourth, fifth, sixth)"
                + " VALUES ( '"
                + date + "', "
                + nums[0] + ", "
                + nums[1] + ", "
                + nums[2] + ", "
                + nums[3] + ", "
                + nums[4] + ", "
                + nums[5] + "); ";
        Log.d("insertWinningNum()" , query);
        mDb.execSQL(query);
    }


    public void insertPurchasedNum(int rank,NumberQuery pn){
        mDb = mDbHelper.getWritableDatabase();
        int[] nums = pn.getNums();
        int r = pn.round;
        String query = "INSERT INTO purchase_history_table"
                + " (round, rank, first, second, third, fourth, fifth, sixth)"
                + " VALUES ("
                + r + ", "
                + rank + ", "
                + nums[0] + ", "
                + nums[1] + ", "
                + nums[2] + ", "
                + nums[3] + ", "
                + nums[4] + ", "
                + nums[5] + ");";
        Log.d("insertPurchaseNum()" , query);
        try {
            mDb.execSQL(query);
            Log.d("insertPurchaseNum()" , "try");
        } catch(Exception e){
            e.printStackTrace();
            Log.d("insertPurchaseNum()" , "catch");
        }
    }
    public int getRank(int gR_round,int[] nums){
        try {
            mDb = mDbHelper.getReadableDatabase();
            int rank = 6;
            int i=0,j=0;
            int cnt = 0;
            boolean isBonus = false;

            // 요청받은 회차가 현재회차보다 나중이거나, 1보다 적을 경우 예외처리
            if(gR_round > LatestRound.round || gR_round < 1){
                return -1;
            }

            String winningRoundNumsQuery = "SELECT * FROM tb_lotto_list WHERE round = " + Integer.toString(gR_round) + ";";
            Log.d("t",winningRoundNumsQuery);
            NumberQuery winningNumOngRround = new NumberQuery();
            Cursor mcur = mDb.rawQuery(winningRoundNumsQuery, null);
            if(mcur != null){
                mcur.moveToNext();
                Log.d("t",Integer.toString(mcur.getInt(2)));
                winningNumOngRround.nums[0]=mcur.getInt(2);
                winningNumOngRround.nums[1]=mcur.getInt(3);
                winningNumOngRround.nums[2]=mcur.getInt(4);
                winningNumOngRround.nums[3]=mcur.getInt(5);
                winningNumOngRround.nums[4]=mcur.getInt(6);
                winningNumOngRround.nums[5]=mcur.getInt(7);
                winningNumOngRround.nums[6]=mcur.getInt(8);
                mDb.close();
            } else{
                return -1;
            }

            while(i<7 && j<6){
                if(winningNumOngRround.nums[i] == nums[j]){
                    i++;
                    j++;
                    cnt++;
                    if(i == 6) isBonus = true;
                } else if(winningNumOngRround.nums[i] < nums[j]){
                    i++;
                } else{
                    j++;
                }
            }

            switch (cnt) {
                case 3:
                    return 5;
                case 4:
                    return 4;
                case 5:
                    return 3;
                case 6:
                    if(isBonus){
                        return 2;
                    }
                    else{
                        return 1;
                    }
            }
            return rank;
        } catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }
    // 구매목록 번호 리딩
    public ArrayList<PurchaseData> loadDBforPHlist(){
        try  {
            mDb = mDbHelper.getReadableDatabase();
            String sqlQueryTbl = "SELECT * FROM purchase_history_table ORDER BY round DESC;";
            Cursor cursor = null;

            // 쿼리 실행
            // (round, rank, first, second, third, fourth, fifth, sixth)
            cursor = mDb.rawQuery(sqlQueryTbl, null);
            ArrayList<PurchaseData> data = new ArrayList();
            int prevround = -1;
            while (cursor.moveToNext()) { // 레코드가 존재한다면

                Log.d("logcheck","into the loadDBfunc");
                int round = cursor.getInt(0);
                int rank = cursor.getInt(1);
                int first = cursor.getInt(2);
                int second = cursor.getInt(3);
                int third = cursor.getInt(4);
                int fourth = cursor.getInt(5);
                int fifth = cursor.getInt(6);
                int sixth = cursor.getInt(7);
                if(round == prevround) {
                    data.add(new PurchaseData(101, first, second, third, fourth, fifth, sixth, round, rank));
                }else{
                    prevround = round;
                    //TODO : 102 코드에는 당첨번호가 들어가야됨. -> SELECT FROM tb_lotto_list
                    String sqlWinTbl = "SELECT * FROM tb_lotto_list WHERE round = " + Integer.toString(round) + ";";
                    Cursor cursor2 = mDb.rawQuery(sqlWinTbl, null);
                    cursor2.moveToNext();
                    data.add(new PurchaseData(102, cursor2.getInt(2), cursor2.getInt(3), cursor2.getInt(4), cursor2.getInt(5), cursor2.getInt(6), cursor2.getInt(7), cursor2.getInt(8), prevround,1));
                    data.add(new PurchaseData(101, first, second, third, fourth, fifth, sixth, round, rank));
                    }
            }
            return data;
        } catch(Exception e){
            e.printStackTrace();
        }
        return new ArrayList<PurchaseData>();
    }
}