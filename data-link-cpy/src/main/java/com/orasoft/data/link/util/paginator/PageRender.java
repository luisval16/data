package com.orasoft.data.link.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {
	private String url;
	private Page<T> page;
	private int totalPages;
	private int numElementsPerPage;
	private int actualPage;

	private List<PageItem> pages;

	public PageRender(String url, Page<T> page) {

		this.url = url;
		this.page = page;
		this.numElementsPerPage = page.getSize();
		this.totalPages = page.getTotalPages();
		this.actualPage = page.getNumber() + 1;
		this.pages = new ArrayList<PageItem>();

		int desde, hasta;
		if (totalPages <= numElementsPerPage) {
			desde = 1;
			hasta = totalPages;
		} else {
			if (this.actualPage <= this.numElementsPerPage / 2) {
				desde = 1;
				hasta = this.numElementsPerPage;
			} else if (this.actualPage >= this.totalPages - this.numElementsPerPage / 2) {
				desde = this.totalPages - this.numElementsPerPage + 1;
				hasta = this.numElementsPerPage;
			} else {
				desde = this.actualPage - this.numElementsPerPage / 2;
				hasta = this.numElementsPerPage;

			}
		}

		for (int i = 0; i < hasta; i++) {
			pages.add(new PageItem(desde + i, this.actualPage == desde + i));
		}
	}

	public String getUrl() {
		return url;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getActualPage() {
		return actualPage;
	}

	public List<PageItem> getPages() {
		return pages;
	}

	public boolean isFirst() {

		return page.isFirst();
	}

	public boolean isLast() {

		return page.isLast();
	}

	public boolean isHasNext() {

		return page.hasNext();
	}

	public boolean isHasPrevious() {
		return page.hasPrevious();

	}

}
