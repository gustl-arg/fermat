package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.layer.world.interfaces.FiatIndex;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.exceptions.CantSelectBestIndexException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro Bicelis on 11/13/2015.
 */
public class IndexHelper {


    public static FiatIndex selectBestIndex(List<FiatIndex> indexList) throws CantSelectBestIndexException {

        if(indexList.size() == 0)
            throw new CantSelectBestIndexException("Cant select best index. No indices submitted", null, "IndexHelper", "indexList size is zero");

        //Get rid of any indices that have zero in its purchase or sale price
        removeInvalidIndexes(indexList);

        if(indexList.size() == 0)
            throw new CantSelectBestIndexException("Cant select best index. No valid indices submitted", null, "IndexHelper", "submitted indices have 0 as purchase/sale value");

        //If there are more than two valid indices, try to remove extreme values
        if(indexList.size() >= 3)
            removeExtremeIndexes(indexList);

        //If there are still more than than two valid indices, calculate the mean, and return the closest index to it
        if(indexList.size() >= 3)
            return getIndexClosestToMean(indexList);


        //If there are only two, return the highest one
        if(indexList.size() == 2)
        {
            double averageIndexA, averageIndexB;
            averageIndexA = indexList.get(0).getPurchasePrice() + indexList.get(0).getSalePrice();
            averageIndexB = indexList.get(1).getPurchasePrice() + indexList.get(1).getSalePrice();

            if(averageIndexA >= averageIndexB)
                return indexList.get(0);
            else
                return indexList.get(1);
        }

        //If there is only one index, return it, nothing more to do
        return indexList.get(0);
    }



    /*INTERNAL FUNCTIONS*/
    private static void removeInvalidIndexes(List<FiatIndex> indexList)
    {
        List<FiatIndex> toRemove = new ArrayList<>();
        for(FiatIndex index: indexList){

            if(index.getPurchasePrice() == 0 || index.getSalePrice() == 0) {
                System.out.println("FIATINDEX - Found INVALID INDEX: " + index.getProviderDescription());
                toRemove.add(index);
            }
        }
        indexList.removeAll(toRemove);
    }


    private static void removeExtremeIndexes(List<FiatIndex> indexList)
    {
        double mean = mean(indexList);
        double standardDeviation = standardDeviation(indexList);
        double extremeLow = mean-standardDeviation;
        double extremeHigh = mean+standardDeviation;

        List<FiatIndex> toRemove = new ArrayList<>();
        for(FiatIndex index: indexList){

            double purchaseSaleAverage = (index.getPurchasePrice() + index.getSalePrice()) / 2;
            if( (purchaseSaleAverage >= extremeHigh) || (purchaseSaleAverage <= extremeLow)) {
                System.out.println("FIATINDEX - Found EXTREME INDEX: " + index.getProviderDescription());
                System.out.println("FIATINDEX - mean=" + mean + "  stdDev=" + standardDeviation);
                System.out.println("FIATINDEX - extremeHigh=" + extremeHigh + "  extremeLow=" + extremeLow);
                System.out.println("FIATINDEX - purchasePrice=" + index.getPurchasePrice() + "  salePrice= " + index.getSalePrice() + ". Average of purchase/sale (" + purchaseSaleAverage + ") outside of extremes!");

                toRemove.add(index);
            }
        }
        indexList.removeAll(toRemove);
    }


    private static FiatIndex getIndexClosestToMean(List<FiatIndex> indexList)
    {
        FiatIndex closestIndex = null;
        double closestIndexDistanceToMean = Double.MAX_VALUE;
        double mean = mean(indexList);

        for(FiatIndex index : indexList)
        {
            double purchaseSaleAverage = (index.getPurchasePrice() + index.getSalePrice()) / 2;
            if( purchaseSaleAverage <= closestIndexDistanceToMean)
                closestIndex = index;
        }
        return closestIndex;
    }


    private static double mean(List<FiatIndex> indexList) {
        double sum = 0;
        for(FiatIndex index : indexList)
        {
            double purchaseSaleAverage = (index.getPurchasePrice() + index.getSalePrice()) / 2;
            sum += purchaseSaleAverage;
        }
        return sum / indexList.size();
    }


    private static double standardDeviation(List<FiatIndex> indexList) {
        double sum = 0;
        double mean = mean(indexList);

        for(FiatIndex index : indexList)
        {
            double purchaseSaleAverage = (index.getPurchasePrice() + index.getSalePrice()) / 2;
            sum += Math.pow(purchaseSaleAverage - mean, 2);
        }
        return Math.sqrt(sum / indexList.size());
    }
}
