/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.imju.panel.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.imju.panel.controllers.PublicacionControlador;
import org.imju.panel.models.Publicacion;
import org.imju.panel.models.PublicacionConsulta;
import org.imju.panel.models.PublicacionRequest;
import org.imju.panel.models.Publicaciones_Modulo;

/**
 *
 * @author IMJULEON
 */
@Path("/publicacion")
public class RESTPublicacion {
    
    @GET
    @Path("getAllPublicacion/{id_usuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllConvocatoria(@PathParam("id_usuario") int id_usuario) {
        String out = null;
        List<Publicaciones_Modulo> publicaciones = null;
        PublicacionControlador ctrls = new PublicacionControlador();
        try {
            publicaciones = ctrls.getAll(id_usuario);
            out = new Gson().toJson(publicaciones);
        } catch (Exception e) {
            out = "{\"error\": \"Error\", \"message\": \"" + e.getMessage() + "\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @GET
    @Path("show/{id_usuario}/{id_convocatoria}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response show(@PathParam("id_usuario") int id_usuario, 
                         @PathParam("id_convocatoria") int id_publicacion) {
        String out = null;
        List<PublicacionConsulta> publicacion = null;
        PublicacionControlador ctrls = new PublicacionControlador();

        try {
            publicacion = ctrls.show(id_usuario, id_publicacion);
            out = new Gson().toJson(publicacion);
        } catch (Exception e) {
            out = "{\"error\": \"Error\", \"message\": \"" + e.getMessage() + "\"}";
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }
    @POST
    @Path("insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(String datosRequest) {
        Gson gson = new Gson();
        PublicacionControlador ctrls = new PublicacionControlador();
        PublicacionRequest request = gson.fromJson(datosRequest, PublicacionRequest.class);
        Publicacion publicacion = new Publicacion();
        try {
            int idPublicacionGenerado = ctrls.insert(request);
            publicacion.setId_publicacion(idPublicacionGenerado);
            return Response.status(Response.Status.OK).entity(gson.toJson(request)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(e.getMessage())).build();
        }
    }
    
    @PUT
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(String datosRequest) {
        Gson gson = new Gson();
        PublicacionControlador ctrls = new PublicacionControlador();
        PublicacionRequest request = gson.fromJson(datosRequest, PublicacionRequest.class);
        try {
            ctrls.update(request);
            return Response.status(Response.Status.OK).entity(gson.toJson(request)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(e.getMessage())).build();
        }
    }
    
    @DELETE
    @Path("delete/{id_usuario}/{id_publicacion}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id_usuario") int id_usuario, 
                           @PathParam("id_publicacion") int id_publicacion) {
        PublicacionControlador ctrls = new PublicacionControlador();
        try {
            boolean resultado = ctrls.delete(id_usuario, id_publicacion);
            if (resultado) {
                return Response.status(Response.Status.OK).entity("{\"mensaje\": \"Publicación eliminada correctamente\"}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"mensaje\": \"No se pudo eliminar la galería\"}").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }
    
}
