package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatPluginsEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.all_definition.enums.Plugins</code>
 * Contains all the type of Plugins in Fermat.
 * Created by ciencias on 2/13/15.
 * Modified by Manuel Perez on 03/08/2015
 * Updated by lnacosta (laion.cj91@gmail.com) on 18/11/2015.
 * Modified by pmgesualdi - (pmgesualdi@hotmail.com) on 01/12/2015. *
 */
public enum Plugins implements FermatPluginsEnum {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    BITDUBAI_BITCOIN_CRYPTO_NETWORK             ("BBTCCNET"     ),
    BITDUBAI_BITCOIN_CRYPTO_NETWORK2            ("BBTCCNET2"    ),
    BITDUBAI_BLOCKCHAIN_INFO_WORLD              ("BBLOCKIW"     ),
    BITDUBAI_CLOUD_CHANNEL                      ("BCLOUDC"      ),
    BITDUBAI_COINAPULT_WORLD                    ("BCOINAW"      ),
    BITDUBAI_CRYPTO_INDEX                       ("BCRYPTOINW"   ),
    BITDUBAI_LICENSE_MANAGER                    ("BLICM"        ),
    BITDUBAI_SHAPE_SHIFT_WORLD                  ("BSHAPESW"     ),

    BITDUBAI_WS_COMMUNICATION_CLIENT_CHANNEL    ("BWSCCLIENTCH" ),
    BITDUBAI_WS_COMMUNICATION_CLOUD_SERVER      ("BWSCCLSERVER" ),

    BITDUBAI_COMMUNICATIONS_NETWORK_NODE        ("BCNNODE"),
    BITDUBAI_COMMUNICATIONS_NETWORK_CLIENT      ("BCNCLIENT"),

