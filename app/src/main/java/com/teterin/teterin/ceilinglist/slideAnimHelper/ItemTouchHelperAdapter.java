package com.teterin.teterin.ceilinglist.slideAnimHelper;

/**
 * Created by ar4er25 on 4/3/2017.
 */

public interface ItemTouchHelperAdapter {


    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
