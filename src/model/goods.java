package model;

public class goods {
private Integer goodID;
private String goodName;
private String type;
private String brand;
private double price;
private Integer amount;
private String Info;
private String SaleStatus;

    public Integer getGoodID() {
        return goodID;
    }

    public void setGoodID(Integer goodID) {
        this.goodID = goodID;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }

    public String getSalestatus() {
        return SaleStatus;
    }

    public void setSalestatus(String salestatus) {
        this.SaleStatus = salestatus;
    }

    @Override
    public String toString() {
        return "goods{" +
                "goodID=" + goodID +
                ", goodName='" + goodName + '\'' +
                ", type='" + type + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", Info='" + Info + '\'' +
                ", SaleStatus='" + SaleStatus + '\'' +
                '}';
    }
}
