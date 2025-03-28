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
import org.imju.panel.controllers.RolControlador;
import org.imju.panel.models.Rol;
import org.imju.panel.models.Modulo;
import org.imju.panel.models.RolRequest;
import org.imju.panel.models.RolWithPermissions;

/**
 *
 * @author IMJULEON
 */
@Path("/rol")
public class RESTRol {

    @GET
    @Path("getAllRol/{id_usuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRol(@PathParam("id_usuario") int id_usuario) {
        String out = null;
        List<Rol> rol = null;
        RolControlador ctrls = new RolControlador();
        try {
            rol = ctrls.getAll(id_usuario);
            out = new Gson().toJson(rol);
        } catch (Exception e) {
            out = """
                  {"error" : "Error"}
                  """ + e;
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @GET
    @Path("getAllModules")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllModules() {
        String out = null;
        List<Modulo> modulo = null;
        RolControlador ctrls = new RolControlador();
        try {
            modulo = ctrls.getAllModules();
            out = new Gson().toJson(modulo);
        } catch (Exception e) {
            out = """
                  {"error" : "Error"}
                  """ + e;
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @GET
    @Path("show/{id_usuario}/{id_rol}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response showTest(@PathParam("id_usuario") int id_usuario, @PathParam("id_rol") int id_rol) {
        String out = null;
        RolControlador ctrls = new RolControlador();
        try {
            RolWithPermissions rolWithPermissions = ctrls.showTest(id_usuario, id_rol);
            out = new Gson().toJson(rolWithPermissions);
        } catch (Exception e) {
            out = """
                  {"error" : "Error"}
                  """ + e;
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @DELETE
    @Path("delete/{id_usuario_registro}/{id_rol}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id_usuario_registro") int id_usuario_registro, @PathParam("id_rol") int id_rol) {
        RolControlador ctrls = new RolControlador();
        try {
            ctrls.delete(id_usuario_registro, id_rol);
            return Response.status(Response.Status.OK).entity("{\"message\": \"Rol eliminado correctamente\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }
    
    @POST
    @Path("insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(String datosRequest) {
        Gson gson = new Gson();
        RolControlador ctrls = new RolControlador();
        RolRequest request = gson.fromJson(datosRequest, RolRequest.class);
        try {
            int idRolGenerado = ctrls.insert(request.getRol(), request.getIdUsuario(), request.getModulos());
            request.getRol().setId_rol(idRolGenerado);
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
        RolControlador ctrls = new RolControlador();
        RolRequest request = gson.fromJson(datosRequest, RolRequest.class);
        try {
            ctrls.update(request.getRol(), request.getIdUsuario(), request.getModulos());
            return Response.status(Response.Status.OK).entity(gson.toJson(request)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(e.getMessage())).build();
        }
    }
    
}
