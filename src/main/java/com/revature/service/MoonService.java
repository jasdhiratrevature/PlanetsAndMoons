package com.revature.service;

import java.util.ArrayList;
import java.util.List;

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
		return null;
	}

	public Moon getMoonById(int myPlanetId, int moonId) {
		// TODO Aimplement
		return null;
	}

	public Moon createMoon(Moon m) {
		// TODO implement
		return dao.createMoon(m);
	}

	public boolean deleteMoonById(int moonId) {
		return false;
	}

	public List<Moon> getMoonsFromPlanet(int myPlanetId) {
		// TODO Auto-generated method stub
		return null;
	}
}