    BITDUBAI_CLOUD_SERVER_COMMUNICATION                             ("BCLOUSC"      ),
    BITDUBAI_USER_NETWORK_SERVICE                                   ("BUSERNETS"    ),
    BITDUBAI_TEMPLATE_NETWORK_SERVICE                               ("BTEMNETS"     ),
    BITDUBAI_INTRAUSER_NETWORK_SERVICE                              ("BINUSERNS"    ),
    BITDUBAI_APP_RUNTIME_MIDDLEWARE                                 ("BAPPRUNM"     ),
    BITDUBAI_DISCOUNT_WALLET_BASIC_WALLET                           ("BDWALLBW"     ),
    BITDUBAI_WALLET_RUNTIME_MODULE                                  ("BWALLRUNM"    ),
    BITDUBAI_BITCOIN_CRYPTO_VAULT                                   ("BBTCCRYV"     ),
    BITDUBAI_ASSETS_CRYPTO_VAULT                                    ("BASSTCRYV"    ),
    BITDUBAI_INTRA_USER_FACTORY_MODULE                              ("BINUSFACM"    ),
    BITDUBAI_BANK_NOTES_WALLET_WALLET_MODULE                        ("BBNWWM"       ),
    BITDUBAI_CRYPTO_LOSS_PROTECTED_WALLET_WALLET_MODULE             ("BCLPWWM"      ),
    BITDUBAI_CRYPTO_WALLET_WALLET_MODULE                            ("BCWWM"        ),
    BITDUBAI_DISCOUNT_WALLET_WALLET_MODULE                          ("BDWWM"        ),
    BITDUBAI_FIAT_OVER_CRYPTO_LOSS_PROTECTED_WALLET_WALLET_MODULE   ("BFOCLPWWM"    ),
    BITDUBAI_FIAT_OVER_CRYPTO_WALLET_WALLET_MODULE                  ("BFOCWWM"      ),
    BITDUBAI_MULTI_ACCOUNT_WALLET_WALLET_MODULE                     ("BMAWWM"       ),
    BITDUBAI_INCOMING_INTRA_USER_TRANSACTION                        ("BININUST"     ),
    BITDUBAI_INCOMING_DEVICE_USER_TRANSACTION                       ("BINDEVUT"     ),
    BITDUBAI_OUTGOING_DEVICE_USER_TRANSACTION                       ("BODEVUST"     ),
    BITDUBAI_INTER_WALLET_TRANSACTION                               ("BINWALLT"     ),
    BITDUBAI_BANK_NOTES_MIDDLEWARE                                  ("BBNMIDD"      ),
    BITDUBAI_BANK_NOTES_NETWORK_SERVICE                             ("BBNNETSER"    ),
    BITDUBAI_WALLET_RESOURCES_NETWORK_SERVICE                       ("BWRNETSER"    ),
    BITDUBAI_WALLET_STORE_NETWORK_SERVICE                           ("BWSTONETSER"  ),
    BITDUBAI_WALLET_COMMUNITY_NETWORK_SERVICE                       ("BWCNETSER"    ),
    BITDUBAI_CRYPTO_ADDRESS_BOOK                                    ("BCADDB"       ),
    BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION                        ("BOUEXUT"      ),
    BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION                        ("BINEXUT"      ),
    BITDUBAI_INCOMING_CRYPTO_TRANSACTION                            ("BINCRYT"      ),
    BITDUBAI_USER_DEVICE_USER                                       ("BUDEVU"       ),
    BITDUBAI_ACTOR_EXTRA_USER                                       ("BAEXU"        ),
    BITDUBAI_USER_INTRA_USER                                        ("BUINU"        ),
    BITDUBAI_COINBASE_WORLD                                         ("BCOINW"       ),
    BITDUBAI_BITCOIN_WALLET_BASIC_WALLET                            ("BBTCWBW"      ),
    BITDUBAI_DEVICE_CONNECTIVITY                                    ("BBTCDEVC"     ),
    BITDUBAI_LOCATION_WORLD                                         ("BLOCW"        ),
    BITDUBAI_ACTOR_DEVELOPER                                        ("BACTORD"      ),
    BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE                             ("BWLMIDD"      ),
    BITDUBAI_WALLET_SKIN_MIDDLEWARE                                 ("BWSMIDD"      ),
    BITDUBAI_WALLET_NAVIGATION_STRUCTURE_MIDDLEWARE                 ("BWNSMIDD"     ),
    BITDUBAI_SUB_APP_SETTINGS_MIDDLEWARE                            ("BSASEMIDD"    ),
    BITDUBAI_WALLET_STATISTICS_NETWORK_SERVICE                      ("BWSTANETSER"  ),
    BITDUBAI_SUBAPP_RESOURCES_NETWORK_SERVICE                       ("BSRNETSER"    ),
    BITDUBAI_CCP_CRYPTO_TRANSMISSION_NETWORK_SERVICE                ("BCTNSER"      ),
    BITDUBAI_REQUEST_MONEY_REQUEST                                  ("BRMR"         ),
    BITDUBAI_DEVELOPER_IDENTITY                                     ("BDEVID"       ),
    BITDUBAI_TRANSLATOR_IDENTITY                                    ("BDTRAID"      ),
    BITDUBAI_IDENTITY_MANAGER                                       ("BIDMAN"       ),
    BITDUBAI_DESIGNER_IDENTITY                                      ("BDDESID"      ),
    BITDUBAI_DEVELOPER_MODULE                                       ("BDEVMOD"      ),
    BITDUBAI_MIDDLEWARE_NOTIFICATION                                ("BDNOTMID"     ),
    BITDUBAI_DESKTOP_RUNTIME                                        ("BDR"          ),

    // Init CCP Plugins
    BITDUBAI_CCP_INTRA_USER_ACTOR                           ("BCCPIUA"  ),
    BITDUBAI_CCP_CRYPTO_ADDRESSES_NETWORK_SERVICE           ("BCCPCANS" ),
    BITDUBAI_CCP_CRYPTO_CRYPTO_TRANSMISSION_NETWORK_SERVICE ("BCCPCTNS" ),
    BITDUBAI_CCP_CRYPTO_PAYMENT_REQUEST_NETWORK_SERVICE     ("BCCPCPRNS"),
    BITDUBAI_CCP_INTRA_USER_IDENTITY                        ("BCCPIUI"  ),
    BITDUBAI_CCP_WALLET_CONTACTS_MIDDLEWARE                 ("BCCPWCM"  ),
    BITDUBAI_CCP_CRYPTO_PAYMENT_REQUEST                     ("BCCPCPR"  ),
    BITDUBAI_CCP_OUTGOING_INTRA_ACTOR_TRANSACTION           ("BCCPOIAT" ),


    BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE    ("BCTNS"),


    // End  CCP Plugins

    //CCM Plugins

    BITDUBAI_CCM_INTRA_WALLET_USER_ACTOR            ("BCCMIWUA"),

    // Init DAP Plugins
    BITDUBAI_DAP_ASSET_ISSUER_ACTOR                       ("BDAPAIA"   ),
    BITDUBAI_DAP_ASSET_USER_ACTOR                         ("BDAPAUA"   ),
    BITDUBAI_DAP_REDEEM_POINT_ACTOR                       ("BDAPRPA"   ),
    BITDUBAI_DAP_ASSET_ISSUER_IDENTITY                    ("BDAPAII"   ),
    BITDUBAI_DAP_ASSET_USER_IDENTITY                      ("BDAPAUI"   ),
    BITDUBAI_DAP_REDEEM_POINT_IDENTITY                    ("BDAPRPI"   ),
    BITDUBAI_ASSET_ISSUING_TRANSACTION                    ("BAIT"      ),
    BITDUBAI_ASSET_DISTRIBUTION_TRANSACTION               ("BADT"      ),
    BITDUBAI_ASSET_RECEPTION_TRANSACTION                  ("BADR"      ),
    BITDUBAI_ISSUER_REDEMPTION_TRANSACTION                ("BIRT"      ),
    BITDUBAI_REDEEM_POINT_REDEMPTION_TRANSACTION          ("BRPRT"     ),
    BITDUBAI_USER_REDEMPTION_TRANSACTION                  ("BURT"      ),
    BITDUBAI_ASSET_APPROPRIATION_TRANSACTION              ("BAAT"      ),
    BITDUBAI_ASSET_WALLET_ISSUER                          ("BASWI"     ),
    BITDUBAI_ASSET_FACTORY                                ("BASF"      ),
    BITDUBAI_ASSET_FACTORY_MODULE                         ("BASFM"     ),
    BITDUBAI_DAP_ASSET_USER_WALLET_MODULE                 ("BDAUWMO"   ),
    BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE               ("BDAIWMO"   ),
    BITDUBAI_DAP_ASSET_REDEEM_POINT_WALLET_MODULE         ("BDARWMO"   ),
    BITDUBAI_DAP_ASSET_ISSUER_COMMUNITY_SUB_APP_MODULE    ("BDAPAICSAM"),
    BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE      ("BDAPAUCSAM"),
    BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE    ("BDAPRPCSAM"),
    BITDUBAI_DAP_ASSET_REDEEM_POINT_WALLET                ("BDAPAWRD"  ),
    BITDUBAI_DAP_ASSET_USER_WALLET                        ("BDAPAWU"   ),
    BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE         ("BDAPAUANS" ),
    BITDUBAI_DAP_ASSET_ISSUER_ACTOR_NETWORK_SERVICE       ("BDAPAIANS" ),
    BITDUBAI_DAP_ASSET_REDEEM_POINT_ACTOR_NETWORK_SERVICE ("BDAPARANS" ),
    BITDUBAI_DAP_ASSET_TRANSMISSION_NETWORK_SERVICE       ("BDAPATNS"  ),

    // End  DAP Plugins

    // Init WPD Plugins
    BITDUBAI_WPD_WALLET_MANAGER_DESKTOP_MODULE              ("BWPDWMDM" ),
    BITDUBAI_WPD_WALLET_FACTORY_MIDDLEWARE                  ("BWPDWFM"  ),
    BITDUBAI_WPD_WALLET_MANAGER_MIDDLEWARE                  ("BWPDWMM"  ),
    BITDUBAI_WPD_WALLET_PUBLISHER_MIDDLEWARE                ("BWPDWPM"  ),
    BITDUBAI_WPD_WALLET_SETTINGS_MIDDLEWARE                 ("BWPDWSEM" ),
    BITDUBAI_WPD_WALLET_STORE_MIDDLEWARE                    ("BWPDWSTM" ),
    BITDUBAI_WPD_PUBLISHER_IDENTITY                         ("BWPDPI"   ),
    BITDUBAI_WPD_WALLET_COMMUNITY_NETWORK_SERVICE           ("BWPDWCNS" ),
    BITDUBAI_WPD_WALLET_RESOURCES_NETWORK_SERVICE           ("BWPDWRNS" ),
    BITDUBAI_WPD_WALLET_STATISTICS_NETWORK_SERVICE          ("BWPDWSNS" ),
    BITDUBAI_WPD_WALLET_STORE_NETWORK_SERVICE               ("BWPDSNS"  ),
    BITDUBAI_WPD_WALLET_FACTORY_SUB_APP_MODULE              ("BWPDWFSAM"),
    BITDUBAI_WPD_WALLET_PUBLISHER_SUB_APP_MODULE            ("BWPDWPSAM"),
    BITDUBAI_WPD_WALLET_STORE_SUB_APP_MODULE                ("BWPDWSSAM"),
    // End  WPD Plugins

