package com.noteitapp.dev.noteit;

/**
 * Created by Jeeva on 4/7/2016.
 */
public class Utils {



    public static String[] colors = {
            "#ef5350","#f44336","#e53935","#d32f2f","#c62828","#b71c1c",
            "#ec407a","#e91e63","#d81b60","#c2185b","#ad1457","#880e4f",
            "#ab47bc","#9c27b0","#8e24aa","#7b1fa2","#7b1fa2","#4a148c",
            "#7e57c2","#673ab7","#5e35b1","#512da8","#4527a0","#311b92",
            "#5c6bc0","#3f51b5","#3949ab","#303f9f","#283593","#1a237e",
            "#42a5f5","#2196f3","#1e88e5","#1976d2","#1565c0","#0d47a1",
            "#29b6f6","#03a9f4","#039be5","#0288d1","#0277bd","#01579b",
            "#26c6da","#00bcd4","#00acc1","#0097a7","#00838f","#006064",
            "#26a69a","#009688","#00897b","#00796b","#00695c","#004d40",
            "#66bb6a","#4caf50","#43a047","#388e3c","#2e7d32","#1b5e20",
            "#d4e157","#cddc39","#c0ca33","#afb42b","#9e9d24","#827717",
            "#ffee58","#ffeb3b","#fdd835","#fbc02d","#f9a825","#f57f17",
            "#ffca28","#ffc107","#ffb300","#ffa000","#ff8f00","#ff6f00",
            "#ffa726","#ff9800","#fb8c00","#f57c00","#ef6c00","#e65100",
            "#e65100","#ff5722","#f4511e","#e64a19","#d84315","#bf360c",
            "#8d6e63","#795548","#6d4c41","#5d4037","#4e342e","#3e2723",
            "#bdbdbd","#9e9e9e","#757575","#616161","#424242","#212121",
            "#78909c","#607d8b","#546e7a","#455a64","#37474f","#263238"
    };

    public static String caps(String tmp){

        return tmp.substring(0,1).toUpperCase() + tmp.substring(1);
    }

}
