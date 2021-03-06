package net.dinastiafoca.world.impl;

import net.dinastiafoca.window.Camera;
import net.dinastiafoca.window.renderer.Renderer;
import net.dinastiafoca.world.Dimension;
import net.dinastiafoca.world.World;
import net.dinastiafoca.world.gen.WorldGenerator;

import java.util.Arrays;

public class SimpleWorld implements World
{
  private final Dimension[] dimensions = new Dimension[3];

  private final WorldGenerator worldGenerator;

  private final long SEED;

  private final int WIDTH;
  private final int HEIGHT;

  private Dimension currentDimension;

  private float gravity = 0.5f;

  public SimpleWorld(int width, int height, long seed) {
    this.SEED = seed;
    this.WIDTH = width;
    this.HEIGHT = height;

    dimensions[0] = new OverworldDimension(this);
    dimensions[1] = new NetherDimension(this);
    dimensions[2] = new TheEndDimension(this);

    currentDimension = dimensions[0];
    worldGenerator = new WorldGenerator(this);

    worldGenerator.regenerateOverworld();
    worldGenerator.regenerateNether();
    worldGenerator.regenerateTheEnd();
  }

  @Override
  public void update() {
    getCurrentDimension().update();
  }

  @Override
  public void render(Renderer renderer, Camera camera) {
    getCurrentDimension().render(renderer, camera);
  }

  @Override
  public Dimension[] getDimensions() {
    return Arrays.copyOf(dimensions, dimensions.length);
  }

  @Override
  public Dimension getDimension(int dimensionIndex)
  {
    return dimensions[dimensionIndex];
  }

  @Override
  public Dimension getCurrentDimension() {
    return currentDimension;
  }

  @Override
  public void setCurrentDimension(int index) {
    this.currentDimension = dimensions[index];
  }

  @Override
  public int getWidth() {
    return WIDTH;
  }

  @Override
  public int getHeight() {
    return HEIGHT;
  }

  @Override
  public float getGravity()
  {
    return gravity;
  }

  @Override
  public void setGravity(float gravity)
  {
    this.gravity = gravity;
  }

  @Override
  public long getSeed()
  {
    return SEED;
  }
}
