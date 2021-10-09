package frc.robot;

public final class Utilities{

    private static int upperIndex;

    private static int getUpperInterpIndex(double distance_arr[], double distance){
        int _size = distance_arr.length;
        for (int i = 0; i < _size; ++i){
            if (distance_arr[i] > distance){
                upperIndex = i;
                break;
            }
            else {
                upperIndex = 1; // TODO: What if distance is outside the table?
            }
        }
        return upperIndex;
    }

    private static int getLowerInterpIndex(int upper_index){
        return upper_index - 1;
    }

    public static double linearInterpolation(double distance_arr[], double y[], double distance) {
        int upper_index = getUpperInterpIndex(distance_arr, distance);
        int lower_index = getLowerInterpIndex(upper_index);

        // Linear Interp the y value for the current distance
        double y_interped = y[lower_index] + (distance - distance_arr[lower_index]) * (y[upper_index] - y[lower_index])
                / (distance_arr[upper_index] - distance_arr[lower_index]);
        return y_interped;
    }

}