package com.bitdubai.fermat_ccp_core.layer.basic_wallet;

import com.bitdubai.fermat_ccp_core.layer.basic_wallet.loss_protected_wallet.LossProtectedWalletPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_ccp_core.layer.basic_wallet.bitcoin_wallet.BitcoinWalletPluginSubsystem;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class BasicWalletLayer extends AbstractLayer {

    public BasicWalletLayer() {
        super(Layers.BASIC_WALLET);
    }

    public void start() throws CantStartLayerException {

        try {
            registerPlugin(new BitcoinWalletPluginSubsystem());
            registerPlugin(new LossProtectedWalletPluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
