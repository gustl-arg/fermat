package com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.structure.functional;

<<<<<<< HEAD
=======
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_dap_api.layer.all_definition.DAPConstants;
>>>>>>> 589579dd634da6d0edd4e49f3e34d40384772f86
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransactionRecord;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.structure.database.AssetUserWalletDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by franklin on 08/10/15.
 */
public class AssetUserWalletBalanceImpl implements com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletBalance {
<<<<<<< HEAD
    private final AssetUserWalletDao assetUserWalletDao;
=======

    private final AssetUserWalletDao assetUserWalletDao;
    private Broadcaster broadcaster;
>>>>>>> 589579dd634da6d0edd4e49f3e34d40384772f86

    /**
     * Constructor.
     */
<<<<<<< HEAD
    public AssetUserWalletBalanceImpl(AssetUserWalletDao assetUserWalletDao) {
        this.assetUserWalletDao = assetUserWalletDao;
=======
    public AssetUserWalletBalanceImpl(AssetUserWalletDao assetUserWalletDao, Broadcaster broadcaster) {
        this.assetUserWalletDao = assetUserWalletDao;
        this.broadcaster = broadcaster;
>>>>>>> 589579dd634da6d0edd4e49f3e34d40384772f86
    }

    @Override
    public long getBalance() throws CantCalculateBalanceException {
        return assetUserWalletDao.getAvailableBalance();
    }

    @Override
    public List<AssetUserWalletList> getAssetUserWalletBalances() throws CantCalculateBalanceException {
        return assetUserWalletDao.getBalanceByAssets();
    }

    @Override
    public Map<ActorAssetIssuer, AssetUserWalletList> getWalletBalanceByIssuer() throws CantCalculateBalanceException {
        List<AssetUserWalletList> balances = getAssetUserWalletBalances();
        HashMap<ActorAssetIssuer, AssetUserWalletList> toReturn = new HashMap<>();
        for (AssetUserWalletList balance : balances) {
            toReturn.put(assetUserWalletDao.getActorByAsset(balance.getDigitalAsset()), balance);
        }
        return toReturn;
    }

    @Override
    public void debit(AssetUserWalletTransactionRecord assetUserWalletTransactionRecord, BalanceType balanceType) throws CantRegisterDebitException {
        assetUserWalletDao.addDebit(assetUserWalletTransactionRecord, balanceType);
<<<<<<< HEAD
=======

        broadcaster.publish(BroadcasterType.UPDATE_VIEW, DAPConstants.DAP_UPDATE_VIEW_ANDROID);
        broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, WalletsPublicKeys.DAP_USER_WALLET.getCode(), "ASSET-USER-DEBIT_" + "Name: " + assetUserWalletTransactionRecord.getDigitalAsset().getName() + " - Balance: " + balanceType.getCode());
>>>>>>> 589579dd634da6d0edd4e49f3e34d40384772f86
    }

    @Override
    public void credit(AssetUserWalletTransactionRecord assetUserWalletTransactionRecord, BalanceType balanceType) throws CantRegisterCreditException {
        assetUserWalletDao.addCredit(assetUserWalletTransactionRecord, balanceType);
<<<<<<< HEAD
=======

        broadcaster.publish(BroadcasterType.UPDATE_VIEW, DAPConstants.DAP_UPDATE_VIEW_ANDROID);
        broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, WalletsPublicKeys.DAP_USER_WALLET.getCode(), "ASSET-USER-CREDIT_" + "Name: " + assetUserWalletTransactionRecord.getDigitalAsset().getName() + " - Balance: " + balanceType.getCode());
>>>>>>> 589579dd634da6d0edd4e49f3e34d40384772f86
    }
}
