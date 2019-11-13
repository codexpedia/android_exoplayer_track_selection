package com.example.andrioidexoplayertrackselection.track_selection_dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.andrioidexoplayertrackselection.R;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.ui.TrackSelectionView;

import java.util.Collections;
import java.util.List;

/** Fragment to show a track selection in tab of the track selection dialog. */
public final class TrackSelectionViewFragment extends Fragment implements TrackSelectionView.TrackSelectionListener {

  private MappingTrackSelector.MappedTrackInfo mappedTrackInfo;
  private int rendererIndex;
  private boolean allowAdaptiveSelections;
  private boolean allowMultipleOverrides;

  /* package */ boolean isDisabled;
  /* package */ List<DefaultTrackSelector.SelectionOverride> overrides;

  public TrackSelectionViewFragment() {
    // Retain instance across activity re-creation to prevent losing access to init data.
    setRetainInstance(true);
  }

  public void init(
      MappingTrackSelector.MappedTrackInfo mappedTrackInfo,
      int rendererIndex,
      boolean initialIsDisabled,
      @Nullable DefaultTrackSelector.SelectionOverride initialOverride,
      boolean allowAdaptiveSelections,
      boolean allowMultipleOverrides) {
    this.mappedTrackInfo = mappedTrackInfo;
    this.rendererIndex = rendererIndex;
    this.isDisabled = initialIsDisabled;
    this.overrides = initialOverride == null ? Collections.emptyList() : Collections.singletonList(initialOverride);
    this.allowAdaptiveSelections = allowAdaptiveSelections;
    this.allowMultipleOverrides = allowMultipleOverrides;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.exo_track_selection_dialog, container, /* attachToRoot= */ false);
    TrackSelectionView trackSelectionView = rootView.findViewById(R.id.exo_track_selection_view);
    trackSelectionView.setShowDisableOption(true);
    trackSelectionView.setAllowMultipleOverrides(allowMultipleOverrides);
    trackSelectionView.setAllowAdaptiveSelections(allowAdaptiveSelections);
    trackSelectionView.init(mappedTrackInfo, rendererIndex, isDisabled, overrides, /* listener= */ this);
    return rootView;
  }

  @Override
  public void onTrackSelectionChanged(boolean isDisabled, List<DefaultTrackSelector.SelectionOverride> overrides) {
    this.isDisabled = isDisabled;
    this.overrides = overrides;
  }
}