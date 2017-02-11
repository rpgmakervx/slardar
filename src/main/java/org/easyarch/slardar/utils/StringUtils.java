package org.easyarch.slardar.utils;

import java.util.Random;
import java.util.UUID;

/**
 * Description :
 * Created by code4j on 16-10-30
 * abbreviate("0123456789",0,10)
 * ^
 * |
 * 上午1:24
 */

public class StringUtils {

    private static final Random RANDOM = new Random();

    /**
     * 字符串是否是空
     *
     * @param src
     * @return
     */
    public static boolean isEmpty(final String src) {
        return src == null || src.length() == 0;
    }
    public static boolean isNotEmpty(final String src) {
        return !isEmpty(src);
    }

    /**
     * 字符串是否是空白的
     *
     * @param src
     * @return
     */
    public static boolean isBlank(final String src) {
        return trimToEmpty(src).length() == 0;
    }

    /**
     * 字符串头部添加省略符
     *
     * @param src
     * @param begin
     * @param ellipsisLength
     * @return
     */
    public static String headAbbreviate(final String src, int begin, int ellipsisLength) {
        final int length = src.length();
        if (src == null || src.isEmpty() || begin > length - ellipsisLength) {
            return src;
        }
        StringBuffer buffer = new StringBuffer();
        String ellipsis = "...";
        if (ellipsisLength > 3) {
            for (int index = 0; index < ellipsisLength - 3; index++) {
                buffer.append(ellipsis.concat("."));
            }
        }
        String newStr = src.substring(0, begin) + buffer.toString();
        return newStr;
    }

    /**
     * 字符串尾部添加省略符
     *
     * @param end
     * @param ellipsisLength
     * @param src
     * @return
     */
    public static String tailAbbreviate(final String src ,int end, int ellipsisLength) {
        final int length = src.length();
        if (src == null || src.isEmpty() || length - end < ellipsisLength) {
            return src;
        }
        StringBuffer buffer = new StringBuffer();
        String ellipsis = "...";
        if (ellipsisLength > 3) {
            for (int index = 0; index < ellipsisLength - 3; index++) {
                buffer.append(ellipsis.concat("."));
            }
        }
        String newStr = buffer.toString() + src.substring(length - end, length);
        return newStr;
    }

    /**
     * 字符串首字母大写
     *
     * @param str
     * @return
     */
    public static String capitalize(final String str) {
        int length = str.length();
        if (str == null || length == 0) {
            return str;
        }
        char first = str.charAt(0);
        if (Character.isTitleCase(first)) {
            return str;
        }
        return String.valueOf(Character.toTitleCase(first))
                .concat(str.substring(1));
    }

    /**
     * 给字符串追加内容
     *
     * @param src
     * @param plugin
     * @return
     */
    public static String append(final String src, String plugin) {
        return src.concat(plugin);
    }

    /**
     * 给字符串前面添加内容
     *
     * @param src
     * @param plugin
     * @return
     */
    public static String preAppend(final String src, String plugin) {
        return plugin.concat(src);
    }

    /**
     * 用指定的标识符将字符串居中
     *
     * @param src
     * @param pad
     * @param symbol
     * @return
     */
    public static String center(final String src, int pad, String symbol) {
        if (src == null || src.isEmpty() || pad <= 0) {
            return src;
        }
        String symbolDump = "";
        for (int index = 0; index < pad; index++) {
            symbolDump += symbol;
        }
        return append(preAppend(src, symbolDump), symbolDump);
    }

    /**
     * 将字符串居中
     *
     * @param src
     * @param pad
     * @return
     */
    public static String center(final String src, int pad) {
        return center(src, pad, " ");
    }

    /**
     * 用指定的标签组给字符串打标签
     *
     * @param src
     * @param frontSymbol
     * @param backSymbol
     * @return
     */
    public static String surround(final String src, final String frontSymbol, final String backSymbol) {
        if (src == null || src.isEmpty()) {
            return src;
        }
        return append(preAppend(src, frontSymbol), backSymbol);
    }

    /**
     * 去除字符串中所有空白符
     *
     * @param src 字符串不能为null
     * @return
     */
    public static String trimAll(final String src) {
        String[] slices = src.split(" ");
        StringBuffer buffer = new StringBuffer();
        for (String str : slices) {
            buffer.append(str);
        }
        return buffer.toString();
    }

    /**
     * 去除字符串中所有的空白符（包括null）
     *
     * @param src
     * @return
     */
    public static String trimToEmpty(final String src) {
        return src == null ? "" : trimAll(src);
    }

    /**
     * 在源字符串指定位置插入字符串
     *
     * @param src
     * @param plugin
     * @param index
     * @return
     */
    public static String insert(final String src, String plugin, int index) {
        if (index == 0) {
            return preAppend(src, plugin);
        }
        if (index == src.length()) {
            System.out.println("append!!");
            return append(src, plugin);
        }
        String midstr = src.substring(0, index - 1);
        StringBuffer buffer = new StringBuffer(midstr);
        return buffer.append(plugin).append(
                src.substring(index - 1, src.length())).toString();
    }

