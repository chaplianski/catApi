package com.example.thecatapi

import android.graphics.Camera
import android.graphics.Matrix
import android.service.autofill.Transformation


/*
class Rotate3dAnimation(
    fromDegree: Float,
    toDegree: Float,
    centerX: Float,
    centerY: Float,
    DEPTH: Float,
    reverse: Boolean
) {
    private var mFromDegrees = 0f
    private var mToDegrees = 0f
    private var mCenterX = 0f
    private var mCenterY = 0f
    private var mDepthZ = 0f
    private var mReverse = false
    private var mCamera: Camera? = null

    /**
     * Creates a new 3D rotation on the Y axis. The rotation is defined by its
     * start angle and its end angle. Both angles are in degrees. The rotation
     * is performed around a center point on the 2D space, definied by a pair
     * of X and Y coordinates, called centerX and centerY. When the animation
     * starts, a translation on the Z axis (depth) is performed. The length
     * of the translation can be specified, as well as whether the translation
     * should be reversed in time.
     *
     * @param fromDegrees the start angle of the 3D rotation
     * @param toDegrees the end angle of the 3D rotation
     * @param centerX the X center of the 3D rotation
     * @param centerY the Y center of the 3D rotation
     * @param reverse true if the translation should be reversed, false otherwise
     */
    fun Rotate3dAnimation(
        fromDegrees: Float, toDegrees: Float,
        centerX: Float, centerY: Float, depthZ: Float, reverse: Boolean
    ) {
        mFromDegrees = fromDegrees
        mToDegrees = toDegrees
        mCenterX = centerX
        mCenterY = centerY
        mDepthZ = depthZ
        mReverse = reverse
    }

    fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
        super.initialize(width, height, parentWidth, parentHeight)
        mCamera = Camera()
    }

    protected fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        val fromDegrees = mFromDegrees
        val degrees = fromDegrees + (mToDegrees - fromDegrees) * interpolatedTime
        val centerX = mCenterX
        val centerY = mCenterY
        val camera: Camera? = mCamera
        val matrix: Matrix = t.getMatrix()
        if (camera != null) {
            camera.save()
        }
        if (mReverse) {
            if (camera != null) {
                camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime)
            }
        } else {
            if (camera != null) {
                camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime))
            }
        }
        if (camera != null) {
            camera.rotateY(degrees)
            camera.getMatrix(matrix)
            camera.restore()
        }

        matrix.preTranslate(-centerX, -centerY)
        matrix.postTranslate(centerX, centerY)
    }


}
*/
