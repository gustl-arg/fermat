package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.pluginOld.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceLocal;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
<<<<<<< HEAD
=======
import com.bitdubai.fermat_ccp_api.all_definition.enums.EventType;
>>>>>>> 589579dd634da6d0edd4e49f3e34d40384772f86
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.RequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.ActorProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.events.ActorNetworkServicePendingsNotificationEvent;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.pluginOld.IntraActorNetworkServicePluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.ActorNetworkServiceRecord;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
<<<<<<< HEAD
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
=======
>>>>>>> 589579dd634da6d0edd4e49f3e34d40384772f86
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by mati on 2015.10.15..
 */
public class ActorNetworkServiceRecordedAgent extends FermatAgent{


    private static final long SEND_SLEEP_TIME    = 15000;
    private static final long RECEIVE_SLEEP_TIME = 15000;
    private static final int SEND_TASK = 0;
    private static final int RECEIVE_TASK = 1;
    private final ExecutorService threadPoolExecutor;

    private Runnable toSend   ;
    private Runnable toReceive;

    // network services registered
    private Map<String, ActorNetworkServiceRecord> poolConnectionsWaitingForResponse;

    private Future<?>[] futures= new Future[2];


    private final IntraActorNetworkServicePluginRoot actorNetworkServicePluginRoot;

    public ActorNetworkServiceRecordedAgent(final IntraActorNetworkServicePluginRoot ActorNetworkServicePluginRoot) {

        this.actorNetworkServicePluginRoot = ActorNetworkServicePluginRoot;
        this.status                                       = AgentStatus.CREATED                         ;

        poolConnectionsWaitingForResponse = new HashMap<>();

        threadPoolExecutor = Executors.newFixedThreadPool(2);
        //Create a thread to send the messages
        this.toSend = new Runnable() {
            @Override
            public void run() {
                while (isRunning())
                    sendCycle();
            }
        };

        //Create a thread to receive the messages
        this.toReceive = new Runnable() {
            @Override
            public void run() {
                while (isRunning())
                    receiveCycle();
            }
        };
    }


