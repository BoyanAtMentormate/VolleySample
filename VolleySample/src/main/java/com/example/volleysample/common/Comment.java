package com.example.volleysample.common;

import com.google.gson.annotations.SerializedName;

public class Comment {
	@SerializedName("approval_required")
	private Boolean approvalRequired;

	@SerializedName("ascending_sort_order")
	private Boolean ascendingSortOrder;

	@SerializedName("created_at")
	private String createdAt;

	private Boolean enabled;

	private Integer id;

	@SerializedName("login_required")
	private Boolean loginRequired;

	@SerializedName("object_id")
	private Integer objectId;

	@SerializedName("object_type")
	private String objectType;

	@SerializedName("site_id")
	private Integer siteId;

	private String title;

	@SerializedName("updated_at")
	private String updatedAt;

	public Comment(Boolean approvalRequired, Boolean ascendingSortOrder, String createdAt,
			Boolean enabled, Integer id, Boolean loginRequired, Integer objectId,
			String objectType, Integer siteId, String title, String updatedAt) {
		super();
		this.approvalRequired = approvalRequired;
		this.ascendingSortOrder = ascendingSortOrder;
		this.createdAt = createdAt;
		this.enabled = enabled;
		this.id = id;
		this.loginRequired = loginRequired;
		this.objectId = objectId;
		this.objectType = objectType;
		this.siteId = siteId;
		this.title = title;
		this.updatedAt = updatedAt;
	}

	public Boolean getApprovalRequired() {
		return approvalRequired;
	}

	public void setApprovalRequired(Boolean approvalRequired) {
		this.approvalRequired = approvalRequired;
	}

	public Boolean getAscendingSortOrder() {
		return ascendingSortOrder;
	}

	public void setAscendingSortOrder(Boolean ascendingSortOrder) {
		this.ascendingSortOrder = ascendingSortOrder;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getLoginRequired() {
		return loginRequired;
	}

	public void setLoginRequired(Boolean loginRequired) {
		this.loginRequired = loginRequired;
	}

	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

}
