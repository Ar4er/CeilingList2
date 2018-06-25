package com.teterin.teterin.ceilinglist.logic.details;

/**
 * Created by ar4er25 on 2/10/2017.
 */

public class Cd60 extends Detail {
    private static final String TAG = "Cd60";
    private double mSizeForUpSide;
    private double mSizeForDownSide;
    private int mDownSideStep;
    private Detail mCdCountOf3Size;
    private Detail mCdCountOf4Size;

    public Cd60(int x, int y, final double firstSize, final double secondSize, int CdStep) {
        super(x, y, firstSize);
        setLongWidth();
        mSizeForDownSide = chooseSize((int)firstSize, (int)secondSize, getCeilingWidth());
        mSizeForUpSide = chooseSize((int)firstSize, (int)secondSize, getCeilingLong());
        mDownSideStep = CdStep;
        if (mSizeForUpSide != mSizeForDownSide){
            if (mSizeForUpSide<mSizeForDownSide) {
                mCdCountOf3Size = new Detail(this) {
                    @Override
                    public int calculateCount(int x, int y) {
                        return countOfUpSIde();
                    }
                };
                mCdCountOf4Size = new Detail(this) {
                    @Override
                    public int calculateCount(int x, int y) {
                        return countInDownSIde();
                    }
                };
            } else {
                mCdCountOf3Size = new Detail(this) {
                    @Override
                    public int calculateCount(int x, int y) {
                        return countInDownSIde();
                    }
                };
                mCdCountOf4Size = new Detail(this) {
                    @Override
                    public int calculateCount(int x, int y) {
                        return countOfUpSIde();
                    }
                };
            }
            mCdCountOf3Size.setCount(mCdCountOf3Size.calculateCount(x, y));
            mCdCountOf4Size.setCount(mCdCountOf4Size.calculateCount(x, y));
            setCount(mCdCountOf3Size.getCount() + mCdCountOf4Size.getCount());
        }else {
            setCount(calculateCount(x, y));
           mCdCountOf3Size = new Detail(this) {
               @Override
               public int calculateCount(int x, int y) {
                   if (mSizeForDownSide == firstSize) {
                       return (int) firstSize;
                   } else return (int) secondSize;
               }
           };
            mCdCountOf3Size.setCount(mCdCountOf3Size.calculateCount(x, y));
        }
    }


    public Cd60(int count, final int countOf3m, final int countOf4m) {
        super(count);
        mCdCountOf3Size = new Detail(countOf3m) {
            @Override
            public int calculateCount(int x, int y) {
                return countOf3m;
            }
        };
        mCdCountOf4Size = new Detail(countOf4m) {
            @Override
            public int calculateCount(int x, int y) {
                return countOf4m;
            }
        };
    }



    @Override
    public int calculateCount(int x, int y) {
        return countOfUpSIde()+countInDownSIde();
    }

    public Detail getCdCountOf3Size() {
        return mCdCountOf3Size;
    }

    public Detail getCdCountOf4Size() {
        return mCdCountOf4Size;
    }

    public double getSizeForDownSide() {
        return mSizeForDownSide;
    }

    public double getSizeForUpSide() {
        return mSizeForUpSide;
    }

    public int getDownSideStep() {
        return mDownSideStep;
    }

    public void setDownSideStep(int downSideStep) {
        mDownSideStep = downSideStep;
    }

   // вычисление длинны и ширины


    // вычисление количества каркаса в нижнем уровне
    public int countInDownSIde(){
        int lines = getDownLines();
        double countInOneLine = getCeilingWidth()/mSizeForDownSide;
        return countInSide(lines, countInOneLine);
    }

    //колличество линий в нижнем уровне
    public int getDownLines() {
        return (int) Math.ceil(getCeilingLong()/getDownSideStep());
    }

    //колличество соединителей в одной  линии
    public int calculateConnectorsInLine(int ceilingLong, int cdSize){
        int count = 0;
        if (ceilingLong>cdSize) {
            ceilingLong-= cdSize;
            while (ceilingLong > 0) {
                ceilingLong-= cdSize;
                count++;
            }
        }
        return count;
    }

    //колличество соединителей в потолке
    public int calculateConnectors(){
        return calculateConnectorsInLine(getCeilingWidth(), (int)mSizeForDownSide) * getDownLines() +
                calculateConnectorsInLine(getCeilingLong(), (int)mSizeForUpSide) * calcuateUpLines();
    }

    //вычисление колличества верхних линий
    public int calcuateUpLines(){
        if (getCeilingWidth()<1000){
            return 0;
        }
        int sizebetweenextremeLines = getCeilingWidth() - 1000;
        int lines;
        if (sizebetweenextremeLines<=1000){
            lines = 2;
        }
        lines = sizebetweenextremeLines/1000+1;
        if (sizebetweenextremeLines%1000>100)
            lines+=1;
        return lines;
    }

    //вычисление количества каркаса в верхнем уровне
    public  int countOfUpSIde(){
        int lines = calcuateUpLines();
        double countInOneLine = getCeilingLong()/mSizeForUpSide;
        return countInSide(lines, countInOneLine);
    }

    // рассчет количества каркаса в уровне
    private int countInSide(int lines, double countInOneLine) {
        double rest = countInOneLine-(int)countInOneLine;
        if (rest>0.5){
            return  ((int)countInOneLine+1)*lines;
        }
        if (rest>0.33){
            countInOneLine = (int)countInOneLine+0.5;
        }
        double result = countInOneLine*lines;
        double roudedResult = Math.ceil(result);
        return (int)roudedResult;
    }

    //вычисление остатка каркаса в линии
    private double calculateRest (int size, int ceilingLong) {
        int rest = 0;
        if (size<ceilingLong) {
           int lastElement = ceilingLong%size;
            rest = size - lastElement;
        }
        if (rest>=size){
            rest = rest - size;
        }
        return rest;
    }

    private double chooseSize (int firstSize, int secendSize, int ceilingLong) {
        if (firstSize==0.0)
            return secendSize;
        if (secendSize==0.0)
            return firstSize;
        if (firstSize>=ceilingLong&&firstSize<secendSize)
            return firstSize;
        if (secendSize>=ceilingLong&&secendSize<firstSize)
            return secendSize;
        double firstRest = calculateRest(firstSize, ceilingLong);
        double secendRest = calculateRest(secendSize, ceilingLong);
        if (firstRest<secendRest) {
            return firstSize;
        } else {
            return  secendSize;
        }
    }

   public static void main(String[] args) {
       Cd60 cd60 = new Cd60(6000, 5000, 3000.0, 4000.0, 400);
       System.out.println(cd60.calculateConnectors());
    }
}
