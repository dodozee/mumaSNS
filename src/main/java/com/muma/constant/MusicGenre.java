package com.muma.constant;

import java.util.ArrayList;
import java.util.List;

public enum MusicGenre {

    BALLAD(1, "BALLAD"), RAP(2, "RAP");

    public Integer value;
    public String name;
    private static List<MusicGenre> typeList = null;

    MusicGenre(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static List<MusicGenre> getMusicGenres() {
        if (typeList == null) {
            typeList = new ArrayList<>();
            typeList.add(BALLAD);
            typeList.add(RAP);
        }
        return typeList;
    }
}