    //Init BNK Plugins
    BITDUBAI_BNK_HOLD_MONEY_TRANSACTION         ("BBNKHMT"  ),
    BITDUBAI_BNK_UNHOLD_MONEY_TRANSACTION       ("BBNKUMT"  ),
    BITDUBAI_BNK_DEPOSIT_MONEY_TRANSACTION      ("BBNKDMT"  ),
    BITDUBAI_BNK_WITHDRAW_MONEY_TRANSACTION     ("BBNKWMT"  ),
    BITDUBAI_BNK_BANK_MONEY_WALLET_MODULE       ("BBNKBMWM" ),
    BITDUBAI_BNK_BANK_MONEY_WALLET              ("BBNKBMW"  ),

    // End BNK Plugins

    //Init CSH Plugins
    BITDUBAI_CSH_MONEY_TRANSACTION_HOLD         ("BCSHMTH"  ),
    BITDUBAI_CSH_MONEY_TRANSACTION_UNHOLD       ("BCSHMTU"  ),
    BITDUBAI_CSH_MONEY_TRANSACTION_DEPOSIT      ("BCSHMTD"  ),
    BITDUBAI_CSH_MONEY_TRANSACTION_WITHDRAWAL   ("BCSHMTW"  ),
    BITDUBAI_CSH_MONEY_WALLET_MODULE            ("BCSHMWM"),
    BITDUBAI_CSH_WALLET_CASH_MONEY              ("BCSHWCM"  ),
    // End CSH Plugins

    //Init CER Plugins
    BITDUBAI_CER_PROVIDER_FILTER                ("BCERPF"  ),
    BITDUBAI_CER_PROVIDER_BITCOINVENEZUELA      ("BCERPBV"  ),
    BITDUBAI_CER_PROVIDER_DOLARTODAY            ("BCERPDT"  ),
    BITDUBAI_CER_PROVIDER_ELCRONISTA            ("BCERPEC"  ),
    BITDUBAI_CER_PROVIDER_EUROPEAN_CENTRAL_BANK ("BCERPECB" ),
    BITDUBAI_CER_PROVIDER_LANACION              ("BCERPLN"  ),
    BITDUBAI_CER_PROVIDER_YAHOO                 ("BCERPYH"  ),

    // End CER Plugins

    // Init new Plugins

