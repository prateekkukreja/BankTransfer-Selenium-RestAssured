package UIElement;

public abstract class BasePage {

    protected String pageName;
    protected String pageURL;

    public BasePage(String pageName) {
        this.pageName = pageName;
    }

    public String getPageName() {
        return this.pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }


}
