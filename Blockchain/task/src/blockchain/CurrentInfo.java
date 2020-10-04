package blockchain;

/**
 * @author Dionysios Stolis 10/4/2020 <dstolis@gmail.com>
 */
class CurrentInfo {

    private final int currentID;
    private final int numZeros;
    private final String hash;

    public CurrentInfo (int currentID, int numZeros, String hash){
        this.currentID = currentID;
        this.numZeros = numZeros;
        this.hash = hash;
    }

    public int getCurrentID() {
        return currentID;
    }

    public int getNumZeros() {
        return numZeros;
    }

    public String getHash() {
        return hash;
    }
}
