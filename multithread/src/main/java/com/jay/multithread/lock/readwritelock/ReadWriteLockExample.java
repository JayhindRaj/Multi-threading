package com.jay.multithread.lock.readwritelock;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {
    public static final int HIGHEST_PRICE = 1000;

    public static void main(String[] args) {
        InventoryDataBase inventoryDataBase = new InventoryDataBase();
        Random random = new Random();

        for (int i = 0; i < 10000000; i++) {
            inventoryDataBase.addItem(random.nextInt(HIGHEST_PRICE));
        }

        Thread writer = new Thread(() -> {
            while (true) {
                inventoryDataBase.addItem(random.nextInt(HIGHEST_PRICE));
                inventoryDataBase.removeItem(random.nextInt(HIGHEST_PRICE));

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Writer-Thread");

        writer.setDaemon(true);
        writer.start();

        int noOFReaderThreads = 15;
        List<Thread> readers = new ArrayList<>();

        for (int readerIndex = 0; readerIndex < noOFReaderThreads; readerIndex++) {
            Thread reader = new Thread(() -> {
                int upperBound = random.nextInt(HIGHEST_PRICE);
                int lowerBound = upperBound > 0 ? random.nextInt(upperBound) : 0;
                inventoryDataBase.getNumberOfItemsInPriceRange(lowerBound, upperBound);
            }, "Reader-Thread-" + (readerIndex + 1));

            reader.setDaemon(true);
            readers.add(reader);
        }

        long startReadingTime = System.currentTimeMillis();
        for (Thread thread : readers) {
            thread.start();
        }
        for (Thread thread : readers) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endReadingTime = System.currentTimeMillis();
        System.out.println(String.format("Reading took %d ms", endReadingTime - startReadingTime));
    }

    public static class InventoryDataBase {
        //        private ReentrantLock reentrantLock = new ReentrantLock();
        private ReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        private Lock write = reentrantReadWriteLock.writeLock();
        private Lock read = reentrantReadWriteLock.readLock();
        private TreeMap<Integer, Integer> priceToCountMap = new TreeMap<>();

        public int getNumberOfItemsInPriceRange(int lowerBound, int upperBound) {
            read.lock();
            try {
                Integer fromKey = priceToCountMap.ceilingKey(lowerBound);
                Integer toKey = priceToCountMap.floorKey(upperBound);

                if (fromKey == null || toKey == null) {
                    return 0;
                }

                NavigableMap<Integer, Integer> rangeOfPrices = priceToCountMap.subMap(fromKey, true, toKey, true);

                int sum = 0;

                for (int noOfItemsForPrice : rangeOfPrices.values()) {
                    sum += noOfItemsForPrice;
                }

                return sum;
            } finally {
                read.unlock();
            }
        }

        public void addItem(int price) {
            write.lock();
            try {
                priceToCountMap.merge(price, 1, Integer::sum);
            } finally {
                write.unlock();
            }
        }

        public void removeItem(int price) {
            write.lock();
            try {
                Integer noOfItemsForPrice = priceToCountMap.get(price);
                if (noOfItemsForPrice == null || noOfItemsForPrice == 1) {
                    priceToCountMap.remove(price);
                } else {
                    priceToCountMap.put(price, noOfItemsForPrice - 1);
                }
            } finally {
                write.unlock();
            }
        }
    }
}
