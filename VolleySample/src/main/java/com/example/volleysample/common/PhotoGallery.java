package com.example.volleysample.common;

import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

public class PhotoGallery {
	private String caption;

	@SerializedName("created_on")
	private String createdOn;

	private String description;

	private Integer id;
	@SerializedName("image_id")
	private Integer imageId;

	@SerializedName("is_approved")
	private Boolean isApproved;

	@SerializedName("link_url")
	private String linkUrl;

	private String name;

	@SerializedName("photo_gallery_id")
	private Integer photoGalleryId;

	private Integer position;

	private String tags;

	@SerializedName("updated_on")
	private String updatedOn;

	@SerializedName("user_id")
	private String userId;

	private int views;

	private Comment comment;

	private Thumbnails thumbnails;

	@SerializedName("self_location")
	private String selfLocation;

	@SerializedName("user_name")
	private String userName;

	public PhotoGallery(String caption, String createdOn, String description, Integer id,
			Integer imageId, Boolean isApproved, String linkUrl, String name,
			Integer photoGalleryId, Integer position, String tags, String updatedOn, String userId,
			int views, Comment comment, Thumbnails thumbnails, String selfLocation, String userName) {
		super();
		this.caption = caption;
		this.createdOn = createdOn;
		this.description = description;
		this.id = id;
		this.imageId = imageId;
		this.isApproved = isApproved;
		this.linkUrl = linkUrl;
		this.name = name;
		this.photoGalleryId = photoGalleryId;
		this.position = position;
		this.tags = tags;
		this.updatedOn = updatedOn;
		this.userId = userId;
		this.views = views;
		this.comment = comment;
		this.thumbnails = thumbnails;
		this.selfLocation = selfLocation;
		this.userName = userName;
	}
	
	public static Type getJsonArrayType() {
		return new TypeToken<Collection<PhotoGallery>>(){}.getType();
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getImageId() {
		return imageId;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

	public Boolean getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPhotoGalleryId() {
		return photoGalleryId;
	}

	public void setPhotoGalleryId(Integer photoGalleryId) {
		this.photoGalleryId = photoGalleryId;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public Thumbnails getThumbnails() {
		return thumbnails;
	}

	public void setThumbnails(Thumbnails thumbnails) {
		this.thumbnails = thumbnails;
	}

	public String getSelfLocation() {
		return selfLocation;
	}

	public void setSelfLocation(String selfLocation) {
		this.selfLocation = selfLocation;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
