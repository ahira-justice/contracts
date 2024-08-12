package com.ahirajustice.contracts.modules.client.viewmodels;

import com.ahirajustice.contracts.common.entities.Client;
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
public class ClientViewModel extends BaseViewModel {

    private String profession;
    private String accountNumber;
    private String username;

    public static ClientViewModel from(Client client) {
        ClientViewModel response = new ClientViewModel();

        BeanUtils.copyProperties(client, response);
        response.setAccountNumber(client.getAccount().getAccountNumber());
        response.setUsername(client.getUser().getUsername());

        return response;
    }

}
