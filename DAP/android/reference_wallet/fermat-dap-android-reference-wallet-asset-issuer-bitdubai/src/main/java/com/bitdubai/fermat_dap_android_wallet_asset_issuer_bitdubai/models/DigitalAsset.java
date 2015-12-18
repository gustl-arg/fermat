package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models;

import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by francisco on 08/10/15.
 */
public class DigitalAsset {

    private String name;
    private String amount;
    private Long availableBalanceQuantity;
    private Long bookBalanceQuantity;
    private Long availableBalance;
    private Timestamp expDate;
    private String walletPublicKey;
    private String assetPublicKey;
    private ActorAssetUser actorAssetUser;
    private byte[] image;

    public DigitalAsset() {
    }

    public DigitalAsset(String name, String amount) {
        setName(name);
        setAmount(amount);
    }

    public static ArrayList<DigitalAsset> getAssets() {
        List<DigitalAsset> assets = new ArrayList<>();
        assets.add(new DigitalAsset("KFC Coupon", "150.457"));
        assets.add(new DigitalAsset("Burgerking Coupon", "150.457"));
        assets.add(new DigitalAsset("MacDonalds Coupon", "150.457"));
        assets.add(new DigitalAsset("Free Coupon", "150.457"));
        return (ArrayList<DigitalAsset>) assets;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return String.format("%s | %s BTC", name, amount);
    }

    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    public void setWalletPublicKey(String walletPublicKey) {
        this.walletPublicKey = walletPublicKey;
    }

    public String getAssetPublicKey() {
        return assetPublicKey;
    }

    public void setAssetPublicKey(String assetPublicKey) {
        this.assetPublicKey = assetPublicKey;
    }

    public ActorAssetUser getActorAssetUser() {
        return actorAssetUser;
    }

    public void setActorAssetUser(ActorAssetUser actorAssetUser) {
        this.actorAssetUser = actorAssetUser;
    }

    public Long getAvailableBalanceQuantity() {
        return availableBalanceQuantity;
    }

    public void setAvailableBalanceQuantity(Long availableBalanceQuantity) {
        this.availableBalanceQuantity = availableBalanceQuantity;
    }

    public Long getBookBalanceQuantity() {
        return bookBalanceQuantity;
    }

    public void setBookBalanceQuantity(Long bookBalanceQuantity) {
        this.bookBalanceQuantity = bookBalanceQuantity;
    }

    public void setAvailableBalance(Long availableBalance) {
        this.availableBalance = availableBalance;
    }

    public Long getAvailableBalance() {
        return availableBalance;
    }

    public Double getAvailableBalanceBitcoin() {
        return Double.valueOf(availableBalance) / 1000000;
    }

    public String getFormattedAvailableBalanceBitcoin() {
        DecimalFormat df = new DecimalFormat("0.000000");
        return df.format(getAvailableBalanceBitcoin());
    }

    public Date getExpDate() {
        return expDate;
    }

    public String getFormattedExpDate() {
        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        return df.format(expDate);
    }

    public void setExpDate(Timestamp expDate) {
        this.expDate = expDate;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
