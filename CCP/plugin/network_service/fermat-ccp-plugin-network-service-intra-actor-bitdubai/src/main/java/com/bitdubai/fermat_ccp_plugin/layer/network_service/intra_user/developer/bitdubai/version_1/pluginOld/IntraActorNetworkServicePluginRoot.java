/*
 * @#TemplateNetworkServicePluginRoot.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.pluginOld;

import android.util.Base64;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
<<<<<<< HEAD
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantCreateNotificationException;
=======
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantCreateNotificationException;
>>>>>>> 589579dd634da6d0edd4e49f3e34d40384772f86
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.ActorProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.NotificationDescriptor;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.CantAskIntraUserForAcceptanceException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.CantConfirmNotificationException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.CantGetNotificationsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorAcceptIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorCancellingIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorDenyConnectingIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorDisconnectingIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorInIntraUserSearchException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorSearchingCacheSuggestionsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorSearchingSuggestionsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserNotification;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraActorNetworkServiceDataBaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraActorNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IncomingNotificationDao;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.OutgoingNotificationDao;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.pluginOld.communication.ClientConnectionCloseNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.pluginOld.communication.ClientConnectionLooseNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.pluginOld.communication.ClientSuccessfullReconnectNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.pluginOld.communication.CompleteComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.pluginOld.communication.CompleteComponentRegistrationNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.pluginOld.communication.FailureComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.pluginOld.communication.NewSentMessageNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.pluginOld.communication.VPNConnectionCloseNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantAddIntraWalletCacheUserException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeNetworkIntraUserDataBaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeTemplateNetworkServiceDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantListIntraWalletCacheUserException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecord;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.pluginOld.structure.ActorNetworkServiceRecordedAgent;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.Identity;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.IntraActorNetworkServiceDao;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.IntraUserNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.structure.CommunicationRegistrationProcessNetworkServiceAgent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.ClientConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.VPNConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantEstablishConnectionException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRegisterComponentException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;


/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.TemplateNetworkServicePluginRoot</code> is
 * the responsible to initialize all component to work together, and hold all resources they needed.
 * <p/>
 * <p/>
 * Created by Roberto Requena - (rrequena) on 21/07/15.
 *
 * @version 1.0
 */

