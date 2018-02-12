package com.jcroberts.abalone.ai;

import com.jcroberts.abalone.game.GameBoard;
import com.jcroberts.abalone.game.GridSelections;
import com.jcroberts.abalone.game.Move;
import com.jcroberts.abalone.game.MovementLogic;

/**
 * Created by jcrob on 10/02/2018.
 */

public class AIMove extends Move {

    private int score;

    AIMove(GameBoard gb, GridSelections gs, MovementLogic ml, int s){
        super(gb, gs, ml);

        score = s;
    }

    int getScore(){
        return score;
    }
}
