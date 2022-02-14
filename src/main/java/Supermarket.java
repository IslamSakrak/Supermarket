import java.io.*;
import java.nio.file.FileSystemNotFoundException;
import java.util.*;

public class Supermarket {
    private final Map<String, Integer> shoppingCartItems = new HashMap<>();

    public void start() {
        try {
            File file = new File("src/main/resources/groceriesList.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            int MAX_LINE = 96;
            for (int i = 0; i < MAX_LINE ; i++) {
                line = bufferedReader.readLine().toLowerCase(Locale.ROOT);
                shoppingCartItems.putIfAbsent(line,(int) (Math.random()*100 +1));
            }
        } catch (IOException e) {
            throw new FileSystemNotFoundException("The file could not be found, check the file or adjust the location");
        }
        System.out.println("There are " + shoppingCartItems.size() + " items in the cart.");
        int sum = 0;
        Random generator = new Random();
        Object[] values = shoppingCartItems.values().toArray();
        Object randomValue = values[generator.nextInt(values.length)];
        for (Map.Entry<String, Integer> entry : shoppingCartItems.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
            if (isOnDiscount() && randomValue == entry.getValue()) {
                System.out.println("There is a discount on " + entry.getKey() + " which price has dropped from " + entry.getValue() + " to "
                        + (int) (entry.getValue() * 0.7));
                sum += entry.getValue() * 0.7;
            } else if (isPriceIncrease() && randomValue == entry.getValue()) {
                System.out.println("There is a price increase on " + entry.getKey() + " which price has increased from " + entry.getValue() + " to "
                        + (int) (entry.getValue() * 1.1));
                sum += entry.getValue() * 1.1;
            } else {
                sum += entry.getValue();
            }
        }
        System.out.println("Which is a total of $" + sum + " dollars.");
    }

    private boolean isOnDiscount() {
        double d = Math.random();
        return d < 0.5;
    }

    private boolean isPriceIncrease() {
        double d = Math.random();
        return d < 0.2;
    }
}
