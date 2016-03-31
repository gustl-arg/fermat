package com.bitdubai.fermat_bnp_plugin.layer.network_service.bank_notes.developer.bitdubai.version_1;

/**
 * This plugin is in charge of finding the banks notes of a certain country, downloading them and storing them in the
 * local device.
 * <p/>
 * Initially it will look into a centralized place, but afterwords it will implement a method to download it from peers.
 * <p/>
 * * * * *
 * * *
 */

/**
 * I commented this class because compileJava is not working with this plugin old version (this version
 * looks like before the new core) Manuel - Mordor Team
 */

public class BankNotesNetworkServicePluginRoot{ /*implements Service, NetworkService, DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem, Plugin {

    /**
     * Service Interface member variables.
     */
//    ServiceStatus serviceStatus = ServiceStatus.CREATED;
//    List<FermatEventListener> listenersAdded = new ArrayList<>();
//
//    /**
//     * DealWithEvents Interface member variables.
//     */
//    EventManager eventManager;
//
//    ErrorManager errorManager;
//    /**
//     * UsesFileSystem Interface member variables.
//     */
//    PluginFileSystem pluginFileSystem;
//
//    /**
//     * DealsWithPluginIdentity Interface member variables.
//     */
//    UUID pluginId;
//
//
//    /**
//     * Service Interface implementation.
//     */
//
//    @Override
//    public void start() throws CantStartPluginException {
//        /**
//         * I will initialize the handling of com.bitdubai.platform events.
//         */
//
//        FermatEventListener fermatEventListener;
//        FermatEventHandler fermatEventHandler;
//        this.serviceStatus = ServiceStatus.STARTED;
//    }
//
//    @Override
//    public void pause() {
//        this.serviceStatus = ServiceStatus.PAUSED;
//    }
//
//    @Override
//    public void resume() {
//        this.serviceStatus = ServiceStatus.STARTED;
//    }
//
//    @Override
//    public void stop() {
//        /**
//         * I will remove all the event listeners registered with the event manager.
//         */
//
//        for (FermatEventListener fermatEventListener : listenersAdded) {
//            eventManager.removeListener(fermatEventListener);
//        }
//        listenersAdded.clear();
//        this.serviceStatus = ServiceStatus.STOPPED;
//    }
//
//    @Override
//    public ServiceStatus getStatus() {
//        return this.serviceStatus;
//    }
//
//    /**
//     * NetworkService Interface implementation.
//     */
//
//    @Override
//    public PlatformComponentProfile getPlatformComponentProfilePluginRoot() {
//        return null;
//    }
//
//    @Override
//    public PlatformComponentType getPlatformComponentType() {
//        return null;
//    }
//
//    @Override
//    public NetworkServiceType getNetworkServiceType() {
//        return null;
//    }
//
//    @Override
//    public List<PlatformComponentProfile> getRemoteNetworkServicesRegisteredList() {
//        return null;
//    }
//
//    @Override
//    public void requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters discoveryQueryParameters) {
//
//    }
//
//    @Override
//    public NetworkServiceConnectionManager getNetworkServiceConnectionManager() {
//        return null;
//    }
//
//    @Override
//    public DiscoveryQueryParameters constructDiscoveryQueryParamsFactory(PlatformComponentType platformComponentType, NetworkServiceType networkServiceType, String alias, String identityPublicKey, Location location, Double distance, String name, String extraData, Integer firstRecord, Integer numRegister, PlatformComponentType fromOtherPlatformComponentType, NetworkServiceType fromOtherNetworkServiceType) {
//        return null;
//    }
//
//
//    /**
//     * Handles the events CompleteComponentRegistrationNotification
//     * @param platformComponentProfileRegistered
//     */
//    @Override
//    public void handleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile platformComponentProfileRegistered) {
//
//    }
//
//    @Override
//    public void handleFailureComponentRegistrationNotificationEvent(PlatformComponentProfile networkServiceApplicant, PlatformComponentProfile remoteParticipant) {
//
//    }
//
//    @Override
//    public void handleCompleteRequestListComponentRegisteredNotificationEvent(List<PlatformComponentProfile> platformComponentProfileRegisteredList) {
//
//    }
//
//
//    /**
//     * Handles the events CompleteRequestListComponentRegisteredNotificationEvent
//     * @param remoteComponentProfile
//     */
//    @Override
//    public void handleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile applicantComponentProfile, PlatformComponentProfile remoteComponentProfile) {
//
//    }
//
//
//    /**
//     * Handles the events VPNConnectionCloseNotificationEvent
//     * @param fermatEvent
//     */
//    @Override
//    public void handleVpnConnectionCloseNotificationEvent(FermatEvent fermatEvent) {
//
//       /* if(fermatEvent instanceof VPNConnectionCloseNotificationEvent){
//
//            VPNConnectionCloseNotificationEvent vpnConnectionCloseNotificationEvent = (VPNConnectionCloseNotificationEvent) fermatEvent;
//
//            if(vpnConnectionCloseNotificationEvent.getNetworkServiceApplicant() == getNetworkServiceType()){
//
//                if(communicationNetworkServiceConnectionManager != null)
//                 communicationNetworkServiceConnectionManager.closeConnection(vpnConnectionCloseNotificationEvent.getRemoteParticipant().getIdentityPublicKey());
//
//            }
//
//        }*/
//
//    }
//
//    /**
//     * Handles the events ClientConnectionCloseNotificationEvent
//     * @param fermatEvent
//     */
//    @Override
//    public void handleClientConnectionCloseNotificationEvent(FermatEvent fermatEvent) {
//
//       /* if(fermatEvent instanceof ClientConnectionCloseNotificationEvent){
//            this.register = false;
//
//            if(communicationNetworkServiceConnectionManager != null)
//                communicationNetworkServiceConnectionManager.closeAllConnection();
//        }*/
//
//    }
//
//    /*
//    * Handles the events ClientConnectionLooseNotificationEvent
//    */
//    @Override
//    public void handleClientConnectionLooseNotificationEvent(FermatEvent fermatEvent) {
//
////        if(communicationNetworkServiceConnectionManager != null)
////            communicationNetworkServiceConnectionManager.stop();
//
//    }
//
//    /*
//     * Handles the events ClientSuccessfullReconnectNotificationEvent
//     */
//    @Override
//    public void handleClientSuccessfullReconnectNotificationEvent(FermatEvent fermatEvent) {
//
////        if(communicationNetworkServiceConnectionManager != null)
////            communicationNetworkServiceConnectionManager.restart();
//
////        if(!this.register){
////
////            if(communicationRegistrationProcessNetworkServiceAgent == null){
////                communicationRegistrationProcessNetworkServiceAgent = new CommunicationRegistrationProcessNetworkServiceAgent(this, wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection());
////            }
////
////            communicationRegistrationProcessNetworkServiceAgent.start();
////        }
//
//    }
//
//    @Override
//    public boolean isRegister() {
//        return false;
//    }
//
//    @Override
//    public void setPlatformComponentProfilePluginRoot(PlatformComponentProfile platformComponentProfile) {
//
//    }
//
//    @Override
//    public void initializeCommunicationNetworkServiceConnectionManager() {
//
//    }
//
//    @Override
//    public String getIdentityPublicKey() {
//        return null;
//    }
//
//    @Override
//    public String getAlias() {
//        return null;
//    }
//
//    @Override
//    public String getName() {
//        return null;
//    }
//
//    @Override
//    public String getExtraData() {
//        return null;
//    }
//
//    /**
//     * UsesFileSystem Interface implementation.
//     */
//
//    @Override
//    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
//        this.pluginFileSystem = pluginFileSystem;
//    }
//
//    /**
//     * DealWithEvents Interface implementation.
//     */
//
//    @Override
//    public void setEventManager(EventManager eventManager) {
//        this.eventManager = eventManager;
//    }
//
//    /**
//     * DealWithErrors Interface implementation.
//     */
//
//    @Override
//    public void setErrorManager(ErrorManager errorManager) {
//        this.errorManager = errorManager;
//    }
//
//    /**
//     * DealsWithPluginIdentity methods implementation.
//     */
//
//    @Override
//    public void setId(UUID pluginId) {
//        this.pluginId = pluginId;
//    }
//
//    @Override
//    public FermatManager getManager() {
//        return null;
//    }

}
