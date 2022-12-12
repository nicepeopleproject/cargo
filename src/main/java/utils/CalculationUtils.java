package utils;

import models.Box;
import models.BoxParams;
import models.Truck;

import java.util.List;

public class CalculationUtils {
    public static BoxParams getMaxDimensionsOfFitableBox(Truck truck){
        // TODO возвращает размеры максимальной коробки, которая может поместиться в грузовик
        return null;
    }

    public static boolean hasEnoughSpace(Box box, BoxParams boxParams){
        // TODO возвращает истинну, если коробка box можно разместить в параллепипеде с параметрами boxParams, иначе false
        return false;
    }

    public static Truck findBestTruckForTransportation(List<Truck> trucks, Box box, Double kmDistance){
        //todo выбирает наиболее выгодный грузовик из списка для доставки товара
        return null;
    }
}
