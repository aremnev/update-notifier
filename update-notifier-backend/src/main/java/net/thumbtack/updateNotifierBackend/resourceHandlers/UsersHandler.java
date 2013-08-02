package net.thumbtack.updateNotifierBackend.resourceHandlers;

import java.util.Set;

import javax.inject.Singleton;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import net.thumbtack.updateNotifierBackend.UpdateNotifierBackend;
import net.thumbtack.updateNotifierBackend.databaseService.Resource;

@Path("/users")
@Singleton
public class UsersHandler {

	@Path("signin")
	@GET
	public long signIn(@QueryParam("email") String userEmail) {
		Long userId = UpdateNotifierBackend.getDatabaseService()
				.getUserIdByEmail(userEmail);
		if (userId == null) {
			throw(new WebApplicationException("Database get account error"));
		}
		return userId;
	}

	@Path("/{id}/resourses")
	@GET
	@Produces({"application/json"})
	public String getUserResources(@PathParam("id") long userId,
			@DefaultValue("") @QueryParam("tags") String tagsString) {
		// TODO process errors
		long[] tags = parseTags(tagsString);
		Set<Resource> resources = UpdateNotifierBackend
				.getDatabaseService().getResourcesByIdAndTags(userId, tags);
		if(resources == null) {
			throw(new BadRequestException("Incorrect userId"));
		}
		return new Gson().toJson(resources);
	}

	@Path("/{id}/resourses")
	@DELETE
	public void deleteUserResources(@PathParam("id") long userId,
			@DefaultValue("") @QueryParam("tags") String tagsString) {
		// TODO process errors
		long[] tags = parseTags(tagsString);
		UpdateNotifierBackend.getDatabaseService()
		.deleteResourcesByIdAndTags(userId, tags);
	}

	@Path("/{id}/resourses")
	@POST
	@Consumes({"application/json"})
	public void addUserResource(@PathParam("id") long userId, String resourceJson) {
		// TODO process errors
		Resource res = parseResource(resourceJson);
		UpdateNotifierBackend.getResourcesChangesListener().onAddResource(res);
		UpdateNotifierBackend.getDatabaseService()
		.addResource(userId, res);
	}

	@Path("/{id}/resourses/{resourceId}")
	@PUT
	@Consumes({"application/json"})
	public void editUserResource(@PathParam("id") long userId, 
			@PathParam("resourceId") long resourceId, String resourceJson) {
		// TODO process errors
		Resource res = parseResource(resourceJson);
		Resource savedResource = UpdateNotifierBackend
				.getDatabaseService().getResource(userId, resourceId);
		if(savedResource == null) {
			throw(new BadRequestException("Resource not exist"));
		}
		
		if(savedResource.getUrl() != res.getUrl()) {
			UpdateNotifierBackend.getResourcesChangesListener().onEditResourceUrl(res);
		}
		
		UpdateNotifierBackend.getDatabaseService().editResource(userId, resourceId, res);
		
	}

	@Path("/{id}/resourses/{resourceId}")
	@GET
	@Produces({"application/json"})
	public String getUserResource(@PathParam("id") long userId, 
			@PathParam("resourceId") long resourceId) {
		// TODO process errors
		return new Gson().toJson(UpdateNotifierBackend.getDatabaseService()
		.getResource(userId, resourceId));
	}

	@Path("/{id}/tags")
	@GET
	@Produces({"application/json"})
	public String getUserTags(@PathParam("id") long userId) {
		// TODO process errors
		return new Gson().toJson(UpdateNotifierBackend.getDatabaseService()
		.getTags(userId));
	}

	private Resource parseResource(String resourceJson) {
		try{
			return new Gson().fromJson(resourceJson, Resource.class);
		} catch(JsonSyntaxException ex) {
			throw(new BadRequestException("Json parsing error"));
		}
	}
	
	private static long[] parseTags(String tagsString) {
		long[] tags;
		if("".equals(tagsString) || tagsString == null) {
			tags = null;
		} else {
			String[] tagsStrings = tagsString.split(",");
			tags = new long[tagsStrings.length];
			try{
				for(int i = 0; i < tagsStrings.length; i += 1) {
					tags[i] = Long.parseLong(tagsStrings[i]);
				}
			} catch(NumberFormatException ex) {
				throw(new BadRequestException("Tags id parsing error"));
			}
		}
		return tags;
	}
}
