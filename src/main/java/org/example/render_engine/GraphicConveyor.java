package org.example.render_engine;

import org.example.math.Vector3f;
import org.example.math.matrix.Matrix4f;

import javax.vecmath.Point2f;

public class GraphicConveyor {
    public static Matrix4f scaleMatrix4f(float scaleX, float scaleY, float scaleZ) {
        float[][] matrix = new float[][]
                {
                        {scaleX, 0,0,0},
                        {0, scaleY,0,0},
                        {0,0,scaleZ,0},
                        {0,0,0,1}
                };
        return new Matrix4f(matrix);
    }

    public static Matrix4f rotateX(float angleX) {
        float cos = (float) Math.cos(Math.toRadians(angleX));
        float sin = (float) Math.sin(Math.toRadians(angleX));
        float[][] matrix = new float[][]
                {
                        {1,0,0,0},
                        {0,cos, sin,0},
                        {0,-sin, cos, 0},
                        {0,0,0,1}
                };
        return new Matrix4f(matrix);
    }

    public static Matrix4f rotateY(float angleY) {
        float cos = (float) Math.cos(Math.toRadians(angleY));
        float sin = (float) Math.sin(Math.toRadians(angleY));
        float[][] matrix = new float[][]
                {
                        {cos,0,sin,0},
                        {0,1,0,0},
                        {-sin,0,cos,0},
                        {0,0,0,1}
                };
        return new Matrix4f(matrix);
    }

    public static Matrix4f rotateZ(float angleZ) {
        float cos = (float) Math.cos(Math.toRadians(angleZ));
        float sin = (float) Math.sin(Math.toRadians(angleZ));
        float[][] matrix = new float[][]
                {
                        {cos,sin,0,0},
                        {-sin, cos,0,0},
                        {0,0,1,0},
                        {0,0,0,1}
                };
        return new Matrix4f(matrix);
    }

    public static Matrix4f rotateMatrix4f(float angleX, float angleY, float angleZ) {
        return Matrix4f.multiply(rotateZ(angleZ), Matrix4f.multiply(rotateY(angleY), rotateX(angleX)));
    }

    public static Matrix4f translationMatrix4f(float translationX, float translationY, float translationZ) {
        float[][] matrix = new float[][]
                {
                        {1,0,0,translationX},
                        {0,1,0,translationY},
                        {0,0,1,translationZ},
                        {0,0,0,1}
                };
        return new Matrix4f(matrix);
    }

    public static Matrix4f rotateScaleTranslate(
            float scaleX, float scaleY, float scaleZ,
            float angleX, float angleY, float angleZ,
            float translationX, float translationY, float translationZ
    ) {
        return Matrix4f.multiply(
                translationMatrix4f(translationX,translationY,translationZ),
                Matrix4f.multiply(rotateMatrix4f(angleX,angleY,angleZ), scaleMatrix4f(scaleX,scaleY,scaleZ)));
    }

    public static Matrix4f rotateScaleTranslate() {
        return rotateScaleTranslate(1,1,1,0,0,0,0,0,0);
    }

    public static Matrix4f lookAt(Vector3f eye, Vector3f target) {
        return lookAt(eye, target, new Vector3f(0F, 1.0F, 0F));
    }

    public static Matrix4f lookAt(Vector3f eye, Vector3f target, Vector3f up) {
        Vector3f resultZ = Vector3f.deduct(target, eye);
        Vector3f resultX = Vector3f.crossProduct(up, resultZ);
        Vector3f resultY = Vector3f.crossProduct(resultZ, resultX);

        Vector3f resultXN = resultX.normalize();
        Vector3f resultYN = resultY.normalize();
        Vector3f resultZN = resultZ.normalize();

        float[][] matrix = new float[][]
                {
                        {resultXN.getX(), resultYN.getX(), resultZN.getX(), -resultXN.dot(eye)},
                        {resultXN.getY(), resultYN.getY(), resultZN.getY(), -resultYN.dot(eye)},
                        {resultXN.getZ(), resultYN.getZ(), resultZN.getZ(), -resultZN.dot(eye)},
                        {0, 0, 0, 1}
                };
        return new Matrix4f(matrix);
    }

    public static Matrix4f perspective(
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        Matrix4f result = new Matrix4f();
        // Господи, за что ты так со мной, зачем нам нужен этот 0.5F ???
        float tangentMinusOnDegree = (float) (1.0F / (Math.tan(fov * 0.5F)));
        result.setValue(0,0,tangentMinusOnDegree);
        result.setValue(1,1,tangentMinusOnDegree / aspectRatio);
        result.setValue(2,2,(farPlane + nearPlane) / (farPlane - nearPlane));
        result.setValue(2,3,2 * (nearPlane * farPlane) / (nearPlane - farPlane));
        result.setValue(3,2,1.0F);
        return result;
    }

    public static Point2f vertexToPoint(final Vector3f vertex, final int width, final int height) {
        return new Point2f(vertex.getX() * width + width / 2.0F, -vertex.getY() * height + height / 2.0F);
    }
}