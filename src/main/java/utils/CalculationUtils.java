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


        double maxiBoxi = 0.0;
        double curSrParam = srParam;

        // сортировка боксов в boxes по их максимальному параметру - глубине
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

                if(curBoxParam.get(1) <= curSrParam)
                {
                    if(curBoxParam.get(-1) >= maxiBoxi)
                    {
                        maxiBoxi = curBoxParam.get(-1);
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
        double curMinParam = minParam;
        double curMaxParam = maxParam;

        // расстоновка коробок по слоям
        for(int b = 0; b < boxes.size(); b++)
        {
            Box curBox = boxes.get(b);
            double boxVolume = curBox.getLength() * curBox.getWidth() * curBox.getHeight();
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

            if(scurBoxParam.get(-1) <= curMaxParam)
            {
                if (scurBoxParam.get(0) <= curMinParam) // заполнение слоя
                {
                    curMinParam -= scurBoxParam.get(0);
                    if (potolok < (scurBoxParam.get(-1) + pol))
                    {
                        potolok = scurBoxParam.get(-1) + pol;
                    }
                }
                else // переход на следующий слой
                {
                    curMinParam = minParam;
                    curMaxParam -= potolok;
                    pol = potolok;
                }
            }
            else
            {
                return null;
            }

        }
        curMaxParam -= potolok;
        BoxParams res = new BoxParams(minParam, srParam, curMaxParam);
        return res;
    }

    public static boolean hasEnoughSpace(Box box, BoxParams boxParams){
        double bodyVolume = boxParams.getHeight() * boxParams.getWidth() * boxParams.getLength();
        double boxVolume = box.getLength() * box.getHeight() * box.getWidth();
        if (boxVolume <= bodyVolume)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static Truck findBestTruckForTransportation(List<Truck> trucks, Box box, Double kmDistance){
        //todo выбирает наиболее выгодный грузовик из списка для доставки товара
        return null;
    }
}
