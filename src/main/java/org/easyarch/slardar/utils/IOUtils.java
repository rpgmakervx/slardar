package org.easyarch.slardar.utils;/**
 * Description : 
 * Created by YangZH on 16-10-28
 *  上午10:03
 */

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;

/**
 * Description :
 * Created by code4j on 16-10-28
 * 上午10:03
 */

public class IOUtils {

    private static final int DEFAULT_BUFFER_SIZE = 2 << 12;

    public static void closeIO(InputStream is) {
        try {
            if (is != null) {
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeIO(OutputStream os) {
        try {
            if (os != null) {
                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeIO(Writer writer) {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeIO(Reader reader) {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeChl(Channel ch) {
        try {
            ch.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void flushIO(OutputStream os) {
        try {
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void flushIO(Writer writer) {
        try {
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] getBytes(char[] chars) {
        Charset cs = Charset.forName("UTF-8");
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars);
        cb.flip();
        ByteBuffer bb = cs.encode(cb);
        return bb.array();
    }

    public static byte[] toByteArray(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        copy(is, baos);
        return baos.toByteArray();
    }

    public static byte[] toNIOByteArray(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        nioCopy(is, baos);
        return baos.toByteArray();
    }

    public static byte[] toByteArrayx(InputStream is, int length) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        copyx(is, baos, length);
        return baos.toByteArray();
    }

    public static char[] toCharArray(Reader reader) {
        CharArrayWriter caw = new CharArrayWriter();
        copy(reader, caw);
        return caw.toCharArray();
    }

    public static char[] toCharArrayx(Reader reader, int length) {
        CharArrayWriter caw = new CharArrayWriter();
        copyx(reader, caw, length);
        return caw.toCharArray();
    }

    public static String toString(InputStream is) {
        return toString(new InputStreamReader(is));
    }

    public static String toNIOString(InputStream is) {
        byte[] bytes = toNIOByteArray(is);
        return new String(bytes, 0, bytes.length);
    }

    public static String toStringln(InputStream is) {
        StringWriter sw = new StringWriter();
        try {
            System.out.println("read available:" + is.available());
            copyln(is, sw);
            return sw.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toString(Reader reader) {
        StringWriter sw = new StringWriter();
        copy(reader, sw);
        return sw.toString();
    }

    public static void write(OutputStream os, byte[] bytes) {
        if (bytes == null)
            bytes = new byte[0];
        try {
            os.write(bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            flushIO(os);
        }
    }

    public static void write(OutputStream os, String string) {
        byte[] bytes;
        if (string == null)
            bytes = new byte[0];
        else
            bytes = string.getBytes();
        write(os, bytes);
    }

    public static void println(OutputStream os, String string) {
        PrintStream ps = new PrintStream(os);
        ps.println(string);
    }

    public static void print(OutputStream os,String string){
        PrintStream ps = new PrintStream(os);
        ps.print(string);
    }

    private static long bufferedCopy(InputStream is, OutputStream os, int size) {
        byte[] bytes = new byte[size];
        int len = 0;
        long count = 0;
        try {
            while ((len = is.read(bytes)) != -1) {
                os.write(bytes, 0, len);
                count += len;
            }
            flushIO(os);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return count;
    }

    private static long bufferedCopy(Reader reader, Writer writer, int bufsize) {
        char[] buffer = new char[bufsize];
        int len = 0;
        long count = 0;
        try {
            while ((len = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, len);
                count += len;
            }
            flushIO(writer);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return count;
    }

    /**
     * 拷贝小于4G的流
     *
     * @param is
     * @param os
     * @return
     */
    public static long copy(InputStream is, OutputStream os) {
        return bufferedCopy(is, os, DEFAULT_BUFFER_SIZE);
    }

    public static long copy(Reader reader, Writer writer) {
        return bufferedCopy(reader, writer, DEFAULT_BUFFER_SIZE);
    }

    public static long copy(InputStream is, Writer writer) {
        InputStreamReader isr = new InputStreamReader(is);
        return copy(isr, writer);
    }

    public static long copy(Reader reader, OutputStream os) {
        OutputStreamWriter osw = new OutputStreamWriter(os);
        return copy(reader, osw);
    }

    public static long nioCopy(InputStream is, OutputStream os, int size) {
        ReadableByteChannel readChannel = Channels.newChannel(is);
        WritableByteChannel writeChannel = Channels.newChannel(os);
        ByteBuffer buffer = ByteBuffer.allocate(size);
        int len = 0;
        int count = 0;
        try {
            while ((len = readChannel.read(buffer)) != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    writeChannel.write(buffer);
                }
                buffer.clear();
                count += len;
            }
            return count;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static long nioCopy(InputStream is, OutputStream os) {
        return nioCopy(is, os, DEFAULT_BUFFER_SIZE << 2);
    }

    public static long nioCopyx(InputStream is, OutputStream os, int size, int packetsize) {
        ReadableByteChannel readChannel = Channels.newChannel(is);
        WritableByteChannel writeChannel = Channels.newChannel(os);
        ByteBuffer buffer = ByteBuffer.allocate(size);
        int len = 0;
        int count = 0;
        try {
            for (; ; ) {
                if (count >= packetsize)
                    break;
                len = readChannel.read(buffer);
                buffer.flip();
                while (buffer.hasRemaining()) {
                    writeChannel.write(buffer);
                }
                buffer.clear();
                count += len;
            }
            return count;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static long nioCopyx(InputStream is, OutputStream os, int packetsize) {
        return nioCopyx(is, os, DEFAULT_BUFFER_SIZE << 2, packetsize);
    }

    public static long copyx(InputStream is, OutputStream os, int bufsize, int packetsize) {
        byte[] bytes = new byte[bufsize];
        int len = 0;
        long count = 0;
        try {
            for (; ; ) {
                if (count >= packetsize)
                    break;
                len = is.read(bytes);
                os.write(bytes, 0, len);
                count += len;
            }
            flushIO(os);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return count;
    }

    public static long copyx(InputStream is, OutputStream os, int packetSize) {
        return copyx(is, os, DEFAULT_BUFFER_SIZE, packetSize);
    }

    public static long copyx(InputStream is, Writer writer, int packetSize) {
        return copyx(new InputStreamReader(is), writer, DEFAULT_BUFFER_SIZE, packetSize);
    }

    public static long copyx(Reader reader, OutputStream os, int packetSize) {
        return copyx(reader, new OutputStreamWriter(os), DEFAULT_BUFFER_SIZE, packetSize);
    }

    public static long copyx(Reader reader, Writer writer, int bufsize, int packetsize) {
        char[] buffer = new char[bufsize];
        int len = 0;
        long count = 0;
        try {
            for (; ; ) {
                if (count >= packetsize)
                    break;
                len = reader.read(buffer);
                writer.write(buffer, 0, len);
                count += len;
            }
            flushIO(writer);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return count;
    }

    public static long copyx(Reader reader, Writer writer, int packetsize) {
        return copyx(reader, writer, DEFAULT_BUFFER_SIZE,packetsize);
    }

    public static long copyln(Reader reader, Writer writer) {
        BufferedReader br = new BufferedReader(reader);
        BufferedWriter bw = new BufferedWriter(writer);
        String line = "";
        int count = 0;
        try {
            while ((line = br.readLine()) != null) {
                writer.write(line);
                count += line.length();
            }
            return count;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        } finally {
            closeIO(br);
            closeIO(bw);
        }
    }

    public static long copyln(InputStream is, OutputStream os) {
        return copyln(new InputStreamReader(is), new OutputStreamWriter(os));
    }

    public static long copyln(InputStream is, Writer writer) {
        return copyln(new InputStreamReader(is), writer);
    }

    public static long copyln(Reader reader, OutputStream os) {
        return copyln(reader, new OutputStreamWriter(os));
    }

    public static boolean equals(InputStream is1, InputStream is2) {
        String hashStr1 = CodecUtils.sha1(toByteArray(is1));
        String hashStr2 = CodecUtils.sha1(toByteArray(is2));
        return hashStr1.equals(hashStr2);
    }

    public static boolean equals(Reader reader1, Reader reader2) {
        String hashStr1 = CodecUtils.sha1(getBytes(toCharArray(reader1)));
        String hashStr2 = CodecUtils.sha1(getBytes(toCharArray(reader2)));
        return hashStr1.equals(hashStr2);
    }

    public static ReadableByteChannel streamToChannel(InputStream is) {
        return Channels.newChannel(is);
    }

    public static WritableByteChannel streamToChannel(OutputStream os) {
        return Channels.newChannel(os);
    }

    /**
     * 针对文件流的transferTo
     *
     * @param from
     * @param to
     * @return
     */
    public static long transferTo(FileInputStream from, OutputStream to) {
        FileChannel fChannel = from.getChannel();
        WritableByteChannel tChannel = streamToChannel(to);
        return transferTo(fChannel, tChannel);
    }

    public static long transferTo(FileChannel from, WritableByteChannel to) {
        long count = 0;
        try {
            count = from.transferTo(0, from.size(), to);
        } catch (IOException e) {
            e.printStackTrace();
            count = -1;
        }
        return count;
    }


    public static long transferFrom(InputStream from, FileOutputStream to) {
        ReadableByteChannel fChannel = streamToChannel(from);
        FileChannel tChannel = to.getChannel();
        try {
            return transferFrom(fChannel, tChannel, from.available());
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static long transferFrom(ReadableByteChannel from, FileChannel to, long length) {
        long count = 0;
        try {
            count = to.transferFrom(from, 0, length);
        } catch (IOException e) {
            e.printStackTrace();
            count = -1;
        }
        return count;
    }

}
