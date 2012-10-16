package com.zhyfoundry.crm.core.dao;

/**
 * <p>
 * DB pager info
 * </p>
 * <p>
 * 数据库分页信息类
 * </p>
 */
public class Pager {

    /**
     * <p>
     * start line number
     * </p>
     * <p>
     * 起始数据行号
     * </p>
     */
    private int firstResult;

    /**
     * <p>
     * max get count
     * </p>
     * <p>
     * 最大取得件数
     * </p>
     */
    private int maxResults;

    /**
     * @param firstResult 起始数据行号(numbered from 0)
     * @param maxResults 最大取得件数
     */
    public Pager(final int firstResult, final int maxResults) {
        super();
        this.firstResult = firstResult;
        this.maxResults = maxResults;
    }

    /**
     * @param maxResults 最大取得件数
     */
    public Pager(final int maxResults) {
        super();
        this.maxResults = maxResults;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(final int firstResult) {
        this.firstResult = firstResult;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(final int maxResults) {
        this.maxResults = maxResults;
    }

}
