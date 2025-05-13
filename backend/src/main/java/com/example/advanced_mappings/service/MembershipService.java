package com.example.advanced_mappings.service;

import com.example.advanced_mappings.dtos.MembershipDTO;
import com.example.advanced_mappings.dtos.Response;

public interface MembershipService {
    Response createMembership(MembershipDTO membershipDTO);
    Response getAllMemberships();
    Response getMembershipById(Long id);
    Response updateMembership(Long id, MembershipDTO membershipDTO);
    Response deleteMembership(Long id);
}
