package com.meuapp.resource;

import com.meuapp.model.User;
import com.meuapp.service.AuthService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
    
    @Inject
    AuthService authService;
    
    @POST
    @Path("/register")
    public Response register(RegisterRequest request) {
        if (User.findByEmail(request.email).isPresent()) {
            return Response.status(Response.Status.CONFLICT)
                .entity(Map.of("error", "Email já cadastrado"))
                .build();
        }
        
        User user = new User(
            request.name,
            request.email,
            authService.hashPassword(request.password)
        );
        
        User.save(user);
        
        return Response.status(Response.Status.CREATED)
            .entity(Map.of("message", "Usuário cadastrado com sucesso"))
            .build();
    }
    
    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        var userOptional = User.findByEmail(request.email);
        
        if (userOptional.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED)
                .entity(Map.of("error", "Credenciais inválidas"))
                .build();
        }
        
        User user = userOptional.get();
        
        if (!authService.verifyPassword(request.password, user.getPassword())) {
            return Response.status(Response.Status.UNAUTHORIZED)
                .entity(Map.of("error", "Credenciais inválidas"))
                .build();
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login realizado com sucesso");
        response.put("user", Map.of(
            "id", user.getId(),
            "name", user.getName(),
            "email", user.getEmail()
        ));
        
        return Response.ok(response).build();
    }
    
    @GET
    @Path("/users")
    public Response listUsers() {
        return Response.ok(User.findAll()).build();
    }
    
    // Classes DTO
    public static class RegisterRequest {
        public String name;
        public String email;
        public String password;
    }
    
    public static class LoginRequest {
        public String email;
        public String password;
    }
}