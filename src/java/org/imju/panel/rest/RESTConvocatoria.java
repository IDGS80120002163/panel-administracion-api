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
import org.imju.panel.controllers.ConvocatoriaControlador;
import org.imju.panel.models.Convocatoria;
import org.imju.panel.models.ConvocatoriaCompleta;
import org.imju.panel.models.Convocatoria_Modulo;
import org.imju.panel.models.ConvocatoriaRequest;

/**
 *
 * @author IMJULEON
 */
@Path("/convocatoria")
public class RESTConvocatoria {

    @GET
    @Path("getAllConvocatoria/{id_usuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllConvocatoria(@PathParam("id_usuario") int id_usuario) {
        String out = null;
        List<Convocatoria_Modulo> convocatoria = null;
        ConvocatoriaControlador ctrls = new ConvocatoriaControlador();
        try {
            convocatoria = ctrls.getAll(id_usuario);
            out = new Gson().toJson(convocatoria);
        } catch (Exception e) {
            out = "{\"error\": \"Error\", \"message\": \"" + e.getMessage() + "\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @GET
    @Path("show/{id_usuario}/{id_convocatoria}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response show(@PathParam("id_usuario") int id_usuario, 
                         @PathParam("id_convocatoria") int id_convocatoria) {
        String out = null;
        List<ConvocatoriaCompleta> convocatoria = null;
        ConvocatoriaControlador ctrls = new ConvocatoriaControlador();

        try {
            convocatoria = ctrls.show(id_usuario, id_convocatoria);
            out = new Gson().toJson(convocatoria);
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
        ConvocatoriaControlador ctrls = new ConvocatoriaControlador();
        ConvocatoriaRequest request = gson.fromJson(datosRequest, ConvocatoriaRequest.class);
        Convocatoria convocatoria = new Convocatoria();
        try {
            int idConvocatoriaGenerado = ctrls.insert(request.getId_usuario(), 
                                                      request.getConvocatoria_Modulo(), 
                                                      request.getDetalle_Galeria());
            convocatoria.setId_convocatoria(idConvocatoriaGenerado);
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
        ConvocatoriaControlador ctrls = new ConvocatoriaControlador();
        ConvocatoriaRequest request = gson.fromJson(datosRequest, ConvocatoriaRequest.class);
        //Convocatoria convocatoria = new Convocatoria();
        try {
            ctrls.update(request.getId_usuario(), 
                         request.getConvocatoria_Modulo(), 
                         request.getDetalle_Galeria());
            return Response.status(Response.Status.OK).entity(gson.toJson(request)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(e.getMessage())).build();
        }
    }
    
    @DELETE
    @Path("delete/{id_usuario}/{id_convocatoria}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id_usuario") int id_usuario, 
                           @PathParam("id_convocatoria") int id_convocatoria) {
        ConvocatoriaControlador ctrls = new ConvocatoriaControlador();
        try {
            boolean resultado = ctrls.delete(id_usuario, id_convocatoria);
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
