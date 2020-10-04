package blockchain;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Dionysios Stolis 9/27/2020 <dionstol@gmail.com>
 */
public class Block {

    private String hash;
    private final String prevHash;
    private final int id;
    private final long timeStamp;
    private int magicNumber;
    private int proofOfWork;
    private final int numZeros;
    private final String minerName;
    private String zerosInfo;

    public Block(int numZeros, int id, String prevHash) {
        this.numZeros = numZeros;
        this.id = id;
        this.prevHash = prevHash;
        timeStamp = System.currentTimeMillis();
        this.minerName = Thread.currentThread().getName().substring(14);
        hash();
    }

    private void hash() {
        String zeros = new String(new char[numZeros]).replace("\0", "0");
        long t0 = System.currentTimeMillis();
        do {
            magicNumber = ThreadLocalRandom.current().nextInt();
            hash = applySha256(getString() + magicNumber);
        } while (!hash.startsWith(zeros));
        proofOfWork = (int) (System.currentTimeMillis() - t0) / 1000;
    }

    private String getString() {
        return id + timeStamp + prevHash;
    }

    public String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashByte = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte elem : hashByte) {
                String hex = Integer.toHexString(elem & 0xff);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String getHash() {
        return hash;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public int getId() {
        return id;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public int getMagicNumber() {
        return magicNumber;
    }

    public int getProofOfWork() {
        return proofOfWork;
    }

    public String getMinerName() {
        return minerName;
    }

    public String getZerosInfo() {
        return zerosInfo;
    }

    public void setZerosInfo(String info) {
        zerosInfo = info;
    }

    @Override
    public String toString() {
        String ls = System.lineSeparator();
        StringBuilder sb = new StringBuilder();

        sb.append("Block:").append(ls);
        sb.append("Created by miner # ").append(getMinerName()).append(ls);
        sb.append("Id: ").append(getId()).append(ls);
        sb.append("Timestamp: ").append(getTimeStamp()).append(ls);
        sb.append("Magic number: ").append(getMagicNumber()).append(ls);
        sb.append("Hash of the previous block:").append(ls);
        sb.append(getPrevHash()).append(ls);
        sb.append("Hash of the block:").append(ls);
        sb.append(getHash()).append(ls);
        sb.append("Block was generating for: ").append(getProofOfWork()).append(" seconds").append(ls);
        sb.append(getZerosInfo()).append(ls);

        return sb.toString();
    }
}
