package com.majeed.springservice.domain;

//This class is created to hold the k-means data which will be shown to the user in the web page
public class KMData {
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getClusterId() {
		return clusterId;
	}

	public void setClusterId(int clusterId) {
		this.clusterId = clusterId;
	}

	private int weight;
	private int clusterId;

	@Override
	public String toString() {
		return "KMData [id=" + id + "]";
	}
}
