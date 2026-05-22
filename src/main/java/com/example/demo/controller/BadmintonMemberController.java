package com.example.demo.controller;

import com.example.demo.entity.MemberBadminton;
import com.example.demo.model.PageRequestListDTO;
import com.example.demo.model.PageResponseDTO;
import com.example.demo.model.badminton.MemberRequestDTO;
import com.example.demo.model.badminton.MemberResponseDTO;
import com.example.demo.repository.MemberBadmintonRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ec")
public class BadmintonMemberController {
    private final MemberBadmintonRepository  memberBadmintonRepository;

    public BadmintonMemberController ( MemberBadmintonRepository  memberBadmintonRepository) {
        this.memberBadmintonRepository = memberBadmintonRepository;
    }
    @PostMapping
    @Operation(
            summary = "Create New Member",
            description = "Menambahkan member badminton baru ke dalam sistem"
    )
    public MemberResponseDTO create(@RequestBody MemberRequestDTO req) {
        MemberBadminton member = new MemberBadminton();
        member.setName(req.getName());
        member.setClassname(req.getClassname());

        MemberBadminton savedMember = memberBadmintonRepository.save(member);

        MemberResponseDTO responseDTO = new MemberResponseDTO();
        responseDTO.setId(savedMember.getId());
        responseDTO.setName(savedMember.getName());
        responseDTO.setClassname(savedMember.getClassname());

        return responseDTO;
    }
    @GetMapping
    @Operation(
            summary = "Get List Member",
            description = ""
    )
//    public PageResponseDTO<MemberResponseDTO> list(PageRequestListDTO req){
//        int page = req.getPageNo() != null ? req.getPageNo() - 1 : 0;
//        int size = req.getRecordPerPage() != null ? req.getRecordPerPage() : 10;
//
//        PageRequest pageable = PageRequest.of(
//                page,
//                size,
//                Sort.by(Sort.Direction.DESC, "id")
//        );
//        Page<MemberBadminton> pageResult = memberBadmintonRepository.findAll(pageable);
//        List<MemberResponseDTO> memberResponseDTOS = pageResult.getContent().stream()
//                .map(memberBadminton -> {
//                    MemberResponseDTO memberResponseDTO = new MemberResponseDTO();
//                    memberResponseDTO.setId(memberBadminton.getId());
//                    memberResponseDTO.setName(memberBadminton.getName());
//                    memberResponseDTO.setClassname(memberBadminton.getClassname());
//                    return memberResponseDTO;
//                }).toList();
//        return new  PageResponseDTO<>(memberResponseDTOS, page, size);
//    }
    public PageResponseDTO<MemberResponseDTO> list(PageRequestListDTO req) {
        int page = req.getPageNo() != null && req.getPageNo() > 0 ? req.getPageNo() - 1 : 0;
        int size = req.getRecordPerPage() != null ? req.getRecordPerPage() : 10;

        PageRequest pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "id")
        );

        Page<MemberBadminton> pageResult;

        // Cek apakah parameter search / keyword dikirim dari request
        if (req.getSearch() != null && !req.getSearch().trim().isEmpty()) {
            String keyword = req.getSearch().trim();
            // Memanggil query custom di repository untuk pencarian partial (LIKE)
            pageResult = memberBadmintonRepository.findByNameContainingIgnoreCaseOrClassnameContainingIgnoreCase(keyword, keyword, pageable);
        } else {
            pageResult = memberBadmintonRepository.findAll(pageable);
        }

        List<MemberResponseDTO> memberResponseDTOS = pageResult.getContent().stream()
                .map(memberBadminton -> {
                    MemberResponseDTO memberResponseDTO = new MemberResponseDTO();
                    memberResponseDTO.setId(memberBadminton.getId());
                    memberResponseDTO.setName(memberBadminton.getName());
                    memberResponseDTO.setClassname(memberBadminton.getClassname());
                    return memberResponseDTO;
                }).toList();

        // Tambahkan totalElements agar DTO penampung bisa mengembalikan total list member
        long totalData = pageResult.getTotalElements();
        int totalPage = pageResult.getTotalPages();

        // Return sesuai struktur PageResponseDTO baru kamu
        return new PageResponseDTO<>(memberResponseDTOS, totalData, totalPage);
    }

}
