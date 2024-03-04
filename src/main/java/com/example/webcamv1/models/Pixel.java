package com.example.webcamv1.models;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class Pixel {
  private final int A;
  private final int R;
  private final int G;
  private final int B;

  public Pixel(int argb) {
    A = (argb >> 24) & 0xFF;
    R = (argb >> 16) & 0xFF;
    G = (argb >> 8) & 0xFF;
    B = argb & 0xFF;
  }

  @Contract(" -> new")
  public @NotNull RGB getRGB() {
    return new RGB(R, G, B);
  }

  public int getA() {
    return A;
  }

  public int getR() {
    return R;
  }

  public int getG() {
    return G;
  }

  public int getB() {
    return B;
  }

  public int getGrayScale() {
    int grayScale = (R + G + B) / 3;
    return A << 24 | grayScale << 16 | grayScale << 8 | grayScale;
  }

  @Override
  public String toString() {
    return getRGB().toString();
  }
}
