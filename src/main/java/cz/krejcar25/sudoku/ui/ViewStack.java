package cz.krejcar25.sudoku.ui;

import java.util.ArrayList;

public class ViewStack {
    private ArrayList<BaseView> stack;

    public ViewStack(BaseView base) {
        stack = new ArrayList<>();
        stack.add(base);
        base.setViewStack(this);
    }

    public int push(BaseView view) {
        int index = stack.size();
        if (view.isInViewStack()) return -1;
        stack.add(view);
        view.setViewStack(this);
        return index;
    }

    public void pop(int count) {
        for (int i = 0; i < count; i++) pop();
    }

    public BaseView pop() {
        int index = stack.size() - 1;
        if (index == 0) return null;
        BaseView view = stack.get(index);
        view.removeFromViewStack();
        return view;
    }

    public BaseView get() {
        return stack.get(stack.size() - 1);
    }

    public void removeSpecific(BaseView view) {
        stack.remove(view);
    }

    public boolean isbase() {
        return stack.size() == 1;
    }
}
