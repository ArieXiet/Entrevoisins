package com.openclassrooms.entrevoisins.utils;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;


import org.hamcrest.Matcher;

public class GetNameAction implements ViewAction {
	@Override
	public Matcher<View> getConstraints() {
		return null;
	}

	@Override
	public String getDescription() {
		return "Get name of neighbour";
	}

	@Override
	public void perform(UiController uiController, View view) {
		if (view != null)
			view.performClick();
	}
}