package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
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
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.CryptoAddressDealers;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.ProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.RequestAction;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.RequestType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantAcceptAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantConfirmAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantDenyAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantGetPendingAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantListPendingCryptoAddressRequestsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantSendAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.PendingRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressRequest;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.event_handlers.ClientConnectionCloseNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.event_handlers.ClientConnectionLooseNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.event_handlers.ClientSuccessfullReconnectNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.event_handlers.CompleteComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.event_handlers.CompleteComponentRegistrationNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.event_handlers.FailureComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.event_handlers.NewSentMessageNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.event_handlers.VPNConnectionCloseNotificationEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.database.CryptoAddressesNetworkServiceDao;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.database.CryptoAddressesNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantChangeProtocolStateException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantCreateRequestException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantHandleNewMessagesException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantInitializeCryptoAddressesNetworkServiceDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantReceiveAcceptanceException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantReceiveDenialException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantReceiveRequestException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages.AcceptMessage;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages.DenyMessage;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages.NetworkServiceMessage;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages.ReceivedMessage;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages.RequestMessage;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.structure.AddressesConstants;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.structure.CryptoAddressesExecutorAgent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.communications.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.communications.CommunicationNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantInitializeNetworkServiceDatabaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.structure.CommunicationNetworkServiceConnectionManager_V2;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.structure.CommunicationRegistrationProcessNetworkServiceAgent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.ClientConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.VPNConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * This plugin manages the exchange of crypto addresses between actors.
 *
 * Created by Leon Acosta (laion.cj91@gmail.com) on 22/09/2015.
 */
