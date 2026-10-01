package vn.hcmute.de4.model;

import java.util.List;

public class PageResult_24162037<T> {
    private final List<T> items;
    private final int page;
    private final int size;
    private final long totalItems;
    private final int totalPages;

    public PageResult_24162037(List<T> items, int page, int size, long totalItems) {
        this.items = items; this.page = page; this.size = size; this.totalItems = totalItems;
        this.totalPages = Math.max(1, (int) Math.ceil((double) totalItems / size));
    }
    public List<T> getItems() { return items; }
    public int getPage() { return page; }
    public int getSize() { return size; }
    public long getTotalItems() { return totalItems; }
    public int getTotalPages() { return totalPages; }
    public boolean isFirst() { return page <= 0; }
    public boolean isLast() { return page >= totalPages - 1; }
}
