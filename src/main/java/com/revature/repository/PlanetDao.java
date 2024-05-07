package com.revature.repository;

import java.util.ArrayList;
import java.util.List;

import com.revature.models.Planet;
import com.revature.utilities.ConnectionUtil;

import java.sql.*;

public class PlanetDao {
    
    public List<Planet> getAllPlanets(int ownerId) {
		// TODO: implement
		List<Planet> planets = new ArrayList<>();
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "SELECT id, name, ownerId FROM planets WHERE ownerId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, ownerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int owner_Id = rs.getInt("ownerId");

                Planet planet = new Planet();
                planet.setId(id);
                planet.setName(name);
                planet.setOwnerId(owner_Id);

                planets.add(planet);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving planets: " + e.getMessage());
        }
		System.out.println(planets);
        return planets;
	}

	public Planet getPlanetByName(int ownerId, String planetName) {
		// TODO: implement
        try (Connection connection = ConnectionUtil.createConnection()) {
            System.out.println(planetName);
            String sql = "SELECT id, name, ownerId FROM planets WHERE name = ? AND ownerId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, planetName);
            ps.setInt(2, ownerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int owner_Id = rs.getInt("ownerId");

                Planet planet = new Planet();
                planet.setId(id);
                planet.setName(name);
                planet.setOwnerId(owner_Id);

                return planet;
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving planet by name: " + e.getMessage());
        }
        return null;			
	}

	public Planet getPlanetById(int ownerId,int planetId) {
		// TODO: implement
		try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "SELECT id, name, ownerId FROM planets WHERE id = ? AND ownerId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, planetId);
            ps.setInt(2, ownerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int owner_Id = rs.getInt("ownerId");

                Planet planet = new Planet();
                planet.setId(id);
                planet.setName(name);
                planet.setOwnerId(owner_Id);

                return planet;
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving planet by id: " + e.getMessage());
        }
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

	public boolean deletePlanetById(int ownerId,int planetId) {
		// TODO: implement
        try (Connection connection = ConnectionUtil.createConnection()) {
            // Construct SQL query to delete associated moons
            String deleteMoonsSql = "DELETE FROM moons WHERE myPlanetId = ?";
            PreparedStatement deleteMoonsPs = connection.prepareStatement(deleteMoonsSql);
            deleteMoonsPs.setInt(1, planetId);
            
            // Execute delete statement for associated moons
            int moonsRowsAffected = deleteMoonsPs.executeUpdate();
            
            // Construct SQL query to delete the planet
            String deletePlanetSql = "DELETE FROM planets WHERE id = ? AND ownerId = ?";
            PreparedStatement deletePlanetPs = connection.prepareStatement(deletePlanetSql);
            deletePlanetPs.setInt(1, planetId);
            deletePlanetPs.setInt(2, ownerId);
            
            // Execute delete statement for the planet
            int planetRowsAffected = deletePlanetPs.executeUpdate();
            
            // Check if both delete statements were successful
            return moonsRowsAffected > 0 && planetRowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting planet by id: " + e.getMessage());
            return false;
        }
	}
}
