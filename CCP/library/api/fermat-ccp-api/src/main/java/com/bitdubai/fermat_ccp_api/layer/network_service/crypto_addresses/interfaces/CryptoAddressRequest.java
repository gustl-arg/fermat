package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.CryptoAddressDealers;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.ProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.RequestAction;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.RequestType;

import java.util.UUID;

/**
 * The interface <code>CryptoAddressRequest</code>
 * provides al the methods to get the information of a pending address exchange request.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/2015.
 */
public interface CryptoAddressRequest {

    UUID getRequestId();

    String getWalletPublicKey();

    Actors getIdentityTypeRequesting();

    Actors getIdentityTypeResponding();

    String getIdentityPublicKeyRequesting();

    String getIdentityPublicKeyResponding();

    CryptoCurrency getCryptoCurrency();

    CryptoAddress getCryptoAddress();

    RequestAction getAction();

    RequestType getRequestType();

    CryptoAddressDealers getCryptoAddressDealer();

    BlockchainNetworkType getBlockchainNetworkType();

    ProtocolState getState();

    int getSentNumber();

    long getSentDate();

    String getMessageType();

    boolean isReadMark();

}
