package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantCreateCustomerBrokerNewSaleNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantGetCustomerBrokerNewNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantGetListCustomerBrokerNewNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces.CustomerBrokerNew;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces.CustomerBrokerNewManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database.CustomerBrokerNewNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantNewPurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantNewSaleNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerNewNegotiationTransactionException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 08.12.15.
 */

public class CustomerBrokerNewManagerImpl implements CustomerBrokerNewManager {

    /*Represent the Transaction database DAO */
    private CustomerBrokerNewNegotiationTransactionDatabaseDao  customerBrokerNewNegotiationTransactionDatabaseDao;

    /*Represent the Transaction Negotiation Purchase*/
    private CustomerBrokerNewPurchaseNegotiationTransaction     customerBrokerNewPurchaseNegotiationTransaction;

    /*Represent the Transaction Negotiation Sale*/
    private CustomerBrokerNewSaleNegotiationTransaction         customerBrokerNewSaleNegotiationTransaction;

    /*Represent the Negotiation Purchase*/
    private CustomerBrokerPurchaseNegotiationManager            customerBrokerPurchaseNegotiationManager;

    /*Represent the Negotiation Sale*/
    private CustomerBrokerSaleNegotiationManager                customerBrokerSaleNegotiationManager;

    /*Represent the Error Manager*/
    private ErrorManager                                        errorManager;

    /*Represent Plugin Version*/
    private PluginVersionReference                              pluginVersionReference;

    public CustomerBrokerNewManagerImpl(
        CustomerBrokerNewNegotiationTransactionDatabaseDao  customerBrokerNewNegotiationTransactionDatabaseDao,
        CustomerBrokerPurchaseNegotiationManager            customerBrokerPurchaseNegotiationManager,
        CustomerBrokerSaleNegotiationManager                customerBrokerSaleNegotiationManager,
        ErrorManager                                        errorManager,
        PluginVersionReference                              pluginVersionReference
    ){
        this.customerBrokerNewNegotiationTransactionDatabaseDao = customerBrokerNewNegotiationTransactionDatabaseDao;
        this.customerBrokerPurchaseNegotiationManager           = customerBrokerPurchaseNegotiationManager;
        this.customerBrokerSaleNegotiationManager               = customerBrokerSaleNegotiationManager;
        this.errorManager                                       = errorManager;
        this.pluginVersionReference                             = pluginVersionReference;
    }

    /*IMPLEMENTATION CustomerBrokerNewManager*/
    @Override
    public void createCustomerBrokerNewPurchaseNegotiationTransaction(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException {
        try {

            //VALIDATE NOT REPEAT NEGOTIATION
            if(customerBrokerNewNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerNewNegotiationTranasctionFromNegotiationId(customerBrokerPurchaseNegotiation.getNegotiationId()) == null) {

                System.out.print("\n\n**** 2) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER NEW - MANAGER - PURCHASE NEGOTIATION****\n");
                customerBrokerNewPurchaseNegotiationTransaction = new CustomerBrokerNewPurchaseNegotiationTransaction(
                    customerBrokerPurchaseNegotiationManager,
                    customerBrokerNewNegotiationTransactionDatabaseDao,
                    errorManager,
                    pluginVersionReference
                );
                customerBrokerNewPurchaseNegotiationTransaction.sendPurchaseNegotiationTranasction(customerBrokerPurchaseNegotiation);

            }
        } catch (CantNewPurchaseNegotiationTransactionException e){
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException(e.getMessage(),e, CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER NEW PURCHASE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER NEW PURCHASE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        }
    }

    @Override
    public CustomerBrokerNew getCustomerBrokerNewNegotiationTransaction(UUID transactionId) throws CantGetCustomerBrokerNewNegotiationTransactionException{

        try {

            return customerBrokerNewNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerNewNegotiationTranasction(transactionId);

        } catch (CantRegisterCustomerBrokerNewNegotiationTransactionException e){
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantGetCustomerBrokerNewNegotiationTransactionException(e.getMessage(), e, CantGetCustomerBrokerNewNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR GET CUSTOMER BROKER NEW NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantGetCustomerBrokerNewNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantCreateCustomerBrokerNewSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR GET CUSTOMER BROKER NEW NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        }

    }

    @Override
    public List<CustomerBrokerNew> getAllCustomerBrokerNewNegotiationTransaction() throws CantGetListCustomerBrokerNewNegotiationTransactionException{

        try{

            return customerBrokerNewNegotiationTransactionDatabaseDao.getAllRegisterCustomerBrokerNewNegotiationTranasction();

        } catch (CantRegisterCustomerBrokerNewNegotiationTransactionException e){
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantGetListCustomerBrokerNewNegotiationTransactionException(e.getMessage(), e, CantGetListCustomerBrokerNewNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR GET LIST CUSTOMER BROKER NEW NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantGetListCustomerBrokerNewNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantGetListCustomerBrokerNewNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR GET LIST CUSTOMER BROKER NEW NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        }
    }
    /*END IMPLEMENTATION CustomerBrokerNewManager*/

}
