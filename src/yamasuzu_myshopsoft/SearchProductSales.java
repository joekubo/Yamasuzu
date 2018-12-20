
package yamasuzu_myshopsoft;

public class SearchProductSales {
    private String productcode;
    private String type;
    private String description;
    private int qty;

    public SearchProductSales(String productcode, String type, String description, int qty) {
        this.productcode = productcode;
        this.type = type;
        this.description = description;
        this.qty = qty;
    }

    public String getProductcode() {
        return productcode;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getQty() {
        return qty;
    }
    
    
}
