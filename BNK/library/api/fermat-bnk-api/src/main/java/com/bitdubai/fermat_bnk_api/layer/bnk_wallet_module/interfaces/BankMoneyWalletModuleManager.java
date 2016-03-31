package com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.BankMoneyWalletPreferenceSettings;

/**
 * Created by memo on 04/12/15.
 */
public interface BankMoneyWalletModuleManager extends ModuleManager<BankMoneyWalletPreferenceSettings, ActiveActorIdentityInformation> {

    BankingWallet getBankingWallet();
}
