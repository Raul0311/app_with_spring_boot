package com.example.demo.application.rolecases;

import java.util.List;

public record RoleUpdateCommand(Long userId, List<String> roleNames) {}