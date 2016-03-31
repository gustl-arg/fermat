package com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.enums.EventType;


/**
 * Created by rodrigo on 1/7/16.
 */
public class IncomingAssetReversedOnCryptoNetworkWaitingTransferenceRedemptionEventListener extends GenericEventListener {
    public IncomingAssetReversedOnCryptoNetworkWaitingTransferenceRedemptionEventListener(FermatEventMonitor fermatEventMonitor) {
        super(EventType.INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_REDEMPTION, fermatEventMonitor);
    }
}
