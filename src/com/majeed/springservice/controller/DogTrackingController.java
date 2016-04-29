package com.majeed.springservice.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.majeed.dao.DogTrackingService;
import com.majeed.springservice.domain.Dog;
import com.majeed.springservice.domain.KMData;

@RestController
public class DogTrackingController {
	DogTrackingService dogtrackingservice = new DogTrackingService();

	// To get all dogs
	@RequestMapping(value = "/dogs", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Dog> getAllDogs() {
		List<Dog> dogs = dogtrackingservice.getAllDogs();
		return dogs;
	}
	//To get number of dogs
	@RequestMapping(value = "/dogsCount", method = RequestMethod.GET, headers = "Accept=application/json")
	public int getDogsCount() {
		int count = dogtrackingservice.getDogsCount();
		return count;
	}
	//To get a certain dog by id
	@RequestMapping(value = "/dogs/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public Dog getDogById(@PathVariable int id) {
		Dog dog = dogtrackingservice.getDogById(id);
		return dog;
	}
	//To get dog clusters based on k value
		@RequestMapping(value = "/dogClusters", method = RequestMethod.GET, headers = "Accept=application/json")
		public List<List<KMData>> getClusters(@RequestParam("k") int k) {
		List<List<KMData>> clusters = dogtrackingservice.getClusters(k);
		return clusters;
	}
	//To delte a dog with certain id	
	@RequestMapping(value = "/dogs/delete/{ids}", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	List<Dog> deleteAllDogs(@PathVariable int[] ids) {
		for (int i = 0; i < ids.length; i++) {
			dogtrackingservice.deleteDog(ids[i]);
		}
		List<Dog> dogs = dogtrackingservice.getAllDogs();
		return dogs;
	}
	//To create a new dog
	@RequestMapping(value = "/dogs/insert/{weight}/{name}/{heartBeat}/{temperature}/{latitude}/{longitude}", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	List<Dog> addDog(@PathVariable int weight, @PathVariable String name,
			@PathVariable int heartBeat, @PathVariable int temperature,
			@PathVariable double latitude, @PathVariable double longitude)
			throws ParseException {
		Dog dog = new Dog();
		dog.setWeight(weight);
		dog.setName(name);
		dog.setHeartBeat(heartBeat);
		dog.setTemperature(temperature);
		dog.setLatitude(latitude);
		dog.setLongitude(longitude);
		dogtrackingservice.addDog(dog);
		return dogtrackingservice.getAllDogs();
	}
}
