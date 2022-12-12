package models;

import java.util.List;

public class Truck {
    private List<Box> boxList;
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
        // TODO реализовать методов использовать
        return false;
    }

    public Double priceForTransportation(double kmDistance, Box box){
        // TODO если нет места то выбрасываем ошибку throw new RuntimeError()
        // TODO иначе вычисляем стоимость и возвращаем
        return null;
    }

    public void loadingOfCargo(Box box){
        // TODO если есть место, то грузим товар
        // иначе выбрасываем ошибку
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
