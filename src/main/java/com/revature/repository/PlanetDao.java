package com.revature.repository;

import java.util.ArrayList;
import java.util.List;

import com.revature.models.Planet;
import com.revature.utilities.ConnectionUtil;

import java.sql.*;

public class PlanetDao {
    
    public List<Planet> getAllPlanets() {
		// TODO: implement
		List<Planet> planets = new ArrayList<>();

        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "SELECT id, name, ownerId FROM planets";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int ownerId = rs.getInt("ownerId");

                Planet planet = new Planet();
                planet.setId(id);
                planet.setName(name);
                planet.setOwnerId(ownerId);

                planets.add(planet);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving planets: " + e.getMessage());
        }
		System.out.println(planets);
        return planets;
	}

	public Planet getPlanetByName(String planetName) {
		// TODO: implement
		return null;			
	}

	public Planet getPlanetById(int planetId) {
		// TODO: implement
		return null;
	}

	public Planet createPlanet(Planet p) {
		// TODO: implement
		 try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "INSERT INTO planets (name, ownerId) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, p.getName());
            ps.setInt(2, p.getOwnerId());
            ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int generatedId = rs.getInt(1); // Retrieve the auto-generated id
				p.setId(generatedId); // Set the id to the planet object
				return p;
			}
            return null; // Failed to create planet
        } catch (SQLException e) {
            System.out.println("Error creating planet: " + e.getMessage());
            return null;
        }
	}

	public boolean deletePlanetById(int planetId) {
		// TODO: implement
		return false;
	}
}
