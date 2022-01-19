package assignment03;

import java.util.Comparator;

class NaturalComparator<T extends Comparable<T>>  implements Comparator<T> {
    @Override
    public int compare(T a, T b){
        return a.compareTo(b);
    }
}