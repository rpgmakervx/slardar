package org.easyarch.slardar.utils;/**
 * Description : 
 * Created by YangZH on 16-11-1
 *  上午12:30
 */


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Description :
 * Created by code4j on 16-11-1
 * 上午12:30
 */

public class ArrayUtils {

    public static<T> T[] newArray(Class<T> type,int length){
        return (T[]) Array.newInstance(type,length);
    }

    private static int getLength(Object array) {
        if (array == null) {
            return 0;
        }
        return Array.getLength(array);
    }

    private static Object expandSpace(final Object array, int expsize, final Class<?> contentType) {
        if (array != null) {
            int length = getLength(array);
            Object newarray = Array.newInstance(contentType, length + expsize);
            System.arraycopy(array, 0, newarray, 0, length);
            return newarray;
        }
        return Array.newInstance(contentType, 1);
    }

    public static boolean isEmpty(Object array) {
        return array == null || Array.getLength(array) == 0;
    }

    public static boolean isEmpty(int[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(float[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(double[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(long[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(byte[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(short[] array) {
        return array == null || array.length == 0;
    }

    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }


    public static <T> List<Integer> indexOf(T[] array, T elem) {
        List<Integer> indexs = new ArrayList<Integer>();
        int index = 0;
        for (T t : array) {
            if (t.equals(elem))
                indexs.add(index);
            index++;
        }
        return indexs;
    }

    public static List<Integer> indexOf(Object array, Object elem) {
        List<Integer> indexs = new ArrayList<Integer>();
        if (isEmpty(array))
            return indexs;
        int length = getLength(array);
        for (int index = 0; index < length; index++) {
            if (Array.get(array, index).equals(elem))
                indexs.add(index);
        }
        return indexs;
    }

    public static List<Integer> indexOf(int[] array, int elem) {
        List<Integer> indexs = new ArrayList<Integer>();
        if (isEmpty(array))
            return indexs;
        for (int index = 0; index < array.length; index++) {
            if (array[index] == elem)
                indexs.add(index);
        }
        return indexs;
    }

    public static List<Integer> indexOf(float[] array, float elem) {
        List<Integer> indexs = new ArrayList<Integer>();
        if (isEmpty(array))
            return indexs;
        for (int index = 0; index < array.length; index++) {
            if (array[index] == elem)
                indexs.add(index);
        }
        return indexs;
    }

    public static List<Integer> indexOf(double[] array, double elem) {
        List<Integer> indexs = new ArrayList<Integer>();
        if (isEmpty(array))
            return indexs;
        for (int index = 0; index < array.length; index++) {
            if (array[index] == elem)
                indexs.add(index);
        }
        return indexs;
    }

    public static List<Integer> indexOf(long[] array, long elem) {
        List<Integer> indexs = new ArrayList<Integer>();
        if (isEmpty(array))
            return indexs;
        for (int index = 0; index < array.length; index++) {
            if (array[index] == elem)
                indexs.add(index);
        }
        return indexs;
    }

    public static List<Integer> indexOf(byte[] array, byte elem) {
        List<Integer> indexs = new ArrayList<Integer>();
        if (isEmpty(array))
            return indexs;
        for (int index = 0; index < array.length; index++) {
            if (array[index] == elem)
                indexs.add(index);
        }
        return indexs;
    }

    public static List<Integer> indexOf(short[] array, short elem) {
        List<Integer> indexs = new ArrayList<Integer>();
        if (isEmpty(array))
            return indexs;
        for (int index = 0; index < array.length; index++) {
            if (array[index] == elem)
                indexs.add(index);
        }
        return indexs;
    }

    public static <T> boolean contains(T[] array, T elem) {
        return indexOf(array, elem).isEmpty();
    }

    public static boolean contains(int[] array, int elem) {
        return indexOf(array, elem).isEmpty();
    }

    public static boolean contains(float[] array, float elem) {
        return indexOf(array, elem).isEmpty();
    }

    public static boolean contains(double[] array, double elem) {
        return indexOf(array, elem).isEmpty();
    }

    public static boolean contains(long[] array, long elem) {
        return indexOf(array, elem).isEmpty();
    }

    public static boolean contains(byte[] array, byte elem) {
        return indexOf(array, elem).isEmpty();
    }

    public static boolean contains(short[] array, short elem) {
        return indexOf(array, elem).isEmpty();
    }

    private static Object add(Object array, Object elem, Class<?> type) {
        final Object newarray = expandSpace(array, 1, type);
        int length = getLength(array);
        Array.set(newarray, length, elem);
        return newarray;
    }

    private static Object add(Object array, int index, Object elem, final Class<?> type) {
        if (array == null) {
            if (index > 0) {
                throw new ArrayIndexOutOfBoundsException("insert point is " + index + ",but array size is " + 0);
            }
            final Object newarray = Array.newInstance(type, 1);
            Array.set(newarray, 0, elem);
            return newarray;
        }
        int length = getLength(array);
        if (index > length || index < 0) {
            throw new ArrayIndexOutOfBoundsException("insert point is " + index + ",but array size is " + 0);
        }
        final Object newarray = Array.newInstance(array.getClass().getComponentType(), length + 1);
        System.arraycopy(array, 0, newarray, 0, index);
        Array.set(newarray, index, elem);
        if (index < length) {
            System.arraycopy(array, index, newarray, index + 1, length - index);
        }
        return newarray;
    }

    public static <T> Object addAll(Object array, T[] elems) {
        int srcLength = getLength(array);
        int elemsLength = Array.getLength(elems);
        if (srcLength < elemsLength) {
            throw new ArrayIndexOutOfBoundsException("src length is " + srcLength +
                    ", but elemes length is " + elemsLength);
        }
        T[] expanedArray = (T[]) expandSpace(array, elemsLength, array.getClass().getComponentType());
        System.arraycopy(elems, 0, expanedArray, 0, elemsLength + srcLength);
        return expanedArray;
    }

    public static <T> T[] addAll(T[] array, T... elems) {
        return (T[]) addAll((Object) array, elems);
    }

    public static <T> T[] add(T[] array, int index, T elem) {
        return (T[]) add(array, index, elem, array.getClass().getComponentType());
    }

    public static int[] add(int[] array, int index, int elem) {
        return (int[]) add(array, index, elem, Integer.TYPE);
    }

    public static float[] add(float[] array, int index, float elem) {
        return (float[]) add(array, index, elem, Float.TYPE);
    }

    public static double[] add(double[] array, int index, double elem) {
        return (double[]) add(array, index, elem, Double.TYPE);
    }

    public static long[] add(long[] array, int index, long elem) {
        return (long[]) add(array, index, elem, Long.TYPE);
    }

    public static byte[] add(byte[] array, int index, byte elem) {
        return (byte[]) add(array, index, elem, Byte.TYPE);
    }

    public static short[] add(short[] array, int index, short elem) {
        return (short[]) add(array, index, elem, Short.TYPE);
    }

    public static int[] addAll(int[] array, int... elems) {
        int srcLength = getLength(array);
        int elemsLength = Array.getLength(elems);
        if (srcLength < elemsLength) {
            throw new ArrayIndexOutOfBoundsException("src length is " + srcLength +
                    ", but elemes length is " + elemsLength);
        }
        int[] expanedArray = (int[]) expandSpace(array, elemsLength, Integer.TYPE);
        System.arraycopy(elems, 0, expanedArray, srcLength, elemsLength);
        return expanedArray;
    }

    public static float[] addAll(float[] array, float... elems) {
        int srcLength = getLength(array);
        int elemsLength = Array.getLength(elems);
        if (srcLength < elemsLength) {
            throw new ArrayIndexOutOfBoundsException("src length is " + srcLength +
                    ", but elemes length is " + elemsLength);
        }
        float[] expanedArray = (float[]) expandSpace(array, elemsLength, Integer.TYPE);
        System.arraycopy(elems, 0, expanedArray, srcLength, elemsLength);
        return expanedArray;
    }

    public static double[] addAll(double[] array, double... elems) {
        int srcLength = getLength(array);
        int elemsLength = Array.getLength(elems);
        if (srcLength < elemsLength) {
            throw new ArrayIndexOutOfBoundsException("src length is " + srcLength +
                    ", but elemes length is " + elemsLength);
        }
        double[] expanedArray = (double[]) expandSpace(array, elemsLength, Integer.TYPE);
        System.arraycopy(elems, 0, expanedArray, srcLength, elemsLength);
        return expanedArray;
    }

    public static long[] addAll(long[] array, long... elems) {
        int srcLength = getLength(array);
        int elemsLength = Array.getLength(elems);
        if (srcLength < elemsLength) {
            throw new ArrayIndexOutOfBoundsException("src length is " + srcLength +
                    ", but elemes length is " + elemsLength);
        }
        long[] expanedArray = (long[]) expandSpace(array, elemsLength, Integer.TYPE);
        System.arraycopy(elems, 0, expanedArray, srcLength, elemsLength);
        return expanedArray;
    }

    public static byte[] addAll(byte[] array, byte... elems) {
        int srcLength = getLength(array);
        int elemsLength = Array.getLength(elems);
        if (srcLength < elemsLength) {
            throw new ArrayIndexOutOfBoundsException("src length is " + srcLength +
                    ", but elemes length is " + elemsLength);
        }
        byte[] expanedArray = (byte[]) expandSpace(array, elemsLength, Integer.TYPE);
        System.arraycopy(elems, 0, expanedArray, srcLength, elemsLength);
        return expanedArray;
    }

    public static short[] addAll(short[] array, short... elems) {
        int srcLength = getLength(array);
        int elemsLength = Array.getLength(elems);
        if (srcLength < elemsLength) {
            throw new ArrayIndexOutOfBoundsException("src length is " + srcLength +
                    ", but elemes length is " + elemsLength);
        }
        short[] expanedArray = (short[]) expandSpace(array, elemsLength, Integer.TYPE);
        System.arraycopy(elems, 0, expanedArray, srcLength, elemsLength);
        return expanedArray;
    }

    public static Object remove(Object array, int index, Class<?> type) {
        int srcLength = getLength(array);
        if (index < 0 || index >= srcLength) {
            throw new IndexOutOfBoundsException("src length is " + srcLength +
                    ", but index is " + index);
        }

        final Object result = Array.newInstance(type, srcLength - 1);
        System.arraycopy(array, 0, result, 0, index);
        if (index < srcLength - 1) {
            System.arraycopy(array, index + 1, result, index, srcLength - index - 1);
        }

        return result;
    }

    public static Object removeElem(Object array, Object elem) {
        int srcLength = getLength(array);
        if (elem == null) {
            return null;
        }

        final Object result = Array.newInstance(array.getClass().getComponentType(), srcLength - 1);
        List<Integer> points = indexOf(array, elem);
        if (points.isEmpty())
            return array;
        for (int index = 0; index < srcLength; index++) {
            Object o = Array.get(array, index);
            if (!points.contains(o)) {
                Array.set(result, index, o);
            }
            index++;
        }
        return result;
    }

    public static <T> T[] removeElem(T[] array, T elem, Class<T> type) {
        int srcLength = getLength(array);
        if (elem == null) {
            return null;
        }

        final T[] result = (T[]) Array.newInstance(type, srcLength - 1);
        List<Integer> points = indexOf(array, elem);
        if (points.isEmpty())
            return array;
        for (int index = 0; index < srcLength; index++) {
            Object o = Array.get(array, index);
            if (!points.contains(o)) {
                Array.set(result, index, o);
            }
            index++;
        }
        return result;
    }

    public static <T> T[] removeAll(T[] array, T... elems) {
        List<T> keys = new ArrayList<T>();
        for (T t : elems) {
            List<Integer> points = indexOf(array, t);
            if (points.isEmpty())
                return array;
            for (int index = 0; index < array.length; index++) {
                T o = array[index];
                if (!points.contains(o)) {
                    keys.add(o);
                }
                index++;
            }
        }
        T[] newarray = (T[]) Array.newInstance(array.getClass().getComponentType(), keys.size());
        int index = 0;
        for (T t : keys) {
            newarray[index] = t;
        }
        return newarray;
    }

    public static <T> T[] remove(T[] array, int index) {
        return (T[]) remove((Object) array, index, array.getClass().getComponentType());
    }

    public static int[] remove(int[] array, int index) {
        return (int[]) remove(array, index, Integer.TYPE);
    }

    public static float[] remove(float[] array, int index) {
        return (float[]) remove(array, index, Float.TYPE);
    }

    public static double[] remove(double[] array, int index) {
        return (double[]) remove(array, index, Double.TYPE);
    }

    public static long[] remove(long[] array, int index) {
        return (long[]) remove(array, index, Long.TYPE);
    }

    public static byte[] remove(byte[] array, int index) {
        return (byte[]) remove(array, index, Byte.TYPE);
    }

    public static short[] remove(short[] array, int index) {
        return (short[]) remove(array, index, Short.TYPE);
    }

    public static int[] removeElem(int[] array, int elem) {
        return (int[]) removeElem((Object) array, elem);
    }

    public static float[] removeElem(float[] array, float elem) {
        return (float[]) removeElem((Object) array, elem);
    }

    public static double[] removeElem(double[] array, double elem) {
        return (double[]) removeElem((Object) array, elem);
    }

    public static long[] removeElem(long[] array, long elem) {
        return (long[]) removeElem((Object) array, elem);
    }

    public static byte[] removeElem(byte[] array, byte elem) {
        return (byte[]) removeElem((Object) array, elem);
    }

    public static short[] removeElem(short[] array, short elem) {
        return (short[]) removeElem((Object) array, elem);
    }

    public static int[] removeAll(int[] array, int... elems) {
        Set<Integer> forbid = new HashSet<Integer>();
        int count = 0;
        for (Integer t : elems) {
            List<Integer> points = indexOf(array, t);
            count += points.size();
            for (int index = 0; index < array.length; index++) {
                if (points.contains(index)) {
                    forbid.add(array[index]);
                }
            }
        }
        int[] newarray = (int[]) Array.newInstance(array.getClass().getComponentType(), array.length-count);
        int index = 0;
        for (Integer t : array) {
            if (!forbid.contains(t)){
                newarray[index] = t;
                index++;
            }

        }
        return newarray;
    }

    public static float[] removeAll(float[] array, float... elems) {
        Set<Float> forbid = new HashSet<Float>();
        int count = 0;
        for (Float t : elems) {
            List<Integer> points = indexOf(array, t);
            count += points.size();
            for (int index = 0; index < array.length; index++) {
                if (points.contains(index)) {
                    forbid.add(array[index]);
                }
            }
        }
        float[] newarray = (float[]) Array.newInstance(array.getClass().getComponentType(), array.length-count);
        int index = 0;
        for (Float t : array) {
            if (!forbid.contains(t)){
                newarray[index] = t;
                index++;
            }

        }
        return newarray;
    }

    public static double[] removeAll(double[] array, double... elems) {
        Set<Double> forbid = new HashSet<Double>();
        int count = 0;
        for (Double t : elems) {
            List<Integer> points = indexOf(array, t);
            count += points.size();
            for (int index = 0; index < array.length; index++) {
                if (points.contains(index)) {
                    forbid.add(array[index]);
                }
            }
        }
        double[] newarray = (double[]) Array.newInstance(array.getClass().getComponentType(), array.length-count);
        int index = 0;
        for (Double t : array) {
            if (!forbid.contains(t)){
                newarray[index] = t;
                index++;
            }

        }
        return newarray;
    }

    public static long[] removeAll(long[] array, long... elems) {
        Set<Long> forbid = new HashSet<Long>();
        int count = 0;
        for (Long t : elems) {
            List<Integer> points = indexOf(array, t);
            count += points.size();
            for (int index = 0; index < array.length; index++) {
                if (points.contains(index)) {
                    forbid.add(array[index]);
                }
            }
        }
        long[] newarray = (long[]) Array.newInstance(array.getClass().getComponentType(), array.length-count);
        int index = 0;
        for (Long t : array) {
            if (!forbid.contains(t)){
                newarray[index] = t;
                index++;
            }

        }
        return newarray;
    }

    public static byte[] removeAll(byte[] array, byte... elems) {
        Set<Byte> forbid = new HashSet<Byte>();
        int count = 0;
        for (Byte t : elems) {
            List<Integer> points = indexOf(array, t);
            count += points.size();
            for (int index = 0; index < array.length; index++) {
                if (points.contains(index)) {
                    forbid.add(array[index]);
                }
            }
        }
        byte[] newarray = (byte[]) Array.newInstance(array.getClass().getComponentType(), array.length-count);
        int index = 0;
        for (Byte t : array) {
            if (!forbid.contains(t)){
                newarray[index] = t;
                index++;
            }

        }
        return newarray;
    }

    public static short[] removeAll(short[] array, short... elems) {
        Set<Short> forbid = new HashSet<Short>();
        int count = 0;
        for (Short t : elems) {
            List<Integer> points = indexOf(array, t);
            count += points.size();
            for (int index = 0; index < array.length; index++) {
                if (points.contains(index)) {
                    forbid.add(array[index]);
                }
            }
        }
        short[] newarray = (short[]) Array.newInstance(array.getClass().getComponentType(), array.length-count);
        int index = 0;
        for (Short t : array) {
            if (!forbid.contains(t)){
                newarray[index] = t;
                index++;
            }

        }
        return newarray;
    }


    public static String printArray(Object[] array){
        StringBuffer buffer = new StringBuffer();
        if (isEmpty(array)){
            buffer.append("[empty]");
            return buffer.toString();
        }
        buffer.append("[");
        int index = 0;
        for (Object obj:array){
            if (index == array.length - 1){
                buffer.append(obj + "]");
            }else{
                buffer.append(obj + ",");
            }
        }
        return buffer.toString();
    }
}
