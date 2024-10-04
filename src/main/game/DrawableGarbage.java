package main.game;

import main.entity.Drawable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DrawableGarbage {

    private final List<Drawable> drawablesToRemove = new ArrayList<>();

    public void add(Drawable drawable) {
        drawablesToRemove.add(drawable);
    }

    public void addAll(Collection<Drawable> drawables) {
        drawablesToRemove.addAll(drawables);
    }

    public List<Drawable> getDrawables() {
        return drawablesToRemove;
    }

    public void clear() {
        drawablesToRemove.clear();
    }

}