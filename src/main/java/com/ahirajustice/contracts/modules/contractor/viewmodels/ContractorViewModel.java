package com.ahirajustice.contracts.modules.contractor.viewmodels;

import com.ahirajustice.contracts.common.entities.Contractor;
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
public class ContractorViewModel extends BaseViewModel {

    private String profession;
    private String accountNumber;
    private String username;

    public static ContractorViewModel from(Contractor contractor) {
        ContractorViewModel response = new ContractorViewModel();

        BeanUtils.copyProperties(contractor, response);
        response.setAccountNumber(contractor.getAccount().getAccountNumber());
        response.setUsername(contractor.getUser().getUsername());

        return response;
    }

}
