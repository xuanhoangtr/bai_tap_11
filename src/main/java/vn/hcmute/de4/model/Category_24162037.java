package vn.hcmute.de4.model;

public class Category_24162037 {
    private Long categoryId;
    private String categoryName;
    private String categoryCode;
    private String images;
    private boolean status = true;
    private long videoCount;

    public Category_24162037() {}
    public Category_24162037(Long categoryId, String categoryName, String categoryCode, String images, boolean status) {
        this.categoryId = categoryId; this.categoryName = categoryName; this.categoryCode = categoryCode;
        this.images = images; this.status = status;
    }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getCategoryCode() { return categoryCode; }
    public void setCategoryCode(String categoryCode) { this.categoryCode = categoryCode; }
    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }
    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
    public long getVideoCount() { return videoCount; }
    public void setVideoCount(long videoCount) { this.videoCount = videoCount; }
}
