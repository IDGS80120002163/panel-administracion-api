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
import org.imju.panel.controllers.AccesoControlador;
import org.imju.panel.models.Permisos;
import org.imju.panel.models.Rol;
import org.imju.panel.models.Usuario;

/**
 *
 * @author IMJULEON
 */
@Path("/acceso")
public class RESTAcceso {

    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(String datosUsuario) {
        Gson gson = new Gson();
        Usuario usuario = gson.fromJson(datosUsuario, Usuario.class);
        AccesoControlador ctrls = new AccesoControlador();
        try {
            Usuario usuarioSesion = ctrls.logIn(usuario.getEmail(), usuario.getContrasena());
            if (usuarioSesion != null) {
                return Response.status(Response.Status.OK).entity(gson.toJson(usuarioSesion)).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity(gson.toJson("El usuario no existe o está inactivo")).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(e.getMessage())).build();
        }
    }
    
    @DELETE
    @Path("logout/{id_usuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@PathParam("id_usuario") int id_usuario) {
        AccesoControlador ctrls = new AccesoControlador();
        try {
            boolean resultado = ctrls.logOut(id_usuario);
            if (resultado) {
                return Response.status(Response.Status.OK).entity("{\"mensaje\": \"Cierre de sesión exitoso\"}").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"mensaje\": \"No se pudo cerrar la sesión\"}").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }
    
    @PUT
    @Path("updateProfile")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProfile(String datosUsuario) {
        Gson gson = new Gson();
        Usuario usuario = gson.fromJson(datosUsuario, Usuario.class);
        AccesoControlador ctrls = new AccesoControlador();
        try {
            Usuario usuarioSesion = ctrls.updateProfile(usuario);
            if (usuarioSesion != null) {
                return Response.status(Response.Status.OK).entity(gson.toJson(usuarioSesion)).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity(gson.toJson("El usuario no puede volver a usar una antigua contraseña")).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(e.getMessage())).build();
        }
    }
    
    @GET
    @Path("showOutPermit/{id_rol}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response showOutPermit(@PathParam("id_rol") int id_rol) {
        String out = null;
        List<Permisos> permisos = null;
        AccesoControlador ctrls = new AccesoControlador();
        try {
            permisos = ctrls.showOutPermit(id_rol);
            out = new Gson().toJson(permisos);
        } catch (Exception e) {
            out = "{\"error\": \"Error\", \"message\": \"" + e.getMessage() + "\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
