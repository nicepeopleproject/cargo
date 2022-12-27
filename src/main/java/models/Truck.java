package models;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import utils.CalculationUtils;

public class Truck {
    private List<Box> boxList = new ArrayList<>();
    private double costOfCall; // стоимость подачи
    private double costOfLoadingPerKilo; // стоимость загрузки килограма
    private double costOfUnloadingPerKilogram; // стоимость разгрузки за кг
    private double pricePerKilometer; // стоимость траспортировки за 1 км

    private BoxParams maxBoxParams; //максимальная вместительность

    public Truck(double costOfCall, double costOfLoadingPerKilo, double costOfUnloadingPerKilogram, double pricePerKilometer, BoxParams maxBoxParams) {
        this.costOfCall = costOfCall;
        this.costOfLoadingPerKilo = costOfLoadingPerKilo;
        this.costOfUnloadingPerKilogram = costOfUnloadingPerKilogram;
        this.pricePerKilometer = pricePerKilometer;
        this.maxBoxParams = maxBoxParams;
    }

    public boolean hasEnoughSpaceToPlace(Box box){
        if (boxList.isEmpty()) {
            boxList.add(new Box(0, 0, 0, 0));
        }
        if(CalculationUtils.getMaxDimensionsOfFitableBox(new Truck(this.costOfCall, this.costOfLoadingPerKilo, this.costOfUnloadingPerKilogram, this.pricePerKilometer, this.maxBoxParams)) != null && CalculationUtils.hasEnoughSpace(box, CalculationUtils.getMaxDimensionsOfFitableBox(new Truck(this.costOfCall, this.costOfLoadingPerKilo, this.costOfUnloadingPerKilogram, this.pricePerKilometer, this.maxBoxParams)))){
            return true;
        }
        return false;
    }

    public Double priceForTransportation(double kmDistance, Box box){
        double price = 0;
        if(hasEnoughSpaceToPlace(box)){
            price = this.costOfCall + box.getWidth() * box.getHeight() * box.getLength() * box.getDensity() * (costOfLoadingPerKilo + costOfUnloadingPerKilogram) + pricePerKilometer * kmDistance;
        } else {
            throw new RuntimeException("Не лезет!");
        }
        return price;
    }

    public void loadingOfCargo(Box box){
        if(hasEnoughSpaceToPlace(box)){
            this.boxList.add(box);
        } else {
            throw new RuntimeException("Не лезет!");
        }
    }

    public List<Box> getBoxList() {
        return boxList;
    }

    public double getCostOfCall() {
        return costOfCall;
    }

    public double getCostOfLoadingPerKilo() {
        return costOfLoadingPerKilo;
    }

    public double getCostOfUnloadingPerKilogram() {
        return costOfUnloadingPerKilogram;
    }

    public double getPricePerKilometer() {
        return pricePerKilometer;
    }

    public BoxParams getMaxBoxParams() {
        return maxBoxParams;
    }
}
