package com.zhyfoundry.crm.core.dao;

import org.apache.commons.lang.StringUtils;

public class Pager {

	private int startRow;
	private int recordsPerPage;
	private final int pageNo;
	private long totalRows = -1;
	private int pageCount = -1;
	private final String order;

	public Pager(final int pageNo, final int recordsPerPage) {
		this(pageNo, recordsPerPage, null);
	}

	public Pager(final int pageNo, final int recordsPerPage, final String order) {
		super();
		this.order = geneOrderByHQL(order);
		this.pageNo = pageNo;
		this.recordsPerPage = recordsPerPage;
		this.startRow = calcStartIndex();
	}

	private String geneOrderByHQL(String order) {
		if (StringUtils.isEmpty(order)) {
			return "";
		}
		return " order by " + order.replace("=", " ").replace("&", ", ");
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(final int startRow) {
		this.startRow = startRow;
	}

	public int getRecordsPerPage() {
		return recordsPerPage;
	}

	public void setRecordsPerPage(final int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}

	public long getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(long totalRows) {
		this.totalRows = totalRows;
	}

	public int getPageCount() {
		if (pageCount != -1) {
			return pageCount;
		}
		return pageCount = calcPageSum();
	}

	public int getPageNo() {
		return pageNo;
	}

	public String getOrder() {
		return order;
	}

	/** 分页：计算从第几行开始 */
	private int calcStartIndex() {
		if (pageNo <= 1) {
			return 0;
		} else {
			return (pageNo - 1) * recordsPerPage;
		}
	}

	private int calcPageSum() {
		if (totalRows % recordsPerPage > 0) {
			return (int) (totalRows / recordsPerPage + 1);
		} else {
			return (int) (totalRows / recordsPerPage);
		}
	}

	public long getEndRow() {
		return startRow + Math.min(recordsPerPage, totalRows - startRow);
	}

	@Override
	public String toString() {
		return "Pager [recordsPerPage=" + recordsPerPage + ", pageNo=" + pageNo + "]";
	}
}
