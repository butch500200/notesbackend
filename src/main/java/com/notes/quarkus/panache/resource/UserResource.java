package com.notes.quarkus.panache.resource;

import com.notes.quarkus.panache.models.User;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.common.constraint.NotNull;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.cache.NoCache;


import java.net.URI;
import java.util.List;

@Path("/api/users")
public class UserResource {

	@Inject
	SecurityIdentity securityIdentity;
	@Inject
	Logger log;

	@POST
	@Path("/me")
	@NoCache
	@Produces(MediaType.APPLICATION_JSON)
	public User login(@Context SecurityContext securityContext){
		log.info("inside user login");
		String username = securityContext.getUserPrincipal().getName();
		log.info(username + " logged in");
		User user = new User();
		user.userName=username;
		return user;

	}

	@POST
	@Transactional
	@APIResponse(
			responseCode = "201",
			description = "User Created",
			content = @Content(
					mediaType = MediaType.APPLICATION_JSON,
					schema = @Schema(type = SchemaType.OBJECT, implementation = User.class)
			)
	)
	public Response addNewUser (@NotNull @Valid User user, @Context UriInfo uriInfo){
		User addedUser =User.add(user.userName, user.password, user.role);
		System.out.println(addedUser);
		URI uri = uriInfo.getAbsolutePathBuilder().path(Long.toString(addedUser.id)).build();

		return Response.created(uri).entity(addedUser).build();
	}



}
