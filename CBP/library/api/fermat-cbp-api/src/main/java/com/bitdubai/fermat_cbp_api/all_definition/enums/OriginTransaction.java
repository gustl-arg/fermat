package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by franklin on 27/11/15.
 */
public enum OriginTransaction implements FermatEnum {
    STOCK_INITIAL("SINITIAL"),
    RESTOCK_AUTOMATIC("RAUTOMATIC"),
    RESTOCK("RESTOCK"),
    DESTOCK("DESTOCK"),
    PURCHASE("PURC"),
    SALE("SALE");

    OriginTransaction(String code) {
        this.code = code;
    }

    private String code;

    @Override
    public String getCode() {
        return this.code;
    }

    public static OriginTransaction getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "SINITIAL":    return OriginTransaction.STOCK_INITIAL;
            case "RAUTOMATIC":    return OriginTransaction.RESTOCK_AUTOMATIC;
            case "RESTOCK":    return OriginTransaction.RESTOCK;
            case "DESTOCK":    return OriginTransaction.DESTOCK;
            case "SALE":    return OriginTransaction.SALE;
            case "PURC":    return OriginTransaction.PURCHASE;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ContactState enum");
        }
    }
}
