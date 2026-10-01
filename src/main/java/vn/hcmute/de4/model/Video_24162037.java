package vn.hcmute.de4.model;

public class Video_24162037 {
    private Long videoId;
    private String videoCode;
    private String title;
    private String poster;
    private int views;
    private String description;
    private boolean active = true;
    private Long categoryId;
    private String categoryName;
    private long shareCount;
    private long likeCount;
    private boolean liked;

    public Video_24162037() {}
    public Video_24162037(Long videoId, String videoCode, String title, String poster, int views,
                          String description, boolean active, Long categoryId, String categoryName) {
        this.videoId = videoId; this.videoCode = videoCode; this.title = title; this.poster = poster;
        this.views = views; this.description = description; this.active = active;
        this.categoryId = categoryId; this.categoryName = categoryName;
    }
    public Long getVideoId() { return videoId; }
    public void setVideoId(Long videoId) { this.videoId = videoId; }
    public String getVideoCode() { return videoCode; }
    public void setVideoCode(String videoCode) { this.videoCode = videoCode; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getPoster() { return poster; }
    public void setPoster(String poster) { this.poster = poster; }
    public int getViews() { return views; }
    public void setViews(int views) { this.views = views; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public long getShareCount() { return shareCount; }
    public void setShareCount(long shareCount) { this.shareCount = shareCount; }
    public long getLikeCount() { return likeCount; }
    public void setLikeCount(long likeCount) { this.likeCount = likeCount; }
    public boolean isLiked() { return liked; }
    public void setLiked(boolean liked) { this.liked = liked; }
}
