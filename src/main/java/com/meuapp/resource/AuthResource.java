package com.meuapp.resource;

import com.meuapp.model.User;
import com.meuapp.service.AuthService;
import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
    
    @Inject
    AuthService authService;
    
    // Lista de emails que serão considerados administradores
    private static final Set<String> ADMIN_EMAILS = Set.of(
        "admin@meuapp.com",
        "carla@meuapp.com"
    );
    
    @POST
    @Path("/register")
    public Response register(RegisterRequest request) {
        // Validar campos obrigatórios
        if (request.name == null || request.name.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("error", "Nome é obrigatório"))
                .build();
        }
        
        if (request.email == null || request.email.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("error", "Email é obrigatório"))
                .build();
        }
        
        if (request.password == null || request.password.length() < 6) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("error", "Senha deve ter pelo menos 6 caracteres"))
                .build();
        }
        
        // Verificar se email já existe
        if (User.findByEmail(request.email).isPresent()) {
            return Response.status(Response.Status.CONFLICT)
                .entity(Map.of("error", "Email já cadastrado"))
                .build();
        }
        
        // Definir roles baseado no email
        Set<String> roles = new HashSet<>();
        roles.add("USER"); // Todo usuário tem role USER
        
        // Se for email de admin, adiciona role ADMIN
        if (ADMIN_EMAILS.contains(request.email.toLowerCase())) {
            roles.add("ADMIN");
        }
        
        // Criar novo usuário com senha criptografada
        User user = new User(
            request.name,
            request.email.toLowerCase(),
            authService.hashPassword(request.password),
            roles
        );
        
        User.save(user);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Usuário cadastrado com sucesso");
        response.put("user", Map.of(
            "id", user.getId(),
            "name", user.getName(),
            "email", user.getEmail(),
            "roles", user.getRoles()
        ));
        
        return Response.status(Response.Status.CREATED)
            .entity(response)
            .build();
    }
    
    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        // Validar campos
        if (request.email == null || request.email.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("error", "Email é obrigatório"))
                .build();
        }
        
        if (request.password == null || request.password.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("error", "Senha é obrigatória"))
                .build();
        }
        
        // Buscar usuário por email
        var userOptional = User.findByEmail(request.email.toLowerCase());
        
        if (userOptional.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED)
                .entity(Map.of("error", "Credenciais inválidas"))
                .build();
        }
        
        User user = userOptional.get();
        
        // Verificar senha
        if (!authService.verifyPassword(request.password, user.getPassword())) {
            return Response.status(Response.Status.UNAUTHORIZED)
                .entity(Map.of("error", "Credenciais inválidas"))
                .build();
        }
        
        // Gerar JWT token com as roles do usuário
        String token = Jwt.issuer("auth-api")
            .subject(user.getId().toString())
            .claim("name", user.getName())
            .claim("email", user.getEmail())
            .groups(user.getRoles())  // Inclui as roles no token
            .expiresIn(Duration.ofHours(24))
            .sign();
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("token_type", "Bearer");
        response.put("expires_in", 86400); // 24 horas em segundos
        response.put("user", Map.of(
            "id", user.getId(),
            "name", user.getName(),
            "email", user.getEmail(),
            "roles", user.getRoles()
        ));
        
        return Response.ok(response).build();
    }
    
    @GET
    @Path("/users")
    public Response listUsers() {
        // Retorna todos os usuários (sem as senhas)
        var users = User.findAll().stream()
            .map(user -> Map.of(
                "id", user.getId(),
                "name", user.getName(),
                "email", user.getEmail(),
                "roles", user.getRoles()
            ))
            .toList();
        
        return Response.ok(users).build();
    }
    
    @GET
    @Path("/users/{id}")
    public Response getUserById(@PathParam("id") Long id) {
        var userOptional = User.findById(id);
        
        if (userOptional.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("error", "Usuário não encontrado"))
                .build();
        }
        
        User user = userOptional.get();
        
        Map<String, Object> response = Map.of(
            "id", user.getId(),
            "name", user.getName(),
            "email", user.getEmail(),
            "roles", user.getRoles()
        );
        
        return Response.ok(response).build();
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