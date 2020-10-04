package blockchain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author Dionysios Stolis 9/27/2020 <dionstol@gmail.com>
 */
public class Blockchain implements Serializable {

    public static Blockchain instance = new Blockchain();
    private final ArrayList<Block> blockChain = new ArrayList<>();
    private final AtomicInteger currentId;
    private final AtomicInteger numZeros;
    private static final long serialVersionUID = 1L;
    private final Object lockAdd = new Object();
    private final Object lockGet = new Object();
    private final String increase = "N was increased to ";
    private final String decrease = "N was decreased by 1";
    private final String stays = "N stays the same";

    public Blockchain() {
        this.currentId = new AtomicInteger(1);
        this.numZeros = new AtomicInteger(0);
    }

    public boolean addBlock(Block block) {
        synchronized (lockAdd) {
            if (validate(block)) {
                blockChain.add(block);
                currentId.incrementAndGet();
                if (block.getProofOfWork() > 2) {
                    numZeros.decrementAndGet();
                    block.setZerosInfo(decrease);
                } else if (block.getProofOfWork() < 1) {
                    block.setZerosInfo(increase + numZeros.incrementAndGet());
                } else {
                    block.setZerosInfo(stays);
                }
                return true;
            }
            return false;
        }
    }

    private boolean validate(Block block) {
        if (blockChain.isEmpty() && block.getPrevHash().equals("0")) {
            return true;
        }

        String zeros = new String(new char[numZeros.get()]).replace("\0", "0");
        String bcPrevHash = getLastHash();
        String blockHash = block.getHash();
        String bPrevHash = block.getPrevHash();
        return blockHash.startsWith(zeros) && bPrevHash.equals(bcPrevHash);
    }

    public boolean validateBlockChain() {
        if (blockChain.isEmpty()) {
            return true;
        }

        for (int i = 1, n = blockChain.size(); i < n; i++) {
            if (!validate(blockChain.get(i))) {
                return false;
            }
        }
        return true;
    }

    private String getLastHash() {
        return getSize() == 0 ? "0" : blockChain.get(getSize() - 1).getHash();
    }

    public synchronized int getSize() {
        return blockChain.size();
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        if (!validateBlockChain()) {
            throw new RuntimeException("Blockchain is not valid");
        }
    }

    public static Blockchain getInstance() {
        return instance;
    }

    public void printFirstNthBlocks(int max) {
        int size = getSize();
        int n = Math.min(max, size);
        for (int i = 0; i < n; i++) {
            System.out.println(blockChain.get(i));
        }
    }

    public CurrentInfo getCurrentInfo() {
        synchronized (lockGet) {
            return new CurrentInfo(currentId.get(), numZeros.get(), getLastHash());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Block block : blockChain) {
            sb.append(block).append(System.lineSeparator());
        }

        return sb.toString();
    }
}
