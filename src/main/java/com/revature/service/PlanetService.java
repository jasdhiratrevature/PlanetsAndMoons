package com.revature.service;

import java.util.List;

import com.revature.exceptions.MoonFailException;
import com.revature.exceptions.PlanetFailException;
import com.revature.models.Planet;
import com.revature.repository.PlanetDao;

public class PlanetService {

	private PlanetDao dao;

	public PlanetService(PlanetDao dao){
		this.dao = dao;
	}

	public List<Planet> getAllPlanets(int ownerId) {
		// TODO Auto-generated method stub
		return dao.getAllPlanets(ownerId);
	}

	public Planet getPlanetByName(int ownerId, String planetName) {
		// TODO Auto-generated method stub
		return dao.getPlanetByName(ownerId, planetName);
	}

	public Planet getPlanetById(int ownerId, int planetId) {
		// TODO Auto-generated method stub
		return dao.getPlanetById(ownerId, planetId);
	}

	public Planet createPlanet(int ownerId, Planet planet) {
		// TODO Auto-generated method stub

		// Check if the moon name exceeds 30 characters
		 if (planet.getName().length() > 30) {
			throw new PlanetFailException("Planet name cannot exceed 30 characters");
		}

		// Check if a planet with the same name already exists for the given ownerId
		Planet existingPlanet = dao.getPlanetByName(ownerId, planet.getName());
		if (existingPlanet != null) {
			throw new PlanetFailException("Planet with name '" + planet.getName() + "' already exists for Owner ID " + ownerId);
		} 

		planet.setOwnerId(ownerId); // Set the ownerId to the planet
		return dao.createPlanet(planet);
	}

	public boolean deletePlanetById(int ownerId, int planetId) {
		// TODO Auto-generated method stub
		return dao.deletePlanetById(ownerId,planetId);
	}
}
