package com.meuapp.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Set<String> roles;
    
    // Simulando banco de dados em memória
    private static List<User> users = new ArrayList<>();
    private static Long nextId = 1L;
    
    public User() {}
    
    // Construtor para usuário normal (role USER automática)
    public User(String name, String email, String password) {
        this.id = nextId++;
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = new HashSet<>();
        this.roles.add("USER");
    }
    
    // Construtor completo com roles
    public User(String name, String email, String password, Set<String> roles) {
        this.id = nextId++;
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles != null ? roles : new HashSet<>();
        if (!this.roles.contains("USER")) {
            this.roles.add("USER");
        }
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }
    
    // Métodos estáticos para simular banco
    public static User save(User user) {
        users.add(user);
        return user;
    }
    
    public static Optional<User> findByEmail(String email) {
        return users.stream()
            .filter(u -> u.getEmail().equals(email))
            .findFirst();
    }
    
    public static Optional<User> findById(Long id) {
        return users.stream()
            .filter(u -> u.getId().equals(id))
            .findFirst();
    }
    
    public static List<User> findAll() {
        return new ArrayList<>(users);
    }
    
    // Verificar se tem role específica
    public boolean hasRole(String role) {
        return roles != null && roles.contains(role);
    }
    
    // Limpar dados (útil para testes)
    public static void clear() {
        users.clear();
        nextId = 1L;
    }
}