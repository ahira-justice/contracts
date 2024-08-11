package com.ahirajustice.contracts.modules.user.controllers;

import com.ahirajustice.contracts.modules.user.queries.SearchUsersQuery;
import com.ahirajustice.contracts.modules.user.requests.UpdateUserRequest;
import com.ahirajustice.contracts.modules.user.services.UserService;
import com.ahirajustice.contracts.modules.user.viewmodels.UserViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.ahirajustice.contracts.common.constants.RoleConstants.AUTH_PREFIX;
import static com.ahirajustice.contracts.common.constants.RoleConstants.AUTH_SUFFIX;
import static com.ahirajustice.contracts.common.constants.RoleConstants.SUPER_ADMIN;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize(AUTH_PREFIX + SUPER_ADMIN + AUTH_SUFFIX)
    @RequestMapping(path = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<UserViewModel> searchUsers(SearchUsersQuery query) {
        return userService.searchUsers(query);
    }

    @PreAuthorize(AUTH_PREFIX + SUPER_ADMIN + AUTH_SUFFIX)
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public UserViewModel getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    @PreAuthorize(AUTH_PREFIX + SUPER_ADMIN + AUTH_SUFFIX)
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public UserViewModel updateUser(@PathVariable long id, @Valid @RequestBody UpdateUserRequest request) {
        return userService.updateUser(request, id);
    }

}
