package com.excilys.cdb.model;

import static java.lang.Math.ceil;
import static java.lang.Math.max;
import static java.lang.Math.min;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Page {

	public static final int DEFAULT_PAGE_SIZE = 50;
	public static final int MINIMUM_PAGE_INDEX = 0;
	
	private static Logger logger = LoggerFactory.getLogger(Page.class);
	
	private Integer pageIndex;
	private Integer pageMax;
	private Integer pageSize;
	
	private Page() {
		pageIndex = MINIMUM_PAGE_INDEX;
		pageMax = null;
	}
	
	/**
	 * Switch to the first page
	 */
	public void first() {
		pageIndex = MINIMUM_PAGE_INDEX;
	}

	/**
	 * Switch to the next page.
	 */
	public void next() {
		pageIndex = min(pageIndex + 1, pageMax);
	}
	
	/**
	 * Switch to the previous page.
	 */
	public void previous() {
		pageIndex = max(MINIMUM_PAGE_INDEX, pageIndex - 1);
	}
	
	/**
	 * Switch to the last page
	 */
	public void last() {
		pageIndex = pageMax;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public Integer getPageMax() {
		return pageMax;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	
	public String toString() {
		return String.format("<Page %s/%s with %s element by page>", pageIndex, pageMax, pageSize);
	}
	
	public static class PageBuilder {
		
		private Integer pageIndex = MINIMUM_PAGE_INDEX;
		private Integer elementsCount = 0;
		private Integer pageSize = DEFAULT_PAGE_SIZE;
		
		public PageBuilder withElementsCount(Integer elementsCount) throws IllegalArgumentException {
			if (elementsCount == null || elementsCount < 0) {
				logger.error("Page elements count can't be null or negative");
				throw new IllegalArgumentException("Page element count can't be null or negative");
			}
			this.elementsCount = elementsCount;
			return this;
		}
		
		public PageBuilder withPageSize(Integer pageSize) {
			if (elementsCount == null || elementsCount < 0) {
				logger.error("Page size can't be null or negative");
				throw new IllegalArgumentException("Page size can't be null or negative");
			}
			this.pageSize = pageSize;
			return this;
		}
		
		public PageBuilder withPageIndex(Integer pageIndex) {
			if (pageIndex == null || pageIndex < 0) {
				this.pageIndex = MINIMUM_PAGE_INDEX;
			}
			else {
				this.pageIndex = pageIndex;
			}
			return this;
		}
		
		public Page build() {
			Integer pageMax = (int) ceil(((double) elementsCount) / pageSize) - 1;;
			Page page = new Page();
			page.pageSize = pageSize;
			page.pageMax = pageMax;
			page.pageIndex = min(pageIndex, page.pageMax);
			
			return page;
		}
		
	}

}
