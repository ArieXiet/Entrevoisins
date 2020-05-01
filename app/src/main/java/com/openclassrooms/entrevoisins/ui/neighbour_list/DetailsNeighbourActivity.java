package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsNeighbourActivity extends AppCompatActivity {

	private static final String BUNDLE_EXTRA_FAVORITE = "BUNDLE_EXTRA_FAVORITE";
	private static final String BUNDLE_EXTRA_NEIGHBOUR = "BUNDLE_EXTRA_NEIGHBOUR";
	@BindView(R.id.imageView)
	ImageView mImageView;
	@BindView(R.id.firstnameBig)
	TextView mFirstnameBig;
	@BindView(R.id.firstname)
	TextView mFirstname;
	@BindView(R.id.addressImage)
	ImageView mAddressImage;
	@BindView(R.id.addressText)
	TextView mAddressText;
	@BindView(R.id.phoneImage)
	ImageView mPhoneImage;
	@BindView(R.id.phoneText)
	TextView mPhoneText;
	@BindView(R.id.webImage)
	ImageView mWebImage;
	@BindView(R.id.webText)
	TextView mWebText;
	@BindView(R.id.aboutlong)
	TextView mAboutLong;
	@BindView(R.id.fab1)
	FloatingActionButton mFab;

	private Intent mIntent;
	private boolean mFavorite = false;
	private Neighbour mNeighbour;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details_neighbour);
		ButterKnife.bind(this);


		mIntent = getIntent();
		mNeighbour = (Neighbour) mIntent.getSerializableExtra("neighbour");
		mFavorite = mNeighbour.isFavorite();

		init();

	}




	@SuppressLint("SetTextI18n")
	private void init() {
		if (mNeighbour.isFavorite()) {
			Glide.with(this).load(R.drawable.ic_star_black_24dp).into(mFab);
		}else {
			Glide.with(this).load(R.drawable.ic_star_grey).into(mFab);
		}
		Glide.with(this).load(mNeighbour.getAvatarUrl()).into(mImageView);
		mFirstname.setText(mNeighbour.getName());
		mFirstnameBig.setText(mNeighbour.getName());
		mAddressText.setText(mNeighbour.getAddress());
		mPhoneText.setText(mNeighbour.getPhoneNumber());
		mAboutLong.setText(mNeighbour.getAboutMe());
		String mNameLower = mNeighbour.getName().toLowerCase();
		mWebText.setText("www.facebook.fr/" + mNameLower);
		Glide.with(this).load(R.drawable.ic_location).into(mAddressImage);
		Glide.with(this).load(R.drawable.ic_phone).into(mPhoneImage);
		Glide.with(this).load(R.drawable.ic_web).into(mWebImage);

	}

	@OnClick(R.id.fab1)
	void favorite () {
		if (!mNeighbour.isFavorite()) {
			Glide.with(this).load(R.drawable.ic_star_black_24dp).into(mFab);
			mFavorite = true;
		} else {
			Glide.with(this).load(R.drawable.ic_star_grey).into(mFab);
			mFavorite = false;
		}
	}

	@OnClick(R.id.imageBack)
	void backActivity () {
		mIntent.putExtra(BUNDLE_EXTRA_FAVORITE, mFavorite);
		mIntent.putExtra(BUNDLE_EXTRA_NEIGHBOUR, mNeighbour);
		setResult(RESULT_OK, mIntent);
		finish();
	}
}