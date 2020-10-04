package blockchain;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        Blockchain blockchain = Blockchain.getInstance();

        ExecutorService executor = Executors.newFixedThreadPool(15);
        for (int i = 0; i < 15; i++) {
            executor.submit(new Miner());
        }

        executor.awaitTermination(10, TimeUnit.SECONDS);
        blockchain.printFirstNthBlocks(5);
    }
}
