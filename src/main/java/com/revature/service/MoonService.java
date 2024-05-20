package com.revature.service;

import java.util.List;
import java.util.regex.Pattern;

import com.google.common.base.CharMatcher;
import com.revature.exceptions.MoonFailException;
import com.revature.models.Moon;
import com.revature.repository.MoonDao;

public class MoonService {

    private MoonDao dao;

    public MoonService(MoonDao dao) {
        this.dao = dao;
    }

    public List<Moon> getAllMoons(int ownerId) {
        // TODO implement
        try {
            return dao.getAllMoons(ownerId);
        } catch (MoonFailException e) {
            throw new RuntimeException("Service layer: " + e.getMessage(), e);
        }
    }

    public Moon getMoonByName(int myPlanetId, String moonName) {
        // TODO implement
        return dao.getMoonByName(moonName.trim().toLowerCase());
    }

    public Moon getMoonById(int myPlanetId, int moonId) {
        // TODO Aimplement
        return dao.getMoonById(moonId);
    }

    public Moon createMoon(Moon m) {
        //  Trim leading and trailing spaces and make all moon name lowercase
        String lowercaseName = m.getName().trim().toLowerCase();

        // Check if moon name is empty or longer than 30 characters
        if (lowercaseName.isEmpty() || lowercaseName.length() > 30 || m.getMyPlanetId() <= 0) {
            return null;
        }

        // Check for non-ASCII characters in moon name
        if (!isAllAscii(lowercaseName)) {
            return null;
        }

        // Check for SQL injection patterns in the name
        if (containsSQLInjection(lowercaseName)) {
            return null;
        }

        // Check if a moon with the same name already exists
        Moon existingMoon = dao.getMoonByName(lowercaseName);
        if (existingMoon != null) {
            return null;
        }

        m.setName(lowercaseName);

        // If the moon name is valid and it doesn't already exist, proceed to create the moon
        return dao.createMoon(m);
    }


    public boolean deleteMoonById(int moonId) {
        //check if planetId or ownerId is missing
        if (moonId <= 0) {
            return false;
        }
        return dao.deleteMoonById(moonId);
    }

    public List<Moon> getMoonsFromPlanet(int myPlanetId) {
        // TODO Auto-generated method stub
        return dao.getMoonsFromPlanet(myPlanetId);
    }

    // Function to check if a string contains SQL injection patterns
    private boolean isAllAscii(String str) {
        // Validate input for ASCII characters and specific pattern
        CharMatcher asciiMatcher = CharMatcher.ascii();
        return asciiMatcher.matchesAllOf(str);
    }

    private boolean containsSQLInjection(String str) {
        Pattern sqlInjectionPattern = Pattern.compile("(?i)(SELECT|INSERT|UPDATE|DELETE|DROP|ALTER).*");
        return sqlInjectionPattern.matcher(str).find();
    }
}
