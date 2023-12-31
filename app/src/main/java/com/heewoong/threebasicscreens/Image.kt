package com.heewoong.threebasicscreens

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