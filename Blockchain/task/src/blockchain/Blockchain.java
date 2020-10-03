package blockchain;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Dionysios Stolis 9/27/2020 <dionstol@gmail.com>
 */
public class Blockchain implements Serializable {

    public Blockchain instance = new Blockchain();
    private final ArrayList<Block> blockChain = new ArrayList<>();
    private final AtomicInteger currentId;
    private final AtomicInteger numZeros;
    private static final long serialVersionUID = 1L;
    private final Object lockAdd = new Object();
    private final Object lockGet = new Object();
    private final String increase = "N was increased to ";
    private final String decrease = "N was decreased by 1";
    private final String stays = "N stays the same";

    public Blockchain(){
        this.currentId = new AtomicInteger(1);
        this.numZeros = new AtomicInteger(0);
    }

    private ExecutorService executor;

    private int proof;

    {
        System.out.println("Enter how many zeros the hash must starts with:");
        proof =  new Scanner(System.in).nextInt();
    }

//    public Blockchain() {
//        try {
//          Blockchain blockchain = (Blockchain) SerializationUtils.deserialize();
//          this.blocks = blockchain.blocks;
//
//        } catch (IOException | ClassNotFoundException e) {
//            this.blocks = new ArrayList<>();
//        }
//    }

    public void createBlock() {
        executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            Miner miner = new Miner(this);
            miner.run();
        });
//        if (!validate()){
//            throw new IllegalArgumentException("Blockchain is not valid");
//        }
//        String prevHash = blocks.isEmpty() ? "0" : blocks.get(blocks.size() - 1).getHash();
//        Block newBlock = new Block(blocks.size() + 1, System.currentTimeMillis(), prevHash, proof);
//        blocks.add(newBlock);
    }

//    @Override
//    public String toString() {
//        return blocks.stream()
//                .map(Block::toString)
//                .collect(Collectors.joining("\n\n"));
//    }
//
//    boolean validate() {
//        for (int i = 1; i < blocks.size(); i++) {
//            if (Objects.equals(blocks.get(i).getPrevHash(), blocks.get(i).getHash())) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public List<Block> getBlocks() {
//        return blocks;
//    }
}
