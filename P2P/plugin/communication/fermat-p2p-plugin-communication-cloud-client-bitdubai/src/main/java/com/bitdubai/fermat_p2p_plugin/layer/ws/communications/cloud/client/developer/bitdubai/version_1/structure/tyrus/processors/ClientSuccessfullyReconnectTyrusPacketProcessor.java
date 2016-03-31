/*
 * @#ClientSuccessfullyReconnectPacketProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.processors;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.WsCommunicationsTyrusCloudClientChannel;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors.ClientSuccessfullyReconnectTyrusPacketProcessor</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 07/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ClientSuccessfullyReconnectTyrusPacketProcessor extends FermatTyrusPacketProcessor {

    /**
     * Represent the gson
     */
    private Gson gson;

    /**
     * Represent the jsonParser
     */
    private JsonParser jsonParser;

    /**
     * Constructor
     */
    public ClientSuccessfullyReconnectTyrusPacketProcessor(WsCommunicationsTyrusCloudClientChannel wsCommunicationsTyrusCloudClientChannel){
        super(wsCommunicationsTyrusCloudClientChannel);
        gson = new Gson();
        jsonParser = new JsonParser();
    }

    /**
     * (no-javadoc)
     * @see FermatTyrusPacketProcessor#processingPackage(FermatPacket)
     */
    @Override
    public void processingPackage(FermatPacket receiveFermatPacket) {

        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println("ClientSuccessfullyReconnectTyrusPacketProcessor - processingPackage");

       /*
        * Get the platformComponentProfile from the message content and decrypt
        */
        String messageContentJsonStringRepresentation = AsymmetricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), getWsCommunicationsTyrusCloudClientChannel().getClientIdentity().getPrivateKey());

        System.out.println("ClientSuccessfullyReconnectTyrusPacketProcessor - messageContentJsonStringRepresentation = "+messageContentJsonStringRepresentation);

        /*
         * Create a raise a new event whit the platformComponentProfile registered
         */
        FermatEvent event = P2pEventType.CLIENT_SUCCESS_RECONNECT.getNewEvent();
        event.setSource(EventSource.WS_COMMUNICATION_CLOUD_CLIENT_PLUGIN);

        /*
         * we must ensure set true to Client Register preventing registration of the Network Services if are no registered
         */
        getWsCommunicationsTyrusCloudClientChannel().setIsRegister(Boolean.TRUE);

        /*
         * Raise the event
         */
        System.out.println("ClientSuccessfullyReconnectTyrusPacketProcessor - Raised a event = P2pEventType.CLIENT_SUCCESS_RECONNECT");
        getWsCommunicationsTyrusCloudClientChannel().getEventManager().raiseEvent(event);

    }

    /**
     * (no-javadoc)
     * @see FermatTyrusPacketProcessor#getFermatPacketType()
     */
    @Override
    public FermatPacketType getFermatPacketType() {
        return FermatPacketType.CLIENT_CONNECTION_SUCCESSFULLY_RECONNECT;
    }

}
