package com.notes.quarkus.panache.startup;

import com.notes.quarkus.panache.models.Note;
import com.notes.quarkus.panache.models.User;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

@Singleton
public class Startup {
	@Transactional
	public void loadUsers(@Observes StartupEvent evt){
		Note.deleteAll();
		User.deleteAll();
		User.add("admin", "admin", "admin");
		User.add("user", "user", "user");
	}
}
