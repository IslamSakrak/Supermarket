import java.io.*;
import java.util.*;

public class Supermarket {

    private static final Map<String, Integer> priceByProducts = new HashMap<>();

    public void shoppingCartItems() {
        getShoppingCartItems();
        System.out.println("There are " + priceByProducts.size() + " items in the cart.\nWhich is a total of $" + getProductSum() + " dollars."); // outputs the number of products
    }

    public int getRandomNumber() {
        int MAX_PRODUCT_PRICE = 100;
        return new Random().nextInt(1, MAX_PRODUCT_PRICE);
    }

    private int getProductSum() {
        int sum = 0;
        for (Map.Entry<String, Integer> priceByProduct : priceByProducts.entrySet()) {
            printProducts(priceByProduct);
            if (isProductOnDiscount() && isPickedForPriceChange(priceByProduct)) {
                priceDiscountText(priceByProduct);
                sum += getDiscount(priceByProduct);
            } else if (isProductPriceIncrease() && isPickedForPriceChange(priceByProduct)) {
                priceIncreaseText(priceByProduct);
                sum += getPriceIncrease(priceByProduct);
            } else {
                sum += priceByProduct.getValue();
            }
        }
        return sum;
    }


    private boolean isPickedForPriceChange(Map.Entry<String, Integer> priceByProduct) {
        return getRandomNumber() == priceByProduct.getValue();
    }

    private void printProducts(Map.Entry<String, Integer> shoppingCartElement) {
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

    private int getPriceIncrease(Map.Entry<String, Integer> shoppingCartItem) {
        return (int) (shoppingCartItem.getValue() * 1.1);
    }

    private int getDiscount(Map.Entry<String, Integer> shoppingCartItem) {
        return (int) (shoppingCartItem.getValue() * 0.7);
    }

    private void getShoppingCartItems() {
        try (BufferedReader br = getBufferedReader()) {
            for (String line; (line = br.readLine()) != null; ) {
                addToShoppingCart(line);
            }
        } catch (IOException e) {
            System.out.println(e.getCause().getMessage());
        }
    }

    private BufferedReader getBufferedReader() throws IOException {
        File file = new File("src/main/resources/groceriesList.txt");
        return new BufferedReader(new FileReader(file));
    }

    private void addToShoppingCart(String line) {
        priceByProducts.putIfAbsent(line, getRandomNumber());
    }

    private boolean isProductOnDiscount() {
        return Math.random() < 0.8;
    }

    private boolean isProductPriceIncrease() {
        return Math.random() < 0.3;
    }
}