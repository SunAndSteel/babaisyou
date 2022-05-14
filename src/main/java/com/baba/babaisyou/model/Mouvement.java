package com.baba.babaisyou.model;

import com.baba.babaisyou.model.enums.Direction;
import com.baba.babaisyou.model.enums.Effect;
import com.baba.babaisyou.model.enums.Material;
import com.baba.babaisyou.presenter.Game;
import com.baba.babaisyou.view.GameObjectView;

import java.util.*;

public class Mouvement {

    private static final Map<GameObject, Direction> movedObjects = new LinkedHashMap<>();

    /**
     * Bouge l'objet si c'est possible
     * @param direction La direction vers laquelle on veut bouger
     */
    public static void move(GameObject object, Direction direction, Level level) {
        if (!isMovable(object, direction, level))
            return;

        int dX = direction.dX; int dY = direction.dY;
        int x = object.getX(); int y = object.getY();
        int newX = x + dX; int newY = y + dY;
        Game game = Game.getInstance();
        Map<Material, ArrayList<GameObject>> instances = level.getInstances();


        // Itérer dans ce sens permet de bouger par exemple : s'il y a plusieurs objets movable
        // aux mêmes endroits, alors on bouge d'abord l'objet qui est derrière l'autre dans la liste (et donc qui est affiché devant l'autre lors du print)
        for (int i = (level.get(newX, newY).size() - 1); i >= 0; i--) {
            GameObject object2 = level.get(newX, newY).get(i);

            ArrayList<Effect> tags = object2.getTags();

            if (tags.contains(Effect.Movable)) {
                move(object2, direction, level);

            } else if (tags.contains(Effect.Killer)) {
                level.get(x, y).remove(object);
                instances.get(object.getMaterial()).remove(object);
                movedObjects.put(object, Direction.NONE);
                return;

            } else if (tags.contains(Effect.Winner) &&
                    object.getTags().contains(Effect.Player)) {

                game.setWin(true);
            } else if (tags.contains(Effect.Play)) {
                game.mapLoadLevel(Level.getCurrentLevelNbr() + 1);
            }
        }

        movedObjects.put(object, direction);

        level.get(x, y).remove(object);
        level.get(newX, newY).add(object);

        object.setX(newX);
        object.setY(newY);
    }

    /**
     * Vérifie si on peut bouger l'objet dans une direction
     * @param direction La direction
     * @return Vrai si on peut bouger dans la direction, sinon faux
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean isMovable(GameObject object, Direction direction, Level level) {
        int dX = direction.dX; int dY = direction.dY;
        int newX = object.getX() + dX; int newY = object.getY() + dY;

        if ((0 > newX || newX > level.getSizeX() - 1) || (0 > newY || newY > level.getSizeY() - 1))
            return false;

        for (GameObject object2 : level.get(newX, newY)) {

            ArrayList<Effect> tags = object2.getTags();

            if (tags.contains(Effect.Player)) {
                return false;

            } else if (tags.contains(Effect.Movable)) {
                if (!isMovable(object2, direction, level))
                    return false;

            } else if (tags.contains(Effect.Hittable)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Permet de bouger tous les joueurs (si possible) dans une certaine direction.
     * @param direction Direction dans laquelle les joueurs vont bouger.
     */
    public static void movePlayers(Direction direction, Level level) {
//        Mouvement.getMovedObjects().clear();
        ArrayList<GameObject> players = new ArrayList<>();

        Map<Material, ArrayList<GameObject>> instances = level.getInstances();

        for (Rule rule : Rule.getRules()) {
            if (rule.getObj2().getMaterial() == Material.You) {
                players.addAll(instances.get(rule.getMaterial1()));
            }
        }

        // Range la liste par rapport aux x, y des objets
        Collections.sort(players);

        // Permet de bouger les joueurs dans le bon ordre sinon il pourrait y avoir une collision entre le joueur et un autre qui n'aurait pas encore bougé.
        if (direction == Direction.DOWN || direction == Direction.RIGHT) {
            for (int i = players.size() - 1; i >=0; i--) {
                GameObject player = players.get(i);
                move(player, direction, level);
            }
        } else {
            for (GameObject player : players) {
                move(player, direction, level);
            }
        }

        Map<GameObject, Direction> movedObjectsCopy = new HashMap<>(movedObjects);
        level.getReverseStack().add(movedObjectsCopy);
    }

    public static void reverse(Level level) {

        Stack<Map<GameObject, Direction>> reverseStack = level.getReverseStack();

        if (reverseStack.size() == 0) {
            return;
        }

        Map<GameObject, Direction> lastMovedObjects = reverseStack.pop();
        Map<Material, ArrayList<GameObject>> instances = level.getInstances();

        for (GameObject o : lastMovedObjects.keySet()) {

            int x = o.getX();
            int y = o.getY();

            level.get(x, y).remove(o);

            Direction direction = lastMovedObjects.get(o);

            int newX = x - direction.dX;
            int newY = y - direction.dY;

            level.get(newX, newY).add(o);

            o.setX(newX);
            o.setY(newY);

            o.setReverse(true);

            if (!instances.get(o.getMaterial()).contains(o)) {
                instances.get(o.getMaterial()).add(o);
            }

//            GameObject.addMovedObjects(o, direction.reverseDirection());
            movedObjects.put(o, direction.reverseDirection());
        }
    }

    /**
     *
     * @return La liste des objects qui ont bougé
     */
    public static Map<GameObject, Direction> getMovedObjects() {
        return movedObjects;
    }

    /**
     * Bouge l'objet sans vérifier les tags des objets
     * @param direction La direction vers laquelle on veut bouger
     */
    public static void moveWithoutChecking(GameObject object, Direction direction, Level level) {

        int dX = direction.dX; int dY = direction.dY;
        int x = object.getX(); int y = object.getY();
        int newX = x + dX; int newY = y + dY;

        if ((0 > newX || newX > level.getSizeX() - 1) || (0 > newY || newY > level.getSizeY() - 1)) {
            return;
        }

        movedObjects.put(object, direction);

        level.get(x, y).remove(object);
        level.get(newX, newY).add(object);

        object.setX(newX);
        object.setY(newY);
    }
}
