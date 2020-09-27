package blockchain;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @author Dionysios Stolis 9/27/2020 <dionstol@gmail.com>
 */
public class Block implements Serializable {

    private static MessageDigest digest;

    static {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private long id;
    private long ts;
    private String prevHash;
    private int magicNum;
    private String hash;

    public Block(long id, long ts, String prevHash, int proof) {
        this.id = id;
        this.ts = ts;
        this.prevHash = prevHash;
        this.hash = generateHash(proof, ts);
    }

    public long getId() {
        return id;
    }

    public long getTs() {
        return ts;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public String getHash() {
        return hash;
    }

    @Override
    public String toString() {
        long milliseconds = System.currentTimeMillis() - ts;
        long seconds = (int) (milliseconds / 1000) % 60;
        StringBuilder sb = new StringBuilder("Block:").append("\n");
        sb.append("Id:").append(id).append("\n");
        sb.append("Timestamp: ").append(ts).append("\n");
        sb.append("Magic number: ").append(magicNum).append("\n");
        sb.append("Hash of the previous block:").append("\n");
        sb.append(prevHash).append("\n");
        sb.append("Hash of the block:").append("\n");
        sb.append(hash).append("\n");
        sb.append("Block was generating for ").append(seconds).append(" seconds").append("\n");

        return sb.toString();
    }

    public String applySha256(String input) {
        try {
            /* Applies sha256 to our input */
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte elem : hash) {
                String hex = Integer.toHexString(0xff & elem);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String generateHash(int proof, long timeStamp) {
        String zeros = "0".repeat(proof);
        Random random = new Random(34564);
        int magic;
        String hash;
        do {
            magic = random.nextInt(1000000);
            hash = applySha256( timeStamp + String.valueOf(id) + magic  + prevHash);
        } while (!hash.startsWith(zeros));
        magicNum = magic;
        this.hash = hash;
        return hash;
    }
}
