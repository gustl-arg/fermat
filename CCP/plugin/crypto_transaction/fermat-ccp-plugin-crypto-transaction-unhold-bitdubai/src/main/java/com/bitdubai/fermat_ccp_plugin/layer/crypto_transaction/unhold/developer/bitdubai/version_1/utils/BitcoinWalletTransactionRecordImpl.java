package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransactionRecord;

import java.util.UUID;

/**
 * Created by franklin on 16/02/16.
 */
public class BitcoinWalletTransactionRecordImpl implements BitcoinWalletTransactionRecord {
    private final UUID          transactionId     ;
    private final UUID          requestId     ;
    private final String        actorFromPublicKey;
    private final String        actorToPublicKey  ;
    private final Actors        actorFromType     ;
    private final Actors        actorToType       ;
    private final String        transactionHash   ;
    private final CryptoAddress addressFrom       ;
    private final CryptoAddress addressTo         ;
    private final long          amount            ;
    private final long          timestamp         ;
    private final String        memo              ;
    private final BlockchainNetworkType blockchainNetworkType;

    public BitcoinWalletTransactionRecordImpl(final UUID transactionId,
                                              final UUID requestId,
                                              final String actorFromPublicKey,
                                              final String actorToPublicKey,
                                              final Actors actorFromType,
                                              final Actors actorToType,
                                              final String transactionHash,
                                              final CryptoAddress addressFrom,
                                              final CryptoAddress addressTo,
                                              final long amount,
                                              final long timestamp,
                                              final String memo,
                                              final BlockchainNetworkType blockchainNetworkType) {

        this.transactionId      = transactionId     ;
        this.requestId          = requestId         ;
        this.actorFromPublicKey = actorFromPublicKey;
        this.actorToPublicKey   = actorToPublicKey  ;
        this.actorFromType      = actorFromType     ;
        this.actorToType        = actorToType       ;
        this.transactionHash    = transactionHash   ;
        this.addressFrom        = addressFrom       ;
        this.addressTo          = addressTo         ;
        this.amount             = amount            ;
        this.timestamp          = timestamp         ;
        this.memo               = memo              ;
        this.blockchainNetworkType = blockchainNetworkType;
    }

    @Override
    public UUID getTransactionId() {
        return transactionId;
    }

    @Override
    public UUID getRequestId() {
        return requestId;
    }

    @Override
    public String getActorFromPublicKey() {
        return actorFromPublicKey;
    }

    @Override
    public String getActorToPublicKey() {
        return actorToPublicKey;
    }

    @Override
    public Actors getActorFromType() {
        return actorFromType;
    }

    @Override
    public Actors getActorToType() {
        return actorToType;
    }

    @Override
    public String getTransactionHash() {
        return transactionHash;
    }

    @Override
    public CryptoAddress getAddressFrom() {
        return addressFrom;
    }

    @Override
    public CryptoAddress getAddressTo() {
        return addressTo;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String getMemo() {
        return memo;
    }

    @Override
    public BlockchainNetworkType getBlockchainNetworkType() {return blockchainNetworkType;}

}
