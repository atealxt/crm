package com.zhyfoundry.crm.core.dao;

public class Pager {

	private int startRow;
	private int recordsPerPage;

	private long totalRows = -1;

	public Pager(final int startRow, final int recordsPerPage) {
		super();
		this.startRow = startRow;
		this.recordsPerPage = recordsPerPage;
	}

	public Pager(final int recordsPerPage) {
		super();
		this.recordsPerPage = recordsPerPage;
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
}
