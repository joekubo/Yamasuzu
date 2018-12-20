
package yamasuzu_myshopsoft;

public class SearchCategory {
    private int categoryid;
    private String category;

    public SearchCategory(int categoryid, String category) {
        this.categoryid = categoryid;
        this.category = category;
    }

    public int getCategoryid() {
        return categoryid;
    }
    public String getCategory() {
        return category;
    }
    
}