public class CryptoAddressesNetworkServicePluginRoot extends AbstractNetworkService implements
        CryptoAddressesManager,
        DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem        ;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededPluginReference(platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION         , plugin = Plugins.WS_CLOUD_CLIENT)
    private WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER         )
    private EventManager eventManager;


    private List<FermatEventListener> listenersAdded;

    /**
     *  Represent the remoteNetworkServicesRegisteredList
     */
    private CopyOnWriteArrayList<PlatformComponentProfile> remoteNetworkServicesRegisteredList;

    /**
     * Represent the cryptoPaymentRequestNetworkServiceConnectionManager
     */
    private CommunicationNetworkServiceConnectionManager_V2 communicationNetworkServiceConnectionManager;



    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return new CryptoAddressesNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseList(developerObjectFactory);

    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        if(developerDatabase.getName() == "Crypto Addresses")
            return new CryptoAddressesNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableList(developerObjectFactory);
        else
        return new CryptoAddressesNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableListCommunication(developerObjectFactory);

    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            return new CryptoAddressesNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableContent(developerObjectFactory, developerDatabase,developerDatabaseTable);
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**
     * Represent the registrationProcessNetworkServiceAgent
     */
    private CommunicationRegistrationProcessNetworkServiceAgent communicationRegistrationProcessNetworkServiceAgent;

    private CryptoAddressesExecutorAgent cryptoAddressesExecutorAgent;

    private CryptoAddressesNetworkServiceDao cryptoAddressesNetworkServiceDao;

    /**
     * Represent the flag to start only once
     */
    private AtomicBoolean flag = new AtomicBoolean(false);

    private long reprocessTimer =  300000; //five minutes

    private Timer timer = new Timer();

    public CryptoAddressesNetworkServicePluginRoot() {

        super(
                new PluginVersionReference(new Version()),
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.CRYPTO_ADDRESSES,
                "Crypto Addresses Network Service",
                "CryptoAddressesNetworkService",
                null,
                EventSource.NETWORK_SERVICE_CRYPTO_ADDRESSES
        );

        this.listenersAdded = new ArrayList<>();
        this.remoteNetworkServicesRegisteredList = new CopyOnWriteArrayList<>();

    }


    /**
     * Service Interface implementation
     */
    @Override
    public synchronized void start() throws CantStartPluginException {


        if(!flag.getAndSet(true)) {
            if (this.serviceStatus != ServiceStatus.STARTING) {
                serviceStatus = ServiceStatus.STARTING;

                /*
                 * Create a new key pair for this execution
                 */
                initializeClientIdentity();

                System.out.println("********* Crypto Addresses: Starting. ");

                /*
                 * Validate required resources
                 */
                validateInjectedResources();


                // initialize crypto payment request dao

                try {

                    cryptoAddressesNetworkServiceDao = new CryptoAddressesNetworkServiceDao(pluginDatabaseSystem, pluginId);

                    cryptoAddressesNetworkServiceDao.initialize();

                } catch(CantInitializeCryptoAddressesNetworkServiceDatabaseException e) {

                    CantStartPluginException pluginStartException = new CantStartPluginException(e, "", "Problem initializing crypto addresses dao.");
                    errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
                    throw pluginStartException;
                }

                try {

                    /*
                     * Initialize the data base
                     */
                    initializeCommunicationDb();

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
                    if (!wsCommunicationsCloudClientManager.isDisable()){

                        /*
                         * Construct my profile and register me
                         */
                        PlatformComponentProfile platformComponentProfilePluginRoot =  wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructPlatformComponentProfileFactory(
                                getIdentityPublicKey(),
                                getAlias().toLowerCase(),
                                getName(),
                                getNetworkServiceType(),
                                getPlatformComponentType(),
                                getExtraData()
                        );

                        setPlatformComponentProfilePluginRoot(platformComponentProfilePluginRoot);


                        /*
                         * Initialize the agent and start
                         */
                        communicationRegistrationProcessNetworkServiceAgent = new CommunicationRegistrationProcessNetworkServiceAgent(this, wsCommunicationsCloudClientManager);
                        communicationRegistrationProcessNetworkServiceAgent.start();
                    }

                    // change message state to process again first time
                    reprocessMessage();

                    //declare a schedule to process waiting request message
                    startTimer();



                    initializeAgent();
            /*
             * Its all ok, set the new status
            */
                    this.serviceStatus = ServiceStatus.STARTED;

                } catch (CantInitializeNetworkServiceDatabaseException exception) {

                    StringBuffer contextBuffer = new StringBuffer();
                    contextBuffer.append("Plugin ID: " + pluginId);
                    contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
                    contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

                    String context = contextBuffer.toString();
                    String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
                    CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

                    errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(),UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
                    throw pluginStartException;

                }

                System.out.println("********* Crypto Addresses: Successful start. ");

            }
        }



    }

    private void initializeClientIdentity() throws CantStartPluginException {

        System.out.println("Calling the method - initializeClientIdentity() ");

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

                System.out.println("No previous clientIdentity finder - Proceed to create new one");

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

    private void initializeListener(){

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
        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_REQUEST_LIST_COMPONENT_REGISTERED_NOTIFICATION);
//        fermatEventListener.setEventHandler(new CompleteRequestListComponentRegisteredNotificationEventHandler(this));
//        eventManager.addListener(fermatEventListener);
//        listenersAdded.add(fermatEventListener);

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

        /**
         * Listen and handle the sent messages
         */


        fermatEventListener = eventManager.getNewListener(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_SENT_NOTIFICATION);
        fermatEventListener.setEventHandler(new NewSentMessageNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);
    }


    /**
     * This method initialize the database
     *
     * @throws CantInitializeNetworkServiceDatabaseException
     */
    private void initializeCommunicationDb() throws CantInitializeNetworkServiceDatabaseException {

        try {

            this.dataBase = this.pluginDatabaseSystem.openDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeNetworkServiceDatabaseException(cantOpenDatabaseException);

        } catch (DatabaseNotFoundException e) {

            CommunicationNetworkServiceDatabaseFactory communicationLayerNetworkServiceDatabaseFactory = new CommunicationNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                this.dataBase = communicationLayerNetworkServiceDatabaseFactory.createDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            } catch (CantCreateDatabaseException cantCreateDatabaseException) {

                errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeNetworkServiceDatabaseException(cantCreateDatabaseException);

            }
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
                pluginDatabaseSystem               == null ||
                errorManager                       == null ||
                eventManager                       == null ) {


            String context =
                    "Plugin ID:                          " + pluginId                           + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR +
                            "wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR +
                            "pluginDatabaseSystem:               " + pluginDatabaseSystem               + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR +
                            "errorManager:                       " + errorManager                       + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR +
                            "eventManager:                       " + eventManager;

            String possibleCause = "No all required resource are injected";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            
            throw pluginStartException;

        }

    }

    @Override
    public void pause() {

        // pause connections manager.
        communicationNetworkServiceConnectionManager.pause();

        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {

        // resume connections manager.
        communicationNetworkServiceConnectionManager.resume();

        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {

        // remove all listeners from the event manager and from the plugin.
        for (FermatEventListener listener: listenersAdded)
            eventManager.removeListener(listener);

        listenersAdded.clear();

        // close all connections.
        communicationNetworkServiceConnectionManager.closeAllConnection();

        cryptoAddressesExecutorAgent.stopExecutor();

        // set to not registered.
        register = Boolean.FALSE;

        this.serviceStatus = ServiceStatus.STOPPED;
    }

    /**
     * I indicate to the Agent the action that it must take:
     * - Protocol State: PROCESSING_SEND.
     * - Action        : REQUEST.
     * - Type          : SENT.
     */
    @Override
    public void sendAddressExchangeRequest(final String                walletPublicKey            ,
                                           final CryptoCurrency        cryptoCurrency             ,
                                           final Actors                identityTypeRequesting     ,
                                           final Actors                identityTypeResponding     ,
                                           final String                identityPublicKeyRequesting,
                                           final String                identityPublicKeyResponding,
                                           final CryptoAddressDealers  dealer                     ,
                                           final BlockchainNetworkType blockchainNetworkType      ) throws CantSendAddressExchangeRequestException {

        try {

            System.out.println("********* Crypto Addresses: Creating Address Exchange Request. ");

            UUID newId = UUID.randomUUID();

            ProtocolState state  = ProtocolState.PROCESSING_SEND;
            RequestType   type   = RequestType  .SENT           ;
            RequestAction action = RequestAction.REQUEST        ;

            cryptoAddressesNetworkServiceDao.createAddressExchangeRequest(
                    newId,
                    walletPublicKey,
                    cryptoCurrency,
                    identityTypeRequesting,
                    identityTypeResponding,
                    identityPublicKeyRequesting,
                    identityPublicKeyResponding,
                    state,
                    type,
                    action,
                    dealer,
                    blockchainNetworkType,
                    1,
                    System.currentTimeMillis(),
                    AddressesConstants.OUTGOING_MESSAGE,
                    false
            );

            System.out.println("********* Crypto Addresses: Successful Address Exchange Request creation. ");

        } catch (CantCreateRequestException e){

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSendAddressExchangeRequestException(e, null, "Error trying to create the request.");
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSendAddressExchangeRequestException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    /**
     * I update the record with the new address an then, i indicate the ns agent the action that it must take:
     * - Action        : ACCEPT.
     * - Protocol State: PROCESSING_SEND.
     */
    @Override
    public void acceptAddressExchangeRequest(final UUID          requestId    ,
                                             final CryptoAddress cryptoAddress) throws CantAcceptAddressExchangeRequestException,
                                                                                       PendingRequestNotFoundException          {

        System.out.println("************ Crypto Addresses -> i'm executing the acceptance.");

        try {

            ProtocolState protocolState = ProtocolState.PROCESSING_SEND;
            cryptoAddressesNetworkServiceDao.acceptAddressExchangeRequest(requestId, cryptoAddress, protocolState);

            System.out.println("************ Crypto Addresses -> i already execute the acceptance.");

        } catch (CantAcceptAddressExchangeRequestException | PendingRequestNotFoundException e){
            // PendingRequestNotFoundException - THIS SHOULD' HAPPEN.
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAcceptAddressExchangeRequestException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    /**
     * we'll return to the actor all the pending requests pending a local action.
     * State : PENDING_ACTION.
     *
     *
     * @throws CantListPendingCryptoAddressRequestsException      if something goes wrong.
     */
    @Override
    public List<CryptoAddressRequest> listAllPendingRequests() throws CantListPendingCryptoAddressRequestsException {

        try {

            return cryptoAddressesNetworkServiceDao.listAllPendingRequests();

        } catch (CantListPendingCryptoAddressRequestsException e){

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListPendingCryptoAddressRequestsException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    /**
     * we'll return to the crypto addresses all the pending requests pending a local action.
     * State : PENDING_ACTION.
     * Action: REQUEST.
     *
     * @throws CantListPendingCryptoAddressRequestsException      if something goes wrong.
     */
    @Override
    public List<CryptoAddressRequest> listPendingCryptoAddressRequests() throws CantListPendingCryptoAddressRequestsException {

        try {

            return cryptoAddressesNetworkServiceDao.listPendingRequestsByProtocolStateAndAction(ProtocolState.PENDING_ACTION, RequestAction.REQUEST);

        } catch (CantListPendingCryptoAddressRequestsException e){

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListPendingCryptoAddressRequestsException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }


    @Override
    public CryptoAddressRequest getPendingRequest(UUID requestId) throws CantGetPendingAddressExchangeRequestException,
                                                                           PendingRequestNotFoundException              {

        try {

            return cryptoAddressesNetworkServiceDao.getPendingRequest(requestId);

        } catch (PendingRequestNotFoundException e){
            // when i don't find it i only pass the exception (maybe another plugin confirm the pending request).
            throw e;
        } catch (CantGetPendingAddressExchangeRequestException e){

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetPendingAddressExchangeRequestException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    /**
     * when i confirm a request i put it in the final state, indicating:
     * State : DONE.
     * Action: NONE.
     *
     * @param requestId id of the address exchange request we want to confirm.
     *
     * @throws CantConfirmAddressExchangeRequestException   if something goes wrong.
     * @throws PendingRequestNotFoundException              if i can't find the record.
     */
    @Override
    public void confirmAddressExchangeRequest(UUID requestId) throws CantConfirmAddressExchangeRequestException,
                                                                     PendingRequestNotFoundException           {

        System.out.println("****** crypto addresses -> confirming address");
       try {

           //only the record you request the addromperess
           if(cryptoAddressesNetworkServiceDao.getPendingRequest(requestId).getMessageType().equals(AddressesConstants.OUTGOING_MESSAGE))
               cryptoAddressesNetworkServiceDao.confirmAddressExchangeRequest(requestId);

       } catch (CantConfirmAddressExchangeRequestException | PendingRequestNotFoundException e){
           // PendingRequestNotFoundException - THIS SHOULD' HAPPEN.
           errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
           throw e;
       } catch (Exception e){
           errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
           throw new CantConfirmAddressExchangeRequestException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    /**
     * when i deny a request i indicate the ns agent to do the next action:
     * State : PROCESSING_SEND.
     * Action: DENY.
     *
     * @param requestId id of the address exchange request we want to confirm.
     *
     * @throws CantDenyAddressExchangeRequestException      if something goes wrong.
     * @throws PendingRequestNotFoundException              if i can't find the record.
     */
    @Override
    public void denyAddressExchangeRequest(UUID requestId) throws CantDenyAddressExchangeRequestException,
                                                                  PendingRequestNotFoundException        {

        try {

            ProtocolState protocolState = ProtocolState.PROCESSING_SEND;
            cryptoAddressesNetworkServiceDao.denyAddressExchangeRequest(requestId, protocolState);

        } catch(PendingRequestNotFoundException |
                CantDenyAddressExchangeRequestException e){
            // PendingRequestNotFoundException - THIS SHOULD' HAPPEN.
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e){

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDenyAddressExchangeRequestException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    @Override
    public void markReceivedRequest(UUID requestId) throws CantConfirmAddressExchangeRequestException {
        try {

            //update message to read with destination, and update state in DONE, End Message
            if(cryptoAddressesNetworkServiceDao.getPendingRequest(requestId).getMessageType().equals(AddressesConstants.INCOMING_MESSAGE)){
                cryptoAddressesNetworkServiceDao.markRead(requestId);
            }else {
                cryptoAddressesNetworkServiceDao.markReadAndDone(requestId);
            }
        }catch (Exception e){
            throw new CantConfirmAddressExchangeRequestException(e,"","No se pudo marcar como leido el request exchange de address");
        }
    }

    /**
     * (non-javadoc)
     * @see NetworkService#getRemoteNetworkServicesRegisteredList()
     */
    @Override
    public List<PlatformComponentProfile> getRemoteNetworkServicesRegisteredList() {
        return remoteNetworkServicesRegisteredList;
    }

    /**
     * (non-javadoc)
     * @see NetworkService#requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters)
     */
    @Override
    public void requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters discoveryQueryParameters){

        System.out.println(" TemplateNetworkServiceRoot - requestRemoteNetworkServicesRegisteredList");

         /*
         * Request the list of component registers
         */
        try {

            wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().requestListComponentRegistered(this.getPlatformComponentProfilePluginRoot(), discoveryQueryParameters);

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

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

        }

    }
    /**
     * This method initialize the cryptoPaymentRequestNetworkServiceConnectionManager.
     * IMPORTANT: Call this method only in the RegistrationProcessNetworkServiceAgent, when execute the registration process
     * because at this moment, is create the platformComponentProfile for this component
     */
    @Override
    public void initializeCommunicationNetworkServiceConnectionManager() {
            this.communicationNetworkServiceConnectionManager = new CommunicationNetworkServiceConnectionManager_V2(
                    this,
                    getPlatformComponentProfilePluginRoot(),
                    identity,
                    wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(),
                    dataBase,
                    errorManager,
                    eventManager
            );
    }

    /**
     * (non-javadoc)
     * @see NetworkService#getNetworkServiceConnectionManager()
     */
    @Override
    public NetworkServiceConnectionManager getNetworkServiceConnectionManager() {
        return communicationNetworkServiceConnectionManager;
    }

    @Override
    public String getIdentityPublicKey() {
        return this.identity.getPublicKey();
    }

    /**
     * (non-javadoc)
     * @see NetworkService#constructDiscoveryQueryParamsFactory(PlatformComponentType, NetworkServiceType, String, String, Location, Double, String, String, Integer, Integer, PlatformComponentType, NetworkServiceType)
     */
    @Override
    public DiscoveryQueryParameters constructDiscoveryQueryParamsFactory(final PlatformComponentType    platformComponentType         ,
                                                                         final NetworkServiceType       networkServiceType            ,
                                                                         final String                   alias                         ,
                                                                         final String                   identityPublicKey             ,
                                                                         final Location                 location                      ,
                                                                         final Double                   distance                      ,
                                                                         final String                   name                          ,
                                                                         final String                   extraData                     ,
                                                                         final Integer                  firstRecord                   ,
                                                                         final Integer                  numRegister                   ,
                                                                         final PlatformComponentType    fromOtherPlatformComponentType,
                                                                         final NetworkServiceType       fromOtherNetworkServiceType   ) {

        return wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructDiscoveryQueryParamsFactory(
                platformComponentType,
                networkServiceType,
                alias,
                identityPublicKey,
                location,
                distance,
                name,
                extraData,
                firstRecord,
                numRegister,
                fromOtherPlatformComponentType,
                fromOtherNetworkServiceType
        );
    }

    /**
     * Handles the events CompleteComponentRegistrationNotification
     */
    public void initializeAgent(){

        System.out.println("CryptoAddressesNetworkServicePluginRoot - Starting method initializeAgent");

        try {
            if (cryptoAddressesExecutorAgent == null){

                cryptoAddressesExecutorAgent = new CryptoAddressesExecutorAgent(
                        this,
                        cryptoAddressesNetworkServiceDao
                );

                this.cryptoAddressesExecutorAgent.start();
            }
        } catch (CantStartAgentException e) {
            e.printStackTrace();
        }

    }

    public void handleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile platformComponentProfileRegistered){

        System.out.println("CryptoAddressesNetworkServicePluginRoot - Starting method handleCompleteComponentRegistrationNotificationEvent");

        try {

            if (platformComponentProfileRegistered.getPlatformComponentType() == PlatformComponentType.COMMUNICATION_CLOUD_CLIENT && !this.register){

                if(communicationRegistrationProcessNetworkServiceAgent != null && communicationRegistrationProcessNetworkServiceAgent.getActive()){
                    communicationRegistrationProcessNetworkServiceAgent.stop();
                    communicationRegistrationProcessNetworkServiceAgent = null;
                }

                wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().registerComponentForCommunication(this.getNetworkServiceType(), getPlatformComponentProfilePluginRoot());

            }

            if (platformComponentProfileRegistered.getPlatformComponentType() == PlatformComponentType.NETWORK_SERVICE &&
                    platformComponentProfileRegistered.getNetworkServiceType() == this.getNetworkServiceType() &&
                    platformComponentProfileRegistered.getIdentityPublicKey().equals(identity.getPublicKey())) {

                System.out.println("CryptoAddressesNetworkServicePluginRoot - NetWork Service is Registered: " + platformComponentProfileRegistered.getAlias());
                initializeAgent();
                this.register = Boolean.TRUE;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void handleFailureComponentRegistrationNotificationEvent(PlatformComponentProfile networkServiceApplicant, PlatformComponentProfile remoteParticipant) {
        System.out.println("----------------------------------\n" +
                "CRYPTO ADDRESSES FAILED CONNECTION WITH "+remoteParticipant.getIdentityPublicKey()+"\n" +
                "--------------------------------------------------------");
        cryptoAddressesExecutorAgent.connectionFailure(remoteParticipant.getIdentityPublicKey());

        //I check my time trying to send the message
        checkFailedDeliveryTime(remoteParticipant.getIdentityPublicKey());

    }

    /**
     * Handles the events CompleteRequestListComponentRegisteredNotificationEvent
     */

    public void handleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile applicantComponentProfile, PlatformComponentProfile remoteComponentProfile){
        communicationNetworkServiceConnectionManager.handleEstablishedRequestedNetworkServiceConnection(remoteComponentProfile);
    }

    /**
     * Handles the events VPNConnectionCloseNotificationEvent
     * @param fermatEvent
     */
    @Override
    public void handleVpnConnectionCloseNotificationEvent(FermatEvent fermatEvent) {

        if(fermatEvent instanceof VPNConnectionCloseNotificationEvent){

            VPNConnectionCloseNotificationEvent vpnConnectionCloseNotificationEvent = (VPNConnectionCloseNotificationEvent) fermatEvent;

            if(vpnConnectionCloseNotificationEvent.getNetworkServiceApplicant() == getNetworkServiceType()){

                String remotePublicKey =  vpnConnectionCloseNotificationEvent.getRemoteParticipant().getIdentityPublicKey();

                if(communicationNetworkServiceConnectionManager != null) {
                    communicationNetworkServiceConnectionManager.closeConnection(remotePublicKey);
                }

                if (cryptoAddressesExecutorAgent.isConnectionOpen(remotePublicKey)){
                    cryptoAddressesExecutorAgent.connectionFailure(remotePublicKey);
                }

                //reprocess not DONE messages
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

        if(fermatEvent instanceof ClientConnectionCloseNotificationEvent){
            System.out.println("CLOSSING ALL CONNECTIONS IN CRYPTO ADDRESSES ");
            reprocessMessage();

            this.register = false;

            if(communicationNetworkServiceConnectionManager != null) {
                communicationNetworkServiceConnectionManager.closeAllConnection();
                communicationNetworkServiceConnectionManager.stop();
            }

        }

    }

    /*
     * Handles the events ClientConnectionLooseNotificationEvent
     */
    @Override
    public void handleClientConnectionLooseNotificationEvent(FermatEvent fermatEvent) {

        if(communicationNetworkServiceConnectionManager != null) {
            communicationNetworkServiceConnectionManager.stop();
        }

        this.register = Boolean.FALSE;

    }

    /*
     * Handles the events ClientSuccessfullReconnectNotificationEvent
     */
    @Override
    public void handleClientSuccessfullReconnectNotificationEvent(FermatEvent fermatEvent) {


        try {

            if (communicationNetworkServiceConnectionManager != null){
                communicationNetworkServiceConnectionManager.restart();
            }


            initializeAgent();

            /*
             * Mark as register
             */
            this.register = Boolean.TRUE;


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void handleCompleteRequestListComponentRegisteredNotificationEvent(List<PlatformComponentProfile> platformComponentProfileRegisteredList) {

        remoteNetworkServicesRegisteredList.addAllAbsent(platformComponentProfileRegisteredList);
    }

    public void handleNewMessages(final FermatMessage fermatMessage) {

        try {

            Gson gson = new Gson();

            String jsonMessage = fermatMessage.getContent();

            NetworkServiceMessage networkServiceMessage = gson.fromJson(jsonMessage, NetworkServiceMessage.class);

            FermatEvent eventToRaise;
            switch (networkServiceMessage.getMessageType()) {

                case ACCEPT:
                    AcceptMessage acceptMessage = gson.fromJson(jsonMessage, AcceptMessage.class);
                    receiveAcceptance(acceptMessage);


                    //close connection - end message
                    communicationNetworkServiceConnectionManager.closeConnection(acceptMessage.getActorDestination());
                    cryptoAddressesExecutorAgent.getPoolConnectionsWaitingForResponse().remove(acceptMessage.getActorDestination());

                    break;

                case DENY:
                    DenyMessage denyMessage = gson.fromJson(jsonMessage, DenyMessage.class);
                    receiveDenial(denyMessage);

                    //close connection - end message
                    communicationNetworkServiceConnectionManager.closeConnection(denyMessage.getActorDestination());
                    cryptoAddressesExecutorAgent.getPoolConnectionsWaitingForResponse().remove(denyMessage.getActorDestination());
                    break;

                case REQUEST:
                    // update the request to processing receive state with the given action.
                    RequestMessage requestMessage = gson.fromJson(jsonMessage, RequestMessage.class);
                    receiveRequest(requestMessage);
                    break;

                case RECEIVED:

                    ReceivedMessage receivedMessage  =  gson.fromJson(jsonMessage, ReceivedMessage.class);
                    receivedMessage(receivedMessage);

                    break;


                default:
                    throw new CantHandleNewMessagesException(
                            "message type: " +networkServiceMessage.getMessageType().name(),
                            "Message type not handled."
                    );
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void receivedMessage(final ReceivedMessage receivedMessage) throws CantReceiveRequestException {
        try {

            cryptoAddressesNetworkServiceDao.changeActionState(receivedMessage.getRequestId(), RequestAction.NONE);
            cryptoAddressesNetworkServiceDao.changeProtocolState(receivedMessage.getRequestId(),ProtocolState.DONE);

            communicationNetworkServiceConnectionManager.closeConnection(receivedMessage.getActorDestination());
            //remove from the waiting pool
            cryptoAddressesExecutorAgent.connectionFailure(receivedMessage.getActorDestination());
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void handleNewSentMessageNotificationEvent(FermatMessage fermatMessage) {

        try {

            Gson gson = new Gson();

            String jsonMessage = fermatMessage.getContent();

            NetworkServiceMessage networkServiceMessage = gson.fromJson(jsonMessage, NetworkServiceMessage.class);

            switch (networkServiceMessage.getMessageType()) {
                case ACCEPT:
                    AcceptMessage acceptMessage = gson.fromJson(jsonMessage, AcceptMessage.class);
                   cryptoAddressesNetworkServiceDao.changeProtocolState(acceptMessage.getRequestId(), ProtocolState.DONE);

                    break;
                case DENY:
                    DenyMessage denyMessage = gson.fromJson(jsonMessage, DenyMessage.class);
                    cryptoAddressesNetworkServiceDao.changeProtocolState(denyMessage.getRequestId(), ProtocolState.DONE);
                    break;
                case REQUEST:
                    // update the request to processing receive state with the given action.
                    //RequestMessage requestMessage = gson.fromJson(jsonMessage, RequestMessage.class);
                    cryptoAddressesNetworkServiceDao.changeProtocolState(networkServiceMessage.getRequestId(), ProtocolState.PENDING_ACTION);
                    break;
                case RECEIVED:
                    ReceivedMessage receivedMessage  =  gson.fromJson(jsonMessage, ReceivedMessage.class);
                    //receivedMessage(receivedMessage);
                    break;
                default:
                    throw new CantHandleNewMessagesException(
                            "message type: " +networkServiceMessage.getMessageType().name(),
                            "Message type not handled."
                    );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * I indicate to the Agent the action that it must take:
     * - Protocol State: PROCESSING_RECEIVE.
     * - Action        : REQUEST           .
     */
    private void receiveRequest(final RequestMessage requestMessage) throws CantReceiveRequestException {

        try {

            ProtocolState protocolState = ProtocolState.PENDING_ACTION    ;
            RequestType   type          = RequestType  .RECEIVED          ;
            RequestAction action        = RequestAction.REQUEST           ;

            cryptoAddressesNetworkServiceDao.createAddressExchangeRequest(
                    requestMessage.getRequestId(),
                    requestMessage.getWalletPublicKey(),
                    requestMessage.getCryptoCurrency(),
                    requestMessage.getIdentityTypeRequesting(),
                    requestMessage.getIdentityTypeResponding(),
                    requestMessage.getIdentityPublicKeyRequesting(),
                    requestMessage.getIdentityPublicKeyResponding(),
                    protocolState,
                    type,
                    action,
                    requestMessage.getCryptoAddressDealer(),
                    requestMessage.getBlockchainNetworkType(),
                    1,
                    System.currentTimeMillis(),
                    AddressesConstants.INCOMING_MESSAGE,
                    false
            );

        } catch(CantCreateRequestException e) {
            // i inform to error manager the error.
            reportUnexpectedException(e);
            throw new CantReceiveRequestException(e, "", "Error in Crypto Payment Request NS Dao.");
        } catch(Exception e) {

            reportUnexpectedException(e);
            throw new CantReceiveRequestException(e, "", "Unhandled Exception.");
        }
    }

    /**
     * I update the record with the new address an then, i indicate the ns agent the action that it must take:
     * - Action        : ACCEPT.
     * - Protocol State: PROCESSING_RECEIVE.
     */
    private void receiveAcceptance(AcceptMessage acceptMessage) throws CantReceiveAcceptanceException {

        try {

            ProtocolState protocolState = ProtocolState.PROCESSING_SEND;

            cryptoAddressesNetworkServiceDao.acceptAddressExchangeRequest(
                    acceptMessage.getRequestId(),
                    acceptMessage.getCryptoAddress(),
                    protocolState
            );

            cryptoAddressesNetworkServiceDao.changeActionState(acceptMessage.getRequestId(), RequestAction.RECEIVED);



        } catch (CantAcceptAddressExchangeRequestException | PendingRequestNotFoundException e){
            // PendingRequestNotFoundException - THIS SHOULD' HAPPEN.
            reportUnexpectedException(e);
            throw new CantReceiveAcceptanceException(e, "", "Error in crypto addresses DAO");
        } catch (Exception e){

            reportUnexpectedException(e);
            throw new CantReceiveAcceptanceException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    /**
     * I update the record with the new address an then, i indicate the ns agent the action that it must take:
     * - Action        : DENY.
     * - Protocol State: PROCESSING_RECEIVE.
     */
    private void receiveDenial(DenyMessage denyMessage) throws CantReceiveDenialException {

        try {

            ProtocolState protocolState = ProtocolState.PROCESSING_SEND;

            cryptoAddressesNetworkServiceDao.denyAddressExchangeRequest(
                    denyMessage.getRequestId(),
                    protocolState
            );

            cryptoAddressesNetworkServiceDao.changeActionState(denyMessage.getRequestId(), RequestAction.RECEIVED);

        } catch (CantDenyAddressExchangeRequestException | PendingRequestNotFoundException e){
            // PendingRequestNotFoundException - THIS SHOULD' HAPPEN.
            reportUnexpectedException(e);
            throw new CantReceiveDenialException(e, "", "Error in crypto addresses DAO");
        } catch (Exception e){

            reportUnexpectedException(e);
            throw new CantReceiveDenialException(FermatException.wrapException(e), null, "Unhandled Exception.");
        }
    }

    private void reportUnexpectedException(Exception e) {
        errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
    }

    private void checkFailedDeliveryTime(String destinationPublicKey)
    {
        try{

            List<CryptoAddressRequest> cryptoAddressRequestList = cryptoAddressesNetworkServiceDao.listRequestsByActorPublicKey(destinationPublicKey);

            //if I try to send more than 5 times I put it on hold
            for (CryptoAddressRequest record : cryptoAddressRequestList) {

                if(!record.getState().getCode().equals(ProtocolState.WAITING_RESPONSE.getCode()))
                {
                    if(record.getSentNumber() > 10)
                    {
                      //  if(record.getSentNumber() > 20)
                       // {
                            //reprocess at two hours
                       //     reprocessTimer =  2 * 3600 * 1000;
                        //}
                         //update state and process again later
                        cryptoAddressesNetworkServiceDao.changeProtocolState(record.getRequestId(),ProtocolState.WAITING_RESPONSE);
                        cryptoAddressesNetworkServiceDao.changeSentNumber(record.getRequestId(),1);
                    }
                    else
                    {
                        cryptoAddressesNetworkServiceDao.changeSentNumber(record.getRequestId(),record.getSentNumber() + 1);
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

                        cryptoAddressesNetworkServiceDao.delete(record.getRequestId());
                    }

                }

            }


        }
        catch(Exception e)
        {
            System.out.println("EXCEPCION VERIFICANDO WAIT MESSAGE");
            e.printStackTrace();
        }

    }

    //reprocess all messages could not be sent
    private void reprocessMessage()
    {
        try {

            List<CryptoAddressRequest> cryptoAddressRequestList = cryptoAddressesNetworkServiceDao.listUncompletedRequest();

            for(CryptoAddressRequest record : cryptoAddressRequestList) {

                cryptoAddressesNetworkServiceDao.changeProtocolState(record.getRequestId(),ProtocolState.PROCESSING_SEND);
            }
        }
        catch(CantListPendingCryptoAddressRequestsException | CantChangeProtocolStateException |PendingRequestNotFoundException e)
        {
            System.out.println("ADDRESS NS EXCEPCION REPROCESANDO WAIT MESSAGE");
            e.printStackTrace();
        }
    }


    private void reprocessMessage(String remoteIdentityKey)
    {
        try {

            List<CryptoAddressRequest> cryptoAddressRequestList = cryptoAddressesNetworkServiceDao.listUncompletedRequest(remoteIdentityKey);

            for(CryptoAddressRequest record : cryptoAddressRequestList) {

                cryptoAddressesNetworkServiceDao.changeProtocolState(record.getRequestId(),ProtocolState.PROCESSING_SEND);
            }
        }
        catch(CantListPendingCryptoAddressRequestsException | CantChangeProtocolStateException |PendingRequestNotFoundException e)
        {
            System.out.println("ADDRESS NS EXCEPCION REPROCESANDO WAIT MESSAGE");
            e.printStackTrace();
        }
    }


    public WsCommunicationsCloudClientManager getWsCommunicationsCloudClientManager() {
        return wsCommunicationsCloudClientManager;
    }

    public ErrorManager getErrorManager() {
        return errorManager;
    }

    private void startTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // change message state to process retry later
                reprocessMessage();
            }
        }, 0,reprocessTimer);


    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public CommunicationNetworkServiceConnectionManager_V2 getCommunicationNetworkServiceConnectionManager() {
        return communicationNetworkServiceConnectionManager;

    }
}
