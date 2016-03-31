package com.bitdubai.fermat_dap_api.layer.dap_module.asset_factory.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.State;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantCreateAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantCreateEmptyAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantDeleteAsserFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantPublishAssetFactoy;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantSaveAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import com.bitdubai.fermat_dap_api.layer.dap_module.asset_factory.AssetFactorySettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;

import java.util.List;

/**
 * Created by franklin on 11/09/15.
 */
public interface AssetFactoryModuleManager extends ModuleManager<AssetFactorySettings, ActiveActorIdentityInformation> {
    //Implementa solo los metodos que utiliza la sup app
    IdentityAssetIssuer getLoggedIdentityAssetIssuer();

    /**
     * This method save object AssetFactory in database
     */
    void saveAssetFactory(AssetFactory assetFactory) throws CantSaveAssetFactoryException, CantCreateFileException, CantPersistFileException;

    /**
     * This method remove object AssetFactory in database
     */
    void removeAssetFactory(String publicKey) throws CantDeleteAsserFactoryException;

    /**
     * TThis method publishes the asset digital object with the number and amount of Asset, start the transaction
     */
    void publishAsset(AssetFactory assetFactory) throws CantSaveAssetFactoryException;

    /**
     * This method create an empty object AssetFactory
     */
    AssetFactory newAssetFactoryEmpty() throws CantCreateEmptyAssetFactoryException, CantCreateAssetFactoryException;

    /**
     * This method returns the information stored about the Asset Factory
     */
    AssetFactory getAssetFactoryByPublicKey(String assetPublicKey) throws CantGetAssetFactoryException, CantCreateFileException;

    /**
     * This method returns the information stored about the all Asset Factory by issuerIdentityKey.
     */
    List<AssetFactory> getAssetFactoryByIssuer(String issuerIdentityPublicKey) throws CantGetAssetFactoryException, CantCreateFileException;

    /**
     * This method returns the information stored about the all Asset Factory by state
     */
    List<AssetFactory> getAssetFactoryByState(State state) throws CantGetAssetFactoryException, CantCreateFileException;

    /**
     * This method returns the information stored about the all Asset Factory
     */
    List<AssetFactory> getAssetFactoryAll() throws CantGetAssetFactoryException, CantCreateFileException;

    /**
     * This method returns the resource of an asset factory object
     */
    PluginBinaryFile getAssetFactoryResource(Resource resource) throws FileNotFoundException, CantCreateFileException;

    /**
     * TThis method list all wallet installed in device, start the transaction
     */
    List<com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet> getInstallWallets() throws CantListWalletsException;

    /**
     * TThis method verified all value in asset factory not null, start the transaction
     */
    boolean isReadyToPublish(String assetPublicKey) throws CantPublishAssetFactoy;

    long getBitcoinWalletBalance(String walletPublicKey) throws CantLoadWalletException, CantCalculateBalanceException;

    void changeNetworkType(BlockchainNetworkType networkType);

    BlockchainNetworkType getSelectedNetwork();
}
