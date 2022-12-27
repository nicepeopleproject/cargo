package utils;

import models.Box;
import models.BoxParams;
import models.Truck;

import java.util.*;

public class CalculationUtils {
    public static BoxParams getMaxDimensionsOfFitableBox(Truck truck){
        BoxParams params = truck.getMaxBoxParams();
        List<Box> boxes = truck.getBoxList();

        List<Double> props = new ArrayList<>(3);
        props.add(params.getHeight());
        props.add(params.getWidth());
        props.add(params.getLength());

        double bodyVolume = params.getHeight() * params.getLength() * params.getWidth();

        Collections.sort(props);


        double minParam = props.get(0);
        // максимальный параметр - глубина
        double maxParam = props.get(2);
        // средний параметр - высота
        double srParam = props.get(1);


        double miniBoxi = 0.0;
        double curSrParam = srParam;
        double curMinParam = minParam;
        double curMaxParam = maxParam;


        // сортировка боксов в boxes по их минимальному параметру - глубине
        for(int b = 0; b < boxes.size(); b++)
        {
            Box curBox = boxes.get(b);
            double boxVolume = curBox.getLength() * curBox.getWidth() * curBox.getHeight();
            if(boxVolume <= bodyVolume)
            {
                List<Double> curBoxParam = new ArrayList<>(3);
                curBoxParam.add(curBox.getHeight());
                curBoxParam.add(curBox.getWidth());
                curBoxParam.add(curBox.getLength());

                // сортировка параметров конкретной коробки
                Collections.sort(curBoxParam);

                if(curBoxParam.get(2) <= maxParam & curBoxParam.get(1) <= srParam & curBoxParam.get(0) <= minParam)
                {
                    if(curBoxParam.get(0) > miniBoxi)
                    {
                        miniBoxi = curBoxParam.get(0);
                        boxes.remove(b);
                        boxes.add(0, curBox);
                    }
                }
                else
                {
                    return null;
                }
            }
            else
            {
                return null;
            }


        }

        // параметры текущего слоя коробок в кузове
        double potolok = 0.0;
        double pol = 0.0;
        double stena_left = 0.0;
        double stena_right = 0.0;


        // расстоновка коробок по слоям
        for (Box curBox : boxes) {

            List<Double> scurBoxParam = new ArrayList<>(3);
            scurBoxParam.add(curBox.getHeight());
            scurBoxParam.add(curBox.getWidth());
            scurBoxParam.add(curBox.getLength());

            // сортировка параметров конкретной коробки
            Collections.sort(scurBoxParam);

            if (scurBoxParam.get(2) <= curMaxParam) {
                if (scurBoxParam.get(1) <= curSrParam) {
                    if (scurBoxParam.get(0) <= curMinParam) // заполнение слоя
                    {
                        curMinParam -= scurBoxParam.get(0);
                        if (potolok < (scurBoxParam.get(1) + pol)) {
                            potolok = scurBoxParam.get(1) + pol;
                        }

                        if (stena_right < (scurBoxParam.get(2) + stena_left)) {
                            stena_right = scurBoxParam.get(2) + stena_left;
                        }
                    } else // переход на следующий слой
                    {
                        curMinParam = minParam;
                        curSrParam -= potolok;
                        pol = potolok;
                    }
                } else // переход к следующей группе слоев
                {
                    curMinParam = minParam;
                    curSrParam = srParam;
                    curMaxParam -= stena_right;
                    stena_left = stena_right;
                    pol = 0.0;
                    potolok = 0.0;
                }
            } else {
                curMaxParam -= stena_right;
                break;
            }
        }

        return new BoxParams(minParam, srParam, curMaxParam);
    }

    public static boolean hasEnoughSpace(Box box, BoxParams boxParams){
        double bodyVolume = boxParams.getHeight() * boxParams.getWidth() * boxParams.getLength();
        double boxVolume = box.getLength() * box.getHeight() * box.getWidth();

        List<Double> bodyParams = new ArrayList<>(3);
        bodyParams.add(boxParams.getLength());
        bodyParams.add(boxParams.getWidth());
        bodyParams.add(boxParams.getHeight());

        List<Double> boksParams = new ArrayList<>(3);
        boksParams.add(box.getLength());
        boksParams.add(box.getWidth());
        boksParams.add(box.getHeight());


        // cортировка параметров коробки
        Collections.sort(boksParams);

        //сортировка параметров кузова
        Collections.sort(bodyParams);

        if (boxVolume <= bodyVolume)
        {
            return boksParams.get(0) <= bodyParams.get(0) & boksParams.get(1) <= bodyParams.get(1) & boksParams.get(2) <= bodyParams.get(2);
        }

    return false;
    }

    public static Truck findBestTruckForTransportation(List<Truck> trucks, Box box, Double kmDistance){
        double maxi_sum = 0.000;
        int maxi_tr_ID = 0;
        for (int tr = 0; tr < trucks.size(); tr++ )
        {
            Truck cur_truck = trucks.get(tr);
            double sumi = 0.0;
            double kg = box.getDensity() * box.getHeight() * box.getLength() * box.getWidth();
            sumi += cur_truck.getCostOfCall();
            sumi += kg * cur_truck.getCostOfLoadingPerKilo();
            sumi += kg * cur_truck.getCostOfUnloadingPerKilogram();
            sumi += kmDistance * cur_truck.getPricePerKilometer();

            if(sumi > maxi_sum)
            {
                maxi_tr_ID = tr;
                maxi_sum = sumi;
            }
        }
        return trucks.get(maxi_tr_ID);
    }
}
