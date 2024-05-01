package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.exceptions.MoonFailException;
import com.revature.models.Moon;
import com.revature.models.Planet;
import com.revature.utilities.ConnectionUtil;

public class MoonDao {
    
    public List<Moon> getAllMoons(int ownerId) {
		// TODO: implement
		List<Moon> moons = new ArrayList<>();
    try (Connection connection = ConnectionUtil.createConnection()) {
        String sql = "SELECT m.* FROM moons m " +
                     "JOIN planets p ON m.myPlanetId = p.id " +
                     "WHERE p.ownerId = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, ownerId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Moon moon = new Moon();
            moon.setId(rs.getInt("id"));
            moon.setName(rs.getString("name"));
            moon.setMyPlanetId(rs.getInt("myPlanetId"));
            moons.add(moon);
        }
    } catch (SQLException e) {
        System.err.println("Error retrieving moons: " + e.getMessage());
    }
    return moons;
	}



	public Moon getMoonByName(String moonName) {
		// TODO: implement
		try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "SELECT * FROM moons WHERE name = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, moonName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Moon moon = new Moon();
                moon.setId(rs.getInt("id"));
                moon.setName(rs.getString("name"));
                moon.setMyPlanetId(rs.getInt("myPlanetId"));
                return moon;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving moon by name: " + e.getMessage());
        }
        return null;
	}

	public Moon getMoonById(int moonId) {
		// TODO: implement
		try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "SELECT * FROM moons WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, moonId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Moon moon = new Moon();
                moon.setId(rs.getInt("id"));
                moon.setName(rs.getString("name"));
                moon.setMyPlanetId(rs.getInt("myPlanetId"));
                return moon;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving moon by ID: " + e.getMessage());
        }
        return null;
	}

	public Moon createMoon(Moon m) {
		// TODO: implement
		try (Connection connection = ConnectionUtil.createConnection()) {
			String sql = "INSERT INTO moons (name, myPlanetId) VALUES (?, ?)";
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, m.getName());
			ps.setInt(2, m.getMyPlanetId());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int generatedId = rs.getInt(1); // Retrieve the auto-generated id
				m.setId(generatedId); // Set the id to the moon object
				return m;
			}
			return null; // Failed to create moon
		} catch (SQLException e) {
			System.out.println("Error creating moon: " + e.getMessage());
			return null;
		}
	}

	public boolean deleteMoonById(int moonId) {
		// TODO: implement
		try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "DELETE FROM moons WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, moonId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting moon by ID: " + e.getMessage());
            return false;
        }
	}

	public List<Moon> getMoonsFromPlanet(int planetId) {
		// TODO: implement
		List<Moon> moons = new ArrayList<>();
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "SELECT * FROM moons WHERE myPlanetId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, planetId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Moon moon = new Moon();
                moon.setId(rs.getInt("id"));
                moon.setName(rs.getString("name"));
                moon.setMyPlanetId(rs.getInt("myPlanetId"));
                moons.add(moon);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving moons from planet: " + e.getMessage());
        }
        return moons;
	}
}
