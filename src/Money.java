public class Money {
    String name;
    int price;
    int stock;
    public Money(String name, int price, int stock) {
        this.name = name;
        this.price = price;
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
    public int getStock() {
        return stock;
    }
    public void increaseStock() {
        stock++;
    }
    public void decreaseStock() {
        stock--;
    }
}
