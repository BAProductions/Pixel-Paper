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
package org.djabhiphop.pixelPaper.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.djabhiphop.pixelPaper.R;
import org.djabhiphop.pixelPaper.bundle.WallpaperBundle;
import org.djabhiphop.pixelPaper.bundle.WallpaperType;
import org.djabhiphop.pixelPaper.holders.UserHolder;
import org.djabhiphop.pixelPaper.holders.WallpaperHolder;
import org.djabhiphop.pixelPaper.ui.SelectionInterface;
import org.djabhiphop.pixelPaper.util.TypeConverter;

import java.util.ArrayList;
import java.util.List;

public final class WallsAdapter extends RecyclerView.Adapter<WallpaperHolder> {
    private final SelectionInterface mCallback;
    private List<WallpaperBundle> mData = new ArrayList<>();

    public WallsAdapter(@NonNull final SelectionInterface callback) {
        mCallback = callback;
    }

    @NonNull
    @Override
    public WallpaperHolder onCreateViewHolder(@NonNull final ViewGroup parent,
                                              final int viewType) {
        final WallpaperType type = TypeConverter.intToWallpaperType(viewType);

        switch (type) {
            case BUILT_IN:
            case DEFAULT:
            case GRADIENT:
            case MONO:
                return buildDefaultHolder(parent);
            default:
                return buildUserHolder(parent);
        }
    }

    @Override
    public void onBindViewHolder(final @NonNull WallpaperHolder holder, final int position) {
        final WallpaperBundle bundle = mData.get(position);
        holder.bind(bundle);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return TypeConverter.wallpaperTypeToInt(mData.get(position).getType());
    }

    public void setData(final List<WallpaperBundle> data) {
        final WallsDiffCallback callback = new WallsDiffCallback(mData, data);
        DiffUtil.calculateDiff(callback).dispatchUpdatesTo(this);
        mData = data;
    }

    // Reified generics when java?

    @NonNull
    private WallpaperHolder buildDefaultHolder(@NonNull final ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new WallpaperHolder(inflater.inflate(R.layout.item_wallpaper,
                parent, false), mCallback);
    }

    @NonNull
    private UserHolder buildUserHolder(@NonNull final ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new UserHolder(inflater.inflate(R.layout.item_wallpaper,
                parent, false), mCallback);
    }
}
