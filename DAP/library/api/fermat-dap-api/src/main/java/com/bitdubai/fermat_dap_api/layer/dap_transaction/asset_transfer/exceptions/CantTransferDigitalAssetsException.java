package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_transfer.exceptions;

import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 24/09/15.
 */
public class CantTransferDigitalAssetsException extends DAPException {
    static final String DEFAULT_MESSAGE = "There was an error Distributing Digital Assets.";

    public CantTransferDigitalAssetsException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantTransferDigitalAssetsException(final String message) {
        this(null, DEFAULT_MESSAGE, message);
    }

    public CantTransferDigitalAssetsException(Exception exception) {
        super(exception);
    }
}
