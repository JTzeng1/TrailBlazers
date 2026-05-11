package com.example.trailblazers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.trailblazers.database.entities.Trail;
import com.example.trailblazers.database.entities.User;

import org.junit.Test;

public class DatabaseModelUnitTest {

    // Verifies that the User constructor correctly stores username, password,
    // and regular non-admin status.
    @Test
    public void teamMember1_userConstructor_createsRegularUser() {
        User user = new User("testuser1", "testuser1", false);

        assertEquals("testuser1", user.getUserName());
        assertEquals("testuser1", user.getPassword());
        assertFalse(user.isAdmin());
    }

    // Verifies that the User constructor correctly stores an admin account.
    @Test
    public void teamMember1_userConstructor_createsAdminUser() {
        User user = new User("admin2", "admin2", true);

        assertEquals("admin2", user.getUserName());
        assertEquals("admin2", user.getPassword());
        assertTrue(user.isAdmin());
    }

}