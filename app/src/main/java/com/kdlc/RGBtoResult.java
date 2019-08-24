package com.kdlc;

import android.os.Build;
import android.support.annotation.RequiresApi;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class RGBtoResult {

@RequiresApi(api = Build.VERSION_CODES.O)
public static int ResistorResult(double r,double g,double b) {


    double RED = r;
    double GREEN = g;
    double BLUE = b;
    double MED1 = max(RED, GREEN);
    double MAX = max(MED1, BLUE);
    double MED2 = min(RED, GREEN);
    double MIN = min(MED2, BLUE);
    double H=0, S=0, V=0;
    /*  求H值  */
    if (RED == MAX)
        H = (GREEN - BLUE) / (MAX - MIN) * 30;
    if (GREEN == MAX)
        H = (BLUE - RED) / (MAX - MIN) * 30 + 60;
    if (BLUE == MAX)
        H = (RED - GREEN) / (MAX - MIN) * 30 + 120;
    if (H < 0) H += 180;
    /*  求S值  */
    if (MAX == 0)
        S = 0;
    else S = (MAX - MIN) / MAX * 255;
    /*  求V值  */
    V = MAX;

    /* 将H,S,V取整*/
    H = (int) H;
    S = (int) S;
    V = (int) V;
    /*  选择颜色种类  */
    /*  黑色  */
    if (H <= 128)
        if (V >= 0 && V <= 100)
            if (S >= 41) return 0;
    /*  棕色  */
    if (H >= 128)
        if (V >= 60 && V <= 120)
            if (S <= 80 && S >= 32) return 1;
    if (V >= 100 && V <= 255) {
        if (S >= 40 && S <= 255)
            /*  红色  */ {
            if (H >= 0 && H <= 5 || H >= 156 && H <= 180)
                return 2;
                /*  橙色  */
            else if (H >= 6 && H <= 18)
                return 3;
                /*  金色  */
            else if (H>=19 && H<=60)
                return -1;
                /*  绿色  */
            else if (H >= 61 && H <= 100)
                return 5;
                /*  蓝色，没有，不需要  */
                /*  紫色  */
            else if (H >= 101 && H <= 155)
                return 7;
        }
    }
    /*  灰色  */
    if (V >= 46 && V <= 200) {
        if (S >= 0 && S <= 31)
            return  8;
    }
    /*  白色  */
    if (V >= 200 && V <= 255) {
        if (S >= 0 && S <= 30)
            return 9;
    }


return 0;
}


}
