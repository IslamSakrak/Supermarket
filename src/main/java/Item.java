public class Item {
    private final String name;
    private final int price;

    public String getName() {
        return name;
    }
    public Item(String name, int price){
        this.name = name;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

}
