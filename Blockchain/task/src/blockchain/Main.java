package blockchain;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        blockchain.createBlock();
        try {
            SerializationUtils.serialize(blockchain);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(blockchain);
    }
}