    ASSET_APPROPRIATION         ("ASAP"),
    ASSET_BUYER                 ("ASBU"),
    ASSET_DIRECT_SELL           ("ASDS"),
    ASSET_DISTRIBUTION          ("ASD"),
    ASSET_TRANSFER              ("ASTT"),
    ASSET_FACTORY               ("ASF"),
    ASSET_ISSUER                ("ASI"),
    ASSET_ISSUER_COMMUNITY      ("ASIC"),
    ASSET_ISSUING               ("ASIS"),
    ASSET_RECEPTION             ("ASR"),
<<<<<<< HEAD
    ASSET_SELLER("ASSE"),
    ASSET_BUYER("ASBU"),
=======
    ASSET_SELLER                ("ASSE"),
    ASSET_TRANSFER              ("ASTT"),
    ASSET_TRANSMISSION          ("AST"),
>>>>>>> 589579dd634da6d0edd4e49f3e34d40384772f86
    ASSET_USER                  ("ASU"),
    ASSET_USER_COMMUNITY        ("ASUC"),
    BITCOIN_ASSET_VAULT         ("BAV"),
    BITCOIN_NETWORK             ("BN"),
    BITCOIN_VAULT               ("BV"),
    BITCOIN_WALLET              ("BW"),
    LOSS_PROTECTED_WALLET       ("LPW"),
    BITCOIN_WATCH_ONLY_VAULT    ("BWOV"),
    BITCOIN_HOLD                ("BHOLD"),
    BITCOIN_UNHOLD              ("BUNHOLD"),
    CRYPTO_ADDRESSES            ("CA"),
    CRYPTO_ADDRESS_BOOK         ("CAB"),
    CRYPTO_BROKER               ("CB"),
    CRYPTO_BROKER_COMMUNITY     ("CBC"),
    CRYPTO_BROKER_IDENTITY      ("CBI"),
    CRYPTO_BROKER_PURCHASE      ("CBP"),
    CRYPTO_BROKER_SALE          ("CBS"),
    CRYPTO_CUSTOMER             ("CC"),
    CRYPTO_CUSTOMER_IDENTITY    ("CCI"),
    CRYPTO_CUSTOMER_COMMUNITY   ("CCC"),
    CRYPTO_PAYMENT_REQUEST      ("CPR"),
    CRYPTO_TRANSMISSION         ("CT"),
    CRYPTO_WALLET               ("CW"),
    CRYPTO_LOSS_PROTECTED_WALLET("CLPW"),
    DESKTOP_RUNTIME             ("DER"),
    DEVELOPER                   ("DEV"),
    DEVICE_USER                 ("DU"),
    EXTRA_WALLET_USER           ("EWU"),
    INCOMING_CRYPTO             ("IC"),
    INCOMING_EXTRA_USER         ("IEU"),
    INCOMING_INTRA_USER         ("IIU"),
    INTRA_IDENTITY_USER         ("IIA"),
    INTRA_WALLET_USER           ("IWU"),
    ISSUER_REDEMPTION           ("IR"),
    ISSUER_APPROPRIATION        ("ISAP"),
    NOTIFICATION                ("NOT"),
<<<<<<< HEAD
=======
    NEGOTIATION_DIRECT_SELL     ("NDS"),
>>>>>>> 589579dd634da6d0edd4e49f3e34d40384772f86
    ANDROID_CORE                ("AND"),
    OUTGOING_EXTRA_USER         ("OEU"),
    OUTGOING_INTRA_ACTOR        ("OIA"),
    PUBLISHER                   ("PBL"),
    REDEEM_POINT                ("RP"),
    REDEEM_POINT_COMMUNITY      ("RPC"),
    REDEEM_POINT_REDEMPTION     ("RPR"),
    SUB_APP_RESOURCES           ("SAR"),
    SUB_APP_RUNTIME             ("SPR"),
    USER_REDEMPTION             ("UR"),
    WALLET_COMMUNITY            ("WCOM"),
    WALLET_CONTACTS             ("WC"),
    WALLET_FACTORY              ("WF"),
    WALLET_MANAGER              ("WM"),
    SUB_APP_MANAGER             ("SAM"),
    WALLET_PUBLISHER            ("WPU"),
    WALLET_RESOURCES            ("WRE"),
    WALLET_RUNTIME              ("WRU"),
    WALLET_SETTINGS             ("WSE"),
    WALLET_STATISTICS           ("WSTA"),
    WALLET_STORE                ("WST"),




    WS_CLOUD_CLIENT             ("WCL"),

