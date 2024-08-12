package com.ahirajustice.contracts.modules.contract.viewmodels;

import com.ahirajustice.contracts.common.entities.Contract;
import com.ahirajustice.contracts.common.enums.ContractStatus;
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
public class ContractViewModel extends BaseViewModel {

    private String terms;
    private ContractStatus status;
    private String contractor;
    private String client;

    public static ContractViewModel from(Contract contract) {
        ContractViewModel response = new ContractViewModel();

        BeanUtils.copyProperties(contract, response);
        response.setContractor(contract.getContractor().getUser().getUsername());
        response.setClient(contract.getClient().getUser().getUsername());

        return response;
    }

}
