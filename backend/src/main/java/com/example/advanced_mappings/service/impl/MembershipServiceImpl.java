package com.example.advanced_mappings.service.impl;

import com.example.advanced_mappings.dtos.MembershipDTO;
import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.exceptions.NotFoundException;
import com.example.advanced_mappings.models.Membership;
import com.example.advanced_mappings.models.User;
import com.example.advanced_mappings.repos.MembershipRepository;
import com.example.advanced_mappings.repos.UserRepository;
import com.example.advanced_mappings.service.MembershipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response createMembership(MembershipDTO membershipDTO) {
        // Fetch user by provided userId from DTO
        User user = userRepository.findById(membershipDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("Member User not found"));

        // Map the DTO to entity (without setting user directly here)
        Membership membership = modelMapper.map(membershipDTO, Membership.class);

        // Link membership using User's addMembership method to ensure consistency
        user.addMembership(membership);

        // Explicit save (optional if cascade works correctly, but safe here)
        membershipRepository.save(membership);

        return Response.builder()
                .status(200)
                .message("Membership saved and linked to user")
                .build();
    }

    @Override
    public Response getAllMemberships() {
        List<Membership> memberships = membershipRepository.findAll(Sort.by(Sort.Direction.DESC, "membershipId"));
        List<MembershipDTO> dtoList = modelMapper.map(memberships, new TypeToken<List<MembershipDTO>>() {}.getType());
        return Response.builder()
                .status(200)
                .message("success")
                .memberships(dtoList)
                .build();
    }

    @Override
    public Response getMembershipById(Long id) {
        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Membership not found"));

        MembershipDTO dto = modelMapper.map(membership, MembershipDTO.class);
        return Response.builder()
                .status(200)
                .message("success")
                .membership(dto)
                .build();
    }

    @Override
    public Response updateMembership(Long id, MembershipDTO dto) {
        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Membership not found"));

        modelMapper.map(dto, membership);

        membershipRepository.save(membership);

        return Response.builder()
                .status(200)
                .message("Membership updated")
                .build();
    }

    @Override
    public Response deleteMembership(Long id) {
        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Membership not found"));

        membershipRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("Membership deleted")
                .build();
    }
}
