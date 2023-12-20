package com.backend.users.app.services;

import com.backend.users.app.models.dto.UserRequest;
import com.backend.users.app.models.dto.UserResponse;
import com.backend.users.app.models.entities.User;
import com.backend.users.app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Transactional
    public UserResponse save(UserRequest userRequest) {
        return Optional.ofNullable(dtoToEntity(userRequest))
                .map(this.userRepository::save)
                .map(UserService::entityToDto)
                .orElseThrow(() -> new RuntimeException("Error al guardar el usuario"));
    }

    @Transactional
    public UserResponse update(Long id, UserRequest userRequest) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error usuario no encontrado"));

        return Optional.ofNullable(userRequest)
                .map(dto -> {
                    user.setIdentificacion(dto.identificacion());
                    user.setTelefono(dto.telefono());
                    user.setNombre(dto.nombre());
                    return user;
                })
                .map(this.userRepository::save)
                .map(UserService::entityToDto)
                .orElseThrow(() -> new RuntimeException("Error al actualizar el usuario"));
    }

    @Transactional
    public void delete(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error usuario no encontrado"));
        this.userRepository.delete(user);
    }

    private static UserResponse entityToDto(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .identificacion(user.getIdentificacion())
                .telefono(user.getTelefono())
                .nombre(user.getNombre())
                .build();
    }

    private static User dtoToEntity(UserRequest userRequest) {
        return User.builder()
                .identificacion(userRequest.identificacion())
                .telefono(userRequest.telefono())
                .nombre(userRequest.nombre())
                .build();
    }
}
