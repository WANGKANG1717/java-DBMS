package com.dbms.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Data {
    private ArrayList<Pair> value_key;

    public Data() {
        value_key = new ArrayList<Pair>();
    }

    public void addData(String name, String type) {
        value_key.add(new Pair(name, type));
    }

    public int getSize() {
        return value_key.size();
    }

    public Pair getPair(int index) {
        return value_key.get(index);
    }
}
