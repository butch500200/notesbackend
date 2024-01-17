package com.notes.quarkus.panache;

import com.notes.quarkus.panache.models.User;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class UserResourceTest {



	@Test
	@Transactional
	void shouldCreateUser(){
		User testUser = new User();
		testUser.role="test Role";
		testUser.userName ="test Username";
		testUser.password="test password";
		testUser = User.add(testUser);
		assertNotNull(testUser.id);

		testUser = User.findById(testUser.id);
		assertEquals("test Username",testUser.userName);
		assertEquals("test Role",testUser.role);
	}
}
