package com.excilys.cdb.ui.page;

import static java.lang.Math.max;

import java.sql.SQLException;

public abstract class AbstPage {

	public static final int PAGE_SIZE = 50;
	public static final int MINIMUM_PAGE_INDEX = 0;
	
	protected int pageIndex;
	
	public AbstPage() {
		pageIndex = MINIMUM_PAGE_INDEX;
	}
	
	/**
	 * Draw the current page elements.
	 * @throws SQLException
	 */
	public abstract void drawPage() throws SQLException;

	/**
	 * Switch to the next page.
	 */
	public void next() {
		++this.pageIndex;
	}
	
	/**
	 * Switch to the previous page.
	 */
	public void previous() {
		this.pageIndex = max(MINIMUM_PAGE_INDEX, pageIndex - 1);
	}

}
