package org.easyarch.slardar.utils;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Description :
 * Created by xingtianyu on 16-12-8
 * 下午4:34
 */

public class CollectionUtils {

    public static <T> boolean isEmpty(Collection<T> col) {
        return col == null || col.isEmpty();
    }

    public static <T> boolean isNotEmpty(Collection<T> col) {
        return !isEmpty(col);
    }

    public static <T> ArrayList<T> newArrayList(final T... beans) {
        ArrayList<T> list = new ArrayList<T>();
        for (T t : beans) {
            list.add(t);
        }
        return list;
    }

    public static <T> ArrayList<T> newArrayList(final int capacity) {
        if (capacity <= 0) {
            return new ArrayList<T>(0);
        }
        return new ArrayList<T>(capacity);
    }

    public static <T> ArrayList<T> newArrayList(final Iterator<? extends T> elements) {
        ArrayList<T> list = newArrayList();
        if (elements == null) {
            return list;
        }
        while (elements.hasNext()) {
            list.add(elements.next());
        }
        return list;
    }


    public static <T> LinkedList<T> newLinkedList(final T... beans) {
        LinkedList<T> list = new LinkedList<T>();
        for (T t : beans) {
            list.add(t);
        }
        return list;
    }

    public static <T> T[] toArray(final Collection<T> col,Class<T> clazz){
        return col.toArray((T[]) Array.newInstance(clazz,col.size()));
    }

    public static <T> Map<T, Integer> wordcount(final Collection<T> col) {
        return countFrequency(col);
    }


    public static<T> Collection<T> subtract(final Collection<T> a,final Collection<T> b){
        Set<T> set = new HashSet<T>();
        ArrayList<T> result = new ArrayList<T>();
        set.addAll(a);
        Map<T, Integer> aMap = countFrequency(a);
        Map<T, Integer> bMap = countFrequency(b);
        for (T bean : set) {
            int loopSize = aMap.get(bean) - bMap.get(bean);
            for (int index = 0; index < loopSize; index++) {
                result.add(bean);
            }
        }
        return result;
    }

    private static <T> int getFrequency(final T bean, Map<T, Integer> map) {
        Integer frequency = map.get(bean);
        if (frequency == null) {
            return 0;
        }
        return frequency.intValue();
    }

    private static <T> Map<T, Integer> countFrequency(final Collection<T> col) {
        Map<T, Integer> resultMap = new TreeMap<T, Integer>();
        for (T bean : col) {
            Integer count = resultMap.get(bean);
            if (count == null) {
                resultMap.put(bean, 1);
            } else {
                resultMap.put(bean, count.intValue() + 1);
            }
        }
        return resultMap;
    }

    /**
     * 聚合操作，给maplist降低维度到统一的map中
     * @param mapList
     * @return
     */
    public static Map<String,Object> collectMapLists(List<Map<String,Object>> mapList){
        Map<String,Object> collectMap = new HashMap<>();
        for (Map<String,Object> map:mapList){
            collectMap.putAll(map);
        }
        return collectMap;
    }

    /**
     * 平铺操作，将map中的每个元素作为一个键值放入list
     * @param flatMap
     * @return
     */
    public static List<Map<String,Object>> flatMapLists(Map<String,Object> flatMap){
        List<Map<String,Object>> mapList = new ArrayList<>();
        for (Map.Entry<String,Object> entry : flatMap.entrySet()){
            Map<String,Object> map = new HashMap<>();
            map.put(entry.getKey(),entry.getValue());
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 统计maplist的值，返回数组
     * @param mapList
     * @return
     */
    public static Object[] gatherMapListsValues(List<Map<String,Object>> mapList){
        List valueList = new ArrayList();
        for (Map<String,Object> map:mapList){
            for (Map.Entry<String,Object> entry:map.entrySet()){
                valueList.add(entry.getValue());
            }
        }
        return valueList.toArray();
    }

    public static void main(String[] args) {
//        List<String> words = newArrayList("this", "is", "a", "file", "and", "that", "is", "a", "file");
        List<Integer> a = newArrayList(1,2,2,3,3,4,5,5);
        List<Integer> b = newArrayList(1,1,2,3,4,4,4,5);
//        System.out.println(Lists.partition(a,5));;
//        System.out.println(union(a,b));
        System.out.println(subtract(a,b));;
    }
}