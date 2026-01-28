package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.model.PageResponseDTO;
import com.example.demo.model.UserReponseDTO;
import com.example.demo.model.UserRequestDTO;
import com.example.demo.model.PageRequestListDTO;
import com.example.demo.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get User"

    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserReponseDTO.class)
            )
    )
    public Object user(@PathVariable Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        UserReponseDTO userReponseDTO = new UserReponseDTO();
        userReponseDTO.setId(user.getId());
        userReponseDTO.setEmail(user.getEmail());
        userReponseDTO.setFullname(user.getFullname());
        userReponseDTO.setName(user.getName());
        return userReponseDTO;
    }

    @GetMapping
    @Operation(
            summary = "Get list users",
            description = "Ambil list user dengan pagination"
    )
    public PageResponseDTO<UserReponseDTO> list(PageRequestListDTO req) {

        int page = req.getPageNo() != null ? req.getPageNo() - 1 : 0;
        int size = req.getRecordPerPage() != null ? req.getRecordPerPage() : 10;

        PageRequest pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "id")
        );

        Page<User> pageResult = userRepository.findAll(pageable);

        List<UserReponseDTO> data = pageResult.getContent().stream()
                .map(user -> {
                    UserReponseDTO dto = new UserReponseDTO();
                    dto.setId(user.getId());
                    dto.setName(user.getName());
                    dto.setEmail(user.getEmail());
                    dto.setFullname(user.getFullname());
                    return dto;
                })
                .toList();

        return new PageResponseDTO<>(
                data,
                pageResult.getTotalElements(),
                pageResult.getTotalPages()
        );
    }

    @Operation(
            summary = "Create User",
            description = "Menyimpan user baru ke database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "User berhasil dibuat",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserReponseDTO.class)
            )
    )
    @PostMapping
    public Object save(UserRequestDTO userRequestDTO) {
        String name = userRequestDTO.getName();
        String email = userRequestDTO.getEmail();
        String fullname = userRequestDTO.getFullname();
        String password = passwordEncoder.encode(userRequestDTO.getPassword());
        boolean emailExist = userRepository.existsByEmail(email);
        if (emailExist) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email exists");
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setFullname(fullname);
        user.setPassword(password);
        userRepository.save(user);

        UserReponseDTO userReponseDTO = new UserReponseDTO();
        userReponseDTO.setId(user.getId());
        userReponseDTO.setName(user.getName());
        userReponseDTO.setEmail(user.getEmail());
        userReponseDTO.setFullname(user.getFullname());
        return userReponseDTO;
    }
    @PutMapping("/{id}")
    @Operation(
            summary = "Update User",
            description = "Update data user berdasarkan ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "User berhasil diupdate",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserReponseDTO.class)
            )
    )
    public UserReponseDTO update(
            @PathVariable Integer id,
            @RequestBody UserRequestDTO userRequestDTO
    ) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"
                ));

        if (!user.getEmail().equals(userRequestDTO.getEmail())
                && userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Email exists"
            );
        }

        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setFullname(userRequestDTO.getFullname());

        if (userRequestDTO.getPassword() != null &&
                !userRequestDTO.getPassword().isBlank()) {
            user.setPassword(
                    passwordEncoder.encode(userRequestDTO.getPassword())
            );
        }

        userRepository.save(user);

        UserReponseDTO res = new UserReponseDTO();
        res.setId(user.getId());
        res.setName(user.getName());
        return res;
    }

}
