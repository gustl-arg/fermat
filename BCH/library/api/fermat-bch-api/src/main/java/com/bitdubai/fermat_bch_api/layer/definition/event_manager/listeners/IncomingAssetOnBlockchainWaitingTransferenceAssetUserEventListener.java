package com.bitdubai.fermat_bch_api.layer.definition.event_manager.listeners;

import com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.enums.EventType;


/**
 * Created by rodrigo on 10/15/15.
 */
public class IncomingAssetOnBlockchainWaitingTransferenceAssetUserEventListener extends GenericEventListener {
    public IncomingAssetOnBlockchainWaitingTransferenceAssetUserEventListener(FermatEventMonitor fermatEventMonitor) {
        super(EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_USER, fermatEventMonitor);
    }
}
