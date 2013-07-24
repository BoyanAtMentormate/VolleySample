package com.example.volleysample.common;

public class Thumbnails {
	private String large;
	private String medium;
	private String small;
	private String thumb;

	public Thumbnails(String large, String medium, String small, String thumb) {
		super();
		this.large = large;
		this.medium = medium;
		this.small = small;
		this.thumb = thumb;
	}

	public String getLarge() {
		return large;
	}

	public void setLarge(String large) {
		this.large = large;
	}

	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

	public String getSmall() {
		return small;
	}

	public void setSmall(String small) {
		this.small = small;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

}
