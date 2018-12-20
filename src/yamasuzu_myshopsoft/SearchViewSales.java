
package yamasuzu_myshopsoft;

public class SearchViewSales {
    private int salesid;
    private String receiptno;
    private String productcode;
    private String product;
    private String description;
    private String imei;
    private int qty;
    private String unitprice;
    private String saledate;
    private String means_of_payment;
    

    public SearchViewSales(int salesid, String receiptno, String productcode, String product, String description,String imei, int qty, String unitprice, String saledate, String means_of_payment) {
        this.salesid = salesid;
        this.receiptno = receiptno;
        this.productcode = productcode;
        this.product = product;
        this.description = description;
        this.imei = imei;
        this.qty = qty;
        this.unitprice = unitprice;
        this.saledate = saledate;
        this.means_of_payment = means_of_payment;
    }

    public int getSalesid() {
        return salesid;
    }

    public String getReceiptno() {
        return receiptno;
    }

    public String getProductcode() {
        return productcode;
    }

    public String getProduct() {
        return product;
    }

    public String getDescription() {
        return description;
    }

    public String getImei(){
        return imei;
    }
    public int getQty(){
        return qty;
    }
    public String getUnitprice() {
        return unitprice;
    }

    public String getSaledate() {
        return saledate;
    }

    public String getMeans_of_payment() {
        return means_of_payment;
    }
    
   
    
}
