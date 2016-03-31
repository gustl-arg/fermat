package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartServiceException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationPurchaseRecord;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction.NegotiationSaleRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks.ClauseMock;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListPurchaseNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListSaleNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.Test.mocks.PurchaseNegotiationMock;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.Test.mocks.SaleNegotiationMock;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.interfaces.CustomerBrokerClose;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.events.IncomingNegotiationTransactionEvent;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.database.CustomerBrokerCloseNegotiationTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.database.CustomerBrokerCloseNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.database.CustomerBrokerCloseNegotiationTransactionDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.database.CustomerBrokerCloseNegotiationTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.event_handler.CustomerBrokerCloseServiceEventHandler;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerCloseNegotiationTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerCloseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure.CustomerBrokerCloseAgent;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure.CustomerBrokerCloseManagerImpl;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by Yordin Alayn on 16.09.15.
 */

public class NegotiationTransactionCustomerBrokerClosePluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers{

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM,           layer = Layers.PLATFORM_SERVICE,    addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API,        layer = Layers.SYSTEM,              addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API,        layer = Layers.SYSTEM,              addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM,           layer = Layers.PLATFORM_SERVICE,    addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM,     layer = Layers.NEGOTIATION,         plugin = Plugins.NEGOTIATION_PURCHASE)
    private CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM,     layer = Layers.NEGOTIATION,         plugin = Plugins.NEGOTIATION_SALE)
    private CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM,     layer = Layers.NETWORK_SERVICE,     plugin = Plugins.NEGOTIATION_TRANSMISSION)
    private NegotiationTransmissionManager negotiationTransmissionManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS,                layer = Layers.CRYPTO_MODULE,       plugin = Plugins.CRYPTO_ADDRESS_BOOK)
    private CryptoAddressBookManager cryptoAddressBookManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS,                layer = Layers.CRYPTO_VAULT,        plugin = Plugins.BITCOIN_VAULT)
    private CryptoVaultManager cryptoVaultManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM,   layer = Layers.MIDDLEWARE,          plugin = Plugins.WALLET_MANAGER)
    private WalletManagerManager walletManagerManager;

    /*Represent the dataBase*/
    private Database                                                            dataBase;

    /*Represent DeveloperDatabaseFactory*/
    private CustomerBrokerCloseNegotiationTransactionDeveloperDatabaseFactory   customerBrokerCloseNegotiationTransactionDeveloperDatabaseFactory;

    /*Represent CustomerBrokerNewNegotiationTransactionDatabaseDao*/
    private CustomerBrokerCloseNegotiationTransactionDatabaseDao                customerBrokerCloseNegotiationTransactionDatabaseDao;

    /*Represent Customer Broker New Manager*/
    private CustomerBrokerCloseManagerImpl                                      customerBrokerCloseManagerImpl;

    /*Represent Agent*/
    private CustomerBrokerCloseAgent                                            customerBrokerCloseAgent;

    /*Represent the Negotiation Purchase*/
    private CustomerBrokerPurchaseNegotiation                                   customerBrokerPurchaseNegotiation;

    /*Represent the Negotiation Sale*/
    private CustomerBrokerSaleNegotiation                                       customerBrokerSaleNegotiation;

    /*Represent Service Event Handler*/
    private CustomerBrokerCloseServiceEventHandler                              customerBrokerCloseServiceEventHandler;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    public NegotiationTransactionCustomerBrokerClosePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

     /*IMPLEMENTATION Service.*/
     @Override
     public void start() throws CantStartPluginException {
         try {

             //Initialize database
             initializeDb();

             //Initialize Developer Database Factory
             customerBrokerCloseNegotiationTransactionDeveloperDatabaseFactory = new CustomerBrokerCloseNegotiationTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
             customerBrokerCloseNegotiationTransactionDeveloperDatabaseFactory.initializeDatabase();

             //Initialize Dao
             customerBrokerCloseNegotiationTransactionDatabaseDao = new CustomerBrokerCloseNegotiationTransactionDatabaseDao(pluginDatabaseSystem, pluginId, dataBase);

             //Initialize manager
             customerBrokerCloseManagerImpl = new CustomerBrokerCloseManagerImpl(
                     customerBrokerCloseNegotiationTransactionDatabaseDao,
                     customerBrokerPurchaseNegotiationManager,
                     customerBrokerSaleNegotiationManager,
                     cryptoAddressBookManager,
                     cryptoVaultManager,
                     walletManagerManager);

             //Init event recorder service.
             customerBrokerCloseServiceEventHandler = new CustomerBrokerCloseServiceEventHandler(customerBrokerCloseNegotiationTransactionDatabaseDao, eventManager);
             customerBrokerCloseServiceEventHandler.start();

             //Init monitor Agent
             customerBrokerCloseAgent = new CustomerBrokerCloseAgent(
                     pluginDatabaseSystem,
                     logManager,
                     errorManager,
                     eventManager,
                     pluginId,
                     negotiationTransmissionManager,
                     customerBrokerPurchaseNegotiation,
                     customerBrokerSaleNegotiation,
                     customerBrokerPurchaseNegotiationManager,
                     customerBrokerSaleNegotiationManager,
                     cryptoAddressBookManager,
                     cryptoVaultManager,
                     walletManagerManager
             );
             customerBrokerCloseAgent.start();

             //TEST
             //TEST CREATE CUSTOMER BROKER CLOSE PURCHASE NEGOTIATION
//             createCustomerBrokerClosePurchaseNegotiationTest();

             //TEST CREATE CUSTOMER BROKER CLOSE SALE NEGOTIATION
//             createCustomerBrokerCloseSaleNegotiationTest();
//
             //TEST LIST PURCHASE NEGOTIATION
//             getAllPurchaseNegotiationTest();

             //TEST LIST PURCHASE NEGOTIATION
//             getAllSaleNegotiationTest();

             //TEST EVENT REGISTER
//             registerEventTest();

             //TEST GET ALL EVENT
//            getAllEvent();

             //Startes Service
             this.serviceStatus = ServiceStatus.STARTED;
//             System.out.print("-----------------------\n CUSTOMER BROKER CLOSE: SUCCESSFUL START \n-----------------------\n");

         } catch (CantInitializeCustomerBrokerCloseNegotiationTransactionDatabaseException e){
             errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_CLOSE,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,e);
             throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE,FermatException.wrapException(e),"Error Starting Customer Broker Close PluginRoot - Database","Unexpected Exception");
         } catch (CantStartServiceException e){
             errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_CLOSE,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,e);
             throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE,FermatException.wrapException(e),"Error Starting Customer Broker Close PluginRoot - EventHandler","Unexpected Exception");
         } catch (Exception e) {
             errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_CLOSE,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,e);
             throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE,FermatException.wrapException(e),"Error Starting Customer Broker Close PluginRoot","Unexpected Exception");
         }
     }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public FermatManager getManager() {
        return customerBrokerCloseManagerImpl;
    }
    /*END IMPLEMENTATION Service.*/

    /*IMPLEMENTATION DatabaseManagerForDevelopers*/
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return new CustomerBrokerCloseNegotiationTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return new CustomerBrokerCloseNegotiationTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try{
            return new CustomerBrokerCloseNegotiationTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }
    /*END IMPLEMENTATION DatabaseManagerForDevelopers*/

    /*IMPLEMENTATION LogManagerForDevelopers*/
    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.NegotiationTransactionCustomerBrokerClosePluginRoot");
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

        //I will check the current values and update the LogLevel in those which is different
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {

            //if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
            if (NegotiationTransactionCustomerBrokerClosePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                NegotiationTransactionCustomerBrokerClosePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                NegotiationTransactionCustomerBrokerClosePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                NegotiationTransactionCustomerBrokerClosePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    public static LogLevel getLogLevelByClass(String className) {
        try{
            //sometimes the classname may be passed dynamically with an $moretext. I need to ignore whats after this.
            String[] correctedClass = className.split((Pattern.quote("$")));
            return NegotiationTransactionCustomerBrokerClosePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e){
            //If I couldn't get the correct logging level, then I will set it to minimal.
            return DEFAULT_LOG_LEVEL;
        }
    }
    /*END IMPLEMENTATION LogManagerForDevelopers*/

    /*PRIVATE METHOD*/
    private void initializeDb() throws CantInitializeCustomerBrokerCloseNegotiationTransactionDatabaseException {
        try {
            dataBase = this.pluginDatabaseSystem.openDatabase(this.pluginId, CustomerBrokerCloseNegotiationTransactionDatabaseConstants.DATABASE_NAME);
        } catch (DatabaseNotFoundException e) {
            try {
                CustomerBrokerCloseNegotiationTransactionDatabaseFactory databaseFactory = new CustomerBrokerCloseNegotiationTransactionDatabaseFactory(pluginDatabaseSystem);
                dataBase = databaseFactory.createDatabase(pluginId, CustomerBrokerCloseNegotiationTransactionDatabaseConstants.DATABASE_NAME);
            } catch (CantCreateDatabaseException f) {
                errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_CLOSE,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,f);
                throw new CantInitializeCustomerBrokerCloseNegotiationTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_CLOSE,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,z);
                throw new CantInitializeCustomerBrokerCloseNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_CLOSE,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,e);
            throw new CantInitializeCustomerBrokerCloseNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_BROKER_CLOSE,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,e);
            throw new CantInitializeCustomerBrokerCloseNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }
    /*END PRIVATE METHOD*/

    /*TEST METHOD*/

    //TEST CREATE CUSTOMER BROKER CLOSE PURCHASE NEGOTIATION
    private void createCustomerBrokerClosePurchaseNegotiationTest() {

        try {

            System.out.print("\n**** MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - PLUGINROOT - PURCHASE NEGOTIATION TEST: createCustomerBrokerUpdatePurchaseNegotiationTranasction() ****\n");

            CustomerBrokerPurchaseNegotiation negotiationMock = purchaseNegotiationMockTest();
            System.out.print("\n\n**** 1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - PLUGINROOT - PURCHASE NEGOTIATION ****\n" +
                            "\n------------------------------- NEGOTIATION PURCHASE MOCK -------------------------------" +
                            "\n*CustomerPublicKey = " + negotiationMock.getCustomerPublicKey() +
                            "\n*BrokerPublicKey = " + negotiationMock.getBrokerPublicKey()
            );

            //NEGOTIATION XML TEST
            String negotiationMockXML = XMLParser.parseObject(negotiationMock);
            System.out.print("\n\n --- NegotiationMockXML = " + negotiationMockXML);
            CustomerBrokerPurchaseNegotiation purchaseNegotiationXML = new NegotiationPurchaseRecord();
            purchaseNegotiationXML = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(negotiationMockXML, purchaseNegotiationXML);
            System.out.print("\n\n --- Negotiation Mock XML Date" +
                            "\n- NegotiationId = " + purchaseNegotiationXML.getNegotiationId() +
                            "\n- CustomerPublicKey = " + purchaseNegotiationXML.getCustomerPublicKey() +
                            "\n- BrokerPublicKey = " + purchaseNegotiationXML.getCustomerPublicKey()
            );

            //CREATE CUSTOMER BROKER CLOSE NEGOTIATION.
            customerBrokerCloseManagerImpl.createCustomerBrokerClosePurchaseNegotiationTranasction(negotiationMock);

            //GET TRANSACTION OF NEGOTIATION
            System.out.print("\n\n\n\n------------------------------- NEGOTIATION TRANSACTION -------------------------------");
            CustomerBrokerClose ListNegotiation = customerBrokerCloseNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerCloseNegotiationTranasctionFromNegotiationId(negotiationMock.getNegotiationId());
            System.out.print("\n\n --- Negotiation Transaction Date" +
                            "\n- NegotiationId = " + ListNegotiation.getNegotiationId() +
                            "\n- TransactionId = " + ListNegotiation.getTransactionId() +
                            "\n- CustomerPublicKey = " + ListNegotiation.getPublicKeyCustomer() +
                            "\n- BrokerPublicKey = " + ListNegotiation.getPublicKeyBroker() +
                            "\n- NegotiationType = " + ListNegotiation.getNegotiationType().getCode() +
                            "\n- StatusTransaction = " + ListNegotiation.getStatusTransaction().getCode()
            );

            //GET NEGOTIATION OF XML
            System.out.print("\n\n\n\n------------------------------- NEGOTIATION XML -------------------------------");
            if (ListNegotiation.getNegotiationXML() != null) {
                System.out.print("\n- NegotiationXML = " + ListNegotiation.getNegotiationXML());
                CustomerBrokerPurchaseNegotiation purchaseNegotiationTestXML = new NegotiationPurchaseRecord();
                purchaseNegotiationXML = (CustomerBrokerPurchaseNegotiation) XMLParser.parseXML(ListNegotiation.getNegotiationXML(), purchaseNegotiationTestXML);
                if(purchaseNegotiationXML.getNegotiationId() != null) {
                    System.out.print("\n\n\n --- NegotiationXML Date" +
                                    "\n- NegotiationId = " + purchaseNegotiationXML.getNegotiationId() +
                                    "\n- CustomerPublicKey = " + purchaseNegotiationXML.getCustomerPublicKey() +
                                    "\n- BrokerPublicKey = " + purchaseNegotiationXML.getBrokerPublicKey() +
                                    "\n- Status = " + purchaseNegotiationXML.getStatus().getCode()
                    );
                }else{ System.out.print("\n\n\n --- NegotiationXML Date: purchaseNegotiationXML IS NOT INSTANCE OF NegotiationPurchaseRecord");}
            }else{ System.out.print("\n\n\n --- NegotiationXML Date IS NULL"); }

        } catch (CantCreateCustomerBrokerPurchaseNegotiationException e) {
            System.out.print("\n**** MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE. PLUGIN ROOT. ERROR CREATE CUSTOMER BROKER CLOSE. ****\n");
        } catch (CantRegisterCustomerBrokerCloseNegotiationTransactionException e){
            System.out.print("\n**** MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE. PLUGIN ROOT. ERROR LIST CUSTOMER BROKER CLOSE NOT FOUNT. ****\n");
        }

    }

    //TEST CREATE CUSTOMER BROKER CLOSE SALE NEGOTIATION
    private void createCustomerBrokerCloseSaleNegotiationTest() {

        try {

            System.out.print("\n**** MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - PLUGINROOT - SALE NEGOTIATION TEST: createCustomerBrokerClosePurchaseNegotiationTranasction() ****\n");

            CustomerBrokerSaleNegotiation negotiationMock = saleNegotiationMockTest();
            System.out.print("\n\n**** 1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - PLUGINROOT - SALE NEGOTIATION ****\n" +
                            "\n------------------------------- NEGOTIATION SALE MOCK -------------------------------" +
                            "\n*CustomerPublicKey = " + negotiationMock.getCustomerPublicKey() +
                            "\n*BrokerPublicKey = " + negotiationMock.getBrokerPublicKey()
            );

            //NEGOTIATION XML TEST
            String negotiationMockXML = XMLParser.parseObject(negotiationMock);
            System.out.print("\n\n --- NegotiationMockXML = " + negotiationMockXML);
            CustomerBrokerSaleNegotiation saleNegotiationXML = new NegotiationSaleRecord();
            saleNegotiationXML = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(negotiationMockXML, saleNegotiationXML);
            System.out.print("\n\n --- Negotiation Mock XML Date" +
                            "\n- NegotiationId = " + saleNegotiationXML.getNegotiationId() +
                            "\n- CustomerPublicKey = " + saleNegotiationXML.getCustomerPublicKey() +
                            "\n- BrokerPublicKey = " + saleNegotiationXML.getCustomerPublicKey()
            );

            //CREATE CUSTOMER BROKER CLOSE NEGOTIATION.
            customerBrokerCloseManagerImpl.createCustomerBrokerCloseSaleNegotiationTranasction(negotiationMock);

            //GET TRANSACTION OF NEGOTIATION
            System.out.print("\n\n\n\n------------------------------- NEGOTIATION TRANSACTION -------------------------------");
            CustomerBrokerClose ListNegotiation = customerBrokerCloseNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerCloseNegotiationTranasctionFromNegotiationId(negotiationMock.getNegotiationId());
            System.out.print("\n\n --- Negotiation Transaction Date" +
                            "\n- NegotiationId = " + ListNegotiation.getNegotiationId() +
                            "\n- TransactionId = " + ListNegotiation.getTransactionId() +
                            "\n- CustomerPublicKey = " + ListNegotiation.getPublicKeyCustomer() +
                            "\n- BrokerPublicKey = " + ListNegotiation.getPublicKeyBroker() +
                            "\n- NegotiationType = " + ListNegotiation.getNegotiationType().getCode() +
                            "\n- StatusTransaction = " + ListNegotiation.getStatusTransaction().getCode()
            );

            //GET NEGOTIATION OF XML
            System.out.print("\n\n\n\n------------------------------- NEGOTIATION XML -------------------------------");
            if (ListNegotiation.getNegotiationXML() != null) {
                System.out.print("\n- NegotiationXML = " + ListNegotiation.getNegotiationXML());
                CustomerBrokerSaleNegotiation saleNegotiationTestXML = new NegotiationSaleRecord();
                saleNegotiationXML = (CustomerBrokerSaleNegotiation) XMLParser.parseXML(ListNegotiation.getNegotiationXML(), saleNegotiationTestXML);
                if(saleNegotiationXML.getNegotiationId() != null) {
                    System.out.print("\n\n\n --- NegotiationXML Date" +
                                    "\n- NegotiationId = " + saleNegotiationXML.getNegotiationId() +
                                    "\n- CustomerPublicKey = " + saleNegotiationXML.getCustomerPublicKey() +
                                    "\n- BrokerPublicKey = " + saleNegotiationXML.getBrokerPublicKey() +
                                    "\n- Status = " + saleNegotiationXML.getStatus().getCode()
                    );
                }else{ System.out.print("\n\n\n --- NegotiationXML Date: saleNegotiationXML IS NOT INSTANCE OF NegotiationPurchaseRecord");}
            }else{ System.out.print("\n\n\n --- NegotiationXML Date IS NULL"); }

        } catch (CantCreateCustomerBrokerSaleNegotiationException e) {
            System.out.print("\n**** MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE. PLUGIN ROOT. ERROR CREATE CUSTOMER BROKER CLOSE. ****\n");
        } catch (CantRegisterCustomerBrokerCloseNegotiationTransactionException e){
            System.out.print("\n**** MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE. PLUGIN ROOT. ERROR LIST CUSTOMER BROKER CLOSE NOT FOUNT. ****\n");
        }

    }

    //TEST GET ALL NEGOTIATION
    private void getAllPurchaseNegotiationTest(){

        try{

            System.out.print("\n**** MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - PLUGINROOT - LIST CUSTOMER BROKER PURCHASE NEGOTIATION ****\n");

            Collection<CustomerBrokerPurchaseNegotiation> negotiationCollection =customerBrokerPurchaseNegotiationManager.getNegotiations();
            for (CustomerBrokerPurchaseNegotiation negotiation: negotiationCollection){

                System.out.print("\n\n\n --- Negotiation Date" +
                                "\n- NegotiationId = " + negotiation.getNegotiationId() +
                                "\n- CustomerPublicKey = " + negotiation.getCustomerPublicKey() +
                                "\n- BrokerPublicKey = " + negotiation.getBrokerPublicKey() +
                                "\n- Status = " + negotiation.getStatus().getCode()
                );

            }

        } catch (CantGetListPurchaseNegotiationsException e) {
            System.out.print("\n**** MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE. PLUGIN ROOT. ERROR LIST CUSTOMER BROKER PURCHASE NEGOTIATION. ****\n");
        }

    }

    //TEST GET ALL NEGOTIATION
    private void getAllSaleNegotiationTest(){

        try{

            System.out.print("\n**** MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - PLUGINROOT - LIST CUSTOMER BROKER SALE NEGOTIATION ****\n");

            Collection<CustomerBrokerSaleNegotiation> negotiationCollection = customerBrokerSaleNegotiationManager.getNegotiations();
            for (CustomerBrokerSaleNegotiation negotiation: negotiationCollection){

                System.out.print("\n\n\n --- Negotiation Date" +
                                "\n- NegotiationId = " + negotiation.getNegotiationId() +
                                "\n- CustomerPublicKey = " + negotiation.getCustomerPublicKey() +
                                "\n- BrokerPublicKey = " + negotiation.getBrokerPublicKey() +
                                "\n- Status = " + negotiation.getStatus().getCode()
                );

            }

        } catch (CantGetListSaleNegotiationsException e) {
            System.out.print("\n**** MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE. PLUGIN ROOT. ERROR LIST CUSTOMER BROKER SALE NEGOTIATION. ****\n");
        }

    }

    //TEST OTHER
    private CustomerBrokerPurchaseNegotiation purchaseNegotiationMockTest(){

        Date time = new Date();
        long timestamp = time.getTime();
        UUID negotiationId                              = UUID.fromString("eac97ab3-034e-4e57-93dc-b9f4ccaf1a74");
        String              publicKeyCustomer           = "30C5580D5A807CA38771A7365FC2141A6450556D5233DD4D5D14D4D9CEE7B9715B98951C2F28F820D858898AE0CBCE7B43055AB3C506A804B793E230610E711AEA";
        String              publicKeyBroker             = "041FCC359F748B5074D5554FA4DBCCCC7981D6776E57B5465DB297775FB23DBBF064FCB11EDE1979FC6E02307E4D593A81D2347006109F40B21B969E0E290C3B84";
        long                startDataTime               = 0;
        long                negotiationExpirationDate   = timestamp;
        NegotiationStatus statusNegotiation             = NegotiationStatus.SENT_TO_BROKER;
        Collection<Clause> clauses                      = getClausesTest();
        Boolean             nearExpirationDatetime      = Boolean.FALSE;

        return new PurchaseNegotiationMock(
                negotiationId,
                publicKeyCustomer,
                publicKeyBroker,
                startDataTime,
                negotiationExpirationDate,
                statusNegotiation,
                clauses,
                nearExpirationDatetime,
                timestamp
        );
    }

    private CustomerBrokerSaleNegotiation saleNegotiationMockTest(){

        Date time = new Date();
        long timestamp = time.getTime();
        UUID negotiationId                              = UUID.fromString("eac97ab3-034e-4e57-93dc-b9f4ccaf1a74");
        String              publicKeyCustomer           = "30C5580D5A807CA38771A7365FC2141A6450556D5233DD4D5D14D4D9CEE7B9715B98951C2F28F820D858898AE0CBCE7B43055AB3C506A804B793E230610E711AEA";
        String              publicKeyBroker             = "041FCC359F748B5074D5554FA4DBCCCC7981D6776E57B5465DB297775FB23DBBF064FCB11EDE1979FC6E02307E4D593A81D2347006109F40B21B969E0E290C3B84";
        long                startDataTime               = 0;
        long                negotiationExpirationDate   = timestamp;
        NegotiationStatus statusNegotiation             = NegotiationStatus.SENT_TO_BROKER;
        Collection<Clause> clauses                      = getClausesTest();
        Boolean             nearExpirationDatetime      = Boolean.FALSE;

        return new SaleNegotiationMock(
                negotiationId,
                publicKeyCustomer,
                publicKeyBroker,
                startDataTime,
                negotiationExpirationDate,
                statusNegotiation,
                clauses,
                nearExpirationDatetime,
                timestamp
        );
    }

    private Collection<Clause> getClausesTest(){
        Collection<Clause> clauses = new ArrayList<>();
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_CURRENCY,
                MoneyType.BANK.getCode()));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_CURRENCY_QUANTITY,
                "1961"));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_CURRENCY,
                MoneyType.BANK.getCode()));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_DATE_TIME_TO_DELIVER,
                "1000"));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.CUSTOMER_CURRENCY_QUANTITY,
                "2000"));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.CUSTOMER_CURRENCY,
                MoneyType.CASH_ON_HAND.getCode()));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER,
                "100"));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.CUSTOMER_PAYMENT_METHOD,
                ContractClauseType.CASH_ON_HAND.getCode()));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_PAYMENT_METHOD,
                ContractClauseType.BANK_TRANSFER.getCode()));
        return clauses;
    }

    //TEST REGISTER EVENT
    private void registerEventTest(){

        try {

            System.out.print("\n**** MOCK CUSTOMER BROKER UPDATE. REGISTER EVENT. EVENT REGISTER. ****\n");
            IncomingNegotiationTransactionEvent eventTest = new IncomingNegotiationTransactionEvent(EventType.INCOMING_NEGOTIATION_TRANSMISSION_TRANSACTION_CLOSE);
            eventTest.setSource(EventSource.NETWORK_SERVICE_NEGOTIATION_TRANSMISSION);

            customerBrokerCloseServiceEventHandler.incomingNegotiationTransactionEventHandler(eventTest);

        } catch (CantSaveEventException e) {
            System.out.print("\n**** MOCK CUSTOMER BROKER NEW. REGISTER EVENT. ERROR IN EVENT REGISTER. ****\n");
        }
    }
    /*TEST METHOD*/
}