package com.heewoong.threebasicscreens

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class Image {
    var imageSrc: String? = null
    var imageName: String? = null

    constructor(imageSrc: String?, imageName: String?) {
        this.imageSrc = imageSrc
        this.imageName = imageName
    }

    constructor() {}
}