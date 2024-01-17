package com.notes.quarkus.panache.resource;

import com.notes.quarkus.panache.models.Note;
import com.notes.quarkus.panache.models.User;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@Path("/api/notes")
@Produces(MediaType.APPLICATION_JSON)
public class NotesResource {

	@Inject
	Logger log;

	@GET
	public List<Note> getAllNotes(){
		return Note.listAll();
	}

	@GET
	@Path("{id}")
	public Optional<PanacheEntityBase> getNoteById(@PathParam("id")Long id){
		return Note.findByIdOptional(id);

	}

	@DELETE
	@Path("{id}")
	public Response deleteNote(@PathParam("id")Long id){
		 Note.deleteById(id);
		 return Response.status(Response.Status.NO_CONTENT).entity("note deleted").build();


	}

	@POST
	@Transactional
	public Response createNote(Note note){
		System.out.println("inside create");

		User foundUser = User.find("userName", note.user.userName).firstResult();
		if(foundUser== null){
			return Response.status(Response.Status.BAD_REQUEST).entity("invalid user").build();
		}
		note.user=foundUser;
		note.persist();
		return Response.status(Response.Status.CREATED).entity(note).build();
	}

	@PUT
	@Path("{id}")
	@Transactional
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateNote (Note note,@PathParam("id")Long id){
		System.out.println("inside put");
		Note oldNote = Note.findById(id);
		if(oldNote == null){
			return this.createNote(note);
		}
		oldNote.note = note.note;
		oldNote.important = note.important;
		return Response.status(Response.Status.OK).entity(oldNote).build();

	}




}
