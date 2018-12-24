package cz.krejcar25.sudoku;

import java.util.ArrayList;

class ViewStack {
    private ArrayList<BaseView> stack;

    ViewStack(BaseView base) {
        stack = new ArrayList<>();
        stack.add(base);
        base.setViewStack(this);
    }

    int push(BaseView view) {
        int index = stack.size();
        if (view.isInViewStack()) return -1;
        stack.add(view);
        view.setViewStack(this);
        return index;
    }

    void pop(int count) {
        for (int i = 0; i < count; i++) pop();
    }

    BaseView pop() {
        int index = stack.size() - 1;
        if (index == 0) return null;
        BaseView view = stack.get(index);
        stack.remove(index);
        return view;
    }

    BaseView get() {
        return stack.get(stack.size() - 1);
    }

    void removeSpecific(BaseView view) {
        stack.remove(view);
    }
}
