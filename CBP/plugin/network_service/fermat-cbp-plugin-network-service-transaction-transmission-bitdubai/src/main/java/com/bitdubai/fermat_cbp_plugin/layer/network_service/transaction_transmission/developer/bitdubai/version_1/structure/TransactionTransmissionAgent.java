package com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceLocal;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.enums.TransactionTransmissionStates;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.events.IncomingNewContractStatusUpdate;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.interfaces.BusinessTransactionMetadata;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.TransactionTransmissionPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.database.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.database.TransactionTransmissionConnectionsDAO;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.database.TransactionTransmissionContractHashDao;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions.CantInitializeNetworkServiceDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.messages.TransactionTransmissionResponseMessage;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.communications.CommunicationNetworkServiceLocal;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantEstablishConnectionException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 25/11/15.
 */
public class TransactionTransmissionAgent {

    /*
    * Represent the sleep time for the read or send (2000 milliseconds)
    */
    private static final long SLEEP_TIME = 15000;
    private static final long RECEIVE_SLEEP_TIME = 15000;


    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * Represent is the tread is running
     */
    private Boolean running;

    /**
     * Represent the identity
     */
    private ECCKeyPair identity;

    /**
     * Communication Service, Class to send the message
     */
    //CryptoTransmissionNetworkServiceLocal communicationNetworkServiceLocal;

    /**
     * Communication manager, Class to obtain the connections
     */
    CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;

    /**
     *
     */
    WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;

    /**
     * plugin root
     */
    TransactionTransmissionPluginRoot transactionTransmissionPluginRoot;

    /**
     * DAO TransactionTransmission
     */
    TransactionTransmissionContractHashDao transactionTransmissionContractHashDao;
    TransactionTransmissionConnectionsDAO transactionTransmissionConnectionsDAO;

    /**
     *  Represent the remoteNetworkServicesRegisteredList
     */
    private List<PlatformComponentProfile> remoteNetworkServicesRegisteredList;

    /**
     * PlatformComponentProfile platformComponentProfile
     */
    PlatformComponentProfile platformComponentProfile;


    /**
     * Represent the send cycle tread of this NetworkService
     */
    private Thread toSend;

    /**
     * Represent the send messages tread of this TemplateNetworkServiceRemoteAgent
     */
    private Thread toReceive;

    /**
     * Cache de metadata con conexions leidas anteriormente
     * ActorPublicKey, metadata de respuesta
     */
    Map<String, TransactionTransmissionStates> cacheResponseMetadataFromRemotes;

    /**
     * Map contains publicKey from componentProfile to connect
     *  and the number of connections intents.
     */
    Map<String,Integer> connectionsCounters;

    /**
     * Cola de espera, a la cual van pasando las conecciones que no se pudieron conectar para que se hagan con más tiempo y no saturen el server
     */
    Map<String, TransactionTransmissionPlatformComponentProfilePlusWaitTime> waitingPlatformComponentProfile;

    /**
     * Pool connections requested waiting for peer or server response
     *
     * publicKey  and transaccion metadata waiting to be a response
     */
    Map<String,BusinessTransactionMetadata> poolConnectionsWaitingForResponse;


    Map<String, FermatMessage> receiveMessage;
    private boolean flag=true;


    private EventManager eventManager;
    /**
     *  Constructor
     *  @param
     * @param transactionTransmissionPluginRoot
     * @param transactionTransmissionConnectionsDAO
     * @param transactionTransmissionContractHashDao
     * @param communicationNetworkServiceConnectionManager
     * @param errorManager
     */

