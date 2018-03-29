package com.jcroberts.abalone;

import com.jcroberts.abalone.game.GameBoard;
import com.jcroberts.abalone.game.GridSelections;
import com.jcroberts.abalone.game.Move;
import com.jcroberts.abalone.game.MovementLogic;
import com.jcroberts.abalone.game.SelectionChecker;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * JUnit tests for the base game
 * Author: Joshua Roberts
 */

public class BaseGameTest {
    private GridSelections gridSelections;
    private SelectionChecker selectionChecker;
    private GameBoard gameBoard;

    private final int[][] TEST_BOARD = {
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, 2, 2, 1, 1, 1, -1, -1, -1, -1, -1},
            {-1, 0, 1, 0, 0, 0, 0, -1, -1, -1, -1},
            {-1, 0, 0, 1, 2, 2, 2, 1, -1, -1, -1},
            {-1, 0, 0, 0, 0, 0, 0, 2, 0, -1, -1},
            {-1, 0, 0, 2, 0, 0, 0, 0, 1, 0, -1},
            {-1, -1, 0, 1, 2, 2, 1, 0, 0, 0, -1},
            {-1, -1, -1, 1, 0, 0, 1, 0, 0, 0, -1},
            {-1, -1, -1, -1, 0, 0, 2, 0, 0, 0, -1},
            {-1, -1, -1, -1, -1, 0, 0, 0, 0, 0, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}
    };

    @Before
    public void init(){
        gridSelections = new GridSelections();
        selectionChecker = new SelectionChecker();
        gameBoard = new GameBoard(TEST_BOARD);
    }

    @Test
    public void testGridSelectionsAreSame(){
        GridSelections firstSelectionSet = new GridSelections();
        firstSelectionSet.add(new int[]{3, 3});

        GridSelections secondSelectionSet = new GridSelections();
        secondSelectionSet.add(new int[]{3, 3});

        assertTrue("3, 3 is apparently not the same as 3, 3", firstSelectionSet.equals(secondSelectionSet));

        firstSelectionSet = new GridSelections();
        firstSelectionSet.add(new int[]{3, 3});

        secondSelectionSet = new GridSelections();
        secondSelectionSet.add(new int[]{3, 4});

        assertFalse("3, 3 is apparently the same as 3, 4", firstSelectionSet.equals(secondSelectionSet));

        firstSelectionSet.add(new int[]{3, 4});
        secondSelectionSet.add(new int[]{3, 3});

        assertTrue("[[3, 3][3, 4]] is apparently different to [[3, 3][3, 4]]", firstSelectionSet.equals(secondSelectionSet));
    }

    @Test
    public void testSelectionCheckingForLeftAndRight(){
        gridSelections.add(new int[]{3, 5});

        assertTrue("3, 6 cannot be added to 3, 5", selectionChecker.counterSelectionIsLegal(new int[]{3, 6}, gridSelections));
        assertTrue("3, 4 cannot be added to 3, 5", selectionChecker.counterSelectionIsLegal(new int[]{3, 4}, gridSelections));
        assertFalse("7, 4 is added to 3, 5", selectionChecker.counterSelectionIsLegal(new int[]{7, 4}, gridSelections));
        assertFalse("3, 3 is added to 3, 5", selectionChecker.counterSelectionIsLegal(new int[]{3, 3}, gridSelections));

        gridSelections.add(new int[]{3, 6});
        assertFalse("4, 7 was added when it shouldn't be", selectionChecker.counterSelectionIsLegal(new int[]{4, 7}, gridSelections));
        assertTrue("3, 4 cannot be added", selectionChecker.counterSelectionIsLegal(new int[]{3, 4}, gridSelections));
        assertTrue("3, 7 cannot be added", selectionChecker.counterSelectionIsLegal(new int[]{3, 7}, gridSelections));
    }

    @Test
    public void testSelectionCheckingForUpLeftAndDownRight(){
        gridSelections.add(new int[]{2, 2});
        assertTrue("3, 3 cannot be added to 2, 2", selectionChecker.counterSelectionIsLegal(new int[]{3, 3}, gridSelections));
        assertTrue("1, 1 cannot be added to 2, 2", selectionChecker.counterSelectionIsLegal(new int[]{1, 1}, gridSelections));
        assertFalse("4, 4 was added", selectionChecker.counterSelectionIsLegal(new int[]{4, 4}, gridSelections));

        gridSelections.add(new int[]{3, 3});
        assertTrue("4, 4 cannot be added", selectionChecker.counterSelectionIsLegal(new int[]{4, 4}, gridSelections));
        assertTrue("1, 1 cannot be added", selectionChecker.counterSelectionIsLegal(new int[]{1, 1}, gridSelections));
        assertFalse("3, 3 has been added again", selectionChecker.counterSelectionIsLegal(new int[]{3, 3}, gridSelections));
        assertFalse("5, 5 was added", selectionChecker.counterSelectionIsLegal(new int[]{5, 5}, gridSelections));

        gridSelections.add(new int[]{4, 4});
        assertFalse("5, 5 was added", selectionChecker.counterSelectionIsLegal(new int[]{5, 5}, gridSelections));
        assertFalse("2, 3 was added", selectionChecker.counterSelectionIsLegal(new int[]{2, 3}, gridSelections));
        assertFalse("2, 2 was added again", selectionChecker.counterSelectionIsLegal(new int[]{2, 2}, gridSelections));
    }

    @Test
    public void testSelectionCheckingForUpRightAndDownLeft(){
        gridSelections.add(new int[]{5, 5});
        assertTrue("4, 5 cannot be added", selectionChecker.counterSelectionIsLegal(new int[]{4, 5}, gridSelections));
        assertTrue("6, 5 cannot be added", selectionChecker.counterSelectionIsLegal(new int[]{6, 5}, gridSelections));
        assertFalse("3, 5 was added", selectionChecker.counterSelectionIsLegal(new int[]{3, 5}, gridSelections));

        gridSelections.add(new int[]{4, 5});
        assertTrue("3, 5 cannot be added", selectionChecker.counterSelectionIsLegal(new int[]{3, 5}, gridSelections));
        assertTrue("6, 5 cannot be added", selectionChecker.counterSelectionIsLegal(new int[]{6, 5}, gridSelections));
        assertFalse("5, 6 was added", selectionChecker.counterSelectionIsLegal(new int[]{5, 6}, gridSelections));

        gridSelections.add(new int[]{6, 5});
        assertFalse("5, 5 was added again", selectionChecker.counterSelectionIsLegal(new int[]{5, 5}, gridSelections));
        assertFalse("7, 5 was added", selectionChecker.counterSelectionIsLegal(new int[]{7, 5}, gridSelections));
    }

    @Test
    public void testTwoCountersBeingPushedAndOneTakenInDirectionLeft(){
        gridSelections.add(new int[]{1, 3});
        gridSelections.add(new int[]{1, 4});
        gridSelections.add(new int[]{1, 5});
        MovementLogic movementLogic = selectionChecker.checkMoveSelectionIsLegal(new int[]{1, 2}, gridSelections, gameBoard);

        assertTrue("Cannot move {1, 3} {1, 4} {1, 5} to {1, 2}", movementLogic.getIsMovementLegal());
        assertTrue("Movement is in the wrong direction", movementLogic.getMovementDirection() == Move.MOVE_LEFT);
        assertTrue("Not enough counters are being pushed",movementLogic.getNumberOfCountersBeingPushed() == 2);

        Move move = new Move(new GameBoard(gameBoard.copyGameBoard()), gridSelections, movementLogic);
        move.makeMove();
        assertTrue("Nothing has been taken here", move.getHasTakenACounter());
    }

    @Test
    public void testOneCounterBeingPushedAndTakenInDirectionUpLeft(){
        gridSelections.add(new int[]{3, 3});
        gridSelections.add(new int[]{2, 2});

        MovementLogic movementLogic = selectionChecker.checkMoveSelectionIsLegal(new int[]{1, 1}, gridSelections, gameBoard);

        assertTrue("Cannot move {3, 3} {2, 2} to {1, 1}", movementLogic.getIsMovementLegal());
        assertTrue("Move direction is wrong", movementLogic.getMovementDirection() == Move.MOVE_UP_LEFT);
        assertTrue("No counters are being pushed", movementLogic.getNumberOfCountersBeingPushed() == 1);

        Move move = new Move(new GameBoard(gameBoard.copyGameBoard()), gridSelections, movementLogic);
        move.makeMove();
        assertTrue("Nothing has been taken here", move.getHasTakenACounter());
    }

    @Test
    public void testMovementPushingOneCounterInDirectionUpRight(){
        gridSelections.add(new int[]{6, 3});
        gridSelections.add(new int[]{7, 3});

        MovementLogic movementLogic = selectionChecker.checkMoveSelectionIsLegal(new int[]{5, 3}, gridSelections, gameBoard);

        assertTrue("Cannot move", movementLogic.getIsMovementLegal());
        assertTrue("Movement direction is wrong", movementLogic.getMovementDirection() == Move.MOVE_UP_RIGHT);
        assertTrue("No counters are being pushed", movementLogic.getNumberOfCountersBeingPushed() == 1);
    }

    @Test
    public void testMovementPushingOneCounterWithThreeInDirectionRight(){
        gridSelections.add(new int[]{3, 5});
        gridSelections.add(new int[]{3, 4});
        gridSelections.add(new int[]{3, 6});

        MovementLogic movementLogic = selectionChecker.checkMoveSelectionIsLegal(new int[]{3, 7}, gridSelections, gameBoard);

        assertTrue("Cannot move", movementLogic.getIsMovementLegal());
        assertTrue("Movement direction is wrong", movementLogic.getMovementDirection() == Move.MOVE_RIGHT);
        assertTrue("No counters are being pushed", movementLogic.getNumberOfCountersBeingPushed() == 1);

        Move move = new Move(new GameBoard(gameBoard.copyGameBoard()), gridSelections, movementLogic);
        move.makeMove();
        assertTrue("Nothing has been taken here", move.getHasTakenACounter());
    }

    @Test
    public void testMovementPushingOneCounterWithTwoInDirectionDownRight(){
        gridSelections.add(new int[]{3, 6});
        gridSelections.add(new int[]{4, 7});

        MovementLogic movementLogic = selectionChecker.checkMoveSelectionIsLegal(new int[]{5, 8}, gridSelections, gameBoard);

        assertTrue("Movement is illegal", movementLogic.getIsMovementLegal());
        assertTrue("Movement direction is incorrect", movementLogic.getMovementDirection() == Move.MOVE_DOWN_RIGHT);
        assertTrue("No counters being pushed", movementLogic.getNumberOfCountersBeingPushed() == 1);
    }

    @Test
    public void  testMovementPushingOneCounterWithTwoInDirectionDownLeft(){
        gridSelections.add(new int[]{6, 6});
        gridSelections.add(new int[]{7, 6});

        MovementLogic movementLogic = selectionChecker.checkMoveSelectionIsLegal(new int[]{8, 6}, gridSelections, gameBoard);

        assertTrue("Movement is illegal", movementLogic.getIsMovementLegal());
        assertTrue("Movement direction is incorrect", movementLogic.getMovementDirection() == Move.MOVE_DOWN_LEFT);
        assertTrue("No counters being pushed", movementLogic.getNumberOfCountersBeingPushed() == 1);

        Move move = new Move(new GameBoard(gameBoard.copyGameBoard()), gridSelections, movementLogic);
        move.makeMove();
        assertFalse("Has been taken here", move.getHasTakenACounter());
    }

}
