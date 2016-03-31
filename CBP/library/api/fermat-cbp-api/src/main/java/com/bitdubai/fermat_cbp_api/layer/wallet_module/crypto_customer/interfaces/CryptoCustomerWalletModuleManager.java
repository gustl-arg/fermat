package com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletPreferenceSettings;

/**
 * Created by angel on 17/9/15.
 */
public interface CryptoCustomerWalletModuleManager extends ModuleManager<CryptoCustomerWalletPreferenceSettings, ActiveActorIdentityInformation> {

    /**
     * @param walletPublicKey the public key of the wallet
     * @return an interface the contain the methods to manipulate the selected wallet
     */
    CryptoCustomerWalletManager getCryptoCustomerWallet(String walletPublicKey) throws com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCryptoCustomerWalletException;
}