    //CBP
    BANK_MONEY_RESTOCK                  ("BMRE"),
    BANK_MONEY_DESTOCK                  ("BMDE"),
    CASH_MONEY_RESTOCK                  ("CMDE"),
    CASH_MONEY_DESTOCK                  ("CMRE"),
    CONTRACT_PURCHASE                   ("CONTP"),
    CONTRACT_SALE                       ("CONTS"),
    CRYPTO_BROKER_WALLET                ("CBWA"),
    CRYPTO_BROKER_ACTOR                 ("CBAC"),
    CRYPTO_CUSTOMER_ACTOR               ("CCAC"),
    CRYPTO_MONEY_DESTOCK                ("CRDE"),
    CRYPTO_MONEY_RESTOCK                ("CRRE"),
    CUSTOMER_BROKER_CLOSE               ("CBCL"),
    CUSTOMER_BROKER_NEW                 ("CBNE"),
    CUSTOMER_BROKER_UPDATE              ("CBUP"),
    FIAT_INDEX                          ("FI"),
    NEGOTIATION_PURCHASE                ("NGP"),
    NEGOTIATION_SALE                    ("NGS"),
    NEGOTIATION_TRANSMISSION            ("NGTR"),
    OPEN_CONTRACT                       ("OPC"),
    TRANSACTION_TRANSMISSION            ("TRTX"),
    MATCHING_ENGINE                     ("MAEN"),
    CLOSE_CONTRACT                      ("CLC"),
    CUSTOMER_ONLINE_PAYMENT             ("CONP"),
    CUSTOMER_OFFLINE_PAYMENT            ("COFP"),
    BROKER_ACK_OFFLINE_PAYMENT          ("BAFP"),
    BROKER_ACK_ONLINE_PAYMENT           ("BAOP"),
    CUSTOMER_ACK_OFFLINE_MERCHANDISE    ("CAFM"),
    CUSTOMER_ACK_ONLINE_MERCHANDISE     ("CAOM"),
    BROKER_SUBMIT_ONLINE_MERCHANDISE    ("BSOM"),
    BROKER_SUBMIT_OFFLINE_MERCHANDISE   ("BSFM"),

    CUSTOMER_BROKER_PURCHASE            ("CBPU"),
    CUSTOMER_BROKER_SALE                ("CBSA"),

    //CHT

    CHAT_MIDDLEWARE                     ("CHMID"),
    CHAT_NETWORK_SERVICE                ("CHTNS"),
    CHAT_SUP_APP_MODULE                 ("CHTSAM"), CCP_OUTGOING_DRAFT_TRANSACTION("CCPODT"),

    // ART

    ACTOR_NETWORK_SERVICE_ARTIST       ("ANSART"),
    ARTIST_IDENTITY                    ("ARTIDNTY"),
    //WRD
    API_TOKENLY                         ("TOKAP")

    // End  new Plugins
    ;
    private final String code;

    Plugins(final String code) {
        this.code = code;
    }

