package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Negotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces.CustomerBrokerNew;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantGetNegotiationTransactionListException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerNewEventException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerNewNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.structure.CustomerBrokerNewImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 23.11.15.
 */
public class CustomerBrokerNewNegotiationTransactionDatabaseDao {
    private PluginDatabaseSystem pluginDatabaseSystem;

    private UUID pluginId;

    private Database database;

    public CustomerBrokerNewNegotiationTransactionDatabaseDao(final PluginDatabaseSystem pluginDatabaseSystem, final UUID pluginId, final Database database) {
        this.pluginDatabaseSystem   = pluginDatabaseSystem;
        this.pluginId               = pluginId;
        this.database               = database;
    }

    /*INITIALIZE DATABASE*/
    public void initialize() throws CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, CustomerBrokerNewNegotiationTransactionDatabaseConstants.DATABASE_NAME);
        } catch (DatabaseNotFoundException e) {
            try {
                CustomerBrokerNewNegotiationTransactionDatabaseFactory databaseFactory = new CustomerBrokerNewNegotiationTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, CustomerBrokerNewNegotiationTransactionDatabaseConstants.DATABASE_NAME);
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    /*TRANSACTION*/
    //CREATE SEND NEW NEGOTIATION TRANSACTION
    public void createCustomerBrokerNewNegotiationTransaction(UUID transactionId, Negotiation negotiation, NegotiationType negotiationType, NegotiationTransactionStatus statusTransaction) throws CantRegisterCustomerBrokerNewNegotiationTransactionException{

        try {

            Date time = new Date();
            long timestamp = time.getTime();
            String negotiationXML = XMLParser.parseObject(negotiation);

            DatabaseTable table = this.database.getTable(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();

            record.setUUIDValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TRANSACTION_ID_COLUMN_NAME, transactionId);
            record.setUUIDValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId());
            record.setStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_PUBLIC_KEY_BROKER_COLUMN_NAME, negotiation.getBrokerPublicKey());
            record.setStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_PUBLIC_KEY_CUSTOMER_COLUMN_NAME, negotiation.getCustomerPublicKey());
            record.setStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_STATUS_TRANSACTION_COLUMN_NAME, statusTransaction.getCode());
            record.setStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_NEGOTIATION_TYPE_COLUMN_NAME, negotiationType.getCode());
            record.setStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_NEGOTIATION_XML_COLUMN_NAME, negotiationXML);
            record.setLongValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TIMESTAMP_COLUMN_NAME, timestamp);

            table.insertRecord(record);

            if(statusTransaction.getCode() == NegotiationTransactionStatus.PENDING_SUBMIT_CONFIRM.getCode()) {
                System.out.print("\n\n**** 21) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - DAO. CONFIRM negotiationType: " + negotiationType.getCode() + " transactionId: " + transactionId + " ****\n");
            }else{
                System.out.print("\n\n**** 4) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - DAO. NEGOTIATION negotiationType: " + negotiationType.getCode() + "transactionId: " + transactionId + " ****\n");
            }

        } catch (CantInsertRecordException e){
            throw new CantRegisterCustomerBrokerNewNegotiationTransactionException (e.getMessage(), e, "Customer Broker New Negotiation Transaction", "Cant create new Customer Broker New Negotiation Transaction, insert database problems.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerNewNegotiationTransactionException (e.getMessage(), FermatException.wrapException(e), "Customer Broker New Negotiation Transaction", "Cant create new Customer Broker New Negotiation Transaction, unknown failure.");
        }

    }

    //UPDATE STATUS NEW NEGOTIATION TRANSACTION
    public void updateStatusRegisterCustomerBrokerNewNegotiationTranasction(UUID transactionId, NegotiationTransactionStatus statusTransaction) throws CantRegisterCustomerBrokerNewNegotiationTransactionException{
        try {
            if (!transactionExists(transactionId))
                throw new CantRegisterCustomerBrokerNewNegotiationTransactionException("Cant Update Status Customer Broker New Negotiation Transaction, not exists.", "Customer Broker New Negotiation Transaction, Update State", "Cant Update State Customer Broker New Negotiation Transaction, not exists");

            DatabaseTable table = this.database.getTable(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TABLE_NAME);
            table.addUUIDFilter(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
            DatabaseTableRecord record = table.getEmptyRecord();
            record.setStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_STATUS_TRANSACTION_COLUMN_NAME, statusTransaction.getCode());
            table.updateRecord(record);

        } catch (CantUpdateRecordException e) {
            throw new CantRegisterCustomerBrokerNewNegotiationTransactionException(e.getMessage(), e, "Customer Broker New Negotiation Transaction, Update State", "Cant Update State Customer Broker New Negotiation Transaction, update database problems.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerNewNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Customer Broker New Negotiation Transaction, Update State", "Cant Update State Customer Broker New Negotiation Transaction, unknown failure.");
        }
    }

    //GET NEW NEGOTIATION TRANSACTION
    public CustomerBrokerNew getRegisterCustomerBrokerNewNegotiationTranasction(UUID transactionId) throws CantRegisterCustomerBrokerNewNegotiationTransactionException{

        try {
            CustomerBrokerNew getTransaction = null;

            List<DatabaseTableRecord> record;
            DatabaseTable table = this.database.getTable(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TABLE_NAME);
            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant check if customer broker new exists", "Customer Broker New Negotiation Transaction", "");

            table.addUUIDFilter(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            record = table.getRecords();
            if (record.size() == 0)
                return getTransaction;

            for (DatabaseTableRecord records : record) {
                getTransaction = getCustomerBrokerNewFromRecord(records);
            }

            return getTransaction;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCustomerBrokerNewNegotiationTransactionException(em.getMessage(), em, "Customer Broker New Negotiation Transaction not return register", "Cant load " + CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerNewNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Customer Broker New Negotiation Transaction not return register", "unknown failure.");
        }
    }

    //GET NEW NEGOTIATION TRANSACTION
    public CustomerBrokerNew getRegisterCustomerBrokerNewNegotiationTranasctionFromNegotiationId(UUID negotiationId) throws CantRegisterCustomerBrokerNewNegotiationTransactionException{

        try {
            CustomerBrokerNew getTransaction = null;

            List<DatabaseTableRecord> record;
            DatabaseTable table = this.database.getTable(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TABLE_NAME);
            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant check if customer broker new exists", "Customer Broker New Negotiation Transaction", "");

            table.addUUIDFilter(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            record = table.getRecords();
            if (record.size() == 0)
                return getTransaction;

            for (DatabaseTableRecord records : record) {
                getTransaction = getCustomerBrokerNewFromRecord(records);
            }

            return getTransaction;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCustomerBrokerNewNegotiationTransactionException(em.getMessage(), em, "Customer Broker New Negotiation Transaction not return register", "Cant load " + CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerNewNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Customer Broker New Negotiation Transaction not return register", "unknown failure.");
        }
    }

    //GET LIST NEW NEGOTIATION TRANSACTION
    public List<CustomerBrokerNew> getAllRegisterCustomerBrokerNewNegotiationTranasction() throws CantRegisterCustomerBrokerNewNegotiationTransactionException{

        try {
            List<CustomerBrokerNew> getTransactions = new ArrayList<>();

            List<DatabaseTableRecord> record;
            DatabaseTable table = this.database.getTable(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TABLE_NAME);
            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant check if customer broker new exists", "Customer Broker New Negotiation Transaction", "");

            table.loadToMemory();
            record = table.getRecords();
            if (record.size() == 0)
                return getTransactions;

            for (DatabaseTableRecord records : record) {
                getTransactions.add(getCustomerBrokerNewFromRecord(records));
            }

            return getTransactions;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCustomerBrokerNewNegotiationTransactionException(em.getMessage(), em, "Customer Broker New Negotiation Transaction not return register", "Cant load " + CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerNewNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Customer Broker New Negotiation Transaction not return register", "unknown failure.");
        }
    }

    //GET LIST NEW NEGOTIATION TRANSACTION PENDING TO SUBMIT
    public List<CustomerBrokerNew> getPendingToSubmitNegotiation() throws CantGetNegotiationTransactionListException {

        try {
            List<CustomerBrokerNew> getTransactions = new ArrayList<>();

            List<DatabaseTableRecord> record;
            DatabaseTable table = this.database.getTable(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TABLE_NAME);
            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant check if customer broker new exists", "Customer Broker New Negotiation Transaction", "");

            table.addStringFilter(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_STATUS_TRANSACTION_COLUMN_NAME, NegotiationTransactionStatus.PENDING_SUBMIT.getCode(), DatabaseFilterType.EQUAL);
            table.loadToMemory();
            record = table.getRecords();
            if (record.isEmpty())
                return getTransactions;

            for (DatabaseTableRecord records : record) {
                getTransactions.add(getCustomerBrokerNewFromRecord(records));
            }

            return getTransactions;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantGetNegotiationTransactionListException(em.getMessage(), em, "Customer Broker New Negotiation Transaction not return register", "Cant load " + CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantGetNegotiationTransactionListException(e.getMessage(), FermatException.wrapException(e), "Customer Broker New Negotiation Transaction not return register", "unknown failure.");
        }
    }

    //GET LIST NEW NEGOTIATION TRANSACTION PENDING TO CONFIRM
    public List<CustomerBrokerNew> getPendingToConfirmtNegotiation() throws CantGetNegotiationTransactionListException {

        try {
            List<CustomerBrokerNew> getTransactions = new ArrayList<>();

            List<DatabaseTableRecord> record;
            DatabaseTable table = this.database.getTable(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TABLE_NAME);
            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant check if customer broker new exists", "Customer Broker New Negotiation Transaction", "");

            table.addStringFilter(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_STATUS_TRANSACTION_COLUMN_NAME, NegotiationTransactionStatus.PENDING_SUBMIT_CONFIRM.getCode(), DatabaseFilterType.EQUAL);
            table.loadToMemory();
            record = table.getRecords();
            if (record.isEmpty())
                return getTransactions;

            for (DatabaseTableRecord records : record) {
                getTransactions.add(getCustomerBrokerNewFromRecord(records));
            }

            return getTransactions;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantGetNegotiationTransactionListException(em.getMessage(), em, "Customer Broker New Negotiation Transaction not return register", "Cant load " + CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantGetNegotiationTransactionListException(e.getMessage(), FermatException.wrapException(e), "Customer Broker New Negotiation Transaction not return register", "unknown failure.");
        }
    }

    //GET TRANSACTION ID OF NEGOTIATION TRANSACTION
    public UUID getTransactionId(String negotiationId) throws UnexpectedResultReturnedFromDatabaseException {

        String transactionId= getValue(
                negotiationId,
                CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_NEGOTIATION_ID_COLUMN_NAME,
                CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TRANSACTION_ID_COLUMN_NAME);

        return UUID.fromString(transactionId);

    }

    //GET NEGOTIATION TYPE OF NEGOTIATION TRANSACTION
    public NegotiationType getNegotiationType(String negotiationId) throws UnexpectedResultReturnedFromDatabaseException {

        try {

            String contractTypeCode=getValue(
                    negotiationId,
                    CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_NEGOTIATION_ID_COLUMN_NAME,
                    CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_NEGOTIATION_TYPE_COLUMN_NAME);
            return NegotiationType.getByCode(contractTypeCode);

        } catch (InvalidParameterException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,"Getting Contract Type from database","The contractType in database is invalid");
        }

    }

    //GET NEGOTIATION XML OF NEGOTIATION TRANSACTION
    public String getNegotiationXML(String negotiationId) throws UnexpectedResultReturnedFromDatabaseException {
        return getValue(
                negotiationId,
                CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_NEGOTIATION_ID_COLUMN_NAME,
                CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_NEGOTIATION_XML_COLUMN_NAME);

    }

    //DELETE METHOD FOR TEST
    public void deleteAllForTest() throws CantRegisterCustomerBrokerNewNegotiationTransactionException {
        try {

            DatabaseTable table = this.database.getTable(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();
            table.deleteRecord(record);

        } catch (CantDeleteRecordException e) {
            throw new CantRegisterCustomerBrokerNewNegotiationTransactionException(e.getMessage(), e, "Negotiation Transaction, Customer Broker New", "Cant delete Register, delete database problems.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerNewNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Negotiation Transaction, Customer Broker New", "Cant delete Register, unknown failure.");
        }
    }
    /*END TRANSACTION*/

    /*EVENT*/
    //GET LIST PENDING EVENT
    public List<UUID> getPendingEvents() throws UnexpectedResultReturnedFromDatabaseException, CantGetNegotiationTransactionListException {

        try{

            DatabaseTable table = this.database.getTable(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_TABLE_NAME);
            List<UUID> eventTypeList=new ArrayList<>();
            UUID eventId;
            table.addStringFilter(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_STATUS_COLUMN_NAME,EventStatus.PENDING.getCode(),DatabaseFilterType.EQUAL);
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();
            if(records.isEmpty())
                return eventTypeList;
            for(DatabaseTableRecord databaseTableRecord : records){
                eventId=databaseTableRecord.getUUIDValue(
                        CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_ID_COLUMN_NAME);
                eventTypeList.add(eventId);
            }

            return eventTypeList;

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetNegotiationTransactionListException(CantGetNegotiationTransactionListException.DEFAULT_MESSAGE,e,"Getting events in EventStatus.PENDING","Cannot load the table into memory");
        }

    }

    //GET LIST PENDING EVENT
    public List<String> getAllEvents() throws UnexpectedResultReturnedFromDatabaseException, CantGetNegotiationTransactionListException {

        try{

            DatabaseTable table = this.database.getTable(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_TABLE_NAME);
            List<String> eventTypeList=new ArrayList<>();
            String eventString;
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();
            if(records.isEmpty())
                return eventTypeList;

            for(DatabaseTableRecord databaseTableRecord : records){
                eventString =   "\n - ID = "+databaseTableRecord.getUUIDValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_ID_COLUMN_NAME)+
                                "\n - STATUS = "+databaseTableRecord.getStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_STATUS_COLUMN_NAME)+", "+
                                "\n - TYPE = "+databaseTableRecord.getStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_TYPE_COLUMN_NAME)
                ;

                eventTypeList.add(eventString);
            }

            return eventTypeList;

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetNegotiationTransactionListException(CantGetNegotiationTransactionListException.DEFAULT_MESSAGE,e,"Getting events in EventStatus.PENDING","Cannot load the table into memory");
        }

    }

    //GET EVENT TYPE OF TRANSACTION
    public String getEventType(UUID eventId) throws UnexpectedResultReturnedFromDatabaseException {

        try{

            DatabaseTable table = this.database.getTable(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_TABLE_NAME);
            table.addUUIDFilter(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();
            checkDatabaseRecords(records);
            String value=records.get(0).getStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_TYPE_COLUMN_NAME);

            return value;

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,"Getting value from database","Cannot load the database table");
        }

    }

    //GET EVENT TYPE OF TRANSACTION
    public String getEventStatus(UUID eventId) throws UnexpectedResultReturnedFromDatabaseException {

        try{

            DatabaseTable table = this.database.getTable(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_TABLE_NAME);
            table.addUUIDFilter(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();
            checkDatabaseRecords(records);
            String value=records.get(0).getStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_STATUS_COLUMN_NAME);

            return value;

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,"Getting value from database","Cannot load the database table");
        }

    }

    //GET EVENT TYPE OF TRANSACTION
    public String getEvent(UUID eventId) throws UnexpectedResultReturnedFromDatabaseException {

        try{

            DatabaseTable table = this.database.getTable(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_TABLE_NAME);
            table.addUUIDFilter(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();
            checkDatabaseRecords(records);
            String value=records.get(0).getStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_TYPE_COLUMN_NAME)+", "+
                    records.get(0).getStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_STATUS_COLUMN_NAME);

            return value;

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,"Getting value from database","Cannot load the database table");
        }

    }

    /*SAVE NEW EVENT*/
    public void saveNewEventTransaction(String eventType, String eventSource) throws CantSaveEventException {

        try {

            UUID eventId = UUID.randomUUID();
            Date time = new Date();
            long timestamp = time.getTime();

            DatabaseTable table = this.database.getTable(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_TABLE_NAME);
            DatabaseTableRecord eventRecord = table.getEmptyRecord();

            eventRecord.setUUIDValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_ID_COLUMN_NAME, eventId);
            eventRecord.setStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_TYPE_COLUMN_NAME, eventType);
            eventRecord.setStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_TIMESTAMP_COLUMN_NAME, timestamp);

            table.insertRecord(eventRecord);

