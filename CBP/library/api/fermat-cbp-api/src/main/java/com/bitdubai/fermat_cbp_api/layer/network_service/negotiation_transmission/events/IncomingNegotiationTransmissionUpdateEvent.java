package com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.events;

import com.bitdubai.fermat_cbp_api.all_definition.events.GenericCBPFermatEvent;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.interfaces.NetworkService;

/**
 * Created by Yordin Alayn 25.11.15.
 */
public final class IncomingNegotiationTransmissionUpdateEvent extends GenericCBPFermatEvent {

    private NetworkService destinationNetworkService;

    public IncomingNegotiationTransmissionUpdateEvent(EventType eventType) {
        super(eventType);
    }

    public NetworkService getDestinationNetworkService(){
        return this.destinationNetworkService;
    }

    public void setDestinationNetworkService(NetworkService destinationNetworkService){
        this.destinationNetworkService=destinationNetworkService;
    }

}
