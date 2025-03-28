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
import org.imju.panel.controllers.DireccionesControlador;
import org.imju.panel.models.Direccion;
import org.imju.panel.models.Direcciones_Modulo;
import org.imju.panel.models.DireccionCompleta;
import org.imju.panel.models.DireccionRequest;

/**
 *
 * @author IMJULEON
 */
@Path("/direccion")
public class RESTDireccion {
    
    @GET
    @Path("getAllDireccion/{id_usuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDireccion(@PathParam("id_usuario") int id_usuario) {
        String out = null;
        List<Direcciones_Modulo> publicaciones = null;
        DireccionesControlador ctrls = new DireccionesControlador();
        try {
            publicaciones = ctrls.getAll(id_usuario);
            out = new Gson().toJson(publicaciones);
        } catch (Exception e) {
            out = "{\"error\": \"Error\", \"message\": \"" + e.getMessage() + "\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @GET
    @Path("show/{id_usuario}/{id_direccion}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response show(@PathParam("id_usuario") int id_usuario, 
                         @PathParam("id_direccion") int id_direccion) {
        String out = null;
        DireccionesControlador ctrls = new DireccionesControlador();
        try {
            DireccionCompleta direccionCompleta = ctrls.show(id_usuario, id_direccion);
            out = new Gson().toJson(direccionCompleta);
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
        DireccionesControlador ctrls = new DireccionesControlador();
        DireccionRequest request = gson.fromJson(datosRequest, DireccionRequest.class);
        Direccion direccion = new Direccion();
        try {
            int idDireccionGenarada = ctrls.insert(request);
            direccion.setId_direccion(idDireccionGenarada);
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
        DireccionesControlador ctrls = new DireccionesControlador();
        DireccionRequest request = gson.fromJson(datosRequest, DireccionRequest.class);
        try {
            ctrls.update(request);
            return Response.status(Response.Status.OK).entity(gson.toJson(request)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(e.getMessage())).build();
        }
    }
    
    @DELETE
    @Path("delete/{id_usuario}/{id_direccion}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id_usuario") int id_usuario, 
                           @PathParam("id_direccion") int id_direccion) {
        DireccionesControlador ctrls = new DireccionesControlador();
        try {
            boolean resultado = ctrls.delete(id_usuario, id_direccion);
            if (resultado) {
                return Response.status(Response.Status.OK).entity("{\"mensaje\": \"Dirección eliminada correctamente\"}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"mensaje\": \"No se pudo eliminar la dirección\"}").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }
    
}