//            System.out.print("\n\n**** 17) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - DAO - REGISTER NEW EVENT ****\n");

        } catch (CantInsertRecordException exception) {
            throw new CantSaveEventException(exception, "Saving new event.", "Cannot insert a record in Asset Distribution database");
        } catch(Exception exception){
            throw new CantSaveEventException(FermatException.wrapException(exception), "Saving new event.", "Unexpected exception");
        }

    }

    /*UPDATE STATUS EVENT*/
    public void updateEventTansactionStatus(UUID eventId, EventStatus eventStatus) throws CantRegisterCustomerBrokerNewEventException, CantUpdateRecordException {
        try{

            if (!eventExists(eventId))
                throw new CantRegisterCustomerBrokerNewEventException("Cant Update Status Customer Broker New Negotiation Transaction, Event Id not exists.", "Customer Broker New Negotiation Transaction, Update Event State", "Cant Update Event State Customer Broker New Negotiation Transaction, Id not exists");

            DatabaseTable table = this.database.getTable(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_TABLE_NAME);
            table.addUUIDFilter(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            DatabaseTableRecord record = table.getEmptyRecord();
            record.setStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_STATUS_COLUMN_NAME, eventStatus.getCode());

            table.updateRecord(record);

//            System.out.print("\n\n**** 19.1) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - DAO - REGISTER NEW EVENT ****\n");

        }  catch (CantLoadTableToMemoryException e) {
            throw new CantRegisterCustomerBrokerNewEventException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, e, "Customer Broker New Negotiation Transaction Update Event Status Not Found", "unknown failure");
        }
    }
    /*END EVENT*/
    
    /*PRIVATE METHOD*/
    private boolean eventExists(UUID eventId) throws CantRegisterCustomerBrokerNewEventException {

        try {

            DatabaseTable table = this.database.getTable(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_TABLE_NAME);
            if (table == null)
                throw new CantRegisterCustomerBrokerNewEventException("Cant check if event tablet exists", "Customer Broker New Negotiation Transaction", "");

            table.addUUIDFilter(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_EVENT_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            return table.getRecords().size() > 0;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCustomerBrokerNewEventException(em.getMessage(), em, "Customer Broker New Negotiation Transaction Event Id Not Exists", "Cant load " + CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerNewEventException(e.getMessage(), FermatException.wrapException(e), "Customer Broker New Negotiation Transaction Event Id Not Exists", "unknown failure.");
        }

    }

    private boolean transactionExists(UUID transactionId) throws CantRegisterCustomerBrokerNewNegotiationTransactionException {

        try {

            DatabaseTable table = this.database.getTable(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TABLE_NAME);
            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant check if customer broker new tablet exists", "Customer Broker New Negotiation Transaction", "");

            table.addUUIDFilter(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            return table.getRecords().size() > 0;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCustomerBrokerNewNegotiationTransactionException(em.getMessage(), em, "Customer Broker New Negotiation Transaction Id Not Exists", "Cant load " + CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerNewNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Customer Broker New Negotiation Transaction Id Not Exists", "unknown failure.");
        }

    }

    private CustomerBrokerNew getCustomerBrokerNewFromRecord(DatabaseTableRecord record) throws InvalidParameterException{

        UUID                            transactionId       = record.getUUIDValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TRANSACTION_ID_COLUMN_NAME);
        UUID                            negotiationId       = record.getUUIDValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_NEGOTIATION_ID_COLUMN_NAME);
        String                          publicKeyBroker     = record.getStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_PUBLIC_KEY_BROKER_COLUMN_NAME);
        String                          publicKeyCustomer   = record.getStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_PUBLIC_KEY_CUSTOMER_COLUMN_NAME);
        NegotiationTransactionStatus    status              = NegotiationTransactionStatus.getByCode(record.getStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_STATUS_TRANSACTION_COLUMN_NAME));
        NegotiationType                 negotiationType     = NegotiationType.getByCode(record.getStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_NEGOTIATION_TYPE_COLUMN_NAME));
        String                          negotiationXML      = record.getStringValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_NEGOTIATION_XML_COLUMN_NAME);
        long                            timestamp           = record.getLongValue(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TIMESTAMP_COLUMN_NAME);

        return new CustomerBrokerNewImpl(transactionId,negotiationId,publicKeyBroker,publicKeyCustomer,status,negotiationType,negotiationXML,timestamp);
    }

    private String getValue(String key,String keyColumn,String valueColumn) throws UnexpectedResultReturnedFromDatabaseException {
        try{

            DatabaseTable table = this.database.getTable(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TABLE_NAME);
            table.addStringFilter(keyColumn, key, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();
            checkDatabaseRecords(records);
            String value=records.get(0).getStringValue(valueColumn);

            return value;

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        }

    }

    private List<String> getStringList(String key,String keyColumn,String valueColumn) throws CantGetNegotiationTransactionListException {
        try{

            DatabaseTable table = this.database.getTable(CustomerBrokerNewNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_NEW_TABLE_NAME);
            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException ("CANT GET NEGOTIATION TRANSACTION LISt. TABLE NO FOUNT.", "NEGOTIATION TRANSACTION CUSTOMER BROKER NEW", "CANT GET NEGOTIATION TRANSACTION LIST, TABLE NO FOUNT.");

            List<String> negotiationList=new ArrayList<>();
            String negotiation;
            table.addStringFilter(keyColumn,key,DatabaseFilterType.EQUAL);
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();
            if(records.isEmpty()){
                //There is no records in database, I'll return an empty list.
                return negotiationList;
            }

            for(DatabaseTableRecord databaseTableRecord : records){
                negotiation = databaseTableRecord.getStringValue(valueColumn);
                negotiationList.add(negotiation);
            }

            return negotiationList;

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetNegotiationTransactionListException(CantGetNegotiationTransactionListException.DEFAULT_MESSAGE,e,"Getting "+valueColumn+" based on "+key,"Cannot load the table into memory");
        } catch (Exception e){
            throw new CantGetNegotiationTransactionListException(e.getMessage(), FermatException.wrapException(e),"Getting "+valueColumn+" based on "+key,"Cannot load the table into memory");
        }
    }

    private void checkDatabaseRecords(List<DatabaseTableRecord> records) throws UnexpectedResultReturnedFromDatabaseException {
        //Represents the maximum number of records in records
        int validResultsNumber = 1;
        int recordsSize;
        if(records.isEmpty()){ return; }
        recordsSize = records.size();
        if(recordsSize > validResultsNumber){
            throw new UnexpectedResultReturnedFromDatabaseException("I excepted "+validResultsNumber+", but I got "+recordsSize);
        }
    }
}
