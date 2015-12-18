package com.bitdubai.fermat_csh_core.layer.wallet;

import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_csh_core.layer.wallet.cash_money.CashMoneyPluginSubsystem;

/**
 * Created by Alejandro Bicelis on 11/25/2015.
 */
public class WalletLayer extends AbstractLayer {

    public WalletLayer() {
        super(Layers.WALLET);
    }

    public void start() throws CantStartLayerException {

        try {
            registerPlugin(new CashMoneyPluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}