package com.example.demo.controller;

import com.example.demo.entity.MasterTech;
import com.example.demo.entity.User;
import com.example.demo.model.*;
import com.example.demo.repository.MasterTectRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/mastertech")
public class MasterTechController {
    private final MasterTectRepository masterTectRepository;
    public MasterTechController(MasterTectRepository masterTectRepository) {
        this.masterTectRepository = masterTectRepository;
    }
    @Operation(
            summary = "Create Tech",
            description = "Master Tech has been created"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Master Tech has been created",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MasterTechResponseDTO.class)
            )
    )
    @PostMapping
    public Object save(@RequestBody MasterTechRequestDTO requestDTO){
        String name = requestDTO.getName();
        MasterTech mt = new MasterTech();
        mt.setName(name);
        if (masterTectRepository.existsByName(name)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Name already exists");
        }else {
            masterTectRepository.save(mt);
        }
        MasterTechResponseDTO response = new MasterTechResponseDTO();
        response.setId(mt.getId());
        response.setName(name);
        response.setMessage("Master Tech has been created");
        response.setSuccess(true);
        return response;
    }
    @PutMapping("/{id}")
    @Operation(
            summary = "Update Tech",
            description = "Master Tech has been updated"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Master Tech has been updated",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MasterTechResponseDTO.class)
            )
    )
    public MasterTechResponseDTO update(
            @PathVariable Integer id,
            @RequestBody MasterTechRequestDTO requestDTO
    ){

        MasterTech mt = masterTectRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Data Not Found"
        ));
        mt.setName(requestDTO.getName());
        masterTectRepository.save(mt);
        MasterTechResponseDTO response = new MasterTechResponseDTO();
        response.setId(mt.getId());
        response.setName(requestDTO.getName());
        return response;
    }
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Tech",
            description = "Master Tech has been deleted"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Master Tech has been deleted",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MasterTechResponseDTO.class)
            )
    )
    public ResponseEntity<MasterTechResponseDTO> delete(@PathVariable Integer id) {

       boolean data = masterTectRepository.existsById(id);
        MasterTechResponseDTO response = new MasterTechResponseDTO();
       if(!data){
           response.setMessage("Delete Failed");
           response.setSuccess(false);
       }else {
           masterTectRepository.deleteByIdCustom(id);
           response.setMessage("Delete success");
           response.setSuccess(true);
       }
        return ResponseEntity.ok(response);
    }
    @GetMapping
    @Operation(
            summary = "Get list Tech",
            description = ""
    )
    public PageResponseDTO<MasterTechResponseDTO> list(PageRequestListDTO req) {

        int page = req.getPageNo() != null ? req.getPageNo() - 1 : 0;
        int size = req.getRecordPerPage() != null ? req.getRecordPerPage() : 10;

        PageRequest pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "id")
        );

        Page<MasterTech> pageResult = masterTectRepository.findAll(pageable);

        List<MasterTechResponseDTO> data = pageResult.getContent().stream()
                .map(user -> {
                    MasterTechResponseDTO dto = new MasterTechResponseDTO();
                    dto.setId(user.getId());
                    dto.setName(user.getName());
                    return dto;
                })
                .toList();

        return new PageResponseDTO<>(
                data,
                pageResult.getTotalElements(),
                pageResult.getTotalPages()
        );
    }


}