public class IntraActorNetworkServicePluginRoot extends AbstractPlugin implements
        IntraUserManager,
        NetworkService,
        LogManagerForDevelopers,
        DatabaseManagerForDevelopers {

    /**
     * buen
     * Represent the EVENT_SOURCE
     */
    public final static EventSource EVENT_SOURCE = EventSource.NETWORK_SERVICE_INTRA_ACTOR;

    /**
     * Represent the newLoggingLevel
     */
    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    /**
     * Represent the platformComponentProfilePluginRoot
     */
    private PlatformComponentProfile platformComponentProfilePluginRoot;

    /**
     * Represent the platformComponentType
     */
    private PlatformComponentType platformComponentType;

    /**
     * Represent the networkServiceType
     */
    private NetworkServiceType networkServiceType;

    /**
     * Represent the name
     */
    private String name;

    /**
     * Represent the alias
     */
    private String alias;

    /**
     * Represent the extraData
     */
    private String extraData;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    private Broadcaster broadcaster;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededPluginReference(platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION, plugin = Plugins.WS_CLOUD_CLIENT)
    private WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;

    /**
     * Hold the listeners references
     */
    private List<FermatEventListener> listenersAdded;

    /**
     * Represent the communicationNetworkServiceConnectionManager
     */
    private CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;

    /**
     * Represent the dataBase
     */
    private Database dataBaseCommunication;

    private Database dataBase;


    /**
     * Represent the identity
     */
    private ECCKeyPair identity;

    /**
     * Represent the register
     */
    private boolean register;

    /**
     * Represent the communicationRegistrationProcessNetworkServiceAgent
     */
    private CommunicationRegistrationProcessNetworkServiceAgent communicationRegistrationProcessNetworkServiceAgent;

    /**
     * Represent the remoteNetworkServicesRegisteredList
     */
    private CopyOnWriteArrayList<PlatformComponentProfile> remoteNetworkServicesRegisteredList;

    /**
     * Represent the communicationNetworkServiceDeveloperDatabaseFactory
     */
    private CommunicationNetworkServiceDeveloperDatabaseFactory communicationNetworkServiceDeveloperDatabaseFactory;

    /**
     * Agent
     */
    private ActorNetworkServiceRecordedAgent actorNetworkServiceRecordedAgent;

    /**
     * cacha identities to register
     */
    private List<PlatformComponentProfile> actorsToRegisterCache;

    /**
     * Connections arrived
     */
    private AtomicBoolean connectionArrived;

    /**
     * Represent the flag to start only once
     */
    private AtomicBoolean flag = new AtomicBoolean(false);

    /**
     * DAO
     */
    private IncomingNotificationDao incomingNotificationsDao;
    private OutgoingNotificationDao outgoingNotificationDao;

    private IntraActorNetworkServiceDao intraActorNetworkServiceDao;

    private long reprocessTimer =  300000; //five minutes

    private  Timer timer = new Timer();

    /**
     * Constructor
     */
    public IntraActorNetworkServicePluginRoot() {
        super(new PluginVersionReference(new Version()));
        this.listenersAdded = new ArrayList<>();
        this.platformComponentType = PlatformComponentType.NETWORK_SERVICE;
        this.networkServiceType = NetworkServiceType.INTRA_USER;
        this.name = "Intra actor Network Service";
        this.alias = "IntraActorNetworkService";
        this.extraData = null;
        this.actorsToRegisterCache = new ArrayList<>();
        this.remoteNetworkServicesRegisteredList = new CopyOnWriteArrayList<>();
    }

    /**
     * Static method to get the logging level from any class under root.
     *
     * @param className
     * @return
     */
    public static LogLevel getLogLevelByClass(String className) {
        try {

            String[] correctedClass = className.split((Pattern.quote("$")));
            return IntraActorNetworkServicePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    /**
     * This method validate is all required resource are injected into
     * the plugin root by the platform
     *
     * @throws CantStartPluginException
     */
    private void validateInjectedResources() throws CantStartPluginException {

         /*
         * If all resources are inject
         */
        if (wsCommunicationsCloudClientManager == null ||
                pluginDatabaseSystem == null ||
                errorManager == null ||
                eventManager == null) {


            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "No all required resource are injected";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;


        }

    }

    /**
     * This method initialize the communicationNetworkServiceConnectionManager.
     * IMPORTANT: Call this method only in the CommunicationRegistrationProcessNetworkServiceAgent, when execute the registration process
     * because at this moment, is create the platformComponentProfilePluginRoot for this component
     */
    public void initializeCommunicationNetworkServiceConnectionManager() {
        this.communicationNetworkServiceConnectionManager = new CommunicationNetworkServiceConnectionManager(this,platformComponentProfilePluginRoot, identity, wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(), dataBaseCommunication, errorManager, eventManager);
    }

    /**
     * Initialize the event listener and configure
     */
    private void initializeListener() {

         /*
         * Listen and handle Complete Component Registration Notification Event
         */
        FermatEventListener fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteComponentRegistrationNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * Listen and handle Complete Request List Component Registered Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteComponentConnectionRequestNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         *  failure connection
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.FAILURE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
        fermatEventListener.setEventHandler(new FailureComponentConnectionRequestNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * Listen and handle VPN Connection Close Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.VPN_CONNECTION_CLOSE);
        fermatEventListener.setEventHandler(new VPNConnectionCloseNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * Listen and handle Client Connection Close Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.CLIENT_CONNECTION_CLOSE);
        fermatEventListener.setEventHandler(new ClientConnectionCloseNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * Listen and handle Client Connection Loose Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.CLIENT_CONNECTION_LOOSE);
        fermatEventListener.setEventHandler(new ClientConnectionLooseNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * Listen and handle Client Connection Success Reconnect Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.CLIENT_SUCCESS_RECONNECT);
        fermatEventListener.setEventHandler(new ClientSuccessfullReconnectNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

         /*
         * Listen and handle Complete Update Actor Profile Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_UPDATE_ACTOR_NOTIFICATION);

        //TODO: MATIAS REVISAR POR QUE HACE REFERENCIA AL OLD
       // fermatEventListener.setEventHandler(new CompleteUpdateActorNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        initializeMessagesListeners();
    }

    /**
     * Messages listeners
     */
    private void initializeMessagesListeners() {

        /**
         *Listen and handle the received messages
         */
        FermatEventListener fermatEventListener = eventManager.getNewListener(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_RECEIVE_NOTIFICATION);
        //TODO: MATIAS REVISAR POR QUE HACE REFERENCIA AL OLD
      //  fermatEventListener.setEventHandler(new NewMessagesEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /**
         * Listen and handle the sent messages
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_SENT_NOTIFICATION);
        fermatEventListener.setEventHandler(new NewSentMessageNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

    }

    /**
     * (non-Javadoc)
     *
     * @see Service#start()
     */
    @Override
    public synchronized void start() throws CantStartPluginException {

        if(!flag.getAndSet(true)) {
            if (this.serviceStatus != ServiceStatus.STARTING) {
                serviceStatus = ServiceStatus.STARTING;


                logManager.log(com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.pluginOld.IntraActorNetworkServicePluginRoot.getLogLevelByClass(this.getClass().getName()), "IntraActorNetworkServicePluginRoot - Starting", "TemplateNetworkServicePluginRoot - Starting", "TemplateNetworkServicePluginRoot - Starting");

                /*
                 * Validate required resources
                 */
                validateInjectedResources();

                try {

                    /*
                     * Create a new key pair for this execution
                     */
                    initializeClientIdentity();

                    /*
                     * Initialize the data base
                     */
                    initializeDb();


                    /*
                     * Initialize cache data base
                     */
                    initializeCacheDb();

                    /*
                     * Initialize Developer Database Factory
                     */
                    communicationNetworkServiceDeveloperDatabaseFactory = new CommunicationNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
                    communicationNetworkServiceDeveloperDatabaseFactory.initializeDatabase();

                    /*
                     * Initialize listeners
                     */
                    initializeListener();

                    /*
                     * Initialize connection manager
                     */
                    initializeCommunicationNetworkServiceConnectionManager();

                    /*
                     * Verify if the communication cloud client is active
                     */
                    if (!wsCommunicationsCloudClientManager.isDisable()) {

                        /*
                         * Construct my profile and register me
                         */
                        platformComponentProfilePluginRoot =  wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructPlatformComponentProfileFactory(getIdentityPublicKey(),
                                getAlias().toLowerCase(),
                                getName(),
                                getNetworkServiceType(),
                                getPlatformComponentType(),
                                getExtraData());
                        /*
                         * Initialize the agent and start
                         */
                        communicationRegistrationProcessNetworkServiceAgent = new CommunicationRegistrationProcessNetworkServiceAgent(this, wsCommunicationsCloudClientManager);
                        communicationRegistrationProcessNetworkServiceAgent.start();
                    }

                    //DAO
                    incomingNotificationsDao = new IncomingNotificationDao(dataBaseCommunication, this.pluginFileSystem, this.pluginId);

                    outgoingNotificationDao = new OutgoingNotificationDao(dataBaseCommunication,this.pluginFileSystem, this.pluginId);

                    intraActorNetworkServiceDao = new IntraActorNetworkServiceDao(this.dataBase, this.pluginFileSystem,this.pluginId);

                    connectionArrived = new AtomicBoolean(false);

                    // change message state to process again first time
                    reprocessMessage();

                    //declare a schedule to process waiting request message

                        this.startTimer();


                    initializeAgent();

                    /*
                     * Its all ok, set the new status
                    */
                    this.serviceStatus = ServiceStatus.STARTED;


                } catch (CantInitializeTemplateNetworkServiceDatabaseException exception) {

                    StringBuffer contextBuffer = new StringBuffer();
                    contextBuffer.append("Plugin ID: " + pluginId);
                    contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
                    contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

                    String context = contextBuffer.toString();
                    String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
                    CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
                    throw pluginStartException;

                } catch (CantInitializeNetworkIntraUserDataBaseException exception) {

                    StringBuffer contextBuffer = new StringBuffer();
                    contextBuffer.append("Plugin ID: " + pluginId);
                    contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
                    contextBuffer.append("Database Name: " + IntraActorNetworkServiceDataBaseConstants.DATA_BASE_NAME);

                    String context = contextBuffer.toString();

                    CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, "");

                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
                    throw pluginStartException;
                }

            }
        }


    }

    private void initializeClientIdentity() throws CantStartPluginException {

        System.out.println("IntraActorNetworkServicePluginRoot - Calling the method - initializeClientIdentity() ");

        try {

            System.out.println("Loading clientIdentity");

             /*
              * Load the file with the clientIdentity
              */
            PluginTextFile pluginTextFile = pluginFileSystem.getTextFile(pluginId, "private", "clientIdentity", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            String content = pluginTextFile.getContent();

            //System.out.println("content = "+content);

            identity = new ECCKeyPair(content);

        } catch (FileNotFoundException e) {

            /*
             * The file no exist may be the first time the plugin is running on this device,
             * We need to create the new clientIdentity
             */
            try {

                System.out.println("IntraActorNetworkServicePluginRoot - No previous clientIdentity finder - Proceed to create new one");

                /*
                 * Create the new clientIdentity
                 */
                identity = new ECCKeyPair();

                /*
                 * save into the file
                 */
                PluginTextFile pluginTextFile = pluginFileSystem.createTextFile(pluginId, "private", "clientIdentity", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                pluginTextFile.setContent(identity.getPrivateKey());
                pluginTextFile.persistToMedia();

            } catch (Exception exception) {
                /*
                 * The file cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                throw new CantStartPluginException(exception.getLocalizedMessage());
            }


        } catch (CantCreateFileException cantCreateFileException) {

            /*
             * The file cannot be load. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantCreateFileException);
            throw new CantStartPluginException(cantCreateFileException.getLocalizedMessage());

        }

    }

    /**
     * (non-Javadoc)
     *
     * @see Service#pause()
     */
    @Override
    public void pause() {

        /*
         * Pause
         */
        communicationNetworkServiceConnectionManager.pause();

        /*
         * Set the new status
         */
        this.serviceStatus = ServiceStatus.PAUSED;

    }

    /**
     * (non-Javadoc)
     *
     * @see Service#resume()
     */
    @Override
    public void resume() {

        /*
         * resume the managers
         */
        communicationNetworkServiceConnectionManager.resume();

        /*
         * Set the new status
         */
        this.serviceStatus = ServiceStatus.STARTED;

    }

    /**
     * (non-Javadoc)
     *
     * @see Service#stop()
     */
    @Override
    public void stop() {

        //Clear all references of the event listeners registered with the event manager.
        listenersAdded.clear();

        /*
         * Stop all connection on the managers
         */
        communicationNetworkServiceConnectionManager.closeAllConnection();

        //set to not register
        register = Boolean.FALSE;

        /*
         * Set the new status
         */
        this.serviceStatus = ServiceStatus.STOPPED;

    }

    private void initializeAgent() {

        System.out.println("IntraActorNetworkServicePluginRoot - initializeAgent() ");

        try {
           
            if (actorNetworkServiceRecordedAgent == null){


                //TODO: MATIAS REVISAR POR QUE HACE REFERENCIA AL OLD
              //  actorNetworkServiceRecordedAgent = new ActorNetworkServiceRecordedAgent(this);
                actorNetworkServiceRecordedAgent.start();

            }

        } catch (CantStartAgentException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see NetworkService#handleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile)
     */
    public void handleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile platformComponentProfileRegistered) {

        System.out.println("IntraActorNetworkServicePluginRoot - Starting method handleCompleteComponentRegistrationNotificationEvent");

        try {


            if (platformComponentProfileRegistered.getPlatformComponentType() == PlatformComponentType.COMMUNICATION_CLOUD_CLIENT && !this.register){

                if(communicationRegistrationProcessNetworkServiceAgent != null && communicationRegistrationProcessNetworkServiceAgent.getActive()){
                    communicationRegistrationProcessNetworkServiceAgent.stop();
                    communicationRegistrationProcessNetworkServiceAgent = null;
                }

                wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().registerComponentForCommunication(this.getNetworkServiceType(), platformComponentProfilePluginRoot);

            }

            if (platformComponentProfileRegistered.getPlatformComponentType() == PlatformComponentType.NETWORK_SERVICE &&
                    platformComponentProfileRegistered.getNetworkServiceType() == this.getNetworkServiceType() &&
                    platformComponentProfileRegistered.getIdentityPublicKey().equals(identity.getPublicKey())) {

                System.out.println("IntraActorNetworkServicePluginRoot - NetWork Service is Registered: " + platformComponentProfileRegistered.getAlias());
                this.register = Boolean.TRUE;
                initializeAgent();

                CommunicationsClientConnection communicationsClientConnection = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection();

                for (PlatformComponentProfile platformComponentProfile : actorsToRegisterCache) {
                    communicationsClientConnection.registerComponentForCommunication(networkServiceType, platformComponentProfile);
                    System.out.println("IntraActorNetworkServicePluginRoot - Trying to register to: " + platformComponentProfile.getAlias());
                }


            }

            if (platformComponentProfileRegistered.getPlatformComponentType() == PlatformComponentType.ACTOR_INTRA_USER) {
                System.out.println("IntraActorNetworkServicePluginRoot - New Actor registered: " + platformComponentProfileRegistered.getAlias());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void handleFailureComponentRegistrationNotificationEvent(PlatformComponentProfile networkServiceApplicant, PlatformComponentProfile remoteParticipant) {
        System.out.println("----------------------------------\n" +
                "FAILED CONNECTION WITH " + remoteParticipant.getCommunicationCloudClientIdentity() + "\n" +
                "--------------------------------------------------------");
        actorNetworkServiceRecordedAgent.connectionFailure(remoteParticipant.getIdentityPublicKey());

        //I check my time trying to send the message
        checkFailedDeliveryTime(remoteParticipant.getIdentityPublicKey());


    }

    /**
     * (non-Javadoc)
     *
     * @see NetworkService#
     */
    public void handleCompleteRequestListComponentRegisteredNotificationEvent(List<PlatformComponentProfile> platformComponentProfileRegisteredList) {

        System.out.println(" CommunicationNetworkServiceConnectionManager - Starting method handleCompleteRequestListComponentRegisteredNotificationEvent");

        /*
         * save into the cache
         */

        remoteNetworkServicesRegisteredList.addAllAbsent(platformComponentProfileRegisteredList);


        System.out.println("--------------------------------------\n" +
                "REGISTRO DE USUARIOS INTRA USER CONECTADOS");
        for (PlatformComponentProfile platformComponentProfile : platformComponentProfileRegisteredList) {
            System.out.println(platformComponentProfile.getAlias() + "\n");
        }
        System.out.println("--------------------------------------\n" +
                "FIN DE REGISTRO DE USUARIOS INTRA USER CONECTADOS");


    }

    public void handleNewSentMessageNotificationEvent(FermatMessage fermatMessage){
        try {
            ActorNetworkServiceRecord actorNetworkServiceRecord = ActorNetworkServiceRecord.fronJson(fermatMessage.getContent());

            if (actorNetworkServiceRecord.getActorProtocolState()==ActorProtocolState.DONE) {
                // close connection, sender is the destination
                System.out.println("ENTRANDO EN EL METODO PARA CERRAR LA CONEXION DEL HANDLE NEW SENT MESSAGE NOTIFICATION");
                System.out.println("ENTRO AL METODO PARA CERRAR LA CONEXION");
                //   communicationNetworkServiceConnectionManager.closeConnection(actorNetworkServiceRecord.getActorDestinationPublicKey());
                actorNetworkServiceRecordedAgent.getPoolConnectionsWaitingForResponse().remove(actorNetworkServiceRecord.getActorDestinationPublicKey());
            }

            //done message type receive
            if(actorNetworkServiceRecord.getNotificationDescriptor() == NotificationDescriptor.RECEIVED) {
                actorNetworkServiceRecord.setActorProtocolState(ActorProtocolState.DONE);
                getOutgoingNotificationDao().update(actorNetworkServiceRecord);
                actorNetworkServiceRecordedAgent.getPoolConnectionsWaitingForResponse().remove(actorNetworkServiceRecord.getActorDestinationPublicKey());
            }

            System.out.println("SALIENDO DEL HANDLE NEW SENT MESSAGE NOTIFICATION");

        } catch (Exception e) {
            //quiere decir que no estoy reciviendo metadata si no una respuesta
            System.out.println("EXCEPCION DENTRO DEL PROCCESS EVENT");
            e.printStackTrace();

        }
    }


    /**
     * (non-Javadoc)
     *
     * @see NetworkService#handleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile, PlatformComponentProfile)
     */
    public void handleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile applicantComponentProfile, PlatformComponentProfile remoteComponentProfile) {

        System.out.println(" IntraActorNetworkServicePluginRoot - Starting method handleCompleteComponentConnectionRequestNotificationEvent");

        /*
         * Tell the manager to handler the new connection stablished
         */
        communicationNetworkServiceConnectionManager.handleEstablishedRequestedNetworkServiceConnection(remoteComponentProfile);


    }

    public void handleNewMessages(FermatMessage fermatMessage) {
        try {
            System.out.println("----------------------------\n" +
                    "CONVIERTIENDO MENSAJE ENTRANTE A GSON:" + fermatMessage.toJson()
                    + "\n-------------------------------------------------");

            ActorNetworkServiceRecord actorNetworkServiceRecord = ActorNetworkServiceRecord.fronJson(fermatMessage.getContent());

            //NotificationDescriptor intraUserNotificationDescriptor = NotificationDescriptor.getByCode(jsonObject.get(JsonObjectConstants.MESSAGE_TYPE).getAsString());
            switch (actorNetworkServiceRecord.getNotificationDescriptor()) {
                case ASKFORACCEPTANCE:
                    System.out.println("----------------------------\n" +
                            "MENSAJE LLEGO EXITOSAMENTE:" + actorNetworkServiceRecord.getActorSenderAlias()
                            + "\n-------------------------------------------------");

                    actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_RECEIVE);

                    System.out.println("----------------------------\n" +
                            "CREANDO REGISTRO EN EL INCOMING NOTIFICATION DAO:"
                            + "\n-------------------------------------------------");

                    actorNetworkServiceRecord.setFlagReadead(false);
                    getIncomingNotificationsDao().createNotification(actorNetworkServiceRecord);

                    //NOTIFICATION LAUNCH

                    launchIncomingRequestConnectionNotificationEvent(actorNetworkServiceRecord);

                    respondReceiveAndDoneCommunication(actorNetworkServiceRecord);
                    break;
                case ACCEPTED:
                    //TODO: ver si me conviene guardarlo en el outogoing DAO o usar el incoming para las que llegan directamente
                    actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.ACCEPTED);
                    actorNetworkServiceRecord.changeState(ActorProtocolState.DONE);
                    getOutgoingNotificationDao().update(actorNetworkServiceRecord);

                    //create incoming notification

                    actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_RECEIVE);
                    actorNetworkServiceRecord.setFlagReadead(false);
                    getIncomingNotificationsDao().createNotification(actorNetworkServiceRecord);
                    System.out.println("----------------------------\n" +
                            "MENSAJE ACCEPTED LLEGÓ BIEN: CASE ACCEPTED" + actorNetworkServiceRecord.getActorSenderAlias()
                            + "\n-------------------------------------------------");


                    respondReceiveAndDoneCommunication(actorNetworkServiceRecord);

                    break;


                case RECEIVED:

                    //launchIncomingRequestConnectionNotificationEvent(actorNetworkServiceRecord);
                    System.out.println("----------------------------\n" +
                            "INTRA ACTOR NETWORK SERVICE" +
                            "THE RECORD WAS CHANGE TO THE STATE OF DELIVERY" + actorNetworkServiceRecord.getActorSenderAlias()
                            + "\n-------------------------------------------------");
                    //TODO: ver porqué no encuentra el id para cambiarlo
                    if(actorNetworkServiceRecord.getResponseToNotificationId()!=null)
                        getOutgoingNotificationDao().changeProtocolState(actorNetworkServiceRecord.getResponseToNotificationId(), ActorProtocolState.DONE);

                    // close connection, sender is the destination
                    System.out.println("----------------------------\n" +
                            "INTRA ACTOR NETWORK SERVICE" +
                            "THE CONNECTION BECAUSE THE ACTOR PROTOCOL STATE" +
                            "WAS CHANGE TO DONE" + actorNetworkServiceRecord.getActorSenderAlias()
                            + "\n-------------------------------------------------");

                    communicationNetworkServiceConnectionManager.closeConnection(actorNetworkServiceRecord.getActorSenderPublicKey());
                    actorNetworkServiceRecordedAgent.getPoolConnectionsWaitingForResponse().remove(actorNetworkServiceRecord.getActorSenderPublicKey());
                    System.out.println("----------------------------\n" +
                            "INTRA ACTOR NETWORK SERVICE" +
                            "THE CONNECTION WAS CLOSED AND THE AWAITING POOL CLEARED." + actorNetworkServiceRecord.getActorSenderAlias()
                            + "\n-------------------------------------------------");


                    break;

                case DENIED:

                    actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.DENIED);
                    actorNetworkServiceRecord.changeState(ActorProtocolState.DONE);
                    getOutgoingNotificationDao().update(actorNetworkServiceRecord);

                    actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_RECEIVE);
                    actorNetworkServiceRecord.setFlagReadead(false);
                    getIncomingNotificationsDao().createNotification(actorNetworkServiceRecord);
                    System.out.println("----------------------------\n" +
                            "MENSAJE DENIED LLEGÓ BIEN: CASE DENIED" + actorNetworkServiceRecord.getActorDestinationPublicKey()
                            + "\n-------------------------------------------------");


                    respondReceiveAndDoneCommunication(actorNetworkServiceRecord);

                    break;

                case DISCONNECTED:

                    actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.DISCONNECTED);
                    actorNetworkServiceRecord.changeState(ActorProtocolState.DONE);
                    getOutgoingNotificationDao().update(actorNetworkServiceRecord);

                    actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_RECEIVE);
                    actorNetworkServiceRecord.setFlagReadead(false);
                    getIncomingNotificationsDao().createNotification(actorNetworkServiceRecord);
                    System.out.println("----------------------------\n" +
                            "MENSAJE DISCONNECTED LLEGÓ BIEN: CASE DISCONNECTED" + actorNetworkServiceRecord.getActorSenderAlias()
                            + "\n-------------------------------------------------");


                    respondReceiveAndDoneCommunication(actorNetworkServiceRecord);

                    break;

                default:

                    break;

            }

        } catch (Exception e) {
            //quiere decir que no estoy reciviendo metadata si no una respuesta
            e.printStackTrace();

        }

        System.out.println("---------------------------\n" +
                "Llegaron mensajes!!!!\n" +
                "-----------------------------------------");
    }

    // respond receive and done notification
    private void respondReceiveAndDoneCommunication(ActorNetworkServiceRecord actorNetworkServiceRecord) {


        actorNetworkServiceRecord = changeActor(actorNetworkServiceRecord);
        try {
            UUID newNotificationID = UUID.randomUUID();
            long currentTime = System.currentTimeMillis();
            ActorProtocolState protocolState = ActorProtocolState.PROCESSING_SEND;
            actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.RECEIVED);
            outgoingNotificationDao.createNotification(
                    newNotificationID,
                    actorNetworkServiceRecord.getActorSenderPublicKey(),
                    actorNetworkServiceRecord.getActorSenderType(),
                    actorNetworkServiceRecord.getActorDestinationPublicKey(),
                    actorNetworkServiceRecord.getActorSenderAlias(),
                    actorNetworkServiceRecord.getActorSenderPhrase(),
                    actorNetworkServiceRecord.getActorSenderProfileImage(),
                    actorNetworkServiceRecord.getActorDestinationType(),
                    actorNetworkServiceRecord.getNotificationDescriptor(),
                    currentTime,
                    protocolState,
                    false,
                    1,
                    actorNetworkServiceRecord.getId()
            );
        } catch (CantCreateNotificationException e) {
            e.printStackTrace();
        }

    }

    private ActorNetworkServiceRecord changeActor(ActorNetworkServiceRecord actorNetworkServiceRecord) {
        // change actor
        String actorDestination = actorNetworkServiceRecord.getActorDestinationPublicKey();
        actorNetworkServiceRecord.setActorDestinationPublicKey(actorNetworkServiceRecord.getActorSenderPublicKey());
        actorNetworkServiceRecord.setActorSenderPublicKey(actorDestination);
        return actorNetworkServiceRecord;
    }

    private void launchIncomingRequestConnectionNotificationEvent(ActorNetworkServiceRecord actorNetworkServiceRecord) {
      /*  FermatEvent platformEvent = eventManager.getNewEvent(EventType.INCOMING_INTRA_ACTOR_REQUUEST_CONNECTION_NOTIFICATION);
        IncomingActorRequestConnectionNotificationEvent incomingActorRequestConnectionNotificationEvent = (IncomingActorRequestConnectionNotificationEvent) platformEvent;
        incomingActorRequestConnectionNotificationEvent.setSource(EventSource.NETWORK_SERVICE_INTRA_ACTOR);
        incomingActorRequestConnectionNotificationEvent.setActorId(actorNetworkServiceRecord.getActorSenderPublicKey());
        incomingActorRequestConnectionNotificationEvent.setActorName(actorNetworkServiceRecord.getActorSenderAlias());
        incomingActorRequestConnectionNotificationEvent.setActorType(Actors.INTRA_USER);
        eventManager.raiseEvent(platformEvent);*/
    }

    private void checkFailedDeliveryTime(String destinationPublicKey)
    {
        try{

            List<ActorNetworkServiceRecord> actorNetworkServiceRecordList = outgoingNotificationDao.getNotificationByDestinationPublicKey(destinationPublicKey);

            //if I try to send more than 5 times I put it on hold
            for (ActorNetworkServiceRecord record : actorNetworkServiceRecordList) {

                if(!record.getActorProtocolState().getCode().equals(ActorProtocolState.WAITING_RESPONSE.getCode()))
                {
                    if(record.getSentCount() > 10 )
                    {
                      //  if(record.getSentCount() > 20)
                      //  {
                            //reprocess at two hours
                          //  reprocessTimer =  2 * 3600 * 1000;
                       // }

                        record.setActorProtocolState(ActorProtocolState.WAITING_RESPONSE);
                        record.setSentCount(1);
                        //update state and process again later

                        outgoingNotificationDao.update(record);
                    }
                    else
                    {
                        record.setSentCount(record.getSentCount() + 1);
                        outgoingNotificationDao.update(record);
                    }
                }
                else
                {
                    //I verify the number of days I'm around trying to send if it exceeds three days I delete record

                    long sentDate = record.getSentDate();
                    long currentTime = System.currentTimeMillis();
                    long dif = currentTime - sentDate;

                    double dias = Math.floor(dif / (1000 * 60 * 60 * 24));

                    if((int) dias > 3)
                    {
                        //notify the user does not exist to intra user actor plugin
                        record.changeDescriptor(NotificationDescriptor.INTRA_USER_NOT_FOUND);
                        incomingNotificationsDao.createNotification(record);

                        outgoingNotificationDao.delete(record.getId());
                    }

                }

            }


        }
        catch(Exception e)
        {
            System.out.println("INTRA USER NS EXCEPCION VERIFICANDO WAIT MESSAGE");
            e.printStackTrace();
        }

    }


    private String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return format.format(date);
    }
    /**
     * Get the IdentityPublicKey
     *
     * @return String
     */
    public String getIdentityPublicKey() {
        return this.identity.getPublicKey();
    }

    /**
     * (non-Javadoc)
     *
     * @see NetworkService#getPlatformComponentProfilePluginRoot()
     */
    public PlatformComponentProfile getPlatformComponentProfilePluginRoot() {
        return platformComponentProfilePluginRoot;
    }

    /**
     * Set the PlatformComponentProfile
     *
     * @param platformComponentProfilePluginRoot
     */
    public void setPlatformComponentProfilePluginRoot(PlatformComponentProfile platformComponentProfilePluginRoot) {
        this.platformComponentProfilePluginRoot = platformComponentProfilePluginRoot;
    }

    /**
     * (non-Javadoc)
     *
     * @see NetworkService#getPlatformComponentType()
     */
    @Override
    public PlatformComponentType getPlatformComponentType() {
        return platformComponentType;
    }

    /**
     * (non-Javadoc)
     *
     * @see NetworkService#getNetworkServiceType()
     */
    @Override
    public NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    /**
     * Get is Register
     *
     * @return boolean
     */
    @Override
    public boolean isRegister() {
        return register;
    }

    /**
     * (non-javadoc)
     *
     * @see NetworkService#getNetworkServiceConnectionManager()
     */
    public NetworkServiceConnectionManager getNetworkServiceConnectionManager() {
        return communicationNetworkServiceConnectionManager;
    }

    /**
     * (non-javadoc)
     *
     * @see NetworkService#constructDiscoveryQueryParamsFactory(PlatformComponentType, NetworkServiceType,  String,String, Location, Double, String, String, Integer, Integer, PlatformComponentType, NetworkServiceType)
     */
    @Override
    public DiscoveryQueryParameters constructDiscoveryQueryParamsFactory(PlatformComponentType platformComponentType, NetworkServiceType networkServiceType, String alias,String identityPublicKey, Location location, Double distance, String name, String extraData, Integer firstRecord, Integer numRegister, PlatformComponentType fromOtherPlatformComponentType, NetworkServiceType fromOtherNetworkServiceType) {
        return wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructDiscoveryQueryParamsFactory(platformComponentType, networkServiceType, alias,  identityPublicKey, location, distance, name, extraData, firstRecord, numRegister, fromOtherPlatformComponentType, fromOtherNetworkServiceType);
    }

    /**
     * (non-javadoc)
     *
     * @see NetworkService#getRemoteNetworkServicesRegisteredList()
     */
    public List<PlatformComponentProfile> getRemoteNetworkServicesRegisteredList() {
        return remoteNetworkServicesRegisteredList;
    }

    /**
     * (non-javadoc)
     *
     * @see NetworkService#requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters)
     */
    public void requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters discoveryQueryParameters) {

        System.out.println(" IntraActorNetworkServicePluginRoot - requestRemoteNetworkServicesRegisteredList");

         /*
         * Request the list of component registers
         */
        try {

            wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().requestListComponentRegistered(platformComponentProfilePluginRoot, discoveryQueryParameters);

        } catch (CantRequestListException e) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Plugin was not registered";

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

        }

    }

    /**
     * Handles the events VPNConnectionCloseNotificationEvent
     * @param fermatEvent
     */
    @Override
    public void handleVpnConnectionCloseNotificationEvent(FermatEvent fermatEvent) {

        System.out.println("IntraActorNetworkServicePluginRoot - handleVpnConnectionCloseNotificationEvent");

        if(fermatEvent instanceof VPNConnectionCloseNotificationEvent){

            VPNConnectionCloseNotificationEvent vpnConnectionCloseNotificationEvent = (VPNConnectionCloseNotificationEvent) fermatEvent;

            if(vpnConnectionCloseNotificationEvent.getNetworkServiceApplicant() == getNetworkServiceType()){

                String remotePublicKey = vpnConnectionCloseNotificationEvent.getRemoteParticipant().getIdentityPublicKey();
                if(communicationNetworkServiceConnectionManager != null) {
                    System.out.println("ENTRANDO EN EL METODO PARA CERRAR LA CONEXION DEL handleVpnConnectionCloseNotificationEvent");
                    System.out.println("ENTRO AL METODO PARA CERRAR LA CONEXION");
                    communicationNetworkServiceConnectionManager.closeConnection(remotePublicKey);

                }

                // close connection, sender is the destination
                if(actorNetworkServiceRecordedAgent!=null) actorNetworkServiceRecordedAgent.getPoolConnectionsWaitingForResponse().remove(remotePublicKey);

                //reprocess messages
                if(!((VPNConnectionCloseNotificationEvent) fermatEvent).isCloseNormal())
                    reprocessMessage(remotePublicKey);
            }

        }

    }

    /**
     * Handles the events ClientConnectionCloseNotificationEvent
     * @param fermatEvent
     */
    @Override
    public void handleClientConnectionCloseNotificationEvent(FermatEvent fermatEvent) {

        System.out.println("IntraActorNetworkServicePluginRoot - handleClientConnectionCloseNotificationEvent");

        if(fermatEvent instanceof ClientConnectionCloseNotificationEvent){
            try {

                System.out.println("----------------------------\n" +
                        "CHANGING OUTGOING NOTIFICATIONS RECORDS " +
                        "THAT HAVE THE PROTOCOL STATE SET TO SENT" +
                        "TO PROCESSING SEND IN ORDER TO ENSURE PROPER RECEPTION :"
                        + "\n-------------------------------------------------");

                this.register = Boolean.FALSE;

                reprocessMessage();

                if(communicationNetworkServiceConnectionManager != null) {
                    communicationNetworkServiceConnectionManager.closeAllConnection();
                    communicationNetworkServiceConnectionManager.stop();
                }

            }catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /*
     * Handles the events ClientConnectionLooseNotificationEvent
     */
    @Override
    public void handleClientConnectionLooseNotificationEvent(FermatEvent fermatEvent) {

        System.out.println("IntraActorNetworkServicePluginRoot - handleClientConnectionLooseNotificationEvent");

        try {

            if(communicationNetworkServiceConnectionManager != null) {
                communicationNetworkServiceConnectionManager.stop();
            }

            this.register = Boolean.FALSE;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * Handles the events ClientSuccessfullReconnectNotificationEvent
     */
    @Override
    public void handleClientSuccessfullReconnectNotificationEvent(FermatEvent fermatEvent) {

        System.out.println("IntraActorNetworkServicePluginRoot - handleClientSuccessfullReconnectNotificationEvent");

        try {

            if (communicationNetworkServiceConnectionManager != null){
                communicationNetworkServiceConnectionManager.restart();
            }

            initializeAgent();

            /*
             * Mark as register
             */
            this.register = Boolean.TRUE;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void handleCompleteUpdateActorNotificationEvent(PlatformComponentProfile platformComponentProfile){
        /*
         * Recieve Notification that Actor was update sucessfully
         */

    }

    /**
     * Get the Name
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Get the Alias
     *
     * @return String
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Get the ExtraData
     *
     * @return String
     */
    public String getExtraData() {
        return extraData;
    }

    /**
     * Get the New Received Message List
     *
     * @return List<FermatMessage>
     */
    public List<FermatMessage> getNewReceivedMessageList() throws CantReadRecordDataBaseException {

        Map<String, Object> filters = new HashMap<>();
        filters.put(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_FIRST_KEY_COLUMN, MessagesStatus.NEW_RECEIVED.getCode());

        return communicationNetworkServiceConnectionManager.getIncomingMessageDao().findAll(filters);
    }

    /*
     * IntraUserManager Interface method implementation
     */

    /**
     * Mark the message as read
     *
     * @param fermatMessage
     */
    public void markAsRead(FermatMessage fermatMessage) throws CantUpdateRecordDataBaseException {

        ((FermatMessageCommunication) fermatMessage).setFermatMessagesStatus(FermatMessagesStatus.READ);
        communicationNetworkServiceConnectionManager.getIncomingMessageDao().update(fermatMessage);
    }

    @Override
    public List<IntraUserInformation> searchIntraUserByName(String intraUserAlias) throws ErrorInIntraUserSearchException {

        List<IntraUserInformation> intraUserList = new ArrayList<IntraUserInformation>();


        return intraUserList;
    }

    @Override
    public List<IntraUserInformation> getIntraUsersSuggestions(int max, int offset) throws ErrorSearchingSuggestionsException {

        final List<IntraUserInformation> lstIntraUser = new ArrayList<>();

        try {

            /* This is for test and example of how to use
                    * Construct the filter
            */
            DiscoveryQueryParameters discoveryQueryParameters = constructDiscoveryQueryParamsFactory(
                    PlatformComponentType.ACTOR_INTRA_USER, //PlatformComponentType you want to find
                    NetworkServiceType.UNDEFINED,     //NetworkServiceType you want to find
                    null,                     // alias
                    null,                     // identityPublicKey
                    null,                     // location
                    null,                     // distance
                    null,                     // name
                    null,                     // extraData
                    null,                     // offset
                    null,                     // max
                    null,                     // fromOtherPlatformComponentType, when use this filter apply the identityPublicKey
                    null
            );                    // fromOtherNetworkServiceType,    when use this filter apply the identityPublicKey

            List<PlatformComponentProfile> list = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().requestListComponentRegistered(discoveryQueryParameters);

            for (PlatformComponentProfile platformComponentProfile : list) {

                //get extra data

                String actorPhrase = "";
                String profileImage = "";
                if(!platformComponentProfile.getExtraData().equals(""))
                {
                    try {
                        JsonParser jParser = new JsonParser();
                        JsonObject jsonObject = jParser.parse(platformComponentProfile.getExtraData()).getAsJsonObject();

                        actorPhrase = jsonObject.get("PHRASE").getAsString();
                        profileImage  = jsonObject.get("AVATAR_IMG").getAsString();
                    }
                    catch(Exception e){
                        profileImage = platformComponentProfile.getExtraData();
                    }


                }

                byte[] imageByte = Base64.decode(profileImage, Base64.DEFAULT);
                lstIntraUser.add(new IntraUserNetworkService(platformComponentProfile.getIdentityPublicKey(), imageByte, platformComponentProfile.getAlias(),actorPhrase));
            }

            //Create a thread to save intra user cache list

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try
                    {
                        intraActorNetworkServiceDao.saveIntraUserCache(lstIntraUser);
                    } catch (CantAddIntraWalletCacheUserException e) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);

                    }
                }
            },"Thread Cache");

            thread.start();

        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);

        }

        return lstIntraUser;
    }

    @Override
    public List<IntraUserInformation> getCacheIntraUsersSuggestions(int max, int offset) throws ErrorSearchingCacheSuggestionsException {
        try {
            return intraActorNetworkServiceDao.listIntraUserCache(max,offset);

        } catch (CantListIntraWalletCacheUserException e) {
            throw new ErrorSearchingCacheSuggestionsException("CAN'T GET INTRA USER CACHE LIST",e,"","error get table records");
        }
    }

    @Override
    public void askIntraUserForAcceptance(final String intraUserSelectedPublicKey,
                                          final String intraUserSelectedName,
                                          final Actors senderType,
                                          final String intraUserToAddName,
                                          final String intraUserToAddPhrase,
                                          final String intraUserToAddPublicKey,
                                          final Actors destinationType,
                                          final byte[] myProfileImage) throws CantAskIntraUserForAcceptanceException {

        try {

            UUID newNotificationID = UUID.randomUUID();
            NotificationDescriptor notificationDescriptor = NotificationDescriptor.ASKFORACCEPTANCE;
            long currentTime = System.currentTimeMillis();
            ActorProtocolState protocolState = ActorProtocolState.PROCESSING_SEND;

            outgoingNotificationDao.createNotification(
                    newNotificationID,
                    intraUserSelectedPublicKey,
                    senderType,
                    intraUserToAddPublicKey,
                    intraUserSelectedName,
                    intraUserToAddPhrase,
                    myProfileImage,
                    destinationType,
                    notificationDescriptor,
                    currentTime,
                    protocolState,
                    false, 1,
                    null
            );

        } catch (final CantCreateNotificationException e) {

            reportUnexpectedError(e);
            throw new CantAskIntraUserForAcceptanceException(e, "intra actor network service", "database corrupted");
        } catch (final Exception e) {

            reportUnexpectedError(e);
            throw new CantAskIntraUserForAcceptanceException(e, "intra actor network service", "Unhandled error.");
        }

    }

    @Override
    public void acceptIntraUser(String intraUserLoggedInPublicKey, String intraUserToAddPublicKey) throws ErrorAcceptIntraUserException {

        try {


            ActorNetworkServiceRecord actorNetworkServiceRecord = incomingNotificationsDao.changeIntraUserNotificationDescriptor(intraUserToAddPublicKey, NotificationDescriptor.ACCEPTED, ActorProtocolState.PENDING_ACTION);

            actorNetworkServiceRecord.setActorDestinationPublicKey(intraUserToAddPublicKey);
            actorNetworkServiceRecord.setActorSenderPublicKey(intraUserLoggedInPublicKey);

            actorNetworkServiceRecord.setActorSenderAlias(null);

            actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.ACCEPTED);

            actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_SEND);

            outgoingNotificationDao.createNotification(
                    UUID.randomUUID(),
                    actorNetworkServiceRecord.getActorSenderPublicKey(),
                    actorNetworkServiceRecord.getActorSenderType(),
                    actorNetworkServiceRecord.getActorDestinationPublicKey(),
                    actorNetworkServiceRecord.getActorSenderAlias(),
                    actorNetworkServiceRecord.getActorSenderPhrase(),
                    actorNetworkServiceRecord.getActorSenderProfileImage(),
                    actorNetworkServiceRecord.getActorDestinationType(),
                    actorNetworkServiceRecord.getNotificationDescriptor(),
                    System.currentTimeMillis(),
                    actorNetworkServiceRecord.getActorProtocolState(),
                    false,
                    1,
                    actorNetworkServiceRecord.getResponseToNotificationId()
            );


        } catch (Exception e) {
            throw new ErrorAcceptIntraUserException("ERROR INTRA ACTOR NS WHEN ACCEPTING CONNECTION TO INTRAUSER", e, "", "Generic Exception");
        }
    }

    @Override
    public void denyConnection(String intraUserLoggedInPublicKey, String intraUserToRejectPublicKey) throws ErrorDenyConnectingIntraUserException {

        try {

            ActorNetworkServiceRecord actorNetworkServiceRecord = incomingNotificationsDao.changeIntraUserNotificationDescriptor(intraUserToRejectPublicKey, NotificationDescriptor.DENIED, ActorProtocolState.DONE);

            actorNetworkServiceRecord.setActorDestinationPublicKey(intraUserToRejectPublicKey);

            actorNetworkServiceRecord.setActorSenderPublicKey(intraUserLoggedInPublicKey);

            actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.DENIED);

            actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_SEND);

            outgoingNotificationDao.createNotification(actorNetworkServiceRecord);

        } catch (Exception e) {
            throw new ErrorDenyConnectingIntraUserException("ERROR DENY CONNECTION TO INTRAUSER", e, "", "Generic Exception");
        }

    }

    @Override
    public void disconnectIntraUSer(String intraUserLoggedInPublicKey, String intraUserToDisconnectPublicKey) throws ErrorDisconnectingIntraUserException {


        try {

            //make message to actor
            UUID newNotificationID = UUID.randomUUID();
            NotificationDescriptor notificationDescriptor = NotificationDescriptor.DISCONNECTED;
            long currentTime = System.currentTimeMillis();
            ActorProtocolState protocolState = ActorProtocolState.PROCESSING_SEND;

            outgoingNotificationDao.createNotification(
                    newNotificationID,
                    intraUserLoggedInPublicKey,
                    Actors.INTRA_USER,
                    intraUserToDisconnectPublicKey,
                    "",
                    "",
                    new byte[0],
                    Actors.INTRA_USER,
                    notificationDescriptor,
                    currentTime,
                    protocolState,
                    false,
                    1,
                    null
            );




        } catch (Exception e) {
            throw new ErrorDisconnectingIntraUserException("ERROR DISCONNECTING INTRAUSER ", e, "", "Generic Exception");
        }

    }

    @Override
    public void cancelIntraUSer(String intraUserLoggedInPublicKey, String intraUserToCancelPublicKey) throws ErrorCancellingIntraUserException {


        try {

            ActorNetworkServiceRecord actorNetworkServiceRecord = incomingNotificationsDao.changeIntraUserNotificationDescriptor(intraUserToCancelPublicKey, NotificationDescriptor.CANCEL, ActorProtocolState.DONE);

            actorNetworkServiceRecord.setActorDestinationPublicKey(intraUserToCancelPublicKey);

            actorNetworkServiceRecord.setActorSenderPublicKey(intraUserLoggedInPublicKey);

            actorNetworkServiceRecord.changeDescriptor(NotificationDescriptor.CANCEL);

            actorNetworkServiceRecord.changeState(ActorProtocolState.PROCESSING_SEND);

            outgoingNotificationDao.createNotification(actorNetworkServiceRecord);

        } catch (Exception e) {
            throw new ErrorCancellingIntraUserException("ERROR CANCEL CONNECTION TO INTRAUSER ", e, "", "Generic Exception");
        }

    }

    @Override
    public List<IntraUserNotification> getPendingNotifications() throws CantGetNotificationsException {

        try {

            return incomingNotificationsDao.listUnreadNotifications();

        } catch (CantListIntraWalletUsersException e) {

            reportUnexpectedError(e);
            throw new CantGetNotificationsException(e, "intra actor network service", "database corrupted");
        } catch (Exception e) {

            reportUnexpectedError(e);
            throw new CantGetNotificationsException(e, "intra actor network service", "Unhandled error.");
        }
    }

    @Override
    public void confirmNotification(final UUID notificationID) throws CantConfirmNotificationException {

        try {

            incomingNotificationsDao.markNotificationAsRead(notificationID);

        } catch (final CantConfirmNotificationException e) {

            reportUnexpectedError(e);
            throw e;
        } catch (final Exception e) {

            reportUnexpectedError(e);
            throw new CantConfirmNotificationException(e, "notificationID: " + notificationID, "Unhandled error.");
        }
    }

    @Override
    public void registrateActors(List<Actor> actors) {

        //TODO: deberia cambiaresto para que venga el tipo de actor a registrar

        CommunicationsClientConnection communicationsClientConnection = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection();

        for (Actor actor : actors) {

            try {

                /*
                 * Construct  profile and register
                 */

                //profile images and  phrase pass on extra data

                Gson gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("PHRASE", actor.getPhrase());
                jsonObject.addProperty("AVATAR_IMG" , Base64.encodeToString(actor.getPhoto(), Base64.DEFAULT));
                String extraData = gson.toJson(jsonObject);

                PlatformComponentProfile platformComponentProfile = communicationsClientConnection.constructPlatformComponentProfileFactory(actor.getActorPublicKey(),
                        (actor.getName().toLowerCase()),
                        (actor.getName().toLowerCase() + "_" + this.getName().replace(" ", "_")),
                        NetworkServiceType.UNDEFINED,
                        PlatformComponentType.ACTOR_INTRA_USER,
                        extraData);

                if (!actorsToRegisterCache.contains(platformComponentProfile)) {

                    actorsToRegisterCache.add(platformComponentProfile);

                    if (register) {
                        System.out.println("---------- TESTENADO --------------------");
                        System.out.println("----------\n" + platformComponentProfile + "\n --------------------");
                        System.out.println("----------\n " + networkServiceType + "\n --------------------");
                        System.out.println("---------- TESTENADO --------------------");
                        communicationsClientConnection.registerComponentForCommunication(networkServiceType, platformComponentProfile);
                        System.out.println("----------\n Pasamos por el registro robert\n --------------------");
                    }
                }

            } catch (CantRegisterComponentException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void registrateActor(Actor actor) {
        try {
            if (register) {
                final CommunicationsClientConnection communicationsClientConnection = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection();



                Gson gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("PHRASE", actor.getPhrase());
                jsonObject.addProperty("AVATAR_IMG", Base64.encodeToString(actor.getPhoto(), Base64.DEFAULT));
                String extraData = gson.toJson(jsonObject);


                final PlatformComponentProfile platformComponentProfile = communicationsClientConnection.constructPlatformComponentProfileFactory(actor.getActorPublicKey(),
                        (actor.getName().toLowerCase()),
                        (actor.getName().toLowerCase() + "_" + this.getName().replace(" ", "_")),
                        NetworkServiceType.UNDEFINED,
                        PlatformComponentType.ACTOR_INTRA_USER,
                        extraData);

                if (!actorsToRegisterCache.contains(platformComponentProfile)) {
                    actorsToRegisterCache.add(platformComponentProfile);
                }

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("---------------- PROBANDO-----------------------");
                            communicationsClientConnection.registerComponentForCommunication(networkServiceType, platformComponentProfile);
                            System.out.println("---------------- PROBANDO-----------------------");
                        } catch (CantRegisterComponentException e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
                System.out.println("----------\n Pasamos por el registro robert\n --------------------");
//                communicationsClientConnection.registerComponentForCommunication(networkServiceType, platformComponentProfile);
//                System.out.println("----------\n Pasamos por el registro robert\n --------------------");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public Actor contructIdentity(String publicKey, String alias, String phrase, Actors actors, byte[] profileImage) {
        return new Identity(publicKey, alias,phrase, actors, profileImage);
    }

    @Override
    public void updateActor(Actor actor) {
        try {
            if (register) {
                final CommunicationsClientConnection communicationsClientConnection = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection();


                Gson gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("PHRASE", actor.getPhrase());
                jsonObject.addProperty("AVATAR_IMG", Base64.encodeToString(actor.getPhoto(), Base64.DEFAULT));
                String extraData = gson.toJson(jsonObject);


                final PlatformComponentProfile platformComponentProfile = communicationsClientConnection.constructPlatformComponentProfileFactory(actor.getActorPublicKey(),
                        (actor.getName().toLowerCase()),
                        (actor.getName().toLowerCase() + "_" + this.getName().replace(" ", "_")),
                        NetworkServiceType.UNDEFINED,
                        PlatformComponentType.ACTOR_INTRA_USER,
                        extraData);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("---------------- PROBANDO UPDATE-----------------------");
                            communicationsClientConnection.updateRegisterActorProfile(networkServiceType, platformComponentProfile);
                            System.out.println("---------------- PROBANDO UPDATE-----------------------");
                        } catch (CantRegisterComponentException e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
                System.out.println("----------\n Pasamos por el UPDATE robert\n --------------------");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void saveCacheIntraUsersSuggestions(List<IntraUserInformation> lstIntraUser) throws CantInsertRecordException {

    }

    public void connectToBetweenActors(String senderPK, PlatformComponentType senderType, String receiverPK, PlatformComponentType receiverType) {
        PlatformComponentProfile applicantParticipant = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(
                senderPK,
                NetworkServiceType.UNDEFINED,
                senderType
        );

        PlatformComponentProfile remoteParticipant = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(
                receiverPK,
                NetworkServiceType.UNDEFINED,
                receiverType
        );

        try {
            communicationNetworkServiceConnectionManager.connectTo(applicantParticipant, platformComponentProfilePluginRoot, remoteParticipant);
        } catch (CantEstablishConnectionException e) {
            e.printStackTrace();
        }
    }



    public IncomingNotificationDao getIncomingNotificationsDao() {
        return incomingNotificationsDao;
    }

    public OutgoingNotificationDao getOutgoingNotificationDao() {
        return outgoingNotificationDao;
    }

    /**
     * (non-Javadoc)
     *
     * @see LogManagerForDevelopers#getClassesFullPath()
     */
    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.TemplateNetworkServicePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure.IncomingTemplateNetworkServiceMessage");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure.OutgoingTemplateNetworkServiceMessage");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.communications.CommunicationRegistrationProcessNetworkServiceAgent");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.communications.CommunicationNetworkServiceLocal");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.communications.CommunicationNetworkServiceRemoteAgent");
        return returnedClasses;
    }

    /**
     * (non-Javadoc)
     *
     * @see LogManagerForDevelopers#setLoggingLevelPerClass(Map<String, LogLevel>)
     */
    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

        /*
         * I will check the current values and update the LogLevel in those which is different
         */
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {

            /*
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */

            //TODO: MATIAS REVISAR POR QUE HACE REFERENCIA AL OLD
          /*  if (com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.IntraActorNetworkServicePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.IntraActorNetworkServicePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.IntraActorNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.IntraActorNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } */
        }

    }


    /**
     * (non-Javadoc)
     *
     * @see DatabaseManagerForDevelopers#getDatabaseList(DeveloperObjectFactory)
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return communicationNetworkServiceDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    /**
     * (non-Javadoc)
     *
     * @see DatabaseManagerForDevelopers#getDatabaseTableList(DeveloperObjectFactory, DeveloperDatabase)
     */
    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return communicationNetworkServiceDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    /**
     * (non-Javadoc)
     *
     * @see DatabaseManagerForDevelopers#getDatabaseTableContent(DeveloperObjectFactory, DeveloperDatabase, DeveloperDatabaseTable)
     */
    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return communicationNetworkServiceDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
    }

    private void reportUnexpectedError(final Exception e) {
        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
    }


    /**
     * This method initialize the database
     *
     * @throws CantInitializeTemplateNetworkServiceDatabaseException
     */
    private void initializeDb() throws CantInitializeTemplateNetworkServiceDatabaseException {

        try {
            /*
             * Open new database connection
             */
            this.dataBaseCommunication = this.pluginDatabaseSystem.openDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            CommunicationNetworkServiceDatabaseFactory communicationNetworkServiceDatabaseFactory = new CommunicationNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.dataBaseCommunication = communicationNetworkServiceDatabaseFactory.createDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }

    }

    private void initializeCacheDb() throws CantInitializeNetworkIntraUserDataBaseException {

        try {
            /*
             * Open new database connection
             */
            this.dataBase = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeNetworkIntraUserDataBaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            IntraActorNetworkServiceDatabaseFactory intraActorNetworkServiceDatabaseFactory = new IntraActorNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.dataBase = intraActorNetworkServiceDatabaseFactory.createDatabase(pluginId, pluginId.toString());

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeNetworkIntraUserDataBaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }

    }

    //reprocess all messages could not be sent
    private void reprocessMessage()
    {
        try {

           outgoingNotificationDao.changeStatusNotSentMessage();


        }
        catch(CantListIntraWalletUsersException e)
        {
            System.out.println("INTRA USER NS EXCEPCION REPROCESANDO MESSAGEs");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("INTRA USER NS EXCEPCION REPROCESANDO MESSAGEs");
            e.printStackTrace();
        }
    }

    private void reprocessMessage(String receiveIdentityKey)
    {
        try {

           outgoingNotificationDao.changeStatusNotSentMessage(receiveIdentityKey);

        }
        catch(CantListIntraWalletUsersException e)
        {
            System.out.println("INTRA USER NS EXCEPCION REPROCESANDO MESSAGEs");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("INTRA USER NS EXCEPCION REPROCESANDO MESSAGEs");
            e.printStackTrace();
        }
    }


    private void startTimer(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // change message state to process retry later
                reprocessMessage();
            }
        },0, reprocessTimer);
    }


    public WsCommunicationsCloudClientManager getWsCommunicationsCloudClientManager() {
        return wsCommunicationsCloudClientManager;
    }

    public CommunicationNetworkServiceConnectionManager getCommunicationNetworkServiceConnectionManager() {
        return communicationNetworkServiceConnectionManager;
    }


    public ErrorManager getErrorManager() {
        return errorManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

}