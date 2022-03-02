package com.baba.babaisyou.presenter;

import com.baba.babaisyou.model.Floor;
import com.baba.babaisyou.model.Object;

public class Map {
    private Object[][] map;

    public void Map(int m, int n) {
        map = new Object[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; i++) {
                map[i][j] = new Floor(i, j);
            }
        }
    }
}