    public void start() throws CantStartAgentException {

        try {
            if(futures!=null){
                if(futures[SEND_TASK]!=null) futures[SEND_TASK].cancel(true);
                if(futures[RECEIVE_TASK]!=null) futures[RECEIVE_TASK].cancel(true);

                futures[SEND_TASK] = threadPoolExecutor.submit(toSend);
                futures[RECEIVE_TASK] = threadPoolExecutor.submit(toReceive);

            }

            this.status = AgentStatus.STARTED;

        } catch (Exception exception) {

            throw new CantStartAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }

    public void resume() throws CantStartAgentException {
        try {
            if(futures!=null){
                if(futures[SEND_TASK]!=null) futures[SEND_TASK].cancel(true);
                if(futures[RECEIVE_TASK]!=null) futures[RECEIVE_TASK].cancel(true);

                futures[SEND_TASK] = threadPoolExecutor.submit(toSend);
                futures[RECEIVE_TASK] = threadPoolExecutor.submit(toReceive);

            }

            this.status = AgentStatus.STARTED;

        } catch (Exception exception) {

            throw new CantStartAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }

    public void pause() throws CantStopAgentException {
        try {

            if(futures!=null){
                if(futures[SEND_TASK]!=null) futures[SEND_TASK].cancel(true);
                if(futures[RECEIVE_TASK]!=null) futures[RECEIVE_TASK].cancel(true);
            }

            this.status = AgentStatus.PAUSED;

        } catch (Exception exception) {

            throw new CantStopAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }

    public void stop() throws CantStopAgentException {
        try {

            if(futures!=null){
                if(futures[SEND_TASK]!=null) futures[SEND_TASK].cancel(true);
                if(futures[RECEIVE_TASK]!=null) futures[RECEIVE_TASK].cancel(true);
            }

            this.status = AgentStatus.PAUSED;

        } catch (Exception exception) {

            throw new CantStopAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }

    // TODO MANAGE PAUSE, STOP AND RESUME METHODS.

    public void sendCycle() {

        try {

            if (actorNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection() != null){

                if (!actorNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection().isConnected()){
                    //System.out.println("ActorNetworkServiceRecordedAgent - sendCycle() no connection available ... ");
                    return;
                }else {
                    // function to process and send the rigth message to the counterparts.
                    processSend();

                    //Sleep for a time
                    TimeUnit.SECONDS.sleep(2);
                }
            }

        } catch (InterruptedException e) {
            status = AgentStatus.STOPPED;
            System.out.println("ActorNetworkServiceRecordedAgent - sendCycle() Thread Interrupted stopped ... ");
        } catch(Exception e) {
            reportUnexpectedError(FermatException.wrapException(e));
        }

    }

    private void processSend() {
        try {

            List<ActorNetworkServiceRecord> lstActorRecord = actorNetworkServicePluginRoot.getOutgoingNotificationDao().listRequestsByProtocolStateAndNotDone(
                    ActorProtocolState.PROCESSING_SEND
            );


            for (ActorNetworkServiceRecord cpr : lstActorRecord) {
                switch (cpr.getNotificationDescriptor()) {

                    case ASKFORACCEPTANCE:
                    case ACCEPTED:
                    case DISCONNECTED:
                    case DENIED:
                    case RECEIVED:
                        sendMessageToActor(
                                cpr
                        );

                        System.out.print("-----------------------\n" +
                                "ENVIANDO MENSAJE A OTRO INTRA USER!!!!! -----------------------\n" +
                                "-----------------------\n DESDE: " + cpr.getActorSenderAlias());


                        //toWaitingResponse(cpr.getId(),actorNetworkServicePluginRoot.getOutgoingNotificationDao());
                        break;

                }

            }
//        } catch (CantExecuteDatabaseOperationException e) {
//            e.printStackTrace();
//        }
        } catch (CantListIntraWalletUsersException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void receiveCycle() {

        try {

            if (actorNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection() != null) {

                if (!actorNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection().isConnected()) {
                    //System.out.println("ActorNetworkServiceRecordedAgent - receiveCycle() no connection available ... ");
                    return;
                } else {

                    // function to process and send the right message to the counterparts.
                    processReceive();

                    //Sleep for a time
                    Thread.sleep(RECEIVE_SLEEP_TIME);
                }
            }

        } catch (InterruptedException e) {
            status = AgentStatus.STOPPED;
            System.out.println("ActorNetworkServiceRecordedAgent - receiveCycle() Thread Interrupted stopped ... ");
        } catch(Exception e) {

            reportUnexpectedError(FermatException.wrapException(e));
        }

    }

    public void processReceive(){
       try {
            List<ActorNetworkServiceRecord> lstActorRecord = actorNetworkServicePluginRoot.getIncomingNotificationsDao().listRequestsByProtocolStateAndType(
                    ActorProtocolState.PROCESSING_RECEIVE
            );
            for(ActorNetworkServiceRecord cpr : lstActorRecord) {
                switch (cpr.getNotificationDescriptor()) {
                    case ASKFORACCEPTANCE:
                        System.out.println("----------------------------\n" +
                                "MENSAJE PROCESANDOSE:" + cpr.getActorDestinationPublicKey()
                                + "\n-------------------------------------------------");

                        lauchNotification();

                        try {
                            actorNetworkServicePluginRoot.getIncomingNotificationsDao().changeProtocolState(cpr.getId(),ActorProtocolState.PENDING_ACTION);
                        } catch (CantUpdateRecordDataBaseException e) {
                            e.printStackTrace();
                        } catch (CantUpdateRecordException e) {
                            e.printStackTrace();
                        } catch (RequestNotFoundException e) {
                            e.printStackTrace();
                        }

                        break;
                    case ACCEPTED:

                        System.out.print("-----------------------\n" +
                                "ACEPTARON EL REQUEST!!!!!-----------------------\n" +
                                "-----------------------\n NOTIFICAION: " + cpr.getActorDestinationPublicKey());

                        lauchNotification();

                        try {

                            actorNetworkServicePluginRoot.getIncomingNotificationsDao().changeProtocolState(cpr.getId(),ActorProtocolState.PENDING_ACTION);

                        } catch (CantUpdateRecordDataBaseException e) {
                            e.printStackTrace();
                        } catch (CantUpdateRecordException e) {
                            e.printStackTrace();
                        } catch (RequestNotFoundException e) {
                            e.printStackTrace();
                        }

                        break;
                    case DISCONNECTED:
                        System.out.print("-----------------------\n" +
                                "REQUEST PARA DESCONEXION!!!!!-----------------------\n" +
                                "-----------------------\n NOTIFICAION: " + cpr.getActorDestinationPublicKey());

                        lauchNotification();

                        try {

                            actorNetworkServicePluginRoot.getIncomingNotificationsDao().changeProtocolState(cpr.getId(),ActorProtocolState.PENDING_ACTION);

                        } catch (CantUpdateRecordDataBaseException e) {
                            e.printStackTrace();
                        } catch (CantUpdateRecordException e) {
                            e.printStackTrace();
                        } catch (RequestNotFoundException e) {
                            e.printStackTrace();
                        }

                        break;
                    case RECEIVED:
                        sendMessageToActor(cpr);

                            //toWaitingResponse(cpr.getId(),actorNetworkServicePluginRoot.getIncomingNotificationsDao());
                        break;
                    case DENIED:
                        System.out.print("-----------------------\n" +
                                "DENEGARON EL REQUEST!!!!!-----------------------\n" +
                                "-----------------------\n NOTIFICAION: " + cpr.getActorDestinationPublicKey());

                        lauchNotification();

                        try {

                            actorNetworkServicePluginRoot.getIncomingNotificationsDao().changeProtocolState(cpr.getId(),ActorProtocolState.PENDING_ACTION);

                        } catch (CantUpdateRecordDataBaseException e) {
                            e.printStackTrace();
                        } catch (CantUpdateRecordException e) {
                            e.printStackTrace();
                        } catch (RequestNotFoundException e) {
                            e.printStackTrace();
                        }

                        break;
                }
            }



       } catch (CantListIntraWalletUsersException e) {
           e.printStackTrace();
       }
    }



    private void sendMessageToActor(ActorNetworkServiceRecord actorNetworkServiceRecord) {
        try {
            if (!poolConnectionsWaitingForResponse.containsKey(actorNetworkServiceRecord.getActorDestinationPublicKey())) {
                if (actorNetworkServicePluginRoot.getNetworkServiceConnectionManager().getNetworkServiceLocalInstance(actorNetworkServiceRecord.getActorDestinationPublicKey()) == null) {
                        if (actorNetworkServicePluginRoot.getPlatformComponentProfilePluginRoot() != null) {

                            PlatformComponentProfile applicantParticipant = actorNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection()
                                    .constructPlatformComponentProfileFactory(
                                            actorNetworkServiceRecord.getActorSenderPublicKey(),
                                            "A",
                                            "A",
                                            NetworkServiceType.UNDEFINED,
                                            PlatformComponentType.ACTOR_INTRA_USER,"");
                            PlatformComponentProfile remoteParticipant = actorNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection()
                                    .constructPlatformComponentProfileFactory(
                                            actorNetworkServiceRecord.getActorDestinationPublicKey(),
                                            "B",
                                            "B",
                                            NetworkServiceType.UNDEFINED,
                                            PlatformComponentType.ACTOR_INTRA_USER,"");

                            actorNetworkServicePluginRoot.getNetworkServiceConnectionManager().connectTo(
                                    applicantParticipant,
                                    actorNetworkServicePluginRoot.getPlatformComponentProfilePluginRoot(),
                                    remoteParticipant
                            );

                            // I put the actor in the pool of connections waiting for response-
                            poolConnectionsWaitingForResponse.put(actorNetworkServiceRecord.getActorDestinationPublicKey(), actorNetworkServiceRecord);
                        }


                }else{
                    NetworkServiceLocal communicationNetworkServiceLocal = actorNetworkServicePluginRoot.getNetworkServiceConnectionManager().getNetworkServiceLocalInstance(actorNetworkServiceRecord.getActorDestinationPublicKey());

                    System.out.println("----------------------------\n" +
                            "ENVIANDO MENSAJE: " + actorNetworkServiceRecord.getActorDestinationPublicKey()
                            + "\n-------------------------------------------------");


                    communicationNetworkServiceLocal.sendMessage(
                            actorNetworkServiceRecord.getActorSenderPublicKey(),
                            actorNetworkServiceRecord.getActorDestinationPublicKey(),
                           actorNetworkServiceRecord.toJson()
                    );

                    actorNetworkServicePluginRoot.getOutgoingNotificationDao().changeProtocolState(actorNetworkServiceRecord.getId(), ActorProtocolState.SENT);

                    poolConnectionsWaitingForResponse.put(actorNetworkServiceRecord.getActorDestinationPublicKey(), actorNetworkServiceRecord);
                }
            } else {

                NetworkServiceLocal communicationNetworkServiceLocal = actorNetworkServicePluginRoot.getNetworkServiceConnectionManager().getNetworkServiceLocalInstance(actorNetworkServiceRecord.getActorDestinationPublicKey());

                if (communicationNetworkServiceLocal != null) {

                    try {

                        System.out.println("----------------------------\n" +
                                "ENVIANDO MENSAJE: " + actorNetworkServiceRecord.getActorDestinationPublicKey()
                                + "\n-------------------------------------------------");


                        communicationNetworkServiceLocal.sendMessage(
                                actorNetworkServiceRecord.getActorSenderPublicKey(),
                                actorNetworkServiceRecord.getActorDestinationPublicKey(),
                                actorNetworkServiceRecord.toJson()
                        );

                        actorNetworkServicePluginRoot.getOutgoingNotificationDao().changeProtocolState(actorNetworkServiceRecord.getId(), ActorProtocolState.SENT);

                    } catch (Exception e) {

                        reportUnexpectedError(FermatException.wrapException(e));
                    }
                }
            }
        } catch (Exception z) {
            reportUnexpectedError(FermatException.wrapException(z));
        }
    }

    private PlatformComponentType platformComponentTypeSelectorByActorType(Actors type) throws InvalidParameterException {

        switch (type) {

            case INTRA_USER  : return PlatformComponentType.ACTOR_INTRA_USER  ;
            case DAP_ASSET_ISSUER: return PlatformComponentType.ACTOR_ASSET_ISSUER;
            case DAP_ASSET_USER  : return PlatformComponentType.ACTOR_ASSET_USER  ;

            default: throw new InvalidParameterException(
                    " actor type: "+type.name()+"  type-code: "+type.getCode(),
                    " type of actor not expected."
            );
        }
    }



    private void reportUnexpectedError(FermatException e) {
        actorNetworkServicePluginRoot.getErrorManager().reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
    }

    public void connectionFailure(String identityPublicKey){
        this.poolConnectionsWaitingForResponse.remove(identityPublicKey);
    }


    public Map<String, ActorNetworkServiceRecord> getPoolConnectionsWaitingForResponse() {
        return poolConnectionsWaitingForResponse;
    }

    private void lauchNotification(){
        FermatEvent fermatEvent = actorNetworkServicePluginRoot.getEventManager().getNewEvent(EventType.ACTOR_NETWORK_SERVICE_NEW_NOTIFICATIONS);
        ActorNetworkServicePendingsNotificationEvent intraUserActorRequestConnectionEvent = (ActorNetworkServicePendingsNotificationEvent) fermatEvent;
        actorNetworkServicePluginRoot.getEventManager().raiseEvent(intraUserActorRequestConnectionEvent);
    }

}
