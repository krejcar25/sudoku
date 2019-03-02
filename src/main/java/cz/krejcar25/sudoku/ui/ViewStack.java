package cz.krejcar25.sudoku.ui;

import java.util.ArrayList;

public class ViewStack
{
	private final ArrayList<BaseView> stack;

	public ViewStack(BaseView base)
	{
		stack = new ArrayList<>();
		stack.add(base);
		base.setViewStack(this);
	}

	public void push(BaseView view)
	{
		int index = stack.size();
		if (view.isInViewStack()) return;
		stack.add(view);
		view.setViewStack(this);
	}

	public void pop(int count)
	{
		for (int i = 0; i < count; i++) pop();
	}

	private void pop()
	{
		int index = stack.size() - 1;
		if (index == 0) return;
		BaseView view = stack.get(index);
		view.removeFromViewStack();
	}

	public BaseView get()
	{
		return stack.get(stack.size() - 1);
	}

	void removeSpecific(BaseView view)
	{
		stack.remove(view);
	}
}
