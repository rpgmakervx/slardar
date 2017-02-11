package org.easyarch.slardar.jdbc;/**
 * Description : 
 * Created by YangZH on 16-11-2
 *  上午11:23
 */

import java.util.concurrent.*;

/**
 * Description :
 * Created by code4j on 16-11-2
 * 上午11:23
 */

public class Main {


    public static void main(String[] args) throws InterruptedException {
        TransferQueue<Integer> transferQueue = new LinkedTransferQueue();
        BlockingQueue<Integer> linkedBlockingQueue = new LinkedBlockingQueue<Integer>();
        ConcurrentLinkedQueue<Integer> concurrentLinkedQueue = new ConcurrentLinkedQueue<Integer>();
        SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<Integer>();
        long length = (2 << 19L);
        System.out.println("elemet length--->"+length);
        double all = 0.0;
        //-----------transfer offeer
        long begin = System.nanoTime();
        for (int index = 0; index < length; index++) {
            transferQueue.offer(index);
        }
        long transferused = (System.nanoTime() - begin);
        all += transferused;
        System.out.println("transfer offer time:   " + transferused);

        //-----------linked offeer
        begin = System.nanoTime();
        for (int index = 0; index < length; index++) {
            linkedBlockingQueue.offer(index);
        }
        long linkedused = (System.nanoTime() - begin);
        all += linkedused;
        System.out.println("linked offer time:     " + linkedused);

        //-----------concurrent offeer
        begin = System.nanoTime();
        for (int index = 0; index < length; index++) {
            concurrentLinkedQueue.offer(index);
        }
        long concurrentused = (System.nanoTime() - begin);
        all += concurrentused;
        System.out.println("concurrent offer time: " + concurrentused);

        //-----------sync offeer
        begin = System.nanoTime();
        for (int index = 0; index < length; index++) {
            synchronousQueue.offer(index);
        }
        long syncused = (System.nanoTime() - begin);
        all += syncused;
        System.out.println("sync offer time:       " + syncused);

        System.out.println("transfer percent:" + (transferused/all)*100.0+"% , linked percent:"+
                (linkedused/all)*100.0+"% , concurrent percent:"+ (concurrentused/all)*100.0+"%.sync percent:"+
                        (syncused/all)*100.0+"%.\n" +
                "\n" +
                "----------------------------------\n" +
                "\n");



        all = 0.0;

        //-----------transfer offeer
        begin = System.nanoTime();
        for (int index = 0; index < length; index++) {
            transferQueue.poll();
        }
        transferused = (System.nanoTime() - begin);
        all += transferused;
        System.out.println("transfer poll time:    " + transferused);

        //-----------linked offeer
        begin = System.nanoTime();
        for (int index = 0; index < length; index++) {
            linkedBlockingQueue.poll();
        }
        linkedused = (System.nanoTime() - begin);
        all += linkedused;
        System.out.println("linked offer time:     " + linkedused);

        //-----------concurrent offeer
        begin = System.nanoTime();
        for (int index = 0; index < length; index++) {
            concurrentLinkedQueue.poll();
        }
        concurrentused = (System.nanoTime() - begin);
        all += concurrentused;
        System.out.println("concurrent offer time: " + concurrentused);
        //-----------sync offeer
        begin = System.nanoTime();
        for (int index = 0; index < length; index++) {
            synchronousQueue.poll();
        }
        syncused = (System.nanoTime() - begin);
        all += syncused;
        System.out.println("sync offer time:       " + syncused);

        System.out.println("transfer percent:" + (transferused/all)*100.0+"% , linked percent:"+
                (linkedused/all)*100.0+"% , concurrent percent:"+ (concurrentused/all)*100.0+"%.sync percent:"+
                (syncused/all)*100.0+"%.\n");
    }

    public static long usedTime(long begin) {
        return System.nanoTime();
    }

}
