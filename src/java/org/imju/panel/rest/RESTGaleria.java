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
import org.imju.panel.controllers.GaleriaControlador;
import org.imju.panel.models.Galeria;
import org.imju.panel.models.GaleriaImagenes;
import org.imju.panel.models.GaleriaModulo;
import org.imju.panel.models.GaleriaRequest;

/**
 *
 * @author IMJULEON
 */
@Path("/galeria")
public class RESTGaleria {
    
    @GET
    @Path("getAllGaleria/{id_usuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRol(@PathParam("id_usuario") int id_usuario) {
        String out = null;
        List<GaleriaModulo> galeria = null;
        GaleriaControlador ctrls = new GaleriaControlador();
        try {
            galeria = ctrls.getAll(id_usuario);
            out = new Gson().toJson(galeria);
        } catch (Exception e) {
            out = """
                  {"error" : "Error"}
                  """ + e;
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @GET
    @Path("show/{id_usuario}/{id_galeria}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response show(@PathParam("id_usuario") int id_usuario,
                         @PathParam("id_galeria") int id_galeria) {
        String out = null;
        List<GaleriaImagenes> galeria = null;
        GaleriaControlador ctrls = new GaleriaControlador();
        try {
            galeria = ctrls.show(id_usuario, id_galeria);
            out = new Gson().toJson(galeria);
        } catch (Exception e) {
            out = """
                  {"error" : "Error"}
                  """ + e;
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @POST
    @Path("insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(String datosRequest) {
        Gson gson = new Gson();
        GaleriaControlador ctrls = new GaleriaControlador();
        GaleriaRequest request = gson.fromJson(datosRequest, GaleriaRequest.class);
        Galeria galeria = new Galeria();
        try {
            int idGaleriaGenerado = ctrls.insert(request.getIdUsuario(), request.getDetalle_Galeria());
            galeria.setId_galeria(idGaleriaGenerado);
            return Response.status(Response.Status.OK).entity(gson.toJson(request)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(e.getMessage())).build();
        }
    }
    
    @PUT
    @Path("update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(String datosRequest) {
        Gson gson = new Gson();
        GaleriaControlador ctrls = new GaleriaControlador();
        GaleriaRequest request = gson.fromJson(datosRequest, GaleriaRequest.class);
        try {
            ctrls.update(request.getGaleria(), request.getIdUsuario(), request.getDetalle_Galeria());
            return Response.status(Response.Status.OK).entity(gson.toJson(request)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(e.getMessage())).build();
        }
    }
    
    @DELETE
    @Path("delete/{id_usuario}/{id_galeria}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id_usuario") int id_usuario, 
                           @PathParam("id_galeria") int id_galeria) {
        GaleriaControlador ctrls = new GaleriaControlador();
        try {
            boolean resultado = ctrls.delete(id_usuario, id_galeria);
            if (resultado) {
                return Response.status(Response.Status.OK).entity("{\"mensaje\": \"Galería eliminada correctamente\"}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"mensaje\": \"No se pudo eliminar la galería\"}").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }
    
}