    public static Plugins getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "APR"  :   return SUB_APP_RUNTIME          ;
            case "ASAP" :   return ASSET_APPROPRIATION      ;
            case "ASD"  :   return ASSET_DISTRIBUTION       ;
<<<<<<< HEAD
=======
            case "ASDS" :   return ASSET_DIRECT_SELL        ;
>>>>>>> 589579dd634da6d0edd4e49f3e34d40384772f86
            case "ASTT"  :  return ASSET_TRANSFER           ;
            case "ASF"  :   return ASSET_FACTORY            ;
            case "ASI"  :   return ASSET_ISSUER             ;
            case "ASIC" :   return ASSET_ISSUER_COMMUNITY   ;
            case "ASIS" :   return ASSET_ISSUING            ;
            case "ASR"  :   return ASSET_RECEPTION          ;
<<<<<<< HEAD
            case "ASSE":
                return ASSET_SELLER;
            case "ASBU":
                return ASSET_BUYER;
=======
            case "ASSE":    return ASSET_SELLER;
            case "ASBU":    return ASSET_BUYER;
>>>>>>> 589579dd634da6d0edd4e49f3e34d40384772f86
            case "ASU"  :   return ASSET_USER               ;
            case "ASUC" :   return ASSET_USER_COMMUNITY     ;
            case "AST"  :   return ASSET_TRANSMISSION       ;
            case "BAV"  :   return BITCOIN_ASSET_VAULT      ;
            case "BN"   :   return BITCOIN_NETWORK          ;
            case "BV"   :   return BITCOIN_VAULT            ;
            case "BW"   :   return BITCOIN_WALLET           ;
            case "LPW"  :   return LOSS_PROTECTED_WALLET    ;
            case "BWOV" :   return BITCOIN_WATCH_ONLY_VAULT ;
            case "BHOLD":   return BITCOIN_HOLD             ;
            case "BUNHOLD": return BITCOIN_UNHOLD           ;
            case "CONTP" :   return CONTRACT_PURCHASE       ;
            case "CONTS" :   return CONTRACT_SALE           ;
            case "CA"   :   return CRYPTO_ADDRESSES         ;
            case "CAB"  :   return CRYPTO_ADDRESS_BOOK      ;
            case "CB"   :   return CRYPTO_BROKER            ;
            case "CBC"  :   return CRYPTO_BROKER_COMMUNITY  ;
            case "CBI"  :   return CRYPTO_BROKER_IDENTITY   ;
            case "CC"   :   return CRYPTO_CUSTOMER          ;
            case "CCI"  :   return CRYPTO_CUSTOMER_IDENTITY ;
            case "CCC"  :   return CRYPTO_CUSTOMER_COMMUNITY;
            case "CPR"  :   return CRYPTO_PAYMENT_REQUEST   ;
            case "CT"   :   return CRYPTO_TRANSMISSION      ;
            case "CW"   :   return CRYPTO_WALLET            ;
            case "CLPW" :   return CRYPTO_LOSS_PROTECTED_WALLET            ;
            case "DER"  :   return DESKTOP_RUNTIME          ;
            case "DEV"  :   return DEVELOPER                ;
            case "DU"   :   return DEVICE_USER              ;
            case "EWU"  :   return EXTRA_WALLET_USER        ;
            case "IC"   :   return INCOMING_CRYPTO          ;
            case "IEU"  :   return INCOMING_EXTRA_USER      ;
            case "IIU"  :   return INCOMING_INTRA_USER      ;
            case "IWU"  :   return INTRA_WALLET_USER        ;
            case "IIA"  :   return INTRA_IDENTITY_USER      ;
            case "IR"   :   return ISSUER_REDEMPTION        ;
            case "ISAP":    return ISSUER_APPROPRIATION     ;
            case "NOT"  :   return NOTIFICATION             ;
            case "NDS"  :   return NEGOTIATION_DIRECT_SELL  ;
            case "OEU"  :   return OUTGOING_EXTRA_USER      ;
            case "OIA"  :   return OUTGOING_INTRA_ACTOR     ;
            case "PBL"  :   return PUBLISHER                ;
            case "RP"   :   return REDEEM_POINT             ;
            case "RPC"  :   return REDEEM_POINT_COMMUNITY   ;
            case "RPR"  :   return REDEEM_POINT_REDEMPTION  ;
            case "SAR"  :   return SUB_APP_RESOURCES        ;
            case "SPR"  :   return SUB_APP_RUNTIME          ;
            case "UR"   :   return USER_REDEMPTION          ;
            case "WCOM" :   return WALLET_COMMUNITY         ;
            case "WC"   :   return WALLET_CONTACTS          ;
            case "WF"   :   return WALLET_FACTORY           ;
            case "WM"   :   return WALLET_MANAGER           ;
            case "SAM"  :   return SUB_APP_MANAGER          ;
            case "WPU"  :   return WALLET_PUBLISHER         ;
            case "WRE"  :   return WALLET_RESOURCES         ;
            case "WRU"  :   return WALLET_RUNTIME           ;
            case "WSE"  :   return WALLET_SETTINGS          ;
            case "WSTA" :   return WALLET_STATISTICS        ;
            case "WST"  :   return WALLET_STORE             ;
            case "WCL"  :   return WS_CLOUD_CLIENT          ;
            case ("CBAC"):  return CRYPTO_BROKER_ACTOR      ;
            case ("CCAC"):  return CRYPTO_CUSTOMER_ACTOR    ;
            case ("CBWA"):  return CRYPTO_BROKER_WALLET     ;
            case ("BMRE"):  return BANK_MONEY_RESTOCK       ;
            case ("BMDE"):  return BANK_MONEY_DESTOCK       ;
            case ("CMRE"):  return CASH_MONEY_RESTOCK       ;
            case ("CMDE"):  return CASH_MONEY_DESTOCK       ;
            case ("CRRE"):  return CRYPTO_MONEY_RESTOCK     ;
            case ("CRDE"):  return CRYPTO_MONEY_DESTOCK     ;
            case "TRTX":    return TRANSACTION_TRANSMISSION ;
            case "CBPU":    return CUSTOMER_BROKER_PURCHASE ;
            case "CBSA":    return CUSTOMER_BROKER_SALE     ;
            case ("FI"):    return FIAT_INDEX               ;
            case "BBNKHMT": return BITDUBAI_BNK_HOLD_MONEY_TRANSACTION;
            case "BBNKUMT": return BITDUBAI_BNK_UNHOLD_MONEY_TRANSACTION;
            case "BBNKDMT": return BITDUBAI_BNK_DEPOSIT_MONEY_TRANSACTION;
            case "BBNKWMT": return BITDUBAI_BNK_WITHDRAW_MONEY_TRANSACTION;
            case "BBNKBMW": return BITDUBAI_BNK_BANK_MONEY_WALLET;
            case "BBNKBMWM": return BITDUBAI_BNK_BANK_MONEY_WALLET_MODULE;
            case "BCSHMTH": return BITDUBAI_CSH_MONEY_TRANSACTION_HOLD;
            case "BCSHMTU": return BITDUBAI_CSH_MONEY_TRANSACTION_UNHOLD;
            case "BCSHMTD": return BITDUBAI_CSH_MONEY_TRANSACTION_DEPOSIT;
            case "BCSHMTW": return BITDUBAI_CSH_MONEY_TRANSACTION_WITHDRAWAL;
            case "BCSHMWM": return BITDUBAI_CSH_MONEY_WALLET_MODULE;
            case "BCSHWCM": return BITDUBAI_CSH_WALLET_CASH_MONEY;
            case "BCERPF":  return BITDUBAI_CER_PROVIDER_FILTER;
            case "BCERPBV": return BITDUBAI_CER_PROVIDER_BITCOINVENEZUELA;
            case "BCERPDT": return BITDUBAI_CER_PROVIDER_DOLARTODAY;
            case "BCERPEC": return BITDUBAI_CER_PROVIDER_ELCRONISTA;
            case "BCERPECB":return BITDUBAI_CER_PROVIDER_EUROPEAN_CENTRAL_BANK;
            case "BCERPLN": return BITDUBAI_CER_PROVIDER_LANACION;
            case "BCERPYH": return BITDUBAI_CER_PROVIDER_YAHOO;
            case ("NGTR"):  return NEGOTIATION_TRANSMISSION         ;
            case ("CBNE"):  return CUSTOMER_BROKER_NEW              ;
            case ("CBUP"):  return CUSTOMER_BROKER_UPDATE           ;
            case ("CBCL"):  return CUSTOMER_BROKER_CLOSE            ;
            case ("NGS"):   return NEGOTIATION_SALE                 ;
            case ("NGP"):   return NEGOTIATION_PURCHASE             ;
            case "OPC":     return OPEN_CONTRACT                    ;
            case "CLC":     return CLOSE_CONTRACT                   ;
            case "CONP":    return CUSTOMER_ONLINE_PAYMENT          ;
            case "COFP":    return CUSTOMER_OFFLINE_PAYMENT         ;
            case "BAFP":    return BROKER_ACK_OFFLINE_PAYMENT       ;
            case "BAOP":    return BROKER_ACK_ONLINE_PAYMENT        ;
            case "CAOM":    return CUSTOMER_ACK_ONLINE_MERCHANDISE  ;
            case "BSOM":    return BROKER_SUBMIT_ONLINE_MERCHANDISE ;
            case "BSFM":    return BROKER_SUBMIT_OFFLINE_MERCHANDISE;
            case "MAEN":    return MATCHING_ENGINE;

            case "BCNNODE"   :  return BITDUBAI_COMMUNICATIONS_NETWORK_NODE;
            case "BCNCLIENT" :  return BITDUBAI_COMMUNICATIONS_NETWORK_CLIENT;
            case "CHMID":       return CHAT_MIDDLEWARE                          ;
            case "CHTNS":       return CHAT_NETWORK_SERVICE                     ;
            case "CHTSAM":      return CHAT_SUP_APP_MODULE                      ;
            case "CCPODT" :return CCP_OUTGOING_DRAFT_TRANSACTION;
            case "ANSART":   return ACTOR_NETWORK_SERVICE_ARTIST;
            case "ARTIDNTY": return ARTIST_IDENTITY;
            case "TOKAP":   return API_TOKENLY                      ;

            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "This Code Is Not Valid for the Plugins enum"
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
