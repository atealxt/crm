package com.zhyfoundry.crm.core.dao;

public class Pager {

	private int startRow;
	private int recordsPerPage;
	private final int pageNo;
	private long totalRows = -1;
	private int pageCount = -1;

	public Pager(final int pageNo, final int recordsPerPage) {
		super();
		this.pageNo = pageNo;
		this.recordsPerPage = recordsPerPage;
		this.startRow = calcStartIndex();
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
		if (pageCount != -1){
			return pageCount;
		}
		return pageCount = calcPageSum();
	}

	public int getPageNo() {
		return pageNo;
	}

	/** 分页：计算从第几行开始 */
	private int calcStartIndex() {
		if (pageNo <= 1) {
			return 0;
		} else {
			return (pageNo - 1) * recordsPerPage;
		}
	}

	/** 分页：计算总页数 */
	private int calcPageSum() {
		if (totalRows % recordsPerPage > 0) {
			return (int) (totalRows / recordsPerPage + 1);
		} else {
			return (int) (totalRows / recordsPerPage);
		}
	}

	@Override
	public String toString() {
		return "Pager [recordsPerPage=" + recordsPerPage + ", pageNo=" + pageNo + "]";
	}
}
