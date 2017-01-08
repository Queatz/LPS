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
    private static ShaderProgram swirlShader;

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
            + "varying vec4 v_position;\n" //
            + "\n" //
            + "void main()\n" //
            + "{\n" //
            + "    v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
            + "    v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
            + "    v_position = " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
            + "    gl_Position = u_projTrans * v_position;\n" //
            + "}\n";

    private static String waterFragmentShader = "#ifdef GL_ES\n" //
            + "precision mediump float;\n" //
            + "#endif\n" //
            + "varying vec4 v_color;\n" //
            + "varying vec2 v_texCoords;\n" //
            + "varying vec4 v_position;\n" //
            + "uniform sampler2D u_texture;\n" //
            + "uniform mat4 u_projTrans;\n" //
            + "uniform float u_time;\n" //
            + "void main()\n"//
            + "{\n"
            + "        vec2 uv = v_position.xy / 1532.239;"
            + "        vec2 o = vec2(0.05 * cos(u_time + 100.0 * uv.y) + cos(uv.y * 6. + u_time * .26) * .23," +
            "              0.05 * sin(u_time + 25.0 * uv.y + uv.x * 12.2) + cos(uv.y * 12. + u_time * .6) * .3);\n" +
            "        vec4 m = texture2D(u_texture, fract(uv * 7.34 + u_time + (1.4232 * (distance(cos(u_time + uv * .343), sin(u_time + uv * .3434))))));" +
            "        vec4 color = texture2D(u_texture, fract(uv + o + m.gr * .1624));\n" +
            "        gl_FragColor = color * v_color;"
            + "}";

    private static String swirlFragmentShader = "#ifdef GL_ES\n" //
            + "precision mediump float;\n" //
            + "#endif\n" //
            + "varying vec4 v_color;\n" //
            + "varying vec2 v_texCoords;\n" //
            + "varying vec4 v_position;\n" //
            + "uniform sampler2D u_texture;\n" //
            + "uniform mat4 u_projTrans;\n" //
            + "uniform float u_factor;\n" //
            + "void main()\n"//
            + "{\n"
            + "    float factor = "
            + "        (distance(vec2(.5, .5), v_texCoords * vec2(1. - u_factor) + vec2(u_factor)) * sqrt(2.) * u_factor + u_factor)"
            + "        ;"
            + "    gl_FragColor = vec4(texture2D(u_texture, v_texCoords).rgb * .25, factor);"
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

        if (swirlShader != null) {
            swirlShader.dispose();
            swirlShader = null;
        }
    }

    public static void shaderBegin(ShaderProgram shader) {
        shader.setUniformf("u_time", (float) Math.sin(new Date().getTime() / 25000d % (Math.PI * 2)));
    }

    public static void shaderFactor(ShaderProgram shader, float factor) {
        shader.setUniformf("u_factor", factor);
    }

    public static ShaderProgram getSwirlShader() {
        if (swirlShader != null) {
            return swirlShader;
        }

        swirlShader = new ShaderProgram(waterVertexShader, swirlFragmentShader);

        if (!swirlShader.isCompiled()) throw new IllegalArgumentException("couldn't compile shader: " + swirlShader.getLog());
        return swirlShader;
    }
}
