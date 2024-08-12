package com.ahirajustice.contracts.modules.user.viewmodels;

import com.ahirajustice.contracts.common.entities.User;
import com.ahirajustice.contracts.common.viewmodels.BaseViewModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserViewModel extends BaseViewModel {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String role;

    public static UserViewModel from(User user) {
        UserViewModel response = new UserViewModel();

        BeanUtils.copyProperties(user, response);

        return response;
    }
}
