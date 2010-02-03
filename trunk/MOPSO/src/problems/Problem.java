package problems;

import java.util.ArrayList;

public interface Problem {

    public abstract ArrayList<ObjectiveFunction> getFunctions();

    public abstract int getNDimensions();

    public abstract int getNObjectives();

    public abstract double getMaxBound( int dimension );

    public abstract double getMinBound( int dimension );

    public abstract double getMaxInitValue( int dimension );

    public abstract double getMinInitValue( int dimension );

    public abstract double getGValue();

    public abstract String getName();
}

