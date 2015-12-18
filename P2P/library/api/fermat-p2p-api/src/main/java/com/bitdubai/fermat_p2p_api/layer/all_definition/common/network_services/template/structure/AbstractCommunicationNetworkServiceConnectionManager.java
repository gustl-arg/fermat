package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.communications.CommunicationNetworkServiceLocal;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.communications.CommunicationNetworkServiceRemoteAgent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.communications.IncomingMessageDao;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.communications.OutgoingMessageDao;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsVPNConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantEstablishConnectionException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.communications.AbstractCommunicationNetworkServiceConnectionManager</code>
 * <p/>
 *
 * Methods <code>buildCommunicationNetworkServiceLocal</code> and <code>buildCommunicationNetworkServiceRemoteAgent</code>
 * can ve overrided
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 31/05/15.
 * Modified by lnacosta (laion.cj91@gmail.com) on 02/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class AbstractCommunicationNetworkServiceConnectionManager<NS extends AbstractNetworkService> implements NetworkServiceConnectionManager {

    private final CommunicationsClientConnection communicationsClientConnection;
    private final PlatformComponentProfile       platformComponentProfile      ;
    private final ErrorManager                   errorManager                  ;
    private final EventManager                   eventManager                  ;
    private final IncomingMessageDao incomingMessageDao            ;
    private final OutgoingMessageDao outgoingMessageDao            ;
    private final ECCKeyPair                     identity                      ;
    private final EventSource                    eventSource                   ;
    private final PluginVersionReference         pluginVersionReference        ;

    /**
     * Holds all references to the communication network service locals
     */
    private final Map<String, CommunicationNetworkServiceLocal> communicationNetworkServiceLocalsCache;

    /**
     * Holds all references to the communication network service remote agents
     */
    private final Map<String,CommunicationNetworkServiceRemoteAgent> communicationNetworkServiceRemoteAgentsCache;
    private NS networkServicePluginRoot;

    /**
     * Constructor with parameters.
     */
    public AbstractCommunicationNetworkServiceConnectionManager(
            NS networkServicePluginRoot,
            final PlatformComponentProfile       platformComponentProfile      ,
                                                                final ECCKeyPair                     identity                      ,
                                                                final CommunicationsClientConnection communicationsClientConnection,
                                                                final Database                       dataBase                      ,
                                                                final ErrorManager                   errorManager                  ,
                                                                final EventManager                   eventManager                  ,
                                                                final EventSource                    eventSource                   ,
                                                                final PluginVersionReference         pluginVersionReference        ) {

        super();
        this.platformComponentProfile       = platformComponentProfile      ;
        this.identity                       = identity                      ;
        this.communicationsClientConnection = communicationsClientConnection;
        this.errorManager                   = errorManager                  ;
        this.eventManager                   = eventManager                  ;
        this.eventSource                    = eventSource                   ;
        this.pluginVersionReference         = pluginVersionReference        ;
        this.networkServicePluginRoot = networkServicePluginRoot;

        this.incomingMessageDao = new IncomingMessageDao(dataBase);
        this.outgoingMessageDao = new OutgoingMessageDao(dataBase);

        this.communicationNetworkServiceLocalsCache       = new ConcurrentHashMap<>();
        this.communicationNetworkServiceRemoteAgentsCache = new ConcurrentHashMap<>();
    }


    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager# connectTo(PlatformComponentProfile)
     */
    @Override
    public final void connectTo(final PlatformComponentProfile remotePlatformComponentProfile) {

        try {

            /*
             * ask to the communicationLayerManager to connect to other network service
             */
            communicationsClientConnection.requestVpnConnection(platformComponentProfile, remotePlatformComponentProfile);

        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

    }

    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager#connectTo(PlatformComponentProfile, PlatformComponentProfile, PlatformComponentProfile)
     */
    @Override
    public final void connectTo(final PlatformComponentProfile applicantParticipant   ,
                                final PlatformComponentProfile applicantNetworkService,
                                final PlatformComponentProfile remoteParticipant      ) throws CantEstablishConnectionException {

        /*
         * ask to the communicationLayerManager to connect to other network service
         */
        communicationsClientConnection.requestDiscoveryVpnConnection(
                applicantParticipant,
                applicantNetworkService,
                remoteParticipant
        );

    }

    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager#closeConnection(String)
     */
    @Override
    public final void closeConnection(final String remoteNetworkServicePublicKey) {
        //Remove the instance and stop his threads
        if(communicationNetworkServiceLocalsCache.containsKey(remoteNetworkServicePublicKey)) {
            communicationNetworkServiceRemoteAgentsCache.remove(remoteNetworkServicePublicKey).stop();
            communicationNetworkServiceLocalsCache.remove(remoteNetworkServicePublicKey);
        }
    }

    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager#closeAllConnection()
     */
    @Override
    public final void closeAllConnection() {

        //Remove the instance and stop his threads
        for (final Map.Entry<String,CommunicationNetworkServiceRemoteAgent> entry : communicationNetworkServiceRemoteAgentsCache.entrySet())
            entry.getValue().stop();

        communicationNetworkServiceRemoteAgentsCache.clear();
        communicationNetworkServiceLocalsCache.clear();
    }

    /**
     * Handles events that indicate a connection to been established between two
     * network services and prepares all objects to work with this new connection
     *
     * @param remoteComponentProfile
     */
    public final void handleEstablishedRequestedNetworkServiceConnection(PlatformComponentProfile remoteComponentProfile) {

        try {

            /*
             * Get the active connection
             */
            CommunicationsVPNConnection communicationsVPNConnection = communicationsClientConnection.getCommunicationsVPNConnectionStablished(platformComponentProfile.getNetworkServiceType(), remoteComponentProfile);

            //Validate the connection
            if (communicationsVPNConnection != null &&
                    communicationsVPNConnection.isActive()) {

                 /*
                 * Instantiate the local reference
                 */
                CommunicationNetworkServiceLocal communicationNetworkServiceLocal = buildCommunicationNetworkServiceLocal(remoteComponentProfile);

                /*
                 * Instantiate the remote reference
                 */
                CommunicationNetworkServiceRemoteAgent communicationNetworkServiceRemoteAgent = buildCommunicationNetworkServiceRemoteAgent(communicationsVPNConnection);

                /*
                 * Register the observer to the observable agent
                 */
                communicationNetworkServiceRemoteAgent.addObserver(communicationNetworkServiceLocal);

                /*
                 * Start the service thread
                 */
                communicationNetworkServiceRemoteAgent.start();

                /*
                 * Add to the cache
                 */
                communicationNetworkServiceLocalsCache.put(remoteComponentProfile.getIdentityPublicKey(), communicationNetworkServiceLocal);
                communicationNetworkServiceRemoteAgentsCache.put(remoteComponentProfile.getIdentityPublicKey(), communicationNetworkServiceRemoteAgent);

            }

        } catch (final Exception e) {
            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    /**
     * (non-javadoc)
     * @see NetworkServiceConnectionManager#getNetworkServiceLocalInstance(String)
     */
    @Override
    public final CommunicationNetworkServiceLocal getNetworkServiceLocalInstance(final String remoteNetworkServicePublicKey) {

        //return the instance
        return communicationNetworkServiceLocalsCache.get(remoteNetworkServicePublicKey);
    }

    /**
     * Pause the manager
     */
    public final void pause() {

        //Remove the instance and stop his threads
        for (final Map.Entry<String,CommunicationNetworkServiceRemoteAgent> entry : communicationNetworkServiceRemoteAgentsCache.entrySet())
            entry.getValue().pause();

    }

    public final ECCKeyPair getIdentity() {
        return identity;
    }

    /**
     * Resume the manager
     */
    public final void resume() {

        //Remove the instance and stop his threads
        for (final Map.Entry<String,CommunicationNetworkServiceRemoteAgent> entry : communicationNetworkServiceRemoteAgentsCache.entrySet())
            entry.getValue().resume();
    }

    protected CommunicationNetworkServiceLocal buildCommunicationNetworkServiceLocal(final PlatformComponentProfile remoteComponentProfile) {
        //TODO: Leon tenes que pasarle la instancia del network service plugin root acá
        return new CommunicationNetworkServiceLocal(remoteComponentProfile, errorManager, eventManager, outgoingMessageDao,platformComponentProfile.getNetworkServiceType(),networkServicePluginRoot);

    }

    protected CommunicationNetworkServiceRemoteAgent buildCommunicationNetworkServiceRemoteAgent(final CommunicationsVPNConnection communicationsVPNConnection) {
        //TODO: Leon tenes que pasarle la instancia del network service plugin root acá
        return new CommunicationNetworkServiceRemoteAgent(networkServicePluginRoot,identity, communicationsVPNConnection, errorManager, eventManager, incomingMessageDao, outgoingMessageDao);

    }

}