/*
 * Copyright (C) 2019 The djabhiphop Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.djabhiphop.pixelPaper.task;

import android.app.WallpaperManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;

import androidx.annotation.NonNull;

import org.djabhiphop.pixelPaper.R;
import org.djabhiphop.pixelPaper.bundle.WallpaperBundle;
import org.djabhiphop.pixelPaper.factory.GradientWallpaperFactory;
import org.djabhiphop.pixelPaper.factory.MonoWallpaperFactory;

import java.util.ArrayList;
import java.util.List;

final class FetchDataImpl {
    private final List<WallpaperBundle> mData = new ArrayList<>();
    private final Callback mCallbacks;

    FetchDataImpl(@NonNull final Callback callbacks) {
        mCallbacks = callbacks;
    }

    @NonNull
    List<WallpaperBundle> fetchData() {
        addColors();
        addGradients();

        return mData;
    }

    private void addColors() {
        Resources res = mCallbacks.getResources();
        String[] names = res.getStringArray(R.array.wallpaper_mono_names);
        TypedArray colors = res.obtainTypedArray(R.array.wallpaper_mono_colors);
        for (int i = 0; i < colors.length(); i++) {
            final int color = colors.getColor(i, Color.BLACK);
            mData.add(MonoWallpaperFactory.build(names[i], color));
        }

        colors.recycle();
    }

    private void addGradients() {
        Resources res = mCallbacks.getResources();
        String[] names = res.getStringArray(R.array.wallpaper_gradient_names);
        TypedArray gradients = res.obtainTypedArray(R.array.wallpaper_gradient_drawables);
        for (int i = 0; i < gradients.length(); i++) {
            final int resourceId = gradients.getResourceId(i, 0);
            if (resourceId != 0) {
                mData.add(GradientWallpaperFactory.build(names[i], res, resourceId));
            }
        }

        gradients.recycle();
    }

    public interface Callback {
        @NonNull
        Resources getResources();

        @NonNull
        WallpaperManager getWallpaperManager();
    }
}
