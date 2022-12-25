package utils;

import models.Box;
import models.BoxParams;
import models.Truck;

import java.util.*;

public class CalculationUtils {
    public static BoxParams getMaxDimensionsOfFitableBox(Truck truck){
        BoxParams params = truck.getMaxBoxParams();
        List<Box> boxes = truck.getBoxList();

        List<Double> props = new ArrayList<Double>();
        props.add(params.getHeight());
        props.add(params.getWidth());
        props.add(params.getLength());

        double minParam = 10000000000000000.0000000;
        double bodyVolume = 1.0;

        // устанавливаю минимальный параметр (длина) исходного кузова и его объем
        for(int i = 0; i < 3; i++)
        {
            bodyVolume *= props.get(i);
            if(props.get(i) < minParam )
            {
                minParam = props.get(i);
                props.remove(i);
                props.add(0, minParam);

            }

        }

        // максимальный параметр - глубина
        double maxParam = props.get(-1);
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
            double minCurBoxParam = 1000000000000.0000000;
            if(boxVolume <= bodyVolume)
            {
                List<Double> curBoxParam = new ArrayList<Double>(3);
                curBoxParam.add(curBox.getHeight());
                curBoxParam.add(curBox.getWidth());
                curBoxParam.add(curBox.getLength());

                // сортировка параметров конкретной коробки
                for(int a = 0; a < 3; a++)
                {
                    if(curBoxParam.get(a) < minCurBoxParam)
                    {
                        minCurBoxParam = curBoxParam.get(a);
                        curBoxParam.remove(a);
                        curBoxParam.add(0, minCurBoxParam);
                    }
                }

                if(curBoxParam.get(-1) <= maxParam & curBoxParam.get(1) <= srParam & curBoxParam.get(0) <= minParam)
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
        for(int b = 0; b < boxes.size(); b++)
        {
            Box curBox = boxes.get(b);
            double minCurBoxParam = 1000000000000.0000000;

            List<Double> scurBoxParam = new ArrayList<Double>(3);
            scurBoxParam.add(curBox.getHeight());
            scurBoxParam.add(curBox.getWidth());
            scurBoxParam.add(curBox.getLength());

            // сортировка параметров конкретной коробки
            for(int a = 0; a < 3; a++)
            {
                if(scurBoxParam.get(a) < minCurBoxParam)
                {
                    minCurBoxParam = scurBoxParam.get(a);
                    scurBoxParam.remove(a);
                    scurBoxParam.add(0, minCurBoxParam);
                }
            }

            if (scurBoxParam.get(-1) <= curMaxParam)
            {
                if (scurBoxParam.get(1) <= curSrParam)
                {
                    if (scurBoxParam.get(0) <= curMinParam) // заполнение слоя
                    {
                        curMinParam -= scurBoxParam.get(0);
                        if (potolok < (scurBoxParam.get(1) + pol)) {
                            potolok = scurBoxParam.get(1) + pol;
                        }

                        if (stena_right < (scurBoxParam.get(-1) + stena_left)) {
                            stena_right = scurBoxParam.get(-1) + stena_left;
                        }
                    }
                    else // переход на следующий слой
                    {
                        curMinParam = minParam;
                        curSrParam -= potolok;
                        pol = potolok;
                    }
                }
                else // переход к следующей группе слоев
                {
                    curMinParam = minParam;
                    curSrParam = srParam;
                    curMaxParam -= stena_right;
                    stena_left = stena_right;
                    pol = 0.0;
                    potolok = 0.0;
                }
            }
            else
            {
                curMaxParam -= stena_right;
                break;
            }
        }

        BoxParams res = new BoxParams(minParam, srParam, curMaxParam);
        return res;
    }

    public static boolean hasEnoughSpace(Box box, BoxParams boxParams){
        double bodyVolume = boxParams.getHeight() * boxParams.getWidth() * boxParams.getLength();
        double boxVolume = box.getLength() * box.getHeight() * box.getWidth();

        double maxiBox = 0.0;
        double maxiParamBox = 0.0;

        List<Double> bodyParams = new ArrayList<>(3);
        bodyParams.add(boxParams.getLength());
        bodyParams.add(boxParams.getWidth());
        bodyParams.add(boxParams.getHeight());

        List<Double> boksParams = new ArrayList<>(3);
        boksParams.add(box.getLength());
        boksParams.add(box.getWidth());
        boksParams.add(box.getHeight());

        for(int b = 0; b < 3; b++ )
        {
            if(boksParams.get(b) > maxiBox)
            {
                maxiBox = boksParams.get(b);
                boksParams.remove(b);
                boksParams.add(0,maxiBox);
            }

            if(bodyParams.get(b) > maxiParamBox)
            {
                maxiParamBox = bodyParams.get(b);
                bodyParams.remove(b);
                bodyParams.add(0, maxiParamBox);
            }
        }

        if (boxVolume <= bodyVolume)
        {
            if (boksParams.get(0) <= bodyParams.get(0) & boksParams.get(1) <= bodyParams.get(1) & boksParams.get(2) <= bodyParams.get(2))
            {
                return true;
            }
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
