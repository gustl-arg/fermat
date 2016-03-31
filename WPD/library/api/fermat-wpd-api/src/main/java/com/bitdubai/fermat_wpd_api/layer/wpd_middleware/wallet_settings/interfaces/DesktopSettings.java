package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces;

import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantGetDefaultLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantGetDefaultSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantLoadWalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSaveWalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSetDefaultLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSetDefaultSkinException;

import java.util.UUID;

/**
 * Created by mati on 2015.11.21..
 */
public interface DesktopSettings extends FermatSettings {

    /**
     * This method let us know the default language of a wallet
     *
     * @return the identifier of the default language of the wallet
     * @throws CantGetDefaultLanguageException
     */
    UUID getDefaultLanguage() throws CantGetDefaultLanguageException;

    /**
     * This method let us know the default skin of a wallet
     *
     * @return the identifier of the default skin of the wallet
     * @throws CantGetDefaultSkinException
     */
    UUID getDefaultSkin() throws CantGetDefaultSkinException;

    /**
     * This method let us set the default language for a wallet
     *
     * @param languageId the identifier of the language to set as default
     * @throws CantSetDefaultLanguageException
     */
    void setDefaultLanguage(UUID languageId) throws CantSetDefaultLanguageException;

    /**
     * This method let us set the default skin for a wallet
     *
     * @param skinId the identifier of the skin to set as default
     * @throws CantSetDefaultSkinException
     */
    void setDefaultSkin(UUID skinId) throws CantSetDefaultSkinException;

    /**
     * This method let us set the preference settings for a wallet
     *
     * @param walletPreferenceSettings
     * @throws CantSetDefaultSkinException
     */
    void setPreferenceSettings(String walletPreferenceSettings, String walletPublicKey) throws CantSaveWalletSettings;

    /**
     * This method let us get the preference settings for a wallet
     *
     * @return preference settings of a wallet
     * @throws CantGetDefaultSkinException
     */
    String getPreferenceSettings(String walletPublicKey) throws CantLoadWalletSettings;
}
