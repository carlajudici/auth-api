package com.meuapp.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/api/protected")
public class ProtectedResource {
    
    @GET
    @Path("/me")
    @RolesAllowed("USER")
    public Response getCurrentUser(@Context SecurityContext ctx) {
        String userId = ctx.getUserPrincipal().getName();
        return Response.ok("{\"message\": \"Acesso permitido\", \"userId\": \"" + userId + "\"}").build();
    }
    
    @GET
    @Path("/admin")
    @RolesAllowed("ADMIN")
    public Response adminOnly() {
        return Response.ok("{\"message\": \"Área administrativa\"}").build();
    }
}