package blockchain;

import java.io.*;

/**
 * @author Dionysios Stolis 9/27/2020 <dionstol@gmail.com>
 */
//TODO Add persistence layer with saving the blockchain to file
public class SerializationUtils {

    private static final String FILENAME = "blockchain.txt";

    /**
     * Serialize the given object to the file
     */
    protected static void serialize(Object obj) throws IOException {
        FileOutputStream fos = new FileOutputStream(FILENAME);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.close();
    }

    /**
     * Deserialize to an object from the file
     */
    protected static Object deserialize() {
        FileInputStream fis;
        try {
            fis = new FileInputStream(FILENAME);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Object obj = ois.readObject();
            ois.close();
            return obj;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
