package model.entity;

import util.Column;
import util.Id;
import util.Table;

@Table(name = "products")
public class ProductEntity {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "category_id")
    private Integer categoryID;
    @Column(name = "image")
    private String image;
    @Column(name = "list_img")
    private String listImage;
    @Column(name = "price")
    private Float price;
    @Column(name = "sale_price")
    private Float salePrice;
    @Column(name = "description")
    private String description;
    @Column(name = "keyword")
    private String keyword;
    @Column(name = "content")
    private String content;
    @Column(name = "status")
    private boolean status;

    public ProductEntity() {
    }

    public ProductEntity(String id, String name, Integer categoryID, String image, String listImage, Float price, Float salePrice, String description, String keyword, String content, boolean status) {
        this.id = id;
        this.name = name;
        this.categoryID = categoryID;
        this.image = image;
        this.listImage = listImage;
        this.price = price;
        this.salePrice = salePrice;
        this.description = description;
        this.keyword = keyword;
        this.content = content;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getListImage() {
        return listImage;
    }

    public void setListImage(String listImage) {
        this.listImage = listImage;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Float salePrice) {
        this.salePrice = salePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
