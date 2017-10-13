/*
 * Software Name : OCast SDK
 *
 *  Copyright (C) 2017 Orange
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.ocast.mediaroute;

import android.os.Parcel;
import android.os.Parcelable;

import org.ocast.discovery.DialDevice;

import java.net.MalformedURLException;
import java.net.URL;

public class MediaRouteDevice implements Parcelable {
    public static final String EXTRA_DEVICE = "org.ocast.mediaroutedevice.extra.DEVICE";
    private final String mFriendlyName;
    private final String mManufacturer;
    private final String mModelName;
    private final String mUuid;
    private final URL mDialApplURL;

    public MediaRouteDevice(String uuid, String friendlyName, String manufacturer, String modelName, URL urlBase) {
        mUuid = uuid;
        mFriendlyName = friendlyName;
        mManufacturer = manufacturer;
        mModelName = modelName;
        mDialApplURL = urlBase;
    }

    public MediaRouteDevice(DialDevice dd) {
        mUuid = dd.getUuid();
        mFriendlyName = dd.getFriendlyName();
        mManufacturer = dd.getManufacturer();
        mModelName = dd.getModelName();
        mDialApplURL = dd.getDialURL();
    }

    protected MediaRouteDevice(Parcel in) {
        mFriendlyName = in.readString();
        mManufacturer = in.readString();
        mModelName = in.readString();
        mUuid = in.readString();
        try {
            mDialApplURL = new URL(in.readString());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static final Creator<MediaRouteDevice> CREATOR = new Creator<MediaRouteDevice>() {
        @Override
        public MediaRouteDevice createFromParcel(Parcel in) {
            return new MediaRouteDevice(in);
        }

        @Override
        public MediaRouteDevice[] newArray(int size) {
            return new MediaRouteDevice[size];
        }
    };

    /**
     * Retrieve the device friendly name found in <device> tag
     *
     * @return friendly name
     */
    public String getFriendlyName() {
        return mFriendlyName;
    }

    /**
     * Retrieve the manufacturer found in found in <device> tag
     *
     * @return
     */
    public String getManufacturer() {
        return mManufacturer;
    }

    /**
     * Retrieve the modelName found in <device> tag
     *
     * @return
     */
    public String getModelName() {
        return mModelName;
    }

    /**
     * Retrieve the UUID found in <device> tag
     *
     * @return the uuid value without uuid: prefix
     */
    public String getUuid() {
        return mUuid;
    }

    /**
     * Retrieve the Dial application URL found in <device> tag URLBase or the one provided
     * to fromDeviceDescription if it comes from a header.
     *
     * @return
     */
    public String getDialApplURL() {
        return mDialApplURL.toString();
    }

    public URL getDialURL() {
        return mDialApplURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUuid);
        dest.writeString(mFriendlyName);
        dest.writeString(mManufacturer);
        dest.writeString(mModelName);
        dest.writeString(mDialApplURL.toString());
    }
}
