package com.ahirajustice.contracts.modules.user.services.impl;

import com.ahirajustice.contracts.common.entities.User;
import com.ahirajustice.contracts.common.exceptions.BadRequestException;
import com.ahirajustice.contracts.common.exceptions.NotFoundException;
import com.ahirajustice.contracts.common.repositories.UserRepository;
import com.ahirajustice.contracts.modules.user.queries.SearchUsersQuery;
import com.ahirajustice.contracts.modules.user.requests.CreateUserRequest;
import com.ahirajustice.contracts.modules.user.requests.UpdateUserRequest;
import com.ahirajustice.contracts.modules.user.services.UserService;
import com.ahirajustice.contracts.modules.user.viewmodels.UserViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
     public Page<UserViewModel> searchUsers(SearchUsersQuery query) {
        return userRepository.findAll(query.getPredicate(), query.getPageable()).map(UserViewModel::from);
    }

    @Override
    public UserViewModel getUser(long id) {
        Optional<User> userExists = userRepository.findById(id);

        if (userExists.isEmpty()) {
            throw new NotFoundException(String.format("User with id: '%d' does not exist", id));
        }

        return UserViewModel.from(userExists.get());
    }

    @Override
    public User createUser(CreateUserRequest request, String role) {
        Optional<User> userExists = userRepository.findByUsername(request.getUsername());

        if (userExists.isPresent()) {
            throw new BadRequestException(String.format("User with username: '%s' already exists", request.getUsername()));
        }

        User user = buildUser(request);

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(role);

        return userRepository.save(user);
    }

    @Override
    public UserViewModel updateUser(UpdateUserRequest request, long id) {
        Optional<User> userExists = userRepository.findById(id);

        if (userExists.isEmpty()) {
            throw new NotFoundException(String.format("User with id: '%d' does not exist", id));
        }

        User user = userExists.get();

        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return UserViewModel.from(userRepository.save(user));
    }

    private User buildUser(CreateUserRequest request) {
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
    }

}
