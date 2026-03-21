package com.meuapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    
    // Simulando banco de dados em memória
    private static List<User> users = new ArrayList<>();
    private static Long nextId = 1L;
    
    public User() {}
    
    public User(String name, String email, String password) {
        this.id = nextId++;
        this.name = name;
        this.email = email;
        this.password = password;
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
    
    public static List<User> findAll() {
        return new ArrayList<>(users);
    }
}