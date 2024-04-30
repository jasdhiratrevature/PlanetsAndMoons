package com.revature.service;

import java.util.List;

import com.revature.models.Planet;
import com.revature.repository.PlanetDao;

public class PlanetService {

	private PlanetDao dao;

	public PlanetService(PlanetDao dao){
		this.dao = dao;
	}

	public List<Planet> getAllPlanets() {
		// TODO Auto-generated method stub
		return dao.getAllPlanets();
	}

	public Planet getPlanetByName(int ownerId, String planetName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Planet getPlanetById(int ownerId, int planetId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Planet createPlanet(int ownerId, Planet planet) {
		// TODO Auto-generated method stub
		planet.setOwnerId(ownerId); // Set the ownerId to the planet
		return dao.createPlanet(planet);
	}

	public boolean deletePlanetById(int ownerId, int planetId) {
		// TODO Auto-generated method stub
		return false;
	}
}
