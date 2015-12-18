package com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin on 08/09/15.
 */
public class CantRegisterDebitException extends FermatException{
    public static final String DEFAULT_MESSAGE = "Failed To Register Debit in Cash Money Wallet.";
    public CantRegisterDebitException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
