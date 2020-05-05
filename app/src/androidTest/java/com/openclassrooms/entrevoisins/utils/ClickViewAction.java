package com.openclassrooms.entrevoisins.utils;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import org.hamcrest.Matcher;

public class ClickViewAction implements ViewAction {
	@Override
	public Matcher<View> getConstraints() {
		return null;
	}

	@Override
	public String getDescription() {
		return "Click on neighbour";
	}

	@Override
	public void perform(UiController uiController, View view) {
		if (view != null)
			view.performClick();
	}
}
