package com.revature.service;

import java.util.ArrayList;
import java.util.List;

import com.revature.exceptions.MoonFailException;
import com.revature.models.Moon;
import com.revature.models.Planet;
import com.revature.repository.MoonDao;
import com.revature.repository.PlanetDao;
import com.revature.service.PlanetService;

public class MoonService {

	private MoonDao dao;
	private PlanetDao planetDao;

	public MoonService(MoonDao dao) {
		this.dao = dao;
	}

	public MoonService(MoonDao dao, PlanetDao planetDao) {
		this.dao = dao;
		this.planetDao = planetDao;
	}

	public List<Moon> getAllMoons(int ownerId) {
		// TODO implement
		return dao.getAllMoons(ownerId);
	}

	public Moon getMoonByName(int myPlanetId, String moonName) {
		// TODO implement
		return dao.getMoonByName(moonName);
	}

	public Moon getMoonById(int myPlanetId, int moonId) {
		// TODO Aimplement
		return dao.getMoonById(moonId);
	}

	public Moon createMoon(Moon m) {
		 // Check if the moon name exceeds 30 characters
		 if (m.getName().length() > 30) {
			throw new MoonFailException("Moon name cannot exceed 30 characters");
		}

		// Check if a moon with the same name already exists
		Moon existingMoon = dao.getMoonByName(m.getName());
		if (existingMoon != null) {
			throw new MoonFailException("Moon with name '" + m.getName() + "' already exists");
		} 
		// If the moon name is valid and it doesn't already exist, proceed to create the moon
		return dao.createMoon(m);
		
	}

	public boolean deleteMoonById(int moonId) {
		return dao.deleteMoonById(moonId);
	}

	public List<Moon> getMoonsFromPlanet(int myPlanetId) {
		// TODO Auto-generated method stub
		return dao.getMoonsFromPlanet(myPlanetId);
	}
}