    public TransactionTransmissionAgent(
            TransactionTransmissionPluginRoot transactionTransmissionPluginRoot,
            TransactionTransmissionConnectionsDAO transactionTransmissionConnectionsDAO,
            TransactionTransmissionContractHashDao transactionTransmissionContractHashDao,
            CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager,
            WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager,
            PlatformComponentProfile platformComponentProfile,
            ErrorManager errorManager,
            List<PlatformComponentProfile> remoteNetworkServicesRegisteredList,
            ECCKeyPair identity,
            EventManager eventManager) {
        try{

            this.transactionTransmissionPluginRoot = transactionTransmissionPluginRoot;
            this.transactionTransmissionConnectionsDAO = transactionTransmissionConnectionsDAO;
            this.transactionTransmissionContractHashDao = transactionTransmissionContractHashDao;
            this.communicationNetworkServiceConnectionManager = communicationNetworkServiceConnectionManager;
            this.wsCommunicationsCloudClientManager =wsCommunicationsCloudClientManager;
            this.errorManager = errorManager;
            this.remoteNetworkServicesRegisteredList = remoteNetworkServicesRegisteredList;
            this.identity = identity;
            this.platformComponentProfile = platformComponentProfile;
            this.eventManager = eventManager;


            cacheResponseMetadataFromRemotes = new HashMap<String, TransactionTransmissionStates>();
            waitingPlatformComponentProfile = new HashMap<>();


            poolConnectionsWaitingForResponse = new HashMap<> ();

            //Create a thread to send the messages
            this.toSend = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (running)
                        sendCycle();
                }
            });

            //Create a thread to receive the messages
            this.toReceive = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (running)
                        receiveCycle();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            FermatException ex = new FermatException(FermatException.DEFAULT_MESSAGE,FermatException.wrapException(e), "EXCEPTION THAT THE PLUGIN CAN NOT HANDLE BY ITSELF","Check the cause");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, ex);
        }

    }

    /**
     * Start the internal threads to make the job
     */
    public void start(){

        //Set to running
        this.running  = Boolean.TRUE;
        try {
            try {
                transactionTransmissionContractHashDao.initialize();
            } catch (CantInitializeNetworkServiceDatabaseException e) {
                e.printStackTrace();
        //Start the Thread
        toSend.start();
        toReceive.start();

        System.out.println("CryptoTransmissionAgent - started ");
            }
        } catch (Exception exception){
            FermatException ex = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE,FermatException.wrapException(exception), "EXCEPTION THAT THE PLUGIN CAN NOT HANDLE BY ITSELF","Check the cause");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, ex);
        }

    }

    /**
     * Pause the internal threads
     */
    public void pause(){
        this.running  = Boolean.FALSE;
    }

    /**
     * Resume the internal threads
     */
    public void resume(){
        this.running  = Boolean.TRUE;
    }

    /**
     * Stop the internal threads
     */
    public void stop(){

        //Stop the Thread
        toSend.interrupt();
        toReceive.interrupt();
        //Disconnect from the service
    }

    /**
     *
     * Lifeclycle of the networkService Cryptotransmission.
     * This thread controls the send circuit saving each states and metadata in database
     *
     */
    public void sendCycle(){

        try {

            if(transactionTransmissionPluginRoot.isRegister()) {

                // function to process and send metadata to remote
                processMetadata();

                //Discount from the the waiting list
                discountWaitTime();
            }

            //Sleep for a time
            toSend.sleep(SLEEP_TIME);

        } catch (InterruptedException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not sleep"));
        } catch (Exception e) {
            e.printStackTrace();
            FermatException ex = new FermatException(FermatException.DEFAULT_MESSAGE,FermatException.wrapException(e), "EXCEPTION THAT THE PLUGIN CAN NOT HANDLE BY ITSELF","Check the cause");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, ex);

        }

    }

    private void processMetadata() {

        try {
            Map<String, Object> filters = new HashMap<>();
            filters.put(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_CONTRACT_STATUS_COLUMN_NAME, TransactionTransmissionStates.PRE_PROCESSING_SEND.getCode());

         /*
         * Read all pending BusinessTransactionMetadata from database
         */
            List<BusinessTransactionMetadata> businessTransactionMetadataList = transactionTransmissionContractHashDao.findAll(filters);


            for (BusinessTransactionMetadata businessTransactionMetadata : businessTransactionMetadataList) {

                String receiverPublicKey= businessTransactionMetadata.getReceiverId();

                if(!poolConnectionsWaitingForResponse.containsKey(receiverPublicKey)) {

                    //TODO: hacer un filtro por aquellas que se encuentran conectadas

                    if (communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(receiverPublicKey) == null) {

                        if (wsCommunicationsCloudClientManager != null) {

                            if (platformComponentProfile != null) {

                                PlatformComponentProfile applicantParticipant = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(businessTransactionMetadata.getSenderId(), NetworkServiceType.UNDEFINED, PlatformComponentType.ACTOR_INTRA_USER);
                                PlatformComponentProfile remoteParticipant = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(businessTransactionMetadata.getReceiverId(), NetworkServiceType.UNDEFINED, PlatformComponentType.ACTOR_INTRA_USER);
                                communicationNetworkServiceConnectionManager.connectTo(applicantParticipant, platformComponentProfile, remoteParticipant);

                                // pass the businessTransactionMetadata to a pool waiting for the response of the other peer or server failure
                                poolConnectionsWaitingForResponse.put(receiverPublicKey, businessTransactionMetadata);
                            }

                        }
                    }
                }else{

                    NetworkServiceLocal communicationNetworkServiceLocal = transactionTransmissionPluginRoot.getNetworkServiceConnectionManager().getNetworkServiceLocalInstance(receiverPublicKey);

                    if (communicationNetworkServiceLocal != null) {

                        try {
                            businessTransactionMetadata.setState(TransactionTransmissionStates.SENT);

                            System.out.print("-----------------------\n" +
                                    "SENDING BUSINESS TRANSACTION RECORD -----------------------\n" +
                                    "-----------------------\n To: " + receiverPublicKey);

                            // Si se encuentra conectado paso la metadata al dao de la capa de comunicacion para que lo envie
                            Gson gson = new Gson();
                            String jsonBusinessTransaction =gson.toJson(businessTransactionMetadata);

                            // Envio el mensaje a la capa de comunicacion

                            communicationNetworkServiceLocal.sendMessage(identity.getPublicKey(),receiverPublicKey,jsonBusinessTransaction);

                            transactionTransmissionContractHashDao.changeState(businessTransactionMetadata);

                            System.out.print("-----------------------\n" +
                                    "BUSINESS TRANSACTION -----------------------\n" +
                                    "-----------------------\n STATE: " + businessTransactionMetadata.getState());

                        } catch (CantUpdateRecordDataBaseException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        } catch (CantReadRecordDataBaseException e) {
            e.printStackTrace();
        } catch (CantEstablishConnectionException e) {
            e.printStackTrace();
        }
    }

    private void discountWaitTime(){
        if(!waitingPlatformComponentProfile.isEmpty()) {
            for (TransactionTransmissionPlatformComponentProfilePlusWaitTime cryptoTransmissionPlatformComponentProfilePlusWaitTime : waitingPlatformComponentProfile.values()) {
                cryptoTransmissionPlatformComponentProfilePlusWaitTime.WaitTimeDown();
                if (cryptoTransmissionPlatformComponentProfilePlusWaitTime.getWaitTime() <= 0) {
                    remoteNetworkServicesRegisteredList.add(cryptoTransmissionPlatformComponentProfilePlusWaitTime.getPlatformComponentProfile());
                    waitingPlatformComponentProfile.remove(cryptoTransmissionPlatformComponentProfilePlusWaitTime);
                }
            }
        }
    }

    public void addRemoteNetworkServicesRegisteredList(List<PlatformComponentProfile> list){
        remoteNetworkServicesRegisteredList = list;
    }

    private void receiveCycle(){
        try {
            // function to process metadata received
            processReceive();
            //Sleep for a time
            toSend.sleep(RECEIVE_SLEEP_TIME);

        } catch (InterruptedException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not sleep"));
        }

    }

    private void processReceive() {

        try {
            //communicationNetworkServiceConnectionManager.

            Map<String, Object> filters = new HashMap<>();
            filters.put(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_PENDING_FLAG_COLUMN_NAME, "false");

         /*
         * Read all pending CryptoTransmissionMetadata from database
         */
            List<BusinessTransactionMetadata> businessTransactionMetadataList = transactionTransmissionContractHashDao.findAll(filters);


            for(BusinessTransactionMetadata businessTransactionMetadata : businessTransactionMetadataList){

                CommunicationNetworkServiceLocal communicationNetworkServiceLocal = communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(businessTransactionMetadata.getSenderId());

                if(communicationNetworkServiceLocal!=null){

                    System.out.print("-----------------------\n" +
                            "RECEIVING BUSINESS TRANSACTION-----------------------\n" +
                            "-----------------------\n STATE: " + businessTransactionMetadata.getState());

                    // si no contiene la metadata, la tengo que guardar en la bd y notificar que llegó, tambien debería cargar ese caché cuando se lanza el evento de que llega la metadata de respuesta
                    // if( ! cacheResponseMetadataFromRemotes.containsKey(cryptoTransmissionMetadata.getDestinationPublicKey())){

                    try {

                        switch (businessTransactionMetadata.getState()) {

                            case SEEN_BY_DESTINATION_NETWORK_SERVICE:
                                //TODO: revisar que se puede hacer acá
                                System.out.println("Transaction Transmission SEEN_BY_DESTINATION_NETWORK_SERVICE---to implement");
                                break;

                            case CONFIRM_CONTRACT:
                                System.out.print(businessTransactionMetadata.getSenderId()+" Transaction Transmission CONFIRM_CONTRACT");

                                //this.poolConnectionsWaitingForResponse.remove(businessTransactionMetadata.getReceiverId());
                                launchNotification();
                                this.poolConnectionsWaitingForResponse.remove(businessTransactionMetadata.getReceiverId());
                                break;

                            case CONFIRM_RESPONSE:
                                System.out.print(businessTransactionMetadata.getSenderId()+" Transaction Transmission CONFIRM_RESPONSE");
                                launchNotification();
                                this.poolConnectionsWaitingForResponse.remove(businessTransactionMetadata.getReceiverId());
                                break;
                            // si el mensaje viene con un estado de SENT es porque es la primera vez que llega, por lo que tengo que guardarlo en la bd y responder
                            case SENT:

                                businessTransactionMetadata.setState(TransactionTransmissionStates.SEEN_BY_OWN_NETWORK_SERVICE);
                                businessTransactionMetadata.setBusinessTransactionTransactionType(businessTransactionMetadata.getType());
                                transactionTransmissionContractHashDao.update(businessTransactionMetadata);

                                System.out.print("-----------------------\n" +
                                        "RECEIVING BUSINESS TRANSACTION -----------------------\n" +
                                        "-----------------------\n STATE: " + businessTransactionMetadata.getState());

                                launchNotification();

                                TransactionTransmissionResponseMessage cryptoTransmissionResponseMessage = new TransactionTransmissionResponseMessage(
                                        businessTransactionMetadata.getTransactionId(),
                                        TransactionTransmissionStates.SEEN_BY_DESTINATION_NETWORK_SERVICE,
                                        businessTransactionMetadata.getType());

                                Gson gson = new Gson();

                                String message = gson.toJson(cryptoTransmissionResponseMessage);

                                // El destination soy yo porque me lo estan enviando
                                // El sender es el otro y es a quien le voy a responder

                                communicationNetworkServiceLocal.sendMessage(businessTransactionMetadata.getReceiverId(), businessTransactionMetadata.getSenderId(), message);


                                System.out.print("-----------------------\n" +
                                        "SENDING ANSWER -----------------------\n" +
                                        "-----------------------\n STATE: " + businessTransactionMetadata.getState());
                                break;
                            default:
                                //TODO: handle with an exception
                                break;
                        }

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }

        } catch (CantReadRecordDataBaseException e) {
            e.printStackTrace();
        }

    }

    public void setPlatformComponentProfile(PlatformComponentProfile platformComponentProfile){
        try{
            this.platformComponentProfile = platformComponentProfile;
        }catch (Exception ex){
           // throw new ObjectNotSetException(ex, "CAN NOT SET THE OBJECT","");
            ex.printStackTrace();
            FermatException e = new ObjectNotSetException(FermatException.wrapException(ex), "","Check the cause");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }

    }



    public void connectionFailure(String identityPublicKey){
        if(identityPublicKey != null && identityPublicKey.length()>0){
            this.poolConnectionsWaitingForResponse.remove(identityPublicKey);
        }
    }


    public boolean isConnection(String publicKey){
        return this.poolConnectionsWaitingForResponse.containsKey(publicKey);
    }

    public boolean isRunning(){
        return running;
    }

    private void launchNotification(){
        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.INCOMING_NEW_CONTRACT_STATUS_UPDATE);
        IncomingNewContractStatusUpdate incomingNewContractStatusUpdate = (IncomingNewContractStatusUpdate) fermatEvent;
        incomingNewContractStatusUpdate.setSource(EventSource.NETWORK_SERVICE_TRANSACTION_TRANSMISSION);
        eventManager.raiseEvent(incomingNewContractStatusUpdate);
    }


}
