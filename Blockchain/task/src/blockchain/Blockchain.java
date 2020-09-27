package blockchain;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @author Dionysios Stolis 9/27/2020 <dstolis@gmail.com>
 */
public class Blockchain implements Serializable {
    
    private List<Block> blocks;

    public Blockchain() {
        try {
          Blockchain blockchain = (Blockchain) SerializationUtils.deserialize();
          this.blocks = blockchain.blocks;
        } catch (IOException | ClassNotFoundException e) {
            this.blocks = new ArrayList<>();
        }
    }

    private int proof;

    {
        System.out.println("Enter how many zeros the hash must starts with:");
        proof =  new Scanner(System.in).nextInt();
    }

    public void createBlock() {
        if (!validate()){
            throw new IllegalArgumentException("Blockchain is not valid");
        }
        String prevHash = blocks.isEmpty() ? "0" : blocks.get(blocks.size() - 1).getHash();
        Block newBlock = new Block(blocks.size() + 1, System.currentTimeMillis(), prevHash, proof);
        blocks.add(newBlock);
    }

    @Override
    public String toString() {
        return blocks.stream()
                .map(Block::toString)
                .collect(Collectors.joining("\n\n"));
    }

    private boolean validate() {
        for (int i = 1; i < blocks.size(); i++) {
            if (Objects.equals(blocks.get(i).getPrevHash(), blocks.get(i).getHash())) {
                return false;
            }
        }
        return true;
    }
}
