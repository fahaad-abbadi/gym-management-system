package com.example.advanced_mappings.service.impl;

import com.example.advanced_mappings.dtos.LoginRequest;
import com.example.advanced_mappings.dtos.RegisterRequest;
import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.dtos.UserDTO;
import com.example.advanced_mappings.enums.ApprovalStatus;
import com.example.advanced_mappings.enums.Role;
import com.example.advanced_mappings.exceptions.InvalidCredentialsException;
import com.example.advanced_mappings.exceptions.NotFoundException;
import com.example.advanced_mappings.models.Member;
import com.example.advanced_mappings.models.Staff;
import com.example.advanced_mappings.models.Trainer;
import com.example.advanced_mappings.models.User;
import com.example.advanced_mappings.repos.MemberRepository;
import com.example.advanced_mappings.repos.StaffRepository;
import com.example.advanced_mappings.repos.TrainerRepository;
import com.example.advanced_mappings.repos.UserRepository;
import com.example.advanced_mappings.security.JwtUtils;
import com.example.advanced_mappings.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final MemberRepository memberRepository;
    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtUtils jwtUtils;

    @Override
    public Response registerUser(RegisterRequest request) {
        Role role = request.getRole() != null ? request.getRole() : Role.MEMBER;
        ApprovalStatus approvalStatus = (role == Role.MEMBER || role == Role.ADMIN)
                ? ApprovalStatus.APPROVED
                : ApprovalStatus.PENDING;

        User user = User.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .approvalStatus(approvalStatus)
                .build();

        User savedUser = userRepository.save(user);

        createProfileIfNotExists(savedUser);

        return Response.builder()
                .status(201)
                .message("User registered successfully")
                .build();
    }

    @Override
    public Response approveTrainer(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (user.getRole() != Role.TRAINER) {
            throw new IllegalArgumentException("Only trainers can be approved");
        }

        user.setApprovalStatus(ApprovalStatus.APPROVED);
        User savedUser = userRepository.save(user);

        createProfileIfNotExists(savedUser);

        return Response.builder()
                .status(200)
                .message("Trainer approved successfully")
                .build();
    }

    private void createProfileIfNotExists(User user) {
        if (user.getRole() == Role.TRAINER && user.getApprovalStatus() == ApprovalStatus.APPROVED) {
            if (trainerRepository.findByUserId(user.getId()).isEmpty()) {
                Trainer trainerProfile = new Trainer();
                trainerProfile.setHireDate(new Date());
                trainerProfile.setSpeciality("General Fitness");
                trainerProfile.setUser(user);
                trainerRepository.save(trainerProfile);
            }
        } else if (user.getRole() == Role.MEMBER && user.getApprovalStatus() == ApprovalStatus.APPROVED) {
            if (memberRepository.findByUserId(user.getId()).isEmpty()) {
                Member memberProfile = new Member();
                memberProfile.setJoinDate(new Date());
                memberProfile.setUser(user);
                memberRepository.save(memberProfile);
            }
        } else if (user.getRole() == Role.STAFF && user.getApprovalStatus() == ApprovalStatus.APPROVED) {
            if (staffRepository.findByUserId(user.getId()).isEmpty()) {
                Staff staffProfile = new Staff();
                staffProfile.setHireDate(new Date());
                staffProfile.setPositionTitle("Receptionist");
                staffProfile.setSalary(25000.00);
                staffProfile.setUser(user);
                staffRepository.save(staffProfile);
            }
        }
    }


    @Override
    public Response loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmailIgnoreCase(loginRequest.getEmail())
                .orElseThrow(() -> new NotFoundException("Email not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Password does not match");
        }

        String token = jwtUtils.generateToken(user.getEmail());

        return Response.builder()
                .status(200)
                .message("User logged in successfully")
                .token(token)
                .role(user.getRole())
                .build();
    }

    @Override
    public Response getAllUsers() {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<UserDTO> dtos = modelMapper.map(users, new TypeToken<List<UserDTO>>() {}.getType());

        return Response.builder()
                .status(200)
                .message("success")
                .users(dtos)
                .build();
    }

    @Override
    public Response getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return Response.builder()
                .status(200)
                .message("success")
                .user(userDTO)
                .build();
    }

    @Override
    public Response updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (userDTO.getEmail() != null) user.setEmail(userDTO.getEmail());
        if (userDTO.getUserName() != null) user.setUserName(userDTO.getUserName());
        if (userDTO.getPhoneNumber() != null) user.setPhoneNumber(userDTO.getPhoneNumber());
        if (userDTO.getRole() != null) user.setRole(userDTO.getRole());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        userRepository.save(user);

        return Response.builder()
                .status(200)
                .message("User successfully updated")
                .build();
    }

    @Override
    public Response deleteUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        userRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("User successfully deleted")
                .build();
    }

    @Override
    public Response getPendingTrainers() {
        List<User> pending = userRepository.findByApprovalStatus(ApprovalStatus.PENDING)
                .stream()
                .filter(u -> u.getRole() == Role.TRAINER)
                .toList();

        List<UserDTO> dtos = modelMapper.map(pending, new TypeToken<List<UserDTO>>() {}.getType());

        return Response.builder()
                .status(200)
                .message("Pending trainers fetched")
                .users(dtos)
                .build();
    }

    @Override
    public Response updateRole(Long userId, Role newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        user.setRole(newRole);

        if (newRole == Role.TRAINER) {
            user.setApprovalStatus(ApprovalStatus.PENDING);
        }

        User savedUser = userRepository.save(user);

        createProfileIfNotExists(savedUser);

        return Response.builder()
                .status(200)
                .message("Role updated")
                .user(modelMapper.map(user, UserDTO.class))
                .build();
    }

    @Override
    public Response getPendingStaff() {
        List<User> pending = userRepository.findByApprovalStatus(ApprovalStatus.PENDING)
                .stream()
                .filter(u -> u.getRole() == Role.STAFF)
                .toList();

        List<UserDTO> dtos = modelMapper.map(pending, new TypeToken<List<UserDTO>>() {}.getType());

        return Response.builder()
                .status(200)
                .message("Pending staff fetched")
                .users(dtos)
                .build();
    }

    @Override
    public Response approveStaff(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (user.getRole() != Role.STAFF) {
            throw new IllegalArgumentException("Only staff can be approved");
        }

        user.setApprovalStatus(ApprovalStatus.APPROVED);
        User savedUser = userRepository.save(user);

        createProfileIfNotExists(savedUser);

        return Response.builder()
                .status(200)
                .message("Staff approved successfully")
                .build();
    }

    @Override
    public Response getClientsForTrainer(Long trainerId) {
        User trainer = userRepository.findById(trainerId)
                .orElseThrow(() -> new NotFoundException("Trainer not found"));

        if (trainer.getRole() != Role.TRAINER) {
            throw new IllegalArgumentException("User is not a trainer");
        }

        List<User> clients = userRepository.findByAssignedTrainerId(trainerId);
        List<UserDTO> dtos = modelMapper.map(clients, new TypeToken<List<UserDTO>>() {}.getType());

        return Response.builder()
                .status(200)
                .message("Clients fetched")
                .users(dtos)
                .build();
    }

    @Override
    public User getCurrentLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            throw new NotFoundException("Not authenticated");
        }

        String email = auth.getName();

        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new NotFoundException("User not found for email: " + email));
    }
}
