package com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.enums.EventType;

/**
 * Created by eze on 2015.09.02..
 */
public class IncomingCryptoMetadataEvent extends AbstractFermatEvent {
    public IncomingCryptoMetadataEvent() {
        super(EventType.INCOMING_CRYPTO_METADATA);
    }
}
