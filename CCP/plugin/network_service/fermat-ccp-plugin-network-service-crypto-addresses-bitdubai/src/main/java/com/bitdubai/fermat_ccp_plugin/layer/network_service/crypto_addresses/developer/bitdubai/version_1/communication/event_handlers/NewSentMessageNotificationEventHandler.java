package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.event_handlers;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.AbstractCommunicationBaseEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageSentNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;

/**
 * Created by Joaquin C. on 23/11/15.
 */
public class NewSentMessageNotificationEventHandler extends AbstractCommunicationBaseEventHandler<NewNetworkServiceMessageSentNotificationEvent> {

    /**
     * Constructor with parameter
     *
     * @param
     */

    public NewSentMessageNotificationEventHandler(NetworkService networkService) {
        super(networkService);
    }

    /**
     * (non-Javadoc)
     *
     * @param event
     * @throws Exception
     * @see FermatEventHandler#handleEvent(FermatEvent)
     */
    @Override
    public void processEvent(NewNetworkServiceMessageSentNotificationEvent event) {
        ns.handleNewSentMessageNotificationEvent((FermatMessage) event.getData());

    }
}
