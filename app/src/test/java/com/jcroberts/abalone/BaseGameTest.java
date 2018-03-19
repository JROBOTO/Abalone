package com.jcroberts.abalone;

import com.jcroberts.abalone.game.GridSelections;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by jcrob on 19/03/2018.
 */

public class BaseGameTest {

    @Test
    public void gridSelectionsAreSameTest(){
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
    }
}
