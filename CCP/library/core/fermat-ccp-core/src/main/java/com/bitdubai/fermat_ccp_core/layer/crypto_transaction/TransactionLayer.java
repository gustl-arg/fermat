package com.bitdubai.fermat_ccp_core.layer.crypto_transaction;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_ccp_core.layer.crypto_transaction.hold.HoldCryptoMoneyTransactionPluginSubsystem;
import com.bitdubai.fermat_ccp_core.layer.crypto_transaction.incoming_extra_actor.IncomingExtraUserPluginSubsystem;
import com.bitdubai.fermat_ccp_core.layer.crypto_transaction.incoming_intra_user.IncomingIntraUserPluginSubsystem;
import com.bitdubai.fermat_ccp_core.layer.crypto_transaction.outgoing_draft.OutgoingDraftTransactionPluginSubsystem;
import com.bitdubai.fermat_ccp_core.layer.crypto_transaction.outgoing_extra_user.OutgoingExtraUserPluginSubsystem;
import com.bitdubai.fermat_ccp_core.layer.crypto_transaction.outgoing_intra_actor.OutgoingIntraActorPluginSubsystem;
import com.bitdubai.fermat_ccp_core.layer.crypto_transaction.unhold.UnHoldCryptoMoneyTransactionPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class TransactionLayer extends AbstractLayer {

    public TransactionLayer() {
        super(Layers.TRANSACTION);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new IncomingExtraUserPluginSubsystem());
            registerPlugin(new IncomingIntraUserPluginSubsystem());
            registerPlugin(new OutgoingExtraUserPluginSubsystem());
            registerPlugin(new OutgoingIntraActorPluginSubsystem());
            registerPlugin(new OutgoingDraftTransactionPluginSubsystem());
            registerPlugin(new HoldCryptoMoneyTransactionPluginSubsystem());
            registerPlugin(new UnHoldCryptoMoneyTransactionPluginSubsystem());
            //registerPlugin(new UnHoldCryptoMoneyTransactionPluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
