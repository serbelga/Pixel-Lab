package com.example.sergiobelda.photoeditor.ui;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.*;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.example.sergiobelda.photoeditor.R;
import com.example.sergiobelda.photoeditor.editableimageview.EditableImageView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import static com.example.sergiobelda.photoeditor.editableimageview.EditorTool.FIGURE;
import static com.example.sergiobelda.photoeditor.editableimageview.EditorTool.PAINT;
import static com.example.sergiobelda.photoeditor.editableimageview.EditorTool.STICKER;

/**
 * Editor Fragment.
 */
public class EditorFragment extends Fragment {
    private final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 99;

    private EditableImageView editableImageView;
    private BottomNavigationView bottomNavigationView;

    private View toolsBottomSheet;
    private BottomSheetBehavior toolsBottomSheetBehavior;
    private ViewPager toolsViewPager;
    private TabLayout toolsTabLayout;
    public TabItem tabPaint, tabFigure, tabSticker;

    int currentColor;

    public EditorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editableImageView = view.findViewById(R.id.myImageView);
        toolsBottomSheet = view.findViewById(R.id.toolsBottomSheet);
        bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        toolsTabLayout = view.findViewById(R.id.toolsTabLayout);
        toolsViewPager = view.findViewById(R.id.toolsViewPager);
        tabFigure = view.findViewById(R.id.tabFigure);
        tabPaint = view.findViewById(R.id.tabPaint);
        tabSticker = view.findViewById(R.id.tabSticker);
        setImage();
        initializeTabLayout();
        initializeViewPager();
        initializeBottomSheetBehavior();
        initializeBottomNavigationView();
    }

    //TODO exception
    private void setImage() {
        Uri uri = Uri.parse(getArguments().getString("image"));
        Glide.with(this).load(uri).into(editableImageView);
    }

    private void initializeTabLayout() {
        toolsTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(toolsViewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                toolsViewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == PAINT) {
                    editableImageView.setEditMode(PAINT);
                } else if (tab.getPosition() == FIGURE) {
                    editableImageView.setEditMode(FIGURE);
                } else if (tab.getPosition() == STICKER) {
                    editableImageView.setEditMode(STICKER);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
            }
        });
    }

    private void initializeViewPager() {
        final ToolsViewPagerAdapter pagerAdapter = new ToolsViewPagerAdapter(getFragmentManager(), toolsTabLayout.getTabCount());
        toolsViewPager.setAdapter(pagerAdapter);
        toolsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(toolsTabLayout));
    }

    private void initializeBottomSheetBehavior() {
        toolsBottomSheetBehavior = BottomSheetBehavior.from(toolsBottomSheet);
        toolsBottomSheetBehavior.setBottomSheetCallback(createBottomSheetCallback());
        toolsBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    private BottomSheetBehavior.BottomSheetCallback createBottomSheetCallback() {
        // Set up BottomSheetCallback
        return new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {}
        };
    }

    private void initializeBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tools:
                        toolsBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                    case R.id.export:
                        checkWriteExternalStoragePermission();

                        //TODO animation

                        break;
                }
                return true;
            }
        });
    }

    private void checkWriteExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            saveImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE :
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    saveImage();
                }
                break;
        }
    }

    private void saveImage(){
        editableImageView.setDrawingCacheEnabled(true);
        editableImageView.buildDrawingCache(true);
        Bitmap bitmap = editableImageView.getDrawingCache();
        File root = Environment.getExternalStorageDirectory();
        Random rnd = new Random();
        int i = rnd.nextInt(1000);
        File cachePath = new File(root.getAbsolutePath() + "/DCIM/Camera/image_" + String.valueOf(i) + ".jpg");
        try {
            cachePath.createNewFile();
            FileOutputStream ostream = new FileOutputStream(cachePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
            ostream.flush();
            ostream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        editableImageView.setDrawingCacheEnabled(false);
    }

    private class ToolsViewPagerAdapter extends FragmentPagerAdapter {
        private int tabsNum;

        ToolsViewPagerAdapter(@NonNull FragmentManager fm, int tabsNum) {
            super(fm);
            this.tabsNum = tabsNum;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == PAINT) {
                fragment = new TabPaint();
                ((TabPaint) fragment).setTabPaintListener(new TabPaint.TabPaintListener() {
                    @Override
                    public void onColorSelected(int currentColor) {
                        //editableImageView.setImageDrawable(new ColorDrawable(currentColor));
                    }
                });
            } else if (position == FIGURE) {
                fragment = new TabFigure();
                ((TabFigure) fragment).setTabFigureListener(new TabFigure.TabFigureListener() {
                    @Override
                    public void onFigureSelected(int currentFigure) {
                        editableImageView.setFigureMode(currentFigure);
                    }
                });
            } else if (position == STICKER) {
                fragment = new TabSticker();
                ((TabSticker) fragment).setTabStickerListener(new TabSticker.TabStickerListener() {
                    @Override
                    public void onStickerSelected() {
                        //TODO
                    }
                });
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return tabsNum;
        }
    }
}
