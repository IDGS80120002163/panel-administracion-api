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
import org.imju.panel.controllers.CuentaPublicaControlador;
import org.imju.panel.models.Cuenta_Publica_Completa;
import org.imju.panel.models.Cuenta_Publica_Modulo;
import org.imju.panel.models.Cuenta_Publica_Request;

/**
 *
 * @author IMJULEON
 */
@Path("/cuenta-publica")
public class RESTCuentaPublica {
    
    @GET
    @Path("getAllCuentaPublica/{id_usuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCuentaPublica(@PathParam("id_usuario") int id_usuario) {
        String out = null;
        List<Cuenta_Publica_Modulo> cuentas_publicas = null;
        CuentaPublicaControlador ctrls = new CuentaPublicaControlador();
        try {
            cuentas_publicas = ctrls.getAll(id_usuario);
            out = new Gson().toJson(cuentas_publicas);
        } catch (Exception e) {
            out = "{\"error\": \"Error\", \"message\": \"" + e.getMessage() + "\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @GET
    @Path("show/{id_usuario}/{id_cuenta_publica}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response show(@PathParam("id_usuario") int id_usuario, 
                         @PathParam("id_cuenta_publica") int id_cuenta_publica) {
        String out = null;
        CuentaPublicaControlador ctrls = new CuentaPublicaControlador();
        try {
            Cuenta_Publica_Completa cuenta_Publica_Completa = ctrls.show(id_usuario, id_cuenta_publica);
            out = new Gson().toJson(cuenta_Publica_Completa);
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
        CuentaPublicaControlador ctrls = new CuentaPublicaControlador();
        Cuenta_Publica_Request request = gson.fromJson(datosRequest, Cuenta_Publica_Request.class);
        Cuenta_Publica_Modulo cuenta_publica = new Cuenta_Publica_Modulo();
        try {
            int idCuentaPublicaGenerada = ctrls.insert(request);
            cuenta_publica.setId_cuenta_publica(idCuentaPublicaGenerada);
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
        CuentaPublicaControlador ctrls = new CuentaPublicaControlador();
        Cuenta_Publica_Request request = gson.fromJson(datosRequest, Cuenta_Publica_Request.class);
        try {
            ctrls.update(request);
            return Response.status(Response.Status.OK).entity(gson.toJson(request)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(e.getMessage())).build();
        }
    }
    
    @DELETE
    @Path("delete/{id_usuario}/{id_cuenta_publica}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id_usuario") int id_usuario, 
                           @PathParam("id_cuenta_publica") int id_cuenta_publica) {
        CuentaPublicaControlador ctrls = new CuentaPublicaControlador();
        try {
            boolean resultado = ctrls.delete(id_usuario, id_cuenta_publica);
            if (resultado) {
                return Response.status(Response.Status.OK).entity("{\"mensaje\": \"Cuenta pública eliminada correctamente\"}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"mensaje\": \"No se pudo eliminar la cuenta pública\"}").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }
    
}
