package com.notes.quarkus.panache.models;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.Username;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import io.quarkus.security.jpa.UserDefinition;

@Entity
@Table(name = "users")
@UserDefinition
public class User extends PanacheEntity {

	@Username
	public String userName;

	@JsonbTransient
	@Password
	public String password;
	@Roles
	public String role;



	@Override
	public String toString() {
		return "User{" +
				"username='" + userName + '\'' +
				", password='" + password + '\'' +
				", role='" + role + '\'' +
				'}';
	}

	public User() {
	}

	public static User add(String username, String password, String role){
		User user = new User();
		user.userName = username;
		user.password = BcryptUtil.bcryptHash(password);
		user.role = role;
		user.persist();
		return user;
	}
	public static User add(User user){
		user.password = BcryptUtil.bcryptHash(user.password);
		user.persist();
		return user;


	}

	public User(SecurityIdentity securityIdentity) {
		this.userName = securityIdentity.getPrincipal().getName();
	}

}
