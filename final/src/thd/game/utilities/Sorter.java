package thd.game.utilities;

import thd.gameobjects.base.GameObject;
import thd.gameobjects.base.Position;
import thd.gameview.GameView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;


public class Sorter implements Comparator<GameObject> {

    @Override
    public int compare(GameObject a, GameObject b) {
        return Integer.compare(a.positionInSort, b.positionInSort);
    }
}