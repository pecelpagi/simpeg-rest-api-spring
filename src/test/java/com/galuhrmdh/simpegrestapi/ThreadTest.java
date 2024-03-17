package com.galuhrmdh.simpegrestapi;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class ThreadTest {

    @Test
    void mainThread() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                try {
                    Thread.sleep(1000);
                    log.info("Belajar CountDownLatch");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    countDownLatch.countDown();
                }
            });

        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("SELESAI");

    }
}
