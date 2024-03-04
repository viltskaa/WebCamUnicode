package com.example.webcamv1.models;

public record RGB(int r, int g, int b) {
  @Override
  public String toString() {
    return String.format("rgb - %d, %d, %d", r, g, b);
  }
}
