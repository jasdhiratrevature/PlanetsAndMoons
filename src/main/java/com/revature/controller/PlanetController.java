package com.revature.controller;

import com.revature.models.Planet;
import com.revature.models.User;
import com.revature.service.PlanetService;

import io.javalin.http.Context;

public class PlanetController {
	
	private PlanetService planetService;

	public PlanetController(PlanetService planetService){
		this.planetService = planetService;
	}

	public void getAllPlanets(Context ctx) {
		User u = ctx.sessionAttribute("user");
		ctx.json(planetService.getAllPlanets(u.getId())).status(200);
	}

	public void getPlanetByName(Context ctx) {
		// User u = ctx.sessionAttribute("user");
		// String planetName = ctx.pathParam("name");
		
		// Planet p = planetService.getPlanetByName(u.getId(), planetName);
		
		// ctx.json(p).status(200);
		User u = ctx.sessionAttribute("user");
		String planetName = ctx.pathParam("name");
		System.out.println("This name is " + planetName);
    
		// Check if the planetName is null or empty
		if (planetName == null || planetName.isEmpty()) {
			ctx.status(400).result("Planet name cannot be empty");
			return;
		}
		
		// Retrieve the planet from the database or wherever you store the data
		Planet planet = planetService.getPlanetByName(u.getId(), planetName);
		
		// Check if the planet exists
		if (planet == null) {
			ctx.status(404).result("Planet not found");
			return;
		}
		
		// Return the planet details
		ctx.json(planet).status(200);
	}

	public void getPlanetByID(Context ctx) {
		User u = ctx.sessionAttribute("user");
		int planetId = ctx.pathParamAsClass("id", Integer.class).get();
		
		Planet p = planetService.getPlanetById(u.getId(), planetId);
		
		ctx.json(p).status(200);
	}

	public void createPlanet(Context ctx) {
		Planet planetToBeCreated = ctx.bodyAsClass(Planet.class);
		User u = ctx.sessionAttribute("user");
		
		Planet createdPlanet = planetService.createPlanet(u.getId(),planetToBeCreated);
		
		ctx.json(createdPlanet).status(201);
	}

	public void deletePlanet(Context ctx) {
		// User u = ctx.sessionAttribute("user");
		// int planetId = ctx.pathParamAsClass("id", Integer.class).get();
		
		// planetService.deletePlanetById(u.getId(), planetId);
		// ctx.json("Planet successfully deleted").status(202);

		User u = ctx.sessionAttribute("user");
		int planetId = ctx.pathParamAsClass("id", Integer.class).get();
		
		boolean deleted = planetService.deletePlanetById(u.getId(), planetId);
		
		if (deleted) {
			ctx.json("Planet successfully deleted").status(202);
		} else {
			ctx.result("Failed to delete planet").status(500); 
		}
	}
}
