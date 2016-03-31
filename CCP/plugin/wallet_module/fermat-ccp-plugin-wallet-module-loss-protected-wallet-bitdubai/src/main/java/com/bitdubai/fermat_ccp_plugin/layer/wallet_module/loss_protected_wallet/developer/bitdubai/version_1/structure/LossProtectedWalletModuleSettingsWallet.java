package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.loss_protected_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletModuleSettings;

/**
 * Created Natalia Cortez on 07/03/2016.
 */
public class LossProtectedWalletModuleSettingsWallet implements FermatSettings, LossProtectedWalletModuleSettings {

    private ActiveActorIdentityInformation activeActorIdentityInformation;
    private boolean isVisiblePresentationHelp;
    private boolean isVisibleContactsHelp;

    public LossProtectedWalletModuleSettingsWallet() {
        activeActorIdentityInformation = null;
        isVisibleContactsHelp = true;
        isVisiblePresentationHelp = true;
    }

    public void setActiveActorIdentityInformation(ActiveActorIdentityInformation activeActorIdentityInformation) {
        this.activeActorIdentityInformation = activeActorIdentityInformation;
    }

    public void setIsVisiblePresentationHelp(boolean isVisiblePresentationHelp) {
        this.isVisiblePresentationHelp = isVisiblePresentationHelp;
    }

    public void setIsVisibleContactsHelp(boolean isVisibleContactsHelp) {
        this.isVisibleContactsHelp = isVisibleContactsHelp;
    }

    public ActiveActorIdentityInformation getActiveActorIdentityInformation() {
        return activeActorIdentityInformation;
    }

    public boolean isVisiblePresentationHelp() {
        return isVisiblePresentationHelp;
    }

    public boolean isVisibleContactsHelp() {
        return isVisibleContactsHelp;
    }

    @Override
    public void setIsPresentationHelpEnabled(boolean b) {
        this.isVisiblePresentationHelp = b;
    }
}
