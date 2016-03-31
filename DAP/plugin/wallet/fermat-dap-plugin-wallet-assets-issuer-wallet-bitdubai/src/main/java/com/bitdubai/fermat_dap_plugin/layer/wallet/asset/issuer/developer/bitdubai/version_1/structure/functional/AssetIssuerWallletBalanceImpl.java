package com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1.structure.functional;

<<<<<<< HEAD
=======
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_dap_api.layer.all_definition.DAPConstants;
>>>>>>> 589579dd634da6d0edd4e49f3e34d40384772f86
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransactionRecord;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1.structure.database.AssetIssuerWalletDao;

import java.util.List;

/**
 * Created by franklin on 29/09/15.
 */
public class AssetIssuerWallletBalanceImpl implements com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletBalance {

    private final AssetIssuerWalletDao assetIssuerWalletDao;

<<<<<<< HEAD
    public AssetIssuerWallletBalanceImpl(AssetIssuerWalletDao assetIssuerWalletDao) {
        this.assetIssuerWalletDao = assetIssuerWalletDao;

=======
    private Broadcaster broadcaster;

    public AssetIssuerWallletBalanceImpl(AssetIssuerWalletDao assetIssuerWalletDao, Broadcaster broadcaster) {
        this.assetIssuerWalletDao = assetIssuerWalletDao;
        this.broadcaster = broadcaster;
>>>>>>> 589579dd634da6d0edd4e49f3e34d40384772f86
    }

    @Override
    public long getBalance() throws CantCalculateBalanceException {
        return assetIssuerWalletDao.getAvailableBalance();
    }

    @Override
    public List<AssetIssuerWalletList> getAssetIssuerWalletBalances() throws CantCalculateBalanceException {
        return assetIssuerWalletDao.getBalanceByAssets();
    }

    @Override
    public void debit(AssetIssuerWalletTransactionRecord assetIssuerWalletTransactionRecord, BalanceType balanceType) throws CantRegisterDebitException {
        assetIssuerWalletDao.addDebit(assetIssuerWalletTransactionRecord, balanceType);
<<<<<<< HEAD
=======

        broadcaster.publish(BroadcasterType.UPDATE_VIEW, DAPConstants.DAP_UPDATE_VIEW_ANDROID);
        broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, WalletsPublicKeys.DAP_ISSUER_WALLET.getCode(), "ASSET-ISSUER-DEBIT_" + "Name: " + assetIssuerWalletTransactionRecord.getDigitalAsset().getName() + " - Balance: " + balanceType.getCode());
>>>>>>> 589579dd634da6d0edd4e49f3e34d40384772f86
    }

    @Override
    public void credit(AssetIssuerWalletTransactionRecord assetIssuerWalletTransactionRecord, BalanceType balanceType) throws CantRegisterCreditException {
        assetIssuerWalletDao.addCredit(assetIssuerWalletTransactionRecord, balanceType);
<<<<<<< HEAD
=======

        broadcaster.publish(BroadcasterType.UPDATE_VIEW, DAPConstants.DAP_UPDATE_VIEW_ANDROID);
        broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, WalletsPublicKeys.DAP_ISSUER_WALLET.getCode(), "ASSET-ISSUER-CREDIT_" + "Name: " + assetIssuerWalletTransactionRecord.getDigitalAsset().getName() + " - Balance: " + balanceType.getCode());
>>>>>>> 589579dd634da6d0edd4e49f3e34d40384772f86
    }
}
