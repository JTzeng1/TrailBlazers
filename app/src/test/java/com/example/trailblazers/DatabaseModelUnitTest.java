package com.example.trailblazers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.trailblazers.database.entities.Trail;
import com.example.trailblazers.database.entities.User;

import org.junit.Test;

public class DatabaseModelUnitTest {

    // Julian Mendoza Test 1:
    // Verifies that the User constructor correctly stores username, password,
    // and regular non-admin status.
    @Test
    public void teamMember1_userConstructor_createsRegularUser() {
        User user = new User("testuser1", "testuser1", false);

        assertEquals("testuser1", user.getUserName());
        assertEquals("testuser1", user.getPassword());
        assertFalse(user.isAdmin());
    }

    // Julian Mendoza Test 2:
    // Verifies that the User constructor correctly stores an admin account.
    @Test
    public void teamMember1_userConstructor_createsAdminUser() {
        User user = new User("admin2", "admin2", true);

        assertEquals("admin2", user.getUserName());
        assertEquals("admin2", user.getPassword());
        assertTrue(user.isAdmin());
    }

    // Justin Tzeng Test 1:
    // Verifies that User setters correctly update username and password.
    @Test
    public void teamMember2_userSetters_updateUsernameAndPassword() {
        User user = new User("oldName", "oldPassword", false);

        user.setUserName("newName");
        user.setPassword("newPassword");

        assertEquals("newName", user.getUserName());
        assertEquals("newPassword", user.getPassword());
    }

    // Justin Tzeng Test 2:
    // Verifies that User setters correctly update userID and admin status.
    @Test
    public void teamMember2_userSetters_updateUserIdAndAdminStatus() {
        User user = new User("runner", "password", false);

        user.setUserID(10);
        user.setAdmin(true);

        assertEquals(10, user.getUserID());
        assertTrue(user.isAdmin());
    }

    // Alexander Castaneda Test 1:
    // Verifies that Trail setters correctly store trail identity and ownership data.
    @Test
    public void teamMember3_trailSetters_updateTrailIdAndUserId() {
        Trail trail = new Trail();

        trail.setTrailID(5);
        trail.setUserId(2);
        trail.setTitle("Morning Run");

        assertEquals(5, trail.getTrailID());
        assertEquals(2, trail.getUserId());
        assertEquals("Morning Run", trail.getTitle());
    }

    // Alexander Castaneda Test 2:
    // Verifies that Trail setters correctly store run details including distance,
    // time, journal, and polyline.
    @Test
    public void teamMember3_trailSetters_updateTrailDetails() {
        Trail trail = new Trail();

        trail.setDistance(3.5);
        trail.setTime(1800);
        trail.setJournal("Good run today.");
        trail.setPolyline("abc123");

        assertEquals(3.5, trail.getDistance(), 0.001);
        assertEquals(1800, trail.getTime());
        assertEquals("Good run today.", trail.getJournal());
        assertEquals("abc123", trail.getPolyline());
    }

    // Jonathan Cortez-Bautista Test 1:
    // Verifies that a User object's admin status can be changed from admin to non-admin.
    @Test
    public void teamMember4_userSetAdmin_updatesAdminStatusToFalse() {
        User user = new User("adminUser", "password", true);

        user.setAdmin(false);

        assertFalse(user.isAdmin());
    }

    // Jonathan Cortez-Bautista Test 2:
    // Verifies that a Trail object's title and journal can be updated after creation.
    @Test
    public void teamMember4_trailSetters_updateTitleAndJournal() {
        Trail trail = new Trail();

        trail.setTitle("Old Trail");
        trail.setJournal("Old journal entry.");

        trail.setTitle("Updated Trail");
        trail.setJournal("Updated journal entry.");

        assertEquals("Updated Trail", trail.getTitle());
        assertEquals("Updated journal entry.", trail.getJournal());
    }
}
