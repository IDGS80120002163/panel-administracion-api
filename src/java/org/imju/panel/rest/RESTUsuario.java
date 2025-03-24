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
import org.imju.panel.controllers.UsuarioControlador;
import org.imju.panel.models.Rol;
import org.imju.panel.models.Usuario;

/**
 *
 * @author IMJULEON
 */
@Path("/usuario")
public class RESTUsuario {

    @GET
    @Path("getAllUsuario/{id_usuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsuario(@PathParam("id_usuario") int id_usuario) {
        String out = null;
        List<Usuario> usuario = null;
        UsuarioControlador ctrls = new UsuarioControlador();
        try {
            usuario = ctrls.getAll(id_usuario);
            out = new Gson().toJson(usuario);
        } catch (Exception e) {
            out = """
                  {"error" : "Error"}
                  """ + e;
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @GET
    @Path("getAllRoles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRoles() {
        String out = null;
        List<Rol> rol = null;
        UsuarioControlador ctrls = new UsuarioControlador();
        try {
            rol = ctrls.getAllRoles();
            out = new Gson().toJson(rol);
        } catch (Exception e) {
            out = """
                  {"error" : "Error"}
                  """ + e;
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @GET
    @Path("show/{id_usuario}/{id_usuario_consultado}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response show(@PathParam("id_usuario") int id_usuario,
            @PathParam("id_usuario_consultado") int id_usuario_consultado) {
        String out = null;
        List<Usuario> usuario = null;
        UsuarioControlador ctrls = new UsuarioControlador();
        try {
            usuario = ctrls.show(id_usuario, id_usuario_consultado);
            out = new Gson().toJson(usuario);
        } catch (Exception e) {
            out = """
                  {"error" : "Error"}
                  """ + e;
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @POST
    @Path("insert")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(String datosUsuario) {
        Gson gson = new Gson();
        Usuario usuario = gson.fromJson(datosUsuario, Usuario.class);
        UsuarioControlador ctrls = new UsuarioControlador();
        try {
            ctrls.insert(usuario);
            return Response.status(Response.Status.OK).entity(gson.toJson(usuario)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(e.getMessage())).build();
        }
    }

    @PUT
    @Path("update/{id_usuario}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(String datosUsuario, @PathParam("id_usuario") int id_usuario) {
        Gson gson = new Gson();
        Usuario usuario = gson.fromJson(datosUsuario, Usuario.class);
        UsuarioControlador ctrls = new UsuarioControlador();
        try {
            ctrls.update(usuario, id_usuario);
            return Response.status(Response.Status.OK).entity(gson.toJson(usuario)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("delete/{id_usuario}/{id_usuario_eliminado}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id_usuario") int id_usuario, 
                           @PathParam("id_usuario_eliminado") int id_usuario_eliminado) {
        UsuarioControlador ctrls = new UsuarioControlador();
        try {
            boolean resultado = ctrls.delete(id_usuario, id_usuario_eliminado);
            if (resultado) {
                return Response.status(Response.Status.OK).entity("{\"mensaje\": \"Usuario eliminado correctamente\"}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"mensaje\": \"No se pudo eliminar el usuario\"}").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

}
