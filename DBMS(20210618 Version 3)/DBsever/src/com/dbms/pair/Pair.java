package com.dbms.pair;

/**
 * 手敲一个pair，哈哈哈哈哈
 * 看来泛型用不到了
 * @param <K>   数据类型一
 * @param <V>   数据类型二
 */
/*public class Pair<K, V> {
    private K first;       //name
    private V second;      //type
    public Pair(K fir, V sec) {
        first=fir;
        second=sec;
    }
    public K getFirst() {
        return first;
    }
    public V getSecond() {
        return second;
    }
}*/
public class Pair {
    private String first;       //name
    private String second;      //type
    public Pair(String fir, String sec) {
        first=fir;
        second=sec;
    }
    public String getFirst() {
        return first;
    }
    public String getSecond() {
        return second;
    }
    public void  Print() {
        System.out.println("fir:"+first+" sex:"+second);
    }
}
