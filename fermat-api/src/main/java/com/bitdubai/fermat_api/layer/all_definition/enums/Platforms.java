package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum class <code>com.bitdubai.fermat_api.layer.all_definition.enums.Platforms</code>
 * Enums all the platforms to be found on Fermat.
 * Created by lnacosta (laion.cj91@gmail.com) on 02/09/2015.
 * Modified by PatricioGesualdi (pmgesualdi@hotmail.com) on 10/11/2015.
 * Modified by Alejandro Bicelis  on 12/27/2015.
 */
public enum Platforms implements FermatEnum {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    ART_PLATFORM                        ("ART"),
    BLOCKCHAINS                         ("BCH"),
    BANKING_PLATFORM                    ("BNK"),
    COMMUNICATION_PLATFORM              ("CP"),
    CRYPTO_BROKER_PLATFORM              ("CBP"),
    CURRENCY_EXCHANGE_RATE_PLATFORM     ("CER"),
    CASH_PLATFORM                       ("CSH"),
    CHAT_PLATFORM                       ("CHT"),
    CRYPTO_COMMODITY_MONEY              ("CCM"),
    CRYPTO_CURRENCY_PLATFORM            ("CCP"),
    DIGITAL_ASSET_PLATFORM              ("DAP"),
    OPERATIVE_SYSTEM_API                ("OSA"),
    PLUG_INS_PLATFORM                   ("PIP"),
    WALLET_PRODUCTION_AND_DISTRIBUTION  ("WPD"),
    TOKENLY                             ("TKY"),
    ;

    private final String code;

    Platforms(final String code) {
        this.code = code;
    }

    public static Platforms getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "BCH":  return BLOCKCHAINS;
            case "BNK":  return BANKING_PLATFORM;
            case "CP":   return COMMUNICATION_PLATFORM;
            case "CBP":  return CRYPTO_BROKER_PLATFORM;
            case "CER":  return CURRENCY_EXCHANGE_RATE_PLATFORM;
            case "CSH":  return CASH_PLATFORM;
            case "CHT":  return CHAT_PLATFORM;
            case "CCM":  return CRYPTO_COMMODITY_MONEY;
            case "CCP":  return CRYPTO_CURRENCY_PLATFORM;
            case "DAP":  return DIGITAL_ASSET_PLATFORM;
            case "OSA":  return OPERATIVE_SYSTEM_API;
            case "PIP":  return PLUG_INS_PLATFORM;
            case "WPD":  return WALLET_PRODUCTION_AND_DISTRIBUTION;
            case "TKY":  return TOKENLY;

            default:
            throw new InvalidParameterException(
                    "Code Received: " + code,
                    "The received code is not valid for the Platforms enum"
            );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}