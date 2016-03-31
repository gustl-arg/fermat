package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/01/16.
 */
public class PurchaseNegotiationOnlineMock implements CustomerBrokerPurchaseNegotiation {
    @Override
    public String getCustomerPublicKey() {
        return "CustomerPublicKey";
    }

    @Override
    public String getBrokerPublicKey() {
        return "BrokerPublicKey";
    }

    @Override
    public UUID getNegotiationId() {
        return UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
    }

    @Override
    public Long getStartDate() {
        return Long.valueOf(1248);
    }

    @Override
    public Long getLastNegotiationUpdateDate() {
        return Long.valueOf(1248);
    }

    @Override
    public void setLastNegotiationUpdateDate(Long lastNegotiationUpdateDate) {
        //Not implemented in this mock
    }

    @Override
    public Long getNegotiationExpirationDate() {
        return Long.valueOf(2048);
    }

    @Override
    public NegotiationStatus getStatus() {
        return NegotiationStatus.CLOSED;
    }

    @Override
    public Boolean getNearExpirationDatetime() {
        return Boolean.FALSE;
    }

    @Override
    public Collection<Clause> getClauses() throws CantGetListClauseException {
        Collection<Clause> clauses = new ArrayList<>();
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_CURRENCY_QUANTITY,
                "1961"));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_CURRENCY,
                FiatCurrency.US_DOLLAR.getCode()));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_DATE_TIME_TO_DELIVER,
                "1000"));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.CUSTOMER_CURRENCY_QUANTITY,
                "2000"));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.CUSTOMER_CURRENCY,
                CryptoCurrency.BITCOIN.getCode()));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.CUSTOMER_DATE_TIME_TO_DELIVER,
                "100"));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.CUSTOMER_PAYMENT_METHOD,
                ContractClauseType.CRYPTO_TRANSFER.getCode()));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_CRYPTO_ADDRESS,
                "mxJJSdXdKQLS4NeX6Y8tXFFoNASQnBShtv"));
        clauses.add(new ClauseMock(UUID.randomUUID(),
                ClauseType.BROKER_PAYMENT_METHOD,
                ContractClauseType.BANK_TRANSFER.getCode()));
        return clauses;
    }

    @Override
    public void setCancelReason(String cancelReason) {
        //Not implemented in this mock
    }

    @Override
    public String getCancelReason() {
        return null;
    }

    @Override
    public void setMemo(String memo) {
        //Not implemented in this mock
    }

    @Override
    public String getMemo() {
        return "Mock memo";
    }
}

