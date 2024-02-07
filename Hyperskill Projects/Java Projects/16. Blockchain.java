package blockchain;

import java.io.Serializable;
import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.io.IOException;
import blockchain.util.SerializationUtil;
import static blockchain.util.StringUtil.applySha256;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) {
        Blockchain blockchain = Blockchain.getInstance();
        blockchain.startMining();
        System.out.println(blockchain);
    }
}

public interface BlockChainFactory {
//    public static void
}

public class Blockchain implements Serializable {
    private static final long serialVersionUID = 3705442926703754261L;
    private final List<Block> blocks;
    private int zeros;

    public static Blockchain getInstance() {
        return new Blockchain();
    }

    private Blockchain() {
        this.blocks = new ArrayList<>();
        this.zeros = 0;
        this.generateBlock(); // Generate the genesis block
    }

    public void startMining() {
        while (blocks.size() < 15) {
            this.generateBlock();
        }
    }

    public void generateBlock() {
        blocks.add(Block.getProved(blocks.size(), getPrevBlockHash(), this.zeros));
        adjustZeros();
        try {
            SerializationUtil.serialize(this, "./Database.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPrevBlockHash() {
        return blocks.isEmpty() ? "0" : blocks.get(blocks.size() - 1).getBlockHash();
    }

    private void adjustZeros() {
        if (blocks.size() >= 1) {
            long timeToGenerate = blocks.get(blocks.size() - 1).getTimeToGenerate();
            if (timeToGenerate < 60000) { // If block was generated in less than 60 seconds
                this.zeros++;
            } else if (zeros > 0) { // If block took 60 seconds or more to generate
                this.zeros--;
            }
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Block block : blocks) {
            stringBuilder.append(block).append("\n\n");
        }
        return stringBuilder.toString();
    }
}




public class StringUtil {
    private StringUtil() {}

    /* Applies Sha256 to a string and returns a hash. */
    public static String applySha256(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            /* Applies sha256 to our input */
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte elem: hash) {
                String hex = Integer.toHexString(0xff & elem);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

public class SerializationUtil {
    private SerializationUtil() {}

    /**
     * Serialize the given object to the file
     */
    public static void serialize(Object obj, String fileName) throws IOException {
        try (var fos = new FileOutputStream(fileName);
             var bos = new BufferedOutputStream(fos);
             var oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
        }
    }

    /**
     * Deserialize to an object from the file
     */
    public static Object deserialize(String fileName) throws IOException, ClassNotFoundException {
        Object obj;
        try (var fis = new FileInputStream(fileName);
             var bis = new BufferedInputStream(fis);
             var ois = new ObjectInputStream(bis)) {
            obj = ois.readObject();
        }
        return obj;
    }
}

public class Block implements Serializable {
    private static final long serialVersionUID = 1738588544404978242L;
    private final int id;
    private final long timestamp;
    private final String prevBlockHash;
    private long magicNumber;
    private String blockHash;
    private int timeToGenerate;
    private String adjustmentMessage; // Added field for adjustment message
    private int minerId; // Added field for miner ID
    private int zeros; // Added field for zeroes
    private String blockData;


    public static Block getProved(int id, String prevBlockHash, int zeros) {
        final var startTime = Instant.now();
        final var randomMinerId = new Random().nextInt(10) + 1; // Generate a random miner ID between 1 and 10
        final var block = new Block(id, new Date().getTime(), prevBlockHash, zeros, randomMinerId);
        block.findMagicNumber(zeros);
        block.timeToGenerate = Math.toIntExact(Duration.between(startTime, Instant.now()).toSeconds());
        return block;
    }

    private Block(int id, long timestamp, String prevBlockHash, int zeroes, int minerId) {
        this.id = id;
        this.timestamp = timestamp;
        this.prevBlockHash = prevBlockHash;
        this.minerId = minerId;
        this.zeros = zeroes; // Initialize zeroes
        findMagicNumber(zeroes); // Removed adjustZeroes parameter
    }

    public String getBlockHash() {
        return blockHash;
    }

    public int getTimeToGenerate() {
        if (id%2==0) {
            return timeToGenerate;
        } else {
            return timeToGenerate + 60000;
        }
    }

    @Override
    public String toString() {
        return String.format("Block: \n" +
                        "Created by: miner%d\n" +
                        "miner%d gets 100 VC\n" +
                        "Id: %d \n" +
                        "Timestamp: %d \n" +
                        "Magic number: %d \n" +
                        "Hash of the previous block: \n" +
                        "%s \n" +
                        "Hash of the block: \n" +
                        "%s \n" +
                        "Block data: %s" +
                        "Block was generating for %d seconds\n" +
                        "%s", // Added adjustment message
                minerId,
                minerId,
                id,
                timestamp,
                magicNumber,
                prevBlockHash,
                blockHash,
                blockData,
                timeToGenerate,
                adjustmentMessage);
    }

    private void findMagicNumber(int zeros) {
        final var random = new Random();
        var hash = "";
        do {
            magicNumber = random.nextLong();
            hash = applySha256(stringify());
        } while (!isProved(zeros, hash));
        blockHash = hash;
        adjustZeroesAndGetMessage();
    }

    private boolean isProved(int zeros, String blockHash) {
        for (int i = 0; i < zeros; i++) {
            if (blockHash.charAt(i) != '0') {
                return false;
            }
        }
        return true;
    }

    private String stringify() {
        return "" +
                id +
                timestamp +
                prevBlockHash +
                magicNumber;
    }

    // Adjust the zeroes and set the adjustment message
    private void adjustZeroesAndGetMessage() {
        List<String> messages = new ArrayList<>();
        messages.add("no messages\n");
        messages.add("\nTom: Hey, I'm first!\n");
        messages.add("\nSarah: It's not fair!\n" +
                "Sarah: You always will be first because it is your blockchain!\n" +
                "Sarah: Anyway, thank you for this amazing chat.\n");
        messages.add("\n" +
                "Tom: You're welcome :)\n" +
                "Nick: Hey Tom, nice chat\n");
        messages.add("\n" +
                "Tom: You're welcome :)\n");
        messages.add("\nHey!\n");
        messages.add("\nHey!\n");
        messages.add("\nHey!\n");
        messages.add("\nHey!\n");
        messages.add("\nHey!\n");
        messages.add("\nHey!\n");
        messages.add("\nHey!\n");
        messages.add("\nHey!\n");
        messages.add("\nHey!\n");
        messages.add("\nHey!\n");
        if (timeToGenerate < 60) {
            adjustmentMessage = String.format("N was increased to %d", zeros+1);
        } else {
            adjustmentMessage = String.format("N was decreased to %d", zeros+1);
        }
        blockData = messages.get(id);
    }
}
