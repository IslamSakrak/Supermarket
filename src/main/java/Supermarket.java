import java.io.*;
import java.util.*;
public class Supermarket {
    private static final Random random = new Random();
    private static final Map<String, Integer> priceByProducts = new HashMap<>();

    public void transaction() {
        getShoppingCartItems();
        System.out.println("There are " + priceByProducts.size() + " items in the cart.");
        List<Integer> values = priceByProducts.values().stream().toList();
        int randomShoppingCartItemPrice = getRandomItem(values);
        int sum = getPriceSum(randomShoppingCartItemPrice);
        System.out.println("Which is a total of $" + sum + " dollars.");
    }

    private int getPriceSum(int randomValue) {
        int sum = 0;
        /*
        valuesByKeys as in teamsByCaptains. If you are going to include both key and value, this seems to read best.
         At a high level, you can read it as just teams”, so anything that's performed on it is being performed on teams.
          The byCaptains prefix reads as it should do: a less significant qualifier that follows the teams around to help
           someone understands the structure if they need to.
         */
        for (Map.Entry<String, Integer> priceByProduct : priceByProducts.entrySet()) {
            printMapElements(priceByProduct);
            if (isProductOnDiscount() && randomValue == priceByProduct.getValue()) {
                priceDiscountText(priceByProduct);
                sum += getDiscount(priceByProduct);
            } else if (isProductPriceIncrease() && randomValue == priceByProduct.getValue()) {
                priceIncreaseText(priceByProduct);
                sum += getPriceIncrease(priceByProduct);
            } else {
                sum += priceByProduct.getValue();
            }
        }
        return sum;
    }
                                                
    private void printMapElements(Map.Entry<String, Integer> shoppingCartElement) {
        System.out.println(shoppingCartElement.getKey() + ":" + shoppingCartElement.getValue());
    }

    private void priceIncreaseText(Map.Entry<String, Integer> shoppingCartItem) {
        System.out.println("There is a price increase on " + shoppingCartItem.getKey() + " which price has increased from " + shoppingCartItem.getValue() + " to "
                + (int) (shoppingCartItem.getValue() * 1.1));
    }

    private void priceDiscountText(Map.Entry<String, Integer> shoppingCartItem) {
        System.out.println("There is a discount on " + shoppingCartItem.getKey() + " which price has dropped from " + shoppingCartItem.getValue() + " to "
                + (int) (shoppingCartItem.getValue() * 0.7));
    }

    private double getPriceIncrease(Map.Entry<String, Integer> shoppingCartItem) {
        return shoppingCartItem.getValue() * 1.1;
    }

    private double getDiscount(Map.Entry<String, Integer> shoppingCartItem) {
        return shoppingCartItem.getValue() * 0.7;
    }

    private int getRandomItem(List<Integer> values) {
        return values.get(random.nextInt(values.size()));
    }

    private void getShoppingCartItems() {
        try (BufferedReader br = getBufferedReader();) {
            for (String line; (line = br.readLine()) != null; ) {
                addToShoppingCart(line);
            }
        } catch (IOException e) {
            System.out.println("Something is wrong with the file");
        }
    }

    private BufferedReader getBufferedReader() throws IOException {
        File file = new File("src/main/resources/groceriesList.txt");
        FileReader fileReader = new FileReader(file);
        return new BufferedReader(fileReader);
    }

    private void addToShoppingCart(String line) {
        priceByProducts.putIfAbsent(line, random.nextInt(1,100));
    }

    private boolean isProductOnDiscount() {
        double d = Math.random();
        return d < 0.5;
    }

    private boolean isProductPriceIncrease() {
        double d = Math.random();
        return d < 0.2;
    }
}