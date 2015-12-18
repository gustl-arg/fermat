package com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Action;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.CryptoBrokerActor;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CryptoCustomerActor;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.enums.BusinessTransactionTransactionType;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.enums.TransactionTransmissionStates;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.exceptions.CantConfirmNotificationReception;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.exceptions.CantSendBusinessTransactionHashException;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.exceptions.CantSendContractNewStatusNotificationException;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.exceptions.PendingRequestNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.interfaces.BusinessTransactionMetadata;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.database.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.database.TransactionTransmissionContractHashDao;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions.CantGetTransactionTransmissionException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 27/11/15.
 */
public class TransactionTransmissionNetworkServiceManager implements TransactionTransmissionManager {

    TransactionTransmissionContractHashDao transactionTransmissionContractHashDao;

    public TransactionTransmissionNetworkServiceManager(
            TransactionTransmissionContractHashDao transactionTransmissionContractHashDao){
        this.transactionTransmissionContractHashDao=transactionTransmissionContractHashDao;
    }

    @Override
    public void sendContractHashToCryptoCustomer(UUID transactionId,
                                                 String cryptoBrokerActorSenderPublicKey,
                                                 String cryptoCustomerActorReceiverPublicKey,
                                                 String transactionHash,
                                                 String negotiationId) throws CantSendBusinessTransactionHashException {
        //TODO: check the correct PlatformComponentType for sender and receiver
        //TODO: Check is contractId is necessary
        Date date=new Date();
        Timestamp timestamp=new Timestamp(date.getTime());
        BusinessTransactionMetadata businessTransactionMetadata =new BusinessTransactionMetadataRecord(
                transactionHash,
                ContractTransactionStatus.PENDING_CONFIRMATION,
                cryptoCustomerActorReceiverPublicKey,
                PlatformComponentType.NETWORK_SERVICE,
                cryptoBrokerActorSenderPublicKey,
                PlatformComponentType.NETWORK_SERVICE,
                null,
                negotiationId,
                BusinessTransactionTransactionType.TRANSACTION_HASH,
                timestamp.getTime(),
                transactionId,
                TransactionTransmissionStates.SENDING_HASH
        );
        try {
            transactionTransmissionContractHashDao.saveBusinessTransmissionRecord(businessTransactionMetadata);
        } catch (CantInsertRecordDataBaseException e) {
            throw new CantSendBusinessTransactionHashException(e,"Cannot persists the contract hash in table","database corrupted");
        }catch (Exception e){
            throw new CantSendBusinessTransactionHashException(e,"Cannot persists the contract hash in table","database corrupted");
        }

    }

    @Override
    public void sendContractHashToCryptoBroker(UUID transactionId,
                                               String cryptoCustomerActorSenderPublicKey,
                                               String cryptoCustomerBrokerReceiverPublicKey,
                                               String transactionHash,
                                               String negotiationId) throws CantSendBusinessTransactionHashException {
        //TODO: check the correct PlatformComponentType for sender and receiver
        //TODO: Check is contractId is necessary
        Date date=new Date();
        Timestamp timestamp=new Timestamp(date.getTime());
        BusinessTransactionMetadata businessTransactionMetadata =new BusinessTransactionMetadataRecord(
                transactionHash,
                ContractTransactionStatus.PENDING_CONFIRMATION,
                cryptoCustomerActorSenderPublicKey,
                PlatformComponentType.NETWORK_SERVICE,
                cryptoCustomerBrokerReceiverPublicKey,
                PlatformComponentType.NETWORK_SERVICE,
                null,
                negotiationId,
                BusinessTransactionTransactionType.TRANSACTION_HASH,
                timestamp.getTime(),
                transactionId,
                TransactionTransmissionStates.SENDING_HASH
        );
        try {
            transactionTransmissionContractHashDao.saveBusinessTransmissionRecord(businessTransactionMetadata);
        } catch (CantInsertRecordDataBaseException e) {
            throw new CantSendBusinessTransactionHashException(e,"Cannot persists the contract hash in table","database corrupted");
        } catch (Exception e){
            throw new CantSendBusinessTransactionHashException(e,"Cannot persists the contract hash in table","database corrupted");
        }
    }

    @Override
    public void sendContractStatusNotificationToCryptoCustomer(String cryptoBrokerActorSenderPublicKey,
                                                               String cryptoCustomerActorReceiverPublicKey,
                                                               String transactionHash,
                                                               String transactionId,
                                                               ContractTransactionStatus contractStatus) throws CantSendContractNewStatusNotificationException {
        Date date=new Date();
        Timestamp timestamp=new Timestamp(date.getTime());
        UUID uuidTransactionId=UUID.fromString(transactionId);
        BusinessTransactionMetadata businessTransactionMetadata =new BusinessTransactionMetadataRecord(
                transactionHash,
                contractStatus,
                cryptoBrokerActorSenderPublicKey,
                PlatformComponentType.NETWORK_SERVICE,
                cryptoCustomerActorReceiverPublicKey,
                PlatformComponentType.NETWORK_SERVICE,
                null,
                null,
                BusinessTransactionTransactionType.CONTRACT_STATUS_UPDATE,
                timestamp.getTime(),
                uuidTransactionId,
                TransactionTransmissionStates.UPDATE_CONTRACT
        );
        try {
            transactionTransmissionContractHashDao.saveBusinessTransmissionRecord(businessTransactionMetadata);
        } catch (CantInsertRecordDataBaseException e) {
            throw new CantSendContractNewStatusNotificationException(CantSendContractNewStatusNotificationException.DEFAULT_MESSAGE,e,"Cannot persists the contract hash in table","database corrupted");
        } catch (Exception e){
            throw new CantSendContractNewStatusNotificationException(CantSendContractNewStatusNotificationException.DEFAULT_MESSAGE,e,"Cannot persists the contract hash in table","database corrupted");
        }

    }

