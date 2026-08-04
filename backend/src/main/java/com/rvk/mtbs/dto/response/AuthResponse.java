package com.rvk.mtbs.dto.response;

import com.rvk.mtbs.enums.Role;

public record AuthResponse(String token, String tokenType, Long id, String name, String email, Role role) {
}