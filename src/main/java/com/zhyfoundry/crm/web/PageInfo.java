package com.zhyfoundry.crm.web;

public class PageInfo {

    private int page;
    private int pageSize;
    private int startIndex;
    private int count;
    private int pageCount;
    private String jsonData;

    public PageInfo() {}

    public PageInfo(final int page, final int pageSize, final int startIndex, final int count, final int pageCount) {
        super();
        this.page = page;
        this.pageSize = pageSize;
        this.startIndex = startIndex;
        this.count = count;
        this.pageCount = pageCount;
    }

    public PageInfo(final int page, final int pageSize, final int startIndex, final int count, final int pageCount, final String jsonData) {
        super();
        this.page = page;
        this.pageSize = pageSize;
        this.startIndex = startIndex;
        this.count = count;
        this.pageCount = pageCount;
        this.jsonData = jsonData;
    }

    public int getPage() {
        return page;
    }

    public void setPage(final int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(final int startIndex) {
        this.startIndex = startIndex;
    }

    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(final int pageCount) {
        this.pageCount = pageCount;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(final String jsonData) {
        this.jsonData = jsonData;
    }

}