    @Override
    public void sendContractStatusNotificationToCryptoBroker(String cryptoCustomerActorSenderPublicKey,
                                                             String cryptoCustomerBrokerReceiverPublicKey,
                                                             String transactionHash,
                                                             String transactionId,
                                                             ContractTransactionStatus contractStatus) throws CantSendContractNewStatusNotificationException {
        Date date=new Date();
        Timestamp timestamp=new Timestamp(date.getTime());
        UUID uuidTransactionId=UUID.fromString(transactionId);
        BusinessTransactionMetadata businessTransactionMetadata =new BusinessTransactionMetadataRecord(
                transactionHash,
                contractStatus,
                cryptoCustomerActorSenderPublicKey,
                PlatformComponentType.NETWORK_SERVICE,
                cryptoCustomerBrokerReceiverPublicKey,
                PlatformComponentType.NETWORK_SERVICE,
                null,
                null,
                BusinessTransactionTransactionType.CONTRACT_STATUS_UPDATE,
                timestamp.getTime(),
                uuidTransactionId,
                TransactionTransmissionStates.UPDATE_CONTRACT
        );
        try {
            transactionTransmissionContractHashDao.saveBusinessTransmissionRecord(businessTransactionMetadata);
        } catch (CantInsertRecordDataBaseException e) {
            throw new CantSendContractNewStatusNotificationException(CantSendContractNewStatusNotificationException.DEFAULT_MESSAGE,e,"Cannot persists the contract hash in table","database corrupted");
        } catch (Exception e){
            throw new CantSendContractNewStatusNotificationException(CantSendContractNewStatusNotificationException.DEFAULT_MESSAGE,e,"Cannot persists the contract hash in table","database corrupted");
        }
    }

    @Override
    public void confirmNotificationReception(String cryptoBrokerActorSenderPublicKey,
                                             String cryptoCustomerActorReceiverPublicKey,
                                             String contractHash,
                                             String transactionId) throws CantConfirmNotificationReception {
        Date date=new Date();
        Timestamp timestamp=new Timestamp(date.getTime());
        UUID uuidTransactionId=UUID.fromString(transactionId);
        BusinessTransactionMetadata businessTransactionMetadata =new BusinessTransactionMetadataRecord(
                contractHash,
                null,
                cryptoBrokerActorSenderPublicKey,
                PlatformComponentType.NETWORK_SERVICE,
                cryptoCustomerActorReceiverPublicKey,
                PlatformComponentType.NETWORK_SERVICE,
                null,
                null,
                BusinessTransactionTransactionType.CONFIRM_MESSAGE,
                timestamp.getTime(),
                uuidTransactionId,
                TransactionTransmissionStates.CONFIRM_RESPONSE
        );
        try {
            transactionTransmissionContractHashDao.saveBusinessTransmissionRecord(businessTransactionMetadata);
        }  catch (CantInsertRecordDataBaseException e) {
            throw new CantConfirmNotificationReception(CantConfirmNotificationReception.DEFAULT_MESSAGE,e,"Cannot persists the contract hash in table","database corrupted");
        } catch (Exception e){
            throw new CantConfirmNotificationReception(CantConfirmNotificationReception.DEFAULT_MESSAGE,e,"Cannot persists the contract hash in table","database corrupted");
        }
    }

    @Override
    public void confirmReception(UUID transactionID) throws CantConfirmTransactionException {
        try {
            this.transactionTransmissionContractHashDao.confirmReception(transactionID);
        } catch (CantUpdateRecordDataBaseException e) {
            throw new CantConfirmTransactionException(e.DEFAULT_MESSAGE,e,"Confirm reception","Cannot update the flag in database");
        } catch (PendingRequestNotFoundException e) {
            throw new CantConfirmTransactionException(null,e,"Confirm reception","Cannot find the transaction id in database\n"+transactionID);
        } catch (CantGetTransactionTransmissionException e) {
            throw new CantConfirmTransactionException(e.DEFAULT_MESSAGE,e,"Confirm reception","Cannot get the business transaction record from the database");
        } catch (Exception e){
            throw new CantConfirmTransactionException(CantConfirmTransactionException.DEFAULT_MESSAGE,e,"Confirm reception","Cannot get the business transaction record from the database");
        }
    }

    @Override
    public List<Transaction<BusinessTransactionMetadata>> getPendingTransactions(Specialist specialist) throws CantDeliverPendingTransactionsException {
        List<Transaction<BusinessTransactionMetadata>> pendingTransaction=new ArrayList<>();
        try {

            Map<String, Object> filters = new HashMap<>();
            filters.put(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_PENDING_FLAG_COLUMN_NAME, "false");

            List<BusinessTransactionMetadata> businessTransactionMetadataList =transactionTransmissionContractHashDao.findAll(filters);
            if(!businessTransactionMetadataList.isEmpty()){

                for(BusinessTransactionMetadata businessTransactionMetadata : businessTransactionMetadataList){
                    Transaction<BusinessTransactionMetadata> transaction = new Transaction<>(businessTransactionMetadata.getTransactionId(),
                            businessTransactionMetadata,
                            Action.APPLY,
                            businessTransactionMetadata.getTimestamp());

                    pendingTransaction.add(transaction);
                }

            }
            return pendingTransaction;

        } catch (CantReadRecordDataBaseException e) {
            throw new CantDeliverPendingTransactionsException("CAN'T GET PENDING METADATA NOTIFICATIONS",e, "Transaction Transmission network service", "database error");
        } catch (Exception e) {
            throw new CantDeliverPendingTransactionsException("CAN'T GET PENDING METADATA NOTIFICATIONS",e, "Transaction Transmission network service", "database error");

        }
    }
}
