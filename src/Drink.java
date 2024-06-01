public class Drink {
    private String name;
    private int price;
    private String imagePath;
    private int stock;

    public Drink(String name, int price, int stock, String imagePath) {
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
