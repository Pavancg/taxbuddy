package com.taxbuddy.taxbuddy_user_service.dto;

public record AuthResponse(
        String token,
        String email,
        String fullName,
        String role,
        String message
) {

    public static AuthResponse success(String token, String email, String fullName, String role){
        return new AuthResponse(token, email, fullName, role, "Sucesss");
    }

    public static AuthResponse error(String message){
        return new AuthResponse(null, null, null, null, message);
    }
}
