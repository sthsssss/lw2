package org.techtown.lottoworld.winningHistory;

import org.techtown.lottoworld.NumberQuery;

public class WinningHistory {
    NumberQuery numberQuery;
    int rank;

    public WinningHistory(NumberQuery numberQuery, int rank) {
        this.numberQuery = numberQuery;
        this.rank = rank;
    }

    public NumberQuery getWinningNumber() {
        return numberQuery;
    }

    public void setWinningNumber(NumberQuery numberQuery) {
        this.numberQuery = numberQuery;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
