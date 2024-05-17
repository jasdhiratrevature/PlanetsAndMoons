package com.revature.utilities;

import com.revature.controller.MoonController;
import com.revature.controller.PlanetController;
import com.revature.controller.UserController;
import com.revature.exceptions.NotLoggedInException;
import com.revature.repository.MoonDao;
import com.revature.repository.PlanetDao;
import com.revature.repository.UserDao;
import com.revature.service.MoonService;
import com.revature.service.PlanetService;
import com.revature.service.UserService;
//import com.revature.service.mocks.MoonServiceMock;
//import com.revature.service.mocks.PlanetServiceMock;
//import com.revature.service.mocks.UserServiceMock;

import io.javalin.Javalin;

public class RequestMapper {
    private static final UserDao UserDao = new UserDao();
    //private static final UserService UserService = new UserServiceMock(UserDao);
    private static final UserService UserService = new UserService(UserDao);
    private static final UserController UserController = new UserController(UserService);
    private static final PlanetDao PlanetDao = new PlanetDao();
    private static final PlanetService PlanetService = new PlanetService(PlanetDao);
    private static final PlanetController planetController = new PlanetController(PlanetService);
    private static final MoonDao MoonDao = new MoonDao();
    private static final MoonService moonService = new MoonService(MoonDao);
    private static final MoonController moonController = new MoonController(moonService);

    public static void setUpEndPoints(Javalin app) {
        // Authenticate user and create a session for the user, sending username/password in the body as JSON
        app.post("/login", ctx -> UserController.authenticate(ctx));

        // Register a new user, sending username/password in the body as JSON
        app.post("/register", ctx -> UserController.register(ctx));

        // Invalidate session
        app.delete("/logout", ctx -> UserController.logout(ctx));

        // login and create are available without needing to be logged in
        app.get("/webpage/login", ctx -> ctx.html(PageProvider.getPage("login")));

        app.get("/webpage/create", ctx -> ctx.html(PageProvider.getPage("create")));

        // Check for valid sessions
        // Throw a custom exception if a session is not valid
        // This exception will be handled separately
        app.before("/api/*", ctx -> {
            if (!UserController.checkAuthorization(ctx)) {
                throw new NotLoggedInException();
            }
        });

        // Handling the exception when a session doesn't exist
        app.exception(NotLoggedInException.class, (e, ctx) -> {
            ctx.html(e.getMessage()).status(401);
        });

        // requires a valid session to be accessed
        app.get("/api/webpage/home", ctx -> ctx.html(PageProvider.getPage("home")));


        // Get all Planets
        app.get("api/planet", ctx -> planetController.getAllPlanets(ctx));

        // Get a planet with matching name
        app.get("api/planet/name/{name}", ctx -> planetController.getPlanetByName(ctx));

        // Get a planet with matching ID
        app.get("api/planet/id/{id}", ctx -> planetController.getPlanetByID(ctx));

        // Get moons associated with a planet
        app.get("api/planet/{id}/moon", ctx -> moonController.getPlanetMoons(ctx));

        // Get all moons
        app.get("api/moon", ctx -> moonController.getAllMoons(ctx));

        // Get a moon with matching name
        app.get("api/moon/name/{name}", ctx -> moonController.getMoonByName(ctx));

        // Get a moon with matching I
        app.get("api/moon/id/{id}", ctx -> moonController.getMoonById(ctx));


        // Create a new planet, sending the data in the body as JSON
        app.post("api/planet", ctx -> planetController.createPlanet(ctx));

        // Create a new moon, sending the data in the body as JSON
        app.post("api/moon", ctx -> moonController.createMoon(ctx));


        // Delete a planet and all of its moons
        app.delete("api/planet/{id}", ctx -> planetController.deletePlanet(ctx));

        // Delete a moon
        app.delete("api/moon/{id}", ctx -> moonController.deleteMoon(ctx));
    }

}
