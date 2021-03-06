package com.google.android.gms.maps;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.RemoteException;
import android.support.annotation.RequiresPermission;
import android.view.View;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.maps.LocationSource.OnLocationChangedListener;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.zzk;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.internal.IPolylineDelegate;
import com.google.android.gms.maps.model.internal.zzc;
import com.google.android.gms.maps.model.internal.zzd;
import com.google.android.gms.maps.model.internal.zzf;
import com.google.android.gms.maps.model.internal.zzg;
import com.google.android.gms.maps.model.internal.zzh;

public final class GoogleMap {
    public static final int MAP_TYPE_HYBRID = 4;
    public static final int MAP_TYPE_NONE = 0;
    public static final int MAP_TYPE_NORMAL = 1;
    public static final int MAP_TYPE_SATELLITE = 2;
    public static final int MAP_TYPE_TERRAIN = 3;
    private final IGoogleMapDelegate zzaRr;
    private UiSettings zzaRs;

    public interface CancelableCallback {
        void onCancel();

        void onFinish();
    }

    public interface InfoWindowAdapter {
        View getInfoContents(Marker marker);

        View getInfoWindow(Marker marker);
    }

    public interface OnCameraChangeListener {
        void onCameraChange(CameraPosition cameraPosition);
    }

    public interface OnGroundOverlayClickListener {
        void onGroundOverlayClick(GroundOverlay groundOverlay);
    }

    public interface OnIndoorStateChangeListener {
        void onIndoorBuildingFocused();

        void onIndoorLevelActivated(IndoorBuilding indoorBuilding);
    }

    public interface OnInfoWindowClickListener {
        void onInfoWindowClick(Marker marker);
    }

    public interface OnInfoWindowCloseListener {
        void onInfoWindowClose(Marker marker);
    }

    public interface OnInfoWindowLongClickListener {
        void onInfoWindowLongClick(Marker marker);
    }

    public interface OnMapClickListener {
        void onMapClick(LatLng latLng);
    }

    public interface OnMapLoadedCallback {
        void onMapLoaded();
    }

    public interface OnMapLongClickListener {
        void onMapLongClick(LatLng latLng);
    }

    public interface OnMarkerClickListener {
        boolean onMarkerClick(Marker marker);
    }

    public interface OnMarkerDragListener {
        void onMarkerDrag(Marker marker);

        void onMarkerDragEnd(Marker marker);

        void onMarkerDragStart(Marker marker);
    }

    public interface OnMyLocationButtonClickListener {
        boolean onMyLocationButtonClick();
    }

    @Deprecated
    public interface OnMyLocationChangeListener {
        void onMyLocationChange(Location location);
    }

    public interface OnPolygonClickListener {
        void onPolygonClick(Polygon polygon);
    }

    public interface OnPolylineClickListener {
        void onPolylineClick(Polyline polyline);
    }

    public interface SnapshotReadyCallback {
        void onSnapshotReady(Bitmap bitmap);
    }

    private static final class zza extends com.google.android.gms.maps.internal.zzb.zza {
        private final CancelableCallback zzaRO;

        zza(CancelableCallback cancelableCallback) {
            this.zzaRO = cancelableCallback;
        }

        public void onCancel() {
            this.zzaRO.onCancel();
        }

        public void onFinish() {
            this.zzaRO.onFinish();
        }
    }

    protected GoogleMap(IGoogleMapDelegate map) {
        this.zzaRr = (IGoogleMapDelegate) zzx.zzz(map);
    }

