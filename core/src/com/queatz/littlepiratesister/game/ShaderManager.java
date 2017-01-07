package com.queatz.littlepiratesister.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import java.util.Date;

/**
 * Created by jacob on 1/1/17.
 */

public class ShaderManager {
    private static ShaderProgram shader;
    private static ShaderProgram waterShader;

    private static String vertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
            + "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
            + "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
            + "uniform mat4 u_projectionViewMatrix;\n" //
            + "varying vec4 v_color;\n" //
            + "varying vec2 v_texCoords;\n" //
            + "\n" //
            + "void main()\n" //
            + "{\n" //
            + "    v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
            + "    v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
            + "    gl_Position =  u_projectionViewMatrix * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
            + "}\n";

    private static String fragmentShader = "#ifdef GL_ES\n" //
            + "precision mediump float;\n" //
            + "#endif\n" //
            + "varying vec4 v_color;\n" //
            + "varying vec2 v_texCoords;\n" //
            + "uniform sampler2D u_texture;\n" //
            + "void main()\n"//
            + "{\n" //
            + "    gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n" //
            + "    if (gl_FragColor.a < .5) discard;" //
            + "}";

    private static String waterVertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
            + "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
            + "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
            + "uniform mat4 u_projTrans;\n" //
            + "varying vec4 v_color;\n" //
            + "varying vec2 v_texCoords;\n" //
            + "\n" //
            + "void main()\n" //
            + "{\n" //
            + "    v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
            + "    v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
            + "    gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
            + "}\n";
    private static String waterFragmentShader = "#ifdef GL_ES\n" //
            + "precision mediump float;\n" //
            + "#endif\n" //
            + "varying vec4 v_color;\n" //
            + "varying vec2 v_texCoords;\n" //
            + "uniform sampler2D u_texture;\n" //
            + "uniform mat4 u_projTrans;\n" //
            + "uniform float u_time;\n" //
            + "void main()\n"//
            + "{\n"
            + "        vec2 uv = v_texCoords.xy;"
            + "        vec2 o = vec2(0.05 * cos(u_time + 100.0 * uv.y) + cos(uv.y * 6. + u_time * .26) * .23," +
            "              0.05 * sin(u_time + 25.0 * uv.y + uv.x * 12.2) + cos(uv.y * 12. + u_time * .6) * .3);\n" +
            "        vec4 color = texture2D(u_texture, fract(uv + o + texture2D(u_texture, fract(uv * 7.34 + u_time)).yx * .0624));\n" +
            "        gl_FragColor = color * v_color;"
            + "}";

    public static ShaderProgram getShader() {
        if (shader != null) {
            return shader;
        }

        shader = new ShaderProgram(vertexShader, fragmentShader);

        if (!shader.isCompiled()) throw new IllegalArgumentException("couldn't compile shader: " + shader.getLog());

        return shader;
    }

    public static ShaderProgram getWaterShader() {
        if (waterShader != null) {
            return waterShader;
        }

        waterShader = new ShaderProgram(waterVertexShader, waterFragmentShader);

        if (!waterShader.isCompiled()) throw new IllegalArgumentException("couldn't compile shader: " + waterShader.getLog());

        return waterShader;
    }

    public static void dispose() {
        if (waterShader != null) {
            waterShader.dispose();
            waterShader = null;
        }

        if (shader != null) {
            shader.dispose();
            shader = null;
        }
    }

    public static void shaderBegin(ShaderProgram shader) {
        shader.setUniformf("u_time", (float) (new Date().getTime() / 100000d % 10000d));
    }
}
