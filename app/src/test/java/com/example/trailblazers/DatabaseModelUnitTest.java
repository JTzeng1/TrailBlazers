package com.example.trailblazers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.trailblazers.database.entities.Trail;
import com.example.trailblazers.database.entities.User;

import org.junit.Test;

public class DatabaseModelUnitTest {

    // Julian Test 1:
    // Verifies that the User constructor correctly stores username, password,
    // and regular non-admin status.
    @Test
    public void teamMember1_userConstructor_createsRegularUser() {
        User user = new User("testuser1", "testuser1", false);

        assertEquals("testuser1", user.getUserName());
        assertEquals("testuser1", user.getPassword());
        assertFalse(user.isAdmin());
    }

    // Julian Test 2:
    // Verifies that the User constructor correctly stores an admin account.
    @Test
    public void teamMember1_userConstructor_createsAdminUser() {
        User user = new User("admin2", "admin2", true);

        assertEquals("admin2", user.getUserName());
        assertEquals("admin2", user.getPassword());
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
}