    public final Circle addCircle(CircleOptions options) {
        try {
            return new Circle(this.zzaRr.addCircle(options));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final GroundOverlay addGroundOverlay(GroundOverlayOptions options) {
        try {
            zzc addGroundOverlay = this.zzaRr.addGroundOverlay(options);
            return addGroundOverlay != null ? new GroundOverlay(addGroundOverlay) : null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Marker addMarker(MarkerOptions options) {
        try {
            zzf addMarker = this.zzaRr.addMarker(options);
            return addMarker != null ? new Marker(addMarker) : null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Polygon addPolygon(PolygonOptions options) {
        try {
            return new Polygon(this.zzaRr.addPolygon(options));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Polyline addPolyline(PolylineOptions options) {
        try {
            return new Polyline(this.zzaRr.addPolyline(options));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final TileOverlay addTileOverlay(TileOverlayOptions options) {
        try {
            zzh addTileOverlay = this.zzaRr.addTileOverlay(options);
            return addTileOverlay != null ? new TileOverlay(addTileOverlay) : null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(CameraUpdate update) {
        try {
            this.zzaRr.animateCamera(update.zzzH());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(CameraUpdate update, int durationMs, CancelableCallback callback) {
        try {
            this.zzaRr.animateCameraWithDurationAndCallback(update.zzzH(), durationMs, callback == null ? null : new zza(callback));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(CameraUpdate update, CancelableCallback callback) {
        try {
            this.zzaRr.animateCameraWithCallback(update.zzzH(), callback == null ? null : new zza(callback));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void clear() {
        try {
            this.zzaRr.clear();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final CameraPosition getCameraPosition() {
        try {
            return this.zzaRr.getCameraPosition();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public IndoorBuilding getFocusedBuilding() {
        try {
            zzd focusedBuilding = this.zzaRr.getFocusedBuilding();
            return focusedBuilding != null ? new IndoorBuilding(focusedBuilding) : null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final int getMapType() {
        try {
            return this.zzaRr.getMapType();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final float getMaxZoomLevel() {
        try {
            return this.zzaRr.getMaxZoomLevel();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final float getMinZoomLevel() {
        try {
            return this.zzaRr.getMinZoomLevel();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @Deprecated
    public final Location getMyLocation() {
        try {
            return this.zzaRr.getMyLocation();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Projection getProjection() {
        try {
            return new Projection(this.zzaRr.getProjection());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final UiSettings getUiSettings() {
        try {
            if (this.zzaRs == null) {
                this.zzaRs = new UiSettings(this.zzaRr.getUiSettings());
            }
            return this.zzaRs;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isBuildingsEnabled() {
        try {
            return this.zzaRr.isBuildingsEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isIndoorEnabled() {
        try {
            return this.zzaRr.isIndoorEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isMyLocationEnabled() {
        try {
            return this.zzaRr.isMyLocationEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isTrafficEnabled() {
        try {
            return this.zzaRr.isTrafficEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void moveCamera(CameraUpdate update) {
        try {
            this.zzaRr.moveCamera(update.zzzH());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setBuildingsEnabled(boolean enabled) {
        try {
            this.zzaRr.setBuildingsEnabled(enabled);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setContentDescription(String description) {
        try {
            this.zzaRr.setContentDescription(description);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean setIndoorEnabled(boolean enabled) {
        try {
            return this.zzaRr.setIndoorEnabled(enabled);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setInfoWindowAdapter(final InfoWindowAdapter adapter) {
        if (adapter == null) {
            try {
                this.zzaRr.setInfoWindowAdapter(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaRr.setInfoWindowAdapter(new com.google.android.gms.maps.internal.zzd.zza(this) {
            final /* synthetic */ GoogleMap zzaRu;

            public com.google.android.gms.dynamic.zzd zzb(zzf com_google_android_gms_maps_model_internal_zzf) {
                return zze.zzC(adapter.getInfoWindow(new Marker(com_google_android_gms_maps_model_internal_zzf)));
            }

            public com.google.android.gms.dynamic.zzd zzc(zzf com_google_android_gms_maps_model_internal_zzf) {
                return zze.zzC(adapter.getInfoContents(new Marker(com_google_android_gms_maps_model_internal_zzf)));
            }
        });
    }

    public final void setLocationSource(final LocationSource source) {
        if (source == null) {
            try {
                this.zzaRr.setLocationSource(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaRr.setLocationSource(new com.google.android.gms.maps.internal.ILocationSourceDelegate.zza(this) {
            final /* synthetic */ GoogleMap zzaRu;

            public void activate(final zzk listener) {
                source.activate(new OnLocationChangedListener(this) {
                    final /* synthetic */ AnonymousClass11 zzaRG;

                    public void onLocationChanged(Location location) {
                        try {
                            listener.zzd(location);
                        } catch (RemoteException e) {
                            throw new RuntimeRemoteException(e);
                        }
                    }
                });
            }

            public void deactivate() {
                source.deactivate();
            }
        });
    }

    public final void setMapType(int type) {
        try {
            this.zzaRr.setMapType(type);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public final void setMyLocationEnabled(boolean enabled) {
        try {
            this.zzaRr.setMyLocationEnabled(enabled);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnCameraChangeListener(final OnCameraChangeListener listener) {
        if (listener == null) {
            try {
                this.zzaRr.setOnCameraChangeListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaRr.setOnCameraChangeListener(new com.google.android.gms.maps.internal.zze.zza(this) {
            final /* synthetic */ GoogleMap zzaRu;

            public void onCameraChange(CameraPosition position) {
                listener.onCameraChange(position);
            }
        });
    }

    public final void setOnGroundOverlayClickListener(final OnGroundOverlayClickListener listener) {
        if (listener == null) {
            try {
                this.zzaRr.setOnGroundOverlayClickListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaRr.setOnGroundOverlayClickListener(new com.google.android.gms.maps.internal.zzf.zza(this) {
            final /* synthetic */ GoogleMap zzaRu;

            public void zza(zzc com_google_android_gms_maps_model_internal_zzc) {
                listener.onGroundOverlayClick(new GroundOverlay(com_google_android_gms_maps_model_internal_zzc));
            }
        });
    }

    public final void setOnIndoorStateChangeListener(final OnIndoorStateChangeListener listener) {
        if (listener == null) {
            try {
                this.zzaRr.setOnIndoorStateChangeListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaRr.setOnIndoorStateChangeListener(new com.google.android.gms.maps.internal.zzg.zza(this) {
            final /* synthetic */ GoogleMap zzaRu;

            public void onIndoorBuildingFocused() {
                listener.onIndoorBuildingFocused();
            }

            public void zza(zzd com_google_android_gms_maps_model_internal_zzd) {
                listener.onIndoorLevelActivated(new IndoorBuilding(com_google_android_gms_maps_model_internal_zzd));
            }
        });
    }

    public final void setOnInfoWindowClickListener(final OnInfoWindowClickListener listener) {
        if (listener == null) {
            try {
                this.zzaRr.setOnInfoWindowClickListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaRr.setOnInfoWindowClickListener(new com.google.android.gms.maps.internal.zzh.zza(this) {
            final /* synthetic */ GoogleMap zzaRu;

            public void zzh(zzf com_google_android_gms_maps_model_internal_zzf) {
                listener.onInfoWindowClick(new Marker(com_google_android_gms_maps_model_internal_zzf));
            }
        });
    }

    public final void setOnInfoWindowCloseListener(final OnInfoWindowCloseListener listener) {
        if (listener == null) {
            try {
                this.zzaRr.setOnInfoWindowCloseListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaRr.setOnInfoWindowCloseListener(new com.google.android.gms.maps.internal.zzi.zza(this) {
            final /* synthetic */ GoogleMap zzaRu;

            public void zza(zzf com_google_android_gms_maps_model_internal_zzf) {
                listener.onInfoWindowClose(new Marker(com_google_android_gms_maps_model_internal_zzf));
            }
        });
    }

    public final void setOnInfoWindowLongClickListener(final OnInfoWindowLongClickListener listener) {
        if (listener == null) {
            try {
                this.zzaRr.setOnInfoWindowLongClickListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaRr.setOnInfoWindowLongClickListener(new com.google.android.gms.maps.internal.zzj.zza(this) {
            final /* synthetic */ GoogleMap zzaRu;

            public void zzi(zzf com_google_android_gms_maps_model_internal_zzf) {
                listener.onInfoWindowLongClick(new Marker(com_google_android_gms_maps_model_internal_zzf));
            }
        });
    }

    public final void setOnMapClickListener(final OnMapClickListener listener) {
        if (listener == null) {
            try {
                this.zzaRr.setOnMapClickListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaRr.setOnMapClickListener(new com.google.android.gms.maps.internal.zzl.zza(this) {
            final /* synthetic */ GoogleMap zzaRu;

            public void onMapClick(LatLng point) {
                listener.onMapClick(point);
            }
        });
    }

    public void setOnMapLoadedCallback(final OnMapLoadedCallback callback) {
        if (callback == null) {
            try {
                this.zzaRr.setOnMapLoadedCallback(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaRr.setOnMapLoadedCallback(new com.google.android.gms.maps.internal.zzm.zza(this) {
            final /* synthetic */ GoogleMap zzaRu;

            public void onMapLoaded() throws RemoteException {
                callback.onMapLoaded();
            }
        });
    }

    public final void setOnMapLongClickListener(final OnMapLongClickListener listener) {
        if (listener == null) {
            try {
                this.zzaRr.setOnMapLongClickListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaRr.setOnMapLongClickListener(new com.google.android.gms.maps.internal.zzn.zza(this) {
            final /* synthetic */ GoogleMap zzaRu;

            public void onMapLongClick(LatLng point) {
                listener.onMapLongClick(point);
            }
        });
    }

    public final void setOnMarkerClickListener(final OnMarkerClickListener listener) {
        if (listener == null) {
            try {
                this.zzaRr.setOnMarkerClickListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaRr.setOnMarkerClickListener(new com.google.android.gms.maps.internal.zzp.zza(this) {
            final /* synthetic */ GoogleMap zzaRu;

            public boolean zzd(zzf com_google_android_gms_maps_model_internal_zzf) {
                return listener.onMarkerClick(new Marker(com_google_android_gms_maps_model_internal_zzf));
            }
        });
    }

    public final void setOnMarkerDragListener(final OnMarkerDragListener listener) {
        if (listener == null) {
            try {
                this.zzaRr.setOnMarkerDragListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaRr.setOnMarkerDragListener(new com.google.android.gms.maps.internal.zzq.zza(this) {
            final /* synthetic */ GoogleMap zzaRu;

            public void zze(zzf com_google_android_gms_maps_model_internal_zzf) {
                listener.onMarkerDragStart(new Marker(com_google_android_gms_maps_model_internal_zzf));
            }

            public void zzf(zzf com_google_android_gms_maps_model_internal_zzf) {
                listener.onMarkerDragEnd(new Marker(com_google_android_gms_maps_model_internal_zzf));
            }

            public void zzg(zzf com_google_android_gms_maps_model_internal_zzf) {
                listener.onMarkerDrag(new Marker(com_google_android_gms_maps_model_internal_zzf));
            }
        });
    }

    public final void setOnMyLocationButtonClickListener(final OnMyLocationButtonClickListener listener) {
        if (listener == null) {
            try {
                this.zzaRr.setOnMyLocationButtonClickListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaRr.setOnMyLocationButtonClickListener(new com.google.android.gms.maps.internal.zzr.zza(this) {
            final /* synthetic */ GoogleMap zzaRu;

            public boolean onMyLocationButtonClick() throws RemoteException {
                return listener.onMyLocationButtonClick();
            }
        });
    }

    @Deprecated
    public final void setOnMyLocationChangeListener(final OnMyLocationChangeListener listener) {
        if (listener == null) {
            try {
                this.zzaRr.setOnMyLocationChangeListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaRr.setOnMyLocationChangeListener(new com.google.android.gms.maps.internal.zzs.zza(this) {
            final /* synthetic */ GoogleMap zzaRu;

            public void zzq(com.google.android.gms.dynamic.zzd com_google_android_gms_dynamic_zzd) {
                listener.onMyLocationChange((Location) zze.zzp(com_google_android_gms_dynamic_zzd));
            }
        });
    }

    public final void setOnPolygonClickListener(final OnPolygonClickListener listener) {
        if (listener == null) {
            try {
                this.zzaRr.setOnPolygonClickListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaRr.setOnPolygonClickListener(new com.google.android.gms.maps.internal.zzu.zza(this) {
            final /* synthetic */ GoogleMap zzaRu;

            public void zza(zzg com_google_android_gms_maps_model_internal_zzg) {
                listener.onPolygonClick(new Polygon(com_google_android_gms_maps_model_internal_zzg));
            }
        });
    }

    public final void setOnPolylineClickListener(final OnPolylineClickListener listener) {
        if (listener == null) {
            try {
                this.zzaRr.setOnPolylineClickListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.zzaRr.setOnPolylineClickListener(new com.google.android.gms.maps.internal.zzv.zza(this) {
            final /* synthetic */ GoogleMap zzaRu;

            public void zza(IPolylineDelegate iPolylineDelegate) {
                listener.onPolylineClick(new Polyline(iPolylineDelegate));
            }
        });
    }

    public final void setPadding(int left, int top, int right, int bottom) {
        try {
            this.zzaRr.setPadding(left, top, right, bottom);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setTrafficEnabled(boolean enabled) {
        try {
            this.zzaRr.setTrafficEnabled(enabled);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void snapshot(SnapshotReadyCallback callback) {
        snapshot(callback, null);
    }

    public final void snapshot(final SnapshotReadyCallback callback, Bitmap bitmap) {
        try {
            this.zzaRr.snapshot(new com.google.android.gms.maps.internal.zzab.zza(this) {
                final /* synthetic */ GoogleMap zzaRu;

                public void onSnapshotReady(Bitmap snapshot) throws RemoteException {
                    callback.onSnapshotReady(snapshot);
                }

                public void zzr(com.google.android.gms.dynamic.zzd com_google_android_gms_dynamic_zzd) throws RemoteException {
                    callback.onSnapshotReady((Bitmap) zze.zzp(com_google_android_gms_dynamic_zzd));
                }
            }, (zze) (bitmap != null ? zze.zzC(bitmap) : null));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void stopAnimation() {
        try {
            this.zzaRr.stopAnimation();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    IGoogleMapDelegate zzzJ() {
        return this.zzaRr;
    }
}