    /**
     * @param str1
     * @param str2
     * @return
     */
    public static String difference(final String str1, String str2) {
        if (str1 == null) {
            return str2;
        }
        if (str2 == null) {
            return str1;
        }
        final int at = indexOfDifference(str1, str2);
        if (at == -1) {
            return "";
        }
        String diff = str2.substring(at);
        if (diff.isEmpty()) {
            diff = difference(str2, str1);
        }
        return diff;
    }

    /**
     * 两个字符串开始出现不同的临界点
     *
     * @param src1
     * @param src2
     * @return
     */
    private static int indexOfDifference(final String src1, final String src2) {
        if (src1 == src2) {
            return -1;
        }
        if (src1 == null || src2 == null) {
            return 0;
        }
        int i;
        for (i = 0; i < src1.length() && i < src2.length(); ++i) {
            if (src1.charAt(i) != src2.charAt(i)) {
                break;
            }
        }
        if (i < src2.length() || i < src1.length()) {
            return i;
        }
        return -1;
    }

    /**
     * 字符串翻转
     *
     * @param str
     * @return
     */
    public static String reverse(final String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return new StringBuffer(str).reverse().toString();
    }

    /**
     * 是否是回文
     *
     * @param src
     * @return
     */
    public static boolean isPalindrom(final String src) {
        return new StringBuffer(src).reverse()
                .toString().equals(src);
    }

    /**
     * 是否是回文（忽略大小写）
     *
     * @param src
     * @return
     */
    public static boolean isPalindromIgnoreCase(final String src) {
        return new StringBuffer(src).reverse()
                .toString().equalsIgnoreCase(src);
    }

    /**
     * 配合kmp算法获取下一个数组
     *
     * @param t
     * @return
     */
    private static int[] next(char[] t) {
        int[] next = new int[t.length];
        next[0] = -1;
        int i = 0;
        int j = -1;
        while (i < t.length - 1) {
            if (j == -1 || t[i] == t[j]) {
                i++;
                j++;
                if (t[i] != t[j]) {
                    next[i] = j;
                } else {
                    next[i] = next[j];
                }
            } else {
                j = next[j];
            }
        }
        return next;
    }

