package cdio.desert_eagle.project_bts.model.response;

import java.util.ArrayList;

public class PageResponse<T> {
    public ArrayList<Link> links;
    public ArrayList<T> content;
    public Page page;

    public ArrayList<Link> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<Link> links) {
        this.links = links;
    }

    public ArrayList<T> getContent() {
        return content;
    }

    public void setContent(ArrayList<T> content) {
        this.content = content;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
