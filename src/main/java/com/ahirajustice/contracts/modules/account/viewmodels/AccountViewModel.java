package com.ahirajustice.contracts.modules.account.viewmodels;

import com.ahirajustice.contracts.common.entities.Account;
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
public class AccountViewModel extends BaseViewModel {

    private String accountName;
    private String accountNumber;
    private String currencyCode;

    public static AccountViewModel from(Account account) {
        AccountViewModel response = new AccountViewModel();

        BeanUtils.copyProperties(account, response);

        return response;
    }

}