    /**
     * KMP匹配字符串
     *
     * @param s 主串
     * @param t 模式串
     * @return 若匹配成功，返回下标，否则返回-1
     */
    public static int kmp(String s, String t) {
        int[] next = next(t.toCharArray());
        int i = 0;
        int j = 0;
        while (i <= s.length() - 1 && j <= t.length() - 1) {
            if (j == -1 || s.charAt(i) == t.charAt(j)) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (j < t.length()) {
            return -1;
        } else
            return i - t.length(); // 返回模式串在主串中的头下标
    }

    /**
     * 用分隔符把字符串数组拼接成按分隔符分割的字符串
     *
     * @param srcs
     * @param separator
     * @param edge      是否把分隔符添加到边界
     * @return
     */
    public static String join(final String[] srcs, String separator, boolean edge) {
        StringBuffer buffer = new StringBuffer();
        int index = 0;
        for (String src : srcs) {
            if (edge && index == 0) {
                buffer.append(separator);
            }
            buffer.append(src);
            if (index != srcs.length - 1 ||
                    (index == srcs.length - 1 && edge))
                buffer.append(separator);
            index++;
        }
        return buffer.toString();
    }

    /**
     * 用分隔符把字符串数组拼接成按分隔符分割的字符串
     *
     * @param srcs
     * @param separator
     * @return
     */
    public static String join(final String[] srcs, String separator) {
        return join(srcs, separator, false);
    }

    /**
     * 关键字打标签
     *
     * @param src
     * @param key
     * @param frontTag
     * @param backTag
     * @return
     */
    public static String tagKeyWord(final String src, String key, String frontTag, String backTag) {
        StringBuffer buffer = new StringBuffer();
        int begin = 0;
        int end = 0;
        String copy = src;
        while (true) {
            begin = kmp(copy, key);
            if (begin == -1) {
                buffer.append(copy);
                break;
            }
            end = begin + key.length();
            String frontSegment = src.substring(0, begin);
            copy = src.substring(end);
            buffer.append(frontSegment)
                    .append(frontTag)
                    .append(key)
                    .append(backTag);
        }
        return buffer.toString();
    }

    /**
     * 按模式将字符串剥去出来，仅限开头结尾被包裹的字符串
     * @param src
     * @param beginToken
     * @param endToken
     * @return
     */
    public static String strip(final String src,final String beginToken,final String endToken){
        int beginIndex = beginToken.length();
        int endIndex = endToken.length();
        return src.substring(beginIndex,src.length() - beginIndex - endIndex);
    }

    public static String strip(final String src,final String beginToken){
        int beginIndex = beginToken.length();
        return src.substring(beginIndex,src.length() - beginIndex);
    }

//    /**
//     * 从若干对标签中取出字符，返回字符出现的顺序和字符本身的映射
//     * @param src
//     * @param frontTag
//     * @param backTag
//     * @return
//     */
//    public static Map<Integer,String> splitTag(final String src,String frontTag, String backTag){
//        Map<Integer,String> mapper = new HashMap<>();
//        boolean isFront = true;
//        int countDown = 0;
//        int startIndex = 0;
//        int currentIndex = src.indexOf(frontTag);
//        StringBuffer buffer = new StringBuffer();
//        String tmp = src;
//        while (currentIndex > 0){
//            if (isFront){
//                currentIndex = tmp.indexOf(frontTag);
//                isFront = false;
//            }else{
//                currentIndex = tmp.indexOf(backTag) + startIndex;
//                System.out.println("back tag ,, startIndex:"+startIndex+",currentIndex:"+currentIndex);
//                System.out.println("back tag src:"+src);
//                String core = src.substring(startIndex,currentIndex);
//                mapper.put(countDown,core);
//                countDown++;
//                isFront = true;
//            }
//            tmp = tmp.substring(currentIndex + 1,src.length());
//            startIndex = currentIndex;
//            System.out.println("tmp string : "+tmp);
//            System.out.println("startIndex:"+startIndex+",currentIndex:"+currentIndex);
//        }
//
//        return mapper;
//    }

    private static String random(int count, int start, int end, final boolean letters, final boolean numbers,
                                 final char[] chars, final Random random) {
        if (count == 0) {
            return "";
        } else if (count < 0) {
            throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
        }
        if (chars != null && chars.length == 0) {
            throw new IllegalArgumentException("The chars array must not be empty");
        }

        if (start == 0 && end == 0) {
            if (chars != null) {
                end = chars.length;
            } else {
                if (!letters && !numbers) {
                    end = Integer.MAX_VALUE;
                } else {
                    end = 'z' + 1;
                    start = ' ';
                }
            }
        } else {
            if (end <= start) {
                throw new IllegalArgumentException("Parameter end (" + end + ") must be greater than start (" + start + ")");
            }
        }

        final char[] buffer = new char[count];
        final int gap = end - start;

        while (count-- != 0) {
            char ch;
            if (chars == null) {
                ch = (char) (random.nextInt(gap) + start);
            } else {
                ch = chars[random.nextInt(gap) + start];
            }
            if (letters && Character.isLetter(ch)
                    || numbers && Character.isDigit(ch)
                    || !letters && !numbers) {
                if (ch >= 56320 && ch <= 57343) {
                    if (count == 0) {
                        count++;
                    } else {
                        // low surrogate, insert high surrogate after putting it in
                        buffer[count] = ch;
                        count--;
                        buffer[count] = (char) (55296 + random.nextInt(128));
                    }
                } else if (ch >= 55296 && ch <= 56191) {
                    if (count == 0) {
                        count++;
                    } else {
                        // high surrogate, insert low surrogate before putting it in
                        buffer[count] = (char) (56320 + random.nextInt(128));
                        count--;
                        buffer[count] = ch;
                    }
                } else if (ch >= 56192 && ch <= 56319) {
                    // private high surrogate, no effing clue, so skip it
                    count++;
                } else {
                    buffer[count] = ch;
                }
            } else {
                count++;
            }
        }
        return new String(buffer);
    }

    private static String random(final int count, final int start, final int end, final boolean letters, final boolean numbers) {
        return random(count, start, end, letters, numbers, null, RANDOM);
    }

    private static String random(final int count, final boolean letters, final boolean numbers) {
        return random(count, 0, 0, letters, numbers);
    }

    public static String randomString(final int count) {
        return random(count, 32, 127, false, false);
    }

    public static String randomNumeric(final int count) {
        return random(count, false, true);
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static void main(String[] args) {
//        System.out.println(tagKeyWord("this book told about javaweb programming","java","<br>","</br>"));
//        System.out.println(kmp("this book told about java programming","java"));
//        String src1 = "this book told about java programming";
//        String src2 = "this book told about C++ programming";
//        String key = "java";
//        String frontSegement = src.substring(0,21);;
//        System.out.println(frontSegement);
//        System.out.println(src.replace("java","c++"));
//        String[] languages = new String[]{"java", "c++", "python"};
//        System.out.println(join(languages, File.separator));
//        System.out.println(indexOfDifference(src1,src2));
//        System.out.println(capitalize(src2));
        String sql = "select * from user where age = #user.age# and curse_id = #user.curseId#";
        System.out.println(strip("$user.id ","$",""));
//        System.out.println(sql.substring(0,31));
//        System.out.println(sql.indexOf("##"));

    }
}
