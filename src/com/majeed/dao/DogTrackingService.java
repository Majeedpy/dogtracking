package com.majeed.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import com.majeed.springservice.domain.Dog;
import com.majeed.springservice.domain.KMData;
import com.majeed.springservice.utility.DBUtility;

public class DogTrackingService {
	private Connection connection;

	public DogTrackingService() {
		connection = DBUtility.getConnection();
	}
	
	//To add a new dog
	public void addDog(Dog dog) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("insert into dog_list(weight,name,heartBeat,temperature,latitude,longitude) values (?, ?, ?,?,?,?)");
			System.out.println("Dog:" + dog.getName());
			preparedStatement.setInt(1, dog.getWeight());
			preparedStatement.setString(2, dog.getName());
			preparedStatement.setInt(3, dog.getHeartBeat());
			preparedStatement.setInt(4, dog.getTemperature());
			preparedStatement.setDouble(5, dog.getLatitude());
			preparedStatement.setDouble(6, dog.getLongitude());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//To delete a dog
	public void deleteDog(int id) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("delete from dog_list where id=?");
			// Parameters start with 1
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//To update dog record//This  method was not used in this work
	public void updateDog(Dog dog) throws ParseException {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("update dog_list set weight=?, set name=?, heartBeat=?, temperature=?, latitude=?, longitude=?"
							+ "where id=?");
			preparedStatement.setInt(1, dog.getWeight());
			preparedStatement.setString(2, dog.getName());
			preparedStatement.setInt(3, dog.getHeartBeat());
			preparedStatement.setInt(4, dog.getTemperature());
			preparedStatement.setDouble(5, dog.getLatitude());
			preparedStatement.setDouble(6, dog.getLongitude());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//To get all dogs
	public List<Dog> getAllDogs() {
		List<Dog> dogs = new ArrayList<Dog>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from dog_list");
			while (rs.next()) {
				Dog dog = new Dog();
				dog.setId(rs.getInt("id"));
				dog.setWeight(rs.getInt("weight"));
				dog.setName(rs.getString("name"));
				dog.setHeartBeat(rs.getInt("heartBeat"));
				dog.setTemperature(rs.getInt("temperature"));
				dog.setLatitude(rs.getDouble("latitude"));
				dog.setLongitude(rs.getDouble("longitude"));
				dogs.add(dog);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dogs;
	}
	//To get number of dogs
	public int getDogsCount() {
		int count = 0;
		count = getAllDogs().size();
		return count;
	}
	//To get clusters
	public List<List<KMData>> getClusters(int k) {

		int rowCount = getAllDogs().size();
		int[][] km_data = new int[rowCount][3];
		int[][] km_cluster = new int[k][2];

		for (int i = 0; i < rowCount; i++) {

			km_data[i][0] = getAllDogs().get(i).getId();
			km_data[i][1] = getAllDogs().get(i).getWeight();
			// km_data[i][2]=0;
		}
		for (int i = 0; i < k; i++) {
			km_cluster[i][0] = i;
			km_cluster[i][1] = km_data[i][1];
		}

		int[] weightMin = new int[k];

		for (int m = 0; m < 30; m++) {

			int stopCheck = 0;
			for (int j = 0; j < rowCount; j++) {
				for (int i = 0; i < k; i++) {
					weightMin[i] = Math.abs(km_data[j][1] - km_cluster[i][1]);
				}
				km_data[j][2] = getMinIndex(weightMin);
			}
			int delta = 0;
			int deltaCounter = 0;

			for (int j = 0; j < k; j++) {
				int sum = 0;
				int counter = 0;

				for (int i = 0; i < rowCount; i++) {

					if (km_data[i][2] == km_cluster[j][0]) {

						sum += km_data[i][1];
						counter++;
					}
				}
				int avg_weight = Math.round(sum / counter);
				delta += Math.abs(km_cluster[j][1] - avg_weight);
				km_cluster[j][1] = avg_weight;
				deltaCounter++;
			}
			stopCheck = delta / deltaCounter;
			if (stopCheck == 0)
				break;
		}
		//a list of lists is return to keep KMData objects
		List<List<KMData>> kmResponse = new ArrayList<List<KMData>>();
		for (int i = 0; i < k; i++) {

			List<KMData> kmList = new ArrayList<KMData>();
			for (int j = 0; j < rowCount; j++) {
				if (km_data[j][2] == i) {
					KMData km = new KMData();
					km.setId(km_data[j][0]);
					km.setWeight(km_data[j][1]);
					km.setClusterId(km_data[j][2] + 1);
					kmList.add(km);
				}
			}
			kmResponse.add(kmList);
		}

		return kmResponse;
	}
	//To get a dog by Id
	public Dog getDogById(int id) {
		Dog dog = new Dog();
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("select * from dog_list where id=?");
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				dog.setId(rs.getInt("id"));
				dog.setWeight(rs.getInt("weight"));
				dog.setName(rs.getString("name"));
				dog.setHeartBeat(rs.getInt("heartBeat"));
				dog.setTemperature(rs.getInt("temperature"));
				dog.setLatitude(rs.getDouble("latitude"));
				dog.setLongitude(rs.getDouble("longitude"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dog;
	}

	public static int getMinIndex(int[] array) {
		int minValue = array[0];
		int minIndex = 0;
		for (int i = 1; i < array.length; i++) {
			if (array[i] < minValue) {
				minValue = array[i];
				minIndex = i;
			}
		}
		return minIndex;
	}



}
