package cz.krejcar25.sudoku;

import java.util.ArrayList;

public class ViewStack {
    private ArrayList<BaseView> stack;

    public ViewStack(BaseView base) {
        stack = new ArrayList<>();
        stack.add(base);
    }

    public int push(BaseView view) {
        int index = stack.size();
        stack.add(view);
        return index;
    }

    public BaseView pop() {
        int index = stack.size() - 1;
        if (index == 0) return null;
        BaseView view = stack.get(index);
        stack.remove(index);
        return view;
    }

    public BaseView get() {
        return stack.get(stack.size() - 1);
    }
}
