package com.ahirajustice.contracts.modules.user.services;

import com.ahirajustice.contracts.common.entities.User;
import com.ahirajustice.contracts.modules.user.queries.SearchUsersQuery;
import com.ahirajustice.contracts.modules.user.requests.CreateUserRequest;
import com.ahirajustice.contracts.modules.user.requests.UpdateUserRequest;
import com.ahirajustice.contracts.modules.user.viewmodels.UserViewModel;
import org.springframework.data.domain.Page;

public interface UserService {

    Page<UserViewModel> searchUsers(SearchUsersQuery query);
    UserViewModel getUser(long id);
    User createUser(CreateUserRequest request, String role);
    UserViewModel updateUser(UpdateUserRequest request, long id);

